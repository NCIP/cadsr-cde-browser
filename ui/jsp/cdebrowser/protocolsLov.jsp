<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.resource.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.lov.ProtocolsLOVBean " %>
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants" %>

<%@include  file="cdebrowserCommon_html/SessionAuth.html"%>

<%
  TabInfoBean tib = 
    (TabInfoBean)session.getAttribute(FormConstants.PROTOCOLS_LOV_TAB_BEAN);
  ProtocolsLOVBean protolb = 
    (ProtocolsLOVBean)session.getAttribute(FormConstants.PROTOCOLS_LOV_BEAN);
  CommonLOVBean clb = protolb.getCommonLOVBean();
    
  String pageName = "PageId";
  String pageId = "DataElementsGroup";
  String pageUrl = "&"+pageName+"="+pageId;

%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css">
<TITLE>
List of Values - Protocols
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
  document.location.href = "/cdebrowser/formLOVAction.do?method=getProtocolsLOV&"+pageInfo + "<%= pageUrl %>";
    
}
  
//-->
</SCRIPT>
<%@ include  file="cdebrowserCommon_html/tab_include_lov.html" %>
<center>
<p class="OraHeaderSubSub">Protocols </p>
</center>

<form method="POST" action="/cdebrowser/formLOVAction.do">
<INPUT TYPE="HIDDEN" NAME="NOT_FIRST_DISPLAY" VALUE="1">
<INPUT TYPE="HIDDEN" NAME="idVar" VALUE="<%= clb.getJsId() %>">
<INPUT TYPE="HIDDEN" NAME="nameVar" VALUE="<%= clb.getJsName() %>">
<INPUT TYPE="HIDDEN" NAME="method" VALUE="getProtocolsLOV">

<p align="left">
<font face="Arial, Helvetica, sans-serif" size="-1" color="#336699">
  Please enter a keyword. This search will display all protocols which have
  the search criteria in their long name or preferred name. Wildcard character is *.
</font>
</p>
<center>
<table>
<%= clb.getSearchFields() %>
<tr>

  <% 
    String chkContext = (String)request.getAttribute("chkContext");
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
    <th class="OraTableColumnHeader">Preferred Name</th>
    <th class="OraTableColumnHeader">Long Name</th>
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

<%@ include  file="cdebrowserCommon_html/bottom_border_lov.html" %>
</BODY>
</HTML>