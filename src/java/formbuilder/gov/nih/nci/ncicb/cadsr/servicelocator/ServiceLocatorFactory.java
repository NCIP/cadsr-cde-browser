package gov.nih.nci.ncicb.cadsr.servicelocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ejb.ServiceLocatorImpl;



public class ServiceLocatorFactory 
{
  public ServiceLocatorFactory()
  {
  }
 public static ServiceLocator getEJBLocator()
  {
    return new ServiceLocatorImpl();
  }

  public static ServiceLocator getLocator(String locatorClassName)
  {
      ServiceLocator locator =null;
      try {
        System.out.println("In fServicelocator factory locatorClassName="+locatorClassName);        
        Class locatorClass = Class.forName(locatorClassName);
        locator = (ServiceLocator) locatorClass.newInstance();
      }
      catch (Exception ex) {
        throw new ServiceLocatorException(
          "Unable to create specified ServiceLocator implementation", ex);
      }
      return locator;
  }   
}