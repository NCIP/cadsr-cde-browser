package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;

import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;
import java.util.Map;
import javax.servlet.jsp.PageContext;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FormModuleEditAction extends FormEditAction {
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
  public ActionForward getModuleToEdit(
    ActionMapping mapping,
    ActionForm editForm,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    Form crf = null;
    DynaActionForm moduleEditForm = (DynaActionForm) editForm;
    Integer moduleIndex = (Integer) moduleEditForm.get(MODULE_INDEX);
    crf = (Form) getSessionObject(request, CRF);
    List modules = crf.getModules();
    Module selectedModule = (Module)modules.get(moduleIndex.intValue());
    String[] questionArr = getQuestionsAsArray(selectedModule.getQuestions());
    moduleEditForm.set(MODULE_LONG_NAME,selectedModule.getLongName());
    moduleEditForm.set(MODULE_INSTRUCTION_LONG_NAME,selectedModule.getLongName());
    moduleEditForm.set(MODULE_QUESTIONS,questionArr);
    setSessionObject(request, MODULE,selectedModule);
    FormBuilderServiceDelegate service = getFormBuilderService();
    Collection allVdIds = getAllVDsForQuestions(selectedModule.getQuestions());
    Map validValueMap = null; 
    try
    {
      validValueMap = service.getValidValule(allVdIds);
    }
    catch(FormBuilderException exp)
    {
       if (log.isDebugEnabled()) {
        log.debug("Exp while getting validValue"+exp);
      }
    }
    
    setSessionObject(request, VALUE_DOMAIN_VALID_VALUES_MAP,validValueMap);
    
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
    setQuestionsFromArray(module,questionArr);
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
    moduleEditForm.set(MODULE_QUESTIONS,questionArr);
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
    setQuestionsFromArray(module,questionArr);
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
    moduleEditForm.set(MODULE_QUESTIONS,questionArr);
    if (log.isDebugEnabled()) {
      log.info("Move Down Question ");
    }

    return mapping.findForward(MODULE_EDIT);
  }  

  /**
   * Deletes the Question of specified index and adds the Module to deleted list.
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
    setQuestionsFromArray(module,questionArr);
    
    if (deletedQuestions == null) {
      deletedQuestions = new ArrayList();
    }

    List questions = module.getQuestions();

    if ((questions != null) && (questions.size() > 0)) {

      Question deletedQuestion = (Question) questions.remove(questionIndex.intValue());
      decrementDisplayOrder(questions, questionIndex.intValue());
      deletedQuestions.add(deletedQuestion);
    }

    setSessionObject(request, DELETED_QUESTIONS, deletedQuestions);
    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS,questionArr);    

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
    setQuestionsFromArray(module,questionArr);
    List questions = module.getQuestions();

    if ((questions != null) && (questionIndex != null) && (deletedQuestions != null)) {
      Question questionToAdd =
        removeQuestionFromList(addDeletedQuestionIdSeq, deletedQuestions);

      if ((questionIndex.intValue() < questions.size()) && (questionToAdd != null)) {
        Question currQuestion = (Question) questions.get(questionIndex.intValue());
        int displayOrder = currQuestion.getDisplayOrder();
        incrementDisplayOrder(questions, questionIndex.intValue());
        questionToAdd.setDisplayOrder(displayOrder);
        questions.add((questionIndex.intValue()), questionToAdd);
      }
      else {
        int newDisplayOrder = 0;

        if (questionIndex.intValue() != 0) {
          Question lastQuestion = (Question) questions.get(questions.size() - 1);
          newDisplayOrder = lastQuestion.getDisplayOrder() + 1;
        }

        questionToAdd.setDisplayOrder(newDisplayOrder);
        questions.add(questionToAdd);
      }
    }
    setSessionObject(request, DELETED_QUESTIONS, deletedQuestions);
    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS,questionArr);  
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
    String[] questionArr = (String[])moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module,questionArr);
    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);
    List validValues =  currQuestion.getValidValues();

    if ((validValues != null) && (validValues.size() > 1)) {
      FormValidValue currValidValue = (FormValidValue) validValues.get(currValidValueIndex);
      FormValidValue prvValidValue = (FormValidValue) validValues.get(currValidValueIndex - 1);
      int currValidValueDisplayOrder = currValidValue.getDisplayOrder();
      currValidValue.setDisplayOrder(prvValidValue.getDisplayOrder());
      prvValidValue.setDisplayOrder(currValidValueDisplayOrder);
      validValues.remove(currValidValueIndex);
      validValues.add(currValidValueIndex - 1, currValidValue);
    }
    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS,questionArr);
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
    setQuestionsFromArray(module,questionArr);
    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);
    List validValues =  currQuestion.getValidValues();

    if ((validValues != null) && (validValues.size() > 1)) {
      FormValidValue currValidValue = (FormValidValue) validValues.get(currValidValueIndex);
      FormValidValue nextValidValue = (FormValidValue) validValues.get(currValidValueIndex + 1);
      int currValidValueDisplayOrder = currValidValue.getDisplayOrder();
      currValidValue.setDisplayOrder(nextValidValue.getDisplayOrder());
      nextValidValue.setDisplayOrder(currValidValueDisplayOrder);
      validValues.remove(currValidValueIndex);
      validValues.add(currValidValueIndex + 1, currValidValue);
    }
    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS,questionArr);
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
    setQuestionsFromArray(module,questionArr);

    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);
    List validValues =  currQuestion.getValidValues();
    if ((validValues != null) && (validValues.size() > 0)) {

      FormValidValue deletedValidValue = (FormValidValue) validValues.remove(currValidValueIndex);
      decrementDisplayOrder(validValues, currValidValueIndex);
    }

    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS,questionArr);    

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

    String addAvailableValidValueIdSeq =
      (String) moduleEditForm.get(ADD_AVAILABLE_VALID_VALUE);

    Map availbleValidValues = (Map) getSessionObject(request, AVAILABLE_VALID_VALUES);
    
    String[] questionArr = (String[]) moduleEditForm.get(MODULE_QUESTIONS);
    setQuestionsFromArray(module,questionArr);
    
    List questions = module.getQuestions();
    Question currQuestion = (Question) questions.get(currQuestionIndex);
    DataElement de = currQuestion.getDataElement();
    String currDEValueDomainIdSeq = null;
    if(de!=null)
    {
      ValueDomain vd = de.getValueDomain();
      if(vd!=null)
        currDEValueDomainIdSeq=vd.getVdIdseq();
    }
    
    List validValues =  currQuestion.getValidValues();

    if ((currDEValueDomainIdSeq != null) && 
         (validValueIndex != null) &&
             (availbleValidValues != null)) {
      FormValidValue formValidValueToAdd =
        removeValidValueFromMap(addAvailableValidValueIdSeq,currDEValueDomainIdSeq, availbleValidValues);

      if ((currValidValueIndex < validValues.size()) && (formValidValueToAdd != null)) {
        FormValidValue currValidValue = (FormValidValue) validValues.get(currValidValueIndex);
        int displayOrder = currValidValue.getDisplayOrder();
        incrementDisplayOrder(validValues, currValidValueIndex);
        formValidValueToAdd.setDisplayOrder(displayOrder);
        validValues.add(currValidValueIndex, formValidValueToAdd);
      }
      else {
        int newDisplayOrder = 0;

        if (currValidValueIndex != 0) {
          FormValidValue lastValidValue = (FormValidValue) validValues.get(validValues.size() - 1);
          newDisplayOrder = lastValidValue.getDisplayOrder() + 1;
        }
        formValidValueToAdd.setDisplayOrder(newDisplayOrder);
        validValues.add(formValidValueToAdd);
      }
    }
    questionArr = getQuestionsAsArray(module.getQuestions());
    moduleEditForm.set(MODULE_QUESTIONS,questionArr);  
    return mapping.findForward(MODULE_EDIT);
  }  
    

  /**
   * Removes the ValidValue given by "validValueIdSeq" from the question list
   *
   * @param questionIdSeq
   * @param modules
   *
   * @return the removed module
   */
  protected FormValidValue removeValidValueFromMap(
    String validValueIdSeq,String valueDomainIdSeq,
    Map availableValidValues) {
    
    List validValues = (List)availableValidValues.get(valueDomainIdSeq);
    
    ListIterator iterate = validValues.listIterator();

    while (iterate.hasNext()) {
      FormValidValue validValue = (FormValidValue) iterate.next();
      if (validValue.getValueIdseq().equals(validValueIdSeq)) {
        iterate.remove();
        return validValue;
      }
    }

    return null;
  }  

  
  /**
   * Removes the Question given by "quesIdSeq" from the question list
   *
   * @param questionIdSeq
   * @param modules
   *
   * @return the removed module
   */
  protected Question removeQuestionFromList(
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
  protected Question getQuestionFromList(
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
  private String[] getQuestionsAsArray(List questions)
  {
    if(questions==null)
      return null;
    ListIterator iterate = questions.listIterator();
    String[] questionArr = new String[questions.size()];
    while (iterate.hasNext()) {
      int index= iterate.nextIndex();
      Question question = (Question) iterate.next();
      questionArr[index]=question.getLongName();
    }    
    return questionArr;
  }
  
  private void setQuestionsFromArray(Module module, String[] questionArr)
  {
    List questions = module.getQuestions();
    for(int i=0;i<questionArr.length;i++)
    {
      String questionStr = questionArr[i];
      Question currQuestion = (Question)questions.get(i);
      currQuestion.setLongName(questionStr);
    } 
  }  
  private Collection getAllVDsForQuestions(List questions)
  {
    ListIterator iterate = questions.listIterator();
    Collection vdIds = new ArrayList();
    while (iterate.hasNext()) {
      Question question = (Question) iterate.next();
      DataElement de = question.getDataElement();
      if(de!=null)
      {
        ValueDomain vd = de.getValueDomain();
        if(vd!=null)
        {
          if(!vdIds.contains(vd.getVdIdseq()))
            vdIds.add(vd.getVdIdseq());
        }          
      }
    }
    return vdIds;
  }
}
