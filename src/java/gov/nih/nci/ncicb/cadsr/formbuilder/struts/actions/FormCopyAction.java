package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
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
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;


public class FormCopyAction extends FormBuilderSecureBaseDispatchAction {
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
    
    String showCached = (String)request.getAttribute("showCached");
    DynaActionForm hrefCRFForm = (DynaActionForm) form;

    String formIdSeq = null;
    if(showCached!=null&&showCached.equalsIgnoreCase(CaDSRConstants.YES))
    {
        Form crf = (Form) getSessionObject(request, CRF);
        formIdSeq = crf.getIdseq();
    }
    else if (hrefCRFForm != null) {
        formIdSeq = (String) hrefCRFForm.get(FORM_ID_SEQ);
    }

    try {
      setFormForAction(form, request);
      DynaActionForm dynaForm = (DynaActionForm) form;
      Form crf = (Form) getSessionObject(request, CRF);
      dynaForm.set(FORM_LONG_NAME, crf.getLongName());
      dynaForm.set(FORM_VERSION, new Float(1.0));
      
      String delimetedProtocolLongNames = crf.getDelimitedProtocolLongNames();
      dynaForm.set(PROTOCOLS_LOV_NAME_FIELD, delimetedProtocolLongNames);
      //dynaForm.set(PROTOCOLS_LOV_ID_FIELD, crf.getProtocol().getProtoIdseq());
      dynaForm.set(WORKFLOW, "DRAFT NEW");

      NCIUser nciUser =
        (NCIUser) getSessionObject(request, CaDSRConstants.USER_KEY);
      Map contexts = nciUser.getContextsByRole();
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on getFormToCopy for form "+form , exp);
      }
      saveError(ERROR_FORM_DOES_NOT_EXIST, request);
      saveError(exp.getErrorCode(), request);

      return mapping.findForward("failure");
    }

    return mapping.findForward("showSuccess");
  }

    public ActionForward gotoManageProtocolsFormCopy(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      setSessionObject(request, "backTo", NavigationConstants.FORM_COPY); 
      return mapping.findForward(SUCCESS);
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
    DynaActionForm dynaForm = (DynaActionForm) form;
    Form newForm = new FormTransferObject();
    Form crf = (Form) getSessionObject(request, CRF);
    
    try {
      newForm.setLongName((String) dynaForm.get(FORM_LONG_NAME));

      // 		newForm.setFormIdseq((String)dynaForm.get(FORM_ID_SEQ));
      newForm.setPreferredDefinition(
        (String) dynaForm.get(PREFERRED_DEFINITION));

      Context newContext = new ContextTransferObject(null);
      newContext.setConteIdseq((String) dynaForm.get(CRF_CONTEXT_ID_SEQ));
      newForm.setContext(newContext);

      /*Protocol newProtocol = new ProtocolTransferObject(null);
      newProtocol.setProtoIdseq((String) dynaForm.get(PROTOCOLS_LOV_ID_FIELD));
      newForm.setProtocol(newProtocol);
      */
      //TODO - Firm Copy - should copy the protocols Id over....?
      
      newForm.setFormCategory((String) dynaForm.get(FORM_CATEGORY));
      newForm.setFormType((String) dynaForm.get(FORM_TYPE));

      // 	newForm.setAslName((String)dynaForm.get(WORKFLOW));
      newForm.setAslName("DRAFT NEW");

      newForm.setVersion((Float) dynaForm.get(FORM_VERSION));

      newForm.setCreatedBy(request.getRemoteUser());
      newForm.setProtocols(crf.getProtocols());

      FormBuilderServiceDelegate service = getFormBuilderService();
      newForm = service.copyForm(crf.getFormIdseq(), newForm);

      // 		newForm = service.getFormDetails(newFormPK);
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on copying Form  " + crf, exp);
      }
      saveError(exp.getErrorCode(), request);

      return mapping.findForward("failure");
    }

    setSessionObject(request, CRF, newForm);

    
    request.setAttribute("showCached", CaDSRConstants.YES);
    
    saveMessage("cadsr.formbuilder.form.copy.success", request);

    if (dynaForm.get(FORM_GOTO_EDIT) == null) {
      return mapping.findForward("gotoView");
    }
    else if (((Boolean) dynaForm.get(FORM_GOTO_EDIT)).booleanValue()) {
      return mapping.findForward("gotoEdit");
    }
    else {
      return mapping.findForward("gotoView");
    }
     // end of else
  }
  
  
}