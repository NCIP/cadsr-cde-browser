package gov.nih.nci.ncicb.cadsr.resource;
import java.io.Serializable;

public interface FormValidValueChange extends Serializable
{

  public String getValidValueId();
  public void setValidValueId(String vvId);
  
  public FormValidValue getUpdatedValidValue();
  public void setUpdatedValidValue(FormValidValue fvv);
  
  public InstructionChanges getInstrctionChanges();
  public void setInstrctionChanges(InstructionChanges changes);
  
  public boolean isEmpty();
}