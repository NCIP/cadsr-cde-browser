package gov.nih.nci.ncicb.cadsr.security.oc4j;
// java utility classes
import com.evermind.security.AbstractUserManager;
import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Properties; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import com.evermind.security.User;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;

public class DBUserManager extends BaseUserManager implements PersistenceConstants, CaDSRConstants {

  private AbstractDAOFactory daoFactory=null;
  private UserManagerDAO userManagerDAO =null;
  private ServiceLocator locator = null;

  /**
   * Init method to initialise the variables.
   * daoFactoryClassName and serviceLocatorClassName are read from the config files
   * @param <b>properties </b> Properties to get dburl.
   */
  public void init(Properties properties) {
   
    try
    {
      log = LogFactory.getLog(DBUserManager.class.getName());
      
      log.debug("Initializing DBUserManager"+this);
      //String daoFactoryClassName = properties.getProperty(DAO_FACTORY_CLASS_KEY);
      String serviceLocatorClassName = properties.getProperty(ServiceLocator.SERVICE_LOCATOR_CLASS_KEY);
      //String dsLookupKeyVal = properties.getProperty(DATASOURCE_KEY);
      
        //log.debug("daoFactoryClassName ="+daoFactoryClassName);  
      log.debug("serviceLocatorClassName ="+serviceLocatorClassName);  
        //log.debug("dsLookupKey ="+dsLookupKeyVal);   
      locator = ServiceLocatorFactory.getLocator(serviceLocatorClassName);
      
      //log.debug("test*** ="+locator.getString(daoFactoryClassName)); 
      
     // locator.setObject(DAO_FACTORY_CLASS_KEY,daoFactoryClassName);
      //locator.setObject(DATASOURCE_KEY,dsLookupKeyVal);
      
      //daoFactory=AbstractDAOFactory.getDAOFactory(locator);    

    }
    catch (Exception e)
    {
      log.fatal("Error Initializing DBUserManaber", e);   
    }
  }


  /**
   * The default method implementation which will just return true
   * flagging that the user exists. The validation is done in
   * checkpassword method.
   * @param <b>username </b> Username of the logged user.
   * @return <b>boolean </b> returns boolean flag to indicate that the
   *                         user exists.
   */
  protected boolean userExists(String username) {
    return true;
    }

  /**

   * This method will check the username/password given by the user,
   * against database values. It returns true if the username and password
   * is correct.
   * @param <b>username </b> username input by the user.
   * @param <b>password </b> password input by the user.
   * @return <b>boolean </b> returns boolean flag to indicate that the user exists.
   */
  protected boolean checkPassword(String username, String password) {
    boolean result = false;

    log.debug("Check password for user ="+username);
    if(daoFactory==null)
      setAbstractDAOFactory();
    if(userManagerDAO==null)
        userManagerDAO = daoFactory.getUserManagerDAO();
    result =  userManagerDAO.validUser(username,password);
    log.debug("User validation= ="+result);    
    return result;
  }

  /**
   * This method will set the groupname mapped to roles stored in the database.
   * The role of username should matche any of the defined roles in  Princpals.xml
   * @param <b>username </b> Username of the user.

   * @param <b>groupname </b> groupname of the user.
   * @return <b>boolean </b> returns boolean flag to indicate that the user role
   *                        exists and user belongs to that group/role
   */
  protected boolean inGroup(String username, String groupname) {
    boolean result = false;

    log.debug("Check if user = "+username+" in group = "+groupname);
    if(daoFactory==null)
      setAbstractDAOFactory();
    if(userManagerDAO==null)
        userManagerDAO = daoFactory.getUserManagerDAO();       
    result =  userManagerDAO.userInGroup(username,groupname);
    log.debug("User  in group = "+result);  
    return result;
  }
  
  private void setAbstractDAOFactory()
  {
    daoFactory=AbstractDAOFactory.getDAOFactory(locator);
        log.debug("Set AbstractDAOFactory ="+daoFactory);  
  }

  public ServiceLocator getLocator()
  {
    return locator;
  }

  public void setLocator(ServiceLocator locator)
  {
    this.locator = locator;
  }
}