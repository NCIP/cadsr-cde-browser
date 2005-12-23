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
<%@page import="java.util.*" %>
<HTML>
<%
  String urlPrefix = "";

%>
<HEAD>
<TITLE>Formbuilder: Create New Version</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <link href="../css/jdeveloper.css" rel="stylesheet" media="screen"/>


</HEAD>
<BODY topmargin=0 bgcolor="#ffffff">
<%@ include  file="../common/in_process_common_header_inc.jsp" %>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Create&nbsp;New&nbsp;Version"/>
      <jsp:param name="urlPrefix" value=""/>
</jsp:include>

<%@ include file="showMessages.jsp"%>

<html:form action='<%="/saveNewVersion?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.SAVE_NEW_VERSION%>'>
<table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr >
    <td >
      <html:image src='<%=urlPrefix+"i/save.gif"%>' border="0" alt="Save"/>
    </td> 
    <td>
      <html:link action='<%= "/cancelNewVersion?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.CANCEL_NEW_VERSION%>'>
        <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
      </html:link>             
    </td>                
  </tr> 
</table>

      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.name" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="longName"
              />
          </td>
        </tr>
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.context" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="context.name"
              />
          </td>
        </tr>

        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.version" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="version"
              />
          </td>
        </tr>
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.workflow" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="aslName"
              />
          </td>      
        </tr>
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.type" />:</td>  
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="formType"
              />
          </td>        
        </tr>
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.protocols.longName" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="delimitedProtocolLongNames"
              />
          </td>
        </tr>


        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.category" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="formCategory"
              />
          </td>
        </tr>     

        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.definition" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="preferredDefinition"
              />
           </td>
        </tr>
      </table>

      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Create New Version</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>


 <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap>
        <bean:message key="cadsr.formbuilder.form.max.version" />&nbsp;</td>
      <td class="OraFieldText" nowrap> 
        <bean:write name="<%=FormConstants.FORM_MAX_VERSION%>"/>
        <input type="hidden" name="<%=FormConstants.FORM_MAX_VERSION%>" value="123"/>
       <%-- <html:hidden property="<%=FormConstants.FORM_MAX_VERSION%>" /> --%>
      </td>
    </tr>
    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap>
        <bean:message key="cadsr.formbuilder.form.current.version" />&nbsp;</td>
      <td class="OraFieldText" nowrap> 
        <bean:write name="<%=FormConstants.CRF%>" property="version"/>
      </td>
    </tr>
    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap>
        <bean:message key="cadsr.formbuilder.form.new.version" />&nbsp;</td>
      <td class="OraFieldText" nowrap>
        <html:text property="<%=FormConstants.NEW_VERSION_NUMBER%>" size="20"/>
      </td>
    </tr>
    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap>
        <bean:message key="cadsr.formbuilder.form.new.version.changenote" />&nbsp;</td>
      <td class="OraFieldText" nowrap>
        <html:textarea property="<%=FormConstants.CHANGE_NOTE%>" rows="5" cols="40"/>
      </td>
    </tr>
    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap>
        <bean:message key="cadsr.formbuilder.form.new.version.edit" />&nbsp;</td>
      <td class="OraFieldText" nowrap>
        <input type=checkbox class="OraFieldText" name="<%= FormConstants.EDIT_FORM_INDICATOR%>" value ="true"/>
      </td>
    </tr>
</table>
<br/>
<table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr >
    <td >
      <html:image src='<%=urlPrefix+"i/save.gif"%>' border="0" alt="Save"/>
    </td> 
    <td>
      <html:link action='<%= "/cancelNewVersion?" + NavigationConstants.METHOD_PARAM + "=cancelNewVersion"%>'>
        <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
      </html:link>             
    </td>                
  </tr> 
</table>
</html:form>
<%@ include file="/common/common_bottom_border.jsp"%>
</BODY>
</HTML>