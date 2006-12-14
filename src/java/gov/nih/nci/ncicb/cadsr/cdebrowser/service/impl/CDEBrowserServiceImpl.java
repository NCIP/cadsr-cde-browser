package gov.nih.nci.ncicb.cadsr.cdebrowser.service.impl;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.cdebrowser.service.CDEBrowserService;
import gov.nih.nci.ncicb.cadsr.dao.AdminComponentDAO;
import gov.nih.nci.ncicb.cadsr.dao.ClassificationSchemeItemDAO;
import gov.nih.nci.ncicb.cadsr.dao.DataElementDAO;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.Definition;
import gov.nih.nci.ncicb.cadsr.domain.bean.ClassSchemeClassSchemeItemBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.ClassificationSchemeBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.ClassificationSchemeItemBean;
import gov.nih.nci.ncicb.cadsr.domain.bean.DataElementBean;
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UtilDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.domain.AbstractDomainObjectsDAOFactory;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.Designation;
import gov.nih.nci.ncicb.cadsr.resource.impl.DefinitionImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;


public class CDEBrowserServiceImpl implements CDEBrowserService
{
  private AbstractDAOFactory daoFactory;
   AbstractDomainObjectsDAOFactory domainObjectsDaoFactory = null;
  
  public CDEBrowserServiceImpl()
  {
  }
  
  public Properties getApplicationProperties(Locale locale)
  {
    UtilDAO utilDAO = daoFactory.getUtilDAO();
    return utilDAO.getApplicationProperties(CaDSRConstants.CDEBROWSER,locale.getCountry());
  }

  public Properties reloadApplicationProperties(Locale locale, String username)
  {
    // Add code to valiad user preveleges
    return getApplicationProperties(locale);
  }

  public void setDaoFactory(AbstractDAOFactory daoFactory)
  {
    this.daoFactory = daoFactory;
  }


  public AbstractDAOFactory getDaoFactory()
  {
    return daoFactory;
  }
  /**
     * This method populate the input params designation and definition
     * release 3.1 TT 1628
     * @param publicId
     */
   public void populateDataElementAltNameDef (gov.nih.nci.ncicb.cadsr.resource.DataElement de)
   {
     DataElementDAO dao = domainObjectsDaoFactory.getDataElementDAO();

     DataElement deExample = new DataElementBean();      
     deExample.setPublicId(de.getCDEId());
     List eager = new ArrayList();
     eager.add("alternateNames");
     eager.add("definitions");
     eager.add("alternateNames.attCsCsis");
     eager.add("definitions.attCsCsis");
     List<DataElement> deList = dao.find(deExample, eager);
     DataElement dePop = deList.get(0);
     
     List altNames = dePop.getAlternateNames();
     Iterator iter = altNames.iterator();
     //copy alt names cscsi definition for dePop to de
     while (iter.hasNext()) {
         AlternateName altName = (AlternateName) iter.next();
         //find the designation that match the altName
         for (int i=0; i<de.getDesignations().size(); i++) {
             Designation designation =(Designation) de.getDesignations().get(i);
             if (altName.getId().equalsIgnoreCase(designation.getDesigIDSeq())){
                 List cscsis = altName.getCsCsis();
                 //create a resource object from the bean
                 for (int j=0; j<cscsis.size(); j++) {
                     ClassSchemeClassSchemeItemBean cscsiBean = (ClassSchemeClassSchemeItemBean) cscsis.get(j);
                    designation.addCscsi(createCsCsiFromBean(cscsiBean));
                 }
                 break;
             }
         }
     }
     
     iter = dePop.getDefinitions().iterator();
     while (iter.hasNext()){
         Definition defBean = (Definition) iter.next();
         gov.nih.nci.ncicb.cadsr.resource.Definition definition = new DefinitionImpl();
         definition.setContext(new ContextTransferObject());
         definition.getContext().setConteIdseq(defBean.getContext().getId());
         definition.getContext().setName(defBean.getContext().getName());
         definition.setDefinition(defBean.getDefinition());
         definition.setType(defBean.getType());
//       need to add language
//       definition.setLanguage(defBean.get);
         
         List cscsis = defBean.getCsCsis();
         for (int i=0; i<cscsis.size(); i++) {
             ClassSchemeClassSchemeItemBean cscsiBean = (ClassSchemeClassSchemeItemBean) cscsis.get(i);
             definition.addCsCsi(createCsCsiFromBean(cscsiBean));
         }
         if (de.getDefinitions() == null)
           de.setDefinitions(new ArrayList());
           de.getDefinitions().add(definition);
     }
     
   }

   public void setDomainObjectsDaoFactory(AbstractDomainObjectsDAOFactory domainObjectsDaoFactory)
   {
     this.domainObjectsDaoFactory = domainObjectsDaoFactory;
   }


   public AbstractDomainObjectsDAOFactory getDomainObjectsDaoFactory()
   {
     return domainObjectsDaoFactory;
   }
  
  private ClassSchemeItem createCsCsiFromBean (ClassSchemeClassSchemeItemBean cscsiBean){
    ClassSchemeItem cscsi = new CSITransferObject();
    cscsi.setCsCsiIdseq(cscsiBean.getId());
    cscsi.setClassSchemeLongName(cscsiBean.getCs().getLongName());
    cscsi.setClassSchemePrefName(cscsiBean.getCs().getPreferredName());   
    cscsi.setClassSchemeDefinition(cscsiBean.getCs().getPreferredDefinition());
    cscsi.setClassSchemeItemName(cscsiBean.getCsi().getName());
    cscsi.setClassSchemeItemType(cscsiBean.getCsi().getType());
    return cscsi;
  }

   public List getReferenceDocuments(String acIdseq){
      AdminComponentDAO dao = domainObjectsDaoFactory.getAdminComponentDAO();
      ClassificationSchemeBean ac = new ClassificationSchemeBean();
      ac.setId(acIdseq);
      List refDocs = dao.getReferenceDocuments(ac);
      return refDocs;
   }

   public List getReferenceDocumentsForCSI(String csiIdseq){
      ClassificationSchemeItemDAO dao = domainObjectsDaoFactory.getClassificationSchemeItemDAO();
      ClassificationSchemeItemBean csi = new ClassificationSchemeItemBean();
      csi.setId(csiIdseq);
      List refDocs = dao.getReferenceDocuments(csi);
      return refDocs;
      
   }
  
}