package gov.nih.nci.ncicb.cadsr.persistence.bc4j.handler;
import oracle.cle.persistence.Handler;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.crfbuilder.exception.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.handler.QuestionHandler;

public class QuestionHandlerImpl extends Handler implements QuestionHandler  {
  public QuestionHandlerImpl() {
  }

  public Class getReferenceClass(){
    return Question.class;
  }

  public Question findQuestionByPrimaryKey(String pk, Object sessionId) 
                                          throws FormBuilderException {
    return null;
  }

  public int deleteQuestionByPrimaryKey(String pk, Object sessionId) 
                                       throws FormBuilderException {
    return 0;
  }

  public int createQuestion(Question term, Object sessionId) 
                           throws FormBuilderException {
    return 0;
  }

  public int editQuestion(Question term, Object sessionId) 
                         throws FormBuilderException {
    return 0;
  }

  public int addValidValues(FormValidValue[] values) 
                           throws FormBuilderException {
    return 0;
  }
}