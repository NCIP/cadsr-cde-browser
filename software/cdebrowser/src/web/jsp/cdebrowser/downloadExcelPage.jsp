<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%
  String source = request.getParameter("src");
String downloadIDs = request.getParameter("downloadIDs");
%>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>Excel Download</TITLE>
</HEAD>
<BODY onLoad= "goPage()">
<SCRIPT LANGUAGE="JavaScript">
<!--
function goPage() {
	document.forms[0].submit();
}
function closeWindow() {
  close();
}
//-->
</SCRIPT>
<form name="downloadForm" action="/CDEBrowser/search" method="post">
<br>
<p class="OraHeaderSubSub">Downloading data elements to Excel...</p>

<p><font face="Arial, Helvetica, sans-serif" size="-1" color="#336699">This 
operation may take a few minutes, so please do not close this status window while 
it is in progress. You may close this status window after the download is 
complete.</font></p>

<input type="hidden" name="excelDownload" value="9" />
<input type="hidden" name="PageId" value="DataElementsGroup" />
<input type="hidden" name="src" value="<%= StringEscapeUtils.escapeJavaScript(source) %>" />
<input type="hidden" name="downloadIDs" value="<%= downloadIDs %>" />
</form>
</BODY>
</HTML>
