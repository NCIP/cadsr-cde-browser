<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.html.* "%>
<%@ page import="java.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.util.* "%> 
<%@ page import="gov.nih.nci.ncicb.cadsr.resource.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.jsp.tag.handler.AvailableValidValue"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util.CDEDetailsUtils"%>

<HTML>
  <HEAD>
    <TITLE>Formbuilder: Edit Module</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/jsLib/checkbox.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript">
<!--

function populateDefaultValue(defaultValidValue,defaultValidValueId, index){
    var objForm0 = document.forms[0];
    var objQuestionDefaultValue = objForm0['questionDefaultValues[' + index + ']'];
    var objQuestionDefaultValidValueId = objForm0['questionDefaultValidValueIds[' + index + ']'];

    if (defaultValidValueId == '<%=FormConstants.UNKNOWN_VV_ID%>'){
        alert("Please save the module before setting the question default value.");
        return;
    }
    objQuestionDefaultValue.value = defaultValidValue;
    objQuestionDefaultValidValueId.value = defaultValidValueId;
}


function submitForm() {
  document.forms[0].submit();
}

function submitValidValueEdit(methodName,questionIndexValue,validValueIndexValue) {
  if (methodName=='<%=NavigationConstants.DELETE_VALID_VALUE%>' || methodName=='<%=NavigationConstants.DELETE_VALID_VALUES%>'){
	  if (isValidValueQuestionDefault(questionIndexValue,validValueIndexValue)==true){
	    alert("One of the Valid Value is used as the question default value. Please clear the default value of this quesiton first.");
	    return;
	  }
  }	  
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndexValue;
  document.forms[0].<%=FormConstants.VALID_VALUE_INDEX%>.value=validValueIndexValue;
  document.forms[0].submit();
}

function isValidValueQuestionDefault(questionIndexValue,validValueIndexValue){
    var objForm0 = document.forms[0];
    var objQuestionDefaultValidValueId = objForm0['questionDefaultValidValueIds[' + questionIndexValue+ ']'];
    
    var objDeletedValidValueIds = objForm0['<%=FormConstants.SELECTED_ITEMS+"H"%>' + questionIndexValue];
    
    var objDeletedValidValueId = objDeletedValidValueIds[validValueIndexValue];
    
    
    //alert("objDeletedValidValueId.value=" + objDeletedValidValueId.value);
    //alert("objQuestionDefaultValidValueId.value=" + objQuestionDefaultValidValueId.value);
    if (objDeletedValidValueId.value==objQuestionDefaultValidValueId.value){
        return true;
    }else{
        return false;    
    }    
}



function getSelectedNumber(selectedElements){
  var len = selectedElements.length; 
  var i=0;
  var total = 0;
  for( i=0; i<len ; i++) {
    if (selectedElements[i].checked==1) {
    	total = total + 1;
    }	
  }
  return total;
}


function submitValidValuesEdit(methodName,questionIndexValue) {
   var objForm0 = document.forms[0];
   var selectedElements = objForm0['<%=FormConstants.SELECTED_ITEMS%>' + questionIndexValue];
   var selectedSize = getSelectedNumber(selectedElements);
   
   if (selectedSize==0 && methodName=='<%=NavigationConstants.DELETE_VALID_VALUES%>'){
   	alert('Please select at least one valid value to delete');
   	return;
   }

   if(selectedSize>1)
      {
      	  //verify each valid value is used as default
	  var len = selectedElements.length; 
	  var i=0;
	  for( i=0; i<len ; i++) {
	   if (selectedElements[i].checked==1) {
	      if (methodName=='<%=NavigationConstants.DELETE_VALID_VALUE%>' || methodName=='<%=NavigationConstants.DELETE_VALID_VALUES%>' ){
		if (isValidValueQuestionDefault(questionIndexValue, i) ){  
	          alert("One of the Valid Value is used as the question default value. Please clear the default value of this quesiton first.");
	    	  return;
	    	}  
	      }
	   } 
	  }
      
        document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
        document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndexValue;
        document.forms[0].submit();
        return true;
      }
      else
      {
	  var selectedIndexValue;
	  var len = selectedElements.length; 
	  var i=0;
	  for( i=0; i<len ; i++) {
	   if (selectedElements[i].checked==1) {
	     selectedIndexValue = i;
	   } 
	  }
        submitValidValueEdit(methodName,questionIndexValue,selectedIndexValue);
        return;
      }
 }
 
function submitModuleEdit(methodName,questionIndexValue) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndexValue;
  document.forms[0].submit();
}

function submitToSubsets(methodName,questionIndexValue) {
  document.forms[0].<%=FormConstants.QUESTION_INDEX%>.value=questionIndexValue;
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();  
}
function submitModuleToSave(methodName) {
  if(validateModuleEditForm(moduleEditForm)) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();
  }
}

function submitModuleForModuleSkipCreate(methodName) {
  if(validateModuleEditForm(moduleEditForm)) {
  document.forms[0].action='<%=request.getContextPath()%>/createModuleSkipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();
  }  
}

function submitModuleForValidValueSkipCreate(methodName,questionIndex,validValueIndex) {
  if(validateModuleEditForm(moduleEditForm)) {
  document.forms[0].action='<%=request.getContextPath()%>/createValidValueSkipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.SK_QUESTION_INDEX%>.value=questionIndex;
  document.forms[0].<%=FormConstants.SK_VALID_VALUE_INDEX%>.value=validValueIndex;
  document.forms[0].submit();
  }  
}
function submitModuleForModuleSkipEdit(methodName,triggerIndex) {
  if(validateModuleEditForm(moduleEditForm)) {
  document.forms[0].action='<%=request.getContextPath()%>/editModuleSkipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.TRIGGER_ACTION_INDEX%>.value=triggerIndex;
  document.forms[0].submit();
  }  
}

function submitModuleForValidValueSkipEdit(methodName,questionIndex,validValueIndex,triggerIndex) {
  if(validateModuleEditForm(moduleEditForm)) {
  document.forms[0].action='<%=request.getContextPath()%>/editValidValueSkipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.SK_QUESTION_INDEX%>.value=questionIndex;
  document.forms[0].<%=FormConstants.SK_VALID_VALUE_INDEX%>.value=validValueIndex;
  document.forms[0].<%=FormConstants.TRIGGER_ACTION_INDEX%>.value=triggerIndex;
  document.forms[0].submit();
  }  
}

function submitForDeleteSkipModule(methodName,triggerIndex) {

  document.forms[0].action='<%=request.getContextPath()%>/formbuilder/skipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.TRIGGER_ACTION_INDEX%>.value=triggerIndex;
  document.forms[0].submit();
}
function submitForDeleteSkipValidValue(methodName,questionIndex,validValueIndex,triggerIndex) {

  document.forms[0].action='<%=request.getContextPath()%>/formbuilder/skipAction.do'; 
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].<%=FormConstants.SK_QUESTION_INDEX%>.value=questionIndex;
  document.forms[0].<%=FormConstants.SK_VALID_VALUE_INDEX%>.value=validValueIndex;  
  document.forms[0].<%=FormConstants.TRIGGER_ACTION_INDEX%>.value=triggerIndex;
  document.forms[0].submit();
}
function clearProtocol() {
  document.forms[0].protocolIdSeq.value = "";
  document.forms[0].protocolLongName.value = "";
}


  function switchAll(e)
  {
	if (e.checked) {
	    CheckAll();
	}
	else {
	    ClearAll();
	}
  }
  function Check(e)
    {
	e.checked = true;
    }

  function Clear(e)
    {
	e.checked = false;
    }  

  function CheckAll(checkSetName)
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
	    if (e.name == checkSetName) {
		Check(e);
	    }
	}

    }
  
  function ClearAll(checkSetName)
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
            if (e.name == checkSetName) {
		Clear(e);
	    }
	}
    }

  function validate(fo) {
	var len = fo.elements.length;
        var oneChecked = false;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
            if (e.name == "<%= FormConstants.SELECTED_ITEMS %>") {
              if(e.checked == true) {
                 oneChecked=true;
                 i = len;
              }
            }            
        }    
        if(oneChecked == true) {
          return true;
        } else {
          alert("Please Select at least one Data Element");
          return false;
        }
  }
  
  function refDocSelected(srcCompId,targetCompId)
    {
        var targetObj = document.getElementById(targetCompId);
         
         var srcObj = document.getElementById(srcCompId);
         var i;
         var count = 0;
         for (i=0; i<srcObj.options.length; i++) {
           if (srcObj.options[i].selected) {
              targetObj.value = srcObj.options[i].value;
             }
           }
     }
  
  function refDocHyperlink(targetCompId,newValue)
    {
        var targetObj = document.getElementById(targetCompId);
        targetObj.value=newValue;
       
     }  
     
  function submitChangeAsso(url, questionIndexValue){
    var objForm0 = document.forms[0];
    var objQuestionDefaultValidValueId = objForm0['questionDefaultValidValueIds[' + questionIndexValue+ ']'];
    if (objQuestionDefaultValidValueId.value !=''){
    	alert('Please clear the default value of this question and save the module before change CDE association');
    	return
    } 	
   window.location = url;
  }

-->
<%

  // To jum to the correct location on the screen
  String jumpto = (String)request.getAttribute(CaDSRConstants.ANCHOR);
  String jumptoStr ="";
  
  if(jumpto!=null)
    jumptoStr = "onload=\"location.hash='#"+jumpto+"'\"";  
 %>
</SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff" <%=jumptoStr%> >
    <% 
      String contextPath = request.getContextPath();
      String urlPrefix = contextPath+"/";

      // Prepare parameter map for add and edit linx
      java.util.Map params = new java.util.HashMap();
      params.put(FormConstants.MODULE_INDEX, request.getParameter(FormConstants.MODULE_INDEX));
      // params.put(FormConstants.QUESTION_INDEX, request.getParameter(FormConstants.QUESTION_INDEX));

      pageContext.setAttribute("params", params); 
      
      String moduleIndex = (String)(request.getParameter(FormConstants.MODULE_INDEX));

      %>
    <html:form action="/moduleSaveAction.do">
      <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      <html:hidden property="<%=FormConstants.FORM_ID_SEQ%>"/>
      <html:hidden property="<%=FormConstants.QUESTION_ID_SEQ%>"/>
      <html:hidden property="<%=FormConstants.QUESTION_INDEX%>"/> 
      <html:hidden property="<%=FormConstants.VALID_VALUE_INDEX%>"/>
      <html:hidden property="<%=FormConstants.SK_QUESTION_INDEX%>"/>
      <html:hidden property="<%=FormConstants.SK_VALID_VALUE_INDEX%>"/>
      <html:hidden property="<%=FormConstants.TRIGGER_ACTION_INDEX%>"/>
      <html:hidden property="<%=FormConstants.MODULE_INDEX%>"/>
      
      
      <%@ include file="../common/in_process_common_header_inc.jsp"%>
      <jsp:include page="../common/tab_inc.jsp" flush="true">
        <jsp:param name="label" value="Edit&nbsp;Module"/>
        <jsp:param name="urlPrefix" value=""/>
      </jsp:include>
  <table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.form.module.edit"/>
      </td>
    </tr>  
  </table>       
      <%@ include file="/formbuilder/moduleEditButton_inc.jsp"%>
        <%@ include file="showMessages.jsp" %>
    
      <logic:present name="<%=FormConstants.MODULE%>">
      <table  width="80%">
        <tr>
           <td>&nbsp;</td>
        </tr>
        <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.name"/>
            </td>
            <td class="OraFieldText" >
              <bean:write  name="<%=FormConstants.CRF%>" property="longName"/> 
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.publicID"/>
            </td>
            <td class="OraFieldText" >
              <bean:write  name="<%=FormConstants.CRF%>" property="publicId"/> 
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.question.version"/>
            </td>
            <td class="OraFieldText" >
              <bean:write  name="<%=FormConstants.CRF%>" property="version"/> 
            </td>
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
                  maxlength="<%= Integer.toString(FormConstants.LONG_NAME_MAX_LENGTH) %>">
              </html:text>
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="20%" nowrap>
              <bean:message key="cadsr.formbuilder.form.instruction"/>
            </td>        
            <td  class="OraFieldText" width="80%" >
              <html:textarea  styleClass="OraFieldTextInstruction" rows="2" cols="102" 
                 property="<%=FormConstants.MODULE_INSTRUCTION%>">                   
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
             <%
               int vvInstrIndex = 0; //used for instruction Index
             %>
            <logic:iterate id="question" name="module" indexId="questionIndex" type="gov.nih.nci.ncicb.cadsr.resource.Question" property="questions">
             <bean:size id="questionSize" name="module" property="questions"/>
            <!-- and anchor -->
            <A NAME="<%="Q"+questionIndex%>"></A>            
        <!-- If the Question Collection is empty and deleted Questions Exists -->
             <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">                   
              <tr align="right">
                <logic:notEmpty name="<%=FormConstants.DELETED_QUESTIONS%>">
                  <td align="right"   class="OraFieldText" nowrap width="90%">    
                      <html:select styleClass="FreeDropdown" property="<%=FormConstants.ADD_DELETED_QUESTION_IDSEQ_ARR%>">
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
                                     <a href="javascript:submitChangeAsso('<%= "/CDEBrowser/gotoChangeAssociation.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_CHANGE_DE_ASSOCIATION + "&questionIndex=" + questionIndex + "&moduleIndex="+moduleIndex%>', '<%=questionIndex%>')">
                                      <img src="<%=urlPrefix%>i/association.gif" border="0" alt="Change CDE Association"/>
                                      </a>
<%--

                                    <html:link action='<%= "/gotoChangeAssociation?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GO_TO_CHANGE_DE_ASSOCIATION%>'
                                      name="params"
                                      scope="page"
                                      >
                                      <img src="<%=urlPrefix%>i/association.gif" border="0" alt="Change CDE Association"/>
                                    </html:link>
--%>                                    
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
                          <tr class="OraHeaderBlack">
                            
                            <td align="right">
                              <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                <tr class="OraHeaderBlack">                                
                                <logic:notPresent name="question" property="dataElement">
                                 <td >
                                  <html:textarea  styleClass="OraFieldText" rows="2" cols="102" property='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>'>
                                 </html:textarea>
                                 </td>        
                                </logic:notPresent>
                                <logic:present name="question" property="dataElement">
                                 <td >
                                  <html:textarea  styleClass="OraFieldText" rows="2" cols="102" property='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>' readonly="true">
                                 </html:textarea>
                                 </td>        
                                  <td class="OraHeaderBlack" align="center" width="70" >
                                   <html:link page='<%="/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>' 
                                      paramId="p_de_idseq" paramName="question" paramProperty="dataElement.deIdseq" target="_blank">
                                    <bean:write name="question" property="dataElement.CDEId"/>
                                   </html:link>
                                  </td>                        
                                  <td class="OraHeaderBlack" align="center" width="70" >
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
                      <tr><td colspan="2">
                      <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
                
                        <tr class="OraTabledata">
                            <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                              <bean:message key="cadsr.formbuilder.form.instruction"/> 
                            </td>                      
                            <td class="OraFieldTextInstruction">
                               <html:textarea   rows="2" cols="67" 
                                    property='<%=FormConstants.QUESTION_INSTRUCTIONS+"["+questionIndex+"]"%>'>
                                </html:textarea>                               
                            </td>
                        </tr> 
                      <logic:present name="question" property="dataElement">                       
                       <tr class="OraTabledata">
                          <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                            <bean:message key="cadsr.formbuilder.cde.workflow" />
                          </td>                      
                          <td class="OraFieldText">
                              <bean:write name="question" property="dataElement.aslName"/>
                          </td>
                      </tr> 
                      <tr class="OraTabledata">
                          <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                             <bean:message key="cadsr.formbuilder.helpText.moduleEdit.altText.preferred" />
                          </td>                      
                          <td class="OraFieldText">
                              <cde:RefDocAltQuestionTextDisplay questionBeanId= "question" 
                                                  htmlObjectRef='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>'
                                                  selectBoxClassName="AltQuestionField"
                                                  selectBoxSize="4"
                                                  refDocType="<%=ReferenceDocument.REF_DOC_TYPE_PREFERRED_QUESTION_TEXT%>"
                                                  questionIndex="<%=questionIndex.toString()%>" 
                                                  selectBoxJSFunctionName="refDocSelected"
                                                  hyperLinkJSFunctionName="refDocHyperlink"
                                                  /> 
                          </td>                          
                      </tr>    
                      <tr class="OraTabledata">
                          <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                             <bean:message key="cadsr.formbuilder.helpText.moduleEdit.altText.other" />
                          </td>                      
                          <td class="OraFieldText">
                              <cde:RefDocAltQuestionTextDisplay questionBeanId= "question" 
                                                  htmlObjectRef='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>'
                                                  selectBoxClassName="AltQuestionField"
                                                  selectBoxSize="4"
                                                  refDocType="<%=ReferenceDocument.REF_DOC_TYPE_ALT_QUESTION_TEXT%>"
                                                  questionIndex="<%=questionIndex.toString()%>" 
                                                  selectBoxJSFunctionName="refDocSelected"
                                                  hyperLinkJSFunctionName="refDocHyperlink"
                                                  /> 
                          </td>                          
                      </tr>                                                
                      </logic:present>                        
                       <tr class="OraTabledata">
                          <td width="28%" class="OraTableColumnHeader" nowrap align="left">
                            <bean:message key="cadsr.formbuilder.helpText.moduleEdit.altText.orgQuestion" />
                          </td>                      
                          <td class="OraFieldText">
                              <cde:questionAltText questionBeanId= "question" 
                                                  htmlObjectRef='<%=FormConstants.MODULE_QUESTIONS+"["+questionIndex+"]"%>'
                                                  questionProperty = "longName"
                                                  deProperty = "longName"
                                                  orgModuleBeanId= '<%=FormConstants.CLONED_MODULE%>'
                                                  formIndex="0"
                                                  questionIndex="<%=questionIndex.toString()%>" /> 
                          </td>
                      </tr>  
                        <tr class="OraTabledata">
                            <td width="26%" class="OraTableColumnHeader" nowrap align="left">
                              Default value 
                            </td>                      
                            <td class="OraFieldText">
                            <logic:notEmpty name="question" property="validValues">
                            <html:text property='<%=FormConstants.QUESTION_DEFAULTVALUES+"["+questionIndex+"]"%>' readonly="true" size="70"/>
                            <a href="javascript:populateDefaultValue('','', '<%=questionIndex%>')">
			               Clear
			    </a>                          

                            <html:hidden property='<%=FormConstants.QUESTION_DEFAULT_VALIDVALUE_IDS+"["+questionIndex+"]"%>'/>
                            </logic:notEmpty>    
                            <logic:empty name="question" property="validValues">                                    
                              <html:hidden property='<%=FormConstants.QUESTION_DEFAULT_VALIDVALUE_IDS+"["+questionIndex+"]"%>' />
                              <html:text property='<%=FormConstants.QUESTION_DEFAULTVALUES+"["+questionIndex+"]"%>' size="70"/>
                            </logic:empty>    
                            </td>
                        </tr>                       



<%--  value domain details--%>
			   <logic:present name="question" property="dataElement">
                            <logic:present name="question" property="dataElement.valueDomain">
                         	<tr class="OraTabledata">
                                  <td class="OraTableColumnHeader" colspan="2">                              
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.valueDomainDetails"/>        
                                     </td>                                     
                                    </tr>
                                    
                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader" width="26%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.longName"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.longName"/>          
                            
                                     </td>
                                    </tr>
                                    
                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader" width="26%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.datatype"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.datatype"/>          
                            
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader" width="26%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.unitofmeasure"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.unitOfMeasure"/>     
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata">
                                     <td class="OraTableColumnHeader" width="26%">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.displayFormat"/>
                                     </td>
                                     <td class="OraFieldText">
                                       <bean:write  name="question" property="dataElement.valueDomain.displayFormat"/>     
                                     </td>
                                    </tr>

                                    <tr class="OraTabledata" width="26%">
                                     <td class="OraTableColumnHeader">
                                        <bean:message key="cadsr.formbuilder.form.valueDomain.concepts"/>
                                     </td>
                                     <td class="OraFieldText">
                                       
<%=CDEDetailsUtils.getConceptCodesUrl(question.getDataElement().getValueDomain().getConceptDerivationRule(),CDEBrowserParams.getInstance(),"link",",")%>
                                     </td>
                                    </tr>
                            </logic:present>
                           </logic:present>
<%-- end of value domain details--%>


                      </table>
                      </td></tr>
                      
                      <logic:present name="question">
                      <!-- Empty ValidValues -->
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
                                              selectName="<%=FormConstants.ADD_AVAILABLE_VALID_VALUE_INDEX+questionIndex+0%>"/>
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
                      <!-- ValidValues not Empty -->
                      <logic:notEmpty name="question" property="validValues">                           
                          <tr class="OraTabledata">
                            
                            <td  colspan="2" width="90%">
                              <table width="100%" align="center" cellpadding="1" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                  <tr class="OraTabledata">
                                    <td class="OraTabledata" >&nbsp;</td>
                                    <td class="OraTabledata" align="left" width="90%"> 
                                       <table width="60%" align="left" cellpadding="0" cellspacing="0" border="0" >
                                         </tr >
                                            <td width="16%" align="left" >
                                             <a href="javascript:CheckAll('<%= FormConstants.SELECTED_ITEMS+questionIndex %>')">Check All
                                             </a>
                                           </td>
                                           <td width="16%" align="left">
                                             <a href="javascript:ClearAll('<%= FormConstants.SELECTED_ITEMS+questionIndex %>')">Clear All
                                             </a>     
                                           </td>
                                           <td align="left" width="5%">
                                              <a href="javascript:submitValidValuesEdit('<%=NavigationConstants.DELETE_VALID_VALUES%>','<%=questionIndex%>')">                                              
                                               <img src="<%=urlPrefix%>i/delete.gif" border="0" alt="Delete"/>
                                              </a>
                                            </td>
                                            <!-- Subset Uncomment when working on subsets for CDEs
                                            <td align="left">
                                             <a href="javascript:submitToSubsets('<%=NavigationConstants.VIEW_SUBSETTEDVDS_LIST%>','<%=questionIndex %>')">
                                                 Select from existing subsets
                                             </a>
                                            </td>
                                            Subset end -->
                                        </tr>
                                      </table>
                                    </td>                               
                                <logic:iterate id="validValue" name="question" indexId="validValueIndex" type="gov.nih.nci.ncicb.cadsr.resource.FormValidValue" property="validValues">
                                <bean:size id="validValueSize" name="question" property="validValues"/>                                  
                                  <tr class="OraTabledata">
                                    <td class="OraTabledata" >&nbsp;</td>
                                    <td class="OraTabledata" align="right" width="90%">                                                                        
                                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                          <tr class="OraTabledata" >
                                           <td align="left" >
                                              <INPUT TYPE=CHECKBOX NAME="<%= FormConstants.SELECTED_ITEMS+questionIndex%>" value="<%= validValueIndex %>">
                                              <INPUT TYPE=hidden NAME='<%= FormConstants.SELECTED_ITEMS+"H"+questionIndex%>' value='<%= validValue.getValueIdseq()%>'>
                                              </td>
                                               <!-- Adding from available vv list -->
                                                  
                                                  <td align="right"   class="OraFieldText" nowrap width="90%">    
                                                    <cde:availableValidValues
                                                      questionBeanId="question"
                                                      availableValidValusMapId="<%=FormConstants.AVAILABLE_VALID_VALUES_MAP%>"
                                                      selectClassName="FreeDropdown"
                                                      selectName="<%=FormConstants.ADD_AVAILABLE_VALID_VALUE_INDEX+questionIndex+validValueIndex%>"/>
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
                                    <td class="OraTabledata" width="10%">&nbsp;</td>
                                    <td class="OraFieldText" align="right" width="90%">                                                                        
                                        <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                          <tr class="OraHeaderBlack" >
                                           <td class="OraFieldText" width="86%">
                                          <bean:write name="validValue" property="longName"/>
                                          <a href="javascript:populateDefaultValue('<%=validValue.getLongName()%>','<%=validValue.getValueIdseq()%>', '<%=questionIndex%>')">
                                             Set as Question Default Value
                                          </a>                          
                                           </td>
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
                                            <tr class="OraFieldText" >
                                              <td colspan="4"> 
                                                 <table width="100%" align="right" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
                                                   <logic:present name="question" property="dataElement">                                                
                                                      <tr class="OraTabledata" >
                                                       <td  class="OraTableColumnHeader" ><bean:message key="cadsr.formbuilder.valueMeaning.name" /></td>
                                                       <td class="OraFieldText" ><bean:write name="validValue" property="shortMeaning"/></td>                                          
                                                      </tr>
                                                   </logic:present >  
                                                    <tr class="OraTabledata" >
                                                     <td  class="OraTableColumnHeader" >
                                                        <bean:message key="cadsr.formbuilder.form.instruction"/>
                                                     </td>
                                                     <td class="OraFieldTextInstruction" >
                                                         <html:textarea   rows="2" cols="70" 
                                                              property='<%=FormConstants.FORM_VALID_VALUE_INSTRUCTIONS+"["+vvInstrIndex+"]"%>'>
                                                          </html:textarea>                                                              
                                                     </td>                                          
                                                    </tr>                
  
                                                  </table>
                                               </td>
                                           </tr>
                                                <!-- vv skip Pattern -->
                                                <tr class="OraTabledata" >
                                                    
                                                            <td colspan="4" align="right">
                                                            <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
								
								 <logic:present name="validValue" property = "triggerActions" >
								   <logic:notEmpty name="validValue" property = "triggerActions">
									      <logic:iterate id="currTriggerAction" name="validValue" type="gov.nih.nci.ncicb.cadsr.resource.TriggerAction" property="triggerActions" indexId="triggerIndex" >
											<%@ include file="/formbuilder/skipPatternDetailsEditVV_inc.jsp"%>
									      </logic:iterate>
								    </logic:notEmpty>
								 </logic:present>
								                                                             
                                                            </table>
                                                           </td>
                                                    </tr>
                                                  <!-- vv Skip pattern end -->    
                                                  <tr class="OraTabledata" >                                                         
                                                   <td colspan="4" align=right>

                                                         <a href="javascript:submitModuleForValidValueSkipCreate('<%=NavigationConstants.CHECK_MODULE_CHANGES%>','<%=questionIndex%>','<%=validValueIndex%>')"> 
                                                            Add Skip pattern                                                            
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
                                                      selectName="<%=FormConstants.ADD_AVAILABLE_VALID_VALUE_INDEX+questionIndex+validValueSize%>"/>
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
                                  <logic:present name="<%=AvailableValidValue.AVAILABLE_VALID_VALUE_PRESENT%>">
                                  <tr class="OraTabledata" >
                                       <td class="OraTabledata" >&nbsp;</td>
                                       <td colspan="2" align="left" class="AbbreviatedText">
                                       <logic:present name="question" property="dataElement">
                                     <bean:message key="cadsr.formbuilder.helpText.moduleEdit.vd.validvalue.lov"/>
                                       </logic:present>
                                       <logic:notPresent name="question" property="dataElement">
                                     <bean:message key="cadsr.formbuilder.helpText.moduleEdit.validvalue.deleted.lov"/>
                                       </logic:notPresent>					       
                                       </td>
                                   </tr>
                                 </logic:present>
                                  
                                 </logic:equal>  
                                 <%
                                    ++vvInstrIndex;
                                 %>
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
                  <td align="right"   class="OraFieldText" nowrap width="23%">    
                      <html:select styleClass="FreeDropdown" property="<%=FormConstants.ADD_DELETED_QUESTION_IDSEQ_ARR%>">
                        <html:options collection="<%=FormConstants.DELETED_QUESTIONS%>" 
                            property="quesIdseq" labelProperty="longName" />
                      </html:select >
                  </td>
                  <td align="left" width="1%">
                      <a href="javascript:populateDefaultValue('<%=FormConstants.QUESTION_DEFAULT_VALIDVALUE_IDS+"[" + questionIndex +"]"%>',<%=FormConstants.QUESTION_DEFAULTVALUES+"[" + questionIndex +"]"%>, '<%=questionIndex%>')">
                         <img src=<%=urlPrefix%>i/add.gif border=0 alt="Add">
                      </a>                          
                  </td>   
                  </logic:notEmpty>
                  <logic:empty name="<%=FormConstants.DELETED_QUESTIONS%>">
                   <td width="20%">
                    &nbsp;
                   </td>  
                  </logic:empty>               
                 <td align="right" width="1%">
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
        
                <!-- Module skip Pattern -->
                 <logic:present name="module" property = "triggerActions" >
                   <logic:notEmpty name="module" property = "triggerActions">
                            <table width="84%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
                              <logic:iterate id="currTriggerAction" name="module" type="gov.nih.nci.ncicb.cadsr.resource.TriggerAction" property="triggerActions" indexId="triggerIndex" >
					<%@ include file="/formbuilder/skipPatternDetailsEditModule_inc.jsp"%>
                                             
                              </logic:iterate>
                            </table>
                    </logic:notEmpty>
                 </logic:present>
                 <!-- Module Skip pattern end --> 
                 
           
               <table width="79%" align="center" cellpadding="0" cellspacing="0" border="0">     
                <tr>
                <td align="right">
                 <a href="javascript:submitModuleForModuleSkipCreate('<%=NavigationConstants.CHECK_MODULE_CHANGES%>')"> 
                    Add Skip pattern                                                            
                 </a>    &nbsp;
                </td>
               </tr>
              </table>
           <br>        
      </logic:present>
      <%@ include file="/formbuilder/moduleEditButton_inc.jsp"%>
    </html:form>
    <%@ include file="/common/common_bottom_border.jsp"%>
  </BODY>
  <html:javascript formName="moduleEditForm"/>

</HTML>
