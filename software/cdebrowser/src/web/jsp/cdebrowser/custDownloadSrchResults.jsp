<%@ page import="gov.nih.nci.ncicb.cadsr.common.ProcessConstants, java.util.*, gov.nih.nci.ncicb.cadsr.common.resource.*"%>
<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%
	String downloadIDs = infoBean.getStringInfo("downloadIDs");
	String curationURL = (String)session.getAttribute(ProcessConstants.CURATION_URL);
%>

<html>
	<head></head>
	<body onLoad="javascript:document.customDownloadForm.submit();">
		<form name="customDownloadForm" action="<%= curationURL %>/cdecurate/NCICurationServlet" method="POST">
			<input type="hidden" name="reqType" value="showDEfromOutside" />
			<input type="hidden" name="SearchID" value="<%= downloadIDs %>" />
		</form>
	</body>
</html>