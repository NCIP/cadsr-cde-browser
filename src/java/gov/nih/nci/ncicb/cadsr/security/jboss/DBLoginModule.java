package gov.nih.nci.ncicb.cadsr.security.jboss;

import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;
import java.util.Iterator;
import java.util.Set;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.AbstractServerLoginModule;

import java.io.IOException;

import java.security.Principal;
import java.security.acl.Group;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import java.util.Map;


public class DBLoginModule extends AbstractServerLoginModule {
  private Principal identity;
  private char[] credential;
  private AbstractDAOFactory daoFactory = null;
  private UserManagerDAO userManagerDAO = null;
  private ServiceLocator locator = null;

  protected Group[] getRoleSets() throws LoginException {
    /*SimpleGroup grp = new SimpleGroup("Roles");
    Group[] groups = { grp };
    grp.addMember(new SimplePrincipal("CDE MANAGER"));*/
    
    Map rolesAndContexts =
      userManagerDAO.getContextsForAllRoles(
        this.getUsername(), "QUEST_CONTENT");
    Set roles = rolesAndContexts.keySet();
    SimpleGroup grp = new SimpleGroup("Roles");
    Group[] groups = { grp };
    for (Iterator it = roles.iterator(); it.hasNext();) {
      String role = (String)it.next();
      grp.addMember(new SimplePrincipal(role));
      System.out.println("Role: "+role);
    }

    return groups;
  }

  protected Principal getIdentity() {
    return identity;
  }

  public boolean login() throws LoginException {
    System.out.println("in another login");
    
        
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
      if (!authenticateUser(username, password)) {
          throw new FailedLoginException("Incorrect username and password");
      }
    }
    if (getUseFirstPass()) {
      sharedState.put("javax.security.auth.login.name", username);
      sharedState.put("javax.security.auth.login.password", credential);
    }
    super.loginOk = true;

    return true;
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
    System.out.println("Username: "+username);
    System.out.println("Password: "+password);
    return info;
  }
  
  private void setAbstractDAOFactory() {
    daoFactory = AbstractDAOFactory.getDAOFactory(locator);
    
  }

  public void initialize(Subject p0, CallbackHandler p1, Map p2, Map p3) {
    super.initialize(p0, p1, p2, p3);
    for (Iterator it = p3.entrySet().iterator(); it.hasNext();) {
      System.out.println("Option: " +it.next().toString());
    }
    
    
    String serviceLocatorClassName =
      (String) p3.get(ServiceLocator.SERVICE_LOCATOR_CLASS_KEY);
    locator = ServiceLocatorFactory.getLocator(serviceLocatorClassName);
    
    System.out.println("Service Locator class: "+locator);
    
    if (daoFactory == null) {
      setAbstractDAOFactory();
    }
    if (userManagerDAO == null) {
      userManagerDAO = daoFactory.getUserManagerDAO();
    }
    
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
  
}