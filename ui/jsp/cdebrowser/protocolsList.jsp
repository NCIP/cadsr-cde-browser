<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>

<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%//@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.html.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.ProcessConstants " %>
<%@page import="java.util.List " %>
<%@page import="java.util.Iterator " %>

<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="cdebrowserCommon_html/SessionAuth.html"%>

<%
  DataElement de = (DataElement)infoBean.getInfo("de");
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  String pageId = infoBean.getPageId();
  String pageName = PageConstants.PAGEID;
  String pageUrl = "&"+pageName+"="+pageId;
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
Usage
</TITLE>
</HEAD>
<BODY topmargin="0">



<SCRIPT LANGUAGE="JavaScript">
<!--
function redirect1(detailReqType, linkParms ){
  document.location.href="search?dataElementDetails=" + linkParms;
}

function goPage(pgNum,urlInfo) {
  document.location.href= "search?protocolsForDataElements=9&tabClicked=4&deFrmPageNum="+pgNum+"<%= pageUrl %>"+urlInfo;
}
function listChanged(urlInfo) {
  var pgNum = document.forms[0].usages_pages.options[document.forms[0].usages_pages.selectedIndex].value
  document.location.href= "search?protocolsForDataElements=9&tabClicked=4&performQuery=no&deFrmPageNum="+pgNum+"<%= pageUrl %>"+urlInfo;
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
    <td class="TableRowPromptText">Version:</td>
    <td class="OraFieldText"><%=de.getVersion()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Long Name:</td>
    <td class="OraFieldText"><%=de.getLongName()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Short Name:</td>
    <td class="OraFieldText"><%=de.getPreferredName()%></td>
 </tr>
 


 <tr class="OraTabledata">
    <td class="TableRowPromptText">Preferred Question Text:</td>
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

 
</table>
<br>

<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%">Form Usage</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>

<center>
<%
  List usages = (List)infoBean.getInfo(ProcessConstants.DE_USAGES_LIST);
  if (usages.size() > 0) {
    HTMLPageScroller scroller = (HTMLPageScroller)
                  infoBean.getInfo(ProcessConstants.DE_FORM_PAGE_SCROLLER);
    String scrollerHTML = scroller.getScrollerHTML();
%>

<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0">
  <tr><td align="right"><%=scrollerHTML%></td></tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Protocol Number</th>
    <th class="OraTableColumnHeader">Lead Org</th>
    <th class="OraTableColumnHeader">Form Name</th>
    <th class="OraTableColumnHeader">Question Name</th>
    <th class="OraTableColumnHeader">Form Usage Type</th>
  </tr>
<%

    Iterator iter = usages.iterator();
    while (iter.hasNext()) {
      DataElementFormUsage frmUsage = (DataElementFormUsage)iter.next();
  
%>
  <tr class="OraTabledata">
    <td class="OraFieldText"><%=frmUsage.getProtocolLongName()%> </td>
    <td class="OraFieldText"><%=frmUsage.getProtocolLeadOrg()%> </td>
    <td class="OraFieldText"><%=frmUsage.getFormLongName()%> </td>
    <td class="OraFieldText"><%=frmUsage.getQuestionLongName()%> </td>
    <td class="OraFieldText"><%=frmUsage.getUsageType()%> </td>
  </tr>
<%
    }
  }
  else {
%>
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Protocol Number</th>
    <th class="OraTableColumnHeader">Lead Org</th>
    <th class="OraTableColumnHeader">Used By</th>
    <th class="OraTableColumnHeader">Form Usage Type</th>
  </tr>
  <tr class="OraTabledata">
         <td colspan="4">No Form usages exist for the selected CDE.</td>
  </tr>
  </table>
<%
  }
%>
</center>

</form>

<%@ include file="../common/common_bottom_border.jsp"%>

</BODY>
</HTML>

