package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ReferenceDocumentTypeDAO;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public abstract class AbstractDAOFactory implements PersistenceConstants {
  private static Map cache = Collections.synchronizedMap(new HashMap());
  protected ServiceLocator serviceLocator;

  public AbstractDAOFactory() {
  }

  public abstract FormDAO getFormDAO();
  
  public abstract AdminComponentDAO getAdminComponentDAO();

  public abstract ClassificationSchemeDAO getClassificationSchemeDAO();
  
  public abstract ModuleDAO getModuleDAO();

  public abstract QuestionDAO getQuestionDAO();
  
  public abstract QuestionRepititionDAO getQuestionRepititionDAO();

  public abstract FormValidValueDAO getFormValidValueDAO();

  public abstract UserManagerDAO getUserManagerDAO();

  public abstract ContextDAO getContextDAO();

  public abstract FormCategoryDAO getFormCategoryDAO();

  public abstract WorkFlowStatusDAO getWorkFlowStatusDAO();

  public abstract FormInstructionDAO getFormInstructionDAO ();

  public abstract ModuleInstructionDAO getModuleInstructionDAO ();

  public abstract QuestionInstructionDAO getQuestionInstructionDAO ();

  public abstract FormValidValueInstructionDAO getFormValidValueInstructionDAO ();

  public abstract CDECartDAO getCDECartDAO ();
  
  public abstract ValueDomainDAO getValueDomainDAO ();
  
  public abstract DerivedDataElementDAO getDerivedDataElementDAO ();
  
  public abstract ConceptDAO getConceptDAO ();
   
  public abstract ReferenceDocumentDAO getReferenceDocumentDAO ();
  
  public abstract UtilDAO getUtilDAO();

  public abstract ReferenceDocumentTypeDAO getReferenceDocumentTypeDAO ();
   
  public abstract ProtocolDAO getProtocolDAO ();
  
  public abstract TriggerActionDAO getTriggerActionDAO ();

  public static AbstractDAOFactory getDAOFactory(ServiceLocator locator)
    throws DAOCreateException {
    AbstractDAOFactory factory = null;
    String daoFactoryClassName = locator.getString(DAO_FACTORY_CLASS_KEY);

    factory = getDAOFactory(daoFactoryClassName);
    factory.setServiceLocator(locator);

    return factory;
  }

  public static AbstractDAOFactory getDAOFactory(String daoFactoryClassName)
    throws DAOCreateException {
    AbstractDAOFactory factory = null;

    factory = (AbstractDAOFactory) cache.get(daoFactoryClassName);

    if (factory == null) {
      try {
        Class factoryClass = Class.forName(daoFactoryClassName);
        factory = (AbstractDAOFactory) factoryClass.newInstance();
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

  public void setServiceLocator(ServiceLocator newServiceLocator) {
    serviceLocator = newServiceLocator;
  }

  public ServiceLocator getServiceLocator() {
    return serviceLocator;
  }
}
