 /**
   * Get all the valid values for a value domain in a data element
   *
   * @author Amit kochar
   * @version 1.0 (8/7/2002)
   */
package gov.nih.nci.ncicb.cadsr.common.resource.handler;
import java.util.*;
import oracle.cle.persistence.*;
import oracle.jbo.ViewObject;
import gov.nih.nci.ncicb.cadsr.common.util.PageIterator;

public interface ValidValueHandler extends HandlerDefinition{
  
  public List getValidValues(Object aVdIdseq, Object sessionId)
    throws Exception;

  public List getMyValidValues(Object aVdIdseq
                                  ,Object sessionId
                                  ,PageIterator vvIterator)
                          throws Exception;
}