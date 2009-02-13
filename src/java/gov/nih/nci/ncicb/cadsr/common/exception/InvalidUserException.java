package gov.nih.nci.ncicb.cadsr.common.exception;

public class InvalidUserException extends NestedRuntimeException 
{
  public InvalidUserException(String msg)
  {
    super(msg);
  }
}