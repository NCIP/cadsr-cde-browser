<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.resource.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.lov.ClassificationsLOVBean " %>

<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="cdebrowserCommon_html/SessionAuth.html"%>

<%
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  ClassificationsLOVBean cslb = (ClassificationsLOVBean)infoBean.getInfo(ProcessConstants.CS_LOV);
  CommonLOVBean clb = cslb.getCommonLOVBean();
    
  String pageId = infoBean.getPageId();
  String pageName = PageConstants.PAGEID;
  String pageUrl = "&"+pageName+"="+pageId;

%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css">
<TITLE>
List of Values - Classifications
</TITLE>
</HEAD>
<BODY>



<SCRIPT LANGUAGE="JavaScript">
<!--
function passback(P_ID, P_NAME) {
   opener.document.forms[0].<%= clb.getJsName() %>.value = P_NAME;
   opener.document.forms[0].<%= clb.getJsId() %>.value = P_ID;
   opener.document.forms[0].<%= clb.getJsName() %>.focus();
   close();
}

function closeOnClick() {
    close();
}

function goPage(pageInfo) {
  document.location.href = "search?classificationsLOV=9&"+pageInfo + "<%= pageUrl %>";
}


  
//-->
</SCRIPT>
<%@ include  file="cdebrowserCommon_html/tab_include_lov.html" %>
<center>
<p class="OraHeaderSubSub">Classifications </p>
</center>
<form method="POST" ENCTYPE="application/x-www-form-urlencoded" action="<%= infoBean.getStringInfo("controller") %>">
<input type="HIDDEN" name="<%= PageConstants.PAGEID %>" value="<%= infoBean.getPageId()%>"/>
<INPUT TYPE="HIDDEN" NAME="NOT_FIRST_DISPLAY" VALUE="1">
<INPUT TYPE="HIDDEN" NAME="idVar" VALUE="<%= clb.getJsId() %>">
<INPUT TYPE="HIDDEN" NAME="nameVar" VALUE="<%= clb.getJsName() %>">
<INPUT TYPE="HIDDEN" NAME="classificationsLOV" VALUE="9">
<p align="left">
<font face="Arial, Helvetica, sans-serif" size="-1" color="#336699">
  Please enter the search criteria. Wildcard character is *.
</font>
</p>
<center>
<table>
<%= clb.getSearchFields() %>
<tr>
  <td class="fieldtitlebold">Restrict Search to Current Context</td>
<%
  if (clb.isFirstDisplay()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" CHECKED /></td>
<%
  }
  else {
    if (cslb.getIsContextSpecific()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" CHECKED /></td>
<%
    }
    else if (!cslb.getIsContextSpecific()) {
%>
  <td class="OraFieldText"><input type="checkbox" name="chkContext" value="yes" /></td>
<%
    }
  }
%>
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
  <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Class Scheme Item</th>
    <th class="OraTableColumnHeader">Preferred Name</th>
    <th class="OraTableColumnHeader">Long Name</th>
    <th class="OraTableColumnHeader">Context</th>
    <th class="OraTableColumnHeader">Version</th>
    <th class="OraTableColumnHeader">Workflow Status</th>
    <th class="OraTableColumnHeader">Preferred Definition</th>
    
  </tr>
  <tr class="OraTabledata">
         <td colspan="7">No classification scheme items match the search criteria</td>
  </tr>
  </table>
<%
    }
  }
%>
</center>
</form>

<%@ include  file="cdebrowserCommon_html/bottom_border_lov.html" %>
</BODY>
</HTML>

