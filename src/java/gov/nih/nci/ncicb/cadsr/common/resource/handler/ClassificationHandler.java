 /**
   * Get all the classifications for a data element
   *
   * @author Amit kochar
   * @version 1.0 (8/9/2002)
   */

package gov.nih.nci.ncicb.cadsr.common.resource.handler;

import java.util.Vector;

import oracle.cle.persistence.HandlerDefinition;
import gov.nih.nci.ncicb.cadsr.common.util.PageIterator;

public interface ClassificationHandler extends HandlerDefinition{

  public Vector getClassificationSchemes(Object aDeIdseq, Object sessionId)
    throws Exception;

  public Vector getClassificationSchemes(Object aDeIdseq
                                         ,PageIterator pgIter
                                         ,Object sessionId)
                          throws Exception;
}