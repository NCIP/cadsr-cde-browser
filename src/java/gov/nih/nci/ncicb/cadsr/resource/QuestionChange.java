package gov.nih.nci.ncicb.cadsr.resource;
import java.io.Serializable;

public interface QuestionChange extends Serializable
{

  public String getQuestionId();
  public void setQuestionId(String questionId);
  
  public Question getUpdatedQuestion();
  public void setUpdatedQuestion(Question question);
  
  public InstructionChanges getInstrctionChanges();
  public void setInstrctionChanges(InstructionChanges changes);
  
  public FormValidValueChanges getFormValidValueChanges();
  public void setFormValidValueChanges(FormValidValueChanges changes);
  
  public boolean isEmpty();  
}