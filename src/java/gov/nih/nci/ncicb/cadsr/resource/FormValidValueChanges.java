package gov.nih.nci.ncicb.cadsr.resource;
import java.io.Serializable;
import java.util.List;

public interface FormValidValueChanges extends Serializable
{
  public String getQuestionId();
  public void setQuestionId(String questionId);
  
  public List getUpdatedValidValues();
  public void setUpdatedValidValues(List fvvs);
  
  public List getNewValidValues();
  public void setNewValidValues(List fvvs);
  
  public List getDeletedValidValues();
  public void setDeletedValidValues(List fvvs);
  
  public boolean isEmpty();
}