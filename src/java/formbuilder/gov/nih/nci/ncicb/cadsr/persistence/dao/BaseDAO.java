package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.security.oc4j.BaseUserManager;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class BaseDAO {
  protected static Log log = LogFactory.getLog(BaseUserManager.class.getName());
  private ServiceLocator serviceLocator;

  public BaseDAO() {
  }

  public BaseDAO(ServiceLocator locator) {
    serviceLocator = locator;
  }

  public ServiceLocator getServiceLocator() {
    if (log.isDebugEnabled()) {
      log.debug("getServiceLocator() =" + serviceLocator);
    }

    return serviceLocator;
  }

  public void setServiceLocator(ServiceLocator newServiceLocator) {
    serviceLocator = newServiceLocator;
  }

  /**
   * Checks if a user can update an admin component.
   */
  public abstract boolean hasUpdate(
    String username,
    String acIdseq);

  /**
   * Checks if a user can delete an admin component.
   */
  public abstract boolean hasDelete(
    String username,
    String acIdseq);

  /**
   * Checks if a user can create a particular type of an admin component in a 
   */
  public abstract boolean hasCreate(
    String username,
    String acType,
    String conteIdseq);
}
