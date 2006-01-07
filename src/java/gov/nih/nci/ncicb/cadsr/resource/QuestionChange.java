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
 
  public void setDefaultValidValue(FormValidValue defaultValidValue) ;
  public FormValidValue getDefaultValidValue() ;
  public void setDefaultValue(String  defaultValue) ;
  public String getDefaultValue() ;
    
  public void setDefaultValueChange(boolean defaultValueChange);
  public boolean isDefaultValueChange();
  
  
  public boolean isEmpty();  
}