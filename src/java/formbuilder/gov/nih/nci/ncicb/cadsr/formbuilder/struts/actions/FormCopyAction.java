package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;

public class FormCopyAction extends FormBuilderBaseDispatchAction {


    /**
     * Returns Complete form given an Id for Copy.
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
    public ActionForward getFormToCopy(
				       ActionMapping mapping,
				       ActionForm form,
				       HttpServletRequest request,
				       HttpServletResponse response) throws IOException, ServletException {
	try
	    {
		setFormForAction(form,request);
		DynaActionForm dynaForm = (DynaActionForm)form;
		Form crf = (Form)getSessionObject(request, CRF);
		dynaForm.set(FORM_LONG_NAME, crf.getLongName());
		dynaForm.set(FORM_VERSION, new Float(1.0));
		dynaForm.set(PROTOCOLS_LOV_NAME_FIELD, crf.getProtocol().getLongName());
		dynaForm.set(PROTOCOLS_LOV_ID_FIELD, crf.getProtocol().getProtoIdseq());
		dynaForm.set(WORKFLOW, "DRAFT NEW");

		NCIUser nciUser = (NCIUser)getSessionObject(request, CaDSRConstants.USER_KEY);
		Map contexts = nciUser.getContextsByRole(); 
		

	    }
	catch (FormBuilderException exp)
	    {
		if (log.isDebugEnabled()) {
		    log.debug("Exception on getFormToEdit =  " + exp);
		}      
	    }
	return mapping.findForward("showSuccess");
    }

    /**
     * Copies form
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
    public ActionForward formCopy(
				  ActionMapping mapping,
				  ActionForm form,
				  HttpServletRequest request,
				  HttpServletResponse response) throws IOException, ServletException {

	DynaActionForm dynaForm = (DynaActionForm)form;
	Form newForm = new FormTransferObject();

	try
	    {
		newForm.setLongName((String)dynaForm.get(FORM_LONG_NAME));
// 		newForm.setFormIdseq((String)dynaForm.get(FORM_ID_SEQ));
		newForm.setPreferredDefinition((String)dynaForm.get(PREFERRED_DEFINITION));

		Context newContext = new ContextTransferObject(null);
		newContext.setConteIdseq((String)dynaForm.get(CRF_CONTEXT_ID_SEQ));
		newForm.setContext(newContext);

		Protocol newProtocol = new ProtocolTransferObject(null);
		newProtocol.setProtoIdseq((String)dynaForm.get(PROTOCOLS_LOV_ID_FIELD));
		newForm.setProtocol(newProtocol);

		newForm.setFormCategory((String)dynaForm.get(FORM_CATEGORY));
		newForm.setFormType((String)dynaForm.get(FORM_TYPE));

 		newForm.setAslName((String)dynaForm.get(WORKFLOW));
      
		newForm.setVersion((Float)dynaForm.get(FORM_VERSION));

		newForm.setCreatedBy(request.getRemoteUser());

		Form crf = (Form)getSessionObject(request, CRF);
      
		FormBuilderServiceDelegate service = getFormBuilderService();
		newForm = service.copyForm(crf.getFormIdseq(), newForm);
// 		newForm = service.getFormDetails(newFormPK);

	    }
	catch (FormBuilderException exp)
	    {
		if (log.isDebugEnabled()) {
		    log.debug("Exception on copyForm =  " + exp);
		}      
	    }

	setSessionObject(request, CRF, newForm);  
	request.setAttribute(FORM_ID_SEQ, newForm.getFormIdseq());
	if (dynaForm.get(FORM_GOTO_EDIT) == null) {
	    return mapping.findForward("gotoView");
	} else if (((Boolean)dynaForm.get(FORM_GOTO_EDIT)).booleanValue()) {
	    return mapping.findForward("gotoEdit");
	} else {
	    return mapping.findForward("gotoView");
	} // end of else
    }

}
  
