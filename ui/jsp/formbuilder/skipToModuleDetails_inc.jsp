            <logic:notEmpty name="<%=FormConstants.SKIP_TARGET_FORM%>" property = "modules">
              <logic:iterate id="module" name="<%=FormConstants.SKIP_TARGET_FORM%>" type="gov.nih.nci.ncicb.cadsr.resource.Module" property="modules" indexId="modIndex" >                            
               <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
                 <tr >
           	    <td align="left" width="100%">
                   <html:link action='<%="/formbuilder/skipAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.SET_MODULE_AS_TARGET%>'
                      paramId= "<%=FormConstants.MODULE_INDEX%>"
		      paramName="modIndex"
                      scope="page">
                       Skip to this Module
                  </html:link>&nbsp;
           	    </td>                
         	 </tr>
      		</table>
 		<table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">               
                 <tr>                 
                    <td class="OraHeaderBlack">
                      <bean:write name="module" property="longName"/>
                    </td>
                  </tr>        
                   <logic:present name="module" property="instruction">                   
                      <tr>  
                       <td colspan="2">
                           <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                             <tr class="OraTabledata">
                              <td class="OraTableColumnHeader" width="10%" nowrap>
                                <bean:message key="cadsr.formbuilder.form.instruction"/> 
                             </td>
                             <td class="OraFieldTextInstruction">
                               <bean:write  name="module" property="instruction.longName"/>
                             </td>
                            </tr>
                           </table>
                       </td>
                      </tr>
                   </logic:present>                                      
                  <logic:present name="module">
                  <logic:notEmpty name="module" property = "questions">
                    <tr class="OraTabledata">
                      <td>
                        <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraTabledata">      
                          <logic:iterate id="question" name="module" type="gov.nih.nci.ncicb.cadsr.resource.Question" property="questions" indexId="questionIndex" >                           
                            <bean:size id="questionSize" name="module" property="questions" />
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="50">&nbsp;</td>
                              <td height="1"  class="OraFieldText">                               
                              </td>                              
                            </tr>    
                            <tr class="OraTabledata">
                              <td class="OraFieldText" colspan=2>                            
                                <%
                                        HashMap params = new java.util.HashMap();
                                        params.put(FormConstants.SK_QUESTION_INDEX,questionIndex);
                                        params.put(FormConstants.MODULE_INDEX,modIndex);
                                        params.put(NavigationConstants.METHOD_PARAM,NavigationConstants.SET_QUESTION_AS_TARGET);
                                        pageContext.setAttribute("linkParams", params);
                                %>
                                
                                <html:link  name="linkParams" scope="page"  action="/formbuilder/skipAction">
                                     Skip to this Question
                                </html:link>&nbsp;
                                
                              </td>
                          
                            </tr>                               
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="7%"> 
                                &nbsp;
                              </td>
                              <td class="UnderlineOraFieldText" >
                                <bean:write name="question" property="longName"/>
                              </td>
                              <td class="OraTabledata" width="15%" align="right" >
                               <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraTabledata">
                                 <tr>
                                   <logic:present name="question" property = "dataElement">
                                     <td align="right" width="70" class="UnderlineOraFieldText" >                        
                                            <html:link page='<%="/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>'
                                               paramId = "p_de_idseq"
                                                paramName="question"
                                                paramProperty="dataElement.deIdseq"
                                                target="_blank">
                                            <bean:write name="question" property="dataElement.CDEId"/>
                                            </html:link>
                                     </td>
                                    <td align="right" width="70" class="UnderlineOraFieldText">
                                       <bean:write name="question" property="dataElement.version"/>
                                    </td>                                  
                                   </logic:present>
                                   <logic:notPresent name="question" property="dataElement">
                                     <td align="center" width="70" class="UnderlineOraFieldText">
                                       &nbsp;
                                     </td>
                                     <td align="center" width="70" class="UnderlineOraFieldText">
                                       &nbsp;
                                      </td>                              
                                   </logic:notPresent>  
                                 </tr>  
                               </table>
                              </td> 
                            </tr>
                            <logic:present name="question" property="instruction">
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.instruction"/>
                                     </td>
                                     <td class="OraFieldTextInstruction">
                                       <bean:write  name="question" property="instruction.longName"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>

                            <logic:present name="question">
                            <logic:notEmpty name="question" property = "validValues">
                              <tr class="OraTabledata">
                                <td class="OraFieldText" width="50">&nbsp;</td>
                                <td colspan="2">
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                    <logic:iterate id="validValue" name="question" type="gov.nih.nci.ncicb.cadsr.resource.FormValidValue" property="validValues" indexId="vvIndex">
                                      <tr   class="OraTabledata">
                                        <td COLSPAN="2" class="OraFieldText" >&nbsp;</td>
                                      </tr>
                                      <tr   class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td class="OraFieldText">
                                          <bean:write name="validValue" property="longName"/>
                                        </td>
                                      </tr>
                                      <tr   class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td >
                                        <% if(question.getDataElement()!=null|| validValue.getInstruction()!=null){%>
                                          <table align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark" >                          
                                            <logic:present name="question" property="dataElement">
                                               <tr class="OraTabledata">
                                                 <td  class="OraTableColumnHeader" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.name" /></td>
                                                 <td class="OraFieldText" >
                                                  <bean:write name="validValue" property="shortMeaning"/></td>                                          
                                               </tr>  
                                            </logic:present>
                                              <logic:present name="validValue" property="instruction">                
                                                 <tr class="OraTabledata">
                                                  <td class="OraTableColumnHeader" width="10%" nowrap>
                                                    <bean:message key="cadsr.formbuilder.form.instruction"/> 
                                                 </td>
                                                 <td class="OraFieldTextInstruction">
                                                   <bean:write  name="validValue" property="instruction.longName"/>
                                                 </td>
                                                </tr>  
                                                
                                              </logic:present>  
                                           </table>   
                                          <%}%>
                                        </td>                                        
                                      </tr>   
                                                                                                                                                
                                    </logic:iterate><!-- valid Value-->
                                  </table>
                                </td>
                              </tr>
                            </logic:notEmpty>
                            <logic:empty name="question" property = "validValues">
                              <tr class="OraTabledata">
                                <td class="OraFieldText" width="50">&nbsp;</td>
                                <td>
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                      <tr  COLSPAN="3" class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td class="OraFieldText">
                                          &nbsp;
                                        </td>
                                      </tr>   
                                  </table>
                                </td>
                              </tr>                            
                            </logic:empty>
                            </logic:present>
                            
                           <logic:equal value="<%= String.valueOf(questionSize.intValue()-1) %>" name="questionIndex">          
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="50">&nbsp;</td>
                              <td height="1"  class="OraFieldText">                               
                              </td>                              
                            </tr>         
                          </logic:equal>                             
                          </logic:iterate><!-- Question-->
                        </table>
                      </td>
                    </tr>
                  </logic:notEmpty>
                  </logic:present>
                </table>

      		<table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        	   <tr class>
          	      <td >
			              &nbsp;
          	      </td>
        	   </tr> 
        	</table>
                
              </logic:iterate><!-- Module-->
            </logic:notEmpty>