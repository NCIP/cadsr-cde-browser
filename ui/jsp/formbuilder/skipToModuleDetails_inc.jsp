            <bean:define id="currentModuleIndex" name="moduleEditForm" property="moduleIndex" scope="session"/>
            <logic:notEmpty name="<%=FormConstants.SKIP_TARGET_FORM%>" property = "modules">
              <logic:iterate id="module" name="<%=FormConstants.SKIP_TARGET_FORM%>" type="gov.nih.nci.ncicb.cadsr.common.resource.Module" property="modules" indexId="modIndex" >                            
               <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
                 <tr >
           	    <td align="left" width="100%">
                                <%
                                        HashMap paramsModule = new java.util.HashMap();
                                        paramsModule.put(FormConstants.TARGET_MODULE_INDEX,modIndex);
                                        paramsModule.put(NavigationConstants.METHOD_PARAM,NavigationConstants.SET_MODULE_AS_TARGET);
                                        if (currentModuleIndex!=null && currentModuleIndex!=""){
                                        	paramsModule.put(FormConstants.MODULE_INDEX,currentModuleIndex);
                                        }	
                                        pageContext.setAttribute("linkParamsModule", paramsModule);
                                %>
                                <html:link  name="linkParamsModule" scope="page"  action="/formbuilder/skipAction">
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
                          <logic:iterate id="question" name="module" type="gov.nih.nci.ncicb.cadsr.common.resource.Question" property="questions" indexId="questionIndex" >                           
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
                                        params.put(FormConstants.TARGET_MODULE_INDEX,modIndex);
                                        params.put(NavigationConstants.METHOD_PARAM,NavigationConstants.SET_QUESTION_AS_TARGET);
                                        if (currentModuleIndex!=null && currentModuleIndex!=""){
                                            params.put(FormConstants.MODULE_INDEX,currentModuleIndex);
                                        }	
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
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader" width="10%" nowrap>
                                          <bean:message key="cadsr.formbuilder.form.question.mandatory"/> 
                                     </td>
                                     <td class="OraFieldText">
                                	<html:checkbox name="question" property="mandatory" disabled="true"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>

			   <logic:present name="question" property="dataElement">
                            <logic:present name="question" property="dataElement.valueDomain">
                              <tr class="OraTabledata">
                                 <td class="OraFieldText" width="50">&nbsp;</td>
                                  <td class="OraFieldText" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" 
class="OraBGAccentVeryDark" >
                                     <tr class="OraTabledata">
                                      <td class="OraTableColumnHeader"  nowrap colspan="2">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.valueDomainDetails"/>        
                                     </td>                                     
                                    </tr>
                                    
                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader" width="20%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.longName"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.longName"/>          
                            
                                     </td>
                                    </tr>
                                    
                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.datatype"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.datatype"/>          
                            
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.unitofmeasure"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.unitOfMeasure"/>     
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.displayFormat"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.displayFormat"/>     
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader">
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

                            <logic:present name="question">
                            <logic:notEmpty name="question" property = "validValues">
                              <tr class="OraTabledata">
                                <td class="OraFieldText" width="50">&nbsp;</td>
                                <td colspan="2">
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                    <logic:iterate id="validValue" name="question" type="gov.nih.nci.ncicb.cadsr.common.resource.FormValidValue" property="validValues" indexId="vvIndex">
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
                                        <%-- if(question.getDataElement()!=null|| validValue.getInstruction()!=null){--%>
                                          <table align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark" >                          
                                               <tr class="OraTabledata">
                                                 <td  class="OraTableColumnHeader" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.text" /></td>
                                                 <td class="OraFieldText" >
                                                  <bean:write name="validValue" property="formValueMeaningText"/></td>                                          
                                               </tr>  
                                               <tr class="OraTabledata">
                                                 <td  class="OraTableColumnHeader" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.description" /></td>
                                                 <td class="OraFieldText" >
                                                  <bean:write name="validValue" property="formValueMeaningDesc"/></td>                                          
                                               </tr>  
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
                                          <%--}--%>
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