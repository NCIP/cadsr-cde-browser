            <logic:notEmpty name="<%=FormConstants.MODULE_LIST%>" >
               <table width="20%" align="left" cellpadding="0" cellspacing="0" border="0" >
                 </tr >
                    <td width="50%" align="left" >
                     <a href="javascript:CheckAll('<%= FormConstants.SELECTED_ITEMS%>')">Check All
                     </a>
                   </td>
                   <td width="50%" align="left">
                     <a href="javascript:ClearAll('<%= FormConstants.SELECTED_ITEMS%>')">Clear All
                     </a>     
                   </td>
                </tr>
              </table> 
              <br>
              <br>
              <logic:iterate id="module" name="<%=FormConstants.MODULE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.Module"  indexId="modIndex" >
 		<table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >               
                 <tr> 
                
                    <td  >
                       <table width="100%" align="center" cellpadding="0" cellspacing="01" border="0" class="OraBGAccentVeryDark"> 
                         <tr><td class="OraTableColumnHeader">
                           <INPUT TYPE=CHECKBOX NAME="<%= FormConstants.SELECTED_ITEMS%>" value="<%= modIndex %>">
                         </td></tr>
                        </table>
                    </td>
                    <td width="97%">
                       &nbsp;
                    </td>                     
                  </tr> 
                 </table>
 		
 		
 		 <%@ include file="/formbuilder/commonModuleDetails_no_skip_repetition_inc.jsp"%> 

      		<table width="90%" align="center" cellpadding="0" cellspacing="0" border="0" >
        	   <tr class>
          	      <td >
			              &nbsp;
          	      </td>
        	   </tr> 
        	</table>
                
              </logic:iterate><!-- Module-->
            </logic:notEmpty>
            <logic:empty name="<%=FormConstants.MODULE_LIST%>" >
               <br>
               <table width="100%" align="center"  border="0"  >
                 <tr >
                  
                  <td class="MessageText" width="10%" nowrap>
                  <b>
                    The module cart is empty
                   </b>
                 </td>
                </tr>
               </table>             
            </logic:empty>