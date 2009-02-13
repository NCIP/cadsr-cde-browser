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