                             <%
                               String currSkipTargetType = FormJspUtil.getFormElementType(currTriggerAction.getActionTarget());
                               pageContext.setAttribute("currSkipTargetType",currSkipTargetType);
                               String currSkipSourceType = FormJspUtil.getFormElementType(currTriggerAction.getActionSource());
                               pageContext.setAttribute("currSkipSourceType",currSkipSourceType);                                            
                            %> 
                                      <tr class="OraTabledata">
                                       <td>
                                         <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" >
                                           <tr>
                                             <td class="OraTableColumnHeader" width="95%" nowrap>
                                               Skip to 
                                             </td>
                                             <td class="OraTableColumnHeader" align="right" width="5%">
                                               <a href="javascript:submitForDeleteSkipValidValue('<%=NavigationConstants.CONFIRM_VALID_VALUE_SKIP_PATTERN_DELETE%>','<%=questionIndex%>','<%=validValueIndex%>','<%=triggerIndex%>')"> 
                                                  <img src=<%=urlPrefix%>i/delete.gif border=0 alt="Delete">
                                               </a>
                                             </td> 
                                             <td class="OraTableColumnHeader" align="right" width="5%">                                             
                                               <a href="javascript:submitModuleForValidValueSkipEdit('<%=NavigationConstants.CHECK_MODULE_CHANGES%>','<%=questionIndex%>','<%=validValueIndex%>','<%=triggerIndex%>')"> 
                                                     <img src=<%=urlPrefix%>i/edit.gif border=0 alt="Edit">                                            
                                               </a>                                             
                                             </td>                                             
                                            </tr>
                                          </table>
                                         </td>
                                      </tr>
                                        <%@ include file="/formbuilder/skipPatternDetailsViewInclude_inc.jsp"%>  
            
