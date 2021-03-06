<%--L
  Copyright SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.

  Portions of this source file not modified since 2008 are covered by:

  Copyright 2000-2008 Oracle, Inc.

  Distributed under the caBIG Software License.  For details see
  http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
L--%>

<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%
  String source = request.getParameter("src");
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
  document.location.href ="<%=request.getContextPath()%>" + "<%=StringEscapeUtils.escapeJavaScript("/search?excelDownload=9&PageId=DataElementsGroup&src=")%>" + "<%= StringEscapeUtils.escapeJavaScript(source) %>";
  
}
function closeWindow() {
  close();
}
//-->
</SCRIPT>
<form name="downloadForm">
<br>
<p class="OraHeaderSubSub">Downloading data elements to Excel...</p>

<p><font face="Arial, Helvetica, sans-serif" size="-1" color="#336699">This 
operation may take a few minutes, so please do not close this status window while 
it is in progress. You may close this status window after the download is 
complete.</font></p>


</form>
</BODY>
</HTML>
