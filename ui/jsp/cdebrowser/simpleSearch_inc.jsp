

<INPUT TYPE="HIDDEN" NAME="jspSearchIn" VALUE="ALL">
<INPUT TYPE="HIDDEN" NAME="jspLatestVersion" VALUE="Yes">
<INPUT TYPE="HIDDEN" NAME="contextUse" VALUE="both">
<INPUT TYPE="HIDDEN" NAME="jspStatus" VALUE="ALL">
<INPUT TYPE="HIDDEN" NAME="regStatus" VALUE="ALL">

<table width="100%" >
 
 <tr align="left">
    <td class="OraHeaderSubSub" width="60%" align="left" nowrap>Search for DataElements</td>
     <td align="right" class="MessageText"  width="20%" nowrap><b>
   <%
   if (deList!=null&&deList.size()==0)
   {
   %>
    No Matches 
   <%
   }
   else if(deList!=null&&deList.size()!=0)
   {
   %>
    <a class="link" href="#results"><%=topScroller.getTotalRecordCount()%> Matches</a>
   <%
   }
   else{%>
    &nbsp;
   <%
   }
   %>
   </b></td>
  <td align="right" width="20%" nowrap>
        <a href="javascript:changeScreenType(<%="'"+BrowserFormConstants.BROWSER_SCREEN_TYPE_ADVANCED+"'"%>)">
          Advanced search</a>
   </td>

   
 </tr>   
 <tr>
   <td  align="center" colspan="3"><html:img page="/i/beigedot.gif" border="0"  height="1" width="99%" align="top" /> </td>
  </tr> 
 </table>
 
 <table valign="top">
  <tr>
   <td valign="top" class="CDEBrowserPageContext">
     <%=pageContextInfo%>
   </td >
  </tr>
</table>

 <table align="center" width="60%" border="0" cellpadding="0" cellspacing="1" class="OraBGAccentVeryDark" border="0" >
 <tr>
    <td class="OraTabledata"  align="center" nowrap >
      <input type="text" name="jspKeyword" value="<%=desb.getSearchText()%>" size ="65"> 
    </td>

 </tr>
 <tr>
    <td class="OraTabledata"  align="left" nowrap >
      <table width="30%" border="0" >
       <tr>
         <td  nowrap>&nbsp;</td>
         <td class="OraTableColumnHeaderNoBG" nowrap>Search by public ID </td>
         <td class="OraTabledata"  nowrap>
            <input type="checkbox" name="jspPublicIdInd" value="<%=desb.getSearchText()%>" size ="60"> 
         </td>
       </tr>
      </table>
    </td>
 </tr> 
 </table>
 
<br>

 
<table width="80%" border="0" align="center"> 
<%
  if ("".equals(src)) {
%>
 <table with ="80%" align="center" border="0">
 <TR>
    <td align="center" nowrap><a href="javascript:submitSimpleForm()"><html:img page="/i/search.gif" border="0" /></a></td>
    <td  align="center" nowrap><a href="javascript:clearSimpleForm()"><html:img page="/i/clear.gif" border="0" /></a></td>
    <td  align="center" nowrap><a href="javascript:newSearch()"><html:img page="/i/newSearchButton.gif" border="0" /></a></td>
 </TR>
 </table>
<%
  }
  else {
%>
  <table with ="80%" align="center">
  <TR>
    <td  nowrap  ><a href="javascript:submitSimpleForm()"><html:img page="/i/SearchDataElements.gif" border="0" /></a>
    </td>
    <td><a href="javascript:clearSimpleForm()"><html:img page="/i/clear.gif" border="0" /></a>
    </td>
    <td><a href="javascript:newSearch()"><html:img page="/i/newSearchButton.gif" border="0" /></a>
    </td>
    <td><a href="javascript:done()"><html:img page="/i/backButton.gif" border="0" /></a>
    </td>
   </TR>
 </table>
<%
  }
%>
