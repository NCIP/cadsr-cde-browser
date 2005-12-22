
<SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
  if((document.forms[0].<%=FormConstants.FORM_TYPE%>.value == "TEMPLATE") && (document.forms[0].<%=FormConstants.PROTOCOLS_LOV_ID_FIELD%>.value != '')) {
     alert('Protocol must be left blank for a type TEMPLATE');
     return;
  }
  if(validateCreateForm(createForm))
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

  String protoLOVUrl= 
    "javascript:newWin('"+contextPath+"/formLOVAction.do?method=getProtocolsLOV&chkContext=true&idVar=protocolIdSeq&nameVar=protocolLongName"+pageUrl+"','protoLOV',700,600)";

%>
<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.form.create"/>
      </td>
    </tr>  
</table> 

  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" >
    <tr >
      <td >
        &nbsp;
      </td>
    </tr>         
    <TR>
      <td align="right" nowrap width="50%"><a href="javascript:submitForm()"><img src="<%=urlPrefix%>i/save.gif" border=0 alt="Save"></a></td>
      <td nowrap width="50%"><html:link action='<%="/formSearchAction"%>'><html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/></html:link></td>                
    </TR>    
  </table>


  <table cellpadding="0" cellspacing="0" width="80%" align="center">
    <tr >
      <td >
        &nbsp;
      </td>
    </tr>         
    <tr>
      <td class="OraHeaderSubSub" width="100%">Form Details</td>
    </tr>
    <tr>
      <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
    </tr>
  </table>

<%@ include file="showMessages.jsp" %>

  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
    <tr class="OraTabledata">
      
      <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.longName" /></td>
      <td class="OraFieldText" nowrap>
        <html:text 
          size="100"
          property="<%= FormConstants.FORM_LONG_NAME %>"
          maxlength="<%= (new Integer(FormConstants.LONG_NAME_MAX_LENGTH)).toString() %>"
          />
      </td>
    </tr>

    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.definition" /></td>
      <td class="OraFieldText" nowrap>
        <html:textarea 
          property="<%= FormConstants.PREFERRED_DEFINITION %>"
          cols="100"
          rows="3"
          styleClass="OraFieldText"
          />
      </td>
    </tr>
        
    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.context" /></td>
      <td class="OraFieldText" nowrap>
        <html:select styleClass = "Dropdown" property="<%=FormConstants.CONTEXT_ID_SEQ%>">
          <html:options collection="<%=CaDSRConstants.USER_CONTEXTS%>" 
            property="<%=FormConstants.CRF_CONTEXT_ID_SEQ%>" labelProperty="name" />
        </html:select>
      </td>
    </tr>

    <tr class="OraTabledata">
        <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.protocols.longName"/></td>
        <td class="OraFieldText" nowrap>
          <html:text property="<%=FormConstants.PROTOCOLS_LOV_NAME_FIELD%>" 
                 readonly="true" 
                 size="19"
                 styleClass="LOVField"
                 />
          &nbsp;<a href="<%=protoLOVUrl%>"><img src="<%=urlPrefix%>i/search_light.gif" border="0" alt="Search for Protocols"></a>&nbsp;
          <a href="javascript:clearProtocol()"><i>Clear</i></a>
          <html:hidden  property="<%=FormConstants.PROTOCOL_ID_SEQ%>"/>
        </td>
    </tr>


    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.workflow" /></td>
      <td class="OraFieldText" nowrap>
        <!--
        <input type=text 
          name="<%= FormConstants.WORKFLOW %>"
          readonly="true" 
          size="19"
          styleClass="LOVField"
          value="DRAFT NEW"
          />
          -->
          DRAFT NEW
      </td>      
    </tr>


    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.category" /></td>
      <td class="OraFieldText" nowrap>
        <html:select styleClass = "Dropdown" property="<%=FormConstants.FORM_CATEGORY%>">
          <html:option value=""/>
          <html:options name="<%=FormConstants.ALL_FORM_CATEGORIES%>" /> 
        </html:select> 
      </td>
    </tr>

    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.type" /></td>  
      <td class="OraFieldText" nowrap>
        <html:select styleClass = "Dropdown" property="<%=FormConstants.FORM_TYPE%>">
          <html:options name="<%=FormConstants.ALL_FORM_TYPES%>" /> 
        </html:select> 
      </td>        
    </tr>

    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.version" /></td>
      <td class="OraFieldText" nowrap>
        <html:hidden property="<%= FormConstants.FORM_VERSION %>" value="1.0" />
        1.0
      </td>
    </tr>

    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" width="20%" nowrap>
        <bean:message key="cadsr.formbuilder.form.header.instruction"/>
      </td>        
      <td  class="OraFieldText" width="80%" >
        <html:textarea  styleClass="OraFieldTextInstruction" rows="2" cols="102" 
           property="<%=FormConstants.FORM_HEADER_INSTRUCTION%>">
        </html:textarea>
      </td>            
    </tr>   
    
    <tr class="OraTabledata">
      <td class="OraTableColumnHeader" width="20%" nowrap>
        <bean:message key="cadsr.formbuilder.form.footer.instruction"/>
      </td>
      <td  class="OraFieldText" width="80%" >
        <html:textarea  styleClass="OraFieldTextInstruction" rows="2" cols="102" 
           property="<%=FormConstants.FORM_FOOTER_INSTRUCTION%>">
        </html:textarea>
      </td>     
    </tr>  
  
 </table>



  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" >
    <tr >
      <td >
        &nbsp;
      </td>
    </tr>         
    <TR>
      <td align="right" nowrap width="50%"><a href="javascript:submitForm()"><img src="<%=urlPrefix%>i/save.gif" border=0 alt="Save"></a></td>
      <td nowrap width="50%"><html:link action='<%="/formSearchAction"%>'><html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/></html:link></td>                
    </TR>    
  </table>

<html:javascript formName="createForm"/>

