package gov.nih.nci.ncicb.cadsr.exception;

public class FatalException extends NestedRuntimeException 
{

  public FatalException(String str, Exception ex)
  {
    super(str,ex);
  } 
    public FatalException(Throwable ex)
  {
    super(null,ex);
  } 
}