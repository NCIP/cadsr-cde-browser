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
<%@ page import="gov.nih.nci.ncicb.cadsr.resource.*"%>
<%@ page import="java.util.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.*"%>
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

function manageProtocols() {
  document.forms[0].action= '<%=request.getContextPath()+"/gotoManageProtocolsFormEdit.do?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.GOTO_MANAGE_PROTOCOLS_FORM_EDIT%>'
  document.forms[0].submit();
}

function submitModuleRepition(methodName,moduleIndexValue) {
  if(validateFormEditForm(formEditForm)) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.MODULE_INDEX%>.value=moduleIndexValue;
  document.forms[0].action='<%=request.getContextPath()%>/saveFormModuleRepeatAction.do'; 
  document.forms[0].submit();
  }
}
      
-->
<% 



 String urlPrefix = "";
    String startIndex="0";
    pageContext.setAttribute("startIndex", startIndex); 
  String contextPath = request.getContextPath();
  urlPrefix = contextPath+"/";
  
  String pageUrl = "&PageId=DataElementsGroup";
  // HSK
  // To jum to the correct location on the screen
  String jumpto = (String)request.getAttribute(CaDSRConstants.ANCHOR);
  String jumptoStr ="";
  
  if(jumpto!=null)
    jumptoStr = "onload=\"location.hash='#"+jumpto+"'\"";  
    
  String protoLOVUrl= 
    "javascript:newWin('"+contextPath+"/formLOVAction.do?method=getProtocolsLOV&idVar=protocolIdSeq&chkContext=true&nameVar=protocolLongName"+pageUrl+"','protoLOV',700,600)";

%>
</SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff" <%=jumptoStr%>>


      
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
              <bean:message key="cadsr.formbuilder.form.protocols.longName"/>
            </td>
            <td class="OraFieldText">
           <bean:define name="<%=FormConstants.CRF%>" property="protocols" id="protocols"/>
            <%=FormJspUtil.getDelimitedProtocolLongNames((List)protocols,  "<br/>")%>                
            <br/>
            <a href="javascript:manageProtocols()"><i>Manage Protocols</i></a> 
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
              <td class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.publicID" /></td>  
              <td class="OraFieldText" nowrap>
                <bean:write  name="<%=FormConstants.CRF%>" property="publicId"/> 
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
              <td  class="OraFieldText" width="80%" >
                <html:textarea  styleClass="OraFieldTextInstruction" rows="2" cols="102" 
                   property="<%=FormConstants.FORM_HEADER_INSTRUCTION%>">
                </html:textarea>
              </td>            
            </tr>   
            
            <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                <bean:message key="cadsr.formbuilder.form.footer.instruction"/>
              </td>
              <td  class="OraFieldText" width="80%" >
                <html:textarea  styleClass="OraFieldTextInstruction" rows="2" cols="102" 
                   property="<%=FormConstants.FORM_FOOTER_INSTRUCTION%>">
                </html:textarea>
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
                  <td width="0">
                    &nbsp;
                  </td>  
                </logic:empty>                        
                <td align="right" width="205"> 
                  <html:link action='<%="/formbuilder/copyFromModuleList.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GOTO_COPY_FROM_MODULE_LIST%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="startIndex" >      
                     Copy Module from module cart
                  </html:link>		  
                </td>   
                <td align="right" width="160">
                  <html:link action='<%="/formbuilder/moduleSearch.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_MODULE_SEARCH%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="startIndex" > 
                     Copy module from a form
                  </html:link>		   
                </td>  
                <td align="right" width="80">
                  <html:link action='<%="/gotoCreateModule?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_CREATE_MODULE%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="startIndex" >
                    <!-- html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add New Module"/ -->         
                     Create new
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

            <!-- and anchor -->
            <A NAME="<%="M"+moduleIndex%>"></A>
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
                
                <td align="right" width="205"> 
                  <html:link action='<%="/formbuilder/copyFromModuleList.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GOTO_COPY_FROM_MODULE_LIST%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="moduleIndex" >      
                     Copy Module from module cart
                  </html:link>		  
                </td>   
                <td align="right" width="160">
                  <html:link action='<%="/formbuilder/moduleSearch.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_MODULE_SEARCH%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="moduleIndex" > 
                     Copy module from a form
                  </html:link>		   
                </td>  
                <td align="right" width="80">
                  <html:link action='<%="/gotoCreateModule?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_CREATE_MODULE%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="moduleIndex" >
                    <!-- html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add New Module"/ -->         
                     Create new
                  </html:link>
                </td>                 
                       
              </tr>
             
              </table> 
            <!-- Add for delete and new Module end -->             

            <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
              <tr class="OraTableColumnHeader">
                <td class="OraTableColumnHeader">
                  <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                    <tr class="OraHeaderBlack">
                      <td width="65%">
                        <bean:write name="module" property="longName"/>
                      </td>
                      <td align="right">
                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                            <tr class="OraTableColumnHeader">
                              <td align="center">
                                  <a href="javascript:submitModuleRepition('<%=NavigationConstants.CHECK_CHANGES_MODULE_EDIT%>','<%=moduleIndex%>')">
                                     Manage repetition
                                  </a>                                    
                                </td>                              
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
                                             <td  class="OraTableColumnHeader" width="10%" nowrap >
                                                 <bean:message key="cadsr.formbuilder.valueMeaning.name" /></td>
                                             <td class="OraFieldText" >
                                                <bean:write name="validValue" property="shortMeaning"/></td>                                          
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
                                        </td>
                                      </tr>
                                      <!-- vv skip Pattern -->
                                      <logic:present name="validValue" property = "triggerActions" >
			              <logic:notEmpty name="validValue" property = "triggerActions">
                                      
                                      <tr   class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td >	
				          <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
					    <logic:iterate id="currTriggerAction" name="validValue" type="gov.nih.nci.ncicb.cadsr.resource.TriggerAction" property="triggerActions" indexId="triggerIndex" >
						<%@ include file="/formbuilder/skipPatternDetailsView_inc.jsp"%>
					    </logic:iterate>
					  </table>

                                         </td>
                                        </tr>
				       </logic:notEmpty>
				       </logic:present>                                         
                                       <!-- vv Skip pattern end -->                                        
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
                <!-- Module skip Pattern -->
                 <logic:present name="module" property = "triggerActions" >
                   <logic:notEmpty name="module" property = "triggerActions">
                            <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
                              <logic:iterate id="currTriggerAction" name="module" type="gov.nih.nci.ncicb.cadsr.resource.TriggerAction" property="triggerActions" indexId="triggerIndex" >
                             	<%@ include file="/formbuilder/skipPatternDetailsView_inc.jsp"%>
                              </logic:iterate>
                            </table>
                    </logic:notEmpty>
                 </logic:present>
                 <!-- Module Skip pattern end --> 
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
                
                <td align="right" width="205"> 
                  <html:link action='<%="/formbuilder/copyFromModuleList.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GOTO_COPY_FROM_MODULE_LIST%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="moduleSize" >     
                     Copy Module from module cart
                  </html:link>		  
                </td>   
                <td align="right" width="160">
                  <html:link action='<%="/formbuilder/moduleSearch.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_MODULE_SEARCH%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="moduleSize" > 
                     Copy module from a form
                  </html:link>		   
                </td>  
                <td align="right" width="80">
                  <html:link action='<%="/gotoCreateModule?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_CREATE_MODULE%>'
                       paramId="<%=FormConstants.DISPLAY_ORDER%>" paramName="moduleSize" >
                    <!-- html:img src='<%=urlPrefix+"i/new.gif"%>' border="0" alt="Add New Module"/ -->         
                     Create new
                  </html:link>
                </td>                               
              </tr>
              </table> 
            <!-- Add for delete and new Module end -->  
            </logic:equal>
          </logic:iterate>

        </logic:notEmpty>   
 
           <br>
    <!-- skip pattern end -->        
      </logic:present>
     <%@ include file="/formbuilder/editButton_inc.jsp"%>
    </html:form>

    <%@ include file="/common/common_bottom_border.jsp"%>

    <html:javascript formName="formEditForm"/>
   
  </BODY>
</HTML>
