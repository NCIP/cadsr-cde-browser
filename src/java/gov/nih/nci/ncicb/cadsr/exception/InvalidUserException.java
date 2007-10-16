package gov.nih.nci.ncicb.cadsr.exception;

public class InvalidUserException extends NestedRuntimeException 
{
  public InvalidUserException(String msg)
  {
    super(msg);
  }
}