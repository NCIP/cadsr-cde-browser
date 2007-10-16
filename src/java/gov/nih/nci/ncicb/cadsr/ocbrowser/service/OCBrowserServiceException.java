package gov.nih.nci.ncicb.cadsr.ocbrowser.service;
import gov.nih.nci.ncicb.cadsr.exception.NestedCheckedException;

public class OCBrowserServiceException extends NestedCheckedException
{

  public OCBrowserServiceException (
    String msg,
    Throwable cause) {
    super(msg, cause);
  }

}