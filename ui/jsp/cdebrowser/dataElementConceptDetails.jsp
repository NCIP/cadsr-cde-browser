<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.resource.* " %>

<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="cdebrowserCommon_html/SessionAuth.html"%>

<%
  DataElement de = (DataElement)infoBean.getInfo("de");
  DataElementConcept dec = de.getDataElementConcept();
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  String pageId = infoBean.getPageId();
  String pageName = PageConstants.PAGEID;
  String pageUrl = "&"+pageName+"="+pageId;
  Float ocVersion = dec.getObjClassVersion();
  String socVersion;
  if (ocVersion.floatValue() == 0.00f) socVersion = "";
  else socVersion = ocVersion.toString();

  Float ptVersion = dec.getPropertyVersion();
  String sptVersion;
  if (ptVersion.floatValue() == 0.00f) sptVersion = "";
  else sptVersion = ptVersion.toString();
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css">
<TITLE>
Data Element Concept
</TITLE>
</HEAD>
<BODY topmargin="0">



<SCRIPT LANGUAGE="JavaScript">
<!--
function redirect1(detailReqType, linkParms )
{
  document.location.href="search?dataElementDetails=" + linkParms;
  
}
function goPage(pageInfo) {
  document.location.href = "search?searchDataElements=&"+pageInfo;
  
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
    <td class="OraHeaderSubSub" width="100%">Data Element Concept Details</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">

  <tr class="OraTabledata">
    <td class="TableRowPromptText">Public ID:</td>
    <td class="OraFieldText"><%=dec.getPublicId()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Preferred Name:</td>
    <td class="OraFieldText"><%=dec.getPreferredName()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Long Name:</td>
    <td class="OraFieldText"><%=dec.getLongName()%></td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Definition:</td>
    <td class="OraFieldText"><%=dec.getPreferredDefinition()%> </td>
 </tr>

  <tr class="OraTabledata">
    <td class="TableRowPromptText">Context:</td>
    <td class="OraFieldText"><%=dec.getContextName()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Workflow Status:</td>
    <td class="OraFieldText"><%=dec.getAslName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Version:</td>
    <td class="OraFieldText"><%=dec.getVersion()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Conceptual Domain Preferred Name:</td>
    <td class="OraFieldText"><%=dec.getCDPrefName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Conceptual Domain Context Name:</td>
    <td class="OraFieldText"><%=dec.getCDContextName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Conceptual Domain Version:</td>
    <td class="OraFieldText"><%=dec.getCDVersion()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Object Class Preferred Name:</td>
    <td class="OraFieldText"><%=dec.getObjClassPrefName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Object Class Context:</td>
    <td class="OraFieldText"><%=dec.getObjClassContextName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Object Class Version:</td>
    <td class="OraFieldText"><%=socVersion%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Object Class Qualifier:</td>
    <td class="OraFieldText"><%=dec.getObjClassQualifier()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Property Preferred Name:</td>
    <td class="OraFieldText"><%=dec.getPropertyPrefName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Property Context:</td>
    <td class="OraFieldText"><%=dec.getPropertyContextName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Property Version:</td>
    <td class="OraFieldText"><%=sptVersion%> </td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Property Qualifier:</td>
    <td class="OraFieldText"><%=dec.getPropertyQualifier()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Origin:</td>
    <td class="OraFieldText"><%=dec.getOrigin()%> </td>
 </tr>
 
</table>
</form>

<%@ include  file="cdebrowserCommon_html/bottom_border.html" %>

</BODY>
</HTML>

