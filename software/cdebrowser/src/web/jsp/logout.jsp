<%--L
  Copyright SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.

  Portions of this source file not modified since 2008 are covered by:

  Copyright 2000-2008 Oracle, Inc.

  Distributed under the caBIG Software License.  For details see
  http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
L--%>

<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<html>
<head>
<title>Logout</title>
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
	CDEBrowserParams params = CDEBrowserParams.getInstance();
	//clear the session attribute so not to display done button which would take you back to form builder
	request.getSession().removeAttribute("paramsTree");	
	//reload session attributes
	String previousSessionId = (String)request.getParameter(CaDSRConstants.PREVIOUS_SESSION_ID);
  String forwardUrl = request.getContextPath()+"/jsp/cdeBrowse.jsp";
  if(previousSessionId!=null)  
    forwardUrl=forwardUrl+"?PageId=DataElementsGroup&"+CaDSRConstants.PREVIOUS_SESSION_ID+"="+previousSessionId;
	
%>
<body text="#000000" topmargin="0">

	<jsp:include page="/jsp/common/common_cdebrowser_header_jsp_inc.jsp"
			flush="true">
			<jsp:param name="loginDestination"
				value="formCDECartAction.do?method=displayCDECart" />
			<jsp:param name="urlPrefix" value="" />
		</jsp:include>
		<%--<%@ include file="basicHeader_inc.jsp"%>--%>
	
<br>
<br>
  
  <TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <TR>
  <td align=left valign=top width="1%" bgcolor="#336699"><img src="i/top_left.gif" alt="top left" width=4 height="25"></td>
  <td nowrap align=left valign=top width="5%" bgcolor="#336699"><b><font size="3" face="Arial" color="#FFFFFF"></font></b></td>

  <td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>
  
  <TD align=left valign=center bgcolor="#336699" height=25 width="94%">&nbsp;</TD>
  </tr>
  </table>
  
  <table  width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
  <td align=right valign=top width=49 height=21 bgcolor="#336699"><img src="i/left_end_bottom.gif" alt="left end bottom" height=21 width=49></td>
  <TD align=right valign=top bgcolor="#FFFFFF" height=21 width="100%"><img src="i/bottom_middle.gif" alt="botoom middel" height=6 width=100%></TD>
  <td align="LEFT" valign=top height=21 width=5  bgcolor="#FFFFFF"><img src="i/right_end_bottom.gif" alt="right end bottom" height=7 width=5></td>
  </TR>
  </table>
  
      <table width=100% Cellpadding=0 Cellspacing=0 border=0>
      <tr><td>&nbsp;</td></tr>   
      <tr><td>&nbsp;</td></tr>
      <tr><td>&nbsp;</td></tr>
      <tr class="OraTipLabel">
          <td align="center" class="OraTipLabel">You have been successfully logged out.
          Click <a target="_top" href="<%=forwardUrl%>">here</a> to return to CDEBrowser.
          </td>
      </tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td class="OraTipLabel"></td></tr>   
      <tr><td>&nbsp;</td></tr>
      </table>

<%--<TABLE width=100% cellspacing=0 cellpadding=0 border=0>
<TR>
<TD valign=bottom width=99%><img src="<%=request.getContextPath()%>/i/bottom_shade.gif" alt="bottom shade" height=6 width="100%"></TD>
<TD valign=bottom width="1%" align=right><IMG src="<%=request.getContextPath()%>/i/bottomblueright.gif" alt="bottom blue right"></TD>
</TR>
</TABLE>
--%><%--<TABLE width=100% cellspacing=0 cellpadding=0 bgcolor="#336699" border=0>
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
</TABLE>
--%><%@ include file="/jsp/common/common_bottom_border.jsp"%>
</body>
</html>     