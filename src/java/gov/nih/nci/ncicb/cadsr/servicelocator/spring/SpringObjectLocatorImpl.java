package gov.nih.nci.ncicb.cadsr.servicelocator.spring;
import gov.nih.nci.ncicb.cadsr.servicelocator.ObjectLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;

import org.springframework.context.ApplicationContext;


public class SpringObjectLocatorImpl implements ObjectLocator
{
  public static ApplicationContext applicationContext = null;

  /**
   * This static method should replace findObject(String).
   * @param key
   * @return
   */
  public static Object getObject(String key)
  {
     if(applicationContext==null)
      throw new ServiceLocatorException("applicationContext is null");
     return applicationContext.getBean(key);
  }
  
  /**
   * @deprecated use getObject() statically
   */
  public Object findObject(String key)
  {
     if(applicationContext==null)
      throw new ServiceLocatorException("applicationContext is null");
     return applicationContext.getBean(key);
  }
  
}