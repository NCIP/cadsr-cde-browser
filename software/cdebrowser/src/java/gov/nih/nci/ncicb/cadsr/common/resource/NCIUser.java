/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface NCIUser extends Serializable
{
  public String getUsername();
  public boolean hasRoleAccess(String role, Context context);
  public Map getContextsByRole();
  public void setContextsByRole(Map contextsMap);
  public Collection getContextsByRoleAccess(String role);
  //added for GF1224
  public String getEmailAddress();
  public String getPhoneNumber();
}