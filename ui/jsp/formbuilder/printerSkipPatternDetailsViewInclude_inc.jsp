                                  <tr>
                                    <td>
                                      <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" >
                        
                                     
                                             <logic:equal value="<%=FormJspUtil.MODULE%>" name="currSkipTargetType">
                                             <bean:define id="targetModule" name="currTriggerAction" property="actionTarget" type="gov.nih.nci.ncicb.cadsr.common.resource.Module" />                                              
                                              <tr class="PrinterOraTabledata">
                                              <td class="PrinterOraTableColumnHeader" width="20%" nowrap>
                                                Module Name
                                              </td>
                                              <td  class="PrinterOraFieldText" width="80%" nowrap>
                                                    <bean:write  name="targetModule" property="longName"/> 
                                               </td>                                                               
                                             </tr>                 
                                             </logic:equal>   
                                     
                                             <logic:equal value="<%=FormJspUtil.QUESTION%>" name="currSkipTargetType">                                           
                                               <bean:define id="targetQuestion" name="currTriggerAction" property="actionTarget" type="gov.nih.nci.ncicb.cadsr.common.resource.Question" />
                                               
                                              <tr class="PrinterOraTabledata">
                                              <td class="PrinterOraTableColumnHeader" width="20%" nowrap>
                                                Question Name
                                              </td>
                                              <logic:present name="targetQuestion" property="dataElement">
                                              <td  class="PrinterOraFieldText" width="80%" nowrap>
                                                <table width="100%">
                                                  <tr>
                                                    <td  width="90%" class="PrinterOraFieldText" >
                                                      <bean:write  name="targetQuestion" property="longName"/> 
                                                    </td>
                                                   <td width="5%" align=right class="PrinterOraFieldText">
                                                         <bean:write name="targetQuestion" property="dataElement.CDEId"/>
                                                    </td>    
                                                    <td  width="5%" class="PrinterOraFieldText" nowrap align=right>
                                                      <bean:write name="targetQuestion" property="dataElement.version"/>
                                                    </td>                    
                                                  </tr>
                                                 </table>
                                              </td>
                                               </logic:present>
                                  
                                              <logic:notPresent name="targetQuestion" property="dataElement">
                                               <td  class="PrinterOraFieldText" width="70%" >
                                                <bean:write  name="targetQuestion" property="longName"/> 
                                                </td>                   
                                              </logic:notPresent>                 
                                             </tr>   
                                              <tr class="PrinterOraTabledata">
                                              <td class="PrinterOraTableColumnHeader" width="20%" nowrap>
                                                Module Name
                                              </td>
                                              <td  class="PrinterOraFieldText" width="80%" nowrap>
                                                    <bean:write  name="targetQuestion" property="module.longName"/> 
                                               </td>                
                                             </tr>  
                                             </logic:equal>   
                                             
                                             
                                             <tr class="PrinterOraTabledata">
                                              <td class="PrinterOraTableColumnHeader" width="20%" nowrap>
                                                Protocols
                                              </td>
                                              <td  class="PrinterOraFieldText" width="80%" nowrap>
                                                    <%=FormJspUtil.getDelimitedProtocolLongNames(currTriggerAction.getProtocols(),"<br>")%>
                                               </td>
            
                                             </tr>    
                                             <tr class="PrinterOraTabledata">
                                              <td class="PrinterOraTableColumnHeader" width="20%" nowrap>
                                                Classifications
                                              </td>
                                              <td  class="PrinterOraFieldText" width="80%" nowrap>
                                                   <%=FormJspUtil.getDelimitedCSILongNames(currTriggerAction.getClassSchemeItems(),"<br>")%>
                                               </td>                  
                                             </tr>                                                                
                                             
                                             
                                             <tr class="PrinterOraTabledata">
                                              <td class="PrinterOraTableColumnHeader" width="20%" nowrap>
                                                Instruction
                                              </td>
                                              <td  class="PrinterOraFieldText" width="80%" nowrap>
                                                    <bean:write  name="currTriggerAction" property="instruction"/> 
                                               </td>             
                                             </tr>                                                  
                                                                                               
                                       </table>
                                    </td>
                                 </tr>     