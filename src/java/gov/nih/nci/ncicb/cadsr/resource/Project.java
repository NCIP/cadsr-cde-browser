package gov.nih.nci.ncicb.cadsr.resource;
import java.util.List;

public interface Project extends AdminComponent
{
  public void setParent(Project parent);
  public Project getParent();

}