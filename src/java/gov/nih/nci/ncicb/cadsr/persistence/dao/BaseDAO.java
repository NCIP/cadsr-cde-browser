package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.logging.Log;


public abstract class BaseDAO {
  protected static Log log;
  private ServiceLocator serviceLocator;

  public BaseDAO() {
  }

  public BaseDAO(ServiceLocator locator) {
    serviceLocator = locator;
  }

  public ServiceLocator getServiceLocator() {
    return serviceLocator;
  }

  public void setServiceLocator(ServiceLocator newServiceLocator) {
    serviceLocator = newServiceLocator;
  }

  public AbstractDAOFactory getDAOFactory() {
    return AbstractDAOFactory.getDAOFactory(serviceLocator);
  }
}
