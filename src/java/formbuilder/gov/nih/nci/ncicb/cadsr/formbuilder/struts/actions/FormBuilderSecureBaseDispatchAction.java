package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.exception.InvalidUserException;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.ServiceDelegateFactory;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.ServiceStartupException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.*;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.util.SessionUtils;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;


/**
 * Base DispatchAction for all formbuilder DispatchActions
 */
public class FormBuilderSecureBaseDispatchAction extends FormBuilderBaseDispatchAction
  {
  protected static Log log = LogFactory.getLog(FormAction.class.getName());

  /**
   * Sets default method name if no method is specified
   *
   * @return ActionForward
   *
   * @throws Exception
   */
  protected ActionForward dispatchMethod(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    String name) throws Exception {
    
    try {
      String username = getLoggedInUsername(request);
      if ((username == null) || username.equals("")) {
        throw new InvalidUserException("Null username");
      }
      else {
        NCIUser currentuser =
          (NCIUser) request.getSession().getAttribute(USER_KEY);
  
        if (currentuser != null) {
          if (!currentuser.getUsername().equalsIgnoreCase(username)) {
            throw new InvalidUserException("Invalid user state");
          }
        }
        else {
          setApplictionUser(username, request);
        }
      }
      return super.dispatchMethod(mapping, form, request, response, name);
    }
    catch(InvalidUserException userExp)
    {
      request.getSession().invalidate();
      throw userExp;
    }
    catch (Throwable throwable) {
      if (log.isFatalEnabled()) {
        NCIUser user = (NCIUser) getSessionObject(request, USER_KEY);

        if (user != null) {
          log.fatal(user.getUsername(), throwable);
        }
        else
        {
          log.fatal(throwable);
        }
      }
      saveError(ERROR_FATAL, request);
      throw new FatalException(throwable);      
    }
  }
  protected void setApplictionUser(
    String username,
    HttpServletRequest request) {
    NCIUser newuser = getNCIUser(username);
    request.getSession().setAttribute(this.USER_KEY, newuser);
  }

  protected NCIUser getNCIUser(String username) {
    String locatorClassName =
      servlet.getInitParameter(ServiceLocator.SERVICE_LOCATOR_CLASS_KEY);
    ServiceLocator locator = ServiceLocatorFactory.getLocator(locatorClassName);
    AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
    UserManagerDAO dao = daoFactory.getUserManagerDAO();
    NCIUser user = dao.getNCIUser(username);

    if (log.isInfoEnabled()) {
      log.info("getNCIUser()=" + user);
    }

    return user;
  }

  protected String getLoggedInUsername(HttpServletRequest request) {
    return request.getRemoteUser();
  }  
}