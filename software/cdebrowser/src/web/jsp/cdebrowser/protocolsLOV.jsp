<%--L
  Copyright SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.

  Portions of this source file not modified since 2008 are covered by:

  Copyright 2000-2008 Oracle, Inc.

  Distributed under the caBIG Software License.  For details see
  http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
L--%>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.ProcessConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.lov.ProtocolsLOVBean " %>
<%@page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@include  file="cdebrowserCommon_html/SessionAuth.html"%>

<%
  TabInfoBean tib = 
    (TabInfoBean)session.getAttribute(CaDSRConstants.PROTOCOLS_LOV_TAB_BEAN);
  ProtocolsLOVBean protolb = 
    (ProtocolsLOVBean)session.getAttribute(CaDSRConstants.PROTOCOLS_LOV_BEAN);
  CommonLOVBean clb = protolb.getCommonLOVBean();
    
  String pageName = "PageId";
  String pageId = "DataElementsGroup";
  String pageUrl = StringEscapeUtils.escapeJavaScript("&"+pageName+"="+pageId);
  CDEBrowserParams params = CDEBrowserParams.getInstance();
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
List of Values - Protocols
</TITLE>
</HEAD>
<BODY>



<SCRIPT LANGUAGE="JavaScript">
<!--
function passback(P_ID, P_NAME) {
   opener.document.forms[0].<%= StringEscapeUtils.escapeHtml(clb.getJsName()) %>.value = P_NAME;
   opener.document.forms[0].<%= StringEscapeUtils.escapeHtml(clb.getJsId()) %>.value = P_ID;
   opener.document.forms[0].<%= StringEscapeUtils.escapeHtml(clb.getJsName()) %>.focus();
   close();
}

function closeOnClick() {
    close();
}

function goPage(pageInfo) {
  document.location.href = "formLOVAction.do?method=getProtocolsLOV&"+pageInfo + "<%= StringEscapeUtils.escapeHtml(pageUrl) %>";
    
}
  
//-->
</SCRIPT>
<%@ include  file="cdebrowserCommon_html/tab_include_lov.html" %>
<center>
<p class="OraHeaderSubSub">Protocols </p>
</center>

<form method="POST" action="formLOVAction.do">
<INPUT TYPE="HIDDEN" NAME="NOT_FIRST_DISPLAY" VALUE="1">
<INPUT TYPE="HIDDEN" NAME="idVar" VALUE="<%= StringEscapeUtils.escapeHtml(clb.getJsId()) %>">
<INPUT TYPE="HIDDEN" NAME="nameVar" VALUE="<%= StringEscapeUtils.escapeHtml(clb.getJsName()) %>">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="getProtocolsLOV">
<INPUT TYPE="HIDDEN" NAME="contextIdSeq" value="<%= StringEscapeUtils.escapeHtml((String)request.getAttribute("contextIdSeq")) %>">

<p align="left">
<font face="Arial, Helvetica, sans-serif" size="-1" color="#336699">
  Please enter a keyword. This search will display all protocols which have
  the search criteria in their long name or short name. Wildcard character is *.
</font>
</p>
<center>
<table>
<%= clb.getSearchFields() %>
<tr>

  <% 
    String chkContext = StringEscapeUtils.escapeJavaScript((String)request.getAttribute("chkContext"));
    if((chkContext == null) || (!chkContext.equals("true"))) {
  %>
  <td class="fieldtitlebold">Restrict Search to Current Context</td>

<%
  if (clb.isFirstDisplay()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" CHECKED /></td>
<%
  }
  else {
    if (protolb.getIsContextSpecific()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" CHECKED /></td>
<%
    }
    else if (!protolb.getIsContextSpecific()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" /></td>
<%
    }
  }
} else {
%>
<INPUT type="HIDDEN" NAME="chkContext" value="true"/>
<% } %>
</tr>

<TR>
  <TD></TD>
  <TD><input type=submit name="submit" value="Find">&nbsp;
  <INPUT type="button" value="Close" onclick="javascript:closeOnClick()"></TD>
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
    <th class="OraTableColumnHeader">Long Name</th>
    <th class="OraTableColumnHeader">Short Name</th>
    <th class="OraTableColumnHeader">Context</th>
    <th class="OraTableColumnHeader">Version</th>
    <th class="OraTableColumnHeader">Workflow Status</th>
    <th class="OraTableColumnHeader">Preferred Definition</th>
  </tr>
  <tr class="OraTabledata">
         <td colspan="6">No protocol matches the search criteria</td>
  </tr>
  </table>
<%
    }
  }
%>
</center>
</form>

<%@ include file="/jsp/common/common_bottom_border.jsp"%>
</BODY>
</HTML>