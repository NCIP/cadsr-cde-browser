<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="gov.nih.nci.ncicb.cadsr.html.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
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
<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>
<%
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
   String urlPrefix = "";
  %>
<%@ include  file="/formbuilder/tab_include_search.jsp" %>

<P>&nbsp;</P>
<html:form action="/formAction.do">
  <table cellspacing="2" cellpadding="3" border="0">
    <tr>
        <td class="OraFieldtitlebold" nowrap>Form Name:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="formLongName" value="" size ="20"> 
        </td>

        <td class="OraFieldtitlebold" nowrap>Protocol:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="protocolIdSeq" value="" size ="20"> 
        </td>      
    </tr>
    <tr>
        <td class="OraFieldtitlebold" nowrap>Context:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="contextIdSeq" value="" size ="20"> 
        </td>

        <td class="OraFieldtitlebold" nowrap>Workflow:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="workflow" value="" size ="20"> 
        </td>      
    </tr>
    <tr>
        <td class="OraFieldtitlebold" nowrap>Category:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="categoryName" value="" size ="20"> 
        </td>

        <td class="OraFieldtitlebold" nowrap>Type:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="type" value="" size ="20"> 
        </td>      
    </tr>    
    <tr>
      <td>
        <html:hidden value="getAllForms" property="method"/>
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
      <logic:present name="FormSearchResults">
        <table cellspacing="2" cellpadding="3"  border="1" width="100%">
          <tr>
          	<td class="OraTableColumnHeader" nowrap>Long Name</td>
          	<td class="OraTableColumnHeader" nowrap>Type</td>
          	<td class="OraTableColumnHeader" nowrap>Workflow Status:</td>         	
          </tr>        
          <logic:iterate id="form" name="FormSearchResults" type="gov.nih.nci.ncicb.cadsr.resource.Form">
            <tr>
          	<td>
          		<html:link page="/formAction.do" paramName="form" paramProperty="preferredName">
            			<bean:write name="form" property="longName"/><br>
          		</html:link>    
          	</td>
          	<td>
          		<bean:write name="form" property="formType"/><br>
          	</td>
          	<td>
          		<bean:write name="form" property="aslName"/><br>
          	</td>          	
            </tr>
          </logic:iterate>
        </table>
        </logic:present>
   </P>
  <P>
    
  </P>
</html:form>
<P>&nbsp;</P>
<P>&nbsp;</P>

</BODY>
</HTML>
