<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="gov.nih.nci.ncicb.cadsr.html.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants" %>

<HTML>
<%
  String urlPrefix = "../";

%>
<HEAD>
<TITLE>Welcome to Form Builder..</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=urlPrefix%>cdebrowserCommon_html/blaf.css">

</HEAD>
<BODY topmargin=0 bgcolor="#ffffff">

<%@ include  file="/formbuilder/common_header_inc.jsp" %>

<jsp:include page="/formbuilder/tab_inc.jsp" flush="true">
	<jsp:param name="label" value="Form&nbsp;Search" />
	<jsp:param name="urlPrefix" value="../" />
</jsp:include>

<html:form action="/formSearchAction.do">
 <%@ include  file="/formbuilder/formSearch_inc.jsp" %>
  <P>
      
<logic:present name="<%=FormConstants.FORM_SEARCH_RESULTS%>">  
  <%@ include  file="/formbuilder/formResults_inc.jsp" %>
</logic:present> 
<logic:notPresent name="<%=FormConstants.FORM_SEARCH_RESULTS%>">  
	<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  	  <tr class="OraTabledata">
         	<td ><bean:message key="cadsr.formbuilder.search.message"/></td>
  	  </tr>
  	</table>   
</logic:notPresent>
 </P>
</html:form>
<%@ include file="/common/common_bottom_border.jsp"%>
</BODY>
</HTML>
