package gov.nih.nci.ncicb.cadsr.dto;
import java.util.HashMap;
import java.util.Date;

public class GenericTransferObjectImpl implements GenericTransferObject  
{
  protected HashMap stringMap = null;
  protected HashMap dateMap = null;
  protected HashMap floatMap = null;
  
  public GenericTransferObjectImpl()
  {
  }

  public String getString(String key)
  {
    if(stringMap!=null)
      return (String)stringMap.get(key);
    return null;
  }

  public void setString(String key, String value)
  {
    if(stringMap==null)
      stringMap = new HashMap();
    stringMap.put(key,value);
  }

  public Float getFloat(String key)
  {
    if(floatMap!=null)
      return (Float)floatMap.get(key);
    return null;
  }

  public void setFloat(String key, Float value)
  {
    if(floatMap==null)
      floatMap = new HashMap();
    floatMap.put(key,value);  
  }

  public Date getDate(String key)
  {
    if(dateMap!=null)
      return (Date)dateMap.get(key);
    return null;
  }

  public void setDate(String key, Date value)
  {
    if(dateMap==null)
      dateMap = new HashMap();
    dateMap.put(key,value);    
  }
}