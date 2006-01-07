package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueDAO;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
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
  private String defaultValue;
  private FormValidValue  defaultValidValue;
  private boolean defaultValueChange = false;
  
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
    if(updatedQuestion==null&&instructionChanges==null&&fvvChanges==null && !defaultValueChange)
      return true;
    boolean result = true;
    if(updatedQuestion!=null)
      result = false;
    if(instructionChanges!=null&&!instructionChanges.isEmpty())
      result = false;
    if(fvvChanges!=null&&!fvvChanges.isEmpty())
      result = false;
    if (defaultValueChange)  {
        result = false;
    }
    return result;
  }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValidValue(FormValidValue defaultValidValue) {
        this.defaultValidValue = defaultValidValue;
    }

    public FormValidValue getDefaultValidValue() {
        return defaultValidValue;
    }

    public void setInstructionChanges(InstructionChanges instructionChanges) {
        this.instructionChanges = instructionChanges;
    }

    public InstructionChanges getInstructionChanges() {
        return instructionChanges;
    }

    public void setDefaultValueChange(boolean defaultValueChange) {
        this.defaultValueChange = defaultValueChange;
    }

    public boolean isDefaultValueChange() {
        return defaultValueChange;
    }
}
