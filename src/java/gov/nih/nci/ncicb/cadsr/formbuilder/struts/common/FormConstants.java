package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants;


public interface FormConstants extends FormBuilderConstants {
  public static final String FORM_PREFERRED_NAME = "formPreferredName";	
  public static final String FORM_LONG_NAME = "formLongName";
  public static final String FORM_PREFERRED_DEFITION = "preferredDefinition";
  public static final String CRF_CONTEXT_ID_SEQ = "conteIdseq";
  public static final String CRF_CONTEXT = "context";
  public static final String CONTEXT_ID_SEQ = "contextIdSeq";
  public static final String CONTEXT_NAME = "contextName";
  public static final String WORKFLOW = "workflow";
  public static final String FORM_HEADER = "formHeader";
  public static final String FORM_FOOTER = "formFooter";
  public static final String FORM_PROTOCOL_ID_SEQ = "formProtocolIdseq";
  public static final String FORM_PROTOCOL_NAME = "formProtocolName";
  public static final String LONG_NAME = "longName";
  public static final String COMMENTS = "comments";
  public static final String DISPLAY_ORDER = "displayOrder";
  public static final String CRF = "crf";
  public static final String FORM_TYPE = "formType";
  public static final String FORM_SEARCH_RESULTS = "formSearchResults";
  public static final String MODULE_INDEX = "moduleIndex";
  public static final String DELETED_MODULE_INDEX = "deletedModuleIndex";
  public static final String ADD_DELETED_MODULE_IDSEQ = "addDeletedModuleIdSeq";
  public static final String FORM_ID = "formId";
  public static final String FORM_CATEGORY = "formCategory";
  public static final String FORM_VERSION = "formVersion";
  public static final String FORM_COMMENTS = "formComments";
  public static final String FORM_GOTO_EDIT = "gotoEdit";
  public static final String CHANGED_FORM_HEADER = "gotoEdit";
  public static final String CHANGED_FORM_UPDATED_MODULES = "gotoEdit";
  public static final String CHANGED_FORM_DELETED_MODULES = "gotoEdit";
  public static final String MODULE_LONG_NAME = "moduleLongName";
  public static final String MODULE_INSTRUCTION_LONG_NAME =
    "moduleInstructionLongName";
  public static final String CLONED_CRF = "clonedCrf";
  public static final String MODULE = "eModule";
  public static final String CLONED_MODULE = "clonedModule";
  public static final String DELETED_MODULES = "deletedModules";
  public static final String FORM_ID_SEQ = "formIdSeq";
  public static final String PREFERRED_DEFINITION = "preferredDefinition";
  public static final String PROTOCOL_ID_SEQ = "protocolIdSeq";
  public static final String MODULE_ID_SEQ = "moduleIdSeq";
  public static final String SEARCH_ALL = "";
  public static final String CATEGORY_NAME = "categoryName";
  public static final String FORM_SEARCH_RESULTS_PAGINATION =
    "formSearchResultsPagination";
  public static final String PAGEINDEX = "pageIndex";
  public static final String ALL_CONTEXTS = "allContexts";
  public static final String ALL_WORKFLOWS = "allWorkflows";
  public static final String ALL_PROTOCOLS = "allProtocols";
  public static final String ALL_FORM_CATEGORIES = "allFormCategories";
  public static final String ALL_FORM_TYPES = "allFormTypes";
  public static final String PROTOCOLS_LOV_ID_FIELD = "protocolIdSeq";
  public static final String PROTOCOLS_LOV_NAME_FIELD = "protocolLongName";
  public static final String PERFORM_QUERY_FIELD = "performQuery";
  public static final String PROTOCOLS_LOV_BEAN = "protocolLOVBean";
  public static final String PROTOCOLS_LOV_TAB_BEAN = "protocolLOVTabBean";
  public static final String PROTOCOLS_LOV_PROTO_LONG_NAME = "SEARCH";
  public static final String PROTOCOLS_LOV_CONTEXT_CHECK = "chkContext";
  public static final String CSI_NAME = "txtClassSchemeItem";
  public static final String CS_CSI_ID = "jspClassification";
  public static final String QUESTION_LONG_NAME = "questionLongName";
  public static final String QUESTION_ID_SEQ = "questionIdSeq";
  public static final String QUESTION_INDEX = "questionIndex";
  public static final String MODULE_QUESTIONS = "moduleQuestions";
  public static final String DELETED_QUESTIONS = "deletedQuestions";
  public static final String ADD_DELETED_QUESTION_IDSEQ =
    "addDeletedQuestionIdSeq";
  public static final String VALID_VALUE_INDEX = "validValueIndex";
  public static final String AVAILABLE_VALID_VALUES = "AvailableValidValue";
  public static final String ADD_AVAILABLE_VALID_VALUE_INDEX =
      "addAvailableValidValueIndex";
  public static final String VALUE_DOMAIN_VALID_VALUES_MAP =
    "valueDomainValidValueMap";
  public static final String AVAILABLE_VALID_VALUES_MAP =
    "AvailableValidValuesMap";
  public static final String SELECTED_ITEMS = "selectedItems";
  public static final String CLASSIFICATIONS = "classifications";
  public static final String DE_SEARCH_SRC = "src";
  
  public static final int LONG_NAME_MAX_LENGTH = 255;
  public static final int DEFINITION_MAX_LENGTH = 2000;
  public static final int VERSION_MAX_LENGTH = 4;

}