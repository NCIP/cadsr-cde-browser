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
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">
      function submitForm(methodName) {
         var f = document.forms[0];
         document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
         f.submit();
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
      <jsp:param name="label" value="Maintain&nbsp;Reference&nbsp;Documents"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

    <%@ include file="showMessages.jsp"%>


      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Upload Document</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>
      
      <html:form action="/uploadDocument.do" enctype="multipart/form-data">
      <html:hidden property="selectedRefDocId" />
      <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
       
       <table cellpadding="0" cellspacing="0" width="80%" align="center">
       
        <tr >
          <td >
            <html:file  property="uploadedFile" />
          </td>
        </tr>  
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>        
        <tr >
          <td >
            <a href="javascript:submitForm('<%="uploadDocuments"%>')">
               <html:img src='<%="i/save.gif"%>' border="0" alt="Upload Document"/>
            </a>
          </td>
        </tr>          
      </table>      
     </html:form>         
  
<%@ include file="../common/common_bottom_border.jsp"%>
</BODY>
</HTML>
