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
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormCreateAction" %>
<%@page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants" %>

<HTML>
<%
  String urlPrefix = "";

%>
<HEAD>
<TITLE>Welcome to Form Builder..</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=urlPrefix%>cdebrowserCommon_html/blaf.css">

</HEAD>
<BODY topmargin=0 bgcolor="#ffffff">

<%@ include  file="/formbuilder/common_header_inc.jsp" %>

<jsp:include page="/formbuilder/tab_inc.jsp" flush="true">
	<jsp:param name="label" value="Create&nbsp;Form" />
	<jsp:param name="urlPrefix" value="" />
</jsp:include>

<html:form action='<%="/formCreate?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.CREATE_FORM%>'>
  <%@ include  file="/formbuilder/formCreate_inc.jsp" %>
</html:form>
<%@ include file="/common/common_bottom_border.jsp"%>

</BODY>
</HTML>
