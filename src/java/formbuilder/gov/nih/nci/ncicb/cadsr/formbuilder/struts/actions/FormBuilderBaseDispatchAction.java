package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.ServiceDelegateFactory;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.ServiceStartupException;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Base DispatchAction for all formbuilder DispatchActions
 */
public class FormBuilderBaseDispatchAction extends DispatchAction
    implements FormConstants, NavigationConstants,
	       PersistenceConstants,FormBuilderConstants,
	       CaDSRConstants {
    protected static Log log = LogFactory.getLog(FormAction.class.getName());

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
     * Gets the ServiceDelegateFactory form the application scope and
     * instantiates a FormBuilderServiceDelegate from the factory
     *
     * @return FormBuilderServiceDelegate
     *
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
     * Initializes the lookupvalues(contexts,categories,workflows into session)
     *
     * @return ActionForward
     *
     * @throws Exception
     */
    protected void setInitLookupValues(HttpServletRequest req) {
	Object obj = getSessionObject(req, ALL_CONTEXTS);

	if (obj == null) {
	    Collection contexts = getFormBuilderService().getAllContexts();
	    setSessionObject(req, ALL_CONTEXTS, contexts);
	}

	obj = getSessionObject(req, ALL_WORKFLOWS);

	if (obj == null) {
	    Collection workflows =
		getFormBuilderService().getStatusesForACType(FORM_ADMIN_COMPONENT_TYPE);
	    setSessionObject(req, ALL_WORKFLOWS, workflows);
	}

	obj = getSessionObject(req, ALL_FORM_CATEGORIES);

	if (obj == null) {
	    Collection categories = getFormBuilderService().getAllFormCategories();
	    setSessionObject(req, ALL_FORM_CATEGORIES, categories);
	}

	obj = getSessionObject(req, ALL_FORM_TYPES);

	if (obj == null) {
	    Collection types = Arrays.asList(FORM_TYPE_VALUES);
	    setSessionObject(req, ALL_FORM_TYPES, types);
	}
    }

    /**
     * If a iconForm(DynaForm) exist then get the FormDetails for the formIdSeq
     * is retrived.
     *
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     */
    protected Form setFormForAction(
				    ActionForm form,
				    HttpServletRequest request) throws FormBuilderException {
	FormBuilderServiceDelegate service = getFormBuilderService();
	DynaActionForm hrefCRFForm = (DynaActionForm) form;
	Form crf = null;

	if (hrefCRFForm != null) {
	    String formIdSeq = (String) hrefCRFForm.get(FORM_ID_SEQ);
	    if ((formIdSeq != null) && (formIdSeq.length() > 0)) {
		crf = service.getFormDetails(formIdSeq);
		setSessionObject(request, CRF, crf);
	    } else {
		crf = (Form)getSessionObject(request, CRF);
	    } // end of else
	    
	}

	return crf;
    }

    protected void saveMessage(
			       String key,
			       HttpServletRequest request) {
	if (key != null) {
	    ActionMessage errorMessage = new ActionMessage(key);
	    ActionMessages messages = new ActionMessages();
	    messages.add(messages.GLOBAL_MESSAGE, errorMessage);
	    saveMessages(request, messages);
	}
    }

    /**
     * This Action forwards to the default formbuilder home.
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
    public ActionForward sendHome(
				  ActionMapping mapping,
				  ActionForm form,
				  HttpServletRequest request,
				  HttpServletResponse response) throws IOException, ServletException {
	setInitLookupValues(request);

	return mapping.findForward(DEFAULT_HOME);
    }


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
	    if (log.isFatalEnabled()) {
		NCIUser user = (NCIUser) getSessionObject(request, USER_KEY);
		if(user!=null)
		    log.fatal(user.getUsername(), throwable);
		else
		    log.fatal( throwable);
	    }     
	    saveMessage(ERROR_FATAL, request);
	    throw new FatalException(throwable);
	}
    }
}
