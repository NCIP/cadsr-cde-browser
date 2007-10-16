package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AdminComponentDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.CDECartDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ClassificationSchemeDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ConceptDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.DerivedDataElementDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormCategoryDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormInstructionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueInstructionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleInstructionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ProtocolDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionInstructionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionRepititionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ReferenceDocumentDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ReferenceDocumentTypeDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.TriggerActionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UtilDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ValueDomainDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.WorkFlowStatusDAO;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class JDBCDAOFactory extends AbstractDAOFactory
  implements PersistenceConstants {
  private Map daoCache = Collections.synchronizedMap(new HashMap());

  public JDBCDAOFactory() {
  }
   public AdminComponentDAO getAdminComponentDAO() {
     AdminComponentDAO acDAO = (AdminComponentDAO) daoCache.get(JDBC_ADMIN_COMPONENT_DAO);

     if (acDAO == null) {
       acDAO = new JDBCAdminComponentDAO(serviceLocator);
       daoCache.put(JDBC_ADMIN_COMPONENT_DAO, acDAO);
     }

     return acDAO;
   }
    public ClassificationSchemeDAO getClassificationSchemeDAO() {
      ClassificationSchemeDAO csDAO = (ClassificationSchemeDAO) daoCache.get(JDBC_CLASS_SCHEME_COMPONENT_DAO);

      if (csDAO == null) {
        csDAO = new JDBCClassificationSchemeDAO(serviceLocator);
        daoCache.put(JDBC_CLASS_SCHEME_COMPONENT_DAO, csDAO);
      }

      return csDAO;
    }
  public FormDAO getFormDAO() {
    FormDAO formDAO = (JDBCFormDAO) daoCache.get(JDBC_FORM_DAO);

    if (formDAO == null) {
      formDAO = new JDBCFormDAO(serviceLocator);
      daoCache.put(JDBC_FORM_DAO, formDAO);
    }

    return formDAO;
  }

  public  QuestionRepititionDAO getQuestionRepititionDAO()
  {
      QuestionRepititionDAO dao = (JDBCQuestionRepititionDAO) daoCache.get(JDBC_QUESTION_REPITION_DAO);

      if (dao == null) {
        dao = new JDBCQuestionRepititionDAO(serviceLocator);
        daoCache.put(JDBC_QUESTION_REPITION_DAO, dao);
      }

      return dao;      
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

  public FormInstructionDAO getFormInstructionDAO() {
    FormInstructionDAO myDAO =
      (JDBCFormInstructionDAO) daoCache.get(JDBC_FORM_INSTR_DAO);

    if (myDAO == null) {
      myDAO = new JDBCFormInstructionDAO(serviceLocator);
      daoCache.put(JDBC_FORM_INSTR_DAO, myDAO);
    }

    return myDAO;
  }

  public FormValidValueInstructionDAO getFormValidValueInstructionDAO() {
    FormValidValueInstructionDAO myDAO =
      (JDBCFormValidValueInstructionDAO) daoCache.get(JDBC_VALUE_INSTR_DAO);

    if (myDAO == null) {
      myDAO = new JDBCFormValidValueInstructionDAO(serviceLocator);
      daoCache.put(JDBC_VALUE_INSTR_DAO, myDAO);
    }

    return myDAO;
  }

  public ModuleInstructionDAO getModuleInstructionDAO() {
    ModuleInstructionDAO myDAO =
      (JDBCModuleInstructionDAO) daoCache.get(JDBC_MODULE_INSTR_DAO);

    if (myDAO == null) {
      myDAO = new JDBCModuleInstructionDAO(serviceLocator);
      daoCache.put(JDBC_MODULE_INSTR_DAO, myDAO);
    }

    return myDAO;
  }

  public QuestionInstructionDAO getQuestionInstructionDAO() {
    QuestionInstructionDAO myDAO =
      (JDBCQuestionInstructionDAO) daoCache.get(JDBC_QUESTION_INSTR_DAO);

    if (myDAO == null) {
      myDAO = new JDBCQuestionInstructionDAO(serviceLocator);
      daoCache.put(JDBC_QUESTION_INSTR_DAO, myDAO);
    }

    return myDAO;
  }

  public CDECartDAO getCDECartDAO () {
    CDECartDAO myDAO =
      (JDBCCDECartDAO) daoCache.get(JDBC_CDE_CART_DAO);

    if (myDAO == null) {
      myDAO = new JDBCCDECartDAO(serviceLocator);
      daoCache.put(JDBC_CDE_CART_DAO, myDAO);
    }

    return myDAO;
  }

  public ValueDomainDAO getValueDomainDAO () {
    ValueDomainDAO myDAO =
      (JDBCValueDomainDAO) daoCache.get(JDBC_VALUE_DOMAIN_DAO);

    if (myDAO == null) {
      myDAO = new JDBCValueDomainDAO(serviceLocator);
      daoCache.put(JDBC_VALUE_DOMAIN_DAO, myDAO);
    }

    return myDAO;
  }

  public DerivedDataElementDAO getDerivedDataElementDAO () {
    DerivedDataElementDAO myDAO =
      (DerivedDataElementDAO) daoCache.get(JDBC_DERIVED_DATA_ELEMENT_DAO);

    if (myDAO == null) {
      myDAO = new JDBCDerivedDataElementDAO(serviceLocator);
      daoCache.put(JDBC_DERIVED_DATA_ELEMENT_DAO, myDAO);
    }
    
    return myDAO;
  }

  public ConceptDAO getConceptDAO () {
    ConceptDAO myDAO =
      (ConceptDAO) daoCache.get(JDBC_CONCEPT_DAO);

    if (myDAO == null) {
      myDAO = new JDBCConceptDAO(serviceLocator);
      daoCache.put(JDBC_CONCEPT_DAO, myDAO);
    }

    return myDAO;
  }
  
  public ReferenceDocumentDAO getReferenceDocumentDAO () {
    ReferenceDocumentDAO refDocDAO =
      (ReferenceDocumentDAO) daoCache.get(JDBC_REFERENCE_DOCUMENT_DAO);

    if (refDocDAO == null) {
      refDocDAO = new JDBCReferenceDocumentDAO(serviceLocator);
      // Cut-Paste error, fixed by Ram C
      //daoCache.put(JDBC_CONCEPT_DAO, refDocDAO);
      daoCache.put(JDBC_REFERENCE_DOCUMENT_DAO, refDocDAO);
    }

    return refDocDAO;
  }

  public ReferenceDocumentTypeDAO getReferenceDocumentTypeDAO () {
    ReferenceDocumentTypeDAO refDocTypeDAO =
      (ReferenceDocumentTypeDAO) daoCache.get(JDBC_REFERENCE_DOCUMENT_TYPE_DAO);

    if (refDocTypeDAO == null) {
      refDocTypeDAO = new JDBCReferenceDocumentTypeDAO(serviceLocator);
      // Cut-Paste error, fixed by Ram C
      // daoCache.put(JDBC_CONCEPT_DAO, refDocTypeDAO);
      daoCache.put(JDBC_REFERENCE_DOCUMENT_TYPE_DAO, refDocTypeDAO);
    }

    return refDocTypeDAO;
  }

  public UtilDAO getUtilDAO () {
    UtilDAO utilDAO =
      (UtilDAO) daoCache.get(JDBC_UTIL_DAO);

    if (utilDAO == null) {
      utilDAO = new JDBCUtilDAO(serviceLocator);
      // Cut-Paste error, fixed by Ram C
      // daoCache.put(JDBC_CONCEPT_DAO, refDocTypeDAO);
      daoCache.put(JDBC_UTIL_DAO, utilDAO);
    }

    return utilDAO;
  }
  
    public TriggerActionDAO getTriggerActionDAO () {
      TriggerActionDAO triggerActionDAO =
        (TriggerActionDAO) daoCache.get(JDBC_TRIGGER_ACTION_DAO);

      if (triggerActionDAO == null) {
        triggerActionDAO = new JDBCTriggerActionDAO(serviceLocator);

        daoCache.put(JDBC_TRIGGER_ACTION_DAO, triggerActionDAO);
      }

      return triggerActionDAO;
    }

    public ProtocolDAO getProtocolDAO() {
      ProtocolDAO protocolDAO = (JDBCProtocolDAO) daoCache.get(JDBC_PROTOCOL_DAO);

      if (protocolDAO == null) {
        protocolDAO = new JDBCProtocolDAO(serviceLocator);
        daoCache.put(JDBC_PROTOCOL_DAO, protocolDAO);
      }

      return protocolDAO;
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
