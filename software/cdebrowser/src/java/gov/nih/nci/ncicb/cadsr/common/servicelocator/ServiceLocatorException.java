/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.servicelocator;
import gov.nih.nci.ncicb.cadsr.common.exception.NestedRuntimeException;

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
