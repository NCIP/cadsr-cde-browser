package gov.nih.nci.ncicb.cadsr.resource;
import java.util.List;

public interface ObjectClassRelationship extends AdminComponent
{
  public void setProjects(List Projects);
  public List getProjects();
}