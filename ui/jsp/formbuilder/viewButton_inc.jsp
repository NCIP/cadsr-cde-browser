      <table width="30%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
          <td >
 	    <html:link action='<%="/formAction"%>' >				
		<html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Back"/>
	    </html:link>             
          </td> 
          <td >
 	    <html:link action='<%="/formEditAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_EDIT%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
		<html:img src='<%=urlPrefix+"i/editButton.gif"%>' border="0" alt="Edit"/>
	    </html:link>             
          </td>                
          <td >
 	    <html:link action='<%="/formCopyAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_COPY%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
		<html:img src='<%=urlPrefix+"i/copyButton.gif"%>' border="0" alt="Copy"/>
	    </html:link>             
          </td> 
          <td >
 	    <html:link action='<%="/formDeleteAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.DELETE_FORM%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
		<html:img src='<%=urlPrefix+"i/deleteButton.gif"%>' border="0" alt="Delete"/>
	    </html:link>             
          </td>           
        </tr> 
        <tr >
          <td >
            &nbsp;
          </td>                          
        </tr>         
        
      </table>