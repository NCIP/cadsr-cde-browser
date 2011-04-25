
 <script type="text/javascript" src="/CDEBrowser/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true">
 </script>

<link rel="stylesheet" type="text/css" href="/CDEBrowser/js/dojo/dijit/themes/claro/claro.css" />

<script type="text/javascript">
	dojo.require("dijit.form.ComboBox");
	dojo.require("dojo.data.ItemFileReadStore");
	
	var completeData = new dojo.data.ItemFileReadStore({url:"", clearOnClose:"true", urlPreventCache:"true"});
	var tmpVal = null;
	
	function refreshStore(evnt) {
		if (evnt.target.value != tmpVal && evnt.target.value.length >= 3) {
			tmpVal = evnt.target.value;
			completeData.close();
			completeData.url = "/CDEBrowser/jsp/cdebrowser/instantSearch.jsp?searchStr="+evnt.target.value;
			completeData.fetch({onComplete: function(items, req){searchBox.open();}});
			searchBox.store=completeData;
		}
	}

	function populateLongName(widget, item, store) {
		if (item != null) {
			var label = store.getValue(item, 'longName');
			document.forms[0].jspSimpleKeyword.value = label;
			widget.setValue(tmpVal);
		}
    }

    function labelFunc(item) {
    	var longName = searchBox.store.getValue(item, 'prettyLongName');
    	return longName;
    }

</script>

<INPUT TYPE="HIDDEN" NAME="jspSearchIn" VALUE="ALL">
<!-- >INPUT TYPE="HIDDEN" NAME="jspLatestVersion" VALUE="Yes" -->
<INPUT TYPE="HIDDEN" NAME="contextUse" VALUE="both">
<INPUT TYPE="HIDDEN" NAME="jspStatus" VALUE="ALL">
<INPUT TYPE="HIDDEN" NAME="regStatus" VALUE="ALL">
<INPUT TYPE="HIDDEN" NAME="jspCdeId" >
<INPUT TYPE="HIDDEN" NAME="jspKeyword" >

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
   <td  align="center" colspan="4"><html:img page="/i/beigedot.gif" border="0"  height="1" width="99%" align="top" /> </td>
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
 <table valign="top">
  <tr>
   <td valign="top" class="OraTableColumnHeaderWhiteBG" nowrap>
      <input type="radio" name="jspNameSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_EXACT%>"
      <%if (desb.getNameSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_EXACT))
      { %> checked <%}%> >Exact phrase
   </td >
  </tr>
  <tr>
   <td valign="top" class="OraTableColumnHeaderWhiteBG" nowrap>
      <input type="radio" name="jspNameSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_ALL%>" 
      <%if (desb.getNameSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_ALL)) 
      { %> checked <%}%>>All of the words
   </td >
  </tr>
  <tr>
   <td valign="top" class="OraTableColumnHeaderWhiteBG" nowrap>
      <input type="radio" name="jspNameSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_ANY%>" 
      <%if (desb.getNameSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_ANY)) { %> checked <%}%> >At least one of the words
   </td >
  </tr>
</table>    
    </td>
	<td>
		<table valign="top">
			<tr>
				<td>
					<select dojoType="dijit.form.ComboBox"
					   onKeyUp="refreshStore"
						onChange="populateLongName(this, this.item, this.store)" 
						searchAttr="searchStr" 
						 id="searchBox"
						labelType="html"
						labelAttr="prettyLongName"
					   style="width: 500px; font-family: Arial, Helvetica, sans-serif; font-size: 16px" autoComplete="false"
					   jsId="searchBox" { sort: {attribute:"longName"} } 
						hasDownArrow="false" ignoreCase="true"
						pageSize=10 
						title="Start typing the long name here for a list of possible matches"
						value="Start typing the long name here for a list of possible matches"
						onFocus="if (this.getValue()=='' || this.getValue()=='Start typing the long name here for a list of possible matches') this.setValue('')"
						onBlur="if (this.getValue()=='') this.setValue('Start typing the long name here for a list of possible matches')"
					>
					</select>
				</td>
			</tr>
			<tr>
				<td width="40%" align="left" nowrap >
			      <input type="text" name="jspSimpleKeyword" value="<%=desb.getSimpleSearchStr()%>" style="border-color:black;border-width:1px;border-style:solid;width:500px;font-family: Arial, Helvetica, sans-serif;font-size: 16px"> 
			    </td>
			</tr>
		</table>
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
    <html:img page="/i/search_within_result.gif" border="0" />
<% }else { %>       
    <html:img page="/i/search.gif" border="0" />
<% } %>
    </a></td>
    <td  align="center" nowrap><a href="javascript:clearSimpleForm()"><html:img page="/i/clear.gif" border="0" /></a></td>
    <%
       if(deList!=null){
    %>
   <td  align="center" nowrap><a href="javascript:newSearch()"><html:img page="/i/newSearchButton.gif" border="0" /></a></td>
   <%}%>
 </TR>
 </table>
<%
  }
  else {
%>
  <table with ="80%" align="center">
  <TR>
    <td  nowrap  ><a href="javascript:submitSimpleForm();"><html:img page="/i/SearchDataElements.gif" border="0" /></a>
    </td>
    <td><a href="javascript:clearSimpleForm()"><html:img page="/i/clear.gif" border="0" /></a>
    </td>
    <%
      if(deList!=null){
    %>
    <td><a href="javascript:newSearch()"><html:img page="/i/newSearchButton.gif" border="0" /></a>
    </td>
    <%}%>
    <td><a href="javascript:done()"><html:img page="/i/backButton.gif" border="0" /></a>
    </td>
   </TR>
 </table>
<%
  }
%>
