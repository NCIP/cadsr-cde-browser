package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.InstructionChanges;
import java.util.Map;

public class InstructionChangesTransferObject implements InstructionChanges
{
  private Map moduleInstructionChanges = null;
  private Map validValueInstructionChanges = null;
  private Map questionInstructionChanges = null;
  private Map formHeaderInstructionChanges = null;
  private Map formFooterInstructionChanges = null;
  
  public InstructionChangesTransferObject()
  {
  }

  
  public boolean isEmpty()
  {
    boolean qEmpty = true;
    boolean vEmpty = true;
    boolean mEmpty = true;
    boolean fhEmpty = true;
    boolean ffEmpty = true;
    
    if(questionInstructionChanges !=null && ! questionInstructionChanges.isEmpty())
      qEmpty = false;
    if(validValueInstructionChanges !=null && ! validValueInstructionChanges.isEmpty())
      vEmpty = false;
    if(moduleInstructionChanges !=null && ! moduleInstructionChanges.isEmpty())
      mEmpty = false;  
    if(formHeaderInstructionChanges !=null && ! formHeaderInstructionChanges.isEmpty())
      fhEmpty = false;    
    if(formFooterInstructionChanges !=null && ! formFooterInstructionChanges.isEmpty())
      ffEmpty = false;          
    
    if(qEmpty==false||vEmpty==false ||mEmpty==false||fhEmpty==false||ffEmpty==false)
      return false;
      
    return true;
  }


  public void setModuleInstructionChanges(Map moduleInstructionChanges)
  {
    this.moduleInstructionChanges = moduleInstructionChanges;
  }


  public Map getModuleInstructionChanges()
  {
    return moduleInstructionChanges;
  }


  public void setValidValueInstructionChanges(Map validValueInstructionChanges)
  {
    this.validValueInstructionChanges = validValueInstructionChanges;
  }


  public Map getValidValueInstructionChanges()
  {
    return validValueInstructionChanges;
  }


  public void setQuestionInstructionChanges(Map questionInstructionChanges)
  {
    this.questionInstructionChanges = questionInstructionChanges;
  }


  public Map getQuestionInstructionChanges()
  {
    return questionInstructionChanges;
  }


  public void setFormHeaderInstructionChanges(Map formHeaderInstructionChanges)
  {
    this.formHeaderInstructionChanges = formHeaderInstructionChanges;
  }


  public Map getFormHeaderInstructionChanges()
  {
    return formHeaderInstructionChanges;
  }


  public void setFormFooterInstructionChanges(Map formFooterInstructionChanges)
  {
    this.formFooterInstructionChanges = formFooterInstructionChanges;
  }


  public Map getFormFooterInstructionChanges()
  {
    return formFooterInstructionChanges;
  }

}