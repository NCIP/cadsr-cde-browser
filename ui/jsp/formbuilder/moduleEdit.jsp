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
<%@ page import="gov.nih.nci.ncicb.cadsr.jsp.tag.handler.AvailableValidValue"%>
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
function submitModuleToSave(methodName) {
  if(validateModuleEditForm(moduleEditForm)) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();
  }
}

function clearProtocol() {
  document.forms[0].protocolIdSeq.value = "";
  document.forms[0].protocolLongName.value = "";
}

-->
</SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = "";

      // Prepare parameter map for add and edit linx
      java.util.Map params = new java.util.HashMap();
      params.put(FormConstants.MODULE_INDEX, request.getParameter(FormConstants.MODULE_INDEX));
      // params.put(FormConstants.QUESTION_INDEX, request.getParameter(FormConstants.QUESTION_INDEX));

      pageContext.setAttribute("params", params); 

      %>
    <html:form action="/moduleSaveAction.do">
      <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      <html:hidden property="<%=FormConstants.FORM_ID_SEQ%>"/>
      <html:hidden property="<%=FormConstants.QUESTION_ID_SEQ%>"/>
      <html:hidden property="<%=FormConstants.QUESTION_INDEX%>"/> 
      <html:hidden property="<%=FormConstants.VALID_VALUE_INDEX%>"/>
      <%@ include file="../common/in_process_common_header_inc.jsp"%>
      <jsp:include page="../common/tab_inc.jsp" flush="true">
        <jsp:param name="label" value="Edit&nbsp;Module"/>
        <jsp:param name="urlPrefix" value=""/>
      </jsp:include>
      <%@ include file="/formbuilder/moduleEditButton_inc.jsp"%>
        <%@ include file="showMessages.jsp" %>
        <%@ include file="showValidationErrors.jsp" %>
    
      <logic:present name="<%=FormConstants.MODULE%>">
      <table  width="80%">
        <tr>
           <td>&nbsp;</td>
        </tr>
      </table>      
        <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
         <tr align="center">
          <td align="left" class="OraTableColumnHeader">
           <bean:write  name="<%=FormConstants.CRF%>" property="longName"/> 
          </td>       
        </tr>
      </table>
      <table  width="80%">
        <tr>
           <td>&nbsp;</td>
        </tr>
      </table>
        <bean:define id="module" name="<%=FormConstants.MODULE%>"></bean:define>
        
      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Module Header</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>       
      
        <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.module.name"/>
            </td>
            <td class="OraFieldText" nowrap>
              <html:text size="100" property="<%=FormConstants.MODULE_LONG_NAME%>"
                  maxlength="<%= FormConstants.LONG_NAME_MAX_LENGTH %>">
              </html:text>
            </td>
          </tr>
          <!--TODO moved to 1.3 release
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%">
              <bean:message key="cadsr.formbuilder.moduleinstruction.name"/>
            </td>
            <td class="OraFieldText">
              <html:text size="100" property="<%=FormConstants.MODULE_INSTRUCTION_LONG_NAME%>"></html:text>
            </td>
          </tr>
          -->
        </table>
        
      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Questions</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>          
        <!-- If the Question Collection is empty and deleted Question Exists -->
            <logic:empty name="module" property="questions">
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">     
              <tr align="right">                      
                <td align="right" width="3%">
                  <%
                    params.put(FormConstants.QUESTION_INDEX, new Integer(0));
                    %>
                  <html:link action='<%="/gotoAddQuestion?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_ADD_QUESTION%>'
                    name="params" 
                    scope="page"
                    >
                    <html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add Question"/>
                  </html:link>
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
                  <%
                    params.put(FormConstants.QUESTION_INDEX, questionIndex);
                    %>
                  <html:link action='<%="/gotoAddQuestion?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_ADD_QUESTION%>'
                    name="params" 
                    scope="page"
                    >
                    <html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add Question"/>
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
                                    <html:link action='<%= "/gotoChangeAssociation?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_CHANGE_DE_ASSOCIATION%>'
                                      name="params"
                                      scope="page"
                                      >
                                      <img src="<%=urlPrefix%>i/association.gif" border="0" alt="Change DE Association"/>
                                    </html:link>
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
                              <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                <tr class="OraTableColumnHeaderModule">
                                 
                                 <td >
                                  <html:text size="100%"  property='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>'>
                                    maxlength="<%= FormConstants.LONG_NAME_MAX_LENGTH %>"
                                  </html:text>
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
                            <td class="OraFieldText" nowrap align="left" >
                              <bean:message key="cadsr.formbuilder.form.longName" />
                            </td>                       
                            <td class="OraFieldText">
                               : <cde:questionAltText questionBeanId= "question" 
                                                    htmlObjectRef='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>'
                                                    deProperty = "longName"
                                                    formIndex="0"
                                                    questionIndex="<%=questionIndex%>" /> 
                                
                            </td>
                        </tr>
                      </logic:present>
                      <logic:present name="question" property="dataElement.longCDEName">
                      <tr class="OraTabledata">
                          <td class="OraFieldText" nowrap align="left">
                            <bean:message key="cadsr.formbuilder.form.longCDEName" />
                          </td>                      
                          <td class="OraFieldText">
                             : <cde:questionAltText questionBeanId= "question" 
                                                  htmlObjectRef='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>'
                                                  deProperty = "longCDEName"
                                                  formIndex="0"
                                                  questionIndex="<%=questionIndex%>" /> 
                          </td>
                      </tr>
                       <tr class="OraTabledata">
                          <td class="OraFieldText" nowrap align="left">
                            &nbsp;
                          </td> 
                          <td class="OraFieldText" nowrap align="left">
                            &nbsp;
                          </td>                           
                      </tr>                      
                      </logic:present>                      
                      </logic:present>
                       <tr class="OraTabledata">
                          <td class="OraFieldText" nowrap align="left">
                            <bean:message key="cadsr.formbuilder.question.orginalValue" />
                          </td>                      
                          <td class="OraFieldText">
                             : <cde:questionAltText questionBeanId= "question" 
                                                  htmlObjectRef='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>'
                                                  questionProperty = "longName"
                                                  deProperty = "longName"
                                                  orgModuleBeanId= '<%=FormConstants.CLONED_MODULE%>'
                                                  formIndex="0"
                                                  questionIndex="<%=questionIndex%>" /> 
                          </td>
                      </tr>                        
                      <logic:present name="question">
                      <logic:empty name="question" property="validValues">                           
                          <tr class="OraTabledata">
                            <td class="OraTabledata" width="50">&nbsp;</td>
                            <td class="OraTabledata" align="right" width="90%">                                                                        
                                <table width="79%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                  <tr class="OraTabledata" >
                                   <td >&nbsp;</td>
                                    <td align="center">
                                       <!-- Adding from available vv list -->
                                          <td align="right"   class="OraFieldText" nowrap width="90%">    
                                            <cde:availableValidValues
                                              questionBeanId="question"
                                              availableValidValusMapId="<%=FormConstants.AVAILABLE_VALID_VALUES_MAP%>"
                                              selectClassName="FreeDropdown"
                                              selectName="<%=FormConstants.ADD_AVAILABLE_VALID_VALUE_INDEX+questionIndex%>"/>
                                          </td>
                                          <logic:present name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                           <td align="left" width="4%">
                                              <a href="javascript:submitValidValueEdit('<%=NavigationConstants.ADD_FROM_AVAILABLE_VALID_VALUE_LIST%>','<%=questionIndex%>','0')">
                                                 <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                                              </a>                          
                                            </td>
                                          </logic:present>
                                          <logic:notPresent name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                              <td class="OraTabledata" >&nbsp;</td>                                                                                                                                                                           
                                           </logic:notPresent>                                            
                                          <td class="OraTabledata" width="10">&nbsp;</td>
                                          <!-- Adding from available vv list end -->                                         
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
                                    <td class="OraTabledata" >&nbsp;</td>
                                    <td class="OraTabledata" align="right" width="90%">                                                                        
                                        <table width="79%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                          <tr class="OraTabledata" >
                                           <td >&nbsp;</td>
                                               <!-- Adding from available vv list -->
                                                  <td align="right"   class="OraFieldText" nowrap width="90%">    
                                                    <cde:availableValidValues
                                                      questionBeanId="question"
                                                      availableValidValusMapId="<%=FormConstants.AVAILABLE_VALID_VALUES_MAP%>"
                                                      selectClassName="FreeDropdown"
                                                      selectName="<%=FormConstants.ADD_AVAILABLE_VALID_VALUE_INDEX+questionIndex%>"/>
                                                  </td>
                                                  <logic:present name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                                    <td align="left" width="4%">
                                                      <a href="javascript:submitValidValueEdit('<%=NavigationConstants.ADD_FROM_AVAILABLE_VALID_VALUE_LIST%>','<%=questionIndex%>','<%=validValueIndex%>')">
                                                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                                                      </a>                          
                                                    </td>   
                                                  </logic:present>
                                                  <logic:notPresent name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                                    <td class="OraTabledata" >&nbsp;</td>                                                                                                                                                                           
                                                  </logic:notPresent>                                                    
                                                  <!-- Adding from available vv list end -->
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
                                            <td align="center">
                                              <a href="javascript:submitValidValueEdit('<%=NavigationConstants.DELETE_VALID_VALUE%>','<%=questionIndex%>','<%=validValueIndex%>')">                                              
                                               <img src="<%=urlPrefix%>i/delete.gif" border="0" alt="Delete"/>
                                              </a>
                                            </td>
                                          </tr>
                                        </table>                                                                                                            
                                    </td>
                                  </tr> 
                                  
                                <logic:equal value="<%= String.valueOf(validValueSize.intValue()-1) %>" name="validValueIndex">
                                  <tr class="OraTabledata">
                                    <td class="OraTabledata" >&nbsp;</td>
                                    <td class="OraTabledata" align="right" >                                                                        
                                        <table width="79%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                          <tr class="OraTabledata" >
                                           <td >&nbsp;</td>
                                               <!-- Adding from available vv list -->                            
                                                  <td align="right"   class="OraFieldText" nowrap width="90%">    
                                                    <cde:availableValidValues
                                                      questionBeanId="question"
                                                      availableValidValusMapId="<%=FormConstants.AVAILABLE_VALID_VALUES_MAP%>"
                                                      selectClassName="FreeDropdown"
                                                      selectName="<%=FormConstants.ADD_AVAILABLE_VALID_VALUE_INDEX+questionIndex%>"/>
                                                  </td>
                                                  <logic:present name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                                    <td align="left" width="4%">
                                                      <a href="javascript:submitValidValueEdit('<%=NavigationConstants.ADD_FROM_AVAILABLE_VALID_VALUE_LIST%>','<%=questionIndex%>','<%=validValueSize%>')">
                                                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                                                      </a>                          
                                                    </td>   
                                                  </logic:present>
                                                  <logic:notPresent name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                                    <td class="OraTabledata" >&nbsp;</td>                                                                                                                                                                           
                                                  </logic:notPresent>                                                    
                                                  <!-- Adding from available vv list end -->
                                          </tr>
                                         </table>                                                                                                            
                                    </td>
                                  </tr>                                                                  
                                 </logic:equal>                                  
                                </logic:iterate> 

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
                  <%
                    params.put(FormConstants.QUESTION_INDEX, questionSize);
                    %>
                    <html:link action='<%="/gotoAddQuestion?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_ADD_QUESTION%>'
                      name="params" 
                      scope="page"
                      >
                      
                      <html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add Question"/>
                  </html:link>&nbsp;
                </td>
              </tr>
              </table> 
            <!-- Add for delete and new Question end -->  
            </logic:equal>             
            </logic:iterate>          
        </logic:notEmpty>
      </logic:present>
      <%@ include file="/formbuilder/moduleEditButton_inc.jsp"%>
    </html:form>
    <%@ include file="/common/common_bottom_border.jsp"%>
  </BODY>
  <html:javascript formName="moduleEditForm"/>
</HTML>
