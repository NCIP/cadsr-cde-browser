<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.html.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.resource.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants " %>
<%@page import="java.util.List " %>
<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="cdebrowserCommon_html/SessionAuth.html"%>

<%
  DataElement de = (DataElement)infoBean.getInfo("de");
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  ValueDomain vd = de.getValueDomain();
  String pageId = infoBean.getPageId();
  String pageName = PageConstants.PAGEID;
  String pageUrl = "&"+pageName+"="+pageId;
  HTMLPageScroller scroller = (HTMLPageScroller)
                infoBean.getInfo(ProcessConstants.VALID_VALUES_PAGE_SCROLLER);
  
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
Permissible Values
</TITLE>
</HEAD>
<BODY topmargin="0">



<SCRIPT LANGUAGE="JavaScript">
<!--
function redirect1(detailReqType, linkParms ){
  document.location.href="search?dataElementDetails=" + linkParms;
}
function goPage(pgNum,urlInfo) {
  document.location.href= "search?listValidValuesForDataElements=9&tabClicked=2&vvPageNumber="+pgNum+"<%= pageUrl %>"+urlInfo;
}
function listChanged(urlInfo) {
  var pgNum = document.forms[0].vv_pages.options[document.forms[0].vv_pages.selectedIndex].value
  document.location.href= "search?listValidValuesForDataElements=9&tabClicked=2&performQuery=no&vvPageNumber="+pgNum+"<%= pageUrl %>"+urlInfo;
}
  
//-->
</SCRIPT>
<%@ include  file="cdebrowserCommon_html/tab_include.html" %>
<form method="POST" ENCTYPE="application/x-www-form-urlencoded" action="<%= infoBean.getStringInfo("controller") %>">
<input type="HIDDEN" name="<%= PageConstants.PAGEID %>" value="<%= infoBean.getPageId()%>"/>

<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Selected Data Element</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Public ID:</td>
    <td class="OraFieldText"><%=de.getCDEId()%></td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Preferred Name:</td>
    <td class="OraFieldText"><%=de.getPreferredName()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Long Name:</td>
    <td class="OraFieldText"><%=de.getLongName()%></td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Document Text:</td>
    <td class="OraFieldText"><%=de.getLongCDEName()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Definition:</td>
    <td class="OraFieldText"><%=de.getPreferredDefinition()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Workflow Status:</td>
    <td class="OraFieldText"><%=de.getAslName()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Version:</td>
    <td class="OraFieldText"><%=de.getVersion()%> </td>
 </tr>
 
</table>
<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Value Domain Details</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">

  <tr class="OraTabledata">
    <td class="TableRowPromptText">Public ID:</td>
    <td class="OraFieldText"><%=vd.getPublicId()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Preferred Name:</td>
    <td class="OraFieldText"><%=vd.getPreferredName()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Long Name:</td>
    <td class="OraFieldText"><%=vd.getLongName()%></td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Definition:</td>
    <td class="OraFieldText"><%=vd.getPreferredDefinition()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Workflow Status:</td>
    <td class="OraFieldText"><%=vd.getAslName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Version:</td>
    <td class="OraFieldText"><%=vd.getVersion()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Datatype:</td>
    <td class="OraFieldText"><%=vd.getDatatype()%></td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Unit of Measure:</td>
    <td class="OraFieldText"><%=vd.getUnitOfMeasure()%></td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Display Format:</td>
    <td class="OraFieldText"><%=vd.getDisplayFormat()%></td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Maximum Length:</td>
    <td class="OraFieldText"><%=vd.getMaxLength()%></td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Minimum Length:</td>
    <td class="OraFieldText"><%=vd.getMinLength()%></td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Decimal Place:</td>
    <td class="OraFieldText"><%=vd.getDecimalPlace()%></td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">High Value:</td>
    <td class="OraFieldText"><%=vd.getHighValue()%></td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Low Value:</td>
    <td class="OraFieldText"><%=vd.getLowValue()%></td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Value Domain Type:</td>
    <td class="OraFieldText"><%=vd.getVDType()%></td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Conceptual Domain Preferred Name:</td>
    <td class="OraFieldText"><%=vd.getCDPrefName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Conceptual Domain Context Name:</td>
    <td class="OraFieldText"><%=vd.getCDContextName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Conceptual Domain Version:</td>
    <td class="OraFieldText"><%=vd.getCDVersion()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Origin:</td>
    <td class="OraFieldText"><%=vd.getOrigin()%> </td>
 </tr>
  
</table>

<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%">Permissible Values</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>

<%
  if ("Enumerated".equals(vd.getVDType())) {
    List vv = (List)infoBean.getInfo(ProcessConstants.VALID_VALUES_LIST);
    int numberOfValidValues = vv.size();
    if (numberOfValidValues > 0) {
      /*StringBuffer vvPageList = (StringBuffer)
                      infoBean.getInfo(ProcessConstants.VALID_VALUES_PAGE_LIST);*/
        String vvPageList = scroller.getScrollerHTML();
%>

<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0">
  <tr><td align="right"><%=vvPageList%></td></tr>
</table>
<%
  }
%>
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Value</th>
    <th class="OraTableColumnHeader">Value Meaning</th>
    <th class="OraTableColumnHeader">Value Meaning Description</th>
  </tr>
<%
  ValidValue validValue;
  if (numberOfValidValues > 0) {
    for (int i=0;i<numberOfValidValues; i++) {
      validValue = (ValidValue)vv.get(i);
%>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=validValue.getShortMeaningValue()%> </td>
        <td class="OraFieldText"><%=validValue.getShortMeaning()%> </td>
        <td class="OraFieldText"><%=validValue.getDescription()%> </td>
      </tr>
<%
    }
  }
  else {
%>
       <tr class="OraTabledata">
         <td colspan="3">There are no permissible values for the selected CDE.</td>
       </tr>
<%
  }
%>
</table>
<%
  }
  else {
%>
<center>
  <p class="OraHeaderSubSub">This Value Domain is Non Enumerated</p>
</center>

<%
  }
%>
</form>

<%@ include file="../common/common_bottom_border.jsp"%>

</BODY>
</HTML>

