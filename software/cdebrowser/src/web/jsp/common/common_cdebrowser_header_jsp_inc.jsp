<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%
	String dest = pageContext.getRequest().getParameter("loginDestination");	
	String frmURL = CDEBrowserParams.getInstance().getFormBuilderUrl();
	String previousSessionId = (String)request.getParameter(CaDSRConstants.PREVIOUS_SESSION_ID);
    String forwardCDEUrl = request.getContextPath()+"/jsp/cdeBrowse.jsp";
    CDEBrowserParams params = CDEBrowserParams.getInstance();
  //if(previousSessionId!=null)
    //forwardCDEUrl=forwardCDEUrl+"?PageId=DataElementsGroup&"+CaDSRConstants.PREVIOUS_SESSION_ID+"="+previousSessionId;
%>

<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/js/newWinJS.js"/>'></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/js/helpWinJS.js"/>'></SCRIPT>

<%@ include  file="/jsp/common/topHeader.jsp" %>

<TABLE valign="top" width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td valign="top" align="left" nowrap>

    <html:img page="/i/cde_browser_banner_full.gif"  alt="cde browser banner full" border="0" />
    </td>
    <td align=right valign=top colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%=forwardCDEUrl%>" TARGET="_top"><IMG SRC="<%=request.getContextPath()%>/i/icon_home.gif" alt="Home" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Home&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%=frmURL%>/FormBuilder/formSearchAction.do" TARGET="_blank"><html:img page="/i/formicon.gif" alt="FormBuilder" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;FormBuilder&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%=params.getCdeBrowserHelpUrl()%>" target="_blank">
          <html:img page="/i/icon_help.gif" alt="Task Help" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
         <logic:present name="nciUser">
            <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%=request.getContextPath()%>/logout?FirstTimer=0" TARGET="_top"><html:img page="/i/logout.gif" alt="Logout" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font></TD>
          </logic:present>
          <logic:notPresent name="nciUser">
            <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%= dest %>" TARGET="_top">
            <html:img page="/i/icon_login.gif" alt="Login" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;Login&nbsp;</font></TD>
          </logic:notPresent>
        </TR>
      </TABLE>
    </td>
  </tr>
  <tr>
    <td align="left" class="OraInlineInfoText" nowrap>
       <logic:present name="nciUser">
        <bean:message key="user.greet" />
    	<bean:write name="nciUser" property="username"  scope="session"/>
       </logic:present>
    </td>    
  </tr>
</TABLE>