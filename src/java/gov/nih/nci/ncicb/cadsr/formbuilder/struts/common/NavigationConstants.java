package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;
import gov.nih.nci.ncicb.cadsr.CommonNavigationConstants;

public interface NavigationConstants extends CommonNavigationConstants
{
  //struts forward names

  public static final String FORM_DETAILS ="formDetails";
  public static final String FORM_EDIT ="formEdit";
  public static final String MODULE_EDIT ="moduleEdit";
  public static final String NO_CHANGES ="noChanges";
  public static final String CHANGES ="changes";
  public static final String SEARCH_RESULTS ="searchResults";



  //Used to define the forward to use in the target action
  //When Chaining actions

  public static final String GET_ALL_FORMS_METHOD="getAllForms";
  public static final String CLEAR_FORM_SEARCH_METHOD="clearFormSearch";
  public static final String NEW_SEARCH_METHOD="newSearch";


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
  public static final String DELETE_VALID_VALUES="deleteValidValues";
  public static final String ADD_FROM_AVAILABLE_VALID_VALUE_LIST="addFromAvailableValidValueList";
  public static final String SAVE_MODULE="saveModule";
  public static final String CANCEL_MODULE_EDIT ="cancelModuleEdit";
  public static final String VIEW_SUBSETTEDVDS_LIST = "viewSubsettedVDs";
  //Edit Form
  public static final String GET_FORM_TO_EDIT="getFormToEdit";
  public static final String DELETE_FORM="deleteForm";
  public static final String MOVE_MODULE_UP="moveModuleUp";
  public static final String MOVE_MODULE_DOWN="moveModuleDown";
  public static final String DELETE_MODULE="deleteModule";
  public static final String ADD_FROM_DELETED_LIST="addFromDeletedList";
  public static final String CHECK_FOR_UPDATE="checkForUpdate";
  public static final String SAVE_FORM="saveForm";
  public static final String CREATE_NEW_VERSION="gotoCreateNewVersion";
  public static final String SAVE_FORM_MODULE_EDIT="saveFormModuleEdit";
  public static final String SAVE_FORM_CHANGES="saveFormChanges";
  public static final String CHECK_CHANGES_MODULE_EDIT="checkChangesModuleEdit";
  public static final String CHECK_CHANGES_DONE="checkChangesDone";
  public static final String CANCEL_FORM_CHANGES_MODULE_EDIT="cancelFormChangesModuleEdit";
  public static final String SAVE_CONFIRM="saveConfirm";
  public static final String SAVE_CONFIRM_DONE="saveConfirmDone";

  public static final String MOVE_REFDOC_UP="moveRefDocUp";
  public static final String MOVE_REFDOC_DOWN="moveRefDocDown";
  public static final String DELETE_REFDOC="deleteRefDoc";
  public static final String UNDELETE_REFDOC="unDeleteRefDoc";
  public static final String EDIT_REFDOC="getFormToEdit";
  public static final String CREATE_REFDOC="gotoCreateReferenceDoc";
  public static final String DELETE_ATTACHMENT="deleteAttachment";

  public static final String CREATE_MODULE = "createModule";
  public static final String GO_TO_CREATE_MODULE = "goToCreateModule";


  public static final String CREATE_FORM = "createForm";
  public static final String GO_TO_CREATE_FORM = "goToCreateForm";
  public static final String CANCEL_FORM_CREATE="cancelFormCreate";

  public static final String GET_PROTOCOL_LOV_METHOD="getProtocolsLOV";
  public static final String GOTO_MANAGE_PROTOCOLS_FORM_COPY="gotoManageProtocolsFormCopy";
  public static final String GOTO_MANAGE_PROTOCOLS_FORM_EDIT="gotoManageProtocolsFormEdit";
  public static final String GOTO_MANAGE_PROTOCOLS="gotoManageProtocols";


  public static final String CHANGE_DE_ASSOCIATION = "changeAssociation";
  public static final String GO_TO_CHANGE_DE_ASSOCIATION = "gotoChangeDEAssociation";

  public static final String ADD_QUESTION = "addQuestion";
  public static final String GO_TO_ADD_QUESTION = "displayCDECart";
  public static final String SUBSET_QUESTION_VALIDVALUES = "subsetQuestionValidValues";
  public static final String SUBSET_VALIDVALUES = "subsetVVs";
  public static final String ADD_SUBSETTED_VALIDVALUES_QUESTION = "addSubsettedValidValuesQuestion";
  public static final String CANCEL_ADD_SUBSETTED_VALIDVALUES_QUESTION = "cancelAddSubsettedValidValuesQuestion";

  public static final String GET_CLASSIFICATIONS = "getClassifications";

  public static final String GET_CONTEXTS = "getContexts";
  public static final String SAVE_DESIGNATIONS = "saveDesignations";
  public static final String CANCEL_DESIGNATIONS = "cancelDesignations";

  public static final String GOTO_CREATE_NEW_VERSION_FORM = "gotoCreateNewVersion";
  public static final String GET_FORM_VERSIONS = "getFormVersions";
  public static final String SAVE_NEW_VERSION = "saveNewVersion";
  public static final String CANCEL_NEW_VERSION = "cancelNewVersion";

  public static final String GO_TO_ADD_CLASSIFICATIONS = "gotoAddClassifications";
  public static final String ADD_CLASSIFICATIONS = "addClassifications";

  public static final String REMOVE_CLASSIFICATION = "removeClassification";

  public static final String GO_TO_SEARCH = "gotoSearch";

  public static final String GO_TO_MANAGE_CLASSIFICATIONS = "gotoManageClassifications";


// Publish Change Request
 public static final String PUBLISH_FORM = "publishForm";
 public static final String UNPUBLISH_FORM = "unpublishForm";

 //Skip Patterns
  public static final String  ADD_SKIP_PATTERN = "addSkipPattern";

  public static final String SAVE_LATEST_VERSION = "saveLatestVersion";
  public static final String CANCEL_LATEST_VERSION = "cancelLatestVersion";

  public static final String  EDIT_SKIP_PATTERN = "editSkipPattern";

  public static final String  CREATE_FORM_SKIP_PATTERN = "createFormSkipPattern";
  public static final String  CREATE_MODULE_SKIP_PATTERN = "createModuleSkipPattern";
  public static final String  CREATE_VALIDVALUE_SKIP_PATTERN = "createValidValueSkipPattern";

  public static final String  SKIP_TO_FORM_LOCATION = "skipToFormLocation";

  public static final String  SET_CURRENT_FORM_AS_TARGET_FORM="setCurrentFormAsTargetForm";
    public static final String  SET_SELECTED_FORM_AS_TARGET_FORM="setSelectedFormAsTargetForm";


   public static final String  SET_FORM_AS_TARGET = "setFormAsTarget";
   public static final String   SET_MODULE_AS_TARGET="setModuleAsTarget";
    public static final String   SET_QUESTION_AS_TARGET="setQuestionAsTarget";

  public static final String  SKIP_TO_FORM_SEARCH = "skipToFormSearch";
  public static final String  CHECK_MODULE_CHANGES="checkModuleChanges";
  public static final String  SAVE_SKIP_PATTERN = "saveSkipPattern";
  public static final String  CANCEL_MODULE_SAVE="cancelModuleSave";
    public static final String  CANCEL_SKIP_EDIT="cancelSkipEdit";
    public static final String  CONFIRM_MODULE_SKIP_PATTERN_DELETE="confirmModuleSkipPatternDelete";
    public static final String  CONFIRM_VALID_VALUE_SKIP_PATTERN_DELETE="confirmValidValueSkipPatternDelete";
    
    public static final String  DELETE_MODULE_SKIP_PATTERN="deleteModuleSkipPattern";
    public static final String  DELETE_VALID_VALUE_SKIP_PATTERN="deleteValidValueSkipPattern";
    
    
    
  public static final String  GO_TO_MODULE_SEARCH = "goToModuleSearch";
  public static final String  GO_TO_SELECTED_MODULE_LIST = "goToSelectedModuleList";
  public static final String  SET_SELECTED_FORM_AS_MODULE_COPY_FORM="setSelectedFormAsModuleCopyForm";
  public static final String  COPY_SELECTED_MODULE_TO_FORM="copySelectedModuleToForm";
  public static final String  COPY_SELECTED_MODULE_TO_LIST= "copySelectedModuleToList";
  public static final String   CANCEL_MODULE_FORM_SEARCH= "cancelModuleFormSearch";
  public static final String    CANCEL_MODULE_SELECTION= "cancelModuleSelection";
  public static final String     VIEW_MODULE_LIST="viewModuleList";
  public static final String     DONE_VIEW_MODULE_LIST="doneViewModuleList";
  public static final String     DELETE_ELEMENTS_FROM_MODULE_LIST="deleteElementsFromModuleList";
  public static final String     GOTO_COPY_FROM_MODULE_LIST="gotoCopyFromModuleList";
  public static final String     COPY_FROM_MODULE_LIST="copyFromModuleList";
  
  public static final String     ADD_REPETITIONS = "addRepetitions";
  public static final String     DELETE_REPETITIONS = "deleteRepetitions";
    public static final String     DONE_MANAGE_REPETITIONS="doneManageRepetitions";
    public static final String     SAVE_REPETITIONS="saveRepetitions";
     public static final String SHOW_REPETITIONS="showRepetitions";
    public static final String HIDE_REPETITIONS="hideRepetitions";
     

  public static final String ADD_PROTOCOL = "addProtocol";
  public static final String REMOVE_PROTOCOL = "removeProtocol";
  public static final String DONE_PROTOCOL = "doneProtocol";
  public static final String UPDATE_SKIP_PATTERN = "updateSkipPattern";
  
  public static final String SET_SENTINAL_ALERT="setAlertForForm";

}