package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.ServiceDelegateFactory;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.ServiceStartupException;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.action.ActionForward;
import javax.servlet.http.HttpServletResponse;

/**
 * Base DispatchAction for all formbuilder DispatchActions
 * 
 */
public class FormBuilderBaseDispatchAction extends DispatchAction implements FormConstants,NavigationConstants
{

 public static final String DEFAULT_METHOD_NAME= SEND_HOME_METHOD;
 

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
   * Gets the ServiceDelegateFactory form the application scope
   * and instantiates a FormBuilderServiceDelegate from the factory
   * @return FormBuilderServiceDelegate
   * @throws ServiceStartupException
   */
  protected FormBuilderServiceDelegate getFormBuilderService()
    throws ServiceStartupException {
    FormBuilderServiceDelegate svcDelegate = null;
    ServiceDelegateFactory svcFactory =
      (ServiceDelegateFactory) getApplicationObject(
        FormBuilderConstants.SERVICE_DELEGATE_FACTORY_KEY);
    svcDelegate = svcFactory.createService();
    return svcDelegate;
  }
    /**
      * Sets default method name if no method is specified
      * @return ActionForward
      * @throws Exception
     */
  protected ActionForward dispatchMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String name) throws Exception
  {
    if(name==null||name.equals(""))
    {
      name= DEFAULT_METHOD_NAME;
    }
    return super.dispatchMethod(mapping, form, request, response, name);
  }
}