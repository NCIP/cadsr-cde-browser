
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%
	String dest = pageContext.getRequest().getParameter("loginDestination");
%>

<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/jsLib/newWinJS.js"/>'></SCRIPT>

<%@ include  file="../common/topHeader.jsp" %>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td align="left" nowrap>
    <html:img page="/i/graphic6.gif" border="0" />
    </td>

    <td align=right valign=top colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
         <TD valign="TOP" align="CENTER" width="1%" colspan=1>
             <A HREF='<%=request.getContextPath()%>/cdeBrowse.jsp' TARGET="_top">
               <html:img page="/i/icon_home.gif" alt="Home" border="0"  width="32" height="32" />
             </A><br><font color=brown face=verdana size=1>&nbsp;Home&nbsp;</font>
          </TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1>
             <A HREF='<%=request.getContextPath()%>/formSearchAction.do' TARGET="_top">
               <html:img page="/i/formicon.gif" alt="FormBuilder" border="0"  width="32" height="32" />
             </A><br><font color=brown face=verdana size=1>&nbsp;Form&nbsp;Builder&nbsp;</font>
           </TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1>
          <A HREF="javascript:newBrowserWin('<%=request.getContextPath()%>/common/help/cdeBrowserHelp.html','helpWin',700,600)">
            <html:img page="/i/icon_help.gif" alt="Help" border="0"  width="32" height="32" />
          </A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
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