package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.OCRPackage;
import gov.nih.nci.ncicb.cadsr.resource.Project;

public class OCRPackageTransferObject extends AdminComponentTransferObject implements OCRPackage
{
  private Project parent = null;
  
  public OCRPackageTransferObject()
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