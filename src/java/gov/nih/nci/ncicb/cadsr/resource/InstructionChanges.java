package gov.nih.nci.ncicb.cadsr.resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface InstructionChanges extends Serializable
{

  public String getParentId();
  public void setParentId(String parentId);
  
  public Instruction getUpdatedInstruction();
  public void setUpdatedInstruction(Instruction instructions);

  public Instruction getNewInstruction();
  public void setNewInstruction(Instruction instructions);
  
  public Instruction getDeletedInstruction();
  public void setDeletedInstruction(Instruction instructions);
  
  public boolean isEmpty();
}
