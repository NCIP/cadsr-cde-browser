package gov.nih.nci.ncicb.cadsr.common.exception;

public class AuthorizationException extends NestedRuntimeException 
{
  public AuthorizationException(String msg)
  {
    super(msg);
  }
}