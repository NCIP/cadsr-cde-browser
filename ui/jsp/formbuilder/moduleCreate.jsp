<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>

<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<HTML>
  <HEAD>
    <TITLE>Formbuilder: Create Module.</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">
      function submitForm() {
        if(validateCreateModuleForm(createModuleForm))
          document.forms[0].submit();
      }
      function submitCancelForm() {
          document.forms[0].action= '<%=request.getContextPath()+"/cancelAction.do?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.GET_FORM_TO_EDIT%>'
          document.forms[0].submit();
      }   
         
      
    </SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = "";
      String pageUrl = "&PageId=DataElementsGroup";
      String protoLOVUrl= 
      "javascript:newWin('/cdebrowser/formLOVAction.do?method=getProtocolsLOV&idVar=" + FormConstants.PROTOCOLS_LOV_ID_FIELD + "&nameVar=" + FormConstants.PROTOCOLS_LOV_NAME_FIELD +pageUrl+"','protoLOV',700,600)";
      %>
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Create&nbsp;Module"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>
<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.form.module.create"/>
      </td>
    </tr>  
</table> 

    <logic:present name="<%=FormConstants.CRF%>">
    

      <html:form action='<%="/createModule?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.CREATE_MODULE%>'
        >  
      <%@ include file="showMessages.jsp" %>
      
      <html:hidden property="<%= FormConstants.DISPLAY_ORDER %>"/>

      
        <table width="70%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.module.name" />
            </td>
            <td  class="OraFieldText" width="80%" nowrap>
            <html:text 
              property="<%= FormConstants.MODULE_LONG_NAME %>"
              size="50"
              maxlength="<%=Integer.toString(FormConstants.LONG_NAME_MAX_LENGTH)%>"
              />
            </td>
          </tr>

        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" width="20%" nowrap>
            <bean:message key="cadsr.formbuilder.form.instruction"/>
          </td>        
          <td  class="OraFieldText" width="80%" >
            <html:textarea  styleClass="OraFieldTextInstruction" rows="2" cols="102" 
               property="<%=FormConstants.MODULE_INSTRUCTION%>">
            </html:textarea>
          </td>            
        </tr>           
 
      </table>
      
      <%@ include file="/formbuilder/createModule_inc.jsp"%>    

      </html:form>
</logic:present>
<logic:notPresent name="<%=FormConstants.CRF%>">Selected form has been deleted by a diffrent user </logic:notPresent>
<%@ include file="/common/common_bottom_border.jsp"%>

<html:javascript formName="createModuleForm"/>
</BODY>
</HTML>
