package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.exception.InvalidUserException;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RequestProcessor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SecureRequestProcessor extends RequestProcessor
  implements CaDSRConstants {
  protected Log log = LogFactory.getLog(SecureRequestProcessor.class.getName());

  public SecureRequestProcessor() {
    super();
  }

  protected ActionForward processActionPerform(
    HttpServletRequest request,
    HttpServletResponse response,
    Action action,
    ActionForm form,
    ActionMapping mapping) throws IOException, ServletException {
    String username = getLoggedInUsername(request);

    if ((username == null) || username.equals("")) {
      //throw new InvalidUserException("Null username");
    }
    else {
      NCIUser currentuser =
        (NCIUser) request.getSession().getAttribute(USER_KEY);

      if (currentuser != null) {
        if (!currentuser.getUsername().equals(username)) {
          ;
        }

        setApplictionUser(username, request);
      }
      else {
        setApplictionUser(username, request);
      }
    }

    return super.processActionPerform(request, response, action, form, mapping);
  }

  protected void setApplictionUser(
    String username,
    HttpServletRequest request) {
    NCIUser newuser = getNCIUser(username);

    if (newuser == null) {
      throw new InvalidUserException("Null User");
    }

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
