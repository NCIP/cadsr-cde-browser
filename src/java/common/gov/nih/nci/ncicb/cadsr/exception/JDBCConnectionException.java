package gov.nih.nci.ncicb.cadsr.exception;

public class JDBCConnectionException extends NestedCheckedException {
  
  public JDBCConnectionException(String msg) {
    super(msg);
  }

  public JDBCConnectionException(String msg, Exception ex) {
    super(msg,ex);
  }
}