package gov.nih.nci.ncicb.cadsr.common.servicelocator;

public interface Locate 
{
  public ServiceLocator getServiceLocator();

  public void setServiceLocator(ServiceLocator newServiceLocator);
}