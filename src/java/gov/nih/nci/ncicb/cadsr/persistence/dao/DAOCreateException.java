package gov.nih.nci.ncicb.cadsr.persistence.dao;
import gov.nih.nci.ncicb.cadsr.exception.NestedRuntimeException;

public class DAOCreateException extends NestedRuntimeException {
  public DAOCreateException(String msg) {
    super(msg);
  }

  public DAOCreateException(
    String msg,
    Throwable cause) {
    super(msg, cause);
  }
 
}