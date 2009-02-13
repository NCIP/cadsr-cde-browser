package gov.nih.nci.ncicb.cadsr.common.exception;

public class BC4JConnectionException extends NestedCheckedException {
  public BC4JConnectionException(String msg) {
    super(msg);
  }

  public BC4JConnectionException(String msg, Exception ex) {
    super(msg, ex);
  }
}
