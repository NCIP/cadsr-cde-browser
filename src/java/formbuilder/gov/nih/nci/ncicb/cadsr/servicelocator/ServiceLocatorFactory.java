package gov.nih.nci.ncicb.cadsr.servicelocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ejb.ServiceLocatorImpl;



public class ServiceLocatorFactory 
{
  public ServiceLocatorFactory()
  {
  }
  public static ServiceLocator getLocator()
  {
    return new ServiceLocatorImpl();
  }
}