
<SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=urlPrefix%>jsLib/newWinJS.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=urlPrefix%>ljsLib/helpWinJS.js"></SCRIPT>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td align="left" nowrap>

    <img src=<%=urlPrefix%>i/graphic6.gif border=0>
    </td>

    <td align=right valign=top colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%="cdeBrowse.jsp?PageId=DataElementsGroup"%>" TARGET="_top"><IMG SRC="<%=urlPrefix%>i/icon_home.gif" alt="Home" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Home&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="javascript:newBrowserWin('cdebrowserCommon_html/cdeBrowserHelp.html','helpWin',700,600)"><IMG SRC="<%=urlPrefix%>i/icon_help.gif" alt="Task Help" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%="logout?"+CaDSRConstants.LOGOUT_URL+"=cdeBrowse.jsp?FirstTimer=0"%>" TARGET="_top"><IMG SRC="<%=urlPrefix%>i/logout.jpg" alt="Logout" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font></TD>

        </TR>
      </TABLE>
    </td>
  </tr>
  <tr>
    <td align="left" class="fieldtitleleft" nowrap>
       <logic:present name="nciUser">
        <bean:message key="user.greet" />
    	<bean:write name="nciUser" property="username"  scope="session"/>
       </logic:present>
    </td>    
  </tr>
</TABLE>

