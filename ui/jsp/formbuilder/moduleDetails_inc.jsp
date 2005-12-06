            <logic:notEmpty name="<%=FormConstants.CRF%>" property = "modules">
              <logic:iterate id="module" name="<%=FormConstants.CRF%>" type="gov.nih.nci.ncicb.cadsr.resource.Module" property="modules" indexId="modIndex" >                            
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
         	 </tr>
      		</table>               
                 <%@ include file="/formbuilder/commonModuleDetails_inc.jsp"%> 
                
                <!-- Module skip Pattern -->
                         <logic:present name="<%=FormConstants.SKIP_PATTERN%>" >
                          <%
                            TriggerAction triggerAction = (TriggerAction)request.getSession().getAttribute(FormConstants.SKIP_PATTERN);
                            System.out.println("triggerAction="+triggerAction);
                            String skipTargetType = FormJspUtil.getFormElementType(triggerAction.getActionTarget());
                            pageContext.setAttribute("skipTargetType",skipTargetType);
                            pageContext.setAttribute("skipTarget",triggerAction.getActionTarget());    
                            
                            %>
                            <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
                             <%@ include file="/formbuilder/skipPatternDetailsView_inc.jsp"%>
                            </table>
                          </logic:present>
                 <!-- Module Skip pattern end -->  
                 
      		<table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        	   <tr class>
          	      <td >
			             &nbsp;
          	      </td>
        	   </tr> 
        	</table>
                
              </logic:iterate><!-- Module-->
              
            
     
            </logic:notEmpty>