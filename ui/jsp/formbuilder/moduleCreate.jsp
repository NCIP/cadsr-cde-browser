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
    <TITLE>Welcome to Form Builder..</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css"/>
    <SCRIPT LANGUAGE="JavaScript">
      function submitForm() {
        if(validateCreateModuleForm(createModuleForm))
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


    <logic:present name="<%=FormConstants.CRF%>">
      <html:form action='<%="/createModule?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.CREATE_MODULE%>'
        >
      <%@ include file="/formbuilder/createModule_inc.jsp"%>    
      <%@ include file="showMessages.jsp" %>
      <html:hidden property="<%= FormConstants.DISPLAY_ORDER %>"/>

      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>                                                      
          <td class="OraFieldtitlebold" nowrap>
              <bean:message key="cadsr.formbuilder.module.name" /></td> 
          <td class="OraFieldText" nowrap>
            <html:text 
              property="<%= FormConstants.MODULE_LONG_NAME %>"
              size="50"
              maxlength="<%= FormConstants.LONG_NAME_MAX_LENGTH %>"
              />
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
