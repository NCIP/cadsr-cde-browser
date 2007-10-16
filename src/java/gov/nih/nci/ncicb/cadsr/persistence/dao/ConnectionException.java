package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.NestedRuntimeException;

public class ConnectionException extends NestedRuntimeException {
  public ConnectionException(String msg) {
    super(msg);
  }

  public ConnectionException(String msg, Exception ex) {
    super(msg,ex);
  }
}