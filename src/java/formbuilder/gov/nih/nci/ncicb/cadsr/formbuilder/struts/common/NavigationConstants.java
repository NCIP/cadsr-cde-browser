package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

public interface NavigationConstants
{
  //struts forward names
  public static final String SUCCESS = "success";
  public static final String FAILURE = "failure";
  public static final String FORM_DETAILS ="formDetails";
  public static final String FORM_EDIT ="formEdit";
  public static final String MODULE_EDIT ="moduleEdit";
  public static final String SEARCH_RESULTS ="searchResults";

  //Method names

  public static final String METHOD_PARAM="method";
  //Used to define the forward to use in the target action
  //When Chaining actions

  public static final String GET_ALL_FORMS_METHOD="getAllForms";
  public static final String SEND_HOME_METHOD="sendHome";
  public static final String DEFAULT_METHOD=SEND_HOME_METHOD;
  public static final String FORWARD_FRAMED_SEARCH_RESULTS_PAGE="framedSearchResultsPage";
  public static final String DEFAULT_HOME = FORWARD_FRAMED_SEARCH_RESULTS_PAGE;
  public static final String GET_FORM_DETAILS="getFormDetails";
  public static final String GET_FORM_TO_COPY="getFormToCopy";
  public static final String GET_FORM_TO_PRINT="getPrinterVersion";
  public static final String FORM_COPY = "formCopy";
  public static final String CANCEL_FORM_EDIT ="cancelFormEdit";
  //Edit Module
  public static final String GET_MODULE_TO_EDIT ="getModuleToEdit";
  public static final String MOVE_QUESTION_UP="moveQuestionUp";
  public static final String MOVE_QUESTION_DOWN="moveQuestionDown"; 
  public static final String DELETE_QUESTION="deleteQuestion"; 
  public static final String ADD_FROM_DELETED_QUESTION_LIST="addFromDeletedQuestionList";
  public static final String MOVE_VALID_VALUE_UP="moveValidValueUp";
  public static final String MOVE_VALID_VALUE_DOWN="moveValidValueDown"; 
  public static final String DELETE_VALID_VALUE="deleteValidValue"; 
  public static final String ADD_FROM_AVAILABLE_VALID_VALUE_LIST="addFromAvailableValidValueList";
  public static final String SAVE_MODULE="saveModule";
  public static final String CANCEL_MODULE_EDIT ="cancelModuleEdit";
  //Edit Form
  public static final String GET_FORM_TO_EDIT="getFormToEdit";
  public static final String DELETE_FORM="deleteForm";  
  public static final String MOVE_MODULE_UP="moveModuleUp";
  public static final String MOVE_MODULE_DOWN="moveModuleDown"; 
  public static final String DELETE_MODULE="deleteModule";
  public static final String ADD_FROM_DELETED_LIST="addFromDeletedList";
  public static final String SAVE_FORM="saveForm";
 

  public static final String CREATE_MODULE = "createModule";
  public static final String GO_TO_CREATE_MODULE = "goToCreateModule";
  
  public static final String GET_PROTOCOL_LOV_METHOD="getProtocolsLOV";

  public static final String CHANGE_DE_ASSOCIATION = "changeAssociation";
  public static final String GO_TO_CHANGE_DE_ASSOCIATION = "displayCDECart";

  public static final String ADD_QUESTION = "addQuestion";
  public static final String GO_TO_ADD_QUESTION = "displayCDECart";

  public static final String GET_CLASSIFICATIONS = "getClassifications";
  public static final String GO_TO_ADD_CLASSIFICATIONS = "gotoAddClassifications";
  public static final String ADD_CLASSIFICATIONS = "addClassifications";

 public static final String REMOVE_CLASSIFICATION = "removeClassification";

 public static final String GO_TO_SEARCH = "gotoSearch";


}