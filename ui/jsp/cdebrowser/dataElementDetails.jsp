<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.resource.* " %>
<%@page import="java.util.List" %>

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
Data Element Details
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
    <td class="OraHeaderSubSub" width="100%">Data Element Details</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">

 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Public ID:</td>
    <td class="OraFieldText"><%=de.getPublicId()%></td>
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
    <td class="TableRowPromptText" width="20%">Preferred Name:</td>
    <td class="OraFieldText"><%=de.getPreferredName()%></td>
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
    <td class="TableRowPromptText">Value Domain:</td>
    <td class="OraFieldText"><%=de.getValueDomain().getLongName()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Data Element Concept:</td>
    <td class="OraFieldText"><%=de.getDataElementConcept().getLongName()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Context:</td>
    <td class="OraFieldText"><%=de.getContextName()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Workflow Status:</td>
    <td class="OraFieldText"><%=de.getAslName()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Origin:</td>
    <td class="OraFieldText"><%=de.getOrigin()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Registration Status:</td>
    <td class="OraFieldText"><%=de.getRegistrationStatus()%> </td>
 </tr>
 
</table>

<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%">Reference Documents</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Document Name</th>
    <th class="OraTableColumnHeader">Document Type</th>
    <th class="OraTableColumnHeader">Document Text</th>
    <th class="OraTableColumnHeader">Context</th>
    <th class="OraTableColumnHeader">URL</th>
  </tr>
<%
  ReferenceDocument rd;
  List refDocs = de.getRefereceDocs();
  int numberOfDocs = refDocs.size();
  if (numberOfDocs > 0) {
    for (int i=0;i<numberOfDocs; i++) {
      rd = (ReferenceDocument)refDocs.get(i);
%>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=rd.getDocName()%> </td>
        <td class="OraFieldText"><%=rd.getDocType()%> </td>
        <td class="OraFieldText"><%=rd.getDocText()%> </td>
        <td class="OraFieldText">
         <% if (rd.getContext() != null) {
         %><%=rd.getContext().getName()%> <% } %></td>
        <td class="OraFieldText"><a href="<%=rd.getUrl()%>" target="AuxWindow"> <%=rd.getUrl()%> </a></td>
      </tr>
<%
    }
  }
  else {
%>
       <tr class="OraTabledata">
         <td colspan="5">There are no reference documents for the selected CDE.</td>
       </tr>
<%
  }
%>
</table>

<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%">Alternate Names</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Name</th>
    <th class="OraTableColumnHeader">Type</th>
    <th class="OraTableColumnHeader">Context</th>
    <th class="OraTableColumnHeader">Language</th>
  </tr>
<%
  Designation des;
  List desigs = de.getDesignations();
  int numberOfDes = desigs.size();
  if (numberOfDes > 0) {
    for (int i=0;i<numberOfDes; i++) {
      des = (Designation)desigs.get(i);
%>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=des.getName()%> </td>
        <td class="OraFieldText"><%=des.getType()%> </td>
        <td class="OraFieldText"><%=des.getContext().getName()%> </td>
        <td class="OraFieldText"><%=des.getLanguage()%> </td>
      </tr>
<%
    }
  }
  else {
%>
       <tr class="OraTabledata">
         <td colspan=4">There are no alternate names for the selected CDE.</td>
       </tr>
<%
  }
%>
</table>


</form>

<%@ include file="../common/common_bottom_border.jsp"%>

</BODY>
</HTML>

