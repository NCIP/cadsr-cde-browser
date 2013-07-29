<%--L
  Copyright Oracle Inc, SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
L--%>


<table width="100%" >
 
 <tr valign="bottom" align="left">
    <td class="OraHeaderSubSub" width="50%" align="left" nowrap>Search by Name</td>
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
        <a href="javascript:changeScreenType(<%="'"+BrowserFormConstants.BROWSER_SCREEN_TYPE_SIMPLE+"'"%>)">
          Basic search</a>
   </td>

   
 </tr>   
 <tr valign="top" >
   <td  align="center" colspan="4"><html:img page="/i/beigedot.gif" alt="beigedot" border="0"  height="1" width="99%" align="top" /> </td>
  </tr> 
 </table>
 
  <table valign="top">
  <tr>
   <td valign="top" class="CDEBrowserPageContext">
     <%=pageContextInfo%>
   </td >
  </tr>
</table>

 

  <table width="100%" cellpadding="0" cellspacing="1" class="OraBGAccentVeryDark" border="0" >
  <tr>
     <td width="75%" class="OraTabledata"  align="center" nowrap >
       <table border="0" width="100%" cellpadding="0" cellspacing="0" class="OraTabledata" >
         <tr>
          <td colspan="2" align="center">
          <table border="0" width="100%" cellpadding="0" cellspacing="0" class="OraTabledata" >
          <tr>
          <td>
           <table valign="top">
	    <tr>
	     <td valign="top" class="OraTableColumnHeaderWhiteBG" nowrap>
	     	<label for ="advanceSearch_input_1"/>
	        <input id="advanceSearch_input_1" type="radio" name="jspNameSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_EXACT%>"
	        <%if (desb.getNameSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_EXACT))
	        { %> checked <%}%> >Exact phrase
	     </td >
	    </tr>
	    <tr>
	     <td valign="top" class="OraTableColumnHeaderWhiteBG" nowrap>
	     	<label for ="advanceSearch_input_2"/>
	        <input id="advanceSearch_input_2" type="radio" name="jspNameSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_ALL%>" 
	        <%if (desb.getNameSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_ALL)) 
	        { %> checked <%}%>>All of the words
	     </td >
	    </tr>
	    <tr>
	     <td valign="top" class="OraTableColumnHeaderWhiteBG" nowrap>
	     	<label for ="advanceSearch_input_3"/>
	        <input  id ="advanceSearch_input_3" type="radio" name="jspNameSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_ANY%>" 
	        <%if (desb.getNameSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_ANY)) { %> checked <%}%> >At least one of the words
	     </td >
	    </tr>
	  </table>    

          </td>
          <td>
          <label for ="advanceSearch_input_4"/>
           <input id ="advanceSearch_input_4" type="text" name="jspKeyword" value="<%=desb.getSearchText()%>" size ="45"> 
           </td>
           </tr>
           </table>
          </td>
         </tr>
         <tr vlign="top">
            <td nowrap>&nbsp;</td>
            <td width="100%" valign="top" align="center" class="AbbreviatedText">Tip: To search for partial words or phrases use the * as a wildcard.</td>
          </td>
         </tr>
         <tr valign="top">  
          <td nowrap>&nbsp;</td>
          <td  width="100%" valign="top" align="left" class="AbbreviatedText">
            <bean:message key="cadsr.cdebrowser.helpText.results"/>
          </td>
         </tr>           
     	</table>     
       
     </td>
     <td width="25%" class="OraTabledata" valign="top" >
     	<table border="0" width="100%" cellpadding="1" cellspacing="1" class="OraTabledata" >
     	 <tr >
     	    <td  width="100%" class="OraTableColumnHeaderNoBG"  nowrap>Search in the following field(s)</td>
     	 </tr>
     	 <tr>
     	    <td width="80%"  class="OraTabledata" align="right" ><%=desb.getSearchInList()%></td> 
     	 </tr>
     	 <tr>
     	    <td width="80%"  class="AbbreviatedText" align="right" >Tip: Use Shift or Ctrl to select multiple fields.</td> 
     	 </tr>       
       
     	</table>
     </td>
  
 <table width="100%">
   <tr>
         <td nowrap>&nbsp;</td>
   </tr>  
  <tr align="left">
     <td class="OraHeaderSubSub" width="80%" align="left" nowrap>Search by Concept</td>
   
  </tr>  
  <tr>
    <td align="center" ><html:img page="/i/beigedot.gif" alt="beigedot"  border="0"  height="1" width="99%" align="top" /> </td>
   </tr> 
 </table> 
 
 
   <table  width="100%"  border="0">
     <tr>
          <td width="50%" >
            <table  width="100%" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">  
             <tr >
               <td width="40%" class="OraTableColumnHeaderNoBG" nowrap>Concept Name</td>
               <td width="60%" class="OraTabledata">
               <label for ="advanceSearch_input_5"/>
               <input id ="advanceSearch_input_5" type="text"  name="jspConceptName" value="<%=desb.getConceptName()%>"  size="30" ></td>
             </tr>               
            </table>           
           </td>
           <td valign="top" width="50%" >
            <table  width="100%" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
             <tr >
              <td width="40%" class="OraTableColumnHeaderNoBG" nowrap>Concept Code </td>
              <td  width="60%" class="OraTabledata" nowrap>
              <label for ="advanceSearch_input_6"/>
               <input id ="advanceSearch_input_6" type="text"  name="jspConceptCode" value="<%=desb.getConceptCode()%>"  size="20"> 
              </td>
             </tr>                
            </table>  
          </td>
      </tr>         
  </table> 
  
 
 <table valign="bottom" width="100%">
  <tr>
        <td nowrap>&nbsp;</td>
  </tr> 
 <tr valign="bottom" align="left">
    <td class="OraHeaderSubSub" width="80%" align="left" nowrap>Search by Attributes</td>
  
 </tr>  
 <tr valign="top" >
   <td align="center" ><html:img page="/i/beigedot.gif"  alt="beigedot" border="0"  height="1" width="99%" align="top" /> </td>
  </tr> 
 </table> 
 
 <table align="center" width="100%" >
   <tr>
        <td valign="top" width="50%" align="center">
          <table width="100%" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
            <tr >
                <td class="OraTableColumnHeaderNoBG" nowrap>Public ID</td>
                <td class="OraTabledata" nowrap>
                 <label for ="advanceSearch_input_7"/>
                   <input id ="advanceSearch_input_7" type="text" name="jspCdeId" value="<%=desb.getCdeId()%>" > 
                 </td>
            </tr>
            <tr >
               <td class="OraTableColumnHeaderNoBG" >Data Element Concept</td>
               <td class="OraTabledata" nowrap>
                	<label for ="advanceSearch_input_8"/>
                   <input id ="advanceSearch_input_8" type="text" name="txtDataElementConcept" 
                      value="<%=txtDataElementConcept%>" 
                      readonly onFocus="this.blur();"
                      class="LOVField"
                      size ="18"
                    >
                  &nbsp;<a href="<%=decLOVUrl%>"><html:img page="/i/search_light.gif"  alt="search light" border="0" alt="Search for Data Element Concepts" /></a>&nbsp;
                  <a href="javascript:clearDataElementConcept()"><i>Clear</i></a>
   
                  <input id ="advanceSearch_input_9" type="hidden" name="jspDataElementConcept" value="<%=desb.getDecIdseq()%>" >
                </td>
            </tr>
            <tr >
               <td class="OraTableColumnHeaderNoBG" nowrap>Classification</td>
               <td class="OraTabledata" nowrap>
               <label for ="advanceSearch_input_10"/>
                  <input id ="advanceSearch_input_10" type="text" name="txtClassSchemeItem" 
                    value="<%=txtClassSchemeItem%>" 
                    readonly onFocus="this.blur();"
                    class="LOVField"
                    size ="18"
                  >
                &nbsp;<a href="<%=csLOVUrl%>"><html:img page="/i/search_light.gif" alt="search light" border="0" alt="Search for Classification Scheme Items" /></a>&nbsp;
                <a href="javascript:clearClassSchemeItem()"><i>Clear</i></a>
 
                <input id ="advanceSearch_input_11" type="hidden" name="jspClassification" value="<%=desb.getCsCsiIdseq()%>" >
              </td>
            </tr>            
            <tr >
              <td class="OraTableColumnHeaderNoBG" nowrap>Value Domain</td>
              <td class="OraTabledata" nowrap>
              <label for ="advanceSearch_input_12"/>
                <input id ="advanceSearch_input_12" type="text" name="txtValueDomain" 
                    value="<%=txtValueDomain%>" readonly onFocus="this.blur();"
                    class="LOVField"
                    size ="18"
                 >
                 &nbsp;<a href="<%=valueDomainLOVUrl%>"><html:img page="/i/search_light.gif" border="0" alt="Search for Value Domains" /></a>&nbsp;
                <a href="javascript:clearValueDomain()"><i>Clear</i></a>
 
                  <input id ="advanceSearch_input_13" type="hidden" name="jspValueDomain" value="<%=desb.getVdIdseq()%>" >
              </td>
            </tr>
			<tr >
              <td class="OraTableColumnHeaderNoBG" nowrap>Value Domain Type</td>
              <td class="OraTabledata" nowrap>
              <label for ="advanceSearch_input_14"/>
                <input id ="advanceSearch_input_14" type="radio" name="jspVDType" value="<%=ProcessConstants.VD_TYPE_ENUMERATED%>"
	    	        <%if (desb.getVDType().equals(ProcessConstants.VD_TYPE_ENUMERATED))
	    	        { %> checked <%}%> >Enumerated
	    	        <label for ="advanceSearch_input_15"/>
	    	        <input id ="advanceSearch_input_15" type="radio" name="jspVDType" value="<%=ProcessConstants.VD_TYPE_NON_ENUMERATED%>"
	    	        <%if (desb.getVDType().equals(ProcessConstants.VD_TYPE_NON_ENUMERATED))
	    	        { %> checked <%}%> >Non Enumerated
					<label for ="advanceSearch_input_16"/>
					<input id ="advanceSearch_input_16" type="radio" name="jspVDType" value="<%=ProcessConstants.VD_TYPE_BOTH%>"
	    	        <%if (desb.getVDType().equals(ProcessConstants.VD_TYPE_BOTH))
	    	        { %> checked <%}%> >Both
              </td>
            </tr>
            <tr>
                <td class="OraTableColumnHeaderNoBG" nowrap>Permissible Value</td>
                <td class="OraTabledata" nowrap>
                  <label for ="advanceSearch_input_17"/>
                <input id ="advanceSearch_input_17" type="text" name="jspValidValue" value="<%=desb.getValidValue()%>" size ="20"> 
               </td>
            </tr>
            <tr>
            <td colspan="2" class="OraTableColumnHeaderNoBG" >
                      <table valign="top">
	    	    <tr>
	    	     <td valign="top" class="OraTableColumnHeaderWhiteBG" nowrap>
	    	     	<label for ="advanceSearch_input_18"/>
	    	        <input id ="advanceSearch_input_18" type="radio" name="jspPVSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_EXACT%>"
	    	        <%if (desb.getPvSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_EXACT))
	    	        { %> checked <%}%> >Exact phrase
	    	        <label for ="advanceSearch_input_19"/>
	    	        <input id ="advanceSearch_input_19" type="radio" name="jspPVSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_ALL%>" 
	    	        <%if (desb.getPvSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_ALL)) 
	    	        { %> checked <%}%>>All words
	    	        <label for ="advanceSearch_input_20"/>
	    	        <input id ="advanceSearch_input_20" type="radio" name="jspPVSearchMode" value="<%=ProcessConstants.DE_SEARCH_MODE_ANY%>" 
	    	        <%if (desb.getPvSearchMode().equals(ProcessConstants.DE_SEARCH_MODE_ANY)) { %> checked <%}%> >At least one word
	    	     </td >
	    	    </tr>
	    	  </table>    

            
            
            </td>
            </tr>
          </table>
         </td>
         <td valign="top" width="50%" align="center" >
               <table width="100%" cellpadding="0" cellspacing="0"  valign="top" >
                <tr>
                    <td width="100%" valign="top" >
                        <table  width="100%" valign="top"  border=0 cellpadding="0" cellspacing="1" class="OraBGAccentVeryDark">
                        <tr >
                         <td width="30%" class="OraTableColumnHeaderNoBG" nowrap>Alternate Name</td>
                         <td width="70%" class="OraTabledata" nowrap>
                         <label for ="advanceSearch_input_21"/>
                           <input id ="advanceSearch_input_21" type="text" name="jspAltName" value="<%=desb.getAltName()%>" size ="20"> 
                         </td>
                        </tr>    
                        <tr >
                          <td width="30%" class="OraTableColumnHeaderNoBG" >Alternate Name    Type(s)</td>
                          <td width="70%" class="OraTabledata"><%=desb.getAltNameList()%></td>
                        </tr>               
                        <tr >
                         <td width="30%" class="OraTableColumnHeaderNoBG" nowrap>Object Class</td>
                         <td width="70%" class="OraTabledata" nowrap>
                         <label for ="advanceSearch_input_22"/>
                           <input  id ="advanceSearch_input_22" type="text" name="jspObjectClass" value="<%=desb.getObjectClass()%>" size ="20"> 
                         </td>
                        </tr>    
                        <tr >
                         <td width="30%" class="OraTableColumnHeaderNoBG" nowrap>Property</td>
                         <td width="70%" class="OraTabledata" nowrap>
                         <label for ="advanceSearch_input_23"/>
                           <input  id ="advanceSearch_input_23" type="text" name="jspProperty" value="<%=desb.getProperty()%>" size ="20"> 
                         </td>
                        </tr>    
                        </table>
                    </td>
                </tr>

              </table>
         </td>
   </tr>
 </table>

         
                 
 <table width="100%">
   <tr>
         <td nowrap>&nbsp;</td>
  </tr>  
 <tr align="left">
    <td class="OraHeaderSubSub" width="80%" align="left" nowrap>Limit search results using filters</td>
  
 </tr>  
 <tr>
   <td align="center" ><html:img page="/i/beigedot.gif" alt="beigedot" border="0"  height="1" width="99%" align="top" /> </td>
  </tr> 
 </table> 
 
  <table  width="100%"  border="0">
    <tr>
         <td width="50%" >
              <table  width="100%" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark" >
                   <tr>
                         <td valign="top"  class="OraTableColumnHeaderNoBG" nowrap>Version</td>
                          <%
                          
                            if((paramType!=null)&&(paramType.equals("CRF")||paramType.equals("TEMPLATE")))
                              {
                                 if (latestVer.equals(""))
                                    latestVer = "No";
                              }
                              
                            if (latestVer.equals("Yes") || latestVer.equals("")) {
                          %>
                                    <td class="OraTableColumnHeaderNoBG" nowrap>
                                    	<label for ="advanceSearch_input_24"/>
                                      <input  id ="advanceSearch_input_24" type="radio" name="jspLatestVersion" value="Yes" checked> Latest Version
                                      <label for ="advanceSearch_input_25"/>
                                      <input  id ="advanceSearch_input_25" type="radio" name="jspLatestVersion" value="No"> All Versions
                                   </td>
                          <%
                            }
                            else {
                          %>
                                    <td class="OraTableColumnHeaderNoBG" nowrap>
                                     <label for ="advanceSearch_input_26"/>
                                      <input id ="advanceSearch_input_26" type="radio" name="jspLatestVersion" value="Yes" > Latest Version
                                      <label for ="advanceSearch_input_27"/>
                                      <input id ="advanceSearch_input_27" type="radio" name="jspLatestVersion" value="No" checked > All Versions
                                   </td>
                          <%
                            }
                          %>
                    </tr>          
               </table>
          </td>
          <td valign="top" width="50%" >
                <table width="100%" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
                <tr>
                  <td class="OraTableColumnHeaderNoBG" nowrap>Context Use</td>
                  <td class="OraTabledata" nowrap>
                    <%=desb.getContextUseList()%>
                  </td>          
                </tr>
                </table>
         </td>
     </tr>
    <tr>
         <td valign="top" width="50%" >
          <table width="100%" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark" >
            <tr>
             <td width="30%" class="OraTableColumnHeaderNoBG" nowrap>Workflow Status</td>
              <td width="70%" class="OraTabledata"><%=desb.getWorkflowList()%></td>
            </tr>          
          </table>
         </td>
          <td width="50%" >
          <table width="100%" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark" >
             <tr>
              <td width="30%" class="OraTableColumnHeaderNoBG" nowrap>Registration Status</td>
              <td width="70%" class="OraTabledata"><%=desb.getRegStatusList()%></td>
            </tr>
          </table>
         </td>
     </tr>
  </table>
 
 
<table width="80%" border="0" align="center"> 
<%
  if ("".equals(src)) {
%>
 <table with ="80%" align="center" border="0">
 <TR>
    <td align="center" nowrap><a href="javascript:submitForm()">
<% if (searchMode!=null && searchMode.equals(BrowserFormConstants.BROWSER_SEARCH_SCOPE_SEARCHRESULTS)) {
%>
   <html:img page="/i/search_within_result.gif" alt="search within results" border="0" />
 <% }else { %>       
     <html:img page="/i/search.gif" alt="search" border="0" />
 <% } %>
    </a></td>
    <td  align="center" nowrap><a href="javascript:clearForm()"><html:img page="/i/clear.gif" alt="clear" border="0" /></a></td>
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
    <td  nowrap  ><a href="javascript:submitForm()"><html:img page="/i/SearchDataElements.gif" alt="search data element" border="0" /></a>
    </td>
    <td><a href="javascript:clearForm()"><html:img page="/i/clear.gif" alt="clear" border="0" /></a>
    </td>
    <% if(deList!=null){    %>
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