
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


          
<table width="90%" align="center">
 
 <tr align="left">
    <td class="OraHeaderSubSub" width="60%" align="left" nowrap>Search for Modules</td>
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
 
  
 
   
  <table valign="top" width="90%" align="center">
    <tr>
      <td width="50%"  valign="top" >
          <table width="100%" cellpadding="0" cellspacing="1" class="OraBGAccentVeryDark" border="0" %>

            <tr>            
                <td width="30%" class="OraTableColumnHeaderNoBG" nowrap>Module name</td>
                <td width="70%"  class="OraTabledata" nowrap>
                  <html:text property="<%=FormConstants.PROTOCOLS_LOV_NAME_FIELD%>" 
                         readonly="true" 
                         size="60"      
                         />
                  
                </td>
             </tr>
            <tr>            
                <td width="30%" class="OraTableColumnHeaderNoBG" nowrap>Form name</td>
                <td width="70%"  class="OraTabledata" nowrap>
                  <html:text property="<%=FormConstants.PROTOCOLS_LOV_NAME_FIELD%>" 
                         readonly="true" 
                         size="60"      
                         />
                  
                </td>
             </tr>
            <tr>            
                <td width="30%" class="OraTableColumnHeaderNoBG" nowrap>Form Public Id</td>
                <td width="70%"  class="OraTabledata" nowrap>
                  <html:text property="<%=FormConstants.PROTOCOLS_LOV_NAME_FIELD%>" 
                         readonly="true" 
                         size="19"      
                         />
                  
                </td>
             </tr>             
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
 
           
           </table>      
        </td>            
        
      <td width="50%" valign="top" >
          <table width="100%" cellpadding="0" cellspacing="1" class="OraBGAccentVeryDark" border="0" %>
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
                <td width="30%" class="OraTableColumnHeaderNoBG" nowrap><bean:message key="cadsr.formbuilder.form.type" /></td>  
                <td width="70%" class="OraTabledata" nowrap>
                <html:select styleClass = "Dropdown" property="<%=FormConstants.FORM_TYPE%>"
                onkeypress="if(window.event.keyCode==13){submitForm('getAllForms');};">
                  <html:option key="cadsr.formbuilder.form.blank" value="<%=FormConstants.SEARCH_ALL%>" /> 
                  <html:options name="<%=FormConstants.ALL_FORM_TYPES%>" /> 
                   </html:select> 
                </td>  
             </tr>   
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
           </table>
        </td>                      
    </tr>    
 </table>
 <table width="90%" align="center">
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
          </td>          
           </tr>
      </table>       
    </td>
 </TR>
 <table>

