package gov.nih.nci.ncicb.cadsr.formbuilder.common;
import java.util.HashMap;
import java.util.Map;

/**
 * An interface which holds all the constants that are used in FormBuilder application.
 */
public interface FormBuilderConstants {
  public static String SERVICE_DELEGATE_FACTORY_KEY ="ServiceDelegateFactory";   
  public static String SERVICE_DELEGATE_CLASS_KEY = "FormBuilderServiceDelegate";
  public static final String SUCCESS_KEY = "Success";
  public static final String SYSTEM_FAILURE_KEY = "SystemFailure";
  public static final String SESSION_TIME_OUT_KEY = "SessionTimeOut";
  public static final String FAILURE_KEY = "Failure";
  public static final String SIGNON_KEY = "Login";
  public static final String LOGIN_TOKEN_KEY = "Loginkey";
  //This provides the Workflows that can edited,deleted, and copied by cdemanager
  public static final String EDITABLE_WORKFLOW_STATUS_LIST="editableWorkflowStatusList";
  public static final String[] EDITABLE_WORKFLOW_STATUSES=
                       {"CRF MATCH EXISTING","CRF NOT APPROVED","CRF RELOAD"
                        ,"CRF TEMPLATE","DATA QUAL REV DONE"
                        ,"EXACT MATCH","GRP REVIEW","NEW TERM SUBMTD"
                        ,"NEW VERS SUBMTD","RECOMM TERM USED","RECOMMENDED TERM"};
  public static final String DELETABLE_WORKFLOW_STATUS_LIST="deletableWorkflowStatusList";
  public static final String[] DELETABLE_WORKFLOW_STATUSES=
                         {"CRF MATCH EXISTING","CRF NOT APPROVED","CRF RELOAD"
                        ,"CRF TEMPLATE","DATA QUAL REV DONE"
                        ,"EXACT MATCH","GRP REVIEW","NEW TERM SUBMTD"
                        ,"NEW VERS SUBMTD","RECOMM TERM USED","RECOMMENDED TERM"};
  public static final String COPYABLE_WORKFLOW_STATUS_LIST="copyableWorkflowStatusList";
  public static final String[] COPYABLE_WORKFLOW_STATUSES=
                         {"CRF MATCH EXISTING","CRF NOT APPROVED","CRF RELOAD"
                        ,"CRF TEMPLATE","DATA QUAL REV DONE"
                        ,"EXACT MATCH","GRP REVIEW","NEW TERM SUBMTD"
                        ,"NEW VERS SUBMTD","RECOMM TERM USED","RECOMMENDED TERM"};

  /**TODO - delete later skakkodi
  public static final String[] EDITABLE_WORKFLOW_STATUSES=
                       {"CRF GROUP RV DONE"};
  public static final String DELETABLE_WORKFLOW_STATUS_LIST="deletableWorkflowStatusList";
  public static final String[] DELETABLE_WORKFLOW_STATUSES=
                         {"CRF DE TO GROUP"};
  public static final String COPYABLE_WORKFLOW_STATUS_LIST="copyableWorkflowStatusList";
  public static final String[] COPYABLE_WORKFLOW_STATUSES=
                         {"PROTOCOL TO GRP REV"};
 **/                         
  public static final String ERROR_FORM_SAVE_FAILED="FB001";
  public static final String ERROR_FORM_SAVE_RETRIEVE="FB002";
  public static final String ERROR_FORM_DELETE_FAILED="FB002";
  public static final String ERROR_MODULE_SAVE_FAILED="FB004";
  public static final String ERROR_FATAL="FB911";
  

}
