

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>


<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.struts.common.BrowserFormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.struts.common.BrowserNavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.common.FormBuilderConstants" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util.CDECompareJspUtils" %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util.CDEDetailsUtils" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<HTML>
<HEAD>
<TITLE>CDEBrowser  Compare CDEs</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/jsLib/checkbox.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/jsLib/newWinJS.js"/></SCRIPT> 
<SCRIPT LANGUAGE="JavaScript">
<!--
<%
  CDEBrowserParams params = CDEBrowserParams.getInstance();

%>
function removeFromCompareList() {
  if (validateSelection('<%=BrowserFormConstants.CDE_TO_REMOVE%>','Please select at least one data element to remove from the list')) {
    document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value="<%=BrowserNavigationConstants.REMOVE_FROM_CDE_COMPARE_LIST%>";    
    document.forms[0].action='<%=request.getContextPath()%>/cdebrowser/removeFromCompareListAction.do';      
    document.forms[0].submit();
    return true;
  }
}
function changeDisplayOrder() {
    document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value="<%=BrowserNavigationConstants.CHANGE_COMPARE_ORDER%>";    
    document.forms[0].action='<%=request.getContextPath()%>/cdebrowser/changeCompareOrder.do';  
    document.forms[0].submit();
    return true;
}
function done() {
    document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value="<%=BrowserNavigationConstants.DONE_CDE_COMPARE%>";    
    document.forms[0].submit();
    return true;
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

  function CheckAll()
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
	    if (e.name == "<%=BrowserFormConstants.CDE_TO_REMOVE%>") {
		Check(e);
	    }
	}
    }
  
  function ClearAll()
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
            if (e.name == "<%=BrowserFormConstants.CDE_TO_REMOVE%>") {
		Clear(e);
	    }
	}
    }


-->

</SCRIPT>
</HEAD>
<BODY bgcolor="#ffffff" topmargin="0">
    <html:form action="/cdebrowser/doneCompareListAction">
      <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      <html:hidden property="src"/>
      <html:hidden property="<%=FormConstants.MODULE_INDEX%>"/>
      <html:hidden property="<%=FormConstants.QUESTION_INDEX%>"/>
      
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="right" width="1%" colspan=1><A HREF="javascript:newBrowserWin('<%=request.getContextPath()%>/common/help/cdeBrowserHelp.html','helpWin',700,600)"><IMG SRC="<%=request.getContextPath()%>/i/icon_help.gif" alt="Task Help" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>         
        </TR>
      </TABLE>
      <bean:size id="listSize" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" property="cdeList"/> 
      
      <jsp:include page="../common/tab_variable_length_inc.jsp" flush="true">
        <jsp:param name="label" value="Compare&nbsp;CDEs"/>
        <jsp:param name="width" value="100" />
      </jsp:include>
     
      <%@ include file="compareCDEs_inc.jsp"%>
      <%@ include file="../formbuilder/showMessages.jsp" %>     
     
 
      <table cellpadding="0" cellspacing="0" width="90%" align="center" border=0>
         <tr >
           <td>&nbsp;</td>
         </tr>
         <tr >
            <td  ><a class="link" href="#dataElement">Data Element</a></td>
            <td  ><a class="link" href="#dataElementConcept">Data Element Concept</a></td>
            <td  ><a class="link" href="#valueDomain">Value Domain</a></td>
            <td  ><a class="link" href="#permissibleValues">Permissible Values</a></td>
            <td  ><a class="link" href="#referenceDocuments">Reference Documents</a></td>  
            <td  ><a class="link" href="#classifications">Classifications</a></td>
            <td  ><a class="link" href="#data_element_derivation">Data Element Derivation</a></td>    
         </tr>
         <tr >
           <td>&nbsp;</td>
         </tr>         
      </table>

      <A NAME="dataElement"></A> 
            
      <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align="center">
        <tr>
          <td class="OraHeaderSubSub" width="100%">Data Element</td>
        </tr>
        <tr height='2' >
           <td width="100%" ><img height=2 src="i/beigedot.gif" width="99%" align=top border=1> </td>
        </tr>  
      </table>   


     <table cellpadding="0" cellspacing="0" align="center" valign="top" border="0">
        <tr>
          <td class="OraHeaderSubSub" width="<%=CDECompareJspUtils.getHeaderSize()%>">&nbsp;
            <table cellpadding="0" cellspacing="0" align="center" valign="top" border="0">
             <tr>
               <td width="75" ><a href="javascript:CheckAll()">Check All</a></td>
               <td width="75" ><a href="javascript:ClearAll()">Clear All</a></td>
             </tr>
           </table>    
          </td> 
          <!-- For each CDE display the Checkbox and the Display order drop down -->
          <logic:notEmpty name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" property = "cdeList">
           <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
              <td>
                <table cellpadding="0" cellspacing="0" align="left" valign="top" border="0" width="<%=CDECompareJspUtils.getColumnSize()%>">
                 <tr>
                   <td><input type="checkbox"  value="<%=cdeIndex%>" name="<%=BrowserFormConstants.CDE_TO_REMOVE%>"  /></td>
                   <td>
                   <cde:displayOrderSelection
                          collectionSize="<%=listSize.toString()%>" 
                          currentIndex="<%=cdeIndex.toString()%>" 
                          selectName="<%=BrowserFormConstants.CDE_COMPARE_DISPAY_ORDER%>"
                          selectClassName="Dropdown"                         
                   />                     
                   </td>
                 </tr>
                </table>     
              </td>
             </logic:iterate>
             </tr>
            <!--DataElement details Start-->
           <table width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
               <tr class="OraTabledata">
                <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Public ID</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width="<%=CDECompareJspUtils.getColumnSize()%>" >
                              <html:link page='<%="/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>'
                                                paramId = "p_de_idseq"
                                                paramName="currCDE"
                                                paramProperty="deIdseq"
                                                target="_blank">
                                <bean:write name="currCDE" property="CDEId"/>
                              </html:link>
                          </td>
                   </logic:iterate>             
             </tr> 
              <tr class="OraTabledata">
                <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Long Name</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width="<%=CDECompareJspUtils.getColumnSize()%>">
                           <bean:write name="currCDE" property="longName"/>
                         </td>
                   </logic:iterate>    
              </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Short Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width="<%=CDECompareJspUtils.getColumnSize()%>" >
                             <bean:write name="currCDE" property="preferredName"/>
                            </td>
                    </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Document Text</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                           <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                             <bean:write name="currCDE" property="longCDEName"/>
                           </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Definition</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                           <bean:write name="currCDE" property="preferredDefinition"/>               
                          </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Owned by Context</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                              <bean:write name="currCDE" property="contextName"/> 
                             </td>
                     </logic:iterate> 
               </tr> 
                <tr class="OraTabledata">
                   <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Used by Context</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                              <bean:write name="currCDE" property="usingContexts"/> 
                             </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Origin</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                             <bean:write name="currCDE" property="origin"/>                           
                            </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>">Workflow Status</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>
                              <bean:write name="currCDE" property="aslName"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap>Registration Status</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                           <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                            <bean:write name="currCDE" property="registrationStatus"/> 
                           </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Version</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                              <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                              <bean:write name="currCDE" property="version"/>
                              </td>
                      </logic:iterate> 
               </tr>
            </table>    
            <!--DataElement details End-->
            <!--DEC details Start-->
             <br>
            <A NAME="dataElementConcept"></A>
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Data Element Concept</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table> 
            
           <table width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
             <tr class="OraTabledata">
                <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Public ID</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>' >
                                <bean:write name="currCDE" property="dataElementConcept.publicId"/>
                          </td>
                   </logic:iterate>             
             </tr> 
              <tr class="OraTabledata">
                <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Long Name</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>' >
                           <bean:write name="currCDE" property="dataElementConcept.longName"/>
                         </td>
                   </logic:iterate>    
              </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Short Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>' >
                             <bean:write name="currCDE" property="dataElementConcept.preferredName"/>
                            </td>
                    </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Definition</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                           <bean:write name="currCDE" property="dataElementConcept.preferredDefinition"/>               
                          </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Context</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                              <bean:write name="currCDE" property="dataElementConcept.contextName"/> 
                             </td>
                     </logic:iterate> 
               </tr> 
                <tr class="OraTabledata">
                   <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" >Conceptual Domain Short Name</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                              <bean:write name="currCDE" property="dataElementConcept.CDPrefName"/> 
                             </td>                            
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>"  >Object Class Short Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:present name="currCDE" property = "dataElementConcept.objectClass">                             
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                             <bean:write name="currCDE" property="dataElementConcept.objectClass.preferredName"/>                           
                            </td>
                          </logic:present>
                          <logic:notPresent name="currCDE" property = "dataElementConcept.objectClass"> 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                             &nbsp;              
                            </td>                            
                          </logic:notPresent>
                     </logic:iterate> 
               </tr>             
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>"  >Object Class Concept Codes</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:present name="currCDE" property = "dataElementConcept.objectClass">                         
                              <logic:present name="currCDE" property = "dataElementConcept.objectClass.conceptDerivationRule">                             
                                <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >                                                          
                                    <%=CDEDetailsUtils.getConceptCodesUrl(currCDE.getDataElementConcept().getObjectClass().getConceptDerivationRule(),params,"link",",")%>
                                </td>
                              </logic:present>
                              <logic:notPresent name="currCDE" property = "dataElementConcept.objectClass.conceptDerivationRule"> 
                                <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                                 &nbsp;                   
                                </td>                            
                              </logic:notPresent>
                          </logic:present>
                          <logic:notPresent name="currCDE" property = "dataElementConcept.objectClass"> 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                             &nbsp;                   
                            </td>                            
                          </logic:notPresent>
                     </logic:iterate> 
               </tr>    
               
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>">Property Short Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:present name="currCDE" property = "dataElementConcept.property"> 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                             <bean:write name="currCDE" property="dataElementConcept.property.preferredName"/>                           
                            </td>
                          </logic:present>
                          <logic:notPresent name="currCDE" property = "dataElementConcept.property"> 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                             &nbsp;                   
                            </td>  
                          </logic:notPresent>
                     </logic:iterate> 
               </tr>       
               
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>"  >Property Concept Codes</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:present name="currCDE" property = "dataElementConcept.property">                         
                              <logic:present name="currCDE" property = "dataElementConcept.property.conceptDerivationRule">                             
                                <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >                                                                                
                                    <%=CDEDetailsUtils.getConceptCodesUrl(currCDE.getDataElementConcept().getProperty().getConceptDerivationRule(),params,"link",",")%>
                                </td>
                              </logic:present>
                              <logic:notPresent name="currCDE" property = "dataElementConcept.property.conceptDerivationRule"> 
                                <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                                 &nbsp;                   
                                </td>                            
                              </logic:notPresent>
                          </logic:present>
                          <logic:notPresent name="currCDE" property = "dataElementConcept.property"> 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                             &nbsp;                   
                            </td>                            
                          </logic:notPresent>
                     </logic:iterate> 
               </tr>    
    
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" >Origin</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                           <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                            <bean:write name="currCDE" property="dataElementConcept.origin"/> 
                           </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>"  >Workflow Status</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                              <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                              <bean:write name="currCDE" property="dataElementConcept.aslName"/>
                              </td>
                      </logic:iterate> 
               </tr>
            </table>    
            
            <!--DEC details End -->
            
             <!--ValueDomain details Start -->
            <br>
            <A NAME="valueDomain"></A>
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Value Domain</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>
           <table width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
             <tr class="OraTabledata">
                <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Public ID</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>' >
                                <bean:write name="currCDE" property="valueDomain.publicId"/>
                          </td>
                   </logic:iterate>             
             </tr> 
              <tr class="OraTabledata">
                <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Long Name</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>' >
                           <bean:write name="currCDE" property="valueDomain.longName"/>
                         </td>
                   </logic:iterate>    
              </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Short Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>' >
                             <bean:write name="currCDE" property="valueDomain.preferredName"/>
                            </td>
                    </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Definition</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                           <bean:write name="currCDE" property="valueDomain.preferredDefinition"/>               
                          </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" nowrap >Data Type</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                              <bean:write name="currCDE" property="valueDomain.datatype"/> 
                             </td>
                     </logic:iterate> 
               </tr> 
                <tr class="OraTabledata">
                   <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" >Unit of Measure</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                              <bean:write name="currCDE" property="valueDomain.unitOfMeasure"/> 
                             </td>                            
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>"  >Display Format</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                             <bean:write name="currCDE" property="valueDomain.displayFormat"/>                           
                            </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>">Maximum Length</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>
                              <bean:write name="currCDE" property="valueDomain.maxLength"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>">Minimum Length</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>
                              <bean:write name="currCDE" property="valueDomain.minLength"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>">Decimal Place</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>
                              <bean:write name="currCDE" property="valueDomain.decimalPlace"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>">High Value</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>
                              <bean:write name="currCDE" property="valueDomain.highValue"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>">Low Value</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>
                              <bean:write name="currCDE" property="valueDomain.lowValue"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>">Value Domain Type</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>
                              <bean:write name="currCDE" property="valueDomain.VDType"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>">Conceptual Domain Short Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>
                              <bean:write name="currCDE" property="valueDomain.CDPrefName"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>">Representation</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                      <logic:present name="currCDE" property="valueDomain.representation">
                        <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>
                          <bean:write name="currCDE" property="valueDomain.representation.longName"/>
                        </TD>
                      </logic:present>    
                      <logic:notPresent name="currCDE" property="valueDomain.representation">
                        <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>
                          &nbsp;
                        </TD>
                      </logic:notPresent>                         
                     </logic:iterate> 
               </tr>    
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>"  >Concept Codes</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:present name="currCDE" property = "valueDomain">                         
                              <logic:present name="currCDE" property = "valueDomain.conceptDerivationRule">                             
                                <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>                                                          
                                    <%=CDEDetailsUtils.getConceptCodesUrl(currCDE.getValueDomain().getConceptDerivationRule(),params,"link",",")%>
                                </td>
                              </logic:present>
                              <logic:notPresent name="currCDE" property = "valueDomain.conceptDerivationRule"> 
                                <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                                 &nbsp;                   
                                </td>                            
                              </logic:notPresent>
                          </logic:present>
                          <logic:notPresent name="currCDE" property = "valueDomain"> 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                             &nbsp;                   
                            </td>                            
                          </logic:notPresent>
                     </logic:iterate> 
               </tr>                   
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>" >Origin</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                           <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                            <bean:write name="currCDE" property="valueDomain.origin"/> 
                           </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>"  >Workflow Status</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                              <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'  >
                              <bean:write name="currCDE" property="valueDomain.aslName"/>
                              </td>
                      </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="<%=CDECompareJspUtils.getHeaderSize()%>">Version</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='<%=CDECompareJspUtils.getColumnSize()%>'>
                              <bean:write name="currCDE" property="valueDomain.version"/> 
                            </td>
                     </logic:iterate> 
               </tr>                   
            </table>             
              <!--ValueDomain details End -->
              <!--Permissible Values Start-->
            <br>
            <A NAME="permissibleValues"></A>
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Permissible Values</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>

            <table cellSpacing=0 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align=center border=0>
               <tr >
                          <td width="<%=CDECompareJspUtils.getHeaderSize()%>" >&nbsp;</td>
                          <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:empty name="currCDE" property="valueDomain.validValues">
                            <td width="<%=CDECompareJspUtils.getColumnSize()%>" class="PrinterOraFieldText" >&nbsp;</td>
                          </logic:empty>
                          <logic:notEmpty name="currCDE" property="valueDomain.validValues">
                            <bean:size id="vvSize" name="currCDE" property="valueDomain.validValues"/> 
                            <td width="<%=CDECompareJspUtils.getColumnSize()%>" class="PrinterOraFieldText" ><%=vvSize%> Permissible values</td>
                          </logic:notEmpty> 
                          </logic:iterate>
                </tr>
            </table>
            <table vAlign=top cellSpacing=2 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align=center border=0>
             	<tr>
                   <TD vAlign=top width="<%=CDECompareJspUtils.getHeaderSize()%>">
                       &nbsp;
                    </TD>             	
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                   <logic:empty name="currCDE" property="valueDomain.validValues">
                       <td vAlign=top width="<%=CDECompareJspUtils.getColumnSize()%>" class="PrinterOraFieldText" >
                          <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                            <TR class=OraTableColumnHeader>
                              <TH class=OraTableColumnHeader>Value</TH>
                              <TH class=OraTableColumnHeader>Value meaning</TH>
                              <TH class=OraTableColumnHeader>Description</TH>
                             </TR>
                             <TR class=OraTabledata>
                                <TD colspan=3>
                                  CDE does not have Permissible Values
                                </TD>                         
                              </TR> 
                          </table>
                       </td>
                   </logic:empty>
                   <logic:notEmpty name="currCDE" property="valueDomain.validValues">
                       <td vAlign=top width="<%=CDECompareJspUtils.getColumnSize()%>" class="PrinterOraFieldText" >
                          <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                            <TR class=OraTableColumnHeader>
                              <TH class=OraTableColumnHeader>Value</TH>
                              <TH class=OraTableColumnHeader>Value meaning</TH>
                              <TH class=OraTableColumnHeader>Description</TH>
                             </TR>
                             <logic:iterate id="currVV" name="currCDE" type="gov.nih.nci.ncicb.cadsr.common.resource.ValidValue" property="valueDomain.validValues" indexId="vvIndex" >                                                              
                             <TR class=OraTabledata>
                                <TD class=OraFieldText>
                                	<bean:write name="currVV" property="shortMeaningValue"/>
                                </TD>
                                <TD class=OraFieldText>
                                	<bean:write name="currVV" property="shortMeaning"/>
                                </TD>
                                <TD class=OraFieldText>
                                	<bean:write name="currVV" property="description"/>
                                </TD>                              
                              </TR> 
                              </logic:iterate>
                          </table>
                       </td>
                   </logic:notEmpty>                   
                 </logic:iterate>
             	</tr>
            </table>
              <!--Permissible Values End -->
 
               <!--Reference Doc Start-->
               
            <br>
            <A NAME="referenceDocuments"></A>
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Reference Documents</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>

            <table  cellSpacing=2 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align=center border=0>
             	<tr>
                   <TD vAlign=top width="<%=CDECompareJspUtils.getHeaderSize()%>">
                       &nbsp;
                    </TD>             	
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                   <logic:empty name="currCDE" property="refereceDocs">
                       <td vAlign=top width="<%=CDECompareJspUtils.getColumnSize()%>" class="PrinterOraFieldText" >
                          <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                            <TR class=OraTableColumnHeader>
                               <th class="OraTableColumnHeader">Document Name</th>
                               <th class="OraTableColumnHeader">Document Type</th>
                               <th class="OraTableColumnHeader">Document Text</th>
                             </TR>
                             <TR class=OraTabledata>
                                <TD colspan=3>
                                  CDE does not have Reference Document
                                </TD>                         
                              </TR> 
                          </table>
                       </td>
                   </logic:empty>
                  
                   <logic:notEmpty name="currCDE" property="refereceDocs">
                       <td vAlign=top width="<%=CDECompareJspUtils.getColumnSize()%>" class="PrinterOraFieldText" >
                          <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                            <TR class=OraTableColumnHeader>
                               <th class="OraTableColumnHeader">Document Name</th>
                               <th class="OraTableColumnHeader">Document Type</th>
                               <th class="OraTableColumnHeader">Document Text</th>
                             </TR>
                             <logic:iterate id="currRefDoc" name="currCDE" type="gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument" property="refereceDocs" indexId="rdIndex" >                                                              
                             <TR class=OraTabledata>
                                <TD class=OraFieldText>
                                	<bean:write name="currRefDoc" property="docName"/>
                                </TD>
                                <TD class=OraFieldText>
                                	<bean:write name="currRefDoc" property="docType"/>
                                </TD>
                                <TD class=OraFieldText>
                                	<bean:write name="currRefDoc" property="docText"/>
                                </TD>                                
                              </TR> 
                              </logic:iterate>
                          </table>
                       </td>
                   </logic:notEmpty>                   
                 </logic:iterate>
             	</tr>
            </table>               
               
               
               
              <!--Reference Doc Start-->               
 
              <!--classifications Start-->
            <br>
            <A NAME="classifications"></A>
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Classifications</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>

            <table  cellSpacing=2 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align=center border=0>
             	<tr>
                   <TD vAlign=top width="<%=CDECompareJspUtils.getHeaderSize()%>">
                       &nbsp;
                    </TD>             	
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                   <logic:empty name="currCDE" property="classifications">
                       <td width="<%=CDECompareJspUtils.getColumnSize()%>" vAlign=top class="PrinterOraFieldText" >
                          <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                            <TR class=OraTableColumnHeader>
                              <TH class=OraTableColumnHeader>CS* Short Name</TH>
                              <TH class=OraTableColumnHeader>CS* Definition</TH>
                              <TH class=OraTableColumnHeader>CSI* Name</TH>
                              <TH class=OraTableColumnHeader>CSI* Type</TH>
                             </TR>
                             <TR class=OraTabledata>
                                <TD colspan=4>
                                  No Classifications exists for this CDE
                                </TD>                         
                              </TR> 
                          </table>
                       </td>
                   </logic:empty>
                  
                   <logic:notEmpty name="currCDE" property="classifications">
                       <td vAlign=top width="<%=CDECompareJspUtils.getColumnSize()%>" class="PrinterOraFieldText" >
                          <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                            <TR class=OraTableColumnHeader>
                              <TH class=OraTableColumnHeader>CS* Short Name</TH>
                              <TH class=OraTableColumnHeader>CS* Definition</TH>
                              <TH class=OraTableColumnHeader>CSI* Name</TH>
                              <TH class=OraTableColumnHeader>CSI* Type</TH>
                             </TR>
                             <logic:iterate id="currCS" name="currCDE" type="gov.nih.nci.ncicb.cadsr.common.resource.Classification" property="classifications" indexId="csIndex" >                                                              
                             <TR class=OraTabledata>
                                <TD class=OraFieldText>
                                	<bean:write name="currCS" property="classSchemeName"/>
                                </TD>
                                <TD class=OraFieldText>
                                	<bean:write name="currCS" property="classSchemeDefinition"/>
                                </TD>
                                <TD class=OraFieldText>
                                	<bean:write name="currCS" property="classSchemeItemName"/>
                                </TD>  
                                <TD class=OraFieldText>
                                	<bean:write name="currCS" property="classSchemeItemType"/>
                                </TD>                                 
                              </TR> 
                              </logic:iterate>
                          </table>
                       </td>
                   </logic:notEmpty>                   
                 </logic:iterate>
             	</tr>
            </table>
              <!--classifications End -->      
              
             <!--Derivation details Start --> 
            <br>
            <A NAME="data_element_derivation"></A>
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Data Element Derivation</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>             
             
            <table cellSpacing=2 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align=center border=0>
               <tr >
                  <td width="<%=CDECompareJspUtils.getHeaderSize()%>" class="PrinterOraFieldText" >&nbsp;</td>
                  <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                       <logic:notPresent name="currCDE" property="derivedDataElement">
                           <td vAlign=top  width="<%=CDECompareJspUtils.getColumnSize()%>" class="PrinterOraFieldText" >
                              <table vAlign=top cellSpacing=0 cellPadding=0  width="100%" align=center border=0 >
                                 <TR >
                                      <td width="<%=CDECompareJspUtils.getColumnSize()%>" class="OraHeaderSubSub" >Derivation Details</td>                     
                                  </TR> 
                              </table>                           
                              <table cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                                <TR class=OraTableColumnHeader>
                                    <th class="OraTableColumnHeader">Derivation Type</th>
                                    <th class="OraTableColumnHeader">Rule</th>
                                    <th class="OraTableColumnHeader">Method</th>
                                    <th class="OraTableColumnHeader">Concatenation Character</th>  
                                 </TR>
                                 <TR class=OraTabledata>
                                    <TD colspan=4>
                                      CDE is not a derived data element
                                    </TD>                         
                                  </TR> 
                              </table>
                           </td>
                       </logic:notPresent>
                      
                       <logic:present name="currCDE" property="derivedDataElement">
                           <td vAlign=top width="<%=CDECompareJspUtils.getColumnSize()%>" class="PrinterOraFieldText" >
                              <table vAlign=top cellSpacing=0 cellPadding=0  width="100%" align=center border=0 >
                                 <TR >
                                      <td width="<%=CDECompareJspUtils.getColumnSize()%>" class="OraHeaderSubSub" >Derivation Details</td>                     
                                  </TR> 
                              </table>                             
                              <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                                <TR class=OraTableColumnHeader>
                                    <th class="OraTableColumnHeader">Derivation Type</th>
                                    <th class="OraTableColumnHeader">Rule</th>
                                    <th class="OraTableColumnHeader">Method</th>
                                    <th class="OraTableColumnHeader">Concatenation Character</th>  
                                 </TR>
                                 <TR class=OraTabledata>
                                    <logic:present name="currCDE" property="derivedDataElement.type">
                                    <TD class=OraFieldText>
                                      <bean:write name="currCDE" property="derivedDataElement.type.name"/>
                                    </TD>
                                    </logic:present>
                                    <logic:present name="currCDE" property="derivedDataElement.rule">
                                    <TD class=OraFieldText>
                                      <bean:write name="currCDE" property="derivedDataElement.rule"/>
                                    </TD>
                                    </logic:present>
                                    <logic:present name="currCDE" property="derivedDataElement.methods">
                                    <TD class=OraFieldText>
                                      <bean:write name="currCDE" property="derivedDataElement.methods"/>
                                    </TD>  
                                    </logic:present>
                                    <logic:present name="currCDE" property="derivedDataElement.concatenationCharacter">
                                    <TD class=OraFieldText>
                                      <bean:write name="currCDE" property="derivedDataElement.concatenationCharacter"/>
                                    </TD> 
                                    </logic:present>
                                  </TR> 
                              </table>
                           </td>
                       </logic:present>                    
                  </logic:iterate>
                </tr>
             </table>

            <table cellSpacing=2 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align=center border=0>
               <tr >
                  <td width="<%=CDECompareJspUtils.getHeaderSize()%>" class="PrinterOraFieldText" >&nbsp;</td>
                  <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                       <logic:notPresent name="currCDE" property="derivedDataElement">
                           <td vAlign=top  width="<%=CDECompareJspUtils.getColumnSize()%>" class="PrinterOraFieldText" >
                              <table cellSpacing=0 cellPadding=0  width="100%" align=center border=0 >
                                 <TR >
                                      <td width="<%=CDECompareJspUtils.getColumnSize()%>" class="OraHeaderSubSub" >Component Data Elements</td>                     
                                  </TR> 
                              </table>                           
                              <table cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                                <TR class=OraTableColumnHeader>
                                    <th class="OraTableColumnHeader">Derivation Type</th>
                                    <th class="OraTableColumnHeader">Rule</th>
                                    <th class="OraTableColumnHeader">Method</th>
                                    <th class="OraTableColumnHeader">Concatenation Character</th>  
                                 </TR>
                                 <TR class=OraTabledata>
                                    <TD colspan=4>
                                      CDE is not a derived data element
                                    </TD>                         
                                  </TR> 
                              </table>
                           </td>
                       </logic:notPresent>
                      
                       <logic:present name="currCDE" property="derivedDataElement">
                           <td vAlign=top width="<%=CDECompareJspUtils.getColumnSize()%>" class="PrinterOraFieldText" >
                              <table cellSpacing=0 cellPadding=0  width="100%" align=center border=0 >
                                 <TR >
                                      <td width="<%=CDECompareJspUtils.getColumnSize()%>" class="OraHeaderSubSub" >Component Data Elements</td>                     
                                  </TR> 
                              </table>                             
                              <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                                <TR class=OraTableColumnHeader>
                                   <th class="OraTableColumnHeader">Long Name</th>
                                   <th class="OraTableColumnHeader">Context</th>
                                   <th class="OraTableColumnHeader">Public ID</th>   
                                   <th class="OraTableColumnHeader">Version</th>
                                 </TR>
                                <logic:iterate id="derivation" name="currCDE" type="gov.nih.nci.ncicb.cadsr.common.resource.DataElementDerivation" property="derivedDataElement.dataElementDerivation" indexId="dedIndex" >                                                                                               
                                 <TR class=OraTabledata>                                 
                                    <TD class=OraFieldText>
                                      <bean:write name="derivation" property="derivedDataElement.longName"/>
                                    </TD>
                                    <TD class=OraFieldText>
                                      <bean:write name="derivation" property="derivedDataElement.contextName"/>
                                    </TD>
                                    <TD class=OraFieldText>
                                      <bean:write name="derivation" property="derivedDataElement.CDEId"/>
                                    </TD>  
                                    <TD class=OraFieldText>
                                      <bean:write name="derivation" property="derivedDataElement.version"/>
                                    </TD> 
                                  </TR> 
                                 </logic:iterate>
                              </table>
                           </td>
                       </logic:present>                    
                  </logic:iterate>
                </tr>
             </table>              
             <!--Derivation details End -->  
          </logic:notEmpty>  
          </td>
        </tr> 
    </table>
    <br>
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" align="center">
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>   
            
    <%@ include file="compareCDEs_inc.jsp"%>
      <jsp:include page="../common/common_variable_length_bottom_border.jsp" flush="true">
        <jsp:param name="width" value="100" />
      </jsp:include>       
    </html:form>

</body>
</html>