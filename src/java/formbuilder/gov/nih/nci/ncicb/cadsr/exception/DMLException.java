package gov.nih.nci.ncicb.cadsr.exception;

import gov.nih.nci.ncicb.cadsr.exception.NestedCheckedException;

public class DMLException extends NestedCheckedException {
  public DMLException(String msg) {
    super(msg);
  }

  public DMLException(
    String msg,
    Throwable cause) {
    super(msg, cause);
  }

 /** public DataAccessException(Throwable cause) {
    super(cause);
  }
  **/
}