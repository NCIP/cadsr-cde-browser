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
