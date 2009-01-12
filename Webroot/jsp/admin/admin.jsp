<%@ page import="gov.nih.nci.ncicb.cadsr.common.struts.common.BrowserNavigationConstants"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page contentType="text/html;charset=windows-1252"%>

<html>


<head>

<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>Administration Page</title>

    <TITLE>Object Class Details</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">

<SCRIPT LANGUAGE="JavaScript">
<!--

function reloadCDEBrowserProperties() {
  document.forms[0].<%=BrowserNavigationConstants.METHOD_PARAM%>.value='cdebrowser';
  document.forms[0].submit();
}
-->

</SCRIPT>
</head>

<body topmargin="0">
<html:form action="/admin/adminAction">
     <html:hidden value="" property="<%=BrowserNavigationConstants.METHOD_PARAM%>"/>
       <br>
       <br>
       
       <jsp:include page="/jsp/admin/mltitab_inc.jsp" flush="true">
         <jsp:param name="label" value="CDEBrowser&nbsp;Admin"/>
       </jsp:include>

       <br>
       <br>
         <table valign="top" width="100%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
           <tr class="OraTabledata">
             <td  width="80%">All CDEBrowser application properties can be reloaded from the database. Properties include
                             Search Prefernces, Available EVS Sources</td>
             <td class="OraFieldText">
                <a href="javascript:reloadCDEBrowserProperties()">
                   <img src="/i/reload.gif" border=0 alt="Reload Properties">
                </a>                  
             </td>
           </tr>
         </table>	     
 </html:form>
</body>
</html>
