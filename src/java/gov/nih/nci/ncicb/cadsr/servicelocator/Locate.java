package gov.nih.nci.ncicb.cadsr.servicelocator;

public interface Locate 
{
  public ServiceLocator getServiceLocator();

  public void setServiceLocator(ServiceLocator newServiceLocator);
}