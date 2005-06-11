package gov.nih.nci.ncicb.cadsr.umlbrowser.service.impl;
import gov.nih.nci.ncicb.cadsr.dao.EagerConstants;
import gov.nih.nci.ncicb.cadsr.dao.ObjectClassDAO;
import gov.nih.nci.ncicb.cadsr.dao.ObjectClassRelationshipDAO;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.persistence.dao.domain.AbstractDomainObjectsDAOFactory;
import gov.nih.nci.ncicb.cadsr.umlbrowser.service.UmlBrowserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class UmlBrowserServiceImpl implements UmlBrowserService
{
   AbstractDomainObjectsDAOFactory domainObjectsDaoFactory = null;
  public UmlBrowserServiceImpl()
  {
  }
  
  /**
   * Returns a list of ocrs
   */
  public  List getAssociationsForOC(String ocIdseq)
  {
    ObjectClassRelationshipDAO dao = domainObjectsDaoFactory.getObjectClassRelationshipDAO();
    ObjectClass o = DomainObjectFactory.newObjectClass();
    o.setId(ocIdseq);
    List eager = new ArrayList();
	  //eager.add(EagerConstants.CS_CSI);
    eager.add(EagerConstants.AC_CS_CSI);

    return dao.findByObjectClass(o,eager);  

  }

  /**
   * Returns object class
   */
  public  ObjectClass getObjectClass(String ocIdseq)
  {
    ObjectClassDAO dao = domainObjectsDaoFactory.getObjectClassDAO();
    List eager = new ArrayList();
	  //eager.add(EagerConstants.CS_CSI);
    eager.add(EagerConstants.AC_CS_CSI);    
    return dao.findByPK(ocIdseq,eager);

  }  
  
  /**
   * Return all the super classes, The list sorted from super classes to subclasses
   */
  public List getInheritenceRelationships(ObjectClass oc)
  {
    List superClasses =  new ArrayList();
    ObjectClassRelationshipDAO dao = domainObjectsDaoFactory.getObjectClassRelationshipDAO();
    ObjectClassRelationship ocr = dao.getInheritenceRelationship(oc);
    if(ocr!=null&&ocr.getSource()!=null)
      superClasses.add(ocr.getSource());
    while(ocr!=null&&ocr.getTarget()!=null)
    {
      superClasses.add(0,ocr.getTarget());
      ocr = dao.getInheritenceRelationship(ocr.getTarget());
    }
    
    return superClasses;
    
  }

  public void setDomainObjectsDaoFactory(AbstractDomainObjectsDAOFactory domainObjectsDaoFactory)
  {
    this.domainObjectsDaoFactory = domainObjectsDaoFactory;
  }


  public AbstractDomainObjectsDAOFactory getDomainObjectsDaoFactory()
  {
    return domainObjectsDaoFactory;
  }


}