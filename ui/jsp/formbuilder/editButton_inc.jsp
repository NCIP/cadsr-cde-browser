      <table width="25%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
         <td>
            <a href="javascript:submitFormToSave('<%=NavigationConstants.SAVE_FORM%>')">
                <html:img src='<%=urlPrefix+"i/save.gif"%>' border="0" alt="Save"/>
             </a> 
          </td>          
         <td>
           <html:link action='<%="/gotoManageClassifications.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_CLASSIFICATIONS%>'
             paramId = "<%=FormConstants.FORM_ID_SEQ%>"
             paramName="<%=FormConstants.CRF%>"
             paramProperty="formIdseq"
             >
             <html:img src='<%=urlPrefix+"i/classifications.gif"%>' border="0" alt="Manage Classifications"/>
           </html:link>
          </td>      
          <td > 
            <html:link action='<%="/cancelFormEditAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.CANCEL_FORM_EDIT%>'>
              <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
	          </html:link>               
          </td>                
          <td >
            <html:link action='<%="/formEditDeleteAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.DELETE_FORM%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
                paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
              <html:img src='<%=urlPrefix+"i/deleteButton.gif"%>' border="0" alt="Delete"/>
	          </html:link>             
          </td>            
        </tr> 
                
      </table>