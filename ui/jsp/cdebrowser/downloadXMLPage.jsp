<%
  String source = request.getParameter("src");
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
  document.location.href ="<%=request.getContextPath()%>" + "/search?xmlDownload=9&PageId=DataElementsGroup&src=" + "<%= source %>";
  
}
function closeWindow() {
  close();
}



//-->
</SCRIPT>
<form name="downloadForm">

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

</form>
</BODY>
</HTML>
