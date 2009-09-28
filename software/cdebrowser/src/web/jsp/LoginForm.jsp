<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
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
  document.forms[0].reset();
}
-->

</SCRIPT>
</head>

<body text="#000000" topmargin="0">

		<jsp:include page="/jsp/common/common_cdebrowser_header_jsp_inc.jsp"
			flush="true">
			<jsp:param name="loginDestination"
				value="<%=StringEscapeUtils.escapeSql("formCDECartAction.do?method=displayCDECart") %>" />
			<jsp:param name="urlPrefix" value="" />
		</jsp:include>		
<br>
<br>
  
  <TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <TR>
  <td align=left valign=top width="1%" bgcolor="#336699"><img src="<%=request.getContextPath()%>/i/top_left.gif" width=4 height="25"></td>
  <td nowrap align=left valign=top width="5%" bgcolor="#336699"><b><font size="3" face="Arial" color="#FFFFFF">&nbsp; &nbsp;Please Login</font></b></td>

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
  
  <table>
    <tr>    
      <td align="left" class="OraTipText">
        Guest users can login using username "guest" and password "guest".
   <br> If you require an account with curator privileges to a specific context other than Test, please contact NCICB Application Support Email: <a href='mailto:ncicb@pop.nci.nih.gov'>ncicb@pop.nci.nih.gov</a>
      </td>
    </tr>  
  </table> 
  
  <form method="post" action="<%= StringEscapeUtils.escapeHtml(response.encodeURL("j_security_check")) %>">

  <table align=center cellspacing="2" cellpadding="3" border="0" onkeypress="if(event.keyCode==13){submitForm()};">
    <%	if(request.getParameter("failed") != null && (("y").equalsIgnoreCase((String)request.getParameter("failed")))) {%>
    <tr>
      <td colspan=2 class="OraErrorText" nowrap>
      <b>Invalid username/password combination. Please Retry Login </b>
      </td>
    </tr>
    <% } %>    
    <tr>
        <td class="OraFieldtitlebold" nowrap>Username:</td>
        <td class="OraFieldText" nowrap>
          <input type="text" name="j_username" value="<%=StringEscapeUtils.escapeHtml("")%>" size ="20" /> 
        </td>
    </tr>
    <tr>
        <td class="OraFieldtitlebold" nowrap>Password:</td>
        <td class="OraFieldText" nowrap>
          <input type="password" name="j_password" value="<%=StringEscapeUtils.escapeHtml("")%>" size ="20" autocomplete="off" /> 
        </td>
    </tr>
  </table>
  <table width=100% cellspacing="2" cellpadding="3" border="0">
     <TR>
      	<td width=45%>&nbsp;</td>
      	<td width=55%>
      	  <table cellspacing="2" cellpadding="3" border="0">
		     <TR>
		        <td colspan="1" align="left" nowrap><a href="javascript:submitForm()"><img src=<%=request.getContextPath()%>/i/logon.gif border=0 alt="Logon"></a></td>
		        <td colspan="1" align="left" nowrap><a href="javascript:clearForm()"><img src=<%=request.getContextPath()%>/i/clear.gif border=0 alt="Clear"></a></td>
		     </TR>  
		  </table>    
      	</td>
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
<%@ include file="/jsp/common/common_bottom_border.jsp"%>
</body>
</html>