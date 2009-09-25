package gov.nih.nci.ncicb.cadsr.common.cdebrowser.userexception;

import gov.nih.nci.ncicb.cadsr.common.exception.NestedCheckedException;

public class DataElementNotFoundException extends  NestedCheckedException  {
  public DataElementNotFoundException(String msg) {
    super(msg);
  }

  public DataElementNotFoundException(String msg, Exception ex) {
    super(msg,ex);
  }
}