package gov.nih.nci.ncicb.cadsr.servicelocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ejb.ServiceLocatorImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Factory for ServiceLocators.
 * ServiceLocator instatiation shoud be delegated to this classl
 * 
 */
public class ServiceLocatorFactory 
{
  protected static Log log = LogFactory.getLog(ServiceLocatorFactory.class.getName());
  
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
        
        if(log.isDebugEnabled())
          log.debug("Instatiating ServiceLocator = "+locatorClassName);
        Class locatorClass = Class.forName(locatorClassName);
        locator = (ServiceLocator) locatorClass.newInstance();
      }
      catch (Exception ex) {
        throw new ServiceLocatorException(
          "Unable to Create specified ServiceLocator implementation for "+locatorClassName, ex);
      }
      return locator;
  }   
}