package gov.nih.nci.ncicb.cadsr.resource;
import java.io.Serializable;
import java.util.Map;

public interface InstructionChanges extends Serializable
{

  public static final String DELETED_INSTRUCTIONS="deletedInstructions";
  public static final String NEW_INSTRUCTION_MAP="newInstructionMap";
  public static final String UPDATED_INSTRUCTIONS="updatedInstructions";  
  
  public void setModuleInstructionChanges(Map moduleInstructionChanges);


  public Map getModuleInstructionChanges();


  public void setValidValueInstructionChanges(Map validValueInstructionChanges);


  public Map getValidValueInstructionChanges();


  public void setQuestionInstructionChanges(Map questionInstructionChanges);


  public Map getQuestionInstructionChanges();
  
  public void setFormHeaderInstructionChanges(Map formHeaderInstructionChanges);


  public Map getFormHeaderInstructionChanges();


  public void setFormFooterInstructionChanges(Map formFooterInstructionChanges);


  public Map getFormFooterInstructionChanges();
  
  public boolean isEmpty();
}
