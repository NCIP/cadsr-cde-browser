package gov.nih.nci.ncicb.cadsr.cdebrowser;

import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.util.GenericPopListBean;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;


public class DataElementSearchBean extends Object {
  private String searchStr = "";
  private String whereClause = "";
  private String[] strArray = null;
  private StringBuffer workflowList = null;
  private StringBuffer regStatusList = null;
  private StringBuffer altNameList = null;
  private String xmlQueryStmt = "";
  private String vdPrefName = "";
  private String csiName = "";
  private String decPrefName = "";
  private String contextUse = "";
  private StringBuffer usageList = new StringBuffer("");
  private String searchText;
  private String[] aslName;
  private String[] regStatus;
  private String[] altNames;
  private String vdIdseq;
  private String decIdseq;
  private String cdeId;
  private String csCsiIdseq;
  private String latVersionInd;
  private StringBuffer searchInList;
  private String validValue;
  private String altName;

  public DataElementSearchBean(
    HttpServletRequest request,
    String treeParamType,
    String treeParamIdSeq,
    DBUtil dbUtil) throws SQLException {
    strArray = request.getParameterValues("SEARCH");
    vdPrefName = request.getParameter("txtValueDomain");
    decPrefName = request.getParameter("txtDataElementConcept");
    csiName = request.getParameter("txtClassSchemeItem");
    searchText = request.getParameter("jspKeyword");
    aslName = request.getParameterValues("jspStatus");
    regStatus = request.getParameterValues("regStatus");
    altNames = request.getParameterValues("altName");
    vdIdseq = request.getParameter("jspValueDomain");
    decIdseq = request.getParameter("jspDataElementConcept");
    csCsiIdseq = request.getParameter("jspClassification");
    cdeId = request.getParameter("jspCdeId");
    latVersionInd = request.getParameter("jspLatestVersion");
    contextUse = request.getParameter("contextUse");
    validValue = request.getParameter("jspValidValue");
    altName = request.getParameter("jspAltName");

    if (contextUse == null) {
      contextUse = "";
    }

    String selIndex = null;

    buildWorkflowList(aslName, dbUtil);
    buildRegStatusList(regStatus,dbUtil);
    buildAlternateNameList(altNames, dbUtil);
    buildContextUseList(contextUse);
    searchInList = new StringBuffer("");

    String[] searchIn = request.getParameterValues("jspSearchIn");
    this.buildSearchInList(searchIn);
  }

  public String getSearchStr(int arrayIndex) {
    if (strArray != null) {
      return strArray[arrayIndex];
    }
    else {
      return "";
    }
  }

  public StringBuffer getWorkflowList() {
    return workflowList;
  }

  public void buildWorkflowList(
    String[] selectedIndex,
    DBUtil dbUtil) {
    String where = " ACTL_NAME = 'DATAELEMENT' AND ASL_NAME != 'RETIRED DELETED' ";
    workflowList =
      GenericPopListBean.buildList(
        "sbrext.ASL_ACTL_EXT", "ASL_NAME", "ASL_NAME", selectedIndex,
        "jspStatus", dbUtil, where, false, 4, true, true, false, true,
        "LOVField");
  }
  
  public StringBuffer getRegStatusList() {
    return regStatusList;
  }
  
  public void buildRegStatusList(
    String[] selectedIndex,
    DBUtil dbUtil) {
    String where = null;
    regStatusList =
      GenericPopListBean.buildList(
        "sbr.REG_STATUS_LOV", "REGISTRATION_STATUS", "REGISTRATION_STATUS", selectedIndex,
        "regStatus", dbUtil, where, false, 4, true, true, false, true,
        "LOVField");
  }
  public StringBuffer getAltNameList() {
    return altNameList;
  }
  

  public void buildAlternateNameList(
    String[] selectedIndex,
    DBUtil dbUtil) {
    String where = null;
    altNameList =
      GenericPopListBean.buildList(
        "sbr.DESIGNATION_TYPES_LOV", "DETL_NAME", "DETL_NAME", selectedIndex,
        "altName", dbUtil, where, true, 4, false, true, false, true,
        "LOVField");
  }

  public String getVDPrefName() {
    return StringUtils.replaceNull(vdPrefName);
  }

  public String getDECPrefName() {
    return StringUtils.replaceNull(decPrefName);
  }

  public String getCSIName() {
    return StringUtils.replaceNull(csiName);
  }

  public String getSearchText() {
    return StringUtils.replaceNull(searchText);
  }

  public String getAslName() {
    return StringUtils.replaceNull(aslName);
  }
  
  public String getRegStatus() {
    return StringUtils.replaceNull(regStatus);
  }

  public String getVdIdseq() {
    return StringUtils.replaceNull(vdIdseq);
  }

  public String getDecIdseq() {
    return StringUtils.replaceNull(decIdseq);
  }

  public String getCdeId() {
    return StringUtils.replaceNull(cdeId);
  }

  public String getLatVersionInd() {
    return StringUtils.replaceNull(latVersionInd);
  }

  public String getCsCsiIdseq() {
    return StringUtils.replaceNull(csCsiIdseq);
  }

  private void buildContextUseList(String usage) {
    //if ("".equals(contextUse) || "owned_by".equals(contextUse)) {
    if ("owned_by".equals(contextUse)) {
      usageList.append(
        "<select name=\"contextUse\" size=\"1\" class=\"LOVField\"> ");
      usageList.append(
        "<option selected value=\"owned_by\">Owned By</option> ");
      usageList.append("<option value=\"used_by\">Used By</option> ");
      usageList.append("<option value=\"both\">Owned By/Used By</option> ");
      usageList.append("</select> ");
    }
    else if ("used_by".equals(contextUse)) {
      usageList.append(
        "<select name=\"contextUse\" size=\"1\" class=\"LOVField\"> ");
      usageList.append("<option value=\"owned_by\">Owned By</option> ");
      usageList.append("<option selected value=\"used_by\">Used By</option> ");
      usageList.append("<option value=\"both\">Owned By/Used By</option> ");
      usageList.append("</select> ");
    }
    else if ("".equals(contextUse) || "both".equals(contextUse)) {
      usageList.append(
        "<select name=\"contextUse\" size=\"1\" class=\"LOVField\"> ");
      usageList.append("<option value=\"owned_by\">Owned By</option> ");
      usageList.append("<option value=\"used_by\">Used By</option> ");
      usageList.append(
        "<option selected value=\"both\">Owned By/Used By</option> ");
      usageList.append("</select> ");
    }
  }

  public StringBuffer getContextUseList() {
    return usageList;
  }

  private void buildSearchInList(String[] searchIn) {
    searchInList.append(
      "<select multiple name=\"jspSearchIn\" size=\"4\" class=\"LOVField\"> ");

    if (searchIn == null) {
      searchInList.append("<option value=\"ALL\">ALL</option> ");
      searchInList.append("<option selected value=\"Long Name\">Long Name</option> ");
      searchInList.append(
        "<option value=\"Preferred Name\">Preferred Name</option> ");
      searchInList.append("<option value=\"Doc Text\">Document Text</option> ");
      searchInList.append(
        "<option value=\"Hist\">Historic Short CDE Name</option> ");
    }
    else {
      if (StringUtils.containsKey(searchIn, "ALL")) {
        searchInList.append("<option selected value=\"ALL\">ALL</option> ");
      }
      else {
        searchInList.append("<option value=\"ALL\">ALL</option> ");
      }

      if (StringUtils.containsKey(searchIn, "Preferred Name")) {
        searchInList.append(
          "<option selected value=\"Preferred Name\">Preferred Name</option> ");
      }
      else {
        searchInList.append(
          "<option value=\"Preferred Name\">Preferred Name</option> ");
      }

      if (StringUtils.containsKey(searchIn, "Long Name")) {
        searchInList.append(
          "<option selected value=\"Long Name\">Long Name</option> ");
      }
      else {
        searchInList.append("<option value=\"Long Name\">Long Name</option> ");
      }

      if (StringUtils.containsKey(searchIn, "Doc Text")) {
        searchInList.append(
          "<option selected value=\"Doc Text\">Document Text</option> ");
      }
      else {
        searchInList.append("<option value=\"Doc Text\">Document Text</option> ");
      }

      if (StringUtils.containsKey(searchIn, "Hist")) {
        searchInList.append(
          "<option selected value=\"Hist\">Historic Short CDE Name</option> ");
      }
      else {
        searchInList.append(
          "<option value=\"Hist\">Historic Short CDE Name</option> ");
      }
    }

    searchInList.append("</select> ");
  }

  public StringBuffer getSearchInList() {
    return searchInList;
  }

  public String getValidValue() {
    return StringUtils.replaceNull(validValue);
  }
  
  public String getAltName() {
    return StringUtils.replaceNull(altName);
  }
}
