package gov.nih.nci.ncicb.cadsr.servicelocator.ejb;

import gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc.util.DataSourceUtil;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;

import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;

import javax.sql.DataSource;

import gov.nih.nci.ncicb.cadsr.servicelocator.AbstractServiceLocator;


/**
 * This class is an implementation of the Service Locator pattern. It is used
 * to looukup resources such as EJBHomes, JMS Destinations, etc.
 */
public class ServiceLocatorImpl extends AbstractServiceLocator{
  private transient InitialContext ic;

  
  public ServiceLocatorImpl()  {
    ejbLookupPrefix = "java:comp/env/ejb/";
    envLookupPrefix = "java:comp/env/";
    dsLookupPrefix = "jdbc/";  
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
      return (EJBLocalHome) ic.lookup(resolveEJBLookupKey(jndiHomeName));
    }
    catch (Exception e) {
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
      System.out.println("Key="+key);
      Object objref = ic.lookup(key);

      return (EJBHome) PortableRemoteObject.narrow(objref, className);
    }
    catch (Exception e) {
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
          System.out.println("DS key "+key);
          return (DataSource) ic.lookup(key);
      }
      catch (Exception e) {
        throw new ServiceLocatorException("",e);
      }
  }

  /**
   * @return the URL value corresponding to the env entry name.
   */
  public URL getUrl(String envName) throws ServiceLocatorException {
  
      try {
        return (URL) ic.lookup(resolveEnvLookupKey(envName));
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
        return ((Boolean) ic.lookup(resolveEnvLookupKey(envName))).booleanValue();
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
          System.out.println("DS key "+key);
        return (String) ic.lookup(key);
      }
      catch (Exception e) {
        throw new ServiceLocatorException("",e);
      }
  }
  
}
