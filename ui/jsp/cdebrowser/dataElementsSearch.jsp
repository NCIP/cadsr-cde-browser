<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants"%>
<%@page import="gov.nih.nci.ncicb.cadsr.resource.* "%>
<%@page import="gov.nih.nci.ncicb.cadsr.html.* " %>
<%@page import="java.util.List "%>
<%@page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>

<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%
  DESearchQueryBuilder queryBuilder = (DESearchQueryBuilder) infoBean.getInfo(ProcessConstants.DE_SEARCH_QUERY_BUILDER);
  pageContext.getSession().setAttribute("showCached",new Boolean("true"));
  if (queryBuilder != null)
  pageContext.getSession().setAttribute(ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR, 
  queryBuilder.getSortColumnHeader());
  DataElementSearchBean desb = (DataElementSearchBean)infoBean.getInfo("desb");
  List deList = (List)infoBean.getInfo(ProcessConstants.ALL_DATA_ELEMENTS);
  HTMLPageScroller myScroller = (HTMLPageScroller)infoBean.getInfo(ProcessConstants.DE_SEARCH_PAGE_SCROLLER);

  HTMLPageScroller topScroller = (HTMLPageScroller)infoBean.getInfo(
                           ProcessConstants.DE_SEARCH_TOP_PAGE_SCROLLER);
  
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tibSearchDE");
  String pageId = infoBean.getPageId();
  String pageName = PageConstants.PAGEID;
  String pageUrl = "&"+pageName+"="+pageId;
  //String latestVer = desb.getSearchStr(6);
  String latestVer = desb.getLatVersionInd();
  String paramIdseq = (String)infoBean.getInfo("P_IDSEQ");
  //release 3.0, change URL for cde browser jsp
  String contextPath = request.getContextPath() + "/cdebrowser";
  
  if (paramIdseq == null) paramIdseq = "";
  String paramType = (String)infoBean.getInfo("P_PARAM_TYPE");
  if (paramType == null) paramType = "";
  String templateURL = "search?viewTemplate=9&templateIdseq="+paramIdseq+pageUrl;
  //String downloadXMLURL = "javascript:newDownloadWin('search?xmlDownload=9"+pageUrl+"','downloadWin',10,10)";
  String downloadXMLURL = "javascript:fileDownloadWin('" + contextPath + "/downloadXMLPage.jsp?src=deSearch','xmlWin',500,200)";
  //String downloadExcelURL = "javascript:newDownloadWin('search?excelDownload=9"+pageUrl+"','downloadWin',10,10)";
  String downloadExcelURL = "javascript:fileDownloadWin('" + contextPath + "/downloadExcelPage.jsp?src=deSearch','excelWin',500,200)";
  String valueDomainLOVUrl= "javascript:newWin('" + request.getContextPath() + "/search?valueDomainsLOV=9&idVar=jspValueDomain&nameVar=txtValueDomain"+pageUrl+"','vdLOV',700,600)";
  String decLOVUrl= "javascript:newWin('" + request.getContextPath() + "/search?dataElementConceptsLOV=9&idVar=jspDataElementConcept&nameVar=txtDataElementConcept"+pageUrl+"','decLOV',700,600)";
  String csLOVUrl= "javascript:newBrowserWin('" + request.getContextPath() + "/search?classificationsLOV=9&idVar=jspClassification&nameVar=txtClassSchemeItem"+pageUrl+"','csLOV',700,600)";

  
  String txtDataElementConcept = desb.getDECPrefName();
  String txtValueDomain = desb.getVDPrefName();
  String txtClassSchemeItem = desb.getCSIName();
  String pageContextInfo = "";
  if (!paramIdseq.equals("") && !paramType.equals("")){
    CDEBrowserPageContext pg = (CDEBrowserPageContext)infoBean.getInfo(ProcessConstants.PAGE_CONTEXT);
    pageContextInfo = pg.getPageContextDisplayText();
  }
  String firstDisplay = (String)infoBean.getInfo("NOT_FIRST_DISPLAY");
  if (firstDisplay ==null) firstDisplay = "";

  String queryFlag = request.getParameter("performQuery");
  if (queryFlag == null) queryFlag = "";


  String doneURL = "";

  String src = request.getParameter("src");
  if (src == null)
    src="";
  String modIndex = "";
  String quesIndex = "";
  String urlParams = "";
  String newSearchURL = request.getContextPath() + "/cdeBrowse.jsp?performQuery=newSearch&PageId=DataElementsGroup";
  
  if ((src != null) && (!"".equals(src))) {
    modIndex = request.getParameter("moduleIndex");
    quesIndex = request.getParameter("questionIndex");
    doneURL= src+".do?method=displayCDECart&moduleIndex="+modIndex+"&questionIndex="+quesIndex;
    urlParams = "&src="+src+"&method=displayCDECart&moduleIndex="+modIndex+"&questionIndex="+quesIndex;
    newSearchURL += urlParams;
  }
    
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<META HTTP-EQUIV="Expires" CONTENT="Thu, 01 Dec 1994 16:00:00 GMT">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
Data Elements Search - Data Elements
</TITLE>
</HEAD>
<BODY onLoad="turnOff()" topmargin="0">


<SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/jsLib/checkbox.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
function redirect1(detailReqType, linkParms )
{
  var urlString="<%=request.getContextPath()%>/search?dataElementDetails=9" + linkParms + "<%= pageUrl %>"+"&queryDE=yes";
  newBrowserWin(urlString,'deDetails',800,600)
  
}
function goPage(pageNumber, pageInfo) {
  document.location.href ="<%=request.getContextPath()%>" + "/search?searchDataElements=9&"+pageInfo+"&deSearchPageNum="+pageNumber+ "<%= pageUrl %>"+"<%= urlParams %>";
    
}
function clearValueDomain() {
  document.forms[0].jspValueDomain.value = "";
  document.forms[0].txtValueDomain.value = "";
}
function clearDataElementConcept() {
  document.forms[0].jspDataElementConcept.value = "";
  document.forms[0].txtDataElementConcept.value = "";
}
function clearClassSchemeItem() {
  document.forms[0].jspClassification.value = "";
  document.forms[0].txtClassSchemeItem.value = "";
}
function submitForm() {
  if ((document.forms[0].jspAltName.value != "") &&
    (document.forms[0].altName.selectedIndex <=0))
    {
       alert("Please select Alternate Name Type(s) for Alternate Name search");
    }
  else {
     document.forms[0].submit();
  }
}

function clearForm() {
  var version = document.forms[0].jspLatestVersion
  clearValueDomain();
  clearDataElementConcept();
  clearClassSchemeItem();
  document.forms[0].jspKeyword.value = "";
  document.forms[0].jspAltName.value = "";
  document.forms[0].jspCdeId.value = "";
  document.forms[0].jspValidValue.value = "";
  document.forms[0].jspStatus.options[document.forms[0].jspStatus.selectedIndex].value = "ALL";
  document.forms[0].regStatus.options[document.forms[0].regStatus.selectedIndex].value = "ALL";
  document.forms[0].altName.options[document.forms[0].altName.selectedIndex].value = "ALL";
    
}

function turnOn() {
  document.body.style.cursor = "wait";
}

function turnOff() {
  document.body.style.cursor = "default";
  if ("<%= paramType %>" != "") {
    var treeFrame = findFrameByName('tree');
    treeFrame.document.body.style.cursor = "default";
  }
}

turnOn();

function findFrameByName(strName) {
   return findFrame(top, strName);
}

function findFrame(doc, strName) {
 if (doc.frames.length == 0) return;

 for (var i = 0; i != doc.frames.length; i++)
   if (doc.frames[i].name == strName)
     return doc.frames[i];
   else {
     var frm = findFrame(doc.frames[i].window, strName);

     if ( frm  )
       return frm;
   }

 return top;
}

function listChanged(urlInfo) {
  var pgNum = document.forms[0].dePages.options[document.forms[0].dePages.selectedIndex].value
  document.location.href= "search?searchDataElements=9&performQuery=no&deSearchPageNum="+pgNum+"<%= pageUrl %>"+urlInfo;
}

function topListChanged(urlInfo) {
  var pgNum = document.forms[0].dePagesTop.options[document.forms[0].dePagesTop.selectedIndex].value
  document.location.href= "search?searchDataElements=9&performQuery=no&deSearchPageNum="+pgNum+"<%= pageUrl %>"+urlInfo;
}

function crfBuilder() {
  urlString = "search?copyTemplateForm=9&PageId=DataElementsGroup&srcFormIdseq="+"<%= paramIdseq %>";
  top.location.href = urlString;
  
}

function copyForm() {
  urlString = "search?copyTemplateForm=9&PageId=DataElementsGroup&srcFormIdseq="+"<%= paramIdseq %>";
  top.location.href = urlString;
}

function modifyForm() {
  urlString = "search?displayCRFForEdit=9&PageId=DataElementsGroup&formIdseq="+"<%= paramIdseq %>";
  top.location.href = urlString;
}

function ToggleAll(e){
	if (e.checked) {
	    setChecked(1,'selectDE');
	}
	else {
	    setChecked(0,'selectDE');
	}
}

function updateCart() {
  if (validateSelection('selectDE','Please select atleast one data element to add to the CDE Cart')) {
    document.forms[0].performQuery.value = "addToCart";
    document.forms[0].submit();
    return true;
  }
}

function compareCDEs() {
  var numberSelected = validateMultiSelection('selectDE','Please select at least two data elements to compare');
  if (numberSelected > 1) {
  var urlString="<%=request.getContextPath()%>/cdebrowser/compareCDEAction.do?method=compareCDEs&numberSelected=" + numberSelected;
  top.location.href=urlString;
  }
}

function done() {
  top.location.href = "<%=doneURL%>";
}

function newSearch(){
  top.location.href= "<%=newSearchURL%>";
}
//-->
</SCRIPT>
<%@ include  file="cdebrowserCommon_html/tab_include_search.html" %>

<center>
  <h3 class="CDEBrowserPageContext"><%=pageContextInfo%></h3>
</center>
<%
  if (request.getAttribute(ProcessConstants.CDE_CART_ADD_SUCCESS) != null) {
%>
<p class="MessageText">
  <b><%=request.getAttribute(ProcessConstants.CDE_CART_ADD_SUCCESS)%></b>
</p>
<%
  }
%>
<form action="<%= infoBean.getStringInfo("controller") %>" METHOD="POST" NAME="searchForm" onkeypress="if(window.event.keyCode==13){submitForm()};">
<INPUT TYPE="HIDDEN" NAME="NOT_FIRST_DISPLAY" VALUE="1">
<INPUT TYPE="HIDDEN" NAME="SEARCH" VALUE="1">
<INPUT TYPE="HIDDEN" NAME="SEARCH" VALUE="1">
<INPUT TYPE="HIDDEN" NAME="performQuery" VALUE="yes">
<INPUT TYPE="HIDDEN" NAME="src" VALUE="<%=src%>">
<INPUT TYPE="HIDDEN" NAME="moduleIndex" VALUE="<%=modIndex%>">
<INPUT TYPE="HIDDEN" NAME="questionIndex" VALUE="<%=quesIndex%>">
<input type="HIDDEN" name="<%= PageConstants.PAGEID %>" value="<%= infoBean.getPageId()%>"/>

<table width="100%" align="center">
  <tr>
    <td colspan="4" align="left" class="AbbreviatedText">Note: Enter/select search criteria and click search button to initiate search. The wildcard character is *. Click the Help button above for more information on CDEBrowser.</td>
 </tr>
</table>

<table width="100%" align="center">
 
 <tr>
    <td class="OraFieldtitlebold" nowrap>Search For:</td>
    <td class="OraFieldText" nowrap>
      <input type="text" name="jspKeyword" value="<%=desb.getSearchText()%>" size ="20"> 
    </td>

    <td class="OraFieldtitlebold" nowrap>Alternate Name:</td>
    <td class="OraFieldText" nowrap>
      <input type="text" name="jspAltName" value="<%=desb.getAltName()%>" size ="20"> 
    </td>
 </tr>
 <tr>
    <td class="OraFieldtitlebold" nowrap>Search Field(s):</td>
    <td class="OraFieldText"><%=desb.getSearchInList()%></td>
    <td class="OraFieldtitlebold" nowrap>Alternate Name Type(s):</td>
    <td class="OraFieldText"><%=desb.getAltNameList()%></td>
</tr>
 <tr>
     <td class="OraFieldtitlebold" nowrap>Permissible Value:</td>
    <td class="OraFieldText" nowrap>
      <input type="text" name="jspValidValue" value="<%=desb.getValidValue()%>" size ="20"> 
    </td>

 </tr>
 <tr>
    <td class="OraFieldtitlebold" nowrap>Value Domain:</td>
    <td class="OraFieldText" nowrap>
      <input type="text" name="txtValueDomain" 
             value="<%=txtValueDomain%>" readonly onFocus="this.blur();"
             class="LOVField"
             size ="18"
      >
      &nbsp;<a href="<%=valueDomainLOVUrl%>"><html:img page="/i/search_light.gif" border="0" alt="Search for Value Domains" /></a>&nbsp;
      <a href="javascript:clearValueDomain()"><i>Clear</i></a>
      <input type="hidden" name="jspValueDomain" value="<%=desb.getVdIdseq()%>" >
    </td>

    <td class="OraFieldtitlebold" nowrap>Public ID:</td>
    <td class="OraFieldText" nowrap>
      <input type="text" name="jspCdeId" value="<%=desb.getCdeId()%>" > 
    </td>
    
 </tr>
 <tr>
    <td class="OraFieldtitlebold" nowrap>Data Element Concept:</td>
    <td class="OraFieldText" nowrap>
      <input type="text" name="txtDataElementConcept" 
             value="<%=txtDataElementConcept%>" 
             readonly onFocus="this.blur();"
             class="LOVField"
             size ="18"
      >
      &nbsp;<a href="<%=decLOVUrl%>"><html:img page="/i/search_light.gif" border="0" alt="Search for Data Element Concepts" /></a>&nbsp;
      <a href="javascript:clearDataElementConcept()"><i>Clear</i></a>
      <input type="hidden" name="jspDataElementConcept" value="<%=desb.getDecIdseq()%>" >
    </td>

    <td class="OraFieldtitlebold" nowrap>Classification:</td>
    <td class="OraFieldText" nowrap>
      <input type="text" name="txtClassSchemeItem" 
             value="<%=txtClassSchemeItem%>" 
             readonly onFocus="this.blur();"
             class="LOVField"
             size ="18"
      >
      &nbsp;<a href="<%=csLOVUrl%>"><html:img page="/i/search_light.gif" border="0" alt="Search for Classification Scheme Items" /></a>&nbsp;
      <a href="javascript:clearClassSchemeItem()"><i>Clear</i></a>
      <input type="hidden" name="jspClassification" value="<%=desb.getCsCsiIdseq()%>" >
    </td>
 </tr>
 <tr>
    <td class="OraFieldtitlebold" nowrap>Version:</td>
    <td align ="left" nowrap>
      <table>
        <tr>
<%

  if((paramType!=null)&&(paramType.equals("CRF")||paramType.equals("TEMPLATE")))
    {
       if (latestVer.equals(""))
          latestVer = "No";
    }
    
  if (latestVer.equals("Yes") || latestVer.equals("")) {
%>
          <td class="OraFieldText" nowrap>Latest Version<input type="radio" name="jspLatestVersion" value="Yes" checked></td>
          <td class="OraFieldText" nowrap>All Versions<input type="radio" name="jspLatestVersion" value="No"></td>
<%
  }
  else {
%>
          <td class="OraFieldText" nowrap>Latest Version<input type="radio" name="jspLatestVersion" value="Yes"></td>
          <td class="OraFieldText" nowrap>All Versions<input type="radio" name="jspLatestVersion" value="No" checked></td>
<%
  }
%>
        </tr>
      </table>
    </td>
    <td class="OraFieldtitlebold" nowrap>Context Use:</td>
    <td class="OraFieldText" nowrap>
      <%=desb.getContextUseList()%>
    </td>
   
 </tr>
 <tr>
    <td class="OraFieldtitlebold" nowrap>Workflow Status:</td>
    <td class="OraFieldText"><%=desb.getWorkflowList()%></td>
    <td class="OraFieldtitlebold" nowrap>Registration Status:</td>
    <td class="OraFieldText"><%=desb.getRegStatusList()%></td>
</tr>
 
 <TR>
   <td colspan="4" align="center" nowrap> 
     &nbsp;
    </td>
 </TR>
<%
  if ("".equals(src)) {
%>
 <TR>
   <td colspan="4" align="center" nowrap> 
   <table >
    <td colspan="1" align="center" nowrap><a href="javascript:submitForm()"><html:img page="/i/SearchDataElements.gif" border="0" /></a></td>
    <td colspan="1" align="center" nowrap><a href="javascript:clearForm()"><html:img page="/i/clear.gif" border="0" /></a></td>
    <td colspan="1" align="center" nowrap><a href="javascript:newSearch()"><html:img page="/i/newSearchButton.gif" border="0" /></a></td>
    </table>
    </td>
 </TR>
<%
  }
  else {
%>
  <TR>
    <td  nowrap colspan="4" align="center"><a href="javascript:submitForm()"><html:img page="/i/SearchDataElements.gif" border="0" /></a>
    &nbsp;<a href="javascript:clearForm()"><html:img page="/i/clear.gif" border="0" /></a>
    &nbsp; <a href="javascript:newSearch()"><html:img page="/i/newSearchButton.gif" border="0" /></a>
    &nbsp;<a href="javascript:done()"><html:img page="/i/backButton.gif" border="0" /></a>
    &nbsp;
    </td>
 </TR>

<%
  }
%>
</table>
<br>

<%
  if (!queryFlag.equals("")) {
    if (deList!=null&&deList.size()!=0) {
%>
<table cellpadding="0" cellspacing="0" width="100%" align="center">
  <tr>
    <td nowrap>
      <b><a href="<%=downloadExcelURL%>" >[Download Data Elements to Excel]</a></b> &nbsp;&nbsp;
      <b><a href="<%=downloadXMLURL%>" >[Download Data Elements as XML]</a></b> &nbsp;&nbsp;
<%
      if (paramType.equals("TEMPLATE")){
%>
      <b><a href="<%=templateURL%>" target="_blank">[Download Template]</a></b>&nbsp;&nbsp;
      
<%
      }
%>
    </td>
  </tr>
  <tr>
    <td width="100%" nowrap><html:img height="2" page="/i/beigedot.gif" width="99%" align="top" border="0" /> </td>
  </tr>
</table>

<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0">
    <tr>
      <td align="left"><a href="javascript:updateCart()"><html:img page="/i/AddToCDECart.gif" border="0" /></a></td>
      <td align="left"><a href=" "><html:img page="/i/addToCDECompareList.gif" border="0" /></a></td>
      <td align="left"><a href="javascript:compareCDEs()"><html:img page="/i/compareCDEs.gif" border="0" /></a></td>
      <td align="right"><%=topScroller.getScrollerHTML()%></td>
    </tr>
</table>

<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader"><input type="checkbox" name="deList" value="yes" onClick="ToggleAll(this)"/></th>
  	 <th class="OraTableColumnHeader" nowrap>
 		        <cde:sortableColumnHeader
              sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
              ascendingImageUrl='<%=request.getContextPath()+ "/i/sort_up.gif"%>'
              descendingImageUrl='<%=request.getContextPath()+ "/i/sort_down.gif"%>' 
 	       	  actionUrl='<%="/search?performQuery=sortResults" + pageUrl %>'
     	   	  columnHeader="Long Name" 
              orderParamId="sortOrder" 
     	   	  sortFieldId="sortField"
       	     sortFieldValue = "long_name"
            />   
    </th>
    <th class="OraTableColumnHeader">Document Text</th>
    <th class="OraTableColumnHeader">Owned By</th>
    <th class="OraTableColumnHeader">Used By Context</th>
    <th class="OraTableColumnHeader" nowrap>
 	      <cde:sortableColumnHeader
              sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
              ascendingImageUrl='<%=request.getContextPath()+ "/i/sort_up.gif"%>'
              descendingImageUrl='<%=request.getContextPath()+ "/i/sort_down.gif"%>'  
 	       	  actionUrl='<%="/search?performQuery=sortResults" + pageUrl %>'
     	   	  columnHeader="Registration Status" 
              orderParamId="sortOrder" 
     	   	  sortFieldId="sortField"
       	     sortFieldValue = "registration_status"
            />   
    </th>
    <th class="OraTableColumnHeader" nowrap>
 	      <cde:sortableColumnHeader
              sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
              ascendingImageUrl='<%=request.getContextPath()+ "/i/sort_up.gif"%>'
              descendingImageUrl='<%=request.getContextPath()+ "/i/sort_down.gif"%>'
 	       	  actionUrl='<%="/search?performQuery=sortResults" + pageUrl %>'
     	   	  columnHeader="Workflow Status" 
              orderParamId="sortOrder" 
     	   	  sortFieldId="sortField"
       	     sortFieldValue = "asl_name"
            />   
    </th>
    <th class="OraTableColumnHeader">Public ID</th>
    <th class="OraTableColumnHeader">Version</th>
  </tr>
<%
      String pagesDropDown = myScroller.getScrollerHTML();
      for (int i=0; i <deList.size(); i++) {
        DataElement de = (DataElement)deList.get(i);
%>
  <tr class="OraTabledata">
    <td class="OraTableCellSelect"><input type="checkbox" name="selectDE" value="<%=de.getDeIdseq()%>"/></td>
    <td class="OraFieldText"><a href="javascript:redirect1('dataElementDetails','&p_de_idseq=<%=de.getDeIdseq()%>')"><%=de.getLongName()%></a></td>
    <td class="OraFieldText"><%=de.getLongCDEName()%> </td>
    <td class="OraFieldText"><%=de.getContextName()%> </td>
    <td class="OraFieldText"><%=de.getUsingContexts()%> </td>
    <td class="OraFieldText"><%=de.getRegistrationStatus()%> </td>
    <td class="OraFieldText"><%=de.getAslName()%> </td>
    <td class="OraFieldText"><%=de.getCDEId()%> </td>
    <td class="OraFieldText"><%=de.getVersion()%> </td>
    
  </tr>
<%
      }
%>
  </table>
  <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0">
    <tr><td align="right"><%=pagesDropDown%></td></tr>
  </table>
<%
    }
    else if(deList!=null&&deList.size()==0) {
    pageContext.getSession().setAttribute("showCached",new Boolean("true"));
%>
<table cellpadding="0" cellspacing="0" width="100%" align="center">
  <tr>
    <td width="100%"><html:img height="2" page="/i/beigedot.gif" width="99%" align="top" border="0" /> </td>
  </tr>
</table>

<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Long Name</th>
    <th class="OraTableColumnHeader">Document Text</th>
    <th class="OraTableColumnHeader">Owned By</th>
    <th class="OraTableColumnHeader">Used By Context</th>
    <th class="OraTableColumnHeader">Registration</th>
    <th class="OraTableColumnHeader">Workflow Status</th>
    <th class="OraTableColumnHeader">Public ID</th>
    <th class="OraTableColumnHeader">Version</th>
  </tr>
  <tr class="OraTabledata">
         <td colspan="8">No data elements matching the search criteria found.</td>
  </tr>
  </table>
<%
    }
  }
%>
</FORM>
<%@ include file="../common/common_bottom_border.jsp"%>

</BODY>
</HTML>