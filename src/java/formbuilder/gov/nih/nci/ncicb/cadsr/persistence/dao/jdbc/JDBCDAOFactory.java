package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.DAOCreateException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormCategoryDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.WorkFlowStatusDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;


public class JDBCDAOFactory extends AbstractDAOFactory
  implements PersistenceConstants {
  private Map daoCache = Collections.synchronizedMap(new HashMap());

  public JDBCDAOFactory() {
  }

  public FormDAO getFormDAO() {
    FormDAO formDAO = (JDBCFormDAO) daoCache.get(JDBC_FORM_DAO);

    if (formDAO == null) {
      formDAO = new JDBCFormDAO(serviceLocator);
      daoCache.put(JDBC_FORM_DAO, formDAO);
    }

    return formDAO;
  }

  public FormValidValueDAO getFormValidValueDAO() {
    FormValidValueDAO vvDAO =
      (JDBCFormValidValueDAO) daoCache.get(JDBC_FORM_VALID_VALUE_DAO);

    if (vvDAO == null) {
      vvDAO = new JDBCFormValidValueDAO(serviceLocator);
      daoCache.put(JDBC_FORM_VALID_VALUE_DAO, vvDAO);
    }

    return vvDAO;
  }

  public ModuleDAO getModuleDAO() {
    ModuleDAO moduleDAO = (JDBCModuleDAO) daoCache.get(JDBC_MODULE_DAO);

    if (moduleDAO == null) {
      moduleDAO = new JDBCModuleDAO(serviceLocator);
      daoCache.put(JDBC_MODULE_DAO, moduleDAO);
    }

    return moduleDAO;
  }

  public QuestionDAO getQuestionDAO() {
    QuestionDAO myDAO = (JDBCQuestionDAO) daoCache.get(JDBC_QUESTION_DAO);

    if (myDAO == null) {
      myDAO = new JDBCQuestionDAO(serviceLocator);
      daoCache.put(JDBC_QUESTION_DAO, myDAO);
    }

    return myDAO;
  }

  public UserManagerDAO getUserManagerDAO() {
    UserManagerDAO myDAO = (JDBCUserManagerDAO) daoCache.get(JDBC_USER_MGR_DAO);

    if (myDAO == null) {
      myDAO = new JDBCUserManagerDAO(serviceLocator);
      daoCache.put(JDBC_USER_MGR_DAO, myDAO);
    }

    return myDAO;
  }

  public ContextDAO getContextDAO() {
    ContextDAO myDAO = (JDBCContextDAO) daoCache.get(JDBC_CONTEXT_DAO);

    if (myDAO == null) {
      myDAO = new JDBCContextDAO(serviceLocator);
      daoCache.put(JDBC_CONTEXT_DAO, myDAO);
    }

    return myDAO;
  }

  public FormCategoryDAO getFormCategoryDAO() {
    FormCategoryDAO myDAO =
      (JDBCFormCategoryDAO) daoCache.get(JDBC_FORM_CATEGORY_DAO);

    if (myDAO == null) {
      myDAO = new JDBCFormCategoryDAO(serviceLocator);
      daoCache.put(JDBC_FORM_CATEGORY_DAO, myDAO);
    }

    return myDAO;
  }

  public WorkFlowStatusDAO getWorkFlowStatusDAO() {
    WorkFlowStatusDAO myDAO =
      (JDBCWorkFlowStatusDAO) daoCache.get(JDBC_WK_FLOW_STATUS_DAO);

    if (myDAO == null) {
      myDAO = new JDBCWorkFlowStatusDAO(serviceLocator);
      daoCache.put(JDBC_WK_FLOW_STATUS_DAO, myDAO);
    }

    return myDAO;
  }

  public static void main(String[] args) {
    /**
     * JDBCDAOFactory factory = (JDBCDAOFactory)new
     * JDBCDAOFactory().getDAOFactory((ServiceLocator)new
     * TestServiceLocatorImpl()); FormDAO dao = factory.getFormDAO();
     * Collection test = dao.getFormsByContext("Context1");
     * System.out.println(test);
     */
  }
}
