/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.exception;

public class BC4JConnectionException extends NestedCheckedException {
  public BC4JConnectionException(String msg) {
    super(msg);
  }

  public BC4JConnectionException(String msg, Exception ex) {
    super(msg, ex);
  }
}
