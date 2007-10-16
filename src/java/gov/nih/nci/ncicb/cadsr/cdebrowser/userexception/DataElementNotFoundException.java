package gov.nih.nci.ncicb.cadsr.cdebrowser.userexception;

import gov.nih.nci.ncicb.cadsr.exception.NestedCheckedException;

public class DataElementNotFoundException extends  NestedCheckedException  {
  public DataElementNotFoundException(String msg) {
    super(msg);
  }

  public DataElementNotFoundException(String msg, Exception ex) {
    super(msg,ex);
  }
}