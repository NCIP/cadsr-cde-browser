<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.html.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormCreateAction" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants" %>

<HTML>
<%
  String urlPrefix = "";

%>
<HEAD>
<TITLE>Formbuilder: Create Form</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">

</HEAD>
<BODY topmargin=0 bgcolor="#ffffff">

<%@ include  file="../common/in_process_common_header_inc.jsp" %>

<jsp:include page="../common/tab_inc.jsp" flush="true">
	<jsp:param name="label" value="Create&nbsp;Form" />
	<jsp:param name="urlPrefix" value="" />
</jsp:include>

<html:form action='<%="/formCreate?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.CREATE_FORM%>'>
  <%@ include  file="/formbuilder/formCreate_inc.jsp" %>
</html:form>
<%@ include file="/common/common_bottom_border.jsp"%>

</BODY>
</HTML>
