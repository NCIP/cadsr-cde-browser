      <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
           <td width="40%" >
	            &nbsp;
          </td> 
          <td >
 	    <html:link action='<%="/formAction"%>' >				
		<html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Back"/>
	    </html:link>             
          </td>                
          <td >
 	    <html:link action='<%="/formToCopyAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_COPY%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
		<html:img src='<%=urlPrefix+"i/copyButton.gif"%>' border="0" alt="Copy"/>
	    </html:link>             
          </td> 
          <td >
 	    <html:link action='<%="/formToEditAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_EDIT%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
		<html:img src='<%=urlPrefix+"i/editButton.gif"%>' border="0" alt="Edit"/>
	    </html:link>             
          </td>           
          <td >
 	    <html:link action='<%="/formDeleteAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.DELETE_FORM%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
		<html:img src='<%=urlPrefix+"i/deleteButton.gif"%>' border="0" alt="Delete"/>
	    </html:link>
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
        <tr >
          <td >
            &nbsp;
          </td>                          
        </tr>         
        
      </table>