/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.ejb.common;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class SessionBeanAdapter implements SessionBean  {
  protected SessionContext context;
  
  public SessionBeanAdapter() {
  }
  public void ejbCreate() {
  }

  public void ejbActivate() {
  }

  public void ejbPassivate() {
  }

  public void ejbRemove() {
  }

  public void setSessionContext(SessionContext ctx) {
    context = ctx;
  }
}