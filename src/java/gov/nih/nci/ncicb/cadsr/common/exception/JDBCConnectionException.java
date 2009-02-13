package gov.nih.nci.ncicb.cadsr.common.exception;

public class JDBCConnectionException extends NestedCheckedException {
  
  public JDBCConnectionException(String msg) {
    super(msg);
  }

  public JDBCConnectionException(String msg, Exception ex) {
    super(msg,ex);
  }
}