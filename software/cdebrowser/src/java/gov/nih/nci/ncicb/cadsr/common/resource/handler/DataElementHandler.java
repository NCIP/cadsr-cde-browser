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

package gov.nih.nci.ncicb.cadsr.common.resource.handler;

import gov.nih.nci.ncicb.cadsr.common.cdebrowser.userexception.*;
import gov.nih.nci.ncicb.cadsr.common.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.common.util.PageIterator;

import oracle.cle.persistence.HandlerDefinition;

import java.util.List;


public interface DataElementHandler extends HandlerDefinition {
  public List findDataElementsBasedOnQuery(
    String sqlQuery,
    Object[] queryParams,
    Object sessionId,
    PageIterator deIterator) throws Exception;

  public List findDataElementsFromQueryClause(
    String sqlQuery,
    String orderBy,
    Object sessionId,
    PageIterator deIterator) throws Exception;

  public List findDataElementIdsFromQueryClause(
    String sqlQuery,
    String orderByClause,
    Object sessionId) throws Exception;
    
  public DataElement findDataElementsByPublicId(
    int cdeId,
    float version,
    Object sessionId) throws DataElementNotFoundException, Exception;

  public List getAllFormUsages(
    Object deIdseq,
    Object sessionId,
    PageIterator deIterator) throws Exception;

  public List getAllFormUsages(
    Object deIdseq,
    Object sessionId
    ) throws Exception;    
}
