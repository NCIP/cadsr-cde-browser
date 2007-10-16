package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.util.StringPropertyComparator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
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
import gov.nih.nci.ncicb.cadsr.dto.TriggerActionChangesTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.TriggerActionTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormJspUtil;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.FormElement;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;

import gov.nih.nci.ncicb.cadsr.resource.TriggerActionChanges;

import java.util.ArrayList;
import java.util.Arrays;


public class SkipPatternAction extends FormBuilderSecureBaseDispatchAction {



  /**
   * Clear the cache for a new search.
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
  public ActionForward editSkipPattern(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    return mapping.findForward("editSkipPattern");
  }

  /**
   * Skip From a form
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
  public ActionForward createFormSkipPattern(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {


    DynaActionForm searchForm = (DynaActionForm) form;
    FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;
    formBean.clear();
    //Add the new Skip Source
    Form sourceForm = (Form) getSessionObject(request,CRF);
    Form formClone = null;
    /**
      try
      {
           formClone = (Form)sourceForm.clone();
           formClone.setModules(null);
      }
        catch (CloneNotSupportedException exp) {
          saveError(ERROR_FORM_SAVE_FAILED, request);
          if (log.isErrorEnabled()) {
            log.error("On save, Exception on cloneing Form " + exp);
          }
          return mapping.findForward(FAILURE);
        }
        **/
      TriggerAction triggerAction = new TriggerActionTransferObject();
      triggerAction.setActionSource(sourceForm);

      setSessionObject(request,SKIP_PATTERN,triggerAction);
    return mapping.findForward("createSkipPattern");
  }

    /**
     * Skip From a Module
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
    public ActionForward createModuleSkipPattern(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {


      DynaActionForm searchForm = (DynaActionForm) form;
      FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;

      //Add the new Skip Source
      Module sourceModule = (Module) getSessionObject(request,MODULE);
       Form sourceForm = (Form) getSessionObject(request,CRF);
       Form formClone = null;
       Module moduleClone = null;

        sourceModule.setForm(sourceForm);
        TriggerAction triggerAction = new TriggerActionTransferObject();
        triggerAction.setActionSource(sourceModule);
        setSessionObject(request,SKIP_PATTERN,triggerAction);

        formBean.set(SELECTED_SKIP_PROTOCOL_IDS,null);
        formBean.set(SELECTED_SKIP_AC_CSIS,null);
        formBean.set(SKIP_INSTRUCTION,"");

         try {
           FormBuilderServiceDelegate service = getFormBuilderService();
           Collection  csis = service.retrieveFormClassifications(sourceForm.getFormIdseq());
           sourceForm.setClassifications(csis);
         } catch (FormBuilderException exp) {
             if (log.isErrorEnabled()) {
               log.error("Exception while retriveing Classifications for Skip pattern  " , exp);
             }

         }

      return mapping.findForward("createSkipPattern");
    }
    /**
     * Edit Skip From a Module
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
    public ActionForward editModuleSkipPattern(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {


      DynaActionForm searchForm = (DynaActionForm) form;
      String triggerIndexStr = (String)searchForm.get(TRIGGER_ACTION_INDEX);
      int triggerIndex= Integer.parseInt(triggerIndexStr);
      FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;


      Module sourceModule = (Module) getSessionObject(request,MODULE);

      TriggerAction triggerAction = sourceModule.getTriggerActions().get(triggerIndex);
      TriggerAction clone = null;
      try
      {
          clone = (TriggerAction)triggerAction.clone();
      }
      catch (CloneNotSupportedException e)
      {
          if (log.isErrorEnabled()) {
            log.error("Exception while colneing trigger action");
          }
          saveError(ERROR_SKIP_PATTERN_EDIT,request);
          return mapping.findForward("editSkipPattern");

      }
      setSessionObject(request,SKIP_PATTERN,triggerAction);
      setSessionObject(request,SKIP_PATTERN_CLONE,clone);
      formBean.set(SELECTED_SKIP_PROTOCOL_IDS,getProtocolIds(triggerAction.getProtocols()));
      formBean.set(SELECTED_SKIP_AC_CSIS,getAcCsiIds(triggerAction.getClassSchemeItems()));
      formBean.set(SKIP_INSTRUCTION,triggerAction.getInstruction());

       Form sourceForm = (Form) getSessionObject(request,CRF);
        try {
          FormBuilderServiceDelegate service = getFormBuilderService();
          Collection  csis = service.retrieveFormClassifications(sourceForm.getFormIdseq());
          sourceForm.setClassifications(csis);
        } catch (FormBuilderException exp) {
            if (log.isErrorEnabled()) {
              log.error("Exception while retriveing Classifications for Skip pattern  " , exp);
            }
            saveError(ERROR_SKIP_PATTERN_EDIT,request);
            return mapping.findForward("editSkipPattern");
        }

      return mapping.findForward("editSkipPattern");
    }
    /**
     * Skip From a Module
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
    public ActionForward createValidValueSkipPattern(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {


      DynaActionForm searchForm = (DynaActionForm) form;
      FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;
      String questionIndexStr = (String)formBean.get(SK_QUESTION_INDEX);
      String validvalueIndexStr = (String)formBean.get(SK_VALID_VALUE_INDEX);
      int qIndex= Integer.parseInt(questionIndexStr);
      int vvIndex= Integer.parseInt(validvalueIndexStr);

      //Add the new Skip Source
       Module sourceModule = (Module) getSessionObject(request,MODULE);
        Form sourceForm = (Form) getSessionObject(request,CRF);
        Question question = (Question)sourceModule.getQuestions().get(qIndex);
        FormValidValue vv = (FormValidValue)question.getValidValues().get(vvIndex);

        vv.setQuestion(question);
        question.setModule(sourceModule);
        sourceModule.setForm(sourceForm);


         TriggerAction triggerAction = new TriggerActionTransferObject();
         triggerAction.setActionSource(vv);
         setSessionObject(request,SKIP_PATTERN,triggerAction);
        formBean.set(SELECTED_SKIP_PROTOCOL_IDS,null);
        formBean.set(SELECTED_SKIP_AC_CSIS,null);
        formBean.set(SKIP_INSTRUCTION,"");

        try {
          FormBuilderServiceDelegate service = getFormBuilderService();
          Collection  csis = service.retrieveFormClassifications(sourceForm.getFormIdseq());
          sourceForm.setClassifications(csis);
        } catch (FormBuilderException exp) {
            if (log.isErrorEnabled()) {
              log.error("Exception while retriveing Classifications for Skip pattern  " , exp);
            }

        }

      return mapping.findForward("createSkipPattern");
    }

    /**
     * Skip From a Module
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
    public ActionForward editValidValueSkipPattern(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {


      DynaActionForm searchForm = (DynaActionForm) form;
      FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;
      String triggerIndexStr = (String)searchForm.get(TRIGGER_ACTION_INDEX);
      int triggerIndex= Integer.parseInt(triggerIndexStr);

      String questionIndexStr = (String)formBean.get(SK_QUESTION_INDEX);
      String validvalueIndexStr = (String)formBean.get(SK_VALID_VALUE_INDEX);
      int qIndex= Integer.parseInt(questionIndexStr);
      int vvIndex= Integer.parseInt(validvalueIndexStr);


       Module sourceModule = (Module) getSessionObject(request,MODULE);
        Form sourceForm = (Form) getSessionObject(request,CRF);
        Question question = (Question)sourceModule.getQuestions().get(qIndex);
        FormValidValue vv = (FormValidValue)question.getValidValues().get(vvIndex);
        TriggerAction triggerAction = vv.getTriggerActions().get(triggerIndex);

        setSessionObject(request,SKIP_PATTERN,triggerAction);
        formBean.set(SELECTED_SKIP_PROTOCOL_IDS,getProtocolIds(triggerAction.getProtocols()));
        formBean.set(SELECTED_SKIP_AC_CSIS,getAcCsiIds(triggerAction.getClassSchemeItems()));
        formBean.set(SKIP_INSTRUCTION,triggerAction.getInstruction());

        TriggerAction clone = null;
        try
        {
            clone = (TriggerAction)triggerAction.clone();
        }
        catch (CloneNotSupportedException e)
        {
            if (log.isErrorEnabled()) {
              log.error("Exception while colneing trigger action");
            }
            saveError(ERROR_SKIP_PATTERN_EDIT,request);
            return mapping.findForward("editSkipPattern");

        }
        setSessionObject(request,SKIP_PATTERN_CLONE,clone);

        try {
          FormBuilderServiceDelegate service = getFormBuilderService();
          Collection  csis = service.retrieveFormClassifications(sourceForm.getFormIdseq());
          sourceForm.setClassifications(csis);
        } catch (FormBuilderException exp) {
            if (log.isErrorEnabled()) {
              log.error("Exception while retriveing Classifications for Skip pattern  " , exp);
            }
            saveError(ERROR_SKIP_PATTERN_EDIT,request);
        }

      return mapping.findForward("editSkipPattern");
    }

  /**
   *  Skip to Location on the current form
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
  public ActionForward setCurrentFormAsTargetForm(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

      Form crf = (Form)getSessionObject(request,CRF);
      setSessionObject(request,SKIP_TARGET_FORM,crf,true);

      //Set the form as skip source

      return mapping.findForward("skipToFormLocation");
  }

    /**
     *  Skip to Location on the selected form
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
    public ActionForward setSelectedFormAsTargetForm(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm searchForm = (DynaActionForm) form;
      FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;

       String formIdSeq = (String) formBean.get(FORM_ID_SEQ);
        Form crf = null;
        try {
            crf = getFormBuilderService().getFormDetails(formIdSeq);
            setSessionObject(request,SKIP_TARGET_FORM,crf,true);
        }
        catch (FormBuilderException exp) {
          if (log.isErrorEnabled()) {
            log.error("Exception getting CRF", exp);
          }
          saveError(ERROR_FORM_RETRIEVE, request);
          saveError(ERROR_FORM_DOES_NOT_EXIST, request);
          return mapping.findForward(FAILURE);
        }
        //Set the form as skip source

        return mapping.findForward("skipToFormLocation");
    }

    /**
     * Set SKIP_TARGET_FORM as the skip target
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
    public ActionForward setFormAsTarget(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {



      TriggerAction triggerAction = (TriggerAction)getSessionObject(request,SKIP_PATTERN);
      Form targetForm = (Form)getSessionObject(request,SKIP_TARGET_FORM);
      triggerAction.setActionTarget(targetForm);

      return mapping.findForward("editSkipPattern");
    }

    /**
     * Set SKIP_TARGET_FORM.Module as the skip target
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
    public ActionForward setModuleAsTarget(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
      DynaActionForm searchForm = (DynaActionForm) form;
      FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;
      //String indexStr = (String)formBean.get(MODULE_INDEX);
      //MODULE_INDEX is the source module index.
      String indexStr = (String)request.getParameter(TARGET_MODULE_INDEX);
      int index= Integer.parseInt(indexStr);
      TriggerAction triggerAction = (TriggerAction)getSessionObject(request,SKIP_PATTERN);
      Form targetForm = (Form)getSessionObject(request,SKIP_TARGET_FORM);
      List moules = targetForm.getModules();
      Module targetModule = (Module)moules.get(index);

      targetModule.setForm(targetForm);
      triggerAction.setActionTarget(targetModule);

      return mapping.findForward("editSkipPattern");
    }

    /**
     * Set SKIP_TARGET_FORM.Module.question as the skip target
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
    public ActionForward setQuestionAsTarget(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
      DynaActionForm searchForm = (DynaActionForm) form;
      FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;
      //String modIndexStr = (String)formBean.get(TARGET_MODULE_INDEX);
       //String modIndexStr = (String)formBean.get(MODULE_INDEX);

      String modIndexStr = (String)request.getParameter(TARGET_MODULE_INDEX);
      String questionIndexStr = (String)formBean.get(SK_QUESTION_INDEX);
      int modIndex= Integer.parseInt(modIndexStr);
      int questionIndex= Integer.parseInt(questionIndexStr);

      TriggerAction triggerAction = (TriggerAction)getSessionObject(request,SKIP_PATTERN);
      Form targetForm = (Form)getSessionObject(request,SKIP_TARGET_FORM);
      List moules = targetForm.getModules();
      Module targetModule = (Module)moules.get(modIndex);
      Question targetQuestion = (Question)targetModule.getQuestions().get(questionIndex);

      targetQuestion.setModule(targetModule);
      triggerAction.setActionTarget(targetQuestion);

      return mapping.findForward("editSkipPattern");
    }

  /**
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
  public ActionForward skipToFormSearch(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    return mapping.findForward("framedSearchResultsPage");
  }

    /**
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
    public ActionForward cancelModuleSave(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      return mapping.findForward("backToModuleEdit");
    }


    /**
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
    public ActionForward cancelSkipEdit(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

        removeSessionObject(request,SKIP_PATTERN);
        removeSessionObject(request,SKIP_PATTERN_CLONE);
        removeSessionObject(request,SKIP_TARGET_FORM);
      return mapping.findForward("backToModuleEdit");
    }

    /**
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
    public ActionForward confirmModuleSkipPatternDelete(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      return mapping.findForward("confirmDeleteModuleSkip");
    }

    /**
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
    public ActionForward confirmValidValueSkipPatternDelete(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      return mapping.findForward("confirmDeleteValidValueSkip");
    }
    /**
     *  Delete a  Skip pattern for a Module
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
    public ActionForward deleteModuleSkipPattern(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
        DynaActionForm formBean = (DynaActionForm) form;
        String triggerIndexStr = (String)formBean.get(TRIGGER_ACTION_INDEX);
        int triggerIndex= Integer.parseInt(triggerIndexStr);
        Module sourceModule = (Module) getSessionObject(request,MODULE);

        TriggerAction triggerAction =  sourceModule.getTriggerActions().remove(triggerIndex);
        try {
          FormBuilderServiceDelegate service = getFormBuilderService();
          service.deleteTriggerAction(triggerAction.getIdSeq());
          saveMessage("cadsr.formbuilder.delete.skippattern.success",request);
        } catch (FormBuilderException exp) {
            if (log.isErrorEnabled()) {
              log.error("Exception on deleteing  Skip pattern  " , exp);
            }
        saveError(ERROR_SKIP_PATTERN_DELETE, request);
            saveError(exp.getErrorCode(), request);
            return mapping.findForward("backToModuleEdit");

        }
      return mapping.findForward("backToModuleEdit");
    }

    /**
     * Delete a Skip pattern for Valid value
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
    public ActionForward deleteValidValueSkipPattern(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
        DynaActionForm formBean = (DynaActionForm) form;
        String triggerIndexStr = (String)formBean.get(TRIGGER_ACTION_INDEX);
        String questionIndexStr = (String)formBean.get(SK_QUESTION_INDEX);
        String validValueIndexStr = (String)formBean.get(SK_VALID_VALUE_INDEX);
        int triggerIndex= Integer.parseInt(triggerIndexStr);
        int questionIndex= Integer.parseInt(questionIndexStr);
        int validValueIndex= Integer.parseInt(validValueIndexStr);
        Module sourceModule = (Module) getSessionObject(request,MODULE);
        List questions = sourceModule.getQuestions();
        Question question = (Question)questions.get(questionIndex);
        List validValues = question.getValidValues();
        FormValidValue sourceValidValue = (FormValidValue)validValues.get(validValueIndex);

        TriggerAction triggerAction =  sourceValidValue.getTriggerActions().remove(triggerIndex);
        try {
          FormBuilderServiceDelegate service = getFormBuilderService();
          service.deleteTriggerAction(triggerAction.getIdSeq());
          saveMessage("cadsr.formbuilder.delete.skippattern.success",request);
        } catch (FormBuilderException exp) {
            if (log.isErrorEnabled()) {
              log.error("Exception on deleteing  Skip pattern  " , exp);
            }
        saveError(ERROR_SKIP_PATTERN_DELETE, request);
            saveError(exp.getErrorCode(), request);
            return mapping.findForward("backToModuleEdit");

        }
      return mapping.findForward("backToModuleEdit");
    }
        /**
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
        public ActionForward saveSkipPattern(
          ActionMapping mapping,
          ActionForm form,
          HttpServletRequest request,
          HttpServletResponse response) throws IOException, ServletException {
          DynaActionForm skipForm = (DynaActionForm) form;

          TriggerAction triggerAction = (TriggerAction)getSessionObject(request,SKIP_PATTERN);
           TriggerAction triggerActionClone = (TriggerAction)getSessionObject(request,SKIP_PATTERN_CLONE);

          String instruction = (String)skipForm.get(SKIP_INSTRUCTION);
          //because this form bean is in session scope and nothing will be submitted if checkbox is unchecked.
          //String[] selectedProtocolIds = (String[]) skipForm.get(SELECTED_SKIP_PROTOCOL_IDS);
          //String[] selectedAccsis = (String[]) skipForm.get(SELECTED_SKIP_AC_CSIS);

          String[] selectedProtocolIds = request.getParameterValues(SELECTED_SKIP_PROTOCOL_IDS);
          String[] selectedAccsis = request.getParameterValues(SELECTED_SKIP_AC_CSIS);
          skipForm.set(SELECTED_SKIP_PROTOCOL_IDS, selectedProtocolIds);
          skipForm.set(SELECTED_SKIP_AC_CSIS, selectedAccsis);


        //Validate

          TriggerAction savedAction = null;
          boolean isCreate = false;
            if(triggerAction.getIdSeq()==null)
            {
                if(!validateSkipPattern(triggerAction.getActionSource().getTriggerActions(),
                           triggerAction.getIdSeq(),triggerAction.getActionTarget().getIdseq(),
                           selectedProtocolIds,selectedAccsis,request))
                           {
                               return mapping.findForward("editSkipPattern");
                           }

                //Create new Skip Pattern
                 isCreate=true;
                 triggerAction.setInstruction(instruction);
                 if (selectedProtocolIds!=null && selectedProtocolIds.length!=0){
                     List protocols = new ArrayList();
                     for (int i=0; i<selectedProtocolIds.length; i++){
                        Protocol p = new ProtocolTransferObject();
                        p.setProtoIdseq(selectedProtocolIds[i]);
                        protocols.add(p);
                     }
                     triggerAction.setProtocols(protocols);
                 }

                if (selectedAccsis!=null && selectedAccsis.length!=0){
                    List cscsiList = new ArrayList();
                    for (int i=0; i<selectedAccsis.length; i++){
                       ClassSchemeItem csi = new CSITransferObject();
                       csi.setAcCsiIdseq(selectedAccsis[i]);
                       cscsiList.add(csi);
                    }
                    triggerAction.setClassSchemeItems(cscsiList);
                }

                 try {
                   FormBuilderServiceDelegate service = getFormBuilderService();
                   savedAction = service.createTriggerAction(triggerAction);
                     saveMessage("cadsr.formbuilder.create.skippattern.success",request);
                 } catch (FormBuilderException exp) {
                     if (log.isErrorEnabled()) {
                       log.error("Exception on creating new Skip pattern  " , exp);
                     }
                 saveError(ERROR_SKIP_PATTERN_CREATE, request);
                     saveError(exp.getErrorCode(), request);
                     return mapping.findForward("editSkipPattern");

                 }
            }
            else
            {
                //Editing Skip pattern
                TriggerActionChanges changes = new TriggerActionChangesTransferObject();
                changes.setTriggerActionId(triggerAction.getIdSeq());
                boolean update = false;
                if(instruction!=null&&triggerActionClone.getInstruction()!=null)
                {
                    if(!instruction.equals(triggerActionClone.getInstruction()))
                    {
                        changes.setNewInstruction(instruction);
                        update=true;
                    }
                }
                else if(instruction==null&&triggerActionClone.getInstruction()!=null)
                {
                    changes.setNewInstruction("");
                    update=true;
                }
                else if(instruction!=null&&triggerActionClone.getInstruction()==null)
                {
                    changes.setNewInstruction(instruction);
                    update=true;
                }
                if(!triggerActionClone.getActionTarget().getIdseq().equals(triggerAction.getActionTarget().getIdseq()))
                {
//                    changes.setTriggerActionId(triggerAction.getActionTarget().getIdseq());
                    changes.setNewTargetId(triggerAction.getActionTarget().getIdseq());
                }

                if(selectedProtocolIds==null || selectedProtocolIds.length==0)
                {
                    changes.setDeleteProtocols(Arrays.asList(getProtocolIds(triggerAction.getProtocols())));
                    update=true;
                }
                else
                {
                    String[] orgIds = getProtocolIds(triggerAction.getProtocols());
                    changes.setAddProtocols(getNewIds(orgIds,selectedProtocolIds));
                    changes.setDeleteProtocols(getDeletedIds(orgIds,selectedProtocolIds));
                    if(!changes.getAddProtocols().isEmpty()||!changes.getDeleteProtocols().isEmpty())
                        update=true;
                }
                if(selectedAccsis == null || selectedAccsis.length==0)
                {
                    changes.setDeleteCsis(Arrays.asList(getAcCsiIds(triggerAction.getClassSchemeItems())));
                    update=true;
                }
                else
                {
                    String[] orgIds = getAcCsiIds(triggerAction.getClassSchemeItems());
                    changes.setAddCsis(getNewIds(orgIds,selectedAccsis));
                    changes.setDeleteCsis(getDeletedIds(orgIds,selectedAccsis));
                    if(!changes.getAddCsis().isEmpty()||!changes.getDeleteCsis().isEmpty())
                        update=true;
                }
                if(update)
                {

                    if(!validateSkipPattern(triggerAction.getActionSource().getTriggerActions(),
                               triggerAction.getIdSeq(),triggerAction.getActionTarget().getIdseq(),
                               selectedProtocolIds,selectedAccsis,request))
                               {
                                   return mapping.findForward("editSkipPattern");
                               }
                    try {
                      FormBuilderServiceDelegate service = getFormBuilderService();
                      savedAction = service.updateTriggerAction(changes);
                      saveMessage("cadsr.formbuilder.save.skippattern.success",request);
                      //Add Message
                    } catch (FormBuilderException exp) {
                        if (log.isErrorEnabled()) {
                          log.error("Exception on Saving  Skip pattern  " , exp);
                        }
                    saveError(ERROR_SKIP_PATTERN_SAVE, request);
                        saveError(exp.getErrorCode(), request);
                        return mapping.findForward("editSkipPattern");

                    }
                }
                else
                {
                     saveMessage("cadsr.formbuilder.save.skippattern.nochanges",request);
                     removeSessionObject(request,SKIP_PATTERN);
                     removeSessionObject(request,SKIP_PATTERN_CLONE);
                     removeSessionObject(request,SKIP_TARGET_FORM);
                     return mapping.findForward("backToModuleEdit");
                }

            }
            triggerAction.setIdSeq(savedAction.getIdSeq());
            triggerAction.setClassSchemeItems(savedAction.getClassSchemeItems());
            triggerAction.setProtocols(savedAction.getProtocols());
            triggerAction.setInstruction(savedAction.getInstruction());

            FormElement source = triggerAction.getActionSource();
            List<TriggerAction> actions = source.getTriggerActions();

            if(actions==null)
            {
                actions = new ArrayList<TriggerAction>();
                source.setTriggerActions(actions);
            }
            if(isCreate)
            {
                actions.add(triggerAction);
            }

            removeSessionObject(request,SKIP_PATTERN);
            removeSessionObject(request,SKIP_PATTERN_CLONE);
            removeSessionObject(request,SKIP_TARGET_FORM);

            return mapping.findForward("backToModuleEdit");

        }


      private String[] getProtocolIds(List<Protocol> protocols)
      {
          String[] protoIds = new String[0];
          if(protocols==null)
            return protoIds;

          if(protocols.isEmpty())
            return protoIds;
          protoIds = new String[protocols.size()];
          int i=0;
          for(Protocol proto:protocols )
          {
              protoIds[i]=proto.getIdseq();
              i++;
          }
          return protoIds;
      }
    private String[] getAcCsiIds(List<ClassSchemeItem> csis)
    {
        String[] accsiIds = new String[0];
        if(csis==null)
          return accsiIds;

        if(csis.isEmpty())
          return accsiIds;
        accsiIds = new String[csis.size()];
        int i=0;
        for(ClassSchemeItem csi:csis )
        {
            accsiIds[i]=csi.getAcCsiIdseq();
            i++;
        }
        return accsiIds;
    }
    private List<String> getNewIds(String[] orgIds, String[] newIds)
    {
        List<String> orgList = new ArrayList(Arrays.asList(orgIds));  // new ArrayList<String>(orgIds);
        List<String> newList = new ArrayList(Arrays.asList(newIds));
         if(orgIds==null)
            return newList;
         if(orgIds.length==0)
            return newList;
        for(String id:orgIds)
        {
            if(newList.contains(id))
            {
                newList.remove(id);
            }
        }
        return newList;
    }

    private List<String> getDeletedIds(String[] orgIds, String[] newIds)
    {
        List<String> orgList = new ArrayList(Arrays.asList(orgIds));  // new ArrayList<String>(orgIds);
         List<String> newList = new ArrayList(Arrays.asList(newIds));
         List<String> deletedList = new ArrayList();
        if(newIds==null)
           return orgList;
        if(newIds.length==0)
           return orgList;
        //for(String id:newIds)
        for(String id:orgIds)
        {
           if(!newList.contains(id))
           {
               deletedList.add(id);
           }
        }
        return deletedList;
    }
  private boolean validateSkipPattern(List<TriggerAction> actions,
                String newTriggerId,String newTargetId, String[] newProtIdSeqs,
                String[] newAcCsis,HttpServletRequest request)
  {
      if(newAcCsis==null)
          newAcCsis = new String[0];
      if(newProtIdSeqs==null)
          newProtIdSeqs = new String[0];

      if(actions==null)
        return true;
      if(actions.isEmpty())
        return true;
      List<TriggerAction> actionListToCheck = actions;
      if(newTriggerId!=null) //Edit
      {
          actionListToCheck = new ArrayList<TriggerAction>();
          for(TriggerAction action:actions)
          {
              if(!action.getIdSeq().equals(newTriggerId))
                actionListToCheck.add(action);
         }
      }

      //Check to see if there there is matching target
       for(TriggerAction action:actionListToCheck)
       {
           if(newTargetId!=null&&newTargetId.equals(action.getActionTarget().getIdseq()))
           {
               saveError("cadsr.formbuilder.save.skippattern.validate.error.duplicatesourcetarget",request);
                return false;
           }
           //Check to see is anyother trigger action to diffrent target use
           //the same protocol or classification
           if(action.getProtocols()==null)
               action.setProtocols(new ArrayList<Protocol>());

            for(int i=0;i<newProtIdSeqs.length;++i)
            {
               Protocol tempProtocol = new ProtocolTransferObject();
                tempProtocol.setProtoIdseq(newProtIdSeqs[i]);
                if(action.getProtocols().contains(tempProtocol))
                {
                    saveError("cadsr.formbuilder.save.skippattern.validate.error.protocol",request);
                    return false;
                }
            }

           if(action.getClassSchemeItems()==null)
               action.setClassSchemeItems(new ArrayList<ClassSchemeItem>());

           for(int j=0;j<newAcCsis.length;++j)
           {
               ClassSchemeItem tempCSI = new CSITransferObject();
               tempCSI.setAcCsiIdseq(newAcCsis[j]);
               if(action.getClassSchemeItems().contains(tempCSI))
               {
                   saveError("cadsr.formbuilder.save.skippattern.validate.error.classification",request);
                  return false;
              }
           }

       }
      return true;

  }

private boolean contains(List list,Object element)

          {
             ListIterator it= list.listIterator();
             while(it.hasNext())
             {
              if(it.next().equals(element))
                return true;
            }
            return false;
          }
}