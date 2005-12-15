
<SCRIPT LANGUAGE="JavaScript">
<!--
function submitForm(methodName) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();
  }

/* HSK */
function clearClassSchemeItem() {
  document.forms[0].jspClassification.value = "";
  document.forms[0].txtClassSchemeItem.value = "";
}

function clearProtocol() {
  document.forms[0].protocolIdSeq.value = "";
  document.forms[0].protocolLongName.value = "";
}
function clearForm() {
  clearProtocol();
  clearClassSchemeItem();
  document.forms[0].reset();
}
-->
</SCRIPT>
<%
  String pageUrl = "&PageId=DataElementsGroup";
  // HSK
  String contextPath = request.getContextPath();

  String csLOVUrl= contextPath + 
       "/search?classificationsLOV=9&idVar=jspClassification&nameVar=txtClassSchemeItem" + pageUrl;

  String protoLOVUrl = contextPath +
       "/formLOVAction.do?method=getProtocolsLOV&idVar=protocolIdSeq&nameVar=protocolLongName"+pageUrl;

%>


<SCRIPT>
<!--
function gotoProtocolsLOV() {
     var dest = '<%= protoLOVUrl %>' + '&contextIdSeq=' + document.forms[0].<%= FormConstants.CONTEXT_ID_SEQ %>.value;
;
     newWin(dest, 'ProtocolsLOV', 700, 600);
}
function gotoClassificationsLOV() {
     var dest = '<%= csLOVUrl %>' + '&P_CONTE_IDSEQ=' + document.forms[0].<%= FormConstants.CONTEXT_ID_SEQ %>.value;
     newWin(dest, 'ClassificationLOV', 700, 600);
}
-->
</SCRIPT>

 <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>


          
<table width="100%" >
 
 <tr align="left">
    <td class="OraHeaderSubSub" width="60%" align="left" nowrap>Search for Forms</td>
     <td align="right" class="MessageText"  width="20%" nowrap><b>          
    <logic:present name="<%=FormConstants.FORM_SEARCH_RESULTS%>"> 
     <bean:size id="listSize" name="<%=FormConstants.FORM_SEARCH_RESULTS%>" />
     <logic:notEmpty name="<%=FormConstants.FORM_SEARCH_RESULTS%>">
         <a class="link" href="#results"><%=listSize%>  Matches</a>
     </logic:notEmpty>
     
     <logic:empty name="<%=FormConstants.FORM_SEARCH_RESULTS%>">
      No Matches 
     </logic:empty>
    </logic:present>
    
    <logic:notPresent name="<%=FormConstants.FORM_SEARCH_RESULTS%>"> 
      &nbsp;
    </logic:notPresent>
   </b></td>
          
 </tr>   
 <tr>
   <td  align="center" colspan="3"><html:img page="/i/beigedot.gif" border="0"  height="1" width="99%" align="top" /> </td>
  </tr> 
 </table>
 
  
 
  <table align="center" valign="top" width="100%" cellpadding="0" cellspacing="1" class="OraBGAccentVeryDark" border="0">
  
            <tr>
              <td width="20%" class="OraTableColumnHeaderNoBG" nowrap><bean:message key="cadsr.formbuilder.form.name" /></td>
              <td class="OraTabledata" nowrap>
                <html:text property="<%=FormConstants.FORM_LONG_NAME%>" size="60"
                onkeypress="if(event.keyCode==13){submitForm('getAllForms');};"/>
              </td>
            </tr>
            <tr>
              <td colspan=2 width="100%" class="OraTableColumnHeaderNoBG" nowrap>
                <table width="100%" border=0>
                    <tr>
                        <td width="20%" class="OraTableColumnHeaderNoBG" nowrap>Public ID</td>
                        <td class="OraTabledata" nowrap>
                        <html:text property="<%=FormConstants.PUBLIC_ID%>" size="20"
                            onkeypress="if(event.keyCode==13){submitForm('getAllForms');};"/>
                         </td>
                         <td width="50%" class="OraTableColumnHeaderNoBG" nowrap>
                            <table  width="100%" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark" >
                              <tr>
                                 <td valign="top"  class="OraTableColumnHeaderNoBG" nowrap>Version</td>
                                    <td class="OraTableColumnHeaderNoBG" nowrap>
                                      <input type="radio" name="<%=FormConstants.LATEST_VERSION_INDICATOR%>" value="<%=FormConstants.LATEST_VERSION%>" checked> Latest Version
                                      <input type="radio" name="<%=FormConstants.LATEST_VERSION_INDICATOR%>" value="<%=FormConstants.ALL_VERSION%>"> All Versions
                                  </td>
                               </tr>          
                             </table>
                         </td>                         
                    </tr> 
                </table>
              </td>          
            </tr>
   </table>
   
  <table valign="top" width="100%" >
    <tr>
      <td width="50%"  valign="top" >
          <table width="100%" cellpadding="0" cellspacing="1" class="OraBGAccentVeryDark" border="0" %>           
            <tr>            
                <td width="30%" class="OraTableColumnHeaderNoBG" nowrap><bean:message key="cadsr.formbuilder.form.protocol"/></td>
                <td class="OraTabledata" nowrap>
                  <html:text property="<%=FormConstants.PROTOCOLS_LOV_NAME_FIELD%>" 
                         readonly="true" 
                         size="19"
                         styleClass="LOVField"
                         onkeypress="if(window.event.keyCode==13){submitForm('getAllForms');};"/>
                  &nbsp;<a href="javascript:gotoProtocolsLOV()"><img src="<%=urlPrefix%>i/search_light.gif" border="0" alt="Search for Protocol Items"></a>&nbsp;
                  <a href="javascript:clearProtocol()"><i>Clear</i></a>
                  <html:hidden  property="<%=FormConstants.PROTOCOL_ID_SEQ%>"/>
                </td>
             </tr>
            <tr>            
              <td width="30%" class="OraTableColumnHeaderNoBG" nowrap><bean:message key="cadsr.formbuilder.form.classification"/></td>
              <td width="70%" class="OraTabledata" nowrap>
                <html:text property="<%=FormConstants.CSI_NAME%>" 
                       readonly="true" 
                       size="19"
                       styleClass="LOVField"
                       onkeypress="if(window.event.keyCode==13){submitForm('getAllForms');};"
                       />
                &nbsp;<a href="javascript:gotoClassificationsLOV()"><img src="<%=urlPrefix%>i/search_light.gif" border="0" alt="Search for Classification Scheme Items"></a>&nbsp;
                <a href="javascript:clearClassSchemeItem()"><i>Clear</i></a>
                <html:hidden  property="<%=FormConstants.CS_CSI_ID%>"/>
                </td>
             </tr>  
            <tr>            
              <td width="21%" class="OraTableColumnHeaderNoBG" nowrap>Module</td>
              <td width="70%" class="OraTabledata" nowrap>
                <html:text property="<%=FormConstants.MODULE_LONG_NAME%>" size="38"
                onkeypress="if(event.keyCode==13){submitForm('getAllForms');};"/>       
              </td>  
             </tr>       
            <tr>            
              <td width="21%" class="OraTableColumnHeaderNoBG" nowrap>CDE Public ID</td>
              <td width="70%" class="OraTabledata" nowrap>
                <html:text property="<%=FormConstants.CDE_PUBLIC_ID%>" size="21"
                onkeypress="if(event.keyCode==13){submitForm('getAllForms');};"/>       
              </td>  
             </tr>               
           </table>      
        </td>            
        
      <td width="50%" valign="top" >
          <table width="100%" cellpadding="0" cellspacing="1" class="OraBGAccentVeryDark" border="0" %>
            <tr>            
              <td width="21%" class="OraTableColumnHeaderNoBG" nowrap><bean:message key="cadsr.formbuilder.form.workflow" /></td>
              <td width="70%" class="OraTabledata" nowrap>
              <html:select styleClass = "FreeDropdown" property="<%=FormConstants.WORKFLOW%>"
              onkeypress="if(window.event.keyCode==13){submitForm('getAllForms');};">
                 <html:option key="cadsr.formbuilder.form.blank" value="<%=FormConstants.SEARCH_ALL%>" /> 
                 <html:options name="<%=FormConstants.ALL_WORKFLOWS%>"/>
                 </html:select>        
              </td>  
             </tr>              
            <tr>    
              <td width="30%" class="OraTableColumnHeaderNoBG" nowrap><bean:message key="cadsr.formbuilder.form.context" /></td>
              <td width="70%" class="OraTabledata" nowrap>
                <html:select styleClass = "Dropdown" property="<%=FormConstants.CONTEXT_ID_SEQ%>"
                onkeypress="if(window.event.keyCode==13){submitForm('getAllForms');};">
                   <html:option key="cadsr.formbuilder.form.blank" value="<%=FormConstants.SEARCH_ALL%>" /> 
                   <html:options collection="<%=FormConstants.ALL_CONTEXTS%>" 
                     property="conteIdseq" labelProperty="name" />
                 </html:select>
               </td>
             </tr>
            <tr>    
              <td width="30%" class="OraTableColumnHeaderNoBG" nowrap><bean:message key="cadsr.formbuilder.form.category" /></td>
              <td width="70%" class="OraTabledata" nowrap>
              <html:select styleClass = "Dropdown" property="<%=FormConstants.CATEGORY_NAME%>"
              onkeypress="if(window.event.keyCode==13){submitForm('getAllForms');};">
                <html:option key="cadsr.formbuilder.form.blank" value="<%=FormConstants.SEARCH_ALL%>" /> 
                <html:options name="<%=FormConstants.ALL_FORM_CATEGORIES%>" /> 
                </html:select> 
              </td>
             </tr>  
            <tr>    
                <td width="30%" class="OraTableColumnHeaderNoBG" nowrap><bean:message key="cadsr.formbuilder.form.type" /></td>  
                <td width="70%" class="OraTabledata" nowrap>
                <html:select styleClass = "Dropdown" property="<%=FormConstants.FORM_TYPE%>"
                onkeypress="if(window.event.keyCode==13){submitForm('getAllForms');};">
                  <html:option key="cadsr.formbuilder.form.blank" value="<%=FormConstants.SEARCH_ALL%>" /> 
                  <html:options name="<%=FormConstants.ALL_FORM_TYPES%>" /> 
                   </html:select> 
                </td>  
             </tr>             
           </table>
        </td>                      
    </tr>    
 </table>
 <table align="center">
  <TR>
    <td   align="center" nowrap >
      <table align="center" >
           <tr>
        <td nowrap>
            <a href="javascript:submitForm('<%=NavigationConstants.GET_ALL_FORMS_METHOD%>')"><img src=<%=urlPrefix%>i/searchButton.gif border=0></a>
          </td>       
             <td  >
               <a href="javascript:clearForm()"><img src=<%=urlPrefix%>i/reset.gif border=0></a>
              </td>
              <logic:present name="<%=FormConstants.FORM_SEARCH_RESULTS%>">
                <td   nowrap>
                <html:link action='<%="/newFormSearchAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.NEW_SEARCH_METHOD%>' target="_parent">
                <html:img src='<%=urlPrefix+"i/newSearchButton.gif"%>' border="0" alt="New Search"/>
               </html:link>
              </logic:present>
          </td>          
           </tr>
      </table>       
    </td>
 </TR>
 <table>
 

 <logic:present name="<%=FormConstants.MODULE_DISPLAY_ORDER_TO_COPY%>"> 
    <table align="center">
     <TR>
        <td  align="center" nowrap>
      <html:link action='<%="/formbuilder/moduleSearch?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.CANCEL_MODULE_FORM_SEARCH%>' 
       target="_parent" >                     
                    <html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Go back"/>         
      </html:link>&nbsp;
        </td>  
     </TR>    
    </table>
</logic:present>        
 <logic:notPresent name="<%=FormConstants.IN_PROCESS%>"> 
    <table align="center">
     <TR>
        <td  align="center" nowrap>
          <html:link action='<%="/gotoFormCreate?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_CREATE_FORM%>' target="_parent" >
            <html:img src='<%=urlPrefix+"i/create_new_form_template.gif"%>' border="0" alt="Create New Form"/>
          </html:link>&nbsp;
        </td>  
     </TR>    
    </table>
</logic:notPresent>        




