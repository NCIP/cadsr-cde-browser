      <table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
          <td >
 	    <html:link action='<%="/formAction"%>'>				
		<html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Edit"/>
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