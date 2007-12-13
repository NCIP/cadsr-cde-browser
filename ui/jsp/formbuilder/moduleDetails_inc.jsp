            <logic:notEmpty name="<%=FormConstants.CRF%>" property = "modules">
              <logic:iterate id="module" name="<%=FormConstants.CRF%>" type="gov.nih.nci.ncicb.cadsr.common.resource.Module" property="modules" indexId="modIndex" >                            
               <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
                 <tr >
           	    <td align="left" width="18%" >
                   <html:link action='<%="/formViewAddToList?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.COPY_SELECTED_MODULE_TO_LIST%>'
                      paramId= "<%=FormConstants.MODULE_INDEX%>"
		      paramName="modIndex"
                      scope="page">
                       Copy to module cart
                  </html:link>&nbsp;
           	    </td>                       
     <logic:present name="<%=FormConstants.MODULE_DISPLAY_ORDER_TO_COPY%>"> 
          	    <td align="left" width="82%" >
                   <html:link action='<%="/formbuilder/moduleSearch?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.COPY_SELECTED_MODULE_TO_FORM%>'
                      paramId= "<%=FormConstants.MODULE_INDEX%>"
		      paramName="modIndex"
                      scope="page">
                       Copy this module to form
                  </html:link>&nbsp;
           	    </td>      
     </logic:present>      	    
         	 </tr>
      		</table>               
                 <%@ include file="/formbuilder/commonModuleDetails_inc.jsp"%> 
                
                <!-- Module skip Pattern -->
                 <logic:present name="module" property = "triggerActions" >
                   <logic:notEmpty name="module" property = "triggerActions">
                            <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
                              <logic:iterate id="currTriggerAction" name="module" type="gov.nih.nci.ncicb.cadsr.common.resource.TriggerAction" property="triggerActions" indexId="triggerIndex" >
                             	<%@ include file="/formbuilder/skipPatternDetailsView_inc.jsp"%>
                              </logic:iterate>
                            </table>
                    </logic:notEmpty>
                 </logic:present>
                 <!-- Module Skip pattern end -->  
                 
      		<table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        	   <tr class>
          	      <td >
			             &nbsp;
          	      </td>
        	   </tr> 
        	</table>
                <logic:present name="<%=FormConstants.SHOW_MODULE_REPEATS%>">
                    <%@ include file="/formbuilder/repititionDetails_inc.jsp"%> 
                </logic:present>
              </logic:iterate><!-- Module-->
              
            
     
            </logic:notEmpty>