package gov.nih.nci.ncicb.cadsr.util;

/**
 * Overloads the appends method of StringBuffer  
 * 
 */
public class DebugStringBuffer 
{
  private StringBuffer buffer = new StringBuffer();
  
  public DebugStringBuffer()
  {
  }
  public DebugStringBuffer(String str)
  {
    buffer= new StringBuffer(str);
  }

  public DebugStringBuffer append(String str)
  {
      buffer.append(str);
      return this;
  }
  
  public DebugStringBuffer append(String str,String tester)
  {
    if(tester!=null&& !tester.equals(""))
    {
      buffer.append(str);
      return this;
    }
    else
      return this;
  }

  public DebugStringBuffer append(String str,Float tester)
  {
    if(tester!=null)
    {
      buffer.append(str);
      return this;
    }
    else
      return this;
  }  
  public DebugStringBuffer append(DebugStringBuffer strBuffer,String tester)
  {
    // TODO:  Override this java.lang.StringBuffer method
    if(tester!=null&& !tester.equals(""))
    {
      buffer.append(strBuffer);
      return this;
    }
    else
      return this;
  }
  public String toString()
  {
    if(buffer!=null)
      return buffer.toString();
    else
      return null;
  }
  
}