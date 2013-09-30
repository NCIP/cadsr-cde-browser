<%--L
  Copyright SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.

  Portions of this source file not modified since 2008 are covered by:

  Copyright 2000-2008 Oracle, Inc.

  Distributed under the caBIG Software License.  For details see
  http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
L--%>

<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<HTML>
<%   CDEBrowserParams params = CDEBrowserParams.getInstance();
%>
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
    <%@ include file="/jsp/common/syserror_common_header_inc.jsp"%>
    <jsp:include page="/jsp/common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="System&nbsp;Error"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

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
   <%  
    java.lang.Throwable exception = (java.lang.Throwable) 
    request.getAttribute("org.apache.struts.action.EXCEPTION");
    
    if (exception !=null)
	exception.printStackTrace(new java.io.PrintWriter(out));
    %>
  -->
        </td>
      </tr>
    </table>
<%@ include file="/jsp/common/common_bottom_border.jsp"%>
  </BODY>
</HTML>
