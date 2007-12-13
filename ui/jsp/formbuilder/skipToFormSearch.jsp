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
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.common.FormBuilderConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants" %>

<HTML>
<HEAD>
<TITLE>Form Builder: Skip Form search </TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
<%
  String urlPrefix = "../";
  String jumpto = (String)request.getSession().getAttribute(CaDSRConstants.ANCHOR);
  String jumptoStr ="";
  
  if(jumpto!=null)
    jumptoStr = "onload=\"location.hash='#"+jumpto+"'\"";
%>
</HEAD>
<BODY topmargin=0 bgcolor="#ffffff" <%=jumptoStr%> ">


<%@ include file="../common/in_process_common_header_inc.jsp"%>

<jsp:include page="../common/tab_inc.jsp" flush="true">
	<jsp:param name="label" value="Skip&nbsp;pattern&nbsp;form&nbsp;search" />
	<jsp:param name="urlPrefix" value="" />
</jsp:include>
<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.search.page"/>
      </td>
    </tr>  
</table> 
<html:form action="/formbuilder/skipSearchAction.do">
 <%@ include  file="/formbuilder/skipToFormSearch_inc.jsp" %> 
  <logic:present name="<%=FormConstants.SKIP_FORM_SEARCH_RESULTS%>">  
    <A NAME="results"></A>
       <table cellpadding="0" cellspacing="0" width="100%" align="center">  
      <tr>
           <td  nowrap>&nbsp;</td>
      </tr>
      <tr>
         <td  valign="bottom" class="OraHeaderSubSub" width="100%" align="left" nowrap>Search Results</td>
     </tr>       
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>   
  <%@ include  file="/formbuilder/skipSelectionFormResults_inc.jsp" %>
</logic:present> 
   
</html:form>
<%
  request.getSession().removeAttribute(CaDSRConstants.ANCHOR);
%>
<%@ include file="/common/common_bottom_border.jsp"%>

</BODY>
</HTML>
