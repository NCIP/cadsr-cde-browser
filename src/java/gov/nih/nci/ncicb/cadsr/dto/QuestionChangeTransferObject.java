package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.QuestionChange;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.InstructionChanges;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValueChanges;

public class QuestionChangeTransferObject implements QuestionChange 
{
  private Question updatedQuestion = null;
  private InstructionChanges instructionChanges = null;
  private FormValidValueChanges fvvChanges = null;
  private String questionId;
  
  public QuestionChangeTransferObject()
  {
  }

  public String getQuestionId()
  {
    return questionId;
  }
  public void setQuestionId(String questionId)
  {
    this.questionId = questionId;
  }
  
  public Question getUpdatedQuestion()
  {
    return updatedQuestion;
  }

  public void setUpdatedQuestion(Question question)
  {
    this.updatedQuestion=question;
  }

  public InstructionChanges getInstrctionChanges()
  {
    return instructionChanges;
  }

  public void setInstrctionChanges(InstructionChanges changes)
  {
    this.instructionChanges=changes;
  }

  public FormValidValueChanges getFormValidValueChanges()
  {
    return fvvChanges;
  }

  public void setFormValidValueChanges(FormValidValueChanges changes)
  {
    fvvChanges=changes;
  }

  public boolean isEmpty()
  { 
    if(updatedQuestion==null&&instructionChanges==null&&fvvChanges==null)
      return true;
    boolean result = true;
    if(updatedQuestion!=null)
      result = false;
    if(instructionChanges!=null&&!instructionChanges.isEmpty())
      result = false;
    if(fvvChanges!=null&&!fvvChanges.isEmpty())
      result = false;
    return result;
  }
}