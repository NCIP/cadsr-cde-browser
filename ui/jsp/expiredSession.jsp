<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
<head>
<title>Login</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<SCRIPT LANGUAGE="JavaScript">
<!--
if (parent.frames[1]) 
  parent.location.href = self.location.href; 
-->
</SCRIPT>
</head>

<body text="#000000" topmargin="0">

   <%@ include file="basicHeader_inc.jsp"%>

<br>
<br>
  
  <TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <TR>
  <td align=left valign=top width="1%" bgcolor="#336699"><img src="<%=request.getContextPath()%>/i/top_left.gif" width=4 height="25"></td>
  <td nowrap align=left valign=top width="5%" bgcolor="#336699"><b><font size="3" face="Arial" color="#FFFFFF">&nbsp; &nbsp;Session Expired</font></b></td>

  <td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>
  
  <TD align=left valign=center bgcolor="#336699" height=25 width="94%">&nbsp;</TD>
  </tr>
  </table>
  
  <table  width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
  <td align=right valign=top width=49 height=21 bgcolor="#336699"><img src="<%=request.getContextPath()%>/i/left_end_bottom.gif" height=21 width=49></td>
  <TD align=right valign=top bgcolor="#FFFFFF" height=21 width="100%"><img src="<%=request.getContextPath()%>/i/bottom_middle.gif" height=6 width=100%></TD>
  <td align="LEFT" valign=top height=21 width=5  bgcolor="#FFFFFF"><img src="<%=request.getContextPath()%>/i/right_end_bottom.gif" height=7 width=5></td>
  </TR>
  </table>
  
      
      <table width=100% Cellpadding=0 Cellspacing=0 border=0>
      <tr><td>&nbsp;</td></tr>   
      <tr><td>&nbsp;</td></tr>
      <tr><td align="center" class="OraErrorText"><b>Your Session has Expired!</b></td></tr>
      <tr class="OraTipLabel">
          <td align="center" class="OraTipLabel"> Click <a target="_top" href="cdeBrowse.jsp">here</a> to return to CDEBrowser.
          </td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td class="OraTipLabel"></td></tr>   
      <tr><td>&nbsp;</td></tr>
      </table>

<TABLE width=100% cellspacing=0 cellpadding=0 border=0>
<TR>
<TD valign=bottom width=99%><img src="<%=request.getContextPath()%>/i/bottom_shade.gif" height=6 width="100%"></TD>
<TD valign=bottom width="1%" align=right><IMG src="<%=request.getContextPath()%>/i/bottomblueright.gif"></TD>
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
<TD colspan=2><IMG src="<%=request.getContextPath()%>/i/bottom_middle.gif" height=6 width="100%"></TD>
</TR>
</TABLE>
</body>
</html>     