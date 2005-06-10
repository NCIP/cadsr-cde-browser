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

function submitForm() {
  document.forms[0].submit();
}

function clearForm()
{
  document.form[0].reset();
}
-->
</SCRIPT>
</head>

<body text="#000000" topmargin="0">

   <%@ include file="basicHeader_inc.jsp"%>
   
<br>
<br>
  
  <TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <TR>
  <td align=left valign=top width="1%" bgcolor="#336699"><img src="i/top_left.gif" width=4 height="25"></td>
  <td nowrap align=left valign=top width="5%" bgcolor="#336699"><b><font size="3" face="Arial" color="#FFFFFF">&nbsp; &nbsp;Please Login</font></b></td>

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
      <td align="left" class="OraTipText">
        Guest users can login using username "guest" and password "guest".
   <br> If you require an account with curator privileges to a specific context other than Test, please contact NCICB Application Support Email: <a href='mailto:ncicb@pop.nci.nih.gov'>ncicb@pop.nci.nih.gov</a>
      </td>
    </tr>  
  </table>  
  
  <form method="POST" action="j_security_check">

  <table align=center cellspacing="2" cellpadding="3" border="0" onkeypress="if(window.event.keyCode==13){submitForm()};">
    <% if(request.getAttribute("msg") != null) { %>
    <tr>
      <b><td colspan=2 class="OraErrorText"><%= request.getAttribute("msg") %></td></b>
    </tr>
    <% } %>
    <tr>
        <td class="OraFieldtitlebold" nowrap>Username:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="j_username" value="" size ="20"> 
        </td>
    </tr>
    <tr>
        <td class="OraFieldtitlebold" nowrap>Password:</td>
        <td class="OraFieldText" nowrap>
          <input type="password" name="j_password" value="" size ="20"> 
        </td>
  
    </tr>  

  </table>
  <table align=center cellspacing="2" cellpadding="3" border="0">
     <TR>
        
        <td colspan="1" align="right" nowrap><a href="javascript:submitForm()"><img src=i/logon.gif border=0></a></td>
        <td colspan="1" align="left" nowrap><a href="javascript:clearForm()"><img src=i/clear.gif border=0></a></td>
    </TR>  
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </table>    
  </form>
  <SCRIPT>
    document.forms[0].elements[0].focus();
  </SCRIPT>
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