package gov.nih.nci.ncicb.cadsr.persistence.dao;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;


public class BaseDAO 
{

  private ServiceLocator serviceLocator;
  
   public BaseDAO()
  {
  }
  public BaseDAO(ServiceLocator locator)
  {
    serviceLocator = locator;
  }

  public ServiceLocator getServiceLocator()
  {
    return serviceLocator;
  }

  public void setServiceLocator(ServiceLocator newServiceLocator)
  {
    serviceLocator = newServiceLocator;
  }


}