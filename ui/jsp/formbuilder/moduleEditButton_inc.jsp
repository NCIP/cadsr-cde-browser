      <table width="25%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
          <td >
            <html:link action='<%="/formSearchAction"%>'>				
            <html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Cancel"/>
            </html:link>             
          </td>                
          <td >
            <html:link action='<%="/formEditDeleteAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.DELETE_FORM%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
                paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
              <html:img src='<%=urlPrefix+"i/deleteButton.gif"%>' border="0" alt="Delete"/>
	          </html:link>             
          </td>  
         <td>
            <a href="javascript:submitModuleToSave('<%=NavigationConstants.SAVE_MODULE%>')">
                <html:img src='<%=urlPrefix+"i/save.gif"%>' border="0" alt="Save"/>
             </a> 
          </td>          
        </tr> 
                
      </table>