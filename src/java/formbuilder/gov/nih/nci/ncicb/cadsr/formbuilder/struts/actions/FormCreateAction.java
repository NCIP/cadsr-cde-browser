package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Context;
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

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormInstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormInstruction;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;

public class FormCreateAction extends FormBuilderSecureBaseDispatchAction {

  /**
   * Prepares the Create Form page
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
  public ActionForward goToCreateForm(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    DynaActionForm dynaForm = (DynaActionForm)form;
    return mapping.findForward("toCreateForm");

  }

  /**
   * Create a form
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
  public ActionForward createForm(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    DynaActionForm dynaForm = (DynaActionForm)form;
    Form newForm = null;
    FormInstruction newFormHdrInst = null;
    FormInstruction newFormFtrInst = null;

    // assemble a new form information.
    /*
    // all required fields.
    if (!(StringUtils.doesValueExist((String)dynaForm.get(FORM_LONG_NAME))&&
          StringUtils.doesValueExist((String)dynaForm.get(PREFERRED_DEFINITION))&&
          StringUtils.doesValueExist((String)dynaForm.get(FORM_TYPE))&&
          StringUtils.doesValueExist((String)dynaForm.get(CONTEXT_ID_SEQ)))) {
      //System.out.println("******************** in null check");
      return mapping.findForward("toSearch");
    }
    */
    newForm = new FormTransferObject();
    newForm.setLongName((String)dynaForm.get(FORM_LONG_NAME));
    newForm.setPreferredDefinition((String)dynaForm.get(PREFERRED_DEFINITION));

    Context context = new ContextTransferObject();
    context.setConteIdseq((String)dynaForm.get(CONTEXT_ID_SEQ));
    newForm.setContext(context);

    Protocol protocol =
      new ProtocolTransferObject((String)dynaForm.get(PROTOCOLS_LOV_NAME_FIELD));
    protocol.setProtoIdseq((String)dynaForm.get(PROTOCOLS_LOV_ID_FIELD));
    newForm.setProtocol(protocol);

    newForm.setFormType((String)dynaForm.get(FORM_TYPE));
    newForm.setFormCategory((String)dynaForm.get(FORM_CATEGORY));
    newForm.setAslName("DRAFT NEW");
    newForm.setVersion((Float)dynaForm.get(FORM_VERSION));
    newForm.setCreatedBy(request.getRemoteUser());

    // assemble a new form instruction for having form header.
    int dispOrder = 0;
    if (StringUtils.doesValueExist((String)dynaForm.get(FORM_HEADER))){
      newFormHdrInst = new FormInstructionTransferObject();
      //newFormHdrInst.setForm(newForm); // form's qc_idseq missing
      newFormHdrInst.setLongName((String)dynaForm.get(FORM_HEADER));
      newFormHdrInst.setPreferredDefinition((String)dynaForm.get(PREFERRED_DEFINITION));
      newFormHdrInst.setConteIdseq(context.getConteIdseq());
      newFormHdrInst.setAslName("DRAFT NEW");
      newFormHdrInst.setVersion(new Float(1.0));
      newFormHdrInst.setCreatedBy(request.getRemoteUser());
      newFormHdrInst.setDisplayOrder(++dispOrder);
    }

    if (StringUtils.doesValueExist((String)dynaForm.get(FORM_FOOTER))){
      newFormFtrInst = new FormInstructionTransferObject();
      //newFormFtrInst.setForm(newForm); // form's qc_idseq missing
      newFormFtrInst.setLongName((String)dynaForm.get(FORM_FOOTER));
      newFormFtrInst.setPreferredDefinition((String)dynaForm.get(PREFERRED_DEFINITION));
      newFormFtrInst.setConteIdseq(context.getConteIdseq());
      newFormFtrInst.setAslName("DRAFT NEW");
      newFormFtrInst.setVersion(new Float(1.0));
      newFormFtrInst.setCreatedBy(request.getRemoteUser());
      newFormFtrInst.setDisplayOrder(++dispOrder);
    }

    Form createdForm = null;
    try {
      FormBuilderServiceDelegate service = getFormBuilderService();
      createdForm = service.createForm(newForm, newFormHdrInst, newFormFtrInst);
    } catch (FormBuilderException exp) {
        if (log.isDebugEnabled()) {
          log.error("Exception on creating Form and its header and footer " +
            "instructions =  " + exp);
        }
	saveError(exp.getErrorCode(), request);
	return mapping.findForward("failure");
	  
    }

    setSessionObject(request, CRF, createdForm);
    request.setAttribute(FORM_ID_SEQ, createdForm.getFormIdseq());

    saveMessage("cadsr.formbuilder.form.create.success", request);

    return mapping.findForward("gotoEdit");

  }

}

