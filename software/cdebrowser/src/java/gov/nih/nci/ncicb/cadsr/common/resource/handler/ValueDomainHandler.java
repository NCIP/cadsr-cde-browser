/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.resource.handler;

import java.util.*;
import oracle.cle.persistence.*;
import oracle.cle.resource.*;

public interface ValueDomainHandler  {
  public Object findObject(Object key, Object sessionId) throws Exception;
}