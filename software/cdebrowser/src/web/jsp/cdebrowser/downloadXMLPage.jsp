<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%
	String source = request.getParameter("src");
	String downloadIDs = request.getParameter("downloadIDs");
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>XML Download</TITLE>
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

<p class="OraHeaderSubSub">Retrieving data elements as XML...</p> 

<p>
<font face="Arial, Helvetica, sans-serif" size="-1" color="#336699">
This operation may take a few minutes, so please do not close this
status window while it is in progress. Please wait until the File Save
dialog appears, then select "Save" (not "Open").  Once the file has been
saved to your local drive, you may close this status window and open the
saved file in any text or XML editor.
</font>
</p>

<input type="hidden" name="xmlDownload" value="9" />
<input type="hidden" name="PageId" value="DataElementsGroup" />
<input type="hidden" name="src" value="<%= StringEscapeUtils.escapeJavaScript(source) %>" />
<input type="hidden" name="downloadIDs" value="<%= downloadIDs %>" />
</form>
</BODY>
</HTML>
