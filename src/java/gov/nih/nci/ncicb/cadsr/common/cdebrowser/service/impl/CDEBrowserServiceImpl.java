package gov.nih.nci.ncicb.cadsr.common.cdebrowser.service.impl;

import gov.nih.nci.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.cadsr.domain.ClassificationScheme;
import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.Definition;
import gov.nih.nci.cadsr.domain.DefinitionClassSchemeItem;
import gov.nih.nci.cadsr.domain.DesignationClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.service.CDEBrowserService;
import gov.nih.nci.ncicb.cadsr.common.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.UtilDAO;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.domain.AbstractDomainObjectsDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.resource.Designation;
import gov.nih.nci.ncicb.cadsr.common.resource.impl.DefinitionImpl;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;


public class CDEBrowserServiceImpl implements CDEBrowserService
{
	private AbstractDAOFactory daoFactory;
	AbstractDomainObjectsDAOFactory domainObjectsDaoFactory = null;

	private ApplicationService appService = null;

	public CDEBrowserServiceImpl()
	{
	}

	public Properties getApplicationProperties(Locale locale, String toolName) {
		
		UtilDAO utilDAO = daoFactory.getUtilDAO();
		Properties browseProperty = getApplicationProperties(locale);
		Properties formProperty = utilDAO.getApplicationProperties(toolName, locale.getCountry());
		if (formProperty != null)
			browseProperty.putAll(formProperty);
		return browseProperty;
	}

	public Properties getApplicationProperties(Locale locale)
	{
		UtilDAO utilDAO = daoFactory.getUtilDAO();
		Properties browseProperty = utilDAO.getApplicationProperties(CaDSRConstants.CDEBROWSER,locale.getCountry());
		Properties urlProperty = utilDAO.getApplicationURLProperties(locale.getCountry());
		Properties cadsrProperty = utilDAO.getApplicationProperties(CaDSRConstants.cadsrToolName,locale.getCountry()); 
		if (urlProperty != null)
			browseProperty.putAll(urlProperty);   //"FormBuilder_URL", formProperty.get("URL"));
		if (cadsrProperty != null)
			browseProperty.putAll(cadsrProperty);   //"context_test", formProperty.get("EXCLUDE.CONTEXT.00.NAME"));

		return browseProperty;
	}

	public Properties reloadApplicationProperties(Locale locale, String username)
	{
		// Add code to valid user privileges
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
	 * instantiate the application service for cadsr api
	 *
	 */
	private void getCadsrService()
	{
		try {
			if (appService == null)
			{
				CDEBrowserParams params = CDEBrowserParams.getInstance();
				String url = params.getCadsrAPIUrl();
				if (!url.equals(""))
					appService = ApplicationServiceProvider.getApplicationServiceFromUrl(url, "CaDsrServiceInfo");
				else
					appService = ApplicationServiceProvider.getApplicationService("CaDsrServiceInfo");
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
	}

	/**
	 * This method populate the input params designation and definition
	 * release 3.1 TT 1628
	 * @param publicId
	 */
	public void populateDataElementAltNameDef (gov.nih.nci.ncicb.cadsr.common.resource.DataElement de)
	{

		DataElement deExample = new DataElement();      
		deExample.setPublicID(new Long(de.getCDEId()));

		List deList = null;

		try { 
			this.getCadsrService();
			deList = appService.search(deExample.getClass(), deExample);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		DataElement dePop = (DataElement)deList.get(0);


		for (gov.nih.nci.cadsr.domain.Designation altName : dePop.getDesignationCollection()) {
			//find the designation that match the altName
			for (int i=0; i<de.getDesignations().size(); i++) {
				Designation designation =(Designation) de.getDesignations().get(i);
				if (altName.getId().equalsIgnoreCase(designation.getDesigIDSeq())){

					Collection<DesignationClassSchemeItem> desCsis = altName.getDesignationClassSchemeItemCollection();
					List<ClassSchemeClassSchemeItem> cscsis = new ArrayList<ClassSchemeClassSchemeItem>();

					for(DesignationClassSchemeItem desCsi : desCsis) 
						cscsis.add(desCsi.getClassSchemeClassSchemeItem());

					//create a resource object from the bean
					for (int j=0; j<cscsis.size(); j++) {
						designation.addCscsi(createCsCsiFromBean(cscsis.get(j)));
					}
					break;
				}
			}
		}



		for(Definition def : dePop.getDefinitionCollection()) {
			gov.nih.nci.ncicb.cadsr.common.resource.Definition definition = new DefinitionImpl();
			definition.setContext(new ContextTransferObject());
			definition.getContext().setConteIdseq(def.getContext().getId());
			definition.getContext().setName(def.getContext().getName());
			definition.setDefinition(def.getText());
			definition.setType(def.getType());			
			//definition.setLanguage(defBean.get);

			Collection<DefinitionClassSchemeItem> defCsis = def.getDefinitionClassSchemeItemCollection();
			List<ClassSchemeClassSchemeItem> cscsis = new ArrayList<ClassSchemeClassSchemeItem>();

			for(DefinitionClassSchemeItem defCsi : defCsis) 
				cscsis.add(defCsi.getClassSchemeClassSchemeItem());

			for (int i=0; i<cscsis.size(); i++) {
				definition.addCsCsi(createCsCsiFromBean(cscsis.get(i)));
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

	private ClassSchemeItem createCsCsiFromBean (ClassSchemeClassSchemeItem cscsiIn){
		ClassSchemeItem cscsi = new CSITransferObject();
		cscsi.setCsCsiIdseq(cscsiIn.getId());
		cscsi.setClassSchemeLongName(cscsiIn.getClassificationScheme().getLongName());
		cscsi.setClassSchemePrefName(cscsiIn.getClassificationScheme().getPreferredName());   
		cscsi.setClassSchemeDefinition(cscsiIn.getClassificationScheme().getPreferredDefinition());
		cscsi.setClassSchemeItemName(cscsiIn.getClassificationSchemeItem().getLongName());
		cscsi.setClassSchemeItemType(cscsiIn.getClassificationSchemeItem().getType());
		return cscsi;
	}

	public List getReferenceDocuments(String acIdseq){
		ClassificationScheme cs = new ClassificationScheme();
		cs.setId(acIdseq);
		List csList;
		try {
			this.getCadsrService();
			csList = appService.search(cs.getClass(), cs);
		} catch (ApplicationException e) {
			throw new RuntimeException(e);
		}
		cs = (ClassificationScheme)csList.get(0);


		return new ArrayList(cs.getReferenceDocumentCollection());
	}

	/* public List getReferenceDocumentsForCSI(String csiIdseq){
    ClassificationSchemeItemDAO dao = domainObjectsDaoFactory.getClassificationSchemeItemDAO();
    ClassificationSchemeItemBean csi = new ClassificationSchemeItemBean();
    csi.setId(csiIdseq);
    List refDocs = dao.getReferenceDocuments(csi);
    return refDocs;

 }*/
}