package gov.nih.nci.ncicb.cadsr.common.cdebrowser.userexception;

import gov.nih.nci.ncicb.cadsr.common.exception.NestedCheckedException;

public class IllegalURLParametersException extends  NestedCheckedException {
  public IllegalURLParametersException(String msg) {
    super(msg);
  }

  public IllegalURLParametersException(String msg, Exception ex) {
    super(msg,ex);
  }
}