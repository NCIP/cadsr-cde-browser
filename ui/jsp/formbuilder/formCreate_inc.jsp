
<SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
  document.forms[0].submit();
}

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

        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.longName" />:</td>
        <td class="OraFieldText" nowrap>
          <html:text property="<%=FormConstants.FORM_LONG_NAME%>" size="20" />
        </td>
        
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.workflow" />:</td>
        <td><B>DRAFT NEW</B></td>      
        
    </tr>

    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.type" />:</td>  
        <td class="OraFieldText" nowrap>
        <html:select styleClass = "Dropdown" property="<%=FormConstants.FORM_TYPE%>">
        	<html:option key="cadsr.formbuilder.form.blank" value="<%=FormConstants.SEARCH_ALL%>" /> 
		      <html:options name="<%=FormConstants.ALL_FORM_TYPES%>" /> 
	      </html:select> 
        </td>        
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.protocol"/>:</td>
        <td class="OraFieldText" nowrap>
          <html:text property="<%=FormConstants.PROTOCOLS_LOV_NAME_FIELD%>" 
                 readonly="true" 
                 size="19"
                 styleClass="LOVField"
                 onfocus="this.blur();"/>
          &nbsp;<a href="<%=protoLOVUrl%>"><img src="<%=urlPrefix%>i/search_light.gif" border="0" alt="Search for Protocols"></a>&nbsp;
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
		           property="<%=FormConstants.CRF_CONTEXT_ID_SEQ%>" labelProperty="name" />
	        </html:select>
        </td>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.category" />:</td>
        <td class="OraFieldText" nowrap>
        <html:select styleClass = "Dropdown" property="<%=FormConstants.FORM_CATEGORY%>">
        	<html:option key="cadsr.formbuilder.form.blank" value="<%=FormConstants.SEARCH_ALL%>" /> 
		      <html:options name="<%=FormConstants.ALL_FORM_CATEGORIES%>" /> 
	      </html:select> 
        </td>
    </tr>
    
    <tr>
    </tr>   

    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.definition" />:</td>
        <td class="OraFieldText" nowrap>
          <html:text property="<%=FormConstants.PREFERRED_DEFINITION%>" size="35" />
        </td>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.comments" />:</td>
        <td class="OraFieldText" nowrap>
          <html:text property="<%=FormConstants.FORM_COMMENTS%>" size="35" />
        </td>
    </tr>
  </table>

  <br/>
  <hr/>
  <br/>
  
  <table cellspacing="2" cellpadding="3" border="0" width="100%">

    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.header" />:</td>
        <td class="OraFieldText" nowrap>
          <html:text property="<%=FormConstants.FORM_HEADER%>" size="80" />
        </td>
    </tr>

    <tr>
        <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.footer" />:</td>
        <td class="OraFieldText" nowrap>
          <html:text property="<%=FormConstants.FORM_FOOTER%>" size="80" />
        </td>
    </tr>
    <tr>    
      <td>
        <html:hidden value="<%=NavigationConstants.GET_ALL_FORMS_METHOD%>" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      </td>
    </tr>
  </table>

  <br/>
  <br/>
  <table cellspacing="2" cellpadding="3" border="0" width="100%">

    <TR>
      <td colspan="2" align="right" nowrap><a href="javascript:submitForm()"><img src=<%=urlPrefix%>i/save.gif border=0></a></td>
      <td >
            <html:link action='<%="/cancelFormCreateAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.CANCEL_FORM_CREATE%>'>
              <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
	          </html:link>            
      </td>                
    </TR>    
  </table>


