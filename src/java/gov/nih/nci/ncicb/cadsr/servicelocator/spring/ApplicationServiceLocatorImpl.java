package gov.nih.nci.ncicb.cadsr.servicelocator.spring;
import gov.nih.nci.ncicb.cadsr.cdebrowser.service.CDEBrowserService;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.LockingService;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.LockingService;
import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.spring.ApplicationContextFactory;
import gov.nih.nci.ncicb.cadsr.ocbrowser.service.OCBrowserService;

public class ApplicationServiceLocatorImpl implements ApplicationServiceLocator
{

 private  OCBrowserService ocBrowserService = null;

 private  CDEBrowserTreeService treeService = null;

 private  CDEBrowserService cdebrowserService = null;
 
 private  LockingService lockingService = null;

  public ApplicationServiceLocatorImpl()
  {
  }

   public OCBrowserService findOCBrowserService() throws ServiceLocatorException
   {
     if(ocBrowserService==null)
     {
          try
          {
            Object obj = new SpringObjectLocatorImpl().findObject("OCBrowserService");
            ocBrowserService = (OCBrowserService) (obj);
          }
          catch (Exception e)
          {
            throw new ServiceLocatorException("Exp while locating oc serice",e);
          }
     }
     return ocBrowserService;
   }
   public CDEBrowserTreeService findTreeService() throws ServiceLocatorException
   {
     if(treeService==null)
     {
          try
          {
            Object obj = new SpringObjectLocatorImpl().findObject("treeService");
            treeService = (CDEBrowserTreeService) (obj);
          }
          catch (Exception e)
          {
            throw new ServiceLocatorException("Exp while locating tree serice",e);
          }
     }
     return treeService;
   }

   public CDEBrowserService findCDEBrowserService() throws ServiceLocatorException
   {
     if(cdebrowserService==null)
     {
          try
          {
            Object obj = new SpringObjectLocatorImpl().findObject("CDEBrowserService");
            cdebrowserService = (CDEBrowserService) (obj);
          }
          catch (Exception e)
          {
            throw new ServiceLocatorException("Exp while locating cdebrowserService service",e);
          }
     }
     return cdebrowserService;
   }
   
   
    public LockingService findLockingService() throws ServiceLocatorException{
        if(lockingService==null)
        {
             try
             {
               Object obj = new SpringObjectLocatorImpl().findObject("lockingService");
               lockingService = (LockingService) (obj);
             }
             catch (Exception e)
             {
               throw new ServiceLocatorException("Exp while locating lockingService ",e);
             }
        }
        return lockingService;
    }
    
   

}