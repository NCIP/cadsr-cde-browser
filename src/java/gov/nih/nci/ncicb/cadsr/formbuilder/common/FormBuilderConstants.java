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
  public static final String CLEAR_SESSION_KEYS="clearSessionKeys";
                       
  public static final String ERROR_FORM_SAVE_FAILED="FB001";
  public static final String ERROR_FORM_SAVE_RETRIEVE="FB002";
  public static final String ERROR_FORM_DELETE_FAILED="FB003";
  public static final String ERROR_MODULE_SAVE_FAILED="FB004";
  public static final String ERROR_MODULE_RETRIEVE="FB005";
  public static final String ERROR_FORM_RETRIEVE="FB006";
  public static final String ERROR_FORM_CREATE="FB007";
  public static final String ERROR_FORM_DOES_NOT_EXIST="FB008"; 
  public static final String ERROR_FORM_EDIT_FAILED="FB0010";
  public static final String ERROR_FORM_COPY_FAILED="FB0011"; 
  public static final String ERROR_FATAL="FB911";
  

}