package gov.nih.nci.ncicb.cadsr.persistence.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

public abstract class AbstractDAOFactory {

  private static Map cache = Collections.synchronizedMap(new HashMap());
  protected ServiceLocator serviceLocator;

  public AbstractDAOFactory() {
  }

  public abstract FormDAO getFormDAO();

  public abstract ModuleDAO getModuleDAO();

  public abstract QuestionDAO getQuestionDAO();

  public abstract FormValidValueDAO getFormValidValueDAO();


  public static AbstractDAOFactory getDAOFactory(ServiceLocator locator) throws DAOCreateException {
    AbstractDAOFactory factory = null;

    String daoFactoryClassName =
      locator.getString(locator.DAO_FACTORY_CLASS_KEY);
    factory = (AbstractDAOFactory) cache.get(daoFactoryClassName);

    if (factory == null) {
      try {
        Class factoryClass = Class.forName(daoFactoryClassName);
        factory = (AbstractDAOFactory) factoryClass.newInstance();
        factory.setServiceLocator(locator);
        cache.put(factory.getClass().getName(), factory);
      }
      catch (Exception ex) {
        ex.printStackTrace();
        throw new DAOCreateException(
          "Unable to create specified DAOFactory implementation", ex);
      }
    }

    return factory;
  }
  

  public void setServiceLocator(ServiceLocator newServiceLocator)
  {
    serviceLocator = newServiceLocator;
  }

  public ServiceLocator getServiceLocator()
  {
    return serviceLocator;
  }
}
