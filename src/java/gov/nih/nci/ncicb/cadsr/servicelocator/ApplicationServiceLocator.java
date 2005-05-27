package gov.nih.nci.ncicb.cadsr.servicelocator;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;
import gov.nih.nci.ncicb.cadsr.umlbrowser.service.UmlBrowserService;

public interface ApplicationServiceLocator 
{
  public static final String APPLICATION_SERVICE_LOCATOR_CLASS_KEY = "ApplicationServiceLocatorClassName";

  public UmlBrowserService findUmlBrowserService() throws ServiceLocatorException;
  
  public CDEBrowserTreeService findTreeService() throws ServiceLocatorException;
}