
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%
	String dest = pageContext.getRequest().getParameter("loginDestination");
%>

<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/jsLib/newWinJS.js"/>'></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/jsLib/helpWinJS.js"/>'></SCRIPT>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td align="left" nowrap>

    <img src=i/graphic6.gif border=0>
    </td>

    <td align=right valign=top colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%= "formCDECartAction.do?method=displayCDECart"%>" TARGET="_top"><IMG SRC="i/cde_cart.gif" alt="CDE Cart" border=0 ></A><br><font color=brown face=verdana size=1>&nbsp;CDE &nbsp;Cart</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%="cdeBrowse.jsp?PageId=DataElementsGroup"%>" TARGET="_top"><IMG SRC="i/icon_home.gif" alt="Home" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Home&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%= "formSearchAction.do"%>" TARGET="_top"><IMG SRC="i/formicon.gif" alt="FormBuilder" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;FormBuilder&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="javascript:newBrowserWin('<%=request.getContextPath()%>/common/help/cdeBrowserHelp.html',,'helpWin',700,600)"><html:img page="/i/icon_help.gif" alt="Task Help" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
         <logic:present name="nciUser">
            <TD valign="TOP" align="CENTER" width="1%" colspan=1><html:link page="/logout?FirstTimer=0" target="_top"><html:img page="/i/logout.gif" alt="Logout" border="0"  width="32" height="32" /></html:link><br><font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font></TD>
          </logic:present>
          <logic:notPresent name="nciUser">
            <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%= dest %>" TARGET="_top"><html:img page="/i/icon_login.gif" alt="Login" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;Login&nbsp;</font></TD>
          </logic:notPresent>
        </TR>
      </TABLE>
    </td>
  </tr>
   <tr>
    <td  width="100%" >
    <TABLE align ="left" width="100%" Cellpadding=0 Cellspacing=0 border=0 >
          <tr>
           <td  align="left" width="20%" height="10" nowrap><span style="font-size: 10.0pt; font-family: Arial">
               <a href="http://cadsr-prod.nci.nih.gov" target="_blank" >Admin Tool&nbsp;</a> </span></td>

           <td  align="left" width="20%" height="10" nowrap><span style="font-size: 10.0pt; font-family: Arial">
                <a href="http://ncicb.nci.nih.gov/cdecurate" target="_blank">
                      Curation Tool&nbsp;</a></span>
            </td>
           <td  align="left" width="28%" height="10" nowrap><span style="font-size: 10.0pt; font-family: Arial">
                <a href="http://ncimeta.nci.nih.gov" target="_blank" >
                      NCI Metathesaurus&nbsp;</a></span>
            </td>
           <td  align="left" width="32%" height="10" nowrap><span style="font-size: 10.0pt; font-family: Arial">
                <a href="http://nciterms.nci.nih.gov" target="_blank">
                      NCI Terminology Server&nbsp;</a></span>
            </td>

        </tr>
      </table>
     </td>
    <td  width="100%" >
    <TABLE align ="left" width="100%" Cellpadding=0 Cellspacing=0 border=0 >
          <tr>
           <td  align="right"   height="10" nowrap><span style="font-size: 10.0pt; font-family: Arial">
                <a href="http://nciterms.nci.nih.gov" target="_blank">
                      What's new</a>&nbsp;&nbsp;&nbsp;</span>
            </td>

        </tr>
      </table>
     </td>
  </tr>
  <logic:present name="nciUser">
  <tr>
    <td align="left" class="OraInlineInfoText" nowrap>
        <bean:message key="user.greet" />
    	<bean:write name="nciUser" property="username"  scope="session"/>
    </td>
  </tr>
 </logic:present>
  <logic:notPresent name="nciUser">
  <tr>
    <td height="2" align="left"  nowrap>
		&nbsp;
    </td>
  </tr>
 </logic:notPresent>
</TABLE>