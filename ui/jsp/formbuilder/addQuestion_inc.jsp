<table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr >
    <td >
      <a href="javascript:submitForm('<%=NavigationConstants.ADD_QUESTION%>')">
        <html:img src='<%=urlPrefix+"i/add_button.gif"%>' border="0" alt="Add"/>
      </a>
    </td>     
    <td >
      <a href="javascript:submitForm('<%=NavigationConstants.SUBSET_QUESTION_VALIDVALUES%>')">
        <html:img src='<%=urlPrefix+"i/subset_validvalues.gif"%>' border="0" alt="Subset ValidValues and Add"/>
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