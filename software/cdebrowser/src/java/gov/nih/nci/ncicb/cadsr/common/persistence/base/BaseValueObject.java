/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.common.persistence.base;

public class BaseValueObject  {
  public BaseValueObject() {
  }
  /**
   * Check for null value for the given Object. If the input Object is null, an
   * empty String is returned, else toString() of the given object is returned.
   *
   * @param   inputObj an <code>Object</code>.
   * @return  the <code>String</code> value for the given Object.
   */
  protected static String checkForNull(Object inputObj) {
    if (inputObj == null) {
      return new String("");
    }
    else {
      return inputObj.toString();
    }
  }
}