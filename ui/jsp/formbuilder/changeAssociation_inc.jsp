<table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr >
    <td >
      <a href="javascript:submitForm()">
        <html:img src='<%=urlPrefix+"i/update_button.gif"%>' border="0" alt="Update"/>
      </a>
    </td>           
    <td >
      <html:link action='<%= "/cancelAction?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.GET_MODULE_TO_EDIT %>'>
        <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
      </html:link>             
    </td>                
  </tr> 
  <tr >
    <td >
      &nbsp;
    </td>                          
  </tr>         
  
</table>