
      <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
           <td width="40%" >
	            &nbsp;
          </td> 
          <td >
            <html:link action='<%="/formSearchAction"%>' >				
            <html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Back"/>
            </html:link>             
          </td>                
          <td >
		       <cde:secureIcon  formId="<%=FormConstants.CRF%>"
            formScope="<%=CaDSRConstants.SESSION_SCOPE%>"
            activeImageSource="i/selectForCopyButton.gif" 
		       	activeUrl='<%="/formToCopyAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_COPY%>' 
		   	   	formType="TEMPLATE"  
            role="<%=CaDSRConstants.CDE_MANAGER%>" 
		   	   	urlPrefix="<%=urlPrefix%>"
		   	   	paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		   	   	paramProperty="formIdseq"
		   	   	altMessage="Select for Copy" 
				/>   
          </td> 
          <td >
		       <cde:secureIcon  formId="<%=FormConstants.CRF%>" 
            formScope="<%=CaDSRConstants.SESSION_SCOPE%>" 
            activeImageSource="i/editButton.gif" 
		       		activeUrl='<%="/formToEditAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_EDIT%>' 
		   	   	role="<%=CaDSRConstants.CDE_MANAGER%>" 
		   	   	urlPrefix="<%=urlPrefix%>"
		   	   	paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		   	   	paramProperty="formIdseq"
		   	   	altMessage="Edit"
		   	   	target="_parent"            
            />		             
          </td>           
          <td >
		       <cde:secureIcon  formId="<%=FormConstants.CRF%>" 
           formScope="<%=CaDSRConstants.SESSION_SCOPE%>" 
           activeImageSource="i/deleteButton.gif" 
		       		activeUrl='<%="/formHrefDeleteAction.do?"
                         +NavigationConstants.METHOD_PARAM+"="+NavigationConstants.DELETE_FORM%>'
		   	   	role="<%=CaDSRConstants.CDE_MANAGER%>" 
		   	   	urlPrefix="<%=urlPrefix%>"
		   	   	paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		   	   	paramProperty="formIdseq"
		   	   	altMessage="Delete"            
		   	   	/>		                
          </td>  
          <td width="40%" align="right">
 	    <html:link action='<%="/formPrinterAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_PRINT%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq"
 				target="_blank">
		Printer Friendly Version
	    </html:link>
	   </td>	             
          </td>           
        </tr>                 
      </table>