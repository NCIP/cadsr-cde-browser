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

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ManageClassificationsAction
  extends FormBuilderSecureBaseDispatchAction {
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
  public ActionForward getClassifications(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    try {
      DynaActionForm dynaForm = (DynaActionForm) form;

      String formId = (String) dynaForm.get(FORM_ID_SEQ);

      Form crf = (Form) getSessionObject(request, CRF);

      if ((crf == null) || !crf.getFormIdseq().equals(formId)) {
        setFormForAction(form, request);
      }

      FormBuilderServiceDelegate service = getFormBuilderService();

      Collection classifications = service.retrieveFormClassifications(formId);

      setSessionObject(request, CLASSIFICATIONS, classifications);
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on getClassifications ", exp);
      }

      saveError(exp.getErrorCode(), request);
    }

    return mapping.findForward("success");
  }

  public ActionForward gotoAddClassifications(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    return mapping.findForward("success");
  }

  public ActionForward addClassifications(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;

    String[] ids = (String[]) dynaForm.get(CS_CSI_ID);

    try {
      Form crf = (Form) getSessionObject(request, CRF);
      FormBuilderServiceDelegate service = getFormBuilderService();

      for (int i = 0; i < ids.length; i++) {
        String id = ids[i];

        if ((id != null) && (id.length() > 0)) {
          try {
            service.assignFormClassification(crf.getFormIdseq(), id);
          }
          catch (FormBuilderException exp) {
            if (log.isErrorEnabled()) {
              log.error("Exception on addClassification ", exp);
            }

            saveError(exp.getErrorCode(), request);
          }
           // end of try-catch
        }
      }

      Collection classifications =
        service.retrieveFormClassifications(crf.getFormIdseq());

      setSessionObject(request, CLASSIFICATIONS, classifications);
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on addClassification ", exp);
      }

      saveError(exp.getErrorCode(), request);
      saveError("cadsr.formbuilder.classification.add.failure", request);

      return mapping.findForward("failure");
    }

    saveMessage("cadsr.formbuilder.classification.add.success", request);

    return mapping.findForward("success");
  }

  public ActionForward removeClassification(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;

    String[] ids = (String[]) dynaForm.get(CS_CSI_ID);
    String id = ids[0];

    try {
      Form crf = (Form) getSessionObject(request, CRF);

      FormBuilderServiceDelegate service = getFormBuilderService();
      service.removeFormClassification(id);

      Collection classifications =
        service.retrieveFormClassifications(crf.getFormIdseq());

      setSessionObject(request, CLASSIFICATIONS, classifications);
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on removeClassification ", exp);
      }

      saveError(exp.getErrorCode(), request);

      return mapping.findForward("failure");
    }

    saveMessage("cadsr.formbuilder.classification.delete.success", request);

    return mapping.findForward("success");
  }
}
