                                  <tr>
                                    <td>
                                      <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" >
                        
                                     
                                             <logic:equal value="<%=FormJspUtil.MODULE%>" name="currSkipTargetType">
                                             <bean:define id="targetModule" name="currTriggerAction" property="actionTarget" type="gov.nih.nci.ncicb.cadsr.common.resource.Module" />                                              
                                              <tr class="OraTabledata">
                                              <td class="OraTableColumnHeader" width="20%" nowrap>
                                                Module Name
                                              </td>
                                              <td  class="OraFieldText" width="80%" nowrap>
                                                    <bean:write  name="targetModule" property="longName"/> 
                                               </td>                                                               
                                             </tr>                 
                                             </logic:equal>   
                                     
                                             <logic:equal value="<%=FormJspUtil.QUESTION%>" name="currSkipTargetType">                                           
                                               <bean:define id="targetQuestion" name="currTriggerAction" property="actionTarget" type="gov.nih.nci.ncicb.cadsr.common.resource.Question" />
                                               
                                              <tr class="OraTabledata">
                                              <td class="OraTableColumnHeader" width="20%" nowrap>
                                                Question Name
                                              </td>
                                              <logic:present name="targetQuestion" property="dataElement">
                                              <td  class="OraFieldText" width="80%" nowrap>
                                                <table width="100%">
                                                  <tr>
                                                    <td  width="90%" class="OraFieldText" >
                                                      <bean:write  name="targetQuestion" property="longName"/> 
                                                    </td>
                                                   <td width="5%" align=right>
                                                        <html:link page='<%="/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>'
                                                                paramId = "p_de_idseq"
                                                                paramName="targetQuestion"
                                                                paramProperty="dataElement.deIdseq"
                                                                target="_blank">
                                                         <bean:write name="targetQuestion" property="dataElement.CDEId"/>
                                                       </html:link> 
                                                    </td>    
                                                    <td  width="5%" class="OraFieldText" nowrap align=right>
                                                      <bean:write name="targetQuestion" property="dataElement.version"/>
                                                    </td>                    
                                                  </tr>
                                                 </table>
                                              </td>
                                               </logic:present>
                                  
                                              <logic:notPresent name="targetQuestion" property="dataElement">
                                               <td  class="OraFieldText" width="70%" >
                                                <bean:write  name="targetQuestion" property="longName"/> 
                                                </td>                   
                                              </logic:notPresent>                 
                                             </tr>   
                                              <tr class="OraTabledata">
                                              <td class="OraTableColumnHeader" width="20%" nowrap>
                                                Module Name
                                              </td>
                                              <td  class="OraFieldText" width="80%" nowrap>
                                                    <bean:write  name="targetQuestion" property="module.longName"/> 
                                               </td>                
                                             </tr>  
                                             </logic:equal>   
                                             
                                             
                                             <tr class="OraTabledata">
                                              <td class="OraTableColumnHeader" width="20%" nowrap>
                                                Protocols
                                              </td>
                                              <td  class="OraFieldText" width="80%" nowrap>
                                                    <%=FormJspUtil.getDelimitedProtocolLongNames(currTriggerAction.getProtocols(),"<br>")%>
                                               </td>
            
                                             </tr>    
                                             <tr class="OraTabledata">
                                              <td class="OraTableColumnHeader" width="20%" nowrap>
                                                Classifications
                                              </td>
                                              <td  class="OraFieldText" width="80%" nowrap>
                                                   <%=FormJspUtil.getDelimitedCSILongNames(currTriggerAction.getClassSchemeItems(),"<br>")%>
                                               </td>                  
                                             </tr>                                                                
                                             
                                             
                                             <tr class="OraTabledata">
                                              <td class="OraTableColumnHeader" width="20%" nowrap>
                                                Instruction
                                              </td>
                                              <td  class="OraFieldText" width="80%" nowrap>
                                                    <bean:write  name="currTriggerAction" property="instruction"/> 
                                               </td>             
                                             </tr>                                                  
                                                                                               
                                       </table>
                                    </td>
                                 </tr>     