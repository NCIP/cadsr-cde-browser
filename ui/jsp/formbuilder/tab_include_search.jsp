
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
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%="/cdebrowser/"%>" TARGET="_top"><IMG SRC="<%=urlPrefix%>i/icon_home.gif" alt="Home" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Home&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="javascript:newBrowserWin('cdebrowserCommon_html/cdeBrowserHelp.html','helpWin',700,600)"><IMG SRC="<%=urlPrefix%>i/icon_help.gif" alt="Task Help" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%="/cdebrowser/logout?"+CaDSRConstants.LOGOUT_URL+"=/cdebrowser/"%>" TARGET="_top"><IMG SRC="<%=urlPrefix%>i/logout.jpg" alt="Logout" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font></TD>

        </TR>
      </TABLE>
    </td>
  </tr>

</TABLE>
<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
    <td align="left" class="fieldtitleleft" nowrap>
       <logic:present name="nciUser">
        <bean:message key="user.greet" />
    	<bean:write name="nciUser" property="username"  scope="session"/>&nbsp;
       </logic:present>
    </td>
    
  </tr>
  <tr>
    <td width=98%>&nbsp;</td>
    <td valign=bottom align=right>
      <table border=0 cellpadding=0 cellspacing=0>
        <tr>


<TD bgcolor="#336699" width="1%" align=LEFT valign=TOP><IMG SRC="<%=urlPrefix%>i/ctab_open.gif" height=21 width=18 border=0></TD>
<TD width=1% bgcolor="#336699"><b><font size="-1" face="Arial" color="#FFFFFF"><%= "Form&nbsp;Search"%></font></b></TD>
<TD bgcolor="#336699" width="1%" align=RIGHT valign=TOP><IMG SRC="<%=urlPrefix%>i/ctab_close.gif" height=21 width=12 border=0></TD>



</table>
</td>
</TR>
</TABLE>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
<TR>
<td align=left valign=top width="1%" bgcolor="#336699"><img src="<%=urlPrefix%>i/top_left.gif" width=4 height="25"></td>
<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>


<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>

<!-- add here --->

<TD align=left valign=center bgcolor="#336699" height=25 width="94%">&nbsp;</TD>
</tr>
</table>

<table  width=100% Cellpadding=0 Cellspacing=0 border=0>
<tr>
<td align=right valign=top width=49 height=21 bgcolor="#336699"><img src="<%=urlPrefix%>i/left_end_bottom.gif" height=21 width=49></td>
<TD align=right valign=top bgcolor="#FFFFFF" height=21 width="100%"><img src="<%=urlPrefix%>i/bottom_middle.gif" height=6 width=100%></TD>
<td align="LEFT" valign=top height=21 width=5  bgcolor="#FFFFFF"><img src="<%=urlPrefix%>i/right_end_bottom.gif" height=7 width=5></td>
</TR>
</TABLE>
