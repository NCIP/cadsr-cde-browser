package gov.nih.nci.ncicb.cadsr.persistence;

public interface PersistenceConstants {
  public static final String DAO_FACTORY_CLASS_KEY = "DAOFactoryClassName";
  public static final String DATASOURCE_LOCATION_KEY = "CDEBrowserDS";
  public static final String DRIVER_MANAGER_DS = "DriverManagerDS";
  public static final String CONNECTION_STRING = "ConnectionString";
  public static final String DRIVER_CLASS_NAME = "DriverClassName";
  public static final String USERNAME = "username";
  public static final String PASSWORD = "password";
  public static final String JDBC_FORM_DAO = "jdbcFormDAO";
  public static final String JDBC_QUESTION_REPITION_DAO ="jdbcQuestionRepititionDAO";
  public static final String JDBC_MODULE_DAO = "jdbcModuleDAO";
  public static final String JDBC_QUESTION_DAO = "jdbcQuestionDAO";
  public static final String JDBC_FORM_VALID_VALUE_DAO =
    "jdbcFormValidValueDAO";
  public static final String JDBC_USER_MGR_DAO = "jdbcUserManagerDAO";
  public static final String JDBC_CONTEXT_DAO = "jdbcContextDAO";
  public static final String JDBC_FORM_CATEGORY_DAO = "jdbcFormCategoryDAO";
  public static final String JDBC_WK_FLOW_STATUS_DAO = "jdbcWkFlowStatusDAO";
  public static final String JDBC_FORM_INSTR_DAO = "jdbcFormInstrDAO";
  public static final String JDBC_MODULE_INSTR_DAO = "jdbcModuleInstrDAO";
  public static final String JDBC_QUESTION_INSTR_DAO = "jdbcQuestionInstrDAO";
  public static final String JDBC_VALUE_INSTR_DAO = "jdbcValueInstrDAO";
  public static final String JDBC_CDE_CART_DAO = "jdbcCDECartDAO";
  public static final String JDBC_VALUE_DOMAIN_DAO = "jdbcValueDomainDAO";
  public static final String JDBC_DERIVED_DATA_ELEMENT_DAO = "jdbcDerivedDataElementDAO";
  public static final String JDBC_CONCEPT_DAO = "jdbcConceptDAO";
  public static final String JDBC_REFERENCE_DOCUMENT_DAO = "jdbcReferenceDocumentDAO";
  public static final String JDBC_REFERENCE_DOCUMENT_TYPE_DAO = "jdbcReferenceDocumentTypeDAO";
  public static final String JDBC_UTIL_DAO = "jdbcUtilDAO";
  public static final String JDBC_PROTOCOL_DAO= "jdbcProtocolDAO";
  public static final String JDBC_TRIGGER_ACTION_DAO="jdbcTriggerActionDAO";
  public static final String JDBC_ADMIN_COMPONENT_DAO="jdbcAdminComponentDAO";
  public static final String JDBC_CLASS_SCHEME_COMPONENT_DAO="jdbcClassSchemeDAO";



  public static final String FORM_ADMIN_COMPONENT_TYPE="QUEST_CONTENT";

  public static final String FORM_TYPE_CRF="CRF";
  public static final String FORM_TYPE_TEMPLATE="TEMPLATE";
  public static final String[] FORM_TYPE_VALUES={"CRF","TEMPLATE"};

  public static final String IDSEQ_GENERATOR = "admincomponent_crud.cmr_guid";

// Change Order Publish
  public static final String CS_TYPE_PUBLISH ="Publishing";
  public static final String CSI_TYPE_PUBLISH_FORM="Form Type";
  public static final String CSI_TYPE_PUBLISH_TEMPLATE="Template Type";
  
  public static final String QTL_NAME_MODULE="MODULE";
  
    public static final String QTL_NAME_QUESTION="QUESTION";
    public static final String QTL_NAME_VALID_VALUE="VALID_VALUE";

}
