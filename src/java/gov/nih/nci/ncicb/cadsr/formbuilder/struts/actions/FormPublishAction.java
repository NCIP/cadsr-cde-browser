package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.util.ContextUtils;
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


public class FormPublishAction extends FormBuilderSecureBaseDispatchAction {
  /**
   * Publish form.
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
  public ActionForward publishForm(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    

    try {

      Form aForm = (Form) getSessionObject(request, CRF);
      FormBuilderServiceDelegate service = getFormBuilderService();
      Collection contexts = (Collection)getSessionObject(request, ALL_CONTEXTS);

      Context currContext = ContextUtils.getContextByName(contexts,CONTEXT_CABIG);       
      service.publishForm(aForm.getIdseq(),aForm.getFormType(),currContext.getConteIdseq());
      setSessionObject(request,TREE_REFRESH_INDICATOR,YES,true);
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception while publishing the form "+form , exp);
      }
      saveError(ERROR_FORM_PUBLISH, request);

      return mapping.findForward(SUCCESS);
    }
    saveMessage("cadsr.formbuilder.form.publish.success", request);
    return mapping.findForward(SUCCESS);
  }

  /**
   * unpublish form.
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
  public ActionForward unpublishForm(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    

    try {

      Form aForm = (Form) getSessionObject(request, CRF);
      FormBuilderServiceDelegate service = getFormBuilderService();
      Collection contexts = (Collection)getSessionObject(request, ALL_CONTEXTS);

      Context currContext = ContextUtils.getContextByName(contexts,CONTEXT_CABIG);      
      service.unpublishForm(aForm.getIdseq(),aForm.getFormType(),currContext.getConteIdseq());
      setSessionObject(request,TREE_REFRESH_INDICATOR,YES,true);
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception while unpublishing the form "+form , exp);
      }
      saveError(ERROR_FORM_UNPUBLISH, request);

      return mapping.findForward(FAILURE);
    }
    saveMessage("cadsr.formbuilder.form.unpublish.success", request);
    return mapping.findForward(SUCCESS);
  }

}
