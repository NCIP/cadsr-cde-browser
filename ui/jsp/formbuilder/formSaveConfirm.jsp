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
<TITLE>Welcome to Form Builder..</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">
<!--


function submitFormToSave(methodName) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();
}

-->
</SCRIPT>
</HEAD>
<BODY topmargin=0 bgcolor="#ffffff">

<%@ include file="../common/in_process_common_header_inc.jsp"%>

<jsp:include page="../common/tab_inc.jsp" flush="true">
	<jsp:param name="label" value="Save&nbsp;Form" />
	<jsp:param name="urlPrefix" value="" />
</jsp:include>
<%@ include file="showMessages.jsp" %>
<html:form action="/saveFormDoneAction.do?unlock=true">
   <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" >      
        <tr >
          <td align="center" class="OraTipLabel">
             <bean:message key="cadsr.formbuilder.form.edit.prompt.save"/> 
          </td>          
        </tr>     
        <tr >
          <td class ="OraPromptText">
             &nbsp;
          </td>          
        </tr>          
      </table>  
      <table width="15%" align="center" cellpadding="1" cellspacing="1" border="0" >      
        <tr >
         <td align="center">
            <a href="javascript:submitFormToSave('<%=NavigationConstants.SAVE_FORM_CHANGES%>')">
                <html:img src='<%=urlPrefix+"i/yes.gif"%>' border="0" alt="Yes"/>
             </a> 
          </td>   
         <td align="center">
            <a href="javascript:submitFormToSave('<%=NavigationConstants.CANCEL_FORM_EDIT%>')">
                <html:img src='<%=urlPrefix+"i/no.gif"%>' border="0" alt="No"/>
             </a> 
          </td>            
      </tr>      
      </table>  
</html:form>
<%@ include file="/common/common_bottom_border.jsp"%>

</BODY>
</HTML>
