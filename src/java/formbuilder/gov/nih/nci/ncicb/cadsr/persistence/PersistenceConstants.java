package gov.nih.nci.ncicb.cadsr.persistence;

public interface PersistenceConstants {
  public static final String DAO_FACTORY_CLASS_KEY = "DAOFactoryClassName";
  public static final String DATASOURCE_LOCATION_KEY = "FormBuilderDS";
  public static final String DRIVER_MANAGER_DS = "DriverManagerDS";
  public static final String CONNECTION_STRING = "ConnectionString";
  public static final String DRIVER_CLASS_NAME = "DriverClassName";
  public static final String USERNAME = "username";
  public static final String PASSWORD = "password";
  public static final String JDBC_FORM_DAO = "jdbcFormDAO";
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

  public static final String FORM_ADMIN_COMPONENT_TYPE="QUEST_CONTENT";
  
  public static final String[] FORM_TYPE_VALUES={"CRF","TEMPLATE"};

  public static final String IDSEQ_GENERATOR = "admincomponent_crud.cmr_guid";
}
