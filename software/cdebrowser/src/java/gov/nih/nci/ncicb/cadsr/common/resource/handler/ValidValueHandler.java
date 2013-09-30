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