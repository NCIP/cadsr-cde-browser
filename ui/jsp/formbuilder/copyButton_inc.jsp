      <table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
          <td >
            <a href="javascript:submitForm()">
              <img src='<%=urlPrefix+"i/copyButton.gif"%>' border="0" alt="Copy"/>
            </a>
          </td>           
          <td >
            <a href="javascript:clearForm()">
              <html:img src='<%=urlPrefix+"i/clearAllButton.gif"%>' border="0" alt="Clear All"/>
            </a>
          </td>                
          <td >
 	    <html:link action='<%="/formSearchAction"%>'>				
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