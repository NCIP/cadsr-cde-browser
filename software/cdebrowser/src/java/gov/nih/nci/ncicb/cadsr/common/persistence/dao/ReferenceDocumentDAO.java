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

import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.resource.Question;

import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;
import java.util.Collection;


public interface ReferenceDocumentDAO extends AdminComponentDAO{

  /**
   * Creates a new reference document (just the header info).
   *
   * @param <b>newRefDoc</b> ReferenceDocument object
   *
   * @return <b>newQuestion</b> returns ReferenceDocument object
   *
   * @throws <b>DMLException</b>
   */
  public ReferenceDocument createReferenceDoc(ReferenceDocument newRefDoc, String acIdSeq)
    throws DMLException;

  public int deleteReferenceDocument(String refDocId) throws DMLException ;
  public int updateReferenceDocument(ReferenceDocument refDoc) throws DMLException;
  public int deleteAttachment(String name) throws DMLException ;


}
