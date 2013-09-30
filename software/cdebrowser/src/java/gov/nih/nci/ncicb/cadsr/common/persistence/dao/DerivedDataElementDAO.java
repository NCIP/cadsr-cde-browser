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

package gov.nih.nci.ncicb.cadsr.common.persistence.dao;
import gov.nih.nci.ncicb.cadsr.common.resource.DerivedDataElement;

public interface DerivedDataElementDAO  {
  /**
   * Retrieves the derivation data element for a data element
   * @param <b>Idseq of data element</b> dataElementId
   *
   * @return <b>DerivedDataElement</b> DerivedDataElement object.
   *
   * @throws <b>DMLException</b>
   */
  public DerivedDataElement findDerivedDataElement(String dataElementId);


}