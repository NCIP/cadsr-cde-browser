<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="gov.nih.nci.ncicb.cadsr.html.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants" %>
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants" %>
<HTML>
<HEAD>
<TITLE>Welcome to Form Builder..</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK REL=STYLESHEET TYPE="text/css" HREF="../cdebrowserCommon_html/blaf.css">
<SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
  document.forms[0].submit();
}
/* HSK */
function clearClassSchemeItem() {
  document.forms[0].jspClassification.value = "";
  document.forms[0].txtClassSchemeItem.value = "";
}

function clearProtocol() {
  document.forms[0].jspProtocol.value = "";
  document.forms[0].txtProtocol.value = "";
}

-->
</SCRIPT>
</HEAD>
<BODY bgcolor="#ffffff">

<%
  String urlPrefix = "../";
  String pageUrl = "&PageId=DataElementsGroup";
  // HSK

  String csLOVUrl= "javascript:newWin('/cdebrowser/search?classificationsLOV=9&idVar=jspClassification&nameVar=txtClassSchemeItem"+pageUrl+"','csLOV',700,600)";

  String protoLOVUrl= "javascript:newWin('/cdebrowser/search?protocolsLOV=9&idVar=jspProtocol&nameVar=txtProtocol"+pageUrl+"','protoLOV',700,600)";

%>
<%@ include  file="/formbuilder/tab_include_search.jsp" %>

<P>&nbsp;</P>
<html:form action="/formAction.do">
  <table cellspacing="2" cellpadding="3" border="0" width="100%">
    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.name" />:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="<%=FormConstants.FORM_LONG_NAME%>" value="" size ="20"> 
        </td>

    <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.protocol"/>:</td>
    <td class="OraFieldText" nowrap>
      <input type="text" name="txtProtocol" 
             value="" 
             readonly onFocus="this.blur();"
             class="LOVField"
             size ="18"
      >
      &nbsp;<a href="<%=protoLOVUrl%>"><img src="../i/search_light.gif" border="0" alt="Search for Protocols"></a>&nbsp;
      <a href="javascript:clearProtocol()"><i>Clear</i></a>
      <input type="hidden" name="jspProtocol" value="" >
    </td>


    </tr>

    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.context" />:</td>
        <td class="OraFieldText" nowrap>
        <html:select property="<%=FormConstants.CONTEXT_ID_SEQ%>">
		<html:options collection="<%=FormConstants.ALL_CONTEXTS%>" 
		   property="conteIdseq" labelProperty="name" />
	</html:select>
        </td>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.workflow" />:</td>
        <td class="OraFieldText" nowrap>
        <html:select property="<%=FormConstants.WORKFLOW%>">
		<html:options name="<%=FormConstants.ALL_WORKFLOWS%>"/>
	</html:select>        
        </td>      
    </tr>
    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.category" />:</td>
        <td class="OraFieldText" nowrap>
        <html:select property="<%=FormConstants.CATEGORY_NAME%>">
		<html:options name="<%=FormConstants.ALL_FORM_CATEGORIES%>" /> \
	</html:select> 
        </td>


    <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.classification"/>:</td>
    <td class="OraFieldText" nowrap>
      <input type="text" name="<%=FormConstants.CSI_NAME%>" 
             value="" 
             readonly onFocus="this.blur();"
             class="LOVField"
             size ="18"
      >
      &nbsp;<a href="<%=csLOVUrl%>"><img src="../i/search_light.gif" border="0" alt="Search for Classification Scheme Items"></a>&nbsp;
      <a href="javascript:clearClassSchemeItem()"><i>Clear</i></a>
      <input type="hidden" name="<%=FormConstants.CS_CSI_ID%>" value="" >
    </td>

        
    </tr>    
    <tr>
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
    <td colspan="2" align="right" nowrap><a href="javascript:submitForm()"><img src=../i/search.gif border=0></a></td>
    <td colspan="2" align="left" nowrap><a href="javascript:clearForm()"><img src=../i/clear.gif border=0></a></td>
 </TR>    
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </table>
  <P>
    
  </P>
</html:form>
<P>&nbsp;</P>
<P>&nbsp;</P>

</BODY>
</HTML>
