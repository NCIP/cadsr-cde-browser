<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<HTML>
  <HEAD>
    <TITLE>Formbuilder: Copy Form</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = "";
      String contextPath = request.getContextPath();
      String pageUrl = "&PageId=DataElementsGroup";
      String protoLOVUrl= 
      "javascript:newWin('"+contextPath+"/formLOVAction.do?method=getProtocolsLOV&idVar=" + FormConstants.PROTOCOLS_LOV_ID_FIELD + "&chkContext=true&nameVar=" + FormConstants.PROTOCOLS_LOV_NAME_FIELD +pageUrl+"','protoLOV',700,600)";
      %>

    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Copy&nbsp;Form"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.form.copy"/>
      </td>
    </tr>  
</table> 
    <SCRIPT>
<!--

function submitForm() {
  if((document.forms[0].<%=FormConstants.FORM_TYPE%>.value == "TEMPLATE") && (document.forms[0].<%=FormConstants.PROTOCOLS_LOV_ID_FIELD%>.value != '')) {
     alert('Protocol must be left blank for a type TEMPLATE');
     }

  if(validateCopyForm(copyForm))
     document.forms[0].submit();
}

function resetForm() {
     document.forms[0].reset();
}
function clearProtocol() {
  document.forms[0].protocolIdSeq.value = "";
  document.forms[0].protocolLongName.value = "";
}

-->
</SCRIPT>



    <logic:present name="<%=FormConstants.CRF%>">
      <html:form 
        action='<%="/formCopyAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.FORM_COPY%>'
        onsubmit="validateCopyForm(this)"
        >
      <%@ include file="/formbuilder/copyButton_inc.jsp"%>    
      <%@ include file="showMessages.jsp" %>
      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Form Header</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>

      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">

        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.longName" /></td>
          <td class="OraFieldText" nowrap>
            <html:text 
              property="<%= FormConstants.FORM_LONG_NAME %>"
              size="100"
              styleClass="OraFieldText"
              maxlength="<%= (new Integer (FormConstants.LONG_NAME_MAX_LENGTH)).toString() %>"
              />
          </td>
        </tr>

        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.definition" /></td>
          <td class="OraFieldText" nowrap>
            <html:textarea 
              property="<%= FormConstants.PREFERRED_DEFINITION %>"
              name="<%= FormConstants.CRF %>"
              cols="100"
              rows="3"
              styleClass="OraFieldText"
              />
           </td>
         </tr>
         
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.context" /></td>
          <td class="OraFieldText" nowrap>
            <bean:define id="context" scope="session" name="<%= FormConstants.CRF %>" property="<%= FormConstants.CRF_CONTEXT %>" toScope="page"/>

            <html:select styleClass="Dropdown" name="context" property="<%=FormConstants.CRF_CONTEXT_ID_SEQ%>">
              <html:options collection="<%=CaDSRConstants.USER_CONTEXTS%>" 
                property="conteIdseq" labelProperty="name" />
            </html:select>
          </td>
        </tr>

        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.protocol" /></td>
          <td class="OraFieldText" nowrap>
            <html:text 
              property="<%= FormConstants.PROTOCOLS_LOV_NAME_FIELD %>"
              readonly="true" 
              size="19"
              styleClass="LOVField"
              />
              &nbsp;<a href="<%=protoLOVUrl%>"><img src="<%=urlPrefix%>i/search_light.gif" border="0" alt="Search for Protocols"></a>&nbsp;
            <a href="javascript:clearProtocol()"><i>Clear</i></a>
            <html:hidden  property="<%=FormConstants.PROTOCOLS_LOV_ID_FIELD%>"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.workflow" /></td>
          <td class="OraFieldText" nowrap>
            DRAFT NEW
          </td>      
        </tr>

        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.category" /></td>
          <td class="OraFieldText" nowrap>
            <html:select styleClass = "Dropdown" name="<%= FormConstants.CRF %>" property="<%=FormConstants.FORM_CATEGORY%>">
              <html:option value=""/>
              <html:options name="<%=FormConstants.ALL_FORM_CATEGORIES%>" /> \
            </html:select> 
          </td>
        </tr>   

        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.type" /></td>  
          <td class="OraFieldText" nowrap>
            <html:select styleClass="Dropdown" name="<%= FormConstants.CRF %>" property="<%=FormConstants.FORM_TYPE%>">
              <html:options name="<%=FormConstants.ALL_FORM_TYPES%>" /> 
            </html:select> 
          </td>        
        </tr>
    
    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.version" /></td>
      <td class="OraFieldText" nowrap>
        <html:hidden property="<%= FormConstants.FORM_VERSION %>" value="1.0" />
        1.0
      </td>
    </tr>

  <logic:present name="<%=FormConstants.CRF%>" property="instruction">
    <tr class="OraTabledata">
      <td class="TableRowPromptTextLeft"  width="20%">
        <bean:message key="cadsr.formbuilder.form.header.instruction"/>
      </td>                
      <td  class="OraFieldTextInstruction">
        <bean:write  name="<%=FormConstants.CRF%>" property="instruction.longName"/>
      </td>
    </tr>
  </logic:present>
  <logic:present name="<%=FormConstants.CRF%>" property="footerInstruction">     
    <tr class="OraTabledata">
      <td class="TableRowPromptTextLeft"  width="20%">
        <bean:message key="cadsr.formbuilder.form.footer.instruction"/>
      </td>                
      <td  class="OraFieldTextInstruction">
        <bean:write  name="<%=FormConstants.CRF%>" property="footerInstruction.longName"/>
      </td>
    </tr> 
  </logic:present> 

        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap  colspan="2"><bean:message key="cadsr.formbuilder.form.confirmCopy"/>:
            <html:checkbox property="<%= FormConstants.FORM_GOTO_EDIT %>"/>
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
          <td class="OraHeaderSubSub" width="100%">Form Details</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>
      
     <%@ include file="/formbuilder/moduleDetails_inc.jsp"%>

<%@ include file="/formbuilder/copyButton_inc.jsp"%>    
</html:form>

</logic:present>
<logic:notPresent name="<%=FormConstants.CRF%>">Selected form has been deleted by a diffrent user </logic:notPresent>

    <%@ include file="/common/common_bottom_border.jsp"%>

</BODY>

<html:javascript formName="copyForm"/>

</HTML>
