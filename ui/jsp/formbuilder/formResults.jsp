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
<%@page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants" %>

<HTML>
<HEAD>
<TITLE>Welcome to Form Builder..</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
<%
  String urlPrefix = "";
  String jumpto = (String)request.getSession().getAttribute(CaDSRConstants.ANCHOR);
  String jumptoStr ="";
  
  if(jumpto!=null)
    jumptoStr = "onload=\"location.hash='#"+jumpto+"'\"";
%>
</HEAD>
<BODY topmargin=0 bgcolor="#ffffff" <%=jumptoStr%> ">

<logic:notPresent name="<%=FormConstants.IN_PROCESS%>"> 
        <%@ include  file="../common/common_header_inc.jsp" %>
        
        <jsp:include page="../common/tab_inc.jsp" flush="true">
                <jsp:param name="label" value="Form&nbsp;Search" />
                <jsp:param name="urlPrefix" value="" />
        </jsp:include>
</logic:notPresent>        

<logic:present name="<%=FormConstants.IN_PROCESS%>"> 
    <logic:present name="<%=FormConstants.SKIP_PATTERN%>"> 
            <%@ include  file="../common/in_process_common_header_inc.jsp" %>
            
            <jsp:include page="../common/tab_inc.jsp" flush="true">
                    <jsp:param name="label" value="Skip&nbsp;to&nbsp;Form&nbsp;Search" />
                    <jsp:param name="urlPrefix" value="../" />
            </jsp:include>
    </logic:present>  
    
    <logic:present name="<%=FormConstants.MODULE_DISPLAY_ORDER_TO_COPY%>"> 
            <%@ include  file="../common/in_process_common_header_inc.jsp" %>
            
            <jsp:include page="../common/tab_inc.jsp" flush="true">
                    <jsp:param name="label" value="Copy&nbsp;module&nbsp;Form&nbsp;Search" />
                    <jsp:param name="urlPrefix" value="../" />
            </jsp:include>
    </logic:present>  
    <logic:notPresent name="<%=FormConstants.MODULE_DISPLAY_ORDER_TO_COPY%>"> 
    test
    </logic:notPresent>  
</logic:present>  


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
      <tr colspan="2" >
           <td  nowrap>&nbsp;</td>
      </tr>
      <tr>
         <td  valign="bottom" class="OraHeaderSubSub" width="50%" align="left" nowrap>Search Results</td>
          <td  valign="bottom" class="OraFieldText" width="50%" align="right" nowrap>
            <html:link action='<%="/formbuilder/viewModuleList?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.VIEW_MODULE_LIST%>' >          
                    Module list         
            </html:link>          
          </td>
     </tr>       
        <tr>
          <td colspan="2" ><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>   
  <%@ include  file="/formbuilder/formResults_inc.jsp" %>
</logic:present> 
   
</html:form>
<%
  request.getSession().removeAttribute(CaDSRConstants.ANCHOR);
%>
<%@ include file="/common/common_bottom_border.jsp"%>

</BODY>
</HTML>
