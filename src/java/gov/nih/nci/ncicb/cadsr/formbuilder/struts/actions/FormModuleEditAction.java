package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.InstructionChangesTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ValueDomainTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.resource.AdminComponent;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.resource.InstructionChanges;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Orderable;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;

import java.util.HashMap;
import java.util.Iterator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import gov.nih.nci.ncicb.cadsr.util.DTOTransformer;
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


public class FormModuleEditAction  extends FormBuilderSecureBaseDispatchAction{


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
    setSessionObject(request, CLONED_MODULE, orgModule,true);


    crf = (Form) getSessionObject(request, CRF);

    List modules = crf.getModules();
    Module selectedModule = (Module) modules.get(moduleIndex.intValue());
    String[] questionArr = getQuestionsAsArray(selectedModule.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS, questionArr);
    
    moduleEditForm.set(MODULE_LONG_NAME, selectedModule.getLongName());
    
    if(selectedModule.getInstruction()!=null)
    {
      moduleEditForm.set(
        MODULE_INSTRUCTION, selectedModule.getInstruction().getLongName());
    }

    String[] questionInstructionArr = getQuestionInstructionsAsArray(selectedModule.getQuestions());
    moduleEditForm.set(QUESTION_INSTRUCTIONS,questionInstructionArr);

    String[] validValueInstructionArr = getValidValueInstructionsAsArray(selectedModule.getQuestions());
    moduleEditForm.set(FORM_VALID_VALUE_INSTRUCTIONS,validValueInstructionArr);

    setSessionObject(request, MODULE, selectedModule,true);

    FormBuilderServiceDelegate service = getFormBuilderService();
    Collection allVdIds = getAllVDsForQuestions(selectedModule.getQuestions());
    Map validValueMap = null;

    try {
      validValueMap = service.getValidValues(allVdIds);
    }
    catch (FormBuilderException exp) {
      saveError(ERROR_MODULE_RETRIEVE, request);
      saveError(exp.getErrorCode(),request);
      if (log.isErrorEnabled()) {
        log.error("Exp while getting validValue", exp);
      }
      mapping.findForward(FAILURE);
    }
    selectedModule.setForm(crf);
    Map availableValidValuesMap = getAvailableValidValuesForQuestions(selectedModule,selectedModule.getQuestions(),validValueMap);
    setSessionObject(request, AVAILABLE_VALID_VALUES_MAP, availableValidValuesMap,true);

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
    //Module orgModule = (Module) getSessionObject(request, CLONED_MODULE);
    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);
        
    String[] questionInstructionsArr = (String[]) moduleEditForm.get(QUESTION_INSTRUCTIONS);
    setQuestionInstructionsFromArray(module,questionInstructionsArr);
    
    String[] vvInstructionsArr = (String[]) moduleEditForm.get(FORM_VALID_VALUE_INSTRUCTIONS);
    setValidValueInstructionsFromArray(module,vvInstructionsArr);    

    List questions = module.getQuestions();
    //FormActionUtil.setInitDisplayOrders(questions); //This is done to set display order in a sequential order 
                                      // in case  they are  incorrect in database
                                      //Bug #tt 1136
                                      
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

    String[] questionInstructionsArr = (String[]) moduleEditForm.get(QUESTION_INSTRUCTIONS);
    setQuestionInstructionsFromArray(module,questionInstructionsArr);
    
    String[] vvInstructionsArr = (String[]) moduleEditForm.get(FORM_VALID_VALUE_INSTRUCTIONS);
    setValidValueInstructionsFromArray(module,vvInstructionsArr);
    
    List questions = module.getQuestions();
    //FormActionUtil.setInitDisplayOrders(questions); //This is done to set display order in a sequential order 
                                      // in case  they are  incorrect in database
                                      //Bug #tt 1136
                                      
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

    String[] questionInstructionsArr = (String[]) moduleEditForm.get(QUESTION_INSTRUCTIONS);
    setQuestionInstructionsFromArray(module,questionInstructionsArr);
    
    String[] vvInstructionsArr = (String[]) moduleEditForm.get(FORM_VALID_VALUE_INSTRUCTIONS);
    setValidValueInstructionsFromArray(module,vvInstructionsArr);
    

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

    setSessionObject(request, DELETED_QUESTIONS, deletedQuestions,true);
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
    
    String[] questionInstructionsArr = (String[]) moduleEditForm.get(QUESTION_INSTRUCTIONS);
    setQuestionInstructionsFromArray(module,questionInstructionsArr);
    
    String[] vvInstructionsArr = (String[]) moduleEditForm.get(FORM_VALID_VALUE_INSTRUCTIONS);
    setValidValueInstructionsFromArray(module,vvInstructionsArr);    

    List questions = module.getQuestions();
    //FormActionUtil.setInitDisplayOrders(questions); //This is done to set display order in a sequential order 
                                      // in case  they are  incorrect in database
                                      //Bug #tt 1136
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

    setSessionObject(request, DELETED_QUESTIONS, deletedQuestions,true);
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
    
    String[] questionInstructionsArr = (String[]) moduleEditForm.get(QUESTION_INSTRUCTIONS);
    setQuestionInstructionsFromArray(module,questionInstructionsArr);
    
    String[] vvInstructionsArr = (String[]) moduleEditForm.get(FORM_VALID_VALUE_INSTRUCTIONS);
    setValidValueInstructionsFromArray(module,vvInstructionsArr);    

    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);
    List validValues = currQuestion.getValidValues();
    FormActionUtil.setInitDisplayOrders(validValues); //This is done to set display order in a sequential order 
                                      // in case  they are  incorrect in database
                                      //Bug #tt 1136
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

    String[] questionInstructionsArr = (String[]) moduleEditForm.get(QUESTION_INSTRUCTIONS);
    setQuestionInstructionsFromArray(module,questionInstructionsArr);
    
    String[] vvInstructionsArr = (String[]) moduleEditForm.get(FORM_VALID_VALUE_INSTRUCTIONS);
    setValidValueInstructionsFromArray(module,vvInstructionsArr);
    
    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);
    List validValues = currQuestion.getValidValues();
    FormActionUtil.setInitDisplayOrders(validValues); //This is done to set display order in a sequential order 
                                      // in case  they are  incorrect in database
                                      //Bug #tt 1136
                                      
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

    String[] questionInstructionsArr = (String[]) moduleEditForm.get(QUESTION_INSTRUCTIONS);
    setQuestionInstructionsFromArray(module,questionInstructionsArr);
    
    String[] vvInstructionsArr = (String[]) moduleEditForm.get(FORM_VALID_VALUE_INSTRUCTIONS);
    setValidValueInstructionsFromArray(module,vvInstructionsArr);
    
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
   * Deletes the Multiple ValidValue of specified index.
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
  public ActionForward deleteValidValues(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm moduleEditForm = (DynaActionForm) form;

    Integer questionIndex = (Integer) moduleEditForm.get(QUESTION_INDEX);
    
    String[] selectedVVIndexes = (String[])request.getParameterValues(SELECTED_ITEMS+questionIndex);
     
    int currQuestionIndex = questionIndex.intValue();

    Module module = (Module) getSessionObject(request, MODULE);

    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    String[] questionInstructionsArr = (String[]) moduleEditForm.get(QUESTION_INSTRUCTIONS);
    setQuestionInstructionsFromArray(module,questionInstructionsArr);
    
    String[] vvInstructionsArr = (String[]) moduleEditForm.get(FORM_VALID_VALUE_INSTRUCTIONS);
    setValidValueInstructionsFromArray(module,vvInstructionsArr);
    
    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);
    List validValues = currQuestion.getValidValues();

    for(int i=selectedVVIndexes.length-1;i>-1;--i)
    {
      int currValidValueIndex = (new Integer(selectedVVIndexes[i])).intValue();
      if ((validValues != null) && (validValues.size() > 0)) {
        FormValidValue deletedValidValue =
          (FormValidValue) validValues.remove(currValidValueIndex);
        FormActionUtil.decrementDisplayOrder(validValues, currValidValueIndex);
      }
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
    String addAvailableValidValueIndexStr = request.getParameter(ADD_AVAILABLE_VALID_VALUE_INDEX+currQuestionIndex+validValueIndex);
    int addAvailableValidValueIndex = 0;
    if(addAvailableValidValueIndexStr!=null)
      addAvailableValidValueIndex = Integer.parseInt(addAvailableValidValueIndexStr);

    Map availbleValidValuesMap =
      (Map) getSessionObject(request, this.AVAILABLE_VALID_VALUES_MAP);

    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    String[] questionInstructionsArr = (String[]) moduleEditForm.get(QUESTION_INSTRUCTIONS);
    setQuestionInstructionsFromArray(module,questionInstructionsArr);
    
    String[] vvInstructionsArr = (String[]) moduleEditForm.get(FORM_VALID_VALUE_INSTRUCTIONS);
    setValidValueInstructionsFromArray(module,vvInstructionsArr);
    
    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);

    List availablevvList = (List)availbleValidValuesMap.get(currQuestion.getQuesIdseq());

    FormValidValue formValidValueToAdd = (FormValidValue)availablevvList.get(addAvailableValidValueIndex);

    if(formValidValueToAdd==null)
      return mapping.findForward(MODULE_EDIT);

    List validValues = currQuestion.getValidValues();

                                      
    if (currValidValueIndex < validValues.size()) {
      FormValidValue currValidValue =
        (FormValidValue) validValues.get(currValidValueIndex);
      int displayOrder = currValidValue.getDisplayOrder();
      formValidValueToAdd.setDisplayOrder(displayOrder);
      validValues.add(currValidValueIndex, formValidValueToAdd);
     FormActionUtil.incrementDisplayOrder(validValues, currValidValueIndex+1);
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
    module.setContext(crf.getContext());//This is done so that all new Element created will have this Context
    Module orgModule = (Module) getSessionObject(request, CLONED_MODULE);
    String longName = (String) moduleEditForm.get(MODULE_LONG_NAME);    
    module.setLongName(longName);
    
    String moduleInstruction = (String) moduleEditForm.get(MODULE_INSTRUCTION);
    if(moduleInstruction!=null&&!moduleInstruction.trim().equalsIgnoreCase(""))
    {
      Instruction instr = module.getInstruction();
      if(instr==null)
      {
        instr = new InstructionTransferObject();
        instr.setDisplayOrder(0);
        instr.setVersion(new Float(1));
        instr.setAslName("DRAFT NEW");
        instr.setContext(module.getContext());
        instr.setPreferredDefinition(moduleInstruction);
      }
      instr.setLongName(moduleInstruction);
      module.setInstruction(instr);
    }
    else
    {
      module.setInstruction(null);
    }
    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module, questionArr);

    String[] questionInstructionsArr = (String[]) moduleEditForm.get(QUESTION_INSTRUCTIONS);
    setQuestionInstructionsFromArray(module,questionInstructionsArr);
    
    String[] vvInstructionsArr = (String[]) moduleEditForm.get(FORM_VALID_VALUE_INSTRUCTIONS);
    setValidValueInstructionsFromArray(module,vvInstructionsArr);
    
    Map changes = getUpdatedNewDeletedQuestions(module,orgModule.getQuestions(),module.getQuestions());
    InstructionChanges instructionChanges = getUpdatedNewDeletedInstructions(module,orgModule);
    
    Module moduleHeader = null;
    if(!longName.equals(orgModule.getLongName()))
    {
      moduleHeader = new ModuleTransferObject();
      moduleHeader.setLongName(longName);
      moduleHeader.setModuleIdseq(module.getModuleIdseq());
    }
    if(instructionChanges.isEmpty()&&changes.isEmpty()&&moduleHeader==null)
    {
      saveMessage("cadsr.formbuilder.form.edit.nochange", request);
      return mapping.findForward(MODULE_EDIT);
    }

    FormBuilderServiceDelegate service = getFormBuilderService();
    Module updatedModule = null;
    try{
    //TODO move the method param to a transfer object
      updatedModule = service.updateModule(module.getModuleIdseq(),
                            moduleHeader,
                            (Collection)changes.get(UPDATED_QUESTION_LIST),
                           (Collection)changes.get(DELETED_QUESTION_LIST),
                           (Collection)changes.get(NEW_QUESTION_LIST),
                           (Map)changes.get(UPDATED_VV_MAP),
                           (Map)changes.get(NEW_VV_MAP),
                           (Map)changes.get(DELETED_VV_MAP),
                            instructionChanges);                              
    }
    catch(FormBuilderException exp)
    {
        if (log.isErrorEnabled()) {
          log.error("Exception on saving module  "+module,exp);
        }

        saveError(ERROR_MODULE_SAVE_FAILED, request);
        saveError(exp.getErrorCode(), request);
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
     saveError(ERROR_MODULE_SAVE_FAILED, request);
      if (log.isErrorEnabled()) {
        log.error("Exception while cloning module  " + updatedModule,clexp);
      }
      return mapping.findForward(FAILURE);
    }
    FormBuilderBaseDynaFormBean clearForm = (FormBuilderBaseDynaFormBean) form;
    clearForm.clear();
    removeSessionObject(request, AVAILABLE_VALID_VALUES_MAP);
    removeSessionObject(request,CLONED_MODULE);
    removeSessionObject(request,MODULE);
    removeSessionObject(request,DELETED_QUESTIONS);
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
    FormBuilderBaseDynaFormBean moduleEditForm = (FormBuilderBaseDynaFormBean) form;

    Integer moduleIndex = (Integer) moduleEditForm.get(MODULE_INDEX);

    Form crf = (Form)getSessionObject(request,CRF);
    Module module = (Module)getSessionObject(request,MODULE);

    Module orgModule = (Module)getSessionObject(request,CLONED_MODULE);
    List modules = crf.getModules();
    Module orgModuleClone = null;
    try
    {
      orgModuleClone = (Module)orgModule.clone();
    }
    catch (CloneNotSupportedException clexp) {
      if (log.isErrorEnabled()) {
        log.error("Exception while cloning module  " + orgModule,clexp);
      }
    }
    int index = modules.indexOf(module);
    crf.getModules().remove(index);
    crf.getModules().add(index,orgModuleClone);
    removeSessionObject(request, AVAILABLE_VALID_VALUES_MAP);
    removeSessionObject(request,CLONED_MODULE);
    removeSessionObject(request,MODULE);
    removeSessionObject(request,DELETED_QUESTIONS);
    moduleEditForm.clear();
    return mapping.findForward(SUCCESS);

    }
    
  /**
   * Get all related VDs and prepare for display
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
  public ActionForward viewSubsettedVDs(    
      ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    FormBuilderBaseDynaFormBean moduleEditForm = (FormBuilderBaseDynaFormBean) form;
    
    Integer questionIndex = (Integer) moduleEditForm.get(QUESTION_INDEX);
    int currQuestionIndex = questionIndex.intValue();
    Module module = (Module) getSessionObject(request, MODULE);
    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);
    Question nextQuestion = (Question) questions.get(currQuestionIndex+1);
    DataElement cde = currQuestion.getDataElement();
    // Need to change the code after the prototype
    FormBuilderServiceDelegate service = getFormBuilderService();
    Collection allVdIds = new ArrayList();
    allVdIds.add(cde.getValueDomain().getVdIdseq());
    Map validValueMap1 =null;
    Map validValueMap2 = null;
    Map validValueMap3 = null;

    try {
      validValueMap1 = service.getValidValues(allVdIds);
      validValueMap2 = service.getValidValues(allVdIds);
      validValueMap3 = service.getValidValues(allVdIds);
    }
    catch (FormBuilderException exp) {
      saveError(ERROR_MODULE_RETRIEVE, request);
      saveError(exp.getErrorCode(),request);
      if (log.isErrorEnabled()) {
        log.error("Exp while getting validValue", exp);
      }
      mapping.findForward(FAILURE);
    }
    List vvList1 = (List)validValueMap1.get(cde.getValueDomain().getVdIdseq());
    List vvList2 = (List)validValueMap2.get(cde.getValueDomain().getVdIdseq());
    List vvList3 = (List)validValueMap3.get(cde.getValueDomain().getVdIdseq());
    
    ValueDomain vd = cde.getValueDomain();
    ValueDomain vd1 = new ValueDomainTransferObject();
    ValueDomain vd2 = new ValueDomainTransferObject();
    ValueDomain vd3 = new ValueDomainTransferObject();
    
    if(vvList1.size()>4)
    {

      while( vvList1.size()>2)
      {
        vvList1.remove(0);
      }
      while( vvList2.size()>3)
      {
        vvList2.remove(0);
      }      
      while( vvList3.size()>4)
      {
        vvList3.remove(0);
      }  

      vd1.setValidValues(vvList1);
      
      vd2.setValidValues(vvList2);
      
      vd3.setValidValues(vvList3);
      
      Collection subsettedVDs = new ArrayList();
      subsettedVDs.add(vd1);
      subsettedVDs.add(vd2);
      subsettedVDs.add(vd3);
      setSessionObject(request,"subsettedVDs",subsettedVDs,true);
      //request.setAttribute("subsettedVDs",subsettedVDs);

      //request.setAttribute("currentQuestion",currQuestion);
      setSessionObject(request,"currentQuestion",currQuestion,true);
      setSessionObject(request,"nextQuestion",nextQuestion,true);
      
     }
     return mapping.findForward("viewSubsets");
    }
    
  /**
   * Get all related VDs and prepare for display
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
  public ActionForward addSelectedVDSubset(    
      ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    FormBuilderBaseDynaFormBean subsetForm = (FormBuilderBaseDynaFormBean) form;
    Question currQuestion = (Question) getSessionObject(request, "currentQuestion");
    List subsettedVDs = (List)getSessionObject(request, "subsettedVDs");
    Integer vdIndex = (Integer)subsetForm.get("selectedSubsetIndex");
    
    ValueDomain selectedVD = (ValueDomain)subsettedVDs.get(vdIndex.intValue());
    
    List newValidValues = DTOTransformer.toFormValidValueList(
                    selectedVD.getValidValues(), currQuestion);
    currQuestion.setValidValues(newValidValues);               

     return mapping.findForward(MODULE_EDIT);
     //return mapping.findForward("useSubsets");
    }
  
  /**
   * This need to rewritten
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
  public ActionForward subsetSave(    
      ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
            
     return mapping.findForward("useSubsets");
    }
    
 private Map getAvailableValidValuesForQuestions(Module module,List questions, Map vdvvMap)
 {
   ListIterator questionIterator = questions.listIterator();
   Map availableVVMap = new HashMap();
   while(questionIterator.hasNext())
   {
     Question currQuestion = (Question)questionIterator.next();
     currQuestion.setModule(module);
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
            FormValidValue tempFvv = DTOTransformer.toFormValidValue(vv,currQuestion);
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
          if (log.isErrorEnabled()) {
            log.error("Exception while clonning validvalue  " + fvv,clexp);
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
   //Get deleted Questions
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
   // Get Edited and New Questions
   while(editedQuestionIterate.hasNext())
   {
     Question currQuestion = (Question)editedQuestionIterate.next();
     currQuestion.setModule(currModule);
     //Get New Questions
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
     else// Get edited Questions
     {
       int orgQuestionIndex= orgQuestionList.indexOf(currQuestion);
       Question orgQuestion = (Question)orgQuestionList.get(orgQuestionIndex);
       DataElement orgQusetionDE = orgQuestion.getDataElement();
       DataElement currQusetionDE = currQuestion.getDataElement();
       boolean hasDEAssosiationChanged = true;
       //Check If DE association has Changed
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
       //  if the LongName or DE Association or
       // DisplayOrder has been updated add the Question to Edited List.
       if(!orgQuestion.getLongName().equals(currQuestion.getLongName())||
         orgQuestion.getDisplayOrder()!=currQuestion.getDisplayOrder()||
         hasDEAssosiationChanged)
       {
         updatedQuestionList.add(currQuestion);
       }

     // Check if there is change in ValidValues
     Map validValuesChanges = null;
     //Get ValidValue changes when there is no DE association Change
     if(!hasDEAssosiationChanged)
      {
         validValuesChanges =
            getNewDeletedUpdatedValidValues(currQuestion,orgQuestion.getValidValues(),
                                            currQuestion.getValidValues());
      }
      //Get ValidValue changes when there is a DE association Change
      else if(hasDEAssosiationChanged)
      {

        List newVVList = currQuestion.getValidValues();
        List oldVVList = orgQuestion.getValidValues();
        if(oldVVList!=null&&!oldVVList.isEmpty())
        {
          if(validValuesChanges==null)
            validValuesChanges = new HashMap();
          validValuesChanges.put(this.DELETED_VV_LIST,oldVVList);
        }  
        if(newVVList!=null&&!newVVList.isEmpty())
        {
          if(validValuesChanges==null)
            validValuesChanges = new HashMap();
          setAttributesForNewFormValidValues(currQuestion,newVVList);
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
       ListIterator orgIterator =  null;
       if(orgValidValueList!=null)
          orgIterator = orgValidValueList.listIterator();
       List deletedValidValueList = new ArrayList();
       List newVVList = new ArrayList();
       List updatedVVList = new ArrayList();
       // get the deleted Valid Values first
       while(orgIterator!=null&&orgIterator.hasNext())
       {
          FormValidValue currVV = (FormValidValue)orgIterator.next();
          currVV.setQuestion(currQuestion);
          if(editedValidValueList!=null&&!editedValidValueList.contains(currVV))
          {
            deletedValidValueList.add(currVV);
          }
          if(editedValidValueList==null)
          {
            deletedValidValueList.add(currVV);
          }
       }

       ListIterator editedIterator =  null;
       if(editedValidValueList!=null)
        editedIterator = editedValidValueList.listIterator();
       while(editedIterator!=null&&editedIterator.hasNext())
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
   * Get all the changes to Instructions
   *  
   * @param orgQuestionsList
   * @param editedQuestionList
   *
   * @return the TO Containing the Changes 
   */
 private InstructionChanges getUpdatedNewDeletedInstructions(Module currModule, Module orgModule)
 {
  InstructionChanges changes =  new InstructionChangesTransferObject();
  
  Map modChanges = getKeyValueForInstructionChanges(changes.getModuleInstructionChanges(),orgModule.getInstruction(),currModule.getInstruction(),orgModule.getModuleIdseq());
  changes.setModuleInstructionChanges(modChanges);
  List currQuestions = currModule.getQuestions();
  List orgQuestions = orgModule.getQuestions();
  if(currQuestions!=null&&!currQuestions.isEmpty())
  {
    ListIterator currQuestionsIt = currQuestions.listIterator();
    while(currQuestionsIt.hasNext())
    {
      Question currQuestion = (Question)currQuestionsIt.next();
      if(orgQuestions!=null&&orgQuestions.contains(currQuestion))
      {
        int index = orgQuestions.indexOf(currQuestion);
        Question orgQuestion = (Question)orgQuestions.get(index);
        
        Map quesChanges = getKeyValueForInstructionChanges(changes.getQuestionInstructionChanges(),orgQuestion.getInstruction(),currQuestion.getInstruction(),currQuestion.getQuesIdseq());
        changes.setQuestionInstructionChanges(quesChanges);
        
        List vvOrgList = orgQuestion.getValidValues();
        List vvCurrList = currQuestion.getValidValues();
        ListIterator currVVIt = vvCurrList.listIterator();
        while(currVVIt.hasNext())
        {
          FormValidValue currVV = (FormValidValue)currVVIt.next();
          if(vvOrgList!=null&&vvOrgList.contains(currVV))
          {
            int vvindex = vvOrgList.indexOf(currVV);
            FormValidValue orgVV = (FormValidValue)vvOrgList.get(vvindex);
            Map vvChanges = getKeyValueForInstructionChanges(changes.getValidValueInstructionChanges(),orgVV.getInstruction(),currVV.getInstruction(),currVV.getValueIdseq());            
            changes.setValidValueInstructionChanges(vvChanges);
          }
        }
      }      
    }
  }
  
  return changes;
 }
 
 private Map getKeyValueForInstructionChanges(Map mainMap, Instruction orgInstr,Instruction currInstr, String parentId)
 {   
  if(mainMap==null)
       mainMap = new HashMap(); 
   if(currInstr==null&&orgInstr!=null)
   {
     List toDelete = (List)mainMap.get(InstructionChanges.DELETED_INSTRUCTIONS);
     if(toDelete==null)
     {
        toDelete = new ArrayList();       
     }
     toDelete.add(orgInstr);
     mainMap.put(InstructionChanges.DELETED_INSTRUCTIONS,toDelete);
     return mainMap;
   }
   if(currInstr!=null&&orgInstr==null)
   {
     if (!currInstr.getLongName().trim().equals(""))
     {
       Map newInstrs = (Map)mainMap.get(InstructionChanges.NEW_INSTRUCTION_MAP);
       if(newInstrs==null)
       {
          newInstrs = new HashMap();       
       }
       newInstrs.put(parentId,currInstr);
       mainMap.put(InstructionChanges.NEW_INSTRUCTION_MAP,newInstrs);
       return mainMap;     
     }
   }
   if(currInstr!=null&&orgInstr!=null)
   {
     if(!currInstr.equals(orgInstr)&&!currInstr.getLongName().trim().equals(""))
       {
         List updatedInstrs = (List)mainMap.get(InstructionChanges.UPDATED_INSTRUCTIONS);
         if(updatedInstrs==null)
         {
            updatedInstrs = new ArrayList();       
         }
         updatedInstrs.add(currInstr);
         mainMap.put(InstructionChanges.UPDATED_INSTRUCTIONS,updatedInstrs);
         return mainMap;     
       }
       else if(!currInstr.equals(orgInstr)&&currInstr.getLongName().trim().equals(""))
       {
         List toDelete = (List)mainMap.get(InstructionChanges.DELETED_INSTRUCTIONS);
         if(toDelete==null)
         {
            toDelete = new ArrayList();       
         }
         toDelete.add(orgInstr);
         mainMap.put(InstructionChanges.DELETED_INSTRUCTIONS,toDelete);
         return mainMap;       
       }
   }   
   return mainMap;
 }
  /**
   * Removes the Question given by "quesIdSeq" from the question list
   *
   * @param questionIdSeq
   * @param modules
   *
   * @return the removed module
   */
  private void setAttributesForNewFormValidValues(
    Question question,
    List fvvs) {
    if(fvvs==null) return;

    ListIterator iterate = fvvs.listIterator();

    while (iterate.hasNext()) {
      FormValidValue fvv = (FormValidValue) iterate.next();
      fvv.setQuestion(question);
      fvv.setVersion(new Float(1.0));//BugFix 1058
      }
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
   * TODO REmove  not used skakkodi
   * Gets the module given by "moduleIdSeq" from the module list
   *
   * @param moduleIdSeq
   * @param modules
   *
   * @return the  module else returns null;

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
*/
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
      currQuestion.setContext(module.getContext());//This is done so that all new Element created will have this Context
      currQuestion.setLongName(questionStr);
    }
  }

  private String[] getQuestionInstructionsAsArray(List questions) {
    if (questions == null) {
      return null;
    }

    ListIterator iterate = questions.listIterator();
    String[] questionInstructionsArr = new String[questions.size()];

    while (iterate.hasNext()) {
      int index = iterate.nextIndex();
      Question question = (Question) iterate.next();
      Instruction instr = question.getInstruction();
      if(instr!=null)
      {
        questionInstructionsArr[index] = instr.getLongName();
      }
      else
      {
        questionInstructionsArr[index] = "";
      }
    }
    return questionInstructionsArr;
  }

  private void setQuestionInstructionsFromArray(
    Module module,
    String[] questionInstructionArr) {
    List questions = module.getQuestions();
    if(questions==null) 
    {
      questions = new ArrayList();
    }
    

    for (int i = 0; i < questionInstructionArr.length; i++) {
      String instrStr = questionInstructionArr[i];
      Question currQuestion = (Question) questions.get(i);
      if(currQuestion.getInstruction()!=null)
      {
        if(instrStr!=null&&!instrStr.trim().equals(""))
        {
          currQuestion.getInstruction().setLongName(instrStr);
        }
        else
        {
          currQuestion.setInstruction(null);
        }
      }
      else if((instrStr!=null)&&(!instrStr.equals("")))
      {
        Instruction instr = new InstructionTransferObject();
        instr.setLongName(instrStr);
        instr.setDisplayOrder(0);
        instr.setVersion(new Float(1));
        instr.setAslName("DRAFT NEW");
        instr.setContext(module.getContext());        
        instr.setPreferredDefinition(instrStr);        
        currQuestion.setInstruction(instr);
      }
    }
  }
  
  private String[] getValidValueInstructionsAsArray(List questions) {
    if (questions == null) {
      return null;
    }

    ListIterator iterate = questions.listIterator();
    String[] validValueInstructionsArr = new String[getMaxVVSize(questions)];
     int vvIndex = 0;
    while (iterate.hasNext()) {
      int index = iterate.nextIndex();
      Question question = (Question) iterate.next();
      if(question!=null&&question.getValidValues()!=null)
      {
        List vvList = question.getValidValues();
        ListIterator vvIterate = vvList.listIterator();
        while(vvIterate.hasNext())
        {
          FormValidValue vv = (FormValidValue) vvIterate.next();
          Instruction instr = vv.getInstruction();
          if(instr!=null)
          {
            validValueInstructionsArr[vvIndex] = instr.getLongName();
          }
          else
          {
            validValueInstructionsArr[vvIndex] = "";
          } 
         ++vvIndex;
        }
       }
      }
    return validValueInstructionsArr;
  }

  private void setValidValueInstructionsFromArray(
    Module module,
    String[] validValueInstructionArr) {
    List questions = module.getQuestions();
    if(questions==null) 
    {
      questions = new ArrayList();
    }    
    int index=0;
    for (int i = 0; i < questions.size(); i++) {
        Question currQuestion = (Question) questions.get(i);  
        List vvList = currQuestion.getValidValues();
        if(vvList==null) 
        {
          vvList = new ArrayList();
        }            
       for (int j = 0; j < vvList.size(); j++) {  
            String instrStr = validValueInstructionArr[index];
            FormValidValue currVV = (FormValidValue) vvList.get(j); 
            if(currVV.getInstruction()!=null)
            {
              if(instrStr!=null&&!instrStr.trim().equals(""))
              {
                currVV.getInstruction().setLongName(instrStr);
              }
              else
              {
                currVV.setInstruction(null);
              }              
            }
            else if((instrStr!=null)&&(!instrStr.equals("")))
            {
              Instruction instr = new InstructionTransferObject();
              instr.setLongName(instrStr);
              instr.setDisplayOrder(0);
              instr.setVersion(new Float(1));
              instr.setAslName("DRAFT NEW");    
              instr.setContext(module.getContext());              
              instr.setPreferredDefinition(instrStr);                     
              currVV.setInstruction(instr);
            }
          ++index;
       }
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
  
  private int getMaxVVSize(List questions)
  {
    if(questions==null)
      return 0;
    int maxSize = 0;
    ListIterator iterate = questions.listIterator();
    while (iterate.hasNext()) {
      Question question = (Question) iterate.next();
      if(question!=null&&question.getValidValues()!=null)
      {
        List vvList = question.getValidValues();
        if(vvList!=null)
          maxSize=maxSize+vvList.size();
       }
      }    
    return maxSize;
  }
  

}
