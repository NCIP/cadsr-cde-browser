package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

public interface NavigationConstants
{
  //struts forward names
  public static final String SUCCESS = "success";
  public static final String FAILURE = "failure";
  public static final String FORM_DETAILS ="formDetails";
   public static final String FORM_EDIT ="formEdit";

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
  //Edit
  public static final String GET_FORM_TO_EDIT="getFormToEdit";
  public static final String DELETE_FORM="deleteForm";  
  public static final String MOVE_MODULE_UP="moveModuleUp";
  public static final String MOVE_MODULE_DOWN="moveModuleDown"; 
  public static final String DELETE_MODULE="deleteModule";
  public static final String ADD_FROM_DELETED_LIST="addFromDeletedList";
  public static final String SAVE_FORM="saveForm";

  public static final String CREATE_MODULE = "createModule";
  
  public static final String GET_PROTOCOL_LOV_METHOD="getProtocolsLOV";


}