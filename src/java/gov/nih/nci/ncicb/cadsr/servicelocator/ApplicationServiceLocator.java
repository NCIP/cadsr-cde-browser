package gov.nih.nci.ncicb.cadsr.servicelocator;
import gov.nih.nci.ncicb.cadsr.cdebrowser.service.CDEBrowserService;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.LockingService;
import gov.nih.nci.ncicb.cadsr.ocbrowser.service.OCBrowserService;

public interface ApplicationServiceLocator
{
  public static final String APPLICATION_SERVICE_LOCATOR_CLASS_KEY = "ApplicationServiceLocatorClassName";

  public OCBrowserService findOCBrowserService() throws ServiceLocatorException;

  public CDEBrowserTreeService findTreeService() throws ServiceLocatorException;

  public CDEBrowserService findCDEBrowserService() throws ServiceLocatorException;
  
  public LockingService findLockingService() throws ServiceLocatorException;
}