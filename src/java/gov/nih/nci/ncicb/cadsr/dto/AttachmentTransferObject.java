package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.Attachment;

public class AttachmentTransferObject extends BaseTransferObject 
implements Attachment
{
  private String name;
  private String mimeType;
  private long docSize;
  
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


  public void setMimeType(String mimeType)
  {
    this.mimeType = mimeType;
  }


  public String getMimeType()
  {
    return mimeType;
  }


  public void setDocSize(long Docsize)
  {
    this.docSize = Docsize;
  }


  public long getDocSize()
  {
    return docSize;
  }


}