package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.domain.Question;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.TriggerActionChanges;
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
    String classifyCDEIndicator = (String)dynaForm.get(CLASSIFY_CDE_ON_FORM);
    boolean csCDEIndicator = "true".equalsIgnoreCase(classifyCDEIndicator);
                
    boolean success = true;
    try {
      Form crf = (Form) getSessionObject(request, CRF);
      FormBuilderServiceDelegate service = getFormBuilderService();

      //get form Id and CDE Id if it is to classify CDE as well.

      //get classifications
      List csCsiIdList = new ArrayList();
      for (int i = 0; i < ids.length; i++) {
        String id = ids[i];
        if (id!=null && id.length() > 0){
            csCsiIdList.add(id); 
        }
      }  
      
      //get AC including the form and the CDE if CLASSIFY_CDE_ON_FORM is selected
       List acIdList = new ArrayList();
       acIdList.add(crf.getFormIdseq());
      
      if (csCDEIndicator) {
        List CDEList = crf.getCDEIdList();
        acIdList.addAll(CDEList);
      }
    
      try {
             service.assignFormClassification(acIdList, csCsiIdList);
      }catch (FormBuilderException exp) {
            if (log.isErrorEnabled()) {
              log.error("Exception on addClassification ", exp);
            }

            saveError(exp.getErrorCode(), request);
	    success = false;
       }// end of try-catch

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

    if(success && !csCDEIndicator)
      saveMessage("cadsr.formbuilder.classification.add.success", request);
    if (success && csCDEIndicator){
      saveMessage("cadsr.formbuilder.classification.add.form.and.cde.success", request);
    }

    return mapping.findForward("success");
  }

  public ActionForward removeClassification(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;

    /*String[] ids = (String[]) dynaForm.get(AC_CS_CSI_ID);//ascsiId
    String acCsiId = ids[0];
    */
    String[] cscsiIds = (String[]) dynaForm.get(CS_CSI_ID);
    String cscsiId = cscsiIds[0];

    try {
      Form crf = (Form) getSessionObject(request, CRF);

      //check skip pattern
      List<TriggerActionChanges> triggerChangesList = 
                FormActionUtil.findFormSkipPatternForByClassificiation(crf, cscsiId);
      if (triggerChangesList!=null && !triggerChangesList.isEmpty()){
          setSessionObject(request, CLASSIFICATION_ASSOCIATED_TRIGGERS, triggerChangesList);
          request.setAttribute(FormConstants.REMOVED_CLASSIFICATION_ID, cscsiId);
          return mapping.findForward("hasSkipPattern");          
      }

      FormBuilderServiceDelegate service = getFormBuilderService();
      service.removeFormClassification(cscsiId, crf.getFormIdseq());
      
      Collection classifications =
        service.retrieveFormClassifications(crf.getFormIdseq());

      //make sure there is no left-over triggers.
      removeSessionObject(request, CLASSIFICATION_ASSOCIATED_TRIGGERS);
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
    

    public ActionForward updateSkipPatternForCSI(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
      
      DynaActionForm formBean = (DynaActionForm)form;
      String choice = (String)formBean.get("choice");//TODO: need to update the CRF for display
      String cscsiId = (String)formBean.get(FormConstants.REMOVED_CLASSIFICATION_ID);
      Form crf = (Form)getSessionObject(request, CRF);
      try{
          if ("yes".equalsIgnoreCase(choice)){
              FormBuilderServiceDelegate service = getFormBuilderService();
              List<TriggerActionChanges> triggerChangesList = (List<TriggerActionChanges>)
                        getSessionObject(request,CLASSIFICATION_ASSOCIATED_TRIGGERS);
              service.removeFormClassificationUpdateTriggerActions(cscsiId,  crf.getFormIdseq(), triggerChangesList);
              
              //update CRF in session
              removeSessionObject(request, CRF);
              String formId = crf.getFormIdseq();
              crf = service.getFormDetails(formId);  
              
              Collection classifications = service.retrieveFormClassifications(formId);
              setSessionObject(request, CLASSIFICATIONS, classifications);
              setSessionObject(request, CRF, crf);
          }
          removeSessionObject(request,CLASSIFICATION_ASSOCIATED_TRIGGERS);
      }catch(FormBuilderException e){
          if (log.isErrorEnabled()) {
            log.error("Exception on updateSkipPatternForCSI ", e);
          }
          saveError("cadsr.formbuilder.classification.updateSkipPattern.failure", request);
          removeSessionObject(request,CLASSIFICATION_ASSOCIATED_TRIGGERS);
          return mapping.findForward("failure");
      }
      return mapping.findForward(SUCCESS);
      }
}
