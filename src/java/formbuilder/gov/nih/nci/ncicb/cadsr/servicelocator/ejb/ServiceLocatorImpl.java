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



/**
 * This class is an implementation of the Service Locator pattern. It is used
 * to looukup resources such as EJBHomes, JMS Destinations, etc.
 */
public class ServiceLocatorImpl implements ServiceLocator{
  private transient InitialContext ic;

  public ServiceLocatorImpl()  {
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
      return (EJBLocalHome) ic.lookup(jndiHomeName);
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
      Object objref = ic.lookup(jndiHomeName);

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
      if(getProperty(DRIVER_MANAGER_DS)!=null&&getProperty(DRIVER_MANAGER_DS).equalsIgnoreCase("false"))
        return (DataSource) ic.lookup(dataSourceName);
      else
        return DataSourceUtil.getDriverManagerDS(getProperty(DRIVER_CLASS_NAME)
              ,getProperty(CONNECTION_STRING),getProperty(USERNAME)
              ,getProperty(PASSWORD));
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
      return (URL) ic.lookup(envName);
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
      return ((Boolean) ic.lookup(envName)).booleanValue();
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
      return (String) ic.lookup(envName);
    }
    catch (Exception e) {
      throw new ServiceLocatorException("",e);
    }
  }
  
  public String getProperty(String propName)
  {
  
     // This is a temp fix need to revisit
      String propVal = null;
      try{
    //   PropertyReader propReaderKeys =
     //  new PropertyReader(this.getClass(),this.DEPLOYMENT_FILENAME);
     //  Properties keysProperties = propReaderKeys.loadProperties();
     //  propVal= keysProperties.getProperty(propName);
     Map aMap = new HashMap();
     aMap.put(DRIVER_MANAGER_DS,"true");
     aMap.put(DRIVER_CLASS_NAME,"oracle.jdbc.driver.OracleDriver");
     aMap.put(CONNECTION_STRING,"jdbc:oracle:thin:@localhost:1521:red");
     aMap.put(USERNAME,"scott");
     aMap.put(PASSWORD,"tiger");
     
     return (String) aMap.get(propName);
     
      }
      catch(Exception ex)
      {
      }
      return propVal;
  }
}
