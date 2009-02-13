package gov.nih.nci.ncicb.cadsr.common.dto;
import gov.nih.nci.ncicb.cadsr.common.resource.ObjectClass;
import gov.nih.nci.ncicb.cadsr.common.resource.ObjectClassRelationship;
import java.util.List;

public class ObjectClassRelationshipTransferObject extends AdminComponentTransferObject implements ObjectClassRelationship
{
  private List Projects = null;
  private ObjectClass sourceObjectClass= null;
  private ObjectClass targetObjectClass= null;  
  private ObjectClass sourceMultiplicity= null;
  private ObjectClass targetMultiplicity= null;  
  
  public ObjectClassRelationshipTransferObject()
  {
  }

  public void setProjects(List Projects)
  {
    this.Projects = Projects;
  }

  public List getProjects()
  {
    return Projects;
  }
}