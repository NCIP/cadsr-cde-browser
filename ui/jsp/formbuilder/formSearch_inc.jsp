
<SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
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

-->
</SCRIPT>
<%
  String pageUrl = "&PageId=DataElementsGroup";
  // HSK
  String contextPath = request.getContextPath();
    String csLOVUrl= "javascript:newWin('"+contextPath+"/search?classificationsLOV=9&idVar=jspClassification&nameVar=txtClassSchemeItem"+pageUrl+"','csLOV',700,600)";
  String protoLOVUrl= 
    "javascript:newWin('"+contextPath+"/formLOVAction.do?method=getProtocolsLOV&idVar=protocolIdSeq&nameVar=protocolLongName"+pageUrl+"','protoLOV',700,600)";

%>
  <table cellspacing="2" cellpadding="3" border="0" width="100%">
    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.name" />:</td>
        <td class="OraFieldText" nowrap>
          <html:text property="<%=FormConstants.FORM_LONG_NAME%>" size="20" />
        </td>

        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.protocol"/>:</td>
        <td class="OraFieldText" nowrap>
          <html:text property="<%=FormConstants.PROTOCOLS_LOV_NAME_FIELD%>" 
                 readonly="true" 
                 size="19"
                 styleClass="LOVField"
                 onfocus="this.blur();"/>
          &nbsp;<a href="<%=protoLOVUrl%>"><img src="<%=urlPrefix%>i/search_light.gif" border="0" alt="Search for Protocol Items"></a>&nbsp;
          <a href="javascript:clearProtocol()"><i>Clear</i></a>
          <html:hidden  property="<%=FormConstants.PROTOCOL_ID_SEQ%>"/>
        </td>


    </tr>

    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.context" />:</td>
        <td class="OraFieldText" nowrap>
          <html:select styleClass = "Dropdown" property="<%=FormConstants.CONTEXT_ID_SEQ%>">
        	   <html:option key="cadsr.formbuilder.form.blank" value="<%=FormConstants.SEARCH_ALL%>" /> 
		         <html:options collection="<%=FormConstants.ALL_CONTEXTS%>" 
		           property="conteIdseq" labelProperty="name" />
	         </html:select>
        </td>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.workflow" />:</td>
        <td class="OraFieldText" nowrap>
        <html:select styleClass = "Dropdown" property="<%=FormConstants.WORKFLOW%>">
        	<html:option key="cadsr.formbuilder.form.blank" value="<%=FormConstants.SEARCH_ALL%>" /> 
		<html:options name="<%=FormConstants.ALL_WORKFLOWS%>"/>
	</html:select>        
        </td>      
    </tr>
    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.category" />:</td>
        <td class="OraFieldText" nowrap>
        <html:select styleClass = "Dropdown" property="<%=FormConstants.CATEGORY_NAME%>">
        	<html:option key="cadsr.formbuilder.form.blank" value="<%=FormConstants.SEARCH_ALL%>" /> 
		<html:options name="<%=FormConstants.ALL_FORM_CATEGORIES%>" /> 
	</html:select> 
        </td>


    <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.classification"/>:</td>
    <td class="OraFieldText" nowrap>
      <html:text property="<%=FormConstants.CSI_NAME%>" 
      	     readonly="true" 
      	     size="19"
      	     styleClass="LOVField"
      	     onfocus="this.blur();"/>
      &nbsp;<a href="<%=csLOVUrl%>"><img src="<%=urlPrefix%>i/search_light.gif" border="0" alt="Search for Classification Scheme Items"></a>&nbsp;
      <a href="javascript:clearClassSchemeItem()"><i>Clear</i></a>
      <html:hidden  property="<%=FormConstants.CS_CSI_ID%>"/>
    </td>
       
    </tr>    
    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.type" />:</td>  
        <td class="OraFieldText" nowrap>
        <html:select styleClass = "Dropdown" property="<%=FormConstants.FORM_TYPE%>">
        	<html:option key="cadsr.formbuilder.form.blank" value="<%=FormConstants.SEARCH_ALL%>" /> 
		<html:options name="<%=FormConstants.ALL_FORM_TYPES%>" /> 
	</html:select> 
        </td>        
    </tr>     
    <tr>    
      <td>
        <html:hidden value="<%=NavigationConstants.GET_ALL_FORMS_METHOD%>" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      </td>
    </tr>
  <tr>
    <td colspan="4" nowrap align="left" class="AbbreviatedText">Wildcard character for search is *</td>
 </tr>
 <TR>
    <td colspan="2" align="right" nowrap><a href="javascript:submitForm()"><img src=<%=urlPrefix%>i/searchButton.gif border=0></a></td>
    <td colspan="2" align="left" nowrap><a href="javascript:clearForm()"><img src=<%=urlPrefix%>i/clearAllButton.gif border=0></a></td>
 </TR>
 <TR>
    <td colspan="3" align="right" nowrap>
      <html:link action='<%="/gotoFormCreate?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_CREATE_FORM%>' target="_parent" >
        <html:img src='<%=urlPrefix+"i/create_new_form_template.gif"%>' border="0" alt="Create New Form"/>
      </html:link>&nbsp;
    </td>  
 </TR>    
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
  </table>
  <P>
    
  </P>