package gov.nih.nci.ncicb.cadsr.resource;
import java.io.Serializable;
import java.sql.Blob;
import org.apache.struts.upload.FormFile;

public interface Attachment extends Serializable, Audit
{
  public String getName();
  public void setName(String name);
  
  public String getMimeType();
  public void setMimeType(String mimeType);
  
  public long getDocSize();
  public void setDocSize(long docSize);
  
  public Object clone() throws CloneNotSupportedException ;
  
}