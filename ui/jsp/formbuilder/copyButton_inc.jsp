      <table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
          <td >
            <html:image src='<%=urlPrefix+"i/copyButton.gif"%>' border="0" alt="Copy"/>
            <%--
 	    <html:link action='<%="/formToCopyAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.FORM_COPY%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
		<html:img src='<%=urlPrefix+"i/copyButton.gif"%>' border="0" alt="Copy"/>
	    </html:link>             
            --%>
          </td>           
          <td >
            <a href="javascript:clearForm()">
              <html:img src='<%=urlPrefix+"i/clearAllButton.gif"%>' border="0" alt="Clear All"/>
            </a>
          </td>                
          <td >
 	    <html:link action='<%="/formAction"%>'>				
		<html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Edit"/>
	    </html:link>             
          </td>                
        </tr> 
        <tr >
          <td >
            &nbsp;
          </td>                          
        </tr>         
        
      </table>