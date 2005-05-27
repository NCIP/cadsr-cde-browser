package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Project;
import java.util.List;

public class ProjectTransferObject extends AdminComponentTransferObject implements Project
{

  private Project parent = null;

  public ProjectTransferObject()
  {
  }


  public void setParent(Project parent)
  {
    this.parent = parent;
  }


  public Project getParent()
  {
    return parent;
  }

  
}