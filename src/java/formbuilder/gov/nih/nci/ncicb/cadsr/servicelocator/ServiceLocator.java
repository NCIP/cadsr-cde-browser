package gov.nih.nci.ncicb.cadsr.servicelocator;

import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorException;

import java.net.URL;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;

import javax.naming.InitialContext;

import javax.rmi.PortableRemoteObject;

import javax.sql.DataSource;

public interface ServiceLocator {

  public static final String SERVICE_LOCATOR_CLASS_KEY="ServiceLocatorClassName";

  /**
   * will get the ejb Local home factory. clients need to cast to the type of
   * EJBHome they desire
   *
   * @return the Local EJB Home corresponding to the homeName
   */
  public EJBLocalHome getLocalHome(String jndiHomeName)
    throws ServiceLocatorException ;

  /**
   * will get the ejb Remote home factory. clients need to cast to the type of
   * EJBHome they desire
   *
   * @return the EJB Home corresponding to the homeName
   */
  public EJBHome getRemoteHome(
    String jndiHomeName,
    Class className);

  /**
   * This method obtains the datasource itself for a caller
   *
   * @return the DataSource corresponding to the name parameter
   */
  public DataSource getDataSource(String dataSourceName);
  
  /**
   * @return the URL value corresponding to the env entry name.
   */
  public URL getUrl(String envName);

  /**
   * @return the boolean value corresponding to the env entry such as
   *         SEND_CONFIRMATION_MAIL property.
   */
  public boolean getBoolean(String envName);

  /**
   * @return the String value corresponding to the env entry name.
   */
  public String getString(String envName);
  
/**
   * The oed by Object inserted by this method must be available by a getter
   * with the matching key
   * @param key 
   * @param value
   */
  public void setObject(String key,Object value);
}
