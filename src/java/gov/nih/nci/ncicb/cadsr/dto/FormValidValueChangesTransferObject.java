package gov.nih.nci.ncicb.cadsr.dto;
import java.util.List;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValueChanges;

public class FormValidValueChangesTransferObject implements FormValidValueChanges 
{

  private List newValidValues = null;
  private List updatedValidValues = null;
  private List deletedValidValues = null;
  private String questionId = null;
  
  
  public FormValidValueChangesTransferObject()
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
  
  public List getUpdatedValidValues()
  {
    return updatedValidValues;
  }

  public void setUpdatedValidValues(List fvvs)
  {
    updatedValidValues=fvvs;
  }

  public List getNewValidValues()
  {
    return newValidValues;
  }

  public void setNewValidValues(List fvvs)
  {
    newValidValues=fvvs;
  }

  public List getDeletedValidValues()
  {
    return deletedValidValues;
  }

  public void setDeletedValidValues(List fvvs)
  {
    deletedValidValues=fvvs;
  }

  public boolean isEmpty()
  {
    if(deletedValidValues==null&&updatedValidValues==null&&newValidValues==null)
    {
      return true;
    }
    boolean result = true;
    if(deletedValidValues!=null&&!deletedValidValues.isEmpty())
      result =false;
    if(updatedValidValues!=null&&!updatedValidValues.isEmpty())
      result =false;
    if(newValidValues!=null&&!newValidValues.isEmpty())
      result =false;   
    return result;
  }
}