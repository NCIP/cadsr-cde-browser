package gov.nih.nci.ncicb.cadsr.security.oc4j;
// java utility classes
import com.evermind.security.AbstractUserManager;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.resource.NCIOC4JUser;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.Properties; 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import com.evermind.security.User;

public class DBUserManager extends BaseUserManager {

  private AbstractDAOFactory daoFactory=null;
  private UserManagerDAO userManagerDAO =null;

  /**
   * Init method to initialise the variables.
   * @param <b>properties </b> Properties to get dburl.
   */
  public void init(Properties properties) {
   
    String serviceLocatorClassName = properties.getProperty(ServiceLocator.SERVICE_LOCATOR_CLASS_KEY);
    String daoFactoryClassName = properties.getProperty(ServiceLocator.DAO_FACTORY_CLASS_KEY);
    ServiceLocator locator = ServiceLocatorFactory.getLocator(serviceLocatorClassName);
    daoFactory=AbstractDAOFactory.getDAOFactory(locator);    
    System.out.println("in usermanager");
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
    if(daoFactory==null)
      return false;
    if(userManagerDAO==null)
        userManagerDAO = daoFactory.getUserManagerDAO();
    return userManagerDAO.validateUser(username);
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
    if(daoFactory==null)
      return false;
    if(userManagerDAO==null)
        userManagerDAO = daoFactory.getUserManagerDAO();
    return userManagerDAO.validUser(username,password);
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
    if(daoFactory==null)
      return false;
    if(userManagerDAO==null)
        userManagerDAO = daoFactory.getUserManagerDAO();
    return userManagerDAO.userInGroup(username,groupname);
  }
  
  /**
   * Method that is used to get user object
   * @param <b>username</b> name of the user, whose information has to be returned
   * @return <b>User</b> returns User object
   */ 
  public User getUser(String username) {
    if(username != null && userExists(username)) {
      NCIOC4JUser user = (NCIOC4JUser)userManagerDAO.getUser(username);
      user.setUserManager(this);
      return user;
    }
    else {
      return getParent().getUser(username);
    }
  }  
}