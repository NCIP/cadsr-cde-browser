/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.exception;

public class JDBCConnectionException extends NestedCheckedException {
  
  public JDBCConnectionException(String msg) {
    super(msg);
  }

  public JDBCConnectionException(String msg, Exception ex) {
    super(msg,ex);
  }
}