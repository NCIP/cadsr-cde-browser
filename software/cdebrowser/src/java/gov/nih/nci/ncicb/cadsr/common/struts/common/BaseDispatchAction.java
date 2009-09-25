package gov.nih.nci.ncicb.cadsr.common.struts.common;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.CommonNavigationConstants;
import gov.nih.nci.ncicb.cadsr.common.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.common.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ApplicationServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;


/**
 * Base DispatchAction for all  DispatchActions
 */
abstract public class BaseDispatchAction extends DispatchAction
  implements PersistenceConstants, CaDSRConstants,CommonNavigationConstants {
  protected static Log log = LogFactory.getLog(BaseDispatchAction.class.getName());
  private ApplicationServiceLocator appServiceLocator = null;
  /**
   * Retrieve an object from the application scope by its name. This is a
   * convience method.
   */
  protected Object getApplicationObject(String attrName) {
    return servlet.getServletContext().getAttribute(attrName);
  }

  /**
   * Retrieve a session object based on the request and the attribute name.
   */
  protected Object getSessionObject(
    HttpServletRequest req,
    String attrName) {
    Object sessionObj = null;
    HttpSession session = req.getSession(false);

    if (session != null) {
      sessionObj = session.getAttribute(attrName);
    }

    return sessionObj;
  }

  /**
   * Remove a session object based on the request and the attribute name.
   */
  protected void removeSessionObject(
    HttpServletRequest req,
    String attrName) {
    HttpSession session = req.getSession(false);

    if (session != null) {
      session.removeAttribute(attrName);
    }
  }

  /**
   * Add an object to session based on the request and the attribute name.
   */
  protected void setSessionObject(
    HttpServletRequest req,
    String attrName,
    Object sessionObject) {
    HttpSession session = req.getSession(false);

    if (session != null) {
      session.setAttribute(attrName, sessionObject);
    }
  }

  /**
   * Add an object to session based on the request and the attribute name.
   * Reset praram label the attribute to be removed when logged out or system
   * error
   */
  protected void setSessionObject(
    HttpServletRequest req,
    String attrName,
    Object sessionObject,
    boolean clear) {
    HttpSession session = req.getSession(false);

    if (session != null) {
      session.setAttribute(attrName, sessionObject);

      if (clear) {
        setObjectsForClear(session,attrName);
      }
      else {
        setSessionObject(req, attrName, sessionObject);
      }
    }
  }




  protected void saveMessage(
    String key,
    HttpServletRequest request) {
    if (key != null) {
      ActionMessage message = new ActionMessage(key);

      ActionMessages messages = null;
      messages = (ActionMessages)request.getAttribute(Globals.MESSAGE_KEY);
      if(messages==null)
        messages = new ActionMessages();

      messages.add(messages.GLOBAL_MESSAGE, message);
      saveMessages(request, messages);
    }
  }

  /**
   * This Action forwards to the default  home.
   *
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   *
   * @return
   *
   * @throws IOException
   * @throws ServletException
   */
   public ActionForward sendHome;

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
    if ((name == null) || name.equals("")) {
      name = DEFAULT_METHOD;
    }

    try {
      return super.dispatchMethod(mapping, form, request, response, name);
    }
    catch (Throwable throwable) {
      HttpSession session = request.getSession();
      String userName = request.getRemoteUser();
      if(userName==null)
        userName="";
      Collection keys = (Collection)session.getAttribute(this.CLEAR_SESSION_KEYS);
      if(keys!=null)
      {
        Iterator it  = keys.iterator();
        while(it.hasNext())
        {
          session.removeAttribute((String)it.next());
        }
      }
      if(log.isFatalEnabled())
      {
        log.fatal(userName+": Exception in dispatchMethod in method "+name,throwable);
      }
      saveMessage(ERROR_FATAL, request);
      throw new FatalException(throwable);
    }
  }
  /**
   * Used by the action to check if a input form field has a value
   * @param str
   * @return
   */
  protected boolean hasValue(String str)
  {
    if(str==null)
      return false;
    if(str.equals(""))
      return false;
    return true;
  }
  
  /**
   *
   * @return ApplicationServiceLocator
   *
   * @throws ServiceStartupException
   */
  protected ApplicationServiceLocator getApplicationServiceLocator()
    throws ServiceLocatorException {
    if(appServiceLocator==null)
    appServiceLocator =
      (ApplicationServiceLocator) getApplicationObject(
        ApplicationServiceLocator.APPLICATION_SERVICE_LOCATOR_CLASS_KEY);
    if(appServiceLocator==null)
      throw new ServiceLocatorException("Could no find ApplicationServiceLocator with key ="+ ApplicationServiceLocator.APPLICATION_SERVICE_LOCATOR_CLASS_KEY);
    return appServiceLocator;
  }  
  
  private void setObjectsForClear(HttpSession session, String attrName)
  {
        Collection keys =
          (Collection) session.getAttribute(
            CLEAR_SESSION_KEYS);

        if (keys == null) {
          keys = new ArrayList();
        }

        keys.add(attrName);
        session.setAttribute(CLEAR_SESSION_KEYS, keys);

  }
}
