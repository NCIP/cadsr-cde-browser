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
<%@page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.common.FormBuilderConstants" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.Form"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormJspUtil"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.CDEBrowserParams"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util.CDEDetailsUtils"%>



<HTML>
  <HEAD>
    <TITLE>Formbuilder: Copy Module Form Details</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">

</SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = request.getContextPath();
     int dummyInstructionDisplayCount = 3;

%>
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Select&nbsp;Module&nbsp;to&nbsp;copy"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>


    <%@ include file="showMessages.jsp" %>

      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.longName" />
          </td>                
          <td width="80%" class="OraFieldText">
            <bean:write name="<%=FormConstants.MODULE_COPY_FORM%>" property="longName"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td  class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.definition"/>
          </td>                
          <td width="80%" class="OraFieldText">
            <bean:write name="<%=FormConstants.MODULE_COPY_FORM%>" property="preferredDefinition"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td  class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.context"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.MODULE_COPY_FORM%>" property="context.name"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.protocols.longName"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.MODULE_COPY_FORM%>" property="delimitedProtocolLongNames"/>
          </td>
        </tr>   
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.workflow"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.MODULE_COPY_FORM%>" property="aslName"/>
          </td>
        </tr>  
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.category"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.MODULE_COPY_FORM%>" property="formCategory"/>
          </td>
        </tr>  
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.type"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.MODULE_COPY_FORM%>" property="formType"/>
          </td>
        </tr> 
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.publicID"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write  name="<%=FormConstants.MODULE_COPY_FORM%>" property="publicId"/> 
          </td>
        </tr>           
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.question.version"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write  name="<%=FormConstants.MODULE_COPY_FORM%>" property="version"/> 
          </td>
        </tr>    
        <logic:present name="<%=FormConstants.MODULE_COPY_FORM%>" property="instruction">
          <tr class="OraTabledata">
            <td class="TableRowPromptTextLeft"  width="20%">
              <bean:message key="cadsr.formbuilder.form.header.instruction"/>
            </td>                
            <td  class="OraFieldTextInstruction">
              <bean:write  name="<%=FormConstants.MODULE_COPY_FORM%>" property="instruction.longName"/>
            </td>
          </tr>
        </logic:present>
        <logic:present name="<%=FormConstants.MODULE_COPY_FORM%>" property="footerInstruction">     
          <tr class="OraTabledata">
            <td class="TableRowPromptTextLeft"  width="20%">
              <bean:message key="cadsr.formbuilder.form.footer.instruction"/>
            </td>                
            <td  class="OraFieldTextInstruction">
              <bean:write  name="<%=FormConstants.MODULE_COPY_FORM%>" property="footerInstruction.longName"/>
            </td>
          </tr> 
        </logic:present>          
      </table>
      <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        <tr >
          <td >
             &nbsp;
          </td>
        </tr>
      </table> 
      
     <%@ include file="/formbuilder/moduleCopyModuleDetails_inc.jsp"%>
     <br>
      <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        <tr >
          <td align="center">
            <html:link action='<%="/formbuilder/moduleSearch?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.CANCEL_MODULE_SELECTION%>' >          
                    <html:img src='<%=urlPrefix+"/i/backButton.gif"%>' border="0" alt="Go back"/>         
            </html:link>
          </td>
        </tr>
      </table>      

    <%@ include file="/common/common_bottom_border.jsp"%>
  </BODY>
</HTML>
