<%@ page import="java.util.*" %>
<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%
//	String deIdSeqs = infoBean.getStringInfo("deIdSeqs");
//	String curationURL = infoBean.getStringInfo("curationURL");
	List downloadIds = (List)session.getAttribute("downloadList");
	
	StringBuffer deIdSeqs = new StringBuffer();
	if (downloadIds != null && downloadIds.size() > 0) {
		for (int i=0;i<downloadIds.size();i++) {
			deIdSeqs.append(downloadIds.get(i)+",");
		}
		deIdSeqs.deleteCharAt(deIdSeqs.length() - 1);
	}
	
%>
<html>
	<head></head>
	<body onLoad="javascript:document.forms[0].submit();">
		<form action="/cdecurate/NCICurationServlet" method="POST">
			<input type="hidden" name="reqType" value="showDEfromOutside" />
			<input type="hidden" name="SearchID" value="<%= deIdSeqs.toString() %>" />
		</form>
	</body>
</html>