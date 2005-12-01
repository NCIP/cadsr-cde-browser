

      <table width="30%" align="center" cellpadding="0" cellspacing="0" border="0" >
        <tr >
         <logic:notEmpty name="<%=FormConstants.MODULE_LIST%>" >
          <td align="center">

             <a href="javascript:submitModuleListEdit('<%=NavigationConstants.COPY_FROM_MODULE_LIST%>')" >                                              
                 <img src="<%=urlPrefix%>/i/copyButton.gif" border="0" alt="Copy to form"/>
             </a>              
          </td>   
         </logic:notEmpty>
          <td align="center">
            <html:link action='<%="/formbuilder/copyFromModuleList?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.DONE_VIEW_MODULE_LIST%>' 
              target="_parent">          
                    <html:img src='<%=urlPrefix+"/i/backButton.gif"%>' border="0" alt="Go back"/>         
            </html:link>
            
          </td>
          
         <logic:notEmpty name="<%=FormConstants.MODULE_LIST%>" >
          <td align="center">
             <a href="javascript:submitModuleListEdit('<%=NavigationConstants.DELETE_ELEMENTS_FROM_MODULE_LIST%>')" >                                              
                 <img src="<%=urlPrefix%>/i/deleteButton.gif" border="0" alt="Delete"/>
             </a>            
          </td>  
          
         </logic:notEmpty>
        </tr>
      </table>      
