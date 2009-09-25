package gov.nih.nci.ncicb.cadsr.common.servicelocator;
import gov.nih.nci.ncicb.cadsr.common.cdebrowser.service.CDEBrowserService;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.service.OCBrowserService;
import gov.nih.nci.ncicb.cadsr.contexttree.service.CDEBrowserTreeService;

public interface ApplicationServiceLocator
{
  public static final String APPLICATION_SERVICE_LOCATOR_CLASS_KEY = "ApplicationServiceLocatorClassName";

  public OCBrowserService findOCBrowserService() throws ServiceLocatorException;

  public CDEBrowserTreeService findTreeService() throws ServiceLocatorException;

  public CDEBrowserService findCDEBrowserService() throws ServiceLocatorException;
  
  //public LockingService findLockingService() throws ServiceLocatorException;
}