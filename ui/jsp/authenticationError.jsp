<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<html>
<head>
<title>Login Error</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<SCRIPT LANGUAGE="JavaScript">
<!--
if (parent.frames[1]) 
  parent.location.href = self.location.href; 
-->
</SCRIPT>
</head>
<%
  String previousSessionId = (String)request.getParameter(CaDSRConstants.PREVIOUS_SESSION_ID);
  String forwardUrl = "cdeBrowse.jsp";
  if(previousSessionId!=null)
    forwardUrl=forwardUrl+"?PageId=DataElementsGroup&"+CaDSRConstants.PREVIOUS_SESSION_ID+"="+previousSessionId;
	
%>

<body text="#000000" topmargin="0">
<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td align="left" nowrap>

    <img src=i/graphic6.gif border=0>
    </td>

    <td align=right valign=top colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%=forwardUrl%>" TARGET="_top"><IMG SRC="i/icon_home.gif" alt="Home" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Home&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="javascript:newBrowserWin('cdebrowserCommon_html/cdeBrowserHelp.html','helpWin',700,600)"><IMG SRC="i/icon_help.gif" alt="Task Help" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
        </TR>
      </TABLE>
    </td>
  </tr>

</TABLE>
<br>
<br>
  
  <TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <TR>
  <td align=left valign=top width="1%" bgcolor="#336699"><img src="i/top_left.gif" width=4 height="25"></td>
  <td nowrap align=left valign=top width="5%" bgcolor="#336699"><b><font size="3" face="Arial" color="#FFFFFF"></font></b></td>

  <td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>
  
  <TD align=left valign=center bgcolor="#336699" height=25 width="94%">&nbsp;</TD>
  </tr>
  </table>
  
  <table  width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
  <td align=right valign=top width=49 height=21 bgcolor="#336699"><img src="i/left_end_bottom.gif" height=21 width=49></td>
  <TD align=right valign=top bgcolor="#FFFFFF" height=21 width="100%"><img src="i/bottom_middle.gif" height=6 width=100%></TD>
  <td align="LEFT" valign=top height=21 width=5  bgcolor="#FFFFFF"><img src="i/right_end_bottom.gif" height=7 width=5></td>
  </TR>
  </table>
  
      <table>
            <tr>
              <td class="OraErrorHeader">
              	Login Error <br><br>
                <table width="80%" align="center">
                  <tr align="center" >
                     <td  align="left" class="OraErrorText" nowrap>
                      <b>Invalid username/password combination. <a href="LoginForm.jsp"> Retry Login </a></b>
                    </td>
                  </tr>
                  <tr align="center" >
                   <td>
                      &nbsp;
                  </td>
                 </tr>        
                </table>
              </td>
            </tr>
 </table>

<TABLE width=100% cellspacing=0 cellpadding=0 border=0>
<TR>
<TD valign=bottom width=99%><img src="i/bottom_shade.gif" height=6 width="100%"></TD>
<TD valign=bottom width="1%" align=right><IMG src="i/bottomblueright.gif"></TD>
</TR>
</TABLE>
<TABLE width=100% cellspacing=0 cellpadding=0 bgcolor="#336699" border=0>
<TR>
<TD width="60%" align="LEFT">
<FONT face="Arial" color="WHITE" size="-2"></FONT>
<FONT face="Arial" size="-1" color="#CCCC99"></FONT>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<FONT color="white" size=-2 face=arial></FONT>
</TD>

</TR>
<TR>
<TD colspan=2><IMG src="i/bottom_middle.gif" height=6 width="100%"></TD>
</TR>
</TABLE>
</body>
</html>     