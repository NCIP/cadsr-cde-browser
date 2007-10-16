package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

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
    Instruction newFormHdrInst = null;
    Instruction newFormFtrInst = null;

    // assemble a new form information.
    /*
    // all required fields.
    if (!(StringUtils.doesValueExist((String)dynaForm.get(FORM_LONG_NAME))&&
          StringUtils.doesValueExist((String)dynaForm.get(PREFERRED_DEFINITION))&&
          StringUtils.doesValueExist((String)dynaForm.get(FORM_TYPE))&&
          StringUtils.doesValueExist((String)dynaForm.get(CONTEXT_ID_SEQ)))) {
      return mapping.findForward("toSearch");
    }
    */
    newForm = new FormTransferObject();
    newForm.setLongName((String)dynaForm.get(FORM_LONG_NAME));
    newForm.setPreferredDefinition((String)dynaForm.get(PREFERRED_DEFINITION));

    Context context = new ContextTransferObject();
    context.setConteIdseq((String)dynaForm.get(CONTEXT_ID_SEQ));
    newForm.setContext(context);

    String protocolId = (String)dynaForm.get(PROTOCOLS_LOV_NAME_FIELD);
    if (protocolId.length()>0){
        Protocol protocol =
          new ProtocolTransferObject((String)dynaForm.get(PROTOCOLS_LOV_NAME_FIELD));
        protocol.setProtoIdseq((String)dynaForm.get(PROTOCOLS_LOV_ID_FIELD));
        
        List protocols = new ArrayList();
        protocols.add(protocol);    
        newForm.setProtocols(protocols);
    }
    
    newForm.setFormType((String)dynaForm.get(FORM_TYPE));
    newForm.setFormCategory((String)dynaForm.get(FORM_CATEGORY));
    newForm.setAslName("DRAFT NEW");
    newForm.setVersion((Float)dynaForm.get(FORM_VERSION));
    newForm.setCreatedBy(request.getRemoteUser());

    // assemble a new form instruction for having form header.
    int dispOrder = 0;
    String headerInstrStr = (String)dynaForm.get(FORM_HEADER_INSTRUCTION);
    if (StringUtils.doesValueExist(headerInstrStr)){
      newFormHdrInst = new InstructionTransferObject();
      newFormHdrInst.setLongName(headerInstrStr);
      newFormHdrInst.setPreferredDefinition(headerInstrStr);
      newFormHdrInst.setContext(context);
      newFormHdrInst.setAslName("DRAFT NEW");
      newFormHdrInst.setVersion(new Float(1.0));
      newFormHdrInst.setCreatedBy(request.getRemoteUser());
      newFormHdrInst.setDisplayOrder(++dispOrder);
    }
    String footerInstrStr = (String)dynaForm.get(FORM_FOOTER_INSTRUCTION);
    if (StringUtils.doesValueExist(footerInstrStr)){
      newFormFtrInst = new InstructionTransferObject();
      newFormFtrInst.setLongName(footerInstrStr);
      newFormFtrInst.setPreferredDefinition(footerInstrStr);
      newFormFtrInst.setContext(context);
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
        if (log.isErrorEnabled()) {
          log.error("Exception on creating Form  "+newForm , exp);
        }
  saveError(ERROR_FORM_CREATE, request);
	saveError(exp.getErrorCode(), request);
	return mapping.findForward("failure");
	  
    }

    setSessionObject(request, CRF, createdForm);
    request.setAttribute("showCached", CaDSRConstants.YES);

    saveMessage("cadsr.formbuilder.form.create.success", request);

    return mapping.findForward("gotoEdit");

  }

}

