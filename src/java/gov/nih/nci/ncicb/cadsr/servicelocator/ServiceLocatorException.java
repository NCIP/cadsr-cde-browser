package gov.nih.nci.ncicb.cadsr.servicelocator;
import gov.nih.nci.ncicb.cadsr.exception.NestedRuntimeException;

public class ServiceLocatorException extends NestedRuntimeException {
  
  public ServiceLocatorException(String msg) {
    super(msg);
  }

  public ServiceLocatorException(
    String msg,
    Throwable cause) {
    super(msg, cause);
  }
 
}
