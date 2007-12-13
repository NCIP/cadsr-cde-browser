            <logic:notEmpty name="<%=FormConstants.MODULE_COPY_FORM%>" property = "modules">
              <logic:iterate id="module" name="<%=FormConstants.MODULE_COPY_FORM%>" type="gov.nih.nci.ncicb.cadsr.common.resource.Module" property="modules" indexId="modIndex" >                            
              <logic:notPresent name="formLocked">
               <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
                 <tr >
           	    <td align="left" width="18%" >
                   <html:link action='<%="/formbuilder/moduleSearch?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.COPY_SELECTED_MODULE_TO_LIST%>'
                      paramId= "<%=FormConstants.MODULE_INDEX%>"
		      paramName="modIndex"
                      scope="page">
                       Copy to module cart
                  </html:link>&nbsp;
           	    </td>    
           	    <td align="left" width="82%" >
                   <html:link action='<%="/formbuilder/moduleSearch?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.COPY_SELECTED_MODULE_TO_FORM%>'
                      paramId= "<%=FormConstants.MODULE_INDEX%>"
		      paramName="modIndex"
                      scope="page">
                       Copy this module to form
                  </html:link>&nbsp;
           	    </td>                       
         	 </tr>
      		</table>
              </logic:notPresent>
                <%@ include file="/formbuilder/commonModuleDetails_inc.jsp"%> 
                <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        	   <tr class>
          	      <td >
			              &nbsp;
          	      </td>
        	   </tr> 
                </table>
                
              </logic:iterate><!-- Module-->
            </logic:notEmpty>