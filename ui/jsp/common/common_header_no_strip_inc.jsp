<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>

<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%
	String dest = pageContext.getRequest().getParameter("loginDestination");
%>

<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/jsLib/newWinJS.js"/>'></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/jsLib/helpWinJS.js"/>'></SCRIPT>



<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td align="left" nowrap>

    <html:img page="/i/cde_form_builder_banner.gif" border="0" />
    </td>
    <td align=right valign=top colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%= "formCDECartAction.do?method=displayCDECart"%>" TARGET="_top"><IMG SRC="i/cde_cart.gif" alt="CDE Cart" border=0 ></A><br><font color=brown face=verdana size=1>&nbsp;CDE &nbsp;Cart</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%="cdeBrowse.jsp?PageId=DataElementsGroup"%>" TARGET="_top"><IMG SRC="i/icon_home.gif" alt="Home" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Home&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%= "formSearchAction.do"%>" TARGET="_top"><IMG SRC="i/formicon.gif" alt="FormBuilder" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;FormBuilder&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="javascript:newBrowserWin('<%=request.getContextPath()%>/common/help/cdeBrowserHelp.html','helpWin',700,600)"><IMG SRC="i/icon_help.gif" alt="Task Help" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
         <logic:present name="nciUser">
            <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%="logout?FirstTimer=0"%>" TARGET="_top"><html:img page="/i/logout.gif" alt="Logout" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font></TD>
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