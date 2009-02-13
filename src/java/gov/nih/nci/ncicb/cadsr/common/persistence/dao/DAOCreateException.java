package gov.nih.nci.ncicb.cadsr.common.persistence.dao;
import gov.nih.nci.ncicb.cadsr.common.exception.NestedRuntimeException;

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