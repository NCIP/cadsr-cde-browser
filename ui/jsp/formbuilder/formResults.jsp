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
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants" %>

<HTML>
<HEAD>
<TITLE>Welcome to Form Builder..</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">

</HEAD>
<logic:present  name="<%=CaDSRConstants.ANCHOR%>"> 
<BODY topmargin=0 bgcolor="#ffffff" onload="location.hash='#<bean:write name="<%=CaDSRConstants.ANCHOR%>"/>'">
</logic:present>
<logic:notPresent  name="<%=CaDSRConstants.ANCHOR%>"> 
<BODY topmargin=0 bgcolor="#ffffff">
</logic:notPresent>

<%
  String urlPrefix = "";
%>
<%@ include  file="../common/common_header_inc.jsp" %>

<jsp:include page="../common/tab_inc.jsp" flush="true">
	<jsp:param name="label" value="Form&nbsp;Search" />
	<jsp:param name="urlPrefix" value="" />
</jsp:include>
<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.search.page"/>
      </td>
    </tr>  
</table> 
<html:form action="/formSearchAction.do">
 <%@ include  file="/formbuilder/formSearch_inc.jsp" %> 
<logic:present name="<%=FormConstants.FORM_SEARCH_RESULTS%>"> 

    <A NAME="results"></A>
       <table cellpadding="0" cellspacing="0" width="100%" align="center">  
      <tr>
           <td  nowrap>&nbsp;</td>
      </tr>
     <td  valign="bottom" class="OraHeaderSubSub" width="100%" align="left" nowrap>Search Results</td>
     </tr>       
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
        <tr>    
          <td align="left" class="AbbreviatedText">
            <bean:message key="cadsr.cdebrowser.helpText.results"/>
          </td>
        </tr>
      </table>   
  <%@ include  file="/formbuilder/formResults_inc.jsp" %>
</logic:present> 
   
</html:form>
<%@ include file="/common/common_bottom_border.jsp"%>

</BODY>
</HTML>
