package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.Context;

public class ContextTransferObject extends AdminComponentTransferObject implements Context 
{
  private String name;
  
  public ContextTransferObject(String newName)
  {
    name=newName;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String newName)
  {
    name=newName;
  }

  public String getLlName()
  {
    return null;
  }

  public void setLlName(String p0)
  {
  }

  public String getPalName()
  {
    return null;
  }

  public void setPalName(String p0)
  {
  }

  public String getDescription()
  {
    return null;
  }

  public void setDescription(String p0)
  {
  }

  public String getLanguage()
  {
    return null;
  }

  public void setLanguage(String p0)
  {
  }
}