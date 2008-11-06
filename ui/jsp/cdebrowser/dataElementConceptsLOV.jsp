<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.ProcessConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.lov.DataElementConceptsLOVBean " %>
<%@page import="org.apache.commons.lang.StringEscapeUtils" %>
<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="cdebrowserCommon_html/SessionAuth.html"%>

<%
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  DataElementConceptsLOVBean declb = (DataElementConceptsLOVBean)infoBean.getInfo(ProcessConstants.DEC_LOV);
  CommonLOVBean clb = declb.getCommonLOVBean();
    
  String pageId = StringEscapeUtils.escapeJavaScript(infoBean.getPageId());
  String pageName = StringEscapeUtils.escapeJavaScript(PageConstants.PAGEID);
  String pageUrl = StringEscapeUtils.escapeJavaScript("&"+pageName+"="+pageId);

%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
List of Values - Data Element Concepts
</TITLE>
</HEAD>
<BODY topmargin="0">



<SCRIPT LANGUAGE="JavaScript">
<!--
function passback(P_ID, P_NAME) {
   opener.document.forms[0].<%= StringEscapeUtils.escapeJavaScript(clb.getJsName()) %>.value = P_NAME;
   opener.document.forms[0].<%= StringEscapeUtils.escapeJavaScript(clb.getJsId()) %>.value = P_ID;
   opener.document.forms[0].<%= StringEscapeUtils.escapeJavaScript(clb.getJsName()) %>.focus();
   close();
}

function closeOnClick() {
    close();
}

function goPage(pageInfo) {  
  document.location.href = "<%=StringEscapeUtils.escapeHtml("search?dataElementConceptsLOV=9&")%>"+pageInfo+"<%=pageUrl%>";    
}
  
//-->
</SCRIPT>
<%@ include  file="cdebrowserCommon_html/tab_include_lov.html" %>
<center>
<p class="OraHeaderSubSub">Data Element Concepts </p>
</center>

<form method="POST" ENCTYPE="application/x-www-form-urlencoded" action="<%= infoBean.getStringInfo("controller") %>">
<input type="HIDDEN" name="<%= StringEscapeUtils.escapeHtml(PageConstants.PAGEID) %>" value="<%= StringEscapeUtils.escapeHtml(infoBean.getPageId())%>"/>
<INPUT TYPE="HIDDEN" NAME="NOT_FIRST_DISPLAY" VALUE="<%=StringEscapeUtils.escapeHtml("1")%>"/>
<INPUT TYPE="HIDDEN" NAME="idVar" VALUE="<%= StringEscapeUtils.escapeHtml(clb.getJsId()) %>"/>
<INPUT TYPE="HIDDEN" NAME="nameVar" VALUE="<%= StringEscapeUtils.escapeHtml(clb.getJsName()) %>"/>
<INPUT TYPE="HIDDEN" NAME="dataElementConceptsLOV" VALUE="<%=StringEscapeUtils.escapeHtml("9")%>"/>
<p align="left">
<font face="Arial, Helvetica, sans-serif" size="-1" color="#336699">
  Please enter a keyword. This search will display all data element concepts which have
  the search criteria in their long name or short name. Wildcard character is *.
</font>
</p>
<center>
<table>
<%= StringEscapeUtils.escapeHtml(clb.getSearchFields()) %>
<tr>
  <td class="fieldtitlebold">Restrict Search to Current Context</td>
<%
  if (clb.isFirstDisplay()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="<%=StringEscapeUtils.escapeJavaScript("yes")%>" CHECKED /></td>
<%
  }
  else {
    if (declb.getIsContextSpecific()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="<%=StringEscapeUtils.escapeJavaScript("yes")%>" CHECKED /></td>
<%
    }
    else if (!declb.getIsContextSpecific()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="<%=StringEscapeUtils.escapeJavaScript("yes")%>" /></td>
<%
    }
  }
%>
</tr>

<TR>
  <TD></TD>
  <TD><input type=submit name="submit" value="<%=StringEscapeUtils.escapeJavaScript("Find")%>"/>&nbsp;
  <INPUT type="button" value="Close" onclick="javascript:closeOnClick()"/></TD>
</TR>
</table>

<% 
  if (clb.getTotalRecordCount() != 0) {
%>
<%= clb.getHitList() %>

<p class="OraFieldText">Total Record Count:<B> <%= clb.getTotalRecordCount() %></B></p>
<P>
<%= clb.getPageInfo() %>
<%
  }
  else {
    if (!clb.isFirstDisplay()) {
%>
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Short Name</th>
    <th class="OraTableColumnHeader">Long Name</th>
    <th class="OraTableColumnHeader">Context</th>
    <th class="OraTableColumnHeader">Version</th>
    <th class="OraTableColumnHeader">Workflow Status</th>
    <th class="OraTableColumnHeader">Preferred Definition</th>
  </tr>
  <tr class="OraTabledata">
         <td colspan="6">No data element concepts match the search criteria</td>
  </tr>
  </table>
<%
    }
  }
%>
</center>
</form>

<%@ include file="../common/common_bottom_border.jsp"%>
</BODY>
</HTML>

