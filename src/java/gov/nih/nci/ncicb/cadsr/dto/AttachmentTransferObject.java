package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.Attachment;

public class AttachmentTransferObject implements Attachment
{
  private String name;
  
  public AttachmentTransferObject()
  {
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }
}