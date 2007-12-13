<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<HTML>
  <HEAD>
    <TITLE>CDEBrowser: System Error</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">

</SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = "";

%>
    <%@ include file="../common/syserror_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="System&nbsp;Error"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>
    <bean:define id="exception" name="org.apache.struts.action.EXCEPTION"
       type="java.lang.Throwable" />

    <table>
      <tr>
        <td class="OraErrorHeader">
        	Unexpected System Error has Occured <br><br>
      <logic:messagesPresent >
       <table width="80%" align="center">
        <html:messages id="error" >
          <logic:present name="error">
            <tr align="center" >
               <td  align="left" class="OraErrorText" nowrap>
                <b><bean:write  name="error"/></b>
              </td>
            </tr>
          </logic:present>          
        </html:messages> 
           <tr align="center" >
             <td>
                &nbsp;
            </td>
           </tr>        
       </table>
      </logic:messagesPresent>  
  <!--
   <logic:present name="exception">
       Exception StackTrace <br>
   <%  
    exception.printStackTrace(new java.io.PrintWriter(out));
    %>
    </logic:present >    
  -->
        </td>
      </tr>
    </table>
<%@ include file="/common/common_bottom_border.jsp"%>
  </BODY>
</HTML>
