package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;

import java.util.HashMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;


public class FormModuleEditAction  extends FormBuilderBaseDispatchAction{
 
 
  private static final String UPDATED_QUESTION_LIST="updatedQuestionList";
  private static final String NEW_QUESTION_LIST="newQuestionList";
  private static final String DELETED_QUESTION_LIST="deletedQuestionList";
  private static final String DELETED_VV_LIST="deletedVVList";
  private static final String NEW_VV_LIST="newVVList";
  private static final String UPDATED_VV_LIST="updatedVVList";
  private static final String DELETED_VV_MAP="deletedVVMap";
  private static final String NEW_VV_MAP="newVVMap";
  private static final String UPDATED_VV_MAP="updatedVVMap";  

  private static final String VALID_VALUE_CHANGES="validValueChanges";
  /**
   * Sets Module given an Id for Edit.
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
  public ActionForward getModuleToEdit (
    ActionMapping mapping,
    ActionForm editForm,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    Form crf = null;
    DynaActionForm moduleEditForm = (DynaActionForm) editForm;
    
    Integer moduleIndex = (Integer) moduleEditForm.get(MODULE_INDEX);
    
    Form orgCRF = (Form)getSessionObject(request, CLONED_CRF);
    List orgModules = orgCRF.getModules();
    Module orgModule = (Module) orgModules.get(moduleIndex.intValue());
    setSessionObject(request, CLONED_MODULE, orgModule);

    
    crf = (Form) getSessionObject(request, CRF);

    List modules = crf.getModules();
    Module selectedModule = (Module) modules.get(moduleIndex.intValue());
    String[] questionArr = getQuestionsAsArray(selectedModule.getQuestions());
    moduleEditForm.set(MODULE_LONG_NAME, selectedModule.getLongName());
    moduleEditForm.set(
      MODULE_INSTRUCTION_LONG_NAME, selectedModule.getLongName());
    moduleEditForm.set(MODULE_QUESTIONS, questionArr);
    setSessionObject(request, MODULE, selectedModule);

    FormBuilderServiceDelegate service = getFormBuilderService();
    Collection allVdIds = getAllVDsForQuestions(selectedModule.getQuestions());
    Map validValueMap = null;

    try {
      validValueMap = service.getValidValues(allVdIds);
    }
    catch (FormBuilderException exp) {
      if (log.isDebugEnabled()) {
        log.debug("Exp while getting validValue" + exp);
      }
    }
    Map availableValidValuesMap = getAvailableValidValuesForQuestions(selectedModule.getQuestions(),validValueMap);
    setSessionObject(request, AVAILABLE_VALID_VALUES_MAP, availableValidValuesMap);

    return mapping.findForward(SUCCESS);
  }

  /**
   * Swap the display order of the Question with the previous Question.
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
  public ActionForward moveQuestionUp(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm moduleEditForm = (DynaActionForm) form;
    Integer questionIndex = (Integer) moduleEditForm.get(QUESTION_INDEX);
    int currQuestionIndex = questionIndex.intValue();
    Module module = (Module) getSessionObject(request, MODULE);
    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    List questions = module.getQuestions();

    if ((questions != null) && (questions.size() > 1)) {
      Question currQuestion = (Question) questions.get(currQuestionIndex);
      Question prvQuestion = (Question) questions.get(currQuestionIndex - 1);
      int currQuestionDisplayOrder = currQuestion.getDisplayOrder();
      currQuestion.setDisplayOrder(prvQuestion.getDisplayOrder());
      prvQuestion.setDisplayOrder(currQuestionDisplayOrder);
      questions.remove(currQuestionIndex);
      questions.add(currQuestionIndex - 1, currQuestion);
    }

    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS, questionArr);

    if (log.isDebugEnabled()) {
      log.info("Move up Question ");
    }

    return mapping.findForward(MODULE_EDIT);
  }

  /**
   * Swap the display order of the Question with the next Question.
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
  public ActionForward moveQuestionDown(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm moduleEditForm = (DynaActionForm) form;
    Integer questionIndex = (Integer) moduleEditForm.get(QUESTION_INDEX);
    int currQuestionIndex = questionIndex.intValue();
    Module module = (Module) getSessionObject(request, MODULE);
    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    List questions = module.getQuestions();

    if ((questions != null) && (questions.size() > 1)) {
      Question currQuestion = (Question) questions.get(currQuestionIndex);
      Question nextQuestion = (Question) questions.get(currQuestionIndex + 1);
      int currQuestionDisplayOrder = currQuestion.getDisplayOrder();
      currQuestion.setDisplayOrder(nextQuestion.getDisplayOrder());
      nextQuestion.setDisplayOrder(currQuestionDisplayOrder);
      questions.remove(currQuestionIndex);
      questions.add(currQuestionIndex + 1, currQuestion);
    }

    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS, questionArr);

    if (log.isDebugEnabled()) {
      log.info("Move Down Question ");
    }

    return mapping.findForward(MODULE_EDIT);
  }

  /**
   * Deletes the Question of specified index and adds the Module to deleted
   * list.
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
  public ActionForward deleteQuestion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm moduleEditForm = (DynaActionForm) form;
    Integer questionIndex = (Integer) moduleEditForm.get(QUESTION_INDEX);

    Module module = (Module) getSessionObject(request, MODULE);
    List deletedQuestions = (List) getSessionObject(request, DELETED_QUESTIONS);

    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    if (deletedQuestions == null) {
      deletedQuestions = new ArrayList();
    }

    List questions = module.getQuestions();

    if ((questions != null) && (questions.size() > 0)) {
      Question deletedQuestion =
        (Question) questions.remove(questionIndex.intValue());
      FormActionUtil.decrementDisplayOrder(questions, questionIndex.intValue());
      deletedQuestions.add(deletedQuestion);
    }

    setSessionObject(request, DELETED_QUESTIONS, deletedQuestions);
    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS, questionArr);

    return mapping.findForward(MODULE_EDIT);
  }

  /**
   * Add Question from deleted list.
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
  public ActionForward addFromDeletedQuestionList(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm moduleEditForm = (DynaActionForm) form;
    Integer questionIndex = (Integer) moduleEditForm.get(QUESTION_INDEX);

    String addDeletedQuestionIdSeq =
      (String) moduleEditForm.get(ADD_DELETED_QUESTION_IDSEQ);

    Module module = (Module) getSessionObject(request, MODULE);
    List deletedQuestions = (List) getSessionObject(request, DELETED_QUESTIONS);
    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    List questions = module.getQuestions();

    if (
      (questions != null) && (questionIndex != null) &&
          (deletedQuestions != null)) {
      Question questionToAdd =
        removeQuestionFromList(addDeletedQuestionIdSeq, deletedQuestions);

      if (
        (questionIndex.intValue() < questions.size()) &&
            (questionToAdd != null)) {
        Question currQuestion =
          (Question) questions.get(questionIndex.intValue());
        int displayOrder = currQuestion.getDisplayOrder();
        FormActionUtil.incrementDisplayOrder(questions, questionIndex.intValue());
        questionToAdd.setDisplayOrder(displayOrder);
        questions.add((questionIndex.intValue()), questionToAdd);
      }
      else {
        int newDisplayOrder = 0;

        if (questionIndex.intValue() != 0) {
          Question lastQuestion =
            (Question) questions.get(questions.size() - 1);
          newDisplayOrder = lastQuestion.getDisplayOrder() + 1;
        }

        questionToAdd.setDisplayOrder(newDisplayOrder);
        questions.add(questionToAdd);
      }
    }

    setSessionObject(request, DELETED_QUESTIONS, deletedQuestions);
    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS, questionArr);

    return mapping.findForward(MODULE_EDIT);
  }

  /**
   * Swap the display order of the ValidValue with the previous VV.
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
  public ActionForward moveValidValueUp(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm moduleEditForm = (DynaActionForm) form;
    Integer questionIndex = (Integer) moduleEditForm.get(QUESTION_INDEX);
    Integer validValueIndex = (Integer) moduleEditForm.get(VALID_VALUE_INDEX);
    int currQuestionIndex = questionIndex.intValue();
    int currValidValueIndex = validValueIndex.intValue();
    Module module = (Module) getSessionObject(request, MODULE);
    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);
    List validValues = currQuestion.getValidValues();

    if ((validValues != null) && (validValues.size() > 1)) {
      FormValidValue currValidValue =
        (FormValidValue) validValues.get(currValidValueIndex);
      FormValidValue prvValidValue =
        (FormValidValue) validValues.get(currValidValueIndex - 1);
      int currValidValueDisplayOrder = currValidValue.getDisplayOrder();
      currValidValue.setDisplayOrder(prvValidValue.getDisplayOrder());
      prvValidValue.setDisplayOrder(currValidValueDisplayOrder);
      validValues.remove(currValidValueIndex);
      validValues.add(currValidValueIndex - 1, currValidValue);
    }

    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS, questionArr);

    if (log.isDebugEnabled()) {
      log.info("Move up Question ");
    }

    return mapping.findForward(MODULE_EDIT);
  }

  /**
   * Swap the display order of the ValidValue with the next ValidValue.
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
  public ActionForward moveValidValueDown(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm moduleEditForm = (DynaActionForm) form;
    Integer questionIndex = (Integer) moduleEditForm.get(QUESTION_INDEX);
    Integer validValueIndex = (Integer) moduleEditForm.get(VALID_VALUE_INDEX);
    int currQuestionIndex = questionIndex.intValue();
    int currValidValueIndex = validValueIndex.intValue();
    Module module = (Module) getSessionObject(request, MODULE);
    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);
    List validValues = currQuestion.getValidValues();

    if ((validValues != null) && (validValues.size() > 1)) {
      FormValidValue currValidValue =
        (FormValidValue) validValues.get(currValidValueIndex);
      FormValidValue nextValidValue =
        (FormValidValue) validValues.get(currValidValueIndex + 1);
      int currValidValueDisplayOrder = currValidValue.getDisplayOrder();
      currValidValue.setDisplayOrder(nextValidValue.getDisplayOrder());
      nextValidValue.setDisplayOrder(currValidValueDisplayOrder);
      validValues.remove(currValidValueIndex);
      validValues.add(currValidValueIndex + 1, currValidValue);
    }

    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS, questionArr);

    if (log.isDebugEnabled()) {
      log.info("Move Down Question ");
    }

    return mapping.findForward(MODULE_EDIT);
  }

  /**
   * Deletes the ValidValue of specified index.
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
  public ActionForward deleteValidValue(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm moduleEditForm = (DynaActionForm) form;

    Integer questionIndex = (Integer) moduleEditForm.get(QUESTION_INDEX);
    Integer validValueIndex = (Integer) moduleEditForm.get(VALID_VALUE_INDEX);
    int currQuestionIndex = questionIndex.intValue();
    int currValidValueIndex = validValueIndex.intValue();
    Module module = (Module) getSessionObject(request, MODULE);

    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);
    List validValues = currQuestion.getValidValues();

    if ((validValues != null) && (validValues.size() > 0)) {
      FormValidValue deletedValidValue =
        (FormValidValue) validValues.remove(currValidValueIndex);        
      FormActionUtil.decrementDisplayOrder(validValues, currValidValueIndex);
    }

    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS, questionArr);

    return mapping.findForward(MODULE_EDIT);
  }

  /**
   * Add ValidValue from deleted list.
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
  public ActionForward addFromAvailableValidValueList(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm moduleEditForm = (DynaActionForm) form;
    Integer questionIndex = (Integer) moduleEditForm.get(QUESTION_INDEX);
    Integer validValueIndex = (Integer) moduleEditForm.get(VALID_VALUE_INDEX);
    int currQuestionIndex = questionIndex.intValue();
    int currValidValueIndex = validValueIndex.intValue();
    Module module = (Module) getSessionObject(request, MODULE);

    Integer addAvailableValidValueIndex =
      (Integer) moduleEditForm.get(ADD_AVAILABLE_VALID_VALUE_INDEX);

    Map availbleValidValuesMap =
      (Map) getSessionObject(request, this.AVAILABLE_VALID_VALUES_MAP);
    
    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);

    List availablevvList = (List)availbleValidValuesMap.get(currQuestion.getQuesIdseq());
    
    FormValidValue formValidValueToAdd = (FormValidValue)availablevvList.get(addAvailableValidValueIndex.intValue());;
    
    if(formValidValueToAdd==null)
      return mapping.findForward(MODULE_EDIT);
    
    List validValues = currQuestion.getValidValues();
    
    if (currValidValueIndex < validValues.size()) {
      FormValidValue currValidValue =
        (FormValidValue) validValues.get(currValidValueIndex);
      int displayOrder = currValidValue.getDisplayOrder();
      FormActionUtil.incrementDisplayOrder(validValues, currValidValueIndex);
      formValidValueToAdd.setDisplayOrder(displayOrder);
      validValues.add(currValidValueIndex, formValidValueToAdd);
    }
    else {
      int newDisplayOrder = 0;

      if (currValidValueIndex != 0) {
        FormValidValue lastValidValue =
          (FormValidValue) validValues.get(validValues.size() - 1);
        newDisplayOrder = lastValidValue.getDisplayOrder() + 1;
      }

      formValidValueToAdd.setDisplayOrder(newDisplayOrder);
      validValues.add(formValidValueToAdd);
    }

    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS, questionArr);

    return mapping.findForward(MODULE_EDIT);
  }

  /**
   * Add ValidValue from deleted list.
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
  public ActionForward saveModule(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm moduleEditForm = (DynaActionForm) form;  
    Module module = (Module) getSessionObject(request, MODULE);
    Integer index = (Integer) moduleEditForm.get(MODULE_INDEX);
    Form crf = (Form) getSessionObject(request, CRF);
    Form orgCrf = (Form) getSessionObject(request, this.CLONED_CRF);
    module.setForm(crf);
    Module orgModule = (Module) getSessionObject(request, CLONED_MODULE);
    String longName = (String) moduleEditForm.get(MODULE_LONG_NAME);
    module.setLongName(longName);
    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    Map changes = getUpdatedNewDeletedQuestions(module,orgModule.getQuestions(),module.getQuestions());
    Module moduleHeader = null;
    if(!longName.equals(orgModule.getLongName()))
    {
      moduleHeader = new ModuleTransferObject();
      moduleHeader.setLongName(longName);
      moduleHeader.setModuleIdseq(module.getLongName());
    }
    if(changes.isEmpty()&&moduleHeader==null)
    {
      saveMessage("cadsr.formbuilder.form.edit.nochange", request);
      return mapping.findForward(MODULE_EDIT);
    }

    FormBuilderServiceDelegate service = getFormBuilderService();
    Module updatedModule = null;
    try{
    updatedModule = service.updateModule(module.getModuleIdseq(),
                          moduleHeader,
                          (Collection)changes.get(UPDATED_QUESTION_LIST),
                         (Collection)changes.get(DELETED_QUESTION_LIST),
                         (Collection)changes.get(NEW_QUESTION_LIST),
                         (Map)changes.get(UPDATED_VV_MAP),
                         (Map)changes.get(NEW_VV_MAP),
                         (Map)changes.get(DELETED_VV_MAP));
    }
    catch(FormBuilderException exp)
    {
        if (log.isDebugEnabled()) {
          log.debug("Exception on service.updateModule=  " + exp);
        }

        saveMessage(ERROR_MODULE_SAVE_FAILED, request);
        saveMessage(exp.getErrorCode(), request);
        return mapping.findForward(FAILURE);
    }
                         
    saveMessage("cadsr.formbuilder.module.edit.save.success", request);
    crf.getModules().remove(index.intValue());
    crf.getModules().add(index.intValue(),updatedModule);
    orgCrf.getModules().remove(index.intValue());
     try{
     Module newClonedModule = (Module)updatedModule.clone();
     orgCrf.getModules().add(index.intValue(),newClonedModule);
     }
    catch (CloneNotSupportedException clexp) {
      if (log.isDebugEnabled()) {
        log.debug("Exception on Clone =  " + clexp);
      }
      throw new FatalException(clexp);
    }    
    FormBuilderBaseDynaFormBean clearForm = (FormBuilderBaseDynaFormBean) form;
    clearForm.clear();
    removeSessionObject(request, AVAILABLE_VALID_VALUES_MAP);
    removeSessionObject(request,CLONED_MODULE);
    removeSessionObject(request,MODULE);
    return mapping.findForward(SUCCESS);
  }

  /**
   * Cancel Edit and back to Search results
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
  public ActionForward cancelModuleEdit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    FormBuilderBaseDynaFormBean editForm = (FormBuilderBaseDynaFormBean) form;
    removeSessionObject(request, AVAILABLE_VALID_VALUES_MAP);
    removeSessionObject(request,CLONED_MODULE);
    removeSessionObject(request,MODULE);
    editForm.clear();
    return mapping.findForward(SUCCESS);
      
    }  
 private Map getAvailableValidValuesForQuestions(List questions, Map vdvvMap)
 {
   ListIterator questionIterator = questions.listIterator();
   Map availableVVMap = new HashMap();
   while(questionIterator.hasNext())
   {
     Question currQuestion = (Question)questionIterator.next();
     if(currQuestion.getDataElement()==null)
     {
       List vvList  = currQuestion.getValidValues();
       List copyList = cloneFormValidValueList(vvList);
       if(!copyList.isEmpty())
        availableVVMap.put(currQuestion.getQuesIdseq(),copyList);
     }
     else
     {
       //get vv from VD for the question
       String vdIdSeq = null;
       List vdVVList =null;
       ValueDomain vd = currQuestion.getDataElement().getValueDomain();
       if(vd!=null)
        vdIdSeq = vd.getVdIdseq();
       if(vdIdSeq!=null)
       {
         vdVVList = (List)vdvvMap.get(vdIdSeq);
         //Check if vv in question has vpIdseq         
       }
       List vvList = currQuestion.getValidValues();
       ListIterator vvIterator = vvList.listIterator();
       boolean vpvdPresent = false;
       while(vvIterator.hasNext())
       {
         FormValidValue fvv = (FormValidValue)vvIterator.next();
         if(fvv.getVpIdseq()!=null)
          {
            vpvdPresent=true;
            break;
          }
       }  
       //Get the intersection
       List copyList = cloneFormValidValueList(vvList);
       if(vpvdPresent&&vdVVList!=null)
       {
         ListIterator vvvdIterator = vdVVList.listIterator();         
         while(vvvdIterator.hasNext())
         {
           ValidValue vv = (ValidValue)vvvdIterator.next();
           FormValidValue tempFvv = new FormValidValueTransferObject();
           tempFvv.setLongName(vv.getShortMeaningValue());
           tempFvv.setVpIdseq(vv.getVpIdseq());
           if(!copyList.contains(tempFvv))
            copyList.add(tempFvv);
         }          
       }
       if(copyList!=null&&!copyList.isEmpty())
       {
         availableVVMap.put(currQuestion.getQuesIdseq(),copyList);
       }
     }
   }
   return availableVVMap;
 }
 
 private List cloneFormValidValueList(List fvvList)
 {
       List copyVVList = new ArrayList();
       if(fvvList==null)
         return copyVVList;
       ListIterator vvIterator = fvvList.listIterator();
       
       while(vvIterator.hasNext())
       {
         FormValidValue fvv = (FormValidValue)vvIterator.next();
         FormValidValue copyVV = null;
         try{
            copyVV = (FormValidValue)fvv.clone();
         }
        catch (CloneNotSupportedException clexp) {
          if (log.isDebugEnabled()) {
            log.debug("Exception on Clone =  " + clexp);
          }
        }
        if(copyVVList==null)
          copyVVList= new ArrayList();
        if(copyVV!=null)
         copyVVList.add(copyVV);
       }
       return copyVVList;
 }
  /**
   * The Map that is return
   * Has new, updated,deleted questions by respective key
   * the validValue changes are keyed by questionIdseq whose vale
   * is a map containg new, updated,deleted validValues with respective
   * keys
   *
   * @param orgQuestionsList
   * @param editedQuestionList
   *
   * @return the Map Containing the Changes
   */
 private Map getUpdatedNewDeletedQuestions(Module currModule, List orgQuestionList, List editedQuestionList)
 {
   ListIterator editedQuestionIterate = editedQuestionList.listIterator();
   List newQuestionList = new ArrayList();
   List updatedQuestionList = new ArrayList();
   List deletedQuestionList = new ArrayList();
   Map newValidValuesMap = new HashMap();
   Map updatedValidValuesMap = new HashMap();
   Map deletedValidValuesMap = new HashMap();

   
   Map resultMap = new HashMap();
   
   ListIterator iterate = orgQuestionList.listIterator();
   while(iterate.hasNext())
   {
     Question currQuestion = (Question)iterate.next();
     currQuestion.setModule(currModule);
     if(!editedQuestionList.contains(currQuestion))
     {
       deletedQuestionList.add(currQuestion);
     }
   }   
   
   while(editedQuestionIterate.hasNext())
   {
     Question currQuestion = (Question)editedQuestionIterate.next();
     currQuestion.setModule(currModule);
     if(!orgQuestionList.contains(currQuestion))
     {
       List newQuestionVV = currQuestion.getValidValues();
       ListIterator newQuestionVVIt = newQuestionVV.listIterator();
       while(newQuestionVVIt!=null&&newQuestionVVIt.hasNext())
       {
         FormValidValue fvv = (FormValidValue)newQuestionVVIt.next();
         fvv.setQuestion(currQuestion);
       }
       newQuestionList.add(currQuestion);
       
     }
     else
     {
       int orgQuestionIndex= orgQuestionList.indexOf(currQuestion);
       Question orgQuestion = (Question)orgQuestionList.get(orgQuestionIndex);
       DataElement orgQusetionDE = orgQuestion.getDataElement();
       DataElement currQusetionDE = currQuestion.getDataElement();
       boolean hasDEAssosiationChanged = true;
       if(orgQusetionDE==null&&currQusetionDE==null)
       {
         hasDEAssosiationChanged=false;
       }
       else 
       {
         if(orgQusetionDE==null||currQusetionDE==null)
         {
           hasDEAssosiationChanged=true;
         }
         else
         {
           if(orgQusetionDE.equals(currQusetionDE))
           {
             hasDEAssosiationChanged=false;
           }
           else
           {
             hasDEAssosiationChanged = true;
           }
         }
       }
       // Check if the LongName
       // DisplayOrder has been updated
       // DE Association Changed
       if(!orgQuestion.getLongName().equals(currQuestion.getLongName())||
         orgQuestion.getDisplayOrder()!=currQuestion.getDisplayOrder()||
         hasDEAssosiationChanged)
       {
         updatedQuestionList.add(currQuestion);
       }

     // Check if there is change in ValidValues
     Map validValuesChanges = null;
     if(!hasDEAssosiationChanged&&(orgQusetionDE!=null&&currQusetionDE!=null))
      {
         validValuesChanges =  
            getNewDeletedUpdatedValidValues(currQuestion,orgQuestion.getValidValues(),
                                            currQuestion.getValidValues());        
      }
      else if(hasDEAssosiationChanged)
      {
        
        List newVVList = currQuestion.getValidValues();
        if(!newVVList.isEmpty())
        {
          if(validValuesChanges==null)
            validValuesChanges = new HashMap();
          validValuesChanges.put(this.NEW_VV_LIST,newVVList);
        }
      }
      if(validValuesChanges!=null&&!validValuesChanges.isEmpty())
      {        
        List newvvList = (List)validValuesChanges.get(NEW_VV_LIST);
        if(newvvList!=null)
          newValidValuesMap.put(currQuestion.getQuesIdseq(), newvvList);
 
        List deletedList = (List)validValuesChanges.get(DELETED_VV_LIST);
        if(deletedList!=null)
          deletedValidValuesMap.put(currQuestion.getQuesIdseq(), deletedList);

        List updatedvvList = (List)validValuesChanges.get(UPDATED_VV_LIST);
        if(updatedvvList!=null)
          updatedValidValuesMap.put(currQuestion.getQuesIdseq(), updatedvvList);          

      }
     }                                                  
   }
 
   if(!newQuestionList.isEmpty())
   {
     resultMap.put(NEW_QUESTION_LIST,newQuestionList);
   }
   if(!updatedQuestionList.isEmpty())
   {
     resultMap.put(UPDATED_QUESTION_LIST,updatedQuestionList);
   }
   if(!deletedQuestionList.isEmpty())
   {
     resultMap.put(this.DELETED_QUESTION_LIST,deletedQuestionList);
   }
   if(!updatedValidValuesMap.isEmpty())
   {
     resultMap.put(UPDATED_VV_MAP,updatedValidValuesMap);
   }
   if(!deletedValidValuesMap.isEmpty())
   {
     resultMap.put(DELETED_VV_MAP,deletedValidValuesMap);
   }
   if(!newValidValuesMap.isEmpty())
   {
     resultMap.put(NEW_VV_MAP,newValidValuesMap);
   }   
   return resultMap;
 }
  
  /**
  * Gets the validvalue Changes in a map
  *  If no changes returns a empty Map
  * Only Changes are present in the Map
  * */
  private Map getNewDeletedUpdatedValidValues(Question currQuestion, List orgValidValueList,
                                            List editedValidValueList)
    {
       Map resultMap = new HashMap();
       ListIterator orgIterator =   orgValidValueList.listIterator();
       List deletedValidValueList = new ArrayList();
       List newVVList = new ArrayList();
       List updatedVVList = new ArrayList();
       // get the deleted Valid Values first
       while(orgIterator.hasNext())
       {
          FormValidValue currVV = (FormValidValue)orgIterator.next();
          currVV.setQuestion(currQuestion);
          if(!editedValidValueList.contains(currVV))
          {
            deletedValidValueList.add(currVV);
          }
       }
       
       ListIterator editedIterator =   editedValidValueList.listIterator();
       while(editedIterator.hasNext())
       {
         FormValidValue editedVV = (FormValidValue)editedIterator.next();
         editedVV.setQuestion(currQuestion);
         // get new valid Values 
         if(!orgValidValueList.contains(editedVV))
         {
           newVVList.add(editedVV);
         }
         else
         { // get updated(Displayorder) VVs
           int orgIndex = orgValidValueList.indexOf(editedVV);
           FormValidValue orgVV = (FormValidValue)orgValidValueList.get(orgIndex);
           if(editedVV.getDisplayOrder()!= orgVV.getDisplayOrder())
           {
             updatedVVList.add(editedVV);
           }
         }// contained in orgValidValues
       }
       if(!newVVList.isEmpty())
          resultMap.put(NEW_VV_LIST,newVVList);
       if(!updatedVVList.isEmpty())
          resultMap.put(UPDATED_VV_LIST,updatedVVList);
       if(!deletedValidValueList.isEmpty())
          resultMap.put(DELETED_VV_LIST,deletedValidValueList);
      return resultMap;
    }

 


  /**
   * Removes the Question given by "quesIdSeq" from the question list
   *
   * @param questionIdSeq
   * @param modules
   *
   * @return the removed module
   */
  private Question removeQuestionFromList(
    String questionIdSeq,
    List questions) {
    ListIterator iterate = questions.listIterator();

    while (iterate.hasNext()) {
      Question question = (Question) iterate.next();

      if (question.getQuesIdseq().equals(questionIdSeq)) {
        iterate.remove();

        return question;
      }
    }

    return null;
  }

  /**
   * Gets the module given by "moduleIdSeq" from the module list
   *
   * @param moduleIdSeq
   * @param modules
   *
   * @return the  module else returns null;
   */
  private Question getQuestionFromList(
    String moduleIdSeq,
    List questions) {
    ListIterator iterate = questions.listIterator();

    while (iterate.hasNext()) {
      Question question = (Question) iterate.next();

      if (question.getQuesIdseq().equals(moduleIdSeq)) {
        return question;
      }
    }

    return null;
  }

  private String[] getQuestionsAsArray(List questions) {
    if (questions == null) {
      return null;
    }

    ListIterator iterate = questions.listIterator();
    String[] questionArr = new String[questions.size()];

    while (iterate.hasNext()) {
      int index = iterate.nextIndex();
      Question question = (Question) iterate.next();
      questionArr[index] = question.getLongName();
    }

    return questionArr;
  }

  private void setQuestionsFromArray(
    Module module,
    String[] questionArr) {
    List questions = module.getQuestions();

    for (int i = 0; i < questionArr.length; i++) {
      String questionStr = questionArr[i];
      Question currQuestion = (Question) questions.get(i);
      currQuestion.setLongName(questionStr);
    }
  }

  private Collection getAllVDsForQuestions(List questions) {
    ListIterator iterate = questions.listIterator();
    Collection vdIds = new ArrayList();

    while (iterate.hasNext()) {
      Question question = (Question) iterate.next();
      DataElement de = question.getDataElement();

      if (de != null) {
        ValueDomain vd = de.getValueDomain();

        if (vd != null) {
          if (!vdIds.contains(vd.getVdIdseq())) {
            vdIds.add(vd.getVdIdseq());
          }
        }
      }
    }

    return vdIds;
  }
}
