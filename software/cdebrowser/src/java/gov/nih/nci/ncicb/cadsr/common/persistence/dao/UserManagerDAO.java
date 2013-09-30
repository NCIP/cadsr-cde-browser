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
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface UserManagerDAO {
  public boolean userInGroup(
    String username,
    String groupName);

  public boolean validUser(
    String userName,
    String password);

  public NCIUser getNCIUser(String userName);

  public Map getContextsForAllRoles(
    String username,
    String acType);
}
