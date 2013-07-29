/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.exception;

public class InvalidUserException extends NestedRuntimeException 
{
  public InvalidUserException(String msg)
  {
    super(msg);
  }
}