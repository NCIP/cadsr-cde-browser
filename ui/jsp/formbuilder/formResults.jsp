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
<HEAD>
<TITLE>Welcome to Form Builder..</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK REL=STYLESHEET TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css">
<SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
  document.forms[0].submit();
}
-->
</SCRIPT>
</HEAD>
<BODY bgcolor="#ffffff">

<%
   String urlPrefix = "";
  %>
<%@ include  file="/formbuilder/tab_include_search.jsp" %>

<P>&nbsp;</P>
<html:form action="/formAction.do">
  <table cellspacing="2" cellpadding="3" border="0">
    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.name" />:</td>
        <td class="OraFieldText" nowrap>
          <html:text property="formLongName"  />
        </td>

        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.protocol" />:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="<%=FormConstants.PROTOCOL_ID_SEQ%>" value="" size ="20"> 
        </td>      
    </tr>
    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.context" />:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="<%=FormConstants.CONTEXT_ID_SEQ%>" value="" size ="20"> 
        </td>

        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.workflow" />:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="<%=FormConstants.WORKFLOW%>" value="" size ="20"> 
        </td>      
    </tr>
    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.category" />:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="<%=FormConstants.CATEGORY_NAME%>" value="" size ="20"> 
        </td>

        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.type" />:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="<%=FormConstants.FORM_TYPE%>" value="" size ="20"> 
        </td>      
    </tr>    
    <tr>
      <td>
        <html:hidden value="<%=NavigationConstants.GET_ALL_FORMS_METHOD%>" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      </td>
    </tr>
  <tr>
    <td colspan="4" nowrap align="left" class="AbbreviatedText">Wildcard character for search is *</td>
 </tr>
 <TR>
    <td colspan="2" align="right" nowrap><a href="javascript:submitForm()"><img src=i/search.gif border=0></a></td>
    <td colspan="2" align="left" nowrap><a href="javascript:clearForm()"><img src=i/clear.gif border=0></a></td>
 </TR>    
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </table>

  <P>
      <logic:present name="<%=FormConstants.FORM_SEARCH_RESULTS%>">
        <bean:define id="pageBean" name="<%=FormConstants.FORM_SEARCH_RESULTS_PAGENATION%>" type="gov.nih.nci.ncicb.cadsr.jsp.bean.PagenationBean"/>
        <cde:pagenation name="top" textClassName="OraFieldText" selectClassName="LOVField" formIndex="0" pageSize="40" 
                     beanId = "<%=FormConstants.FORM_SEARCH_RESULTS_PAGENATION%>" 
                     actionURL="/cdebrowser/pageAction.do"/>
                     
        <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTableColumnHeader">
          	<th class="OraTableColumnHeader" nowrap>Action</th>
          	<th class="OraTableColumnHeader" nowrap>Long Name</th>
          	<th class="OraTableColumnHeader" nowrap>Type</th>
          	<th class="OraTableColumnHeader" nowrap>Workflow Status</th>         	
          </tr>        
          <logic:iterate id="form" name="<%=FormConstants.FORM_SEARCH_RESULTS%>" 
          	type="gov.nih.nci.ncicb.cadsr.resource.Form"
                offset="<%=pageBean.getOffset()%>"
                length="<%=pageBean.getPageSize()%>">
            <tr class="OraTabledata">
                <td class="OraFieldText">
                  <table cellspacing="1" cellpadding="1"  border="0" width="100%">
                    <tr >                   
                      <td >
                      	<a href='test'/><img src="i/view.gif" border=0 alt='View'></a>
                      </td>
		      <td >
		       	<cde:secureIcon  formId="form" activeImageSource="i/edit.gif" activeUrl="test" 
		            role="<%=CaDSRConstants.CDE_MANAGER%>" altMessage="Edit"/>
		       </td>
		       <td>
		          <cde:secureIcon  formId="form" activeImageSource="i/copy.gif" activeUrl="test" 
		   	   	role="<%=CaDSRConstants.CDE_MANAGER%>" altMessage="Copy"/>
		        </td>
		        <td >
		           <cde:secureIcon  formId="form" activeImageSource="i/delete.gif" activeUrl="test"
		           	role="<%=CaDSRConstants.CDE_MANAGER%>" altMessage="Delete"/>
		        </td>
                       </tr>
                    </table>
                   </td>                
          	<td class="OraFieldText">
          		<html:link page="/formAction.do" paramName="form" paramProperty="preferredName">
            			<bean:write name="form" property="longName"/><br>
          		</html:link>    
          	</td>
          	<td class="OraFieldText">
          		<bean:write name="form" property="formType"/><br>
          	</td>
          	<td class="OraFieldText">
          		<bean:write name="form" property="aslName"/><br>
          	</td>          	
            </tr>
          </logic:iterate>
        </table>
        <cde:pagenation name="bottom" textClassName="OraFieldText" selectClassName="LOVField" formIndex="0" pageSize="40" 
                     beanId = "<%=FormConstants.FORM_SEARCH_RESULTS_PAGENATION%>" 
                     actionURL="/cdebrowser/pageAction.do"/>        
        </logic:present>
   </P>
  <P>
    
  </P>
</html:form>
<P>&nbsp;</P>
<P>&nbsp;</P>

</BODY>
</HTML>
