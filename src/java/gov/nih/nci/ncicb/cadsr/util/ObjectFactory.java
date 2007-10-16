package gov.nih.nci.ncicb.cadsr.util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ObjectFactory 
{

  protected static Log log = LogFactory.getLog(ObjectFactory.class.getName());

  public ObjectFactory()
  {
  }
  public static Object createObect(String className) throws Exception
  {       
      if(log.isDebugEnabled())
        log.debug("Instatiating Object = "+className);
      Class theClass = forName(new ObjectFactory().getClass(),className);
      return theClass.newInstance();

  }      
  /**
   * Load a class for the specified name and scope.
   * In the cases when the active ClassLoader is null, we use
   *  Class.forName(), otherwise ClassLoader.loadClass() is used
   */  
  public static Class forName(Class scope, String className)
    throws ClassNotFoundException
  {
    ClassLoader loader = null;

    try
    {
      loader = Thread.currentThread().getContextClassLoader();
    } // end try
    catch(Exception e)
    {
      loader = scope.getClassLoader();
    } // end catch

    return (loader != null) ? loader.loadClass(className)
                            : Class.forName(className);
  } // end forName  
}