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
import gov.nih.nci.ncicb.cadsr.dto.TriggerActionTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormJspUtil;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.FormElement;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;

import java.util.ArrayList;


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
       /**
          try
          {
               formClone = (Form)sourceForm.clone();
               moduleClone = (Module)sourceModule.clone();
               formClone.setModules(null);
               moduleClone.setQuestions(null);
              moduleClone.setForm(formClone);
          }
            catch (CloneNotSupportedException exp) {
              saveError(ERROR_FORM_SAVE_FAILED, request);
              if (log.isErrorEnabled()) {
                log.error("On save, Exception on cloneing Form/Module " + exp);
              }
              return mapping.findForward(FAILURE);
            }
            
            **/
        sourceModule.setForm(sourceForm);
        TriggerAction triggerAction = new TriggerActionTransferObject();
        triggerAction.setActionSource(sourceModule);
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
    public ActionForward createValidValueSkipPattern(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      
      DynaActionForm searchForm = (DynaActionForm) form;
      FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;
      String questionIndexStr = (String)formBean.get(QUESTION_INDEX);
      String validvalueIndexStr = (String)formBean.get(VALID_VALUE_INDEX);
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
        
        Form formClone = null;      
        Module moduleClone = null;
        Question qClone = null;
        FormValidValue vvClone = null;
        /**
           try
           {
                formClone = (Form)sourceForm.clone();
                moduleClone = (Module)sourceModule.clone();
                qClone = (Question)question.clone();
                vvClone = (FormValidValue)vv.clone();
                formClone.setModules(null);
                moduleClone.setQuestions(null);
                qClone.setValidValues(null);
                moduleClone.setForm(formClone);
                qClone.setModule(moduleClone);
                vvClone.setQuestion(qClone);
           }
             catch (CloneNotSupportedException exp) {
               saveError(ERROR_FORM_SAVE_FAILED, request);
               if (log.isErrorEnabled()) {
                 log.error("On save, Exception on cloneing Form/Module/ValidValue " + exp);
               }
               return mapping.findForward(FAILURE);
             }
             **/
         TriggerAction triggerAction = new TriggerActionTransferObject();
         triggerAction.setActionSource(vv);
         setSessionObject(request,SKIP_PATTERN,triggerAction);     
        
      return mapping.findForward("createSkipPattern");
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
      String indexStr = (String)formBean.get(MODULE_INDEX);
      int index= Integer.parseInt(indexStr);
      TriggerAction triggerAction = (TriggerAction)getSessionObject(request,SKIP_PATTERN);
      Form targetForm = (Form)getSessionObject(request,SKIP_TARGET_FORM);
      List moules = targetForm.getModules();
      Module targetModule = (Module)moules.get(index);
        Module targetModuleClone = null;
      try
      {
           targetModuleClone = (Module)targetModule.clone();
      }
        catch (CloneNotSupportedException exp) {
          saveError(ERROR_FORM_SAVE_FAILED, request);
          if (log.isErrorEnabled()) {
            log.error("On save, Exception on cloneing Nodule " + exp);
          }
          return mapping.findForward(FAILURE);
        }
      targetModuleClone.setForm(targetForm);
      triggerAction.setActionTarget(targetModuleClone);
      
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
      String modIndexStr = (String)formBean.get(MODULE_INDEX);
      String questionIndexStr = (String)formBean.get(QUESTION_INDEX);
      int modIndex= Integer.parseInt(modIndexStr);
      int questionIndex= Integer.parseInt(questionIndexStr);
        
      TriggerAction triggerAction = (TriggerAction)getSessionObject(request,SKIP_PATTERN);
      Form targetForm = (Form)getSessionObject(request,SKIP_TARGET_FORM);
      List moules = targetForm.getModules();
      Module targetModule = (Module)moules.get(modIndex);
      Question targetQuestion = (Question)targetModule.getQuestions().get(questionIndex);
        Module targetModuleClone = null;
        Question targetQuestionClone = null;
      try
      {
           targetModuleClone = (Module)targetModule.clone();
           targetQuestionClone = (Question)targetQuestion.clone();
           
      }
        catch (CloneNotSupportedException exp) {
          saveError(ERROR_FORM_SAVE_FAILED, request);
          if (log.isErrorEnabled()) {
            log.error("On save, Exception on cloneing Nodule " + exp);
          }
          return mapping.findForward(FAILURE);
        }
      targetQuestionClone.setModule(targetModuleClone);
      targetModuleClone.setForm(targetForm);
      triggerAction.setActionTarget(targetQuestionClone);
      
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
        public ActionForward saveSkipPattern(
          ActionMapping mapping,
          ActionForm form,
          HttpServletRequest request,
          HttpServletResponse response) throws IOException, ServletException {
          
            TriggerAction triggerAction = (TriggerAction)getSessionObject(request,SKIP_PATTERN);
            
          List csis = new ArrayList();
            CSITransferObject csito1 = new CSITransferObject();
            csito1.setClassSchemeItemName("Multiple Myeloma");
            csito1.setClassSchemeItemType("DISEASE_TYPE");
            csito1.setCsiIdseq("");
            csito1.setCsCsiIdseq("");
            csito1.setClassSchemeDefinition("Type of Disease");
            csito1.setClassSchemeLongName( "Type of Disease");
            csito1.setAcCsiIdseq("");
            csito1.setCsIdseq("");
             
            csis.add(csito1);
             
            CSITransferObject csito2 = new CSITransferObject();
            csito2.setClassSchemeItemName("caBIG");
            csito2.setClassSchemeItemType("DISEASE_TYPE");
            csito2.setCsiIdseq("");
            csito2.setCsCsiIdseq("");
            csito2.setClassSchemeDefinition(" Cancer Centralized Clinical Database");
            csito2.setClassSchemeLongName( "C3D Domain");
            csito2.setAcCsiIdseq("");
            csito2.setCsIdseq("");            
            csis.add(csito2);
            
            triggerAction.setClassSchemeItems(csis);
            
            List protocols = new ArrayList();            
            Protocol p1 = new ProtocolTransferObject();
            p1.setLongName("CTMS Version 3.0");
            protocols.add(p1);
            Protocol p2 = new ProtocolTransferObject();
            p2.setLongName("NETTRIALS");
            protocols.add(p2);
            
            FormElement source = triggerAction.getActionTarget();
            List<TriggerAction> actions = new ArrayList<TriggerAction>();    
            actions.add(triggerAction);
            source.setTriggerActions(actions);
            if(FormJspUtil.getFormElementType
                         (triggerAction.getActionSource()).equals(FormJspUtil.FORM))
            {
                    return mapping.findForward("backToFormEdit");
            }
            else
            {
                return mapping.findForward("backToModuleEdit");
            }
          
        }

  



}  