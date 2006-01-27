                <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentBlack">               
                 <tr class="PrinterOraTableColumnHeader">                 
                    <td class="PrinterOraTableColumnHeader" colspan="2">
                      <bean:write name="module" property="longName"/>
                    </td>
                  </tr>
                   <logic:present name="module" property="instruction">                   
                      <tr class="PrinterOraTabledata" >  
                       <td colspan="2">
                           <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentBlack" >
                             <tr class="PrinterOraTabledata">
                              <td class="PrinterOraTableColumnHeader" width="10%" nowrap>
                                <bean:message key="cadsr.formbuilder.form.instruction"/> 
                             </td>
                             <td class="PrinterOraTabledata">
                               <bean:write  name="module" property="instruction.longName"/>
                             </td>
                            </tr>
                           </table>
                       </td>
                      </tr>
                   </logic:present>                    

                      <tr class="PrinterOraTabledata" >  
                       <td colspan="2">
                           <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentBlack" >
                             <tr class="PrinterOraTabledata">
                              <td class="PrinterOraTableColumnHeader" width="10%" nowrap>
                               Number of Repetitions&nbsp
                             </td>
                             <td class="PrinterOraTabledata">
                                <bean:write name="module" property="numberOfRepeats"/>
                             </td>
                            </tr>
                           </table>
                       </td>
                      </tr>

                  <logic:present name="module">
                  <logic:notEmpty name="module" property = "questions">
                    <tr class="PrinterOraTabledata">
                      <td>
                        <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" >      
                          <logic:iterate id="question" name="module" type="gov.nih.nci.ncicb.cadsr.resource.Question" property="questions">                           
                            <tr class="PrinterOraTabledata">
                              <td class="PrinterOraFieldText" width="50">&nbsp;</td>
                              <td class="PrinterOraFieldText" height="1"  >                               
                              </td>
                            </tr>                              
                            <tr class="PrinterOraTabledata">
                              <td class="PrinterOraFieldText" width="50">&nbsp;</td>
                              <td class="PrinterUnderlineOraFieldText">
                                <bean:write name="question" property="longName"/>
                              </td>
                              <logic:present name="question" property = "dataElement">
                                <td align="center" width="70" class="PrinterUnderlineOraFieldText" >
				                          <bean:write name="question" property="dataElement.CDEId"/>
	    			                    </td>
                              <td align="center" width="70" class="PrinterUnderlineOraFieldText">
                                	<bean:write name="question" property="dataElement.version"/>
                              </td>                                
                              </logic:present>
                              <logic:notPresent name="question" property="dataElement">
                                  <td align="center" width="70" class="PrinterUnderlineOraFieldText">
                                   &nbsp;
                                  </td>
                                 <td align="center" width="70" class="PrinterUnderlineOraFieldText">
                                    &nbsp;
                                 </td>                              
                            </logic:notPresent>                                
                            </tr>    
                            <logic:present name="question" property="instruction">
                              <tr class="PrinterOraTableColumnHeader">
                                 <td class="PrinterOraTableColumnHeader" width="50">&nbsp;</td>
                                  <td class="PrinterOraTableColumnHeader" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentBlack" >
                                     <tr class="PrinterOraTabledata">
                                      <td class="PrinterOraTableColumnHeader" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.instruction"/>
                                     </td>
                                     <td class="PrinterOraFieldText">
                                       <bean:write  name="question" property="instruction.longName"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>                            
                            <logic:present name="question" property="defaultValidValue">
                              <tr class="PrinterOraTableColumnHeader">
                                 <td class="PrinterOraTableColumnHeader" width="50">&nbsp;</td>
                                  <td class="PrinterOraTableColumnHeader" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentBlack" >
                                     <tr class="PrinterOraTabledata">
                                      <td class="PrinterOraTableColumnHeader" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.questionDefaultValue"/>
                                     </td>
                                     <td class="PrinterOraFieldText">
                                       <bean:write  name="question" property="defaultValidValue.longName"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>                            
                            <logic:present name="question" property="dataElement">
                            <logic:present name="question" property="dataElement.valueDomain">
                              <tr class="PrinterOraTableColumnHeader">
                                 <td class="PrinterOraTableColumnHeader" width="50">&nbsp;</td>
                                  <td class="PrinterOraTableColumnHeader" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" 
class="OraBGAccentBlack" >
                                     <tr class="PrinterOraTabledata">
                                      <td class="PrinterOraTableColumnHeader"  nowrap colspan="2">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.valueDomainDetails"/>        
                                     </td>                                     
                                    </tr>
                                    
                                    <tr class="PrinterOraTabledata">
                                     <td class="PrinterOraTableColumnHeader" width="20%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.longName"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.longName"/>          
                            
                                     </td>
                                    </tr>
                                    
                                    <tr class="PrinterOraTabledata">
                                     <td class="PrinterOraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.datatype"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.datatype"/>          
                            
                                     </td>
                                    </tr>

                                    <tr class="PrinterOraTabledata">
                                     <td class="PrinterOraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.unitofmeasure"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.unitOfMeasure"/>     
                                     </td>
                                    </tr>

                                    <tr class="PrinterOraTabledata">
                                     <td class="PrinterOraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.displayFormat"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.displayFormat"/>     
                                     </td>
                                    </tr>

                                    <tr class="PrinterOraTabledata">
                                     <td class="PrinterOraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.concepts"/>
                                     </td>
                                     <td class="OraFieldText">
                                       
<%=CDEDetailsUtils.getConceptCodesUrl(question.getDataElement().getValueDomain().getConceptDerivationRule(),CDEBrowserParams.getInstance(),"link",",")%>
                                     </td>
                                    </tr>

                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>
                           </logic:present>

                            <logic:present name="question" property="defaultValue">
                              <tr class="PrinterOraTableColumnHeader">
                                 <td class="PrinterOraTableColumnHeader" width="50">&nbsp;</td>
                                  <td class="PrinterOraTableColumnHeader" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentBlack" >
                                     <tr class="PrinterOraTabledata">
                                      <td class="PrinterOraTableColumnHeader" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.questionDefaultValue"/>
                                     </td>
                                     <td class="PrinterOraFieldText">
                                       <bean:write  name="question" property="defaultValue"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>                            

                            
                            <logic:present name="question">
                            <logic:notEmpty name="question" property = "validValues">
                              <tr class="PrinterOraTabledata">
                                <td class="PrinterOraFieldText" width="50">&nbsp;</td>
                                <td>
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentBlack">
                                    <logic:iterate id="validValue" name="question" type="gov.nih.nci.ncicb.cadsr.resource.FormValidValue" property="validValues">
                                      <tr COLSPAN="3" class="PrinterOraTabledata">
                                        <td class="PrinterOraFieldText" width="50">&nbsp;</td>
                                        <td class="PrinterOraFieldText">
                                          <bean:write name="validValue" property="longName"/>
                                        </td>
                                      </tr>
                                      <tr   class="PrinterOraTabledata">
                                        <td class="PrinterOraFieldText" width="50">&nbsp;</td>
                                        <td >                                      
                                        <% if(question.getDataElement()!=null|| validValue.getInstruction()!=null){%>
                                          <table align="center" width="100%" cellpadding="1" cellspacing="1" border="0"  class="OraBGAccentBlack" >                          
                                            <logic:present name="question" property="dataElement">
                                               <tr class="PrinterOraTabledata" >
                                                 <td  class="PrinterOraTableColumnHeader" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.name" /></td>
                                                 <td class="PrinterOraFieldText" >
                                                  <bean:write name="validValue" property="shortMeaning"/></td>                                          
                                               </tr>  
                                            </logic:present>
                                              <logic:present name="validValue" property="instruction">                
                                                 <tr class="PrinterOraTabledata" >
                                                  <td class="PrinterOraTableColumnHeader" width="10%" nowrap>
                                                    <bean:message key="cadsr.formbuilder.form.instruction"/> 
                                                 </td>
                                                 <td class="PrinterOraFieldText">
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
                            </logic:present>
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