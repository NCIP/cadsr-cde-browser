<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<HTML>
  <HEAD>
    <TITLE>Welcome to Form Builder..</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css"/>
    <SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
  document.forms[0].submit();
}

function submitValidValueEdit(methodName,questionIndexValue,validValueIndexValue) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndexValue;
  document.forms[0].<%=FormConstants.VALID_VALUE_INDEX%>.value=validValueIndexValue;
  document.forms[0].submit();
}
function submitModuleEdit(methodName,questionIndexValue) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndexValue;
  document.forms[0].submit();
}
function submitFormToSave(methodName) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();
}

function clearProtocol() {
  document.forms[0].protocolIdSeq.value = "";
  document.forms[0].protocolLongName.value = "";
}

-->
</SCRIPT>
  </HEAD>
  <BODY bgcolor="#ffffff">
    <% String urlPrefix = "";

%>
    <html:form action="/moduleSaveAction.do">
      <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      <html:hidden property="<%=FormConstants.FORM_ID_SEQ%>"/>
      <html:hidden property="<%=FormConstants.QUESTION_ID_SEQ%>"/>
      <html:hidden property="<%=FormConstants.QUESTION_INDEX%>"/>  
      <html:hidden property="<%=FormConstants.VALID_VALUE_INDEX%>"/>
      <%@ include file="/formbuilder/common_header_inc.jsp"%>
      <jsp:include page="/formbuilder/tab_inc.jsp" flush="true">
        <jsp:param name="label" value="Edit&nbsp;Module"/>
        <jsp:param name="urlPrefix" value=""/>
      </jsp:include>
      <%@ include file="/formbuilder/moduleEditButton_inc.jsp"%>
      <logic:messagesPresent message="true">
        <table width="80%" align="center">
          <html:messages id="message" message="true">
            <tr align="center">
              <td align="left" class="OraErrorText"><b>
                  <bean:write name="message"/>
                </b>
                <br/>
              </td>
            </tr>
          </html:messages>
          <tr align="center">
            <td>&nbsp;</td>
          </tr>
        </table>
      </logic:messagesPresent>
      <logic:present name="<%=FormConstants.MODULE%>">
        <bean:define id="module" name="<%=FormConstants.MODULE%>"></bean:define>
        <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.module.name"/>
            </td>
            <td class="OraFieldText" nowrap>
              <html:text size="80%" property="<%=FormConstants.MODULE_LONG_NAME%>"></html:text>
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%">
              <bean:message key="cadsr.formbuilder.moduleinstruction.name"/>
            </td>
            <td class="OraFieldText">
              <html:text size="80%" property="<%=FormConstants.MODULE_INSTRUCTION_LONG_NAME%>"></html:text>
            </td>
          </tr>
        </table>
        
        <!-- If the Question Collection is empty and deleted Question Exists -->
            <logic:empty name="module" property="questions">
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">
             <tr >
                <td >
                  &nbsp;
                </td>
              </tr>         
              <tr align="right">                      
                <td align="right" width="25">
                  <html:link action='<%="/formDetailsAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_DETAILS%>'
                       paramId="<%=FormConstants.FORM_ID_SEQ%>" paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
                    <html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add New Module"/>
                  </html:link>&nbsp;
                </td>
              </tr>
              </table> 
              </logic:empty>
            <!-- Add for delete and new Question end -->         
        
        <logic:notEmpty name="module" property="questions">        

            <logic:iterate id="question" name="module" indexId="questionIndex" type="gov.nih.nci.ncicb.cadsr.resource.Question" property="questions">
             <bean:size id="questionSize" name="module" property="questions"/>
             
        <!-- If the Question Collection is empty and deleted Questions Exists -->
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">              
             <tr >
                <td >
                  &nbsp;
                </td>
              </tr>         
              <tr align="right">
                <logic:notEmpty name="<%=FormConstants.DELETED_QUESTIONS%>">
                  <td align="right"   class="OraFieldText" nowrap width="90%">    
                      <html:select styleClass="FreeDropdown" property="<%=FormConstants.ADD_DELETED_QUESTION_IDSEQ%>">
                        <html:options collection="<%=FormConstants.DELETED_QUESTIONS%>" 
                            property="quesIdseq" labelProperty="longName" />
                      </html:select >
                  </td>
                  <td align="left" width="3%">
                      <a href="javascript:submitModuleEdit('<%=NavigationConstants.ADD_FROM_DELETED_QUESTION_LIST%>','<%=questionIndex%>')">
                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                      </a>                          
                  </td>   
                  </logic:notEmpty>
                  <logic:empty name="<%=FormConstants.DELETED_QUESTIONS%>">
                  <td width="93%">
                    &nbsp;
                  </td>  
                </logic:empty>                        
                <td align="right" width="3%">
                  <html:link action='<%="/formDetailsAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_DETAILS%>'
                       paramId="<%=FormConstants.FORM_ID_SEQ%>" paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
                    <html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add New Module"/>
                  </html:link>&nbsp;
                </td>
              </tr>              
              
              </table> 
            <!-- Add for delete and new Question end -->                 
             
             <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">              
              <tr class="OraTabledata">
                <td>
                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                    <tr class="OraTableColumnHeader">
                      <td class="OraTableColumnHeaderModule">
                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                          <tr class="OraTableColumnHeaderModule">
                            <td width="86%">&nbsp;</td>
                            <td align="right">
                              <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                <tr class="OraTableColumnHeaderModule">
                                  <td align="center">
                                    <logic:notEqual value="<%= String.valueOf(questionSize.intValue()-1) %>" name="questionIndex">
                                     <a href="javascript:submitModuleEdit('<%=NavigationConstants.MOVE_QUESTION_DOWN%>','<%=questionIndex%>')">                                     
                                        <img src="<%=urlPrefix%>i/down.gif" border="0" alt="Down"/>
                                      </a>
                                    </logic:notEqual>                                  
                                  </td>
                                  <td align="center">
                                    <logic:notEqual value="<%= String.valueOf(0) %>" name="questionIndex">                                    
                                     <a href="javascript:submitModuleEdit('<%=NavigationConstants.MOVE_QUESTION_UP%>','<%=questionIndex%>')">
                                        <img src="<%=urlPrefix%>i/up.gif" border="0" alt="Up"/>
                                      </a>
                                    </logic:notEqual>
                                  </td>
                                  <td align="center">
                                       <html:img src='<%=urlPrefix+"i/edit.gif"%>' border="0" alt="Change DE Association"/>
                                  </td>                                  
                                  <td align="center">
                                    <a href="javascript:submitModuleEdit('<%=NavigationConstants.DELETE_QUESTION%>','<%=questionIndex%>')">
                                      <img src="<%=urlPrefix%>i/delete.gif" border="0" alt="Delete"/>
                                    </a>
                                  </td>
                                  
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    
                    <tr class="OraTableColumnHeader">
                      <td class="OraTableColumnHeaderModule">
                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                          <tr class="OraTableColumnHeader">
                            
                            <td align="right">
                              <table width="100%" align="right" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
                                <tr class="OraTableColumnHeaderModule">
                                 
                                 <td >
                                  <html:text size="100%" indexed="true" property='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>'></html:text>
                                 </td>
                                <logic:present name="question" property="dataElement">
                                  <td align="center" width="70" >
                                   <html:link page='<%="/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>' 
                                      paramId="p_de_idseq" paramName="question" paramProperty="dataElement.deIdseq" target="_blank">
                                    <bean:write name="question" property="dataElement.CDEId"/>
                                   </html:link>
                                  </td>                        
                                  <td align="center" width="70" >
                                    <bean:write name="question" property="dataElement.version"/>
                                  </td>
                                 </logic:present>
                                </tr>
                              </table>
                            </td>
                          </tr>
                        </table>
                      </td>
                    </tr>                  
                    
                  </table>
                </td>
              </tr>           
              <tr class="OraTabledata">
                <td>
                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                      <logic:present name="question" property="dataElement">
                      <logic:present name="question" property="dataElement.longName">
                      <tr class="OraTabledata">
                          <td class="OraFieldText" width="50">&nbsp;</td>                       
                          <td class="OraFieldText">
                            <html:radio property='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>' value="dataElement.longName">
                              <bean:write name="question" property="dataElement.longName"/>
                             </html:radio>  
                          </td>
                      </tr>
                      </logic:present>
                      <logic:present name="question" property="dataElement.longCDEName">
                      <tr class="OraTabledata">
                          <td class="OraFieldText" width="50">&nbsp;</td>                       
                          <td class="OraFieldText">
                            <html:radio property='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>' value="dataElement.longCDEName">
                              <bean:write name="question" property="dataElement.longCDEName"/>
                             </html:radio>  
                          </td>
                      </tr>
                      </logic:present>                      
                      </logic:present>
                      <logic:present name="question">
                      <logic:empty name="question" property="validValues">                           
                          <tr class="OraTabledata">
                            <td class="OraFieldText" width="50">&nbsp;</td>
                            <td>
                              <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">                                                                
                                  <tr class="OraTabledata">
                                    <td class="OraTabledata" width="50">&nbsp;</td>
                                    <td class="OraTabledata" align="right" width="90%">                                                                        
                                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                          <tr class="OraTabledata" >
                                           <td width="95%">&nbsp;</td>
                                            <td align="center"><a href="javascript:submitFormEdit('<%=NavigationConstants.DELETE_MODULE%>','<%=NavigationConstants.DELETE_MODULE%>')">
                                              <a href="javascript:submitFormEdit('<%=NavigationConstants.MOVE_MODULE_DOWN%>','<%=NavigationConstants.DELETE_MODULE%>')">
                                               <img src="<%=urlPrefix%>i/add.gif" border="0" alt="Add"/>
                                              </a>
                                            </td>
                                          </tr>
                                        </table>                                                                                                            
                                    </td>
                                  </tr>                                   
                              </table>
                            </td>
                          </tr>
                        </logic:empty>
                      <logic:notEmpty name="question" property="validValues">                           
                          <tr class="OraTabledata">
                            <td class="OraFieldText" width="50">&nbsp;</td>
                            <td>
                              <table width="100%" align="center" cellpadding="1" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                <logic:iterate id="validValue" name="question" indexId="validValueIndex" type="gov.nih.nci.ncicb.cadsr.resource.FormValidValue" property="validValues">
                                <bean:size id="validValueSize" name="question" property="validValues"/>                                  
                                  <tr class="OraTabledata">
                                    <td class="OraTabledata" width="50">&nbsp;</td>
                                    <td class="OraTabledata" align="right" width="90%">                                                                        
                                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                          <tr class="OraTabledata" >
                                           <td width="95%">&nbsp;</td>
                                            <td align="center"><a href="javascript:submitFormEdit('<%=NavigationConstants.DELETE_MODULE%>','<%=validValueIndex%>')">
                                              <a href="javascript:submitFormEdit('<%=NavigationConstants.MOVE_MODULE_DOWN%>','<%=validValueIndex%>')">
                                               <img src="<%=urlPrefix%>i/add.gif" border="0" alt="Add"/>
                                              </a>
                                            </td>
                                          </tr>
                                        </table>                                                                                                            
                                    </td>
                                  </tr>                                   
                                  <tr class="OraFieldText">
                                    <td class="OraTabledata" width="50">&nbsp;</td>
                                    <td class="OraFieldText" align="right" width="90%">                                                                        
                                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                          <tr class="OraTableColumnHeaderVV" >
                                           <td width="86%"><bean:write name="validValue" property="longName"/></td>
                                            <td align="center">
                                              <logic:notEqual value="<%= String.valueOf(validValueSize.intValue()-1) %>" name="validValueIndex">
                                                <a href="javascript:submitValidValueEdit('<%=NavigationConstants.MOVE_VALID_VALUE_DOWN%>','<%=questionIndex%>','<%=validValueIndex%>')">
                                                  <img src="<%=urlPrefix%>i/down.gif" border="0" alt="Down"/>
                                                </a>
                                              </logic:notEqual>   
                                              <logic:equal value="<%= String.valueOf(validValueSize.intValue()-1) %>" name="validValueIndex">
						                                    &nbsp;&nbsp;&nbsp;
                                              </logic:equal>                                                
                                            </td>
                                            <td align="center">
                                              <logic:notEqual value="<%= String.valueOf(0) %>" name="validValueIndex">
                                               <a href="javascript:submitValidValueEdit('<%=NavigationConstants.MOVE_VALID_VALUE_UP%>','<%=questionIndex%>','<%=validValueIndex%>')">                                               
                                                 <img src="<%=urlPrefix%>i/up.gif" border="0" alt="Up"/>
                                                </a>
                                              </logic:notEqual>
                                              <logic:equal value="<%= String.valueOf(0) %>" name="validValueIndex">
						                                     &nbsp;&nbsp;&nbsp;
                                              </logic:equal>                                              
                                            </td>
                                            <td align="center"><a href="javascript:submitValidValueEdit('<%=NavigationConstants.DELETE_VALID_VALUE%>','<%=validValueIndex%>')">
                                              <a href="javascript:submitFormEdit('<%=NavigationConstants.MOVE_MODULE_DOWN%>','<%=validValueIndex%>')">
                                               <img src="<%=urlPrefix%>i/delete.gif" border="0" alt="Delete"/>
                                              </a>
                                            </td>
                                          </tr>
                                        </table>                                                                                                            
                                    </td>
                                  </tr>                                  
                                </logic:iterate>                                
                          <tr class="OraTabledata">
                            <td class="OraFieldText" width="50">&nbsp;</td>
                            <td>
                              <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">                                                                
                                  <tr class="OraTabledata">
                                    <td class="OraTabledata" width="50">&nbsp;</td>
                                    <td class="OraTabledata" align="right" width="90%">                                                                        
                                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                          <tr class="OraTabledata" >
                                           <td width="95%">&nbsp;</td>
                                            <td align="center"><a href="javascript:submitFormEdit('<%=NavigationConstants.DELETE_MODULE%>','<%=NavigationConstants.DELETE_MODULE%>')">
                                              <a href="javascript:submitFormEdit('<%=NavigationConstants.MOVE_MODULE_DOWN%>','<%=NavigationConstants.DELETE_MODULE%>')">
                                               <img src="<%=urlPrefix%>i/add.gif" border="0" alt="Add"/>
                                              </a>
                                            </td>
                                          </tr>
                                        </table>                                                                                                            
                                    </td>
                                  </tr>                                   
                              </table>
                            </td>
                          </tr>                                
                                
                              </table>
                            </td>
                          </tr>
                        </logic:notEmpty>
                      </logic:present>                  
                  </table>
                </td>
              </tr>
             </table>             
            <logic:equal value="<%= String.valueOf(questionSize.intValue()-1) %>" name="questionIndex">
            <!-- Add for delete and new Question -->
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">        
              <tr align="right">
                <logic:notEmpty name="<%=FormConstants.DELETED_QUESTIONS%>">
                  <td align="right"   class="OraFieldText" nowrap width="90%">    
                      <html:select styleClass="FreeDropdown" property="<%=FormConstants.ADD_DELETED_QUESTION_IDSEQ%>">
                        <html:options collection="<%=FormConstants.DELETED_QUESTIONS%>" 
                            property="quesIdseq" labelProperty="longName" />
                      </html:select >
                  </td>
                  <td align="left" width="3%">
                      <a href="javascript:submitModuleEdit('<%=NavigationConstants.ADD_FROM_DELETED_QUESTION_LIST%>','<%=questionSize%>')">
                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                      </a>                          
                  </td>   
                  </logic:notEmpty>
                  <logic:empty name="<%=FormConstants.DELETED_QUESTIONS%>">
                   <td width="93%">
                    &nbsp;
                   </td>  
                  </logic:empty>               
                 <td align="right" width="3%">
                  <html:link action='<%="/formDetailsAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_DETAILS%>'
                       paramId="<%=FormConstants.FORM_ID_SEQ%>" paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq">
                    <html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add New Module"/>
                  </html:link>&nbsp;
                </td>
              </tr>
              </table> 
            <!-- Add for delete and new Question end -->  
            </logic:equal>             
            </logic:iterate>          
        </logic:notEmpty>
      </logic:present>
      <%@ include file="/formbuilder/editButton_inc.jsp"%>
    </html:form>
  </BODY>
</HTML>
