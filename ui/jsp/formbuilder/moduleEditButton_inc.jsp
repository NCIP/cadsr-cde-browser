      <table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
         <td>
             <a href="javascript:submitModuleToSave('<%=NavigationConstants.SAVE_MODULE%>')"> 
            <!--  For subset prototype <a href="javascript:submitModuleToSave('<%="subsetSave"%>')">
                -->
                <html:img src='<%=urlPrefix+"i/save.gif"%>' border="0" alt="Save"/>
                
             </a> 
          </td>  
          <td >
            <html:link action='<%="/cancelModuleEditAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.CANCEL_MODULE_EDIT%>' >            
              <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
	          </html:link>   
            
          </td>          
        </tr> 
                
      </table>