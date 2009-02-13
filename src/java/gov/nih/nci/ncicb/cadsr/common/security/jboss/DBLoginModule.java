package gov.nih.nci.ncicb.cadsr.common.security.jboss;

import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorFactory;

import java.io.IOException;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.AbstractServerLoginModule;


public class DBLoginModule extends AbstractServerLoginModule {
 
  protected Log logger =  LogFactory.getLog(DBLoginModule.class.getName());
  
  private Principal identity;
  private char[] credential;
  private AbstractDAOFactory daoFactory = null;
  private UserManagerDAO userManagerDAO = null;
  private ServiceLocator locator = null;
  private String appUserName = null;
  private String appPassword = null;

  public void initialize(Subject p0, CallbackHandler p1, Map p2, Map p3) {
		try
		{
		    super.initialize(p0, p1, p2, p3);
		    for (Iterator it = p3.entrySet().iterator(); it.hasNext();) {
		      logger.info("Option: " +it.next().toString());
		    }
		    String serviceLocatorClassName =
		      (String) p3.get(ServiceLocator.SERVICE_LOCATOR_CLASS_KEY);
		    locator = ServiceLocatorFactory.getLocator(serviceLocatorClassName);
		    logger.info("Service Locator class: "+locator);
		    
		    //get the application user from login config file
		    appUserName = (String) p3.get("applicationUserName");
		    appPassword = (String) p3.get("applicationPassword");

		    if (daoFactory == null) {
		      setAbstractDAOFactory();
		    }
		    if (userManagerDAO == null) {
		      userManagerDAO = daoFactory.getUserManagerDAO();
		    }
		}
		catch (Exception le)
		{
			logger.error("error at initialize : ", le);
		}
  	}
  
  public boolean login() throws LoginException {
		try
		{
		    logger.info("In another login");
		    if (super.login()) {
		      Object username = sharedState.get("javax.security.auth.login.name");
		      if (username instanceof Principal) {
		        identity = (Principal) username;
		      }
		      else {
		        String name = username.toString();
		        try {
		          identity = createIdentity(name);
		        }
		        catch (Exception e) {
		          throw new LoginException(
		            "Failed to create principal: " + e.getMessage());
		        }
		      }
		      Object password = sharedState.get("javax.security.auth.login.password");
		      if (password instanceof char[]) {
		        credential = (char[]) password;
		      }
		      else if (password != null) {
		        String tmp = password.toString();
		        credential = tmp.toCharArray();
		      }
		
		      return true;
		    }
		    super.loginOk = false;
		    String[] info = getUsernameAndPassword();
		    String username = info[0];
		    String password = info[1];
		    if ((username == null) && (password == null)) {
		      identity = unauthenticatedIdentity;
		    }
		    if (identity == null) {
		      try {
		        identity = createIdentity(username);
		      }
		      catch (Exception e) {
		        throw new LoginException(
		          "Failed to create principal: " + e.getMessage());
		      }
		      String errMsg = userCredential(username.toUpperCase(), password);
		      if (!errMsg.equals(""))
		          throw new FailedLoginException(errMsg);
		      
		      /* since user credential takes care of the authentication, it is not needed
		      if (!authenticateUser(username, password)) {
		          throw new FailedLoginException("Incorrect username and password");
		      } */
		    }
		    if (getUseFirstPass()) {
		      sharedState.put("javax.security.auth.login.name", username);
		      sharedState.put("javax.security.auth.login.password", credential);
		    }
		    super.loginOk = true;
		    logger.debug("loginOk="+loginOk);
		}
		catch (LoginException le)
		{
			logger.error("error at login : ", le);
			throw le;
		}
	    return true;
  	}


  protected Group[] getRoleSets() throws LoginException 
  {
    /*SimpleGroup grp = new SimpleGroup("Roles");
    Group[] groups = { grp };
    grp.addMember(new SimplePrincipal("CDE MANAGER"));*/
    SimpleGroup grp = new SimpleGroup("Roles");
    Group[] groups = { grp };
	try
	{
	    Map rolesAndContexts =
	      userManagerDAO.getContextsForAllRoles(
	        this.getUsername(), "QUEST_CONTENT");
	    Set roles = rolesAndContexts.keySet();
	    //log.debug("Roles for user : "+this.getUsername());
	    for (Iterator it = roles.iterator(); it.hasNext();) 
	    {
	      String role = (String)it.next();
	      grp.addMember(new SimplePrincipal(role));
	      logger.debug("Role: "+role + this.getUsername());
	    }
	    logger.debug("Groups : "+groups);
	}
	catch (Exception le)
	{
		logger.error("error at getRoleSets : ", le);
	}
    return groups;
  }

  protected Principal getIdentity() {
    return identity;
  }

  protected String[] getUsernameAndPassword() throws LoginException {
    String[] info = { null, null };
    if (callbackHandler == null) {
      throw new LoginException(
        "Error: no CallbackHandler available to collect authentication information");
    }
    NameCallback nc = new NameCallback("User name: ", "guest");
    PasswordCallback pc = new PasswordCallback("Password: ", false);
    Callback[] callbacks = { nc, pc };
    String username = null;
    String password = null;
    try {
      callbackHandler.handle(callbacks);
      username = nc.getName();
      char[] tmpPassword = pc.getPassword();
      if (tmpPassword != null) {
        credential = new char[tmpPassword.length];
        System.arraycopy(tmpPassword, 0, credential, 0, tmpPassword.length);
        pc.clearPassword();
        password = new String(credential);
      }
    }
    catch (IOException ioe) {
      throw new LoginException(ioe.toString());
    }
    catch (UnsupportedCallbackException uce) {
      throw new LoginException(
        "CallbackHandler does not support: " + uce.getCallback());
    }
    info[0] = username;
    info[1] = password;
    logger.debug("Username="+username);
    return info;
  }
  
  private void setAbstractDAOFactory() {
    daoFactory = AbstractDAOFactory.getDAOFactory(locator);
    
  }

  
  protected boolean authenticateUser(
    String username,
    String password) {
    boolean isUserValid = false;
    isUserValid = userManagerDAO.validUser(username, password);
    
    return isUserValid;
  }

  protected String getUsername() {
    String username = null;
    if (getIdentity() != null) {
      username = getIdentity().getName();
    }

    return username;
  }

  protected Principal createIdentity(String p0) throws Exception {
    return new SimplePrincipal(p0);
  }
  
  protected String userCredential(String loginUserid, String loginPswd)
  {
	   CaDsrUserCredentials uc = new CaDsrUserCredentials();
	   try
	   {
		  if (appUserName != null)
	        uc.validateCredentials(appUserName, appPassword, loginUserid, loginPswd);
	    }
	    catch (Exception ex)
	    {
	        logger.error("Failed credential validation, code is " + uc.getCheckCode(), ex);
	    	return "Failed credential validation, code is " + uc.getCheckCode();
	    }
	    return "";
  }
}