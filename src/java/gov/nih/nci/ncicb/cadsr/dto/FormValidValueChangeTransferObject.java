package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.InstructionChanges;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValueChange;
import java.util.List;

public class FormValidValueChangeTransferObject implements FormValidValueChange 
{

  private FormValidValue updatedValueValue = null;
  private InstructionChanges instructionChanges = null;
  private String vvId = null;
  
  public FormValidValueChangeTransferObject()
  {
  }

  public String getValidValueId()
  {
    return vvId;
  }
  public void setValidValueId(String vvId)
  {
    this.vvId = vvId;
  }
  
  public FormValidValue getUpdatedValidValue()
  {
    return updatedValueValue;
  }

  public void setUpdatedValidValue(FormValidValue fvv)
  {
    this.updatedValueValue=fvv;
  }

  public InstructionChanges getInstrctionChanges()
  {
    return instructionChanges;
  }

  public void setInstrctionChanges(InstructionChanges changes)
  {
    instructionChanges = changes;
  }

  public boolean isEmpty()
  {
    if(updatedValueValue==null&&instructionChanges==null)
      return true;
    boolean result = true;
    if(instructionChanges!=null&&!instructionChanges.isEmpty())
      result =false;
    if(updatedValueValue!=null)
      result =false;
    return result;
  }
}