package gov.nih.nci.ncicb.cadsr.resource;

import java.util.List;

import oracle.cle.persistence.HandlerDefinition;
import gov.nih.nci.ncicb.cadsr.util.PageIterator;

public interface DataElementHandler extends HandlerDefinition{
  //public Object findObject(Object key, Object sessionId) throws Exception;
  public List findDataElementsBasedOnQuery(String sqlQuery
                                          ,Object[] queryParams
                                          ,Object sessionId
                                          ,PageIterator deIterator) 
                                          throws Exception;

  public List findDataElementsFromQueryClause(String sqlQuery
                                             ,String orderBy
                                             ,Object sessionId
                                             ,PageIterator deIterator) 
                                              throws Exception;
}