package gov.nih.nci.ncicb.cadsr.cdebrowser.userexception;

import gov.nih.nci.ncicb.cadsr.exception.NestedCheckedException;

public class IllegalURLParametersException extends  NestedCheckedException {
  public IllegalURLParametersException(String msg) {
    super(msg);
  }

  public IllegalURLParametersException(String msg, Exception ex) {
    super(msg,ex);
  }
}