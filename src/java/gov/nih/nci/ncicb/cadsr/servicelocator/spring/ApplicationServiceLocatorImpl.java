package gov.nih.nci.ncicb.cadsr.servicelocator.spring;
import gov.nih.nci.ncicb.cadsr.cdebrowser.tree.service.CDEBrowserTreeService;
import gov.nih.nci.ncicb.cadsr.servicelocator.ApplicationServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.spring.ApplicationContextFactory;
import gov.nih.nci.ncicb.cadsr.umlbrowser.service.UmlBrowserService;

public class ApplicationServiceLocatorImpl implements ApplicationServiceLocator
{

 private static UmlBrowserService umlBrowserService = null;
 
 private static CDEBrowserTreeService treeService = null; 

  public ApplicationServiceLocatorImpl()
  {
  }
  
   public UmlBrowserService findUmlBrowserService() throws ServiceLocatorException
   {
     if(umlBrowserService==null)
     {    
          try
          {          
            Object obj = new SpringObjectLocatorImpl().findObject("umlBrowserService");
            umlBrowserService = (UmlBrowserService) (obj);
          }
          catch (Exception e)
          {
            throw new ServiceLocatorException("Exp while locating uml serice",e);
          }
     }
     return umlBrowserService;
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
}