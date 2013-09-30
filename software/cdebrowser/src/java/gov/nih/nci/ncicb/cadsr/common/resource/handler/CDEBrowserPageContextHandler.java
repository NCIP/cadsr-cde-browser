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
import java.util.*;
import oracle.cle.persistence.*;
import oracle.cle.resource.*;

public interface CDEBrowserPageContextHandler extends HandlerDefinition  {
  public Object findPageContext(String nodeType
                               ,String nodeIdseq
                               ,Object sessionId) throws Exception;
}