 <%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>

<%@ page import="gov.nih.nci.ncicb.cadsr.html.* "%>

<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormJspUtil"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util.CDEDetailsUtils"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.resource.Form"%>
<%@ page import="java.util.*"%>
 
 <HTML>
  <HEAD>
    <TITLE>Formbuilder: Manage module repetitions</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/jsLib/checkbox.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript">
    
    
    
function submitToRepetitions(methodName) {
        document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
        document.forms[0].submit();
        return true;
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


function submitModuleListEdit(methodName) {
   var selectedItems = '<%=FormConstants.SELECTED_ITEMS%>';
   var selectedElements = selectedItems;
   var selectedSize = getSelectedNumber(selectedElements);
  if (validateSelection(selectedElements,'Please select at least one Module to delete'))
   {
        document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
        document.forms[0].submit();
        return true;
 
    }
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
  
  

function populateDefaultValue(defaultValidValue,defaultValidValueId, index){
    var objForm0 = document.forms[0];
    var objQuestionDefaultValue = objForm0['<%=FormConstants.QUESTION_DEFAULTS%>[' + index + ']'];
    var objQuestionDefaultValidValueId = objForm0['<%=FormConstants.QUESTION_DEFAULT_VV_IDS%>[' + index + ']'];
    objQuestionDefaultValue.value = defaultValidValue;
    objQuestionDefaultValidValueId.value = defaultValidValueId;
}
</SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = request.getContextPath();
     int dummyInstructionDisplayCount = 3;

%>

    <html:form action="/moduleRepeatAction.do">
      <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      <html:hidden property="<%=FormConstants.MODULE_INDEX%>"/> 
      
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Manage&nbsp;repetitions&nbsp;"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

   <%@ include file="/formbuilder/manageModuleRepetition_inc.jsp"%>

    <%@ include file="showMessages.jsp" %>
      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Selected Module</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>
        <logic:present name="<%=FormConstants.MODULE%>"> 
             <bean:define id="module" name="<%=FormConstants.MODULE%>" />
                   <%@ include file="/formbuilder/commonModuleDetails_inc.jsp"%>
        </logic:present>
        
    <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td align="center">
          
       <table width="100%" align="center" cellpadding="0" cellspacing="5" border="0" >
        <tr >
          <td class="OraHeaderSubSub" align="left">
		Repetitions
          </td>

          <td align="left"  width="2%" >
            <html:text 
              property="<%= FormConstants.NUMBER_OF_MODULE_REPETITIONS %>"
              size="2"
              maxlength="4"
              />
            </td>		
          </td>          
          <td align="left"  width="5%" >
             <a href="javascript:submitToRepetitions('<%=NavigationConstants.ADD_REPETITIONS%>')" >                                              
                  <html:img src='<%=urlPrefix+"/i/add_button.gif"%>' border="0" alt="Add repetitions"/>
             </a>              
          </td>
         
          <td align="left"  width="5%">
              <a href="javascript:submitToRepetitions('<%=NavigationConstants.DELETE_REPETITIONS%>')" >                                              
                 <img src="<%=urlPrefix%>/i/deleteButton.gif" border="0" alt="Delete"/>
             </a>            
          </td>  

        </tr>
      </table>            
          
          </td>
          
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>

              

            <logic:notEmpty name="<%=FormConstants.MODULE_REPETITIONS%>" >
             <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
                 </tr >
                    <td width="10%" align="left" >
                     <a href="javascript:CheckAll('<%= FormConstants.SELECTED_ITEMS%>')">Check All
                     </a>
                   </td>
                   <td width="95%" align="left">
                     <a href="javascript:ClearAll('<%= FormConstants.SELECTED_ITEMS%>')">Clear All
                     </a>     
                   </td>
                </tr>
              </table> 
       
              <br>
              <br>      
               <%
                            int defaultIndex = 0; //used for default Index
                %>
              <logic:iterate id="module" name="<%=FormConstants.MODULE_REPETITIONS%>" type="gov.nih.nci.ncicb.cadsr.resource.Module" indexId="modIndex" >                            
 		<table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >               
                 <tr> 
                
                    <td  >
                       <table width="100%" align="center" cellpadding="0" cellspacing="01" border="0" class="OraBGGrayVeryDark"> 
                         <tr><td class="OraBGGrayLight">
                           <INPUT TYPE=CHECKBOX NAME="<%= FormConstants.SELECTED_ITEMS%>" value="<%=modIndex %>">
                         </td></tr>
                        </table>
                    </td>
                    <td width="97%">
                       &nbsp;
                    </td>                     
                  </tr> 
                 </table>
                 
                 <%@ include file="/formbuilder/editRepeatModuleDetails_inc.jsp"%>

      		<table width="90%" align="center" cellpadding="0" cellspacing="0" border="0" >
        	   <tr class>
          	      <td >
			              &nbsp;
          	      </td>
        	   </tr> 
        	</table>  
                
              </logic:iterate><!-- Module-->
            </logic:notEmpty>  
            <logic:empty name="<%=FormConstants.MODULE_REPETITIONS%>" >
               <br>
               <table width="80%" align="center"  border="0"  >
                 <tr >
                  
                  <td class="MessageText" width="10%" nowrap>
                  <b>
                    This module has no repetitions
                   </b>
                 </td>
                </tr>
               </table>             
            </logic:empty>            
      <%@ include file="/formbuilder/manageModuleRepetition_inc.jsp"%>         
   </html:form>
    <%@ include file="/common/common_bottom_border.jsp"%>
  </BODY>
</HTML>            