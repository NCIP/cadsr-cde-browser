package gov.nih.nci.ncicb.cadsr.dto;
import java.util.Date;

public interface GenericTransferObject 
{
  public String getString(String key);
  public void setString(String key, String value);
  
  public Float getFloat(String key);
  public void setFloat(String key, Float value);
  
  public Date getDate(String key);
  public void setDate(String key, Date value);
  
}