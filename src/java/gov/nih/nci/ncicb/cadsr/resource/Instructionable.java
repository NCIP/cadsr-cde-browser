package gov.nih.nci.ncicb.cadsr.resource;
import java.util.List;

public interface Instructionable {
  public Instruction getInstruction();
  public void setInstruction(Instruction instrcution);
  
  public List getInstructions();
  public void setInstructions(List instrcution);
  
}