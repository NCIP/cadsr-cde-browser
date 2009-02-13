package gov.nih.nci.ncicb.cadsr.common.security.oc4j;

// com.evermind.security packages used in implementing custom user manager class
import com.evermind.security.AbstractUserManager;
import com.evermind.security.User;
import com.evermind.security.Group;
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;


/**
 * 
 */
public abstract class BaseUserManager extends AbstractUserManager {

   protected static Log log = LogFactory.getLog(BaseUserManager.class.getName());
   
   private boolean debug = false;
   
  /**
   * Method to determine if the given username is a valid user on the system.
   * @param <b>username</b> name of the user

   * @return <b>boolean</b> returns true or false depending whether the user 
   *                        exists or not.
   */
  protected abstract boolean userExists(String username);

  /**
   * Method to check the supplied password for a user is valid.
   * @param <b>username</b> name of the user
   * @param <b>password</b> password of the user
   * @return <b>boolean</b> returns true or false depending whether the specified

   *                        username and password are correct or not.
   */
  protected abstract boolean checkPassword(String username, String password);

  /**
   * Determine if a user is a member of a particular group.
   * @param <b>username</b> name of the user
   * @param <b>groupname</b> name of the group 
   * @return <b>boolean</b> returns true or false depending whether the user 
   *                        belongs to the specified group or not.
   */

  protected abstract boolean inGroup(String username, String groupname);

  /**
   * Method that is used to get user object
   * @param <b>username</b> name of the user, whose information has to be returned
   * @return <b>User</b> returns User object
   */ 
  public User getUser(String username) {

    log.info("Geting User with username= "+username);
    if(username != null && userExists(username)) {
      OC4JUserObject user = new OC4JUserObject(username);
      user.setUserManager(this);
      return user;
    }
    else {
      return getParent().getUser(username);
    }
  }  

}

