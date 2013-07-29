/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

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