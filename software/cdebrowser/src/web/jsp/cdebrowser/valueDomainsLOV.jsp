<%--L
  Copyright Oracle Inc, SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
L--%>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>

<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%//@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.ProcessConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.lov.ValueDomainsLOVBean " %>
<%@page import="org.apache.commons.lang.StringEscapeUtils" %>
<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="cdebrowserCommon_html/SessionAuth.html"%>

<%
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  ValueDomainsLOVBean vlb = (ValueDomainsLOVBean)infoBean.getInfo(ProcessConstants.VD_LOV);
  CommonLOVBean clb = vlb.getCommonLOVBean();
    
  String pageId = StringEscapeUtils.escapeJavaScript(infoBean.getPageId());
  String pageName = StringEscapeUtils.escapeJavaScript(PageConstants.PAGEID);
  String pageUrl = StringEscapeUtils.escapeJavaScript("&"+pageName+"="+pageId);
  CDEBrowserParams params = CDEBrowserParams.getInstance();  
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
List of Values - Value Domains
</TITLE>
</HEAD>
<BODY topmargin="0">



<SCRIPT LANGUAGE="JavaScript">
<!--
function passback(P_ID, P_NAME) {
   opener.document.forms[0].txtValueDomain.value = P_NAME;   
   opener.document.forms[0].jspValueDomain.value = P_ID;
   opener.document.forms[0].txtValueDomain.focus();
   close();
}

function closeOnClick() {
    close();
}

function goPage(pageInfo) {
  document.location.href = "<%=StringEscapeUtils.escapeHtml("search?valueDomainsLOV=9&")%>"+pageInfo+"<%=pageUrl%>";
}


  
//-->
</SCRIPT>
<%@ include  file="cdebrowserCommon_html/tab_include_lov.html" %>
<center>
<p class="OraHeaderSubSub">Value Domains </p>
</center>
<form method="POST" ENCTYPE="application/x-www-form-urlencoded" action="<%= infoBean.getStringInfo("controller") %>">
<INPUT type="HIDDEN" name="<%= PageConstants.PAGEID %>" value="<%= StringEscapeUtils.escapeHtml(infoBean.getPageId())%>"/>
<INPUT TYPE="HIDDEN" NAME="NOT_FIRST_DISPLAY" VALUE= "<%=StringEscapeUtils.escapeHtml("1")%>" />
<INPUT TYPE="HIDDEN" NAME="idVar" VALUE="<%= StringEscapeUtils.escapeHtml("jspValueDomain") %>"/>
<INPUT TYPE="HIDDEN" NAME="nameVar" VALUE="<%= StringEscapeUtils.escapeHtml("txtValueDomain") %>" />
<INPUT TYPE="HIDDEN" NAME="valueDomainsLOV" VALUE="<%=StringEscapeUtils.escapeHtml("9")%>"/>
<p align="left">
<font face="Arial, Helvetica, sans-serif" size="-1" color="#336699">
  Please enter a keyword. This search will display all value domains which have
  the search criteria in their long name or short name. Wildcard character is *.
</font>
</p>
<center>
<table>
<%= clb.getSearchFields() %>
<tr>
	<td class="fieldtitlebold">Type</td>

<%
String enumChkd = "";
String nonEnumChkd = "";
String bothChkd = "";

  if (clb.isFirstDisplay() 
		  || (vlb.getSearchEnumerated() && vlb.getSearchNonEnumerated())) {
	  bothChkd = "checked";
	  enumChkd = "";
	  nonEnumChkd = "";
  }
  else if (vlb.getSearchEnumerated()) {
	  bothChkd = "";
	  enumChkd = "checked";
	  nonEnumChkd = "";
  }
  else if (vlb.getSearchNonEnumerated()) {
	  bothChkd = "";
	  enumChkd = "";
	  nonEnumChkd = "checked";
  }
%>
	<td class="OraFieldText">
		<input type="radio" name="enum" value="enum" id="enum" <%= enumChkd %>/> <label for="enum">Enumerated</label><br/>
		<input type="radio" name="enum" value="nonenum" id="nonenum" <%= nonEnumChkd %> /> <label for="nonenum">Non Enumerated</label><br/>
		<input type="radio" name="enum" value="both" id="both" <%= bothChkd %> /> <label for="both">Both</label>
	</td>
</tr>
<tr>
  <td class="fieldtitlebold">Restrict Search to Current Context</td>
<%
  if (clb.isFirstDisplay()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="<%=StringEscapeUtils.escapeJavaScript("yes")%>" CHECKED /></td>
<%
  }
  else {
    if (vlb.getIsContextSpecific()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="<%=StringEscapeUtils.escapeJavaScript("yes")%>" CHECKED /></td>
<%
    }
    else if (!vlb.getIsContextSpecific()) {
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
         <td colspan="6">No value domains match the search criteria</td>
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

