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
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants" %>

<HTML>
<%
  String urlPrefix = "../";

%>
<HEAD>
<TITLE>Welcome to Form Builder..</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">

</HEAD>
<BODY topmargin=0 bgcolor="#ffffff">

<logic:notPresent name="<%=FormConstants.SKIP_PATTERN%>"> 
        <%@ include  file="../common/common_header_no_strip_inc.jsp" %>
        
        <jsp:include page="../common/tab_inc.jsp" flush="true">
                <jsp:param name="label" value="Form&nbsp;Search" />
                <jsp:param name="urlPrefix" value="../" />
        </jsp:include>
</logic:notPresent>        
<logic:present name="<%=FormConstants.SKIP_PATTERN%>"> 
        <%@ include  file="../common/in_process_common_header_no_strip_inc.jsp" %>
        
        <jsp:include page="../common/tab_inc.jsp" flush="true">
                <jsp:param name="label" value="Skip&nbsp;to&nbsp;Form&nbsp;Search" />
                <jsp:param name="urlPrefix" value="../" />
        </jsp:include>
</logic:present>  

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
