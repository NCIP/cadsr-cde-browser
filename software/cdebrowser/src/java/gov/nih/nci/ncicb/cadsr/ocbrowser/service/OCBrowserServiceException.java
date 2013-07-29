/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.ocbrowser.service;
import gov.nih.nci.ncicb.cadsr.common.exception.NestedCheckedException;

public class OCBrowserServiceException extends NestedCheckedException
{

  public OCBrowserServiceException (
    String msg,
    Throwable cause) {
    super(msg, cause);
  }

}