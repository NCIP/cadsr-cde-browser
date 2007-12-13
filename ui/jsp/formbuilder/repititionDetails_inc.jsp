            <% 
             List repeats = FormActionUtil.getRepetitions(module);
             pageContext.setAttribute("repeats",repeats);
             %>
            <logic:present name="repeats" >
            <logic:notEmpty name="repeats" >
              <logic:iterate id="module" name="repeats" type="gov.nih.nci.ncicb.cadsr.common.resource.Module" indexId="modIndex" >                                          
                 <%@ include file="/formbuilder/repeatDetailsModuleDetails_inc.jsp"%> 
      		<table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        	   <tr class>
          	      <td >
			             &nbsp;
          	      </td>
        	   </tr> 
        	</table>
              </logic:iterate><!-- Module-->
            </logic:notEmpty>
            </logic:present>