package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.InstructionChanges;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.ModuleChanges;
import java.util.List;

public class ModuleChangesTransferObject implements ModuleChanges
{
  private List newQuestions = null;
  private List updatedQuestions = null;
  private List deletedQuestions = null;
  private Module updatedModule = null;
  private InstructionChanges instructionChanges= null;
  private String moduleId = null;
  
  public ModuleChangesTransferObject()
  {
  }
  
  public String getModuleId()
  {
    return moduleId;
  }
  public void setModuleId(String moduleId)
  {
    this.moduleId = moduleId;
  }
  
  public List getNewQuestions()
  {
    return newQuestions;
  }
  public void setNewQuestions(List newQuestions)
  {
    this.newQuestions=newQuestions;
  }
  
  public List getUpdatedQuestions()
  {
    return updatedQuestions;
  }
  public void setUpdatedQuestions(List updatedQuestions)
  {
    this.updatedQuestions=updatedQuestions;
  }
  
  public List getDeletedQuestions()
  {
    return deletedQuestions;
  }
  public void setDeletedQuestions(List deletedQuestions)
  {
    this.deletedQuestions=deletedQuestions;
  }
  
  public boolean isEmpty()
  {
    if(deletedQuestions==null&&updatedQuestions==null&&newQuestions==null
        &&instructionChanges==null&&updatedModule==null)
    {
      return true;
    }
    boolean result = true;
    if(deletedQuestions!=null&&!deletedQuestions.isEmpty())
      result =false;
    if(updatedQuestions!=null&&!updatedQuestions.isEmpty())
      result =false;
    if(newQuestions!=null&&!newQuestions.isEmpty())
      result =false;   
    if(instructionChanges!=null&&!instructionChanges.isEmpty())
      result =false;   
    if(updatedModule!=null)
      result =false;         
    return result;
  }

  public InstructionChanges getInstructionChanges()
  {
    return instructionChanges;
  }

  public void setInstructionChanges(InstructionChanges instructionChanges)
  {
    this.instructionChanges = instructionChanges;
  }

  public Module getUpdatedModule()
  {
    return updatedModule;
  }

  public void setUpdatedModule(Module updatedModule)
  {
    this.updatedModule = updatedModule;
  }
}