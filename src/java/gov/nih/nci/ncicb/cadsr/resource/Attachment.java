package gov.nih.nci.ncicb.cadsr.resource;
import java.io.Serializable;

public interface Attachment extends Serializable
{
  public String getName();
  public void setName(String name);
  
  public String getMimeType();
  public void setMimeType(String mimeType);
  
  public long getDocSize();
  public void setDocSize(long docSize);
}