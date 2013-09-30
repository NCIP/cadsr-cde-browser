<%--L
  Copyright SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.

  Portions of this source file not modified since 2008 are covered by:

  Copyright 2000-2008 Oracle, Inc.

  Distributed under the caBIG Software License.  For details see
  http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
L--%>

<%
String  basicSearchType = desb.getBasicSearchType();
String  basicSearchTypeName ="selected";
String  basicSearchTypePublicId="";
if(basicSearchType.equalsIgnoreCase("publicId"))
{
  basicSearchTypePublicId="selected";
}

%>
 
<INPUT id ="simpleSearch_input_5"  TYPE="HIDDEN" NAME="jspSearchIn" VALUE="ALL">

<!-- >INPUT TYPE="HIDDEN" NAME="jspLatestVersion" VALUE="Yes"-->
 
<INPUT id ="simpleSearch_input_7"  TYPE="HIDDEN" NAME="contextUse" VALUE="both">
 
<INPUT id ="simpleSearch_input_8"  TYPE="HIDDEN" NAME="jspStatus" VALUE="ALL">
 
<INPUT id ="simpleSearch_input_9"  TYPE="HIDDEN" NAME="regStatus" VALUE="ALL">
 
<INPUT id ="simpleSearch_input_10"  TYPE="HIDDEN" NAME="jspCdeId" >
 
<INPUT id ="simpleSearch_input_11"  TYPE="HIDDEN" NAME="jspKeyword" >

<table width="100%" >
 
 <tr align="left">
    <td class="OraHeaderSubSub" width="50%" align="left" nowrap>
<% if (searchMode!=null && searchMode.equals(BrowserFormConstants.BROWSER_SEARCH_SCOPE_SEARCHRESULTS)) {
%>
    Search Within Search Results</td>
<% }else { %>Search for Data Elements</td>
<% } %>
   <td align="right" class="MessageText"  width="10%" nowrap><b>
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
        <a href="javascript:gotoCDESearchPrefs()">
          Search preferences</a>
   </td>
  <td align="right" width="20%" nowrap>
        <a href="javascript:changeScreenType(<%="'"+BrowserFormConstants.BROWSER_SCREEN_TYPE_ADVANCED+"'"%>)">
          Advanced search</a>
   </td>
   
 </tr>   
 <tr>
   <td  align="center" colspan="4"><html:img page="/i/beigedot.gif" alt="beigedot" border="0"  height="1" width="99%" align="top" /> </td>
  </tr> 
 </table>
 
 <table valign="top">
  <tr>
   <td valign="top" class="CDEBrowserPageContext">
     <%=pageContextInfo%>
   </td >
  </tr>
  <% if (searchCrumb != null) { %>
  <tr>
   <td valign="top" class="CDEBrowserPageContext">
     <%=searchCrumb%>
   </td >
  </tr>
  <%} %>
</table>

 <table align="center" width="100%" border="0" cellpadding="0" cellspacing="1"  border="0" >
 <tr>
    <td   width="40%" align="left" nowrap >
    <fieldset style="border:0px;">
  	<legend/>
 <table valign="top">
  <tr>
   <td valign="top" class="OraTableColumnHeaderWhiteBG" nowrap>
 	<label  for ="simpleSearch_input_1"/>
      <input id ="simpleSearch_input_1" type="radio" name="jspNameSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_EXACT%>"
      <%if (desb.getNameSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_EXACT))
      { %> checked <%}%> >Exact phrase
   </td >
  </tr>
  <tr>
   <td valign="top" class="OraTableColumnHeaderWhiteBG" nowrap>
   <label  for ="simpleSearch_input_2"/>
      <input id ="simpleSearch_input_2"  type="radio" name="jspNameSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_ALL%>" 
      <%if (desb.getNameSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_ALL)) 
      { %> checked <%}%>>All of the words
   </td >
  </tr>
  <tr>
   <td valign="top" class="OraTableColumnHeaderWhiteBG" nowrap>
   <label  for ="simpleSearch_input_3"/>
      <input id ="simpleSearch_input_3"  type="radio" name="jspNameSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_ANY%>" 
      <%if (desb.getNameSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_ANY)) { %> checked <%}%> >At least one of the words
   </td >
  </tr>
</table>
	</fieldset>    
    </td>
    <td width="40%" align="left" nowrap >
    <label  for ="simpleSearch_input_4"/>
      <input id ="simpleSearch_input_4"  type="text" name="jspSimpleKeyword" value="<%=desb.getSimpleSearchStr()%>" size ="60"> 
    </td>
    <td width="20%" align="left" nowrap >
     <label  for ="simpleSearch_select"/>
      <select id ="simpleSearch_select" name="jspBasicSearchType" class="Dropdown" name="contextIdSeq" >

        <option value="name" <%=basicSearchTypeName%> >Name</option> 
        <option value="publicId" <%=basicSearchTypePublicId%> >Public ID</option> 
      </select>

    </td>
 </tr>
 </table>
 
 
 <table align="center" valign="top"  width="78%" >
     <tr valign="top">    
          <td colspan=2 width="100%" valign="top" align="left" class="AbbreviatedText">
            Tip: This is an exact match search. To search for partial words or phrases use the * as a wildcard.
          </td>
      </tr>       
     <tr valign="top">    
          <td colspan=2 width="100%" valign="top" align="left" class="AbbreviatedText">
            <bean:message key="cadsr.cdebrowser.helpText.results"/>
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
    <td align="center" nowrap><a href="javascript:submitSimpleForm()">
<% if (searchMode!=null && searchMode.equals(BrowserFormConstants.BROWSER_SEARCH_SCOPE_SEARCHRESULTS)) {
%>
    <html:img page="/i/search_within_result.gif" alt="search within results" border="0" />
<% }else { %>       
    <html:img page="/i/search.gif" alt="search" border="0" />
<% } %>
    </a></td>
    <td  align="center" nowrap><a href="javascript:clearSimpleForm()"><html:img page="/i/clear.gif" alt="clear" border="0" /></a></td>
    <%
       if(deList!=null){
    %>
   <td  align="center" nowrap><a href="javascript:newSearch()"><html:img page="/i/newSearchButton.gif" alt="new search button" border="0" /></a></td>
   <%}%>
 </TR>
 </table>
<%
  }
  else {
%>
  <table with ="80%" align="center">
  <TR>
    <td  nowrap  ><a href="javascript:submitSimpleForm()"><html:img page="/i/SearchDataElements.gif" alt="search data elements" border="0" /></a>
    </td>
    <td><a href="javascript:clearSimpleForm()"><html:img page="/i/clear.gif" alt="clear" border="0" /></a>
    </td>
    <%
      if(deList!=null){
    %>
    <td><a href="javascript:newSearch()"><html:img page="/i/newSearchButton.gif" alt="new search button" border="0" /></a>
    </td>
    <%}%>
    <td><a href="javascript:done()"><html:img page="/i/backButton.gif" alt="back button" border="0" /></a>
    </td>
   </TR>
 </table>
<%
  }
%>
