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

function submitModuleEdit(methodName) {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
  document.forms[0].submit();
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


-->
</SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">          
    <html:form action="/addQuestionSubsetted.do">
    <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
    <html:hidden property="<%= FormConstants.QUESTION_INDEX %>"/>
      <%@ include file="../common/in_process_common_header_inc.jsp"%>
      <jsp:include page="../common/tab_inc.jsp" flush="true">
        <jsp:param name="label" value="Add&nbsp;Question"/>
        <jsp:param name="urlPrefix" value=""/>
      </jsp:include>
    <table>
        <tr>    
          <td align="left" class="AbbreviatedText">
            <bean:message key="cadsr.formbuilder.helpText.form.module.addquestion.subsetValidValues"/>
          </td>
        </tr>         
    </table> 
    <%@ include file="showMessages.jsp" %>
    <logic:present name="selectedDataElements">    
     <logic:notEmpty name="selectedDataElements">
     <%@ include file="addQuestionSubsetValidValues_inc.jsp" %>
       <logic:iterate id="selectedDataElement" name="<%=FormConstants.SELECTED_DATAELEMENTS%>"
            indexId="cdeIndex"
            type="gov.nih.nci.ncicb.cadsr.resource.DataElement">
          <bean:size id="selectedSize" name="<%=FormConstants.SELECTED_DATAELEMENTS%>" />
            
          <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">                      
                <tr class="OraTableColumnHeaderModule">
                  <td>
                    <bean:write name="selectedDataElement" property="longName"/>
                  </td>
                  <td align="center" width="70" class="OraFieldText">
                      <html:link page='<%="/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>' 
                         paramId="p_de_idseq" paramName="selectedDataElement" paramProperty="deIdseq" target="_blank">
                        <bean:write name="selectedDataElement" property="CDEId"/>
                      </html:link>
                    </td>
                   <td align="center" width="70" class="OraFieldText">
                      <bean:write name="selectedDataElement" property="version"/>
                   </td>                                                     
                </tr>
                <tr class="OraTabledata">
                  <td colspan=3 class="OraTabledata">
                    <a href="javascript:CheckAll('<%= FormConstants.SELECTED_ITEMS+cdeIndex%>')">Check All</a>&nbsp;-&nbsp;<a href="javascript:ClearAll('<%= FormConstants.SELECTED_ITEMS+cdeIndex%>')">Clear All</a>
                  </td>
                </tr>            
               
                <logic:notEmpty name="selectedDataElement" property="valueDomain.validValues">
                   <tr>
                    <td colspan="3">
                    <logic:iterate id="validValue" name="selectedDataElement" type="gov.nih.nci.ncicb.cadsr.resource.ValidValue" property="valueDomain.validValues" indexId="itemId">                     
                      <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">                        
                        <tr>
                          <td colspan="2" class="OraTabledata">&nbsp;</td>                       
                        </tr>
                        <tr>
                          <td class="OraTabledata" width="50">
                             <INPUT TYPE=CHECKBOX NAME="<%= FormConstants.SELECTED_ITEMS+cdeIndex%>" value="<%= itemId %>">
                             
                          </td>
                          <td>
                            <table width="100%" align="center" cellpadding="1" cellspacing="0" border="0" class="OraBGAccentVeryDark">                        
                             <tr ><td>                       
                               <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">                        
                                 <tr >
                                  <td colspan="2" class="OraTableColumnHeaderVVNOBold" width="100%">
                                    <bean:write name="validValue" property="shortMeaningValue"/>
                                  </td>
                                 </tr>
                                 <tr>
                                  <td width="30%" class="OraTableColumnHeaderVV" >
                                    <bean:message key="cadsr.formbuilder.valueMeaning.name" />
                                  </td>
                                  <td class="OraFieldTextVV" >
                                     :<bean:write name="validValue" property="shortMeaning"/>
                                  </td>                                          
                                </tr>                         
                               </table>
                              </td></tr>
                             </table>
                            </td></tr>
                           </table>                       
                     </logic:iterate><!-- valid Value-->    
                     
                  </td></tr>             
                </logic:notEmpty>     
                <tr class="OraTabledata">
                  <td colspan=3 class="OraTabledata">
                    <a href="javascript:CheckAll('<%= FormConstants.SELECTED_ITEMS+cdeIndex%>')">Check All</a>&nbsp;-&nbsp;<a href="javascript:ClearAll('<%= FormConstants.SELECTED_ITEMS+cdeIndex%>')">Clear All</a>
                  </td>
                </tr>              
          </table>
          <logic:notEqual value="<%= String.valueOf(selectedSize.intValue()-1) %>" name="cdeIndex">          
          <table cellpadding="0" cellspacing="0" width="80%" align="center">
            <tr >
              <td >
                &nbsp;
              </td>
            </tr>         
          </table>            
          </logic:notEqual>
      </logic:iterate>
     <%@ include file="addQuestionSubsetValidValues_inc.jsp" %>
    </logic:notEmpty>  
    </logic:present>
    
    </html:form>
    <%@ include file="/common/common_bottom_border.jsp"%>

  </BODY>
</HTML>
