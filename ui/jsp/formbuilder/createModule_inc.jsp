      <table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
          <td >
            <a href="javascript:submitForm()"><html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Save"/></a>
          </td>           
          <td >
            <html:link action='<%= "/cancelAction?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.GET_FORM_TO_EDIT %>'>
              <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
            </html:link>             
          </td>                
        </tr>             
      </table>