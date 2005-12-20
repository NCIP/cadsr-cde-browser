                                      

                            
                             
                             <%
                               String currSkipTargetType = FormJspUtil.getFormElementType(currTriggerAction.getActionTarget());
                               pageContext.setAttribute("currSkipTargetType",currSkipTargetType);
                                             
                            %> 
                             
                                  <tr class="OraTabledata">
                                   <td>
                                     <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" >
                                       <tr>
                                         <td class="OraTableColumnHeader" width="100%" nowrap>
                                           Skip to 
                                         </td>
                                        </tr>
                                      </table>
                                     </td>
                                  </tr>
                                  <%@ include file="/formbuilder/skipPatternDetailsViewInclude_inc.jsp"%>
                                                       
