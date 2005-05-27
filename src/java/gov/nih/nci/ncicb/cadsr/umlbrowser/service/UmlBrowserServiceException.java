package gov.nih.nci.ncicb.cadsr.umlbrowser.service;
import gov.nih.nci.ncicb.cadsr.exception.NestedCheckedException;

public class UmlBrowserServiceException extends NestedCheckedException
{

  public UmlBrowserServiceException (
    String msg,
    Throwable cause) {
    super(msg, cause);
  }
  
}