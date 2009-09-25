package gov.nih.nci.ncicb.cadsr.common.dto;
import gov.nih.nci.ncicb.cadsr.common.resource.OCRPackage;
import gov.nih.nci.ncicb.cadsr.common.resource.Project;

public class OCRPackageTransferObject extends AdminComponentTransferObject implements OCRPackage
{
  private String name;
  private String id;

  public void setName(String name)
  {
    this.name = name;
  }


  public String getName()
  {
    return name;
  }


  public void setId(String id)
  {
    this.id = id;
  }


  public String getId()
  {
    return id;
  }


}