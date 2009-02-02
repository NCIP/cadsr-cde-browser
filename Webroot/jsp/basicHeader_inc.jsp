
<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/js/helpWinJS.js"/>'></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/js/newWinJS.js"/>'></SCRIPT>
<%@ include  file="/jsp/common/topHeader.jsp" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%
String preSessionId = (String)request.getParameter(CaDSRConstants.PREVIOUS_SESSION_ID);
    	String forwardPage = "/jsp/cdeBrowse.jsp";
  	if(preSessionId!=null)
	  forwardPage=forwardPage+"?PageId=DataElementsGroup&"+CaDSRConstants.PREVIOUS_SESSION_ID+"="+preSessionId;
  	else
	  forwardPage=forwardPage+"?PageId=DataElementsGroup";  
%>
<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
    <td align="left" nowrap>

    <img src=i/cde_browser_banner_full.gif border=0>
    </td>

    <td align=right valign=top nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0>
        <TR>
          <TD valign="TOP" align="center" width="1%" colspan=1><A HREF="<%=forwardPage%>" TARGET="_top"><IMG SRC="i/icon_cdebrowser.gif" alt="CDE Browser" border=0  width=32 height=32></A></TD>
          <TD valign="TOP" align="left" width="1%" colspan=1><A HREF="javascript:newBrowserWin('/help/','helpWin')"><IMG SRC="i/icon_help.gif" alt="Task Help" border=0  width=32 height=32></A></TD>
        </TR>
        <TR>
          <TD valign="TOP" align="center" colspan=1><font color=brown face=verdana size=1>&nbsp;CDE Browser&nbsp;</font></TD>
          <TD valign="TOP" align="left" colspan=1><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
        </TR>
      </TABLE>
    </td>
  </tr>
</TABLE>