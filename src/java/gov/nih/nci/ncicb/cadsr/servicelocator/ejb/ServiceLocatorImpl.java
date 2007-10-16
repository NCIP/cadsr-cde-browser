package gov.nih.nci.ncicb.cadsr.servicelocator.ejb;

import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorAdapter;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;

import java.net.URL;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;

import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;

import javax.sql.DataSource;


/**
 * This class is an implementation of the Service Locator pattern. It is used
 * to looukup resources such as EJBHomes, JMS Destinations, etc.
 */
public class ServiceLocatorImpl extends ServiceLocatorAdapter {
  private transient InitialContext ic;
  
  public ServiceLocatorImpl()  {
    ejbLookupPrefix = "java:comp/env/ejb/";
    envLookupPrefix = "java:comp/env/";
    dsLookupPrefix = "java:comp/env/jdbc/";  
    try {
      ic = new InitialContext();
    }
    catch (Exception e) {
      throw new ServiceLocatorException("",e);
    }
  }

  /**
   * will get the ejb Local home factory. clients need to cast to the type of
   * EJBHome they desire
   *
   * @return the Local EJB Home corresponding to the homeName
   */
  public EJBLocalHome getLocalHome(String jndiHomeName)
    {

    try {
      if(log.isDebugEnabled())
        log.debug("Lookup EJB Local Home with key "+resolveEJBLookupKey(jndiHomeName));
      return (EJBLocalHome) ic.lookup(resolveEJBLookupKey(jndiHomeName));
    }
    catch (Exception e) {
      if(log.isDebugEnabled())
            log.debug("Exception while EJBLocalHome lookup"+e);      
      throw new ServiceLocatorException("",e);
    }
  }

  /**
   * will get the ejb Remote home factory. clients need to cast to the type of
   * EJBHome they desire
   *
   * @return the EJB Home corresponding to the homeName
   */
  public EJBHome getRemoteHome(
    String jndiHomeName,
    Class className)  {      
    try {
      String key = resolveEJBLookupKey(jndiHomeName);
      if(log.isDebugEnabled())
        log.debug("Lookup EJB Remote Home with key "+key);
      Object objref = ic.lookup(key);
      return (EJBHome) PortableRemoteObject.narrow(objref, className);
    }
    catch (Exception e) {
      if(log.isDebugEnabled())
            log.debug("Exception while EJBRemoteHome lookup"+e);      
      throw new ServiceLocatorException("",e);
    }
  }


  /**
   * This method obtains the datasource itself for a caller
   *
   * @return the DataSource corresponding to the name parameter
   */
  public DataSource getDataSource(String dataSourceName)
    {
      try {
          String key = resolveDsLookupKey(dataSourceName);
          if(log.isDebugEnabled())
            log.debug("Lookup DataSource with key="+key);          
          DataSource ds = (DataSource) ic.lookup(key);
          if(log.isDebugEnabled())
            log.debug("Lookedup String with key="+key+" Value="+ds);
          return ds;
      }
      catch (Exception e) {
        if(log.isDebugEnabled())
            log.debug("Exception while DS lookup"+e);      
        throw new ServiceLocatorException("",e);
      }
  }

  /**
   * @return the URL value corresponding to the env entry name.
   */
  public URL getUrl(String envName) throws ServiceLocatorException {
  
      try {
          String key = resolveEnvLookupKey(envName);
          URL url = (URL) ic.lookup(resolveEnvLookupKey(envName));
          if(log.isDebugEnabled())
            log.debug("Lookup String with key="+key+" Value="+url);
          return url;
      }
      catch (Exception e) {
        throw new ServiceLocatorException("",e);
      }
  }

  /**
   * @return the boolean value corresponding to the env entry such as
   *         SEND_CONFIRMATION_MAIL property.
   */
  public boolean getBoolean(String envName)  {        
      try {
          boolean result ; 
          String key = resolveEnvLookupKey(envName);
          result = ((Boolean) ic.lookup(key)).booleanValue();
          if(log.isDebugEnabled())
            log.debug("Lookup String with key="+key+" Value="+result);
          return result;
      }
      catch (Exception e) {
        throw new ServiceLocatorException("",e);
      }
  }

  /**
   * @return the String value corresponding to the env entry name.
   */
  public String getString(String envName) {

      try {
          String key = resolveEnvLookupKey(envName);
          if(log.isDebugEnabled())
            log.debug("Lookup String with key="+key);          
          String result = (String) ic.lookup(key);
          if(log.isDebugEnabled())
            log.debug("Lookedup String with key="+key+" Value="+result);
          return result;
      }
      catch (Throwable e) {
        if(log.isDebugEnabled())
            log.debug("Exception while String lookup"+e);
        throw new ServiceLocatorException("",e);
      }
  }

  public void setObject(String key, Object value)
  {
      try {
          String resolvedKey = resolveEnvLookupKey(key);         
          if(log.isDebugEnabled())
            log.debug("Bind object with key "+resolvedKey);
          ic.bind(resolvedKey,value);
      }
      catch (Exception e) {
        throw new ServiceLocatorException("",e);
      }
  }
  
}
