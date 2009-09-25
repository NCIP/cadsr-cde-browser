package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.exception.NestedRuntimeException;

public class ConnectionException extends NestedRuntimeException {
  public ConnectionException(String msg) {
    super(msg);
  }

  public ConnectionException(String msg, Exception ex) {
    super(msg,ex);
  }
}