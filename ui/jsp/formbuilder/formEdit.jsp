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
    <TITLE>Formbuilder: Edit Form </TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
     document.forms[0].submit();
}

function submitFormEdit(methodName,moduleIndexValue) {
  if(validateFormEditForm(formEditForm)) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.MODULE_INDEX%>.value=moduleIndexValue;
  document.forms[0].submit();
  }
}
function submitFormToSave(methodName) {
  if(validateFormEditForm(formEditForm)) {
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
    String startIndex="0";
    pageContext.setAttribute("startIndex", startIndex); 
  String contextPath = request.getContextPath();
  String pageUrl = "&PageId=DataElementsGroup";
  // HSK

  String protoLOVUrl= 
    "javascript:newWin('"+contextPath+"/formLOVAction.do?method=getProtocolsLOV&idVar=protocolIdSeq&chkContext=true&nameVar=protocolLongName"+pageUrl+"','protoLOV',700,600)";

%>

      
    <html:form action="/formSaveAction.do">
     <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
     <html:hidden value="" property="<%=FormConstants.MODULE_INDEX%>"/>
      <%@ include file="../common/in_process_common_header_inc.jsp"%>
      <jsp:include page="../common/tab_inc.jsp" flush="true">
        <jsp:param name="label" value="Edit&nbsp;Form"/>
        <jsp:param name="urlPrefix" value=""/>
      </jsp:include>
    <table>
        <tr>    
          <td align="left" class="AbbreviatedText">
            <bean:message key="cadsr.formbuilder.helpText.form.edit"/>
          </td>
        </tr>  
    </table> 

      <%@ include file="/formbuilder/editButton_inc.jsp"%>
    <%@ include file="showMessages.jsp" %>

    
      <logic:present name="<%=FormConstants.CRF%>">
       <html:hidden property="<%=FormConstants.FORM_ID_SEQ%>"/>

      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Form Header</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>       
       
        <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.longName"/>
            </td>
            <td  class="OraFieldText" width="80%" nowrap>
              <html:text size="100" property="<%=FormConstants.FORM_LONG_NAME%>"
                 maxlength="<%= Integer.toString(FormConstants.LONG_NAME_MAX_LENGTH)%>">
             </html:text>
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%">
              <bean:message key="cadsr.formbuilder.form.definition"/>
            </td>
            <td  class="OraFieldText" width="80%" >
              <html:textarea  styleClass="OraFieldText" rows="3" cols="102" property="<%=FormConstants.PREFERRED_DEFINITION%>"></html:textarea>
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.context"/>
            </td>
            <td class="OraFieldText" nowrap>
              <html:select styleClass="Dropdown" property="<%=FormConstants.CONTEXT_ID_SEQ%>" >               
                <html:options collection="<%=CaDSRConstants.USER_CONTEXTS%>" property="conteIdseq" labelProperty="name"/>
              </html:select>
            </td>
          </tr>
          
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.protocol"/>
            </td>
            <td class="OraFieldText" nowrap>
              <html:text property="<%=FormConstants.PROTOCOLS_LOV_NAME_FIELD%>" 
                readonly="true"
                size="19"
                styleClass="LOVField"/>
                  <a href="<%=protoLOVUrl%>">
                <img src="<%=urlPrefix%>i/blankSearchLight.gif" border="0" alt="Search for Protocol Items"/>
              </a> <a href="javascript:clearProtocol()"><i>Clear</i></a> 
              <html:hidden property="<%=FormConstants.PROTOCOL_ID_SEQ%>"/>
            </td>
          </tr>

          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.workflow" />
            </td>
            <td class="OraFieldText" nowrap>
            <html:select styleClass = "FreeDropdown" property="<%=FormConstants.WORKFLOW%>">        	
               <html:options name="<%=FormConstants.ALL_WORKFLOWS%>"/>
              </html:select>        
            </td>   
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.category" />
            </td>
            <td class="OraFieldText" nowrap>
              <html:select styleClass = "Dropdown" property="<%=FormConstants.CATEGORY_NAME%>">              
                <html:option value=""/>
                <html:options name="<%=FormConstants.ALL_FORM_CATEGORIES%>" /> 
              </html:select> 
            </td>
          </tr>         
          <tr class="OraTabledata" >
              <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.type" /></td>  
              <td class="OraFieldText" nowrap>
               <html:select styleClass = "Dropdown" property="<%=FormConstants.FORM_TYPE%>"> 
                  <html:options name="<%=FormConstants.ALL_FORM_TYPES%>" /> 
               </html:select> 
              </td>        
          </tr> 
          <tr class="OraTabledata" >
              <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.question.version" /></td>  
              <td class="OraFieldText" nowrap>
                <bean:write  name="<%=FormConstants.CRF%>" property="version"/> 
              </td>        
          </tr>   
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.header.instruction"/>
            </td>
            <td  class="OraFieldTextInstruction" width="80%" nowrap>
		          <TEXTAREA NAME="comments" COLS=79 ROWS=2></TEXTAREA>
            </td>
          </tr>          
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.footer.instruction"/>
            </td>
            <td  class="OraFieldTextInstruction" width="80%" nowrap>
             <TEXTAREA NAME="comments" COLS=79 ROWS=2></TEXTAREA>
            </td>
          </tr> 
                    
        </table>
      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Form Details</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>        
        <!-- If the Modules Collection is empty and deleted modules Exists -->

            <logic:empty name="<%=FormConstants.CRF%>" property="modules">
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">        
              <tr align="right">
                <logic:notEmpty name="<%=FormConstants.DELETED_MODULES%>">
                  <td align="right"   class="OraFieldText" nowrap>    
                      <html:select styleClass="Dropdown" property="<%=FormConstants.ADD_DELETED_MODULE_IDSEQ%>">
                        <html:options collection="<%=FormConstants.DELETED_MODULES%>" 
                            property="moduleIdseq" labelProperty="longName" />
                      </html:select >
                  </td>
                  <td align="left" width="25">
                      <a href="javascript:submitFormEdit('<%=NavigationConstants.ADD_FROM_DELETED_LIST%>','0')">
                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                      </a>                          
                  </td>   
                  </logic:notEmpty>
                  <logic:empty name="<%=FormConstants.DELETED_MODULES%>">
                  <td >
                    &nbsp;
                  </td>  
                </logic:empty>                        
                <td align="right" width="25">
                  <html:link action='<%="/gotoCreateModule?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_CREATE_MODULE%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="startIndex" >
                    <html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add New Module"/>
                  </html:link>
                </td>
              </tr>               
              </table> 
              </logic:empty>
            <!-- Add for delete and new Module end -->            
        
        
        <!-- If the Modules Collection is empty and deleted modules Exists end -->
         
        <logic:notEmpty name="<%=FormConstants.CRF%>" property="modules">
          <logic:iterate id="module" indexId="moduleIndex" name="<%=FormConstants.CRF%>" type="gov.nih.nci.ncicb.cadsr.resource.Module" property="modules">
            <bean:size id="moduleSize" name="<%=FormConstants.CRF%>" property="modules"/>            
            
            <!-- Add for delete and new Module -->
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">        
              <tr align="right">
                <logic:notEmpty name="<%=FormConstants.DELETED_MODULES%>">
                  <td align="right"   class="OraFieldText" nowrap>    
                      <html:select styleClass="Dropdown" property="<%=FormConstants.ADD_DELETED_MODULE_IDSEQ%>">
                        <html:options collection="<%=FormConstants.DELETED_MODULES%>" 
                            property="moduleIdseq" labelProperty="longName" />
                      </html:select >
                  </td>
                  <td align="left" width="25">
                      <a href="javascript:submitFormEdit('<%=NavigationConstants.ADD_FROM_DELETED_LIST%>','<%=moduleIndex%>')">
                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                      </a>                          
                  </td>   
                  </logic:notEmpty>
                  <logic:empty name="<%=FormConstants.DELETED_MODULES%>">
                  <td >
                    &nbsp;
                  </td>  
                </logic:empty>                        
                <td align="right" width="25">
                  <html:link action='<%="/gotoCreateModule?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_CREATE_MODULE%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="moduleIndex" >
                    <html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add New Module"/>
                  </html:link>&nbsp;
                </td>
              </tr>
             
              </table> 
            <!-- Add for delete and new Module end -->             
            
            <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
              <tr class="OraTableColumnHeader">
                <td class="OraTableColumnHeader">
                  <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                    <tr class="OraHeaderBlack">
                      <td width="86%">
                        <bean:write name="module" property="longName"/>
                      </td>
                      <td align="right">
                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                            <tr class="OraTableColumnHeader">
                             <td align="center">
                                <logic:notEqual value="<%= String.valueOf(moduleSize.intValue()-1) %>" name="moduleIndex">
                                  <a href="javascript:submitFormEdit('<%=NavigationConstants.MOVE_MODULE_DOWN%>','<%=moduleIndex%>')">
                                     <img src=<%=urlPrefix%>i/down.gif border=0 alt="Down">
                                  </a>                                  
                                </logic:notEqual>
                             </td>
                             <td align="center">
                                <logic:notEqual value="<%= String.valueOf(0) %>" name="moduleIndex">
                                  <a href="javascript:submitFormEdit('<%=NavigationConstants.MOVE_MODULE_UP%>','<%=moduleIndex%>')">
                                     <img src=<%=urlPrefix%>i/up.gif border=0 alt="Up">
                                  </a>                           
                                </logic:notEqual> 
                              </td>
                              <td align="center">
                                  <a href="javascript:submitFormEdit('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>','<%=moduleIndex%>')">
                                     <img src=<%=urlPrefix%>i/edit.gif border=0 alt="Edit">
                                  </a>                                    
                                </td>
                                <td align="center">
                                    <a href="javascript:submitFormEdit('<%=NavigationConstants.DELETE_MODULE%>','<%=moduleIndex%>')">
                                       <img src=<%=urlPrefix%>i/delete.gif border=0 alt="Delete">
                                    </a>
                                </td>
                              </tr>
                           </table>
                      </td>
                    </tr>
                    <tr>  
                     <td colspan="2">
                       <table align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                         <tr class="OraTabledata">
                          <td class="OraTableColumnHeader" width="10%" nowrap>
                            <bean:message key="cadsr.formbuilder.form.instruction"/> 
                         </td>
                         <td class="OraFieldTextInstruction">
                           Please submit at each follow up after completion of treatment until recurrence, at time of recurrence, and at protocol specified intervals after recurrence. All dates are MONTH, DAY, YEAR.
                         </td>
                        </tr>
                       </table>
                      </td>
		    </tr>
                  </table>
                </td>
              </tr>
              <logic:present name="module">
                <logic:notEmpty name="module" property="questions">
                  <tr class="OraTabledata">
                    <td>                
                        <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraTabledata">      
                          <logic:iterate id="question" name="module" type="gov.nih.nci.ncicb.cadsr.resource.Question" property="questions">                           
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="50">&nbsp;</td>
                              <td height="1"  class="OraFieldText">                               
                              </td>                              
                            </tr>                             
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="7%">&nbsp;</td>
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
                            <tr class="OraTabledata">
                               <td class="OraFieldText" width="50">&nbsp;</td>
                                <td class="OraFieldText" colspan="2">                              
                                 <table align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                   <tr class="OraTabledata">
                                    <td class="OraTableColumnHeader" width="10%" nowrap>
                                      <bean:message key="cadsr.formbuilder.form.instruction"/>
                                   </td>
                                   <td class="OraFieldTextInstruction">
                                     Please submit at each follow up after completion of treatment until recurrence, at time of recurrence, and at protocol specified intervals after recurrence. All dates are MONTH, DAY, YEAR.
                                   </td>
                                  </tr>
                                 </table>                                                            
                               </td>
                             </tr> 


                            <logic:present name="question">
                            <logic:notEmpty name="question" property = "validValues">
                              <tr class="OraTabledata">
                                <td class="OraFieldText" width="50">&nbsp;</td>
                                <td colspan="2">
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                    <logic:iterate id="validValue" name="question" type="gov.nih.nci.ncicb.cadsr.resource.FormValidValue" property="validValues">
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
                                          <table align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark" >
                                             <tr class="OraTabledata">
                                              <td class="OraTableColumnHeader" width="10%" nowrap>
                                                <b>ValueMeaning</b> 
                                             </td>
                                             <td class="OraFieldText">
                                                &nbsp;
                                             </td>
                                            </tr>
                                             <tr class="OraTabledata">
                                              <td class="OraTableColumnHeader" width="10%" nowrap>
                                                <bean:message key="cadsr.formbuilder.form.instruction"/> 
                                             </td>
                                             <td class="OraFieldTextInstruction">
                                               Please submit at each follow up after completion of treatment until recurrence, at time of recurrence, and at protocol specified intervals after recurrence. All dates are MONTH, DAY, YEAR.
                                             </td>
                                            </tr>                        
                                          </table>                                       
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
                          </logic:iterate><!-- Question-->
                        </table>                      
                                           
                    </td>
                  </tr>
                </logic:notEmpty>
              </logic:present>
            </table>         
            <logic:equal value="<%= String.valueOf(moduleSize.intValue()-1) %>" name="moduleIndex">
            <!-- Add for delete and new Module -->
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">        
              <tr align="right">
                <logic:notEmpty name="<%=FormConstants.DELETED_MODULES%>">
                  <td align="right"   class="OraFieldText" nowrap>    
                      <html:select styleClass="Dropdown" property="<%=FormConstants.ADD_DELETED_MODULE_IDSEQ%>">
                        <html:options collection="<%=FormConstants.DELETED_MODULES%>" 
                            property="moduleIdseq" labelProperty="longName" />
                      </html:select >
                  </td>
                  <td align="left" width="25">
                      <a href="javascript:submitFormEdit('<%=NavigationConstants.ADD_FROM_DELETED_LIST%>','<%=moduleSize%>')">
                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                      </a>                          
                  </td>   
                </logic:notEmpty>
                <logic:empty name="<%=FormConstants.DELETED_MODULES%>">
                <td >
                  &nbsp;
                </td>  
                </logic:empty>                        
                <td align="right" width="25">
                  <html:link action='<%="/gotoCreateModule?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_CREATE_MODULE%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="moduleSize" >
                    <html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add New Module"/>
                  </html:link>&nbsp;
                </td>
              </tr>
              </table> 
            <!-- Add for delete and new Module end -->  
            </logic:equal>
          </logic:iterate>
           
        </logic:notEmpty>        
      </logic:present>
     <%@ include file="/formbuilder/editButton_inc.jsp"%>
    </html:form>
    <%@ include file="/common/common_bottom_border.jsp"%>

    <html:javascript formName="formEditForm"/>
  </BODY>
</HTML>
