

<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>


<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.cdebrowser.struts.common.BrowserFormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.cdebrowser.struts.common.BrowserNavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util.CDECompareJspUtils" %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util.CDEDetailsUtils" %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<HTML>
<HEAD>
<TITLE>CDEBrowser  Compare CDEs</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/jsLib/checkbox.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
<!--
<%
  CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
  String evsUrlThesaurus = params.getEvsUrlThesaurus();

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

      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="right" width="1%" colspan=1><A HREF="javascript:newBrowserWin('<%=request.getContextPath()%>/common/help/cdeBrowserHelp.html','helpWin',700,600)"><IMG SRC="<%=request.getContextPath()%>/i/icon_help.gif" alt="Task Help" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>         
        </TR>
      </TABLE>
      <bean:size id="listSize" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" property="cdeList"/> 
      
      <jsp:include page="../common/tab_variable_length_inc.jsp" flush="true">
        <jsp:param name="label" value="Compare&nbsp;CDEs"/>
        <jsp:param name="width" value="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" />
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
            

      <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center" border=0>
        <tr>
          <td class="OraHeaderSubSub" width="100%">Data Element</td>
        </tr>
        <tr height='2' >
           <td width="100%" ><img height=2 src="i/beigedot.gif" width="99%" align=top border=1> </td>
        </tr>  
      </table>   


     <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center" valign="top" border="0">
        <tr>
          <td class="OraHeaderSubSub" width="10%">&nbsp;
            <table cellpadding="0" cellspacing="0" align="center" valign="top" width="100%"  border="0">
             <tr>
               <td width="8%" ><a href="javascript:CheckAll()">Check All</a></td>
               <td width="8%" ><a href="javascript:ClearAll()">Clear All</a></td>
             </tr>
           </table>    
          </td> 
          <!-- For each CDE display the Checkbox and the Display order drop down -->
          <logic:notEmpty name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" property = "cdeList">
           <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
              <td width="30%">
                <table cellpadding="0" cellspacing="0" align="left" valign="top" width="10%"  border="0">
                 <tr>
                   <td width="8%" ><input type="checkbox"  value="<%=cdeIndex%>" name="<%=BrowserFormConstants.CDE_TO_REMOVE%>"  /></td>
                   <td width="8%" >
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
            <!--DataElement details Start-->
           <table width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
             <tr class="OraTabledata">
                <td class="OraTableColumnHeader" width="10%" nowrap >Public ID</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width='30%' >
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
                <td class="OraTableColumnHeader" width="10%" nowrap >Long Name</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width='30%' >
                           <bean:write name="currCDE" property="longName"/>
                         </td>
                   </logic:iterate>    
              </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Preferred Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%' >
                             <bean:write name="currCDE" property="preferredName"/>
                            </td>
                    </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Document Text</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                           <td class="OraFieldText" width='30%'  >
                             <bean:write name="currCDE" property="longCDEName"/>
                           </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Definition</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <td class="OraFieldText" width='30%'  >
                           <bean:write name="currCDE" property="preferredDefinition"/>               
                          </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Owned by Context</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='30%'  >
                              <bean:write name="currCDE" property="contextName"/> 
                             </td>
                     </logic:iterate> 
               </tr> 
                <tr class="OraTabledata">
                   <td class="OraTableColumnHeader" width="10%" nowrap >Used by Context</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='30%'  >Need to be Fixed</td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Origin</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%'  >
                             <bean:write name="currCDE" property="origin"/>                           
                            </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%">Workflow Status</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%'>
                              <bean:write name="currCDE" property="aslName"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap>Registration Status</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                           <td class="OraFieldText" width='30%'  >
                            <bean:write name="currCDE" property="registrationStatus"/> 
                           </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Version</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                              <td class="OraFieldText" width='30%'  >
                              <bean:write name="currCDE" property="version"/>
                              </td>
                      </logic:iterate> 
               </tr>
            </table>    
            <!--DataElement details End-->
            <!--DEC details Start-->
             <br>
            <A NAME="dataElementConcept"></A>
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Data Element Concept</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table> 
            
           <table width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
             <tr class="OraTabledata">
                <td class="OraTableColumnHeader" width="10%" nowrap >Public ID</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width='30%' >
                                <bean:write name="currCDE" property="dataElementConcept.publicId"/>
                          </td>
                   </logic:iterate>             
             </tr> 
              <tr class="OraTabledata">
                <td class="OraTableColumnHeader" width="10%" nowrap >Long Name</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width='30%' >
                           <bean:write name="currCDE" property="dataElementConcept.longName"/>
                         </td>
                   </logic:iterate>    
              </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Preferred Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%' >
                             <bean:write name="currCDE" property="dataElementConcept.preferredName"/>
                            </td>
                    </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Definition</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <td class="OraFieldText" width='30%'  >
                           <bean:write name="currCDE" property="dataElementConcept.preferredDefinition"/>               
                          </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Context</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='30%'  >
                              <bean:write name="currCDE" property="dataElementConcept.contextName"/> 
                             </td>
                     </logic:iterate> 
               </tr> 
                <tr class="OraTabledata">
                   <td class="OraTableColumnHeader" width="10%" >Conceptual Domain Preferred Name</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='30%'  >
                              <bean:write name="currCDE" property="dataElementConcept.CDPrefName"/> 
                             </td>                            
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%"  >Object Class Preferred Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:present name="currCDE" property = "dataElementConcept.objectClass">                             
                            <td class="OraFieldText" width='30%'  >
                             <bean:write name="currCDE" property="dataElementConcept.objectClass.preferredName"/>                           
                            </td>
                          </logic:present>
                          <logic:notPresent name="currCDE" property = "dataElementConcept.objectClass"> 
                            <td class="OraFieldText" width='30%'  >
                             &nbsp;              
                            </td>                            
                          </logic:notPresent>
                     </logic:iterate> 
               </tr>             
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%"  >Object Class Concept Codes</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:present name="currCDE" property = "dataElementConcept.objectClass">                         
                              <logic:present name="currCDE" property = "dataElementConcept.objectClass.conceptDerivationRule">                             
                                <td class="OraFieldText" width='30%'  >
                                    <%=CDEDetailsUtils.getConceptCodes(currCDE.getDataElementConcept().getObjectClass().getConceptDerivationRule(),evsUrlThesaurus,"link",",")%>                                                            
                                </td>
                              </logic:present>
                              <logic:notPresent name="currCDE" property = "dataElementConcept.objectClass.conceptDerivationRule"> 
                                <td class="OraFieldText" width='30%'  >
                                 &nbsp;                   
                                </td>                            
                              </logic:notPresent>
                          </logic:present>
                          <logic:notPresent name="currCDE" property = "dataElementConcept.objectClass"> 
                            <td class="OraFieldText" width='30%'  >
                             &nbsp;                   
                            </td>                            
                          </logic:notPresent>
                     </logic:iterate> 
               </tr>    
               
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%">Property Preferred Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:present name="currCDE" property = "dataElementConcept.property"> 
                            <td class="OraFieldText" width='30%'  >
                             <bean:write name="currCDE" property="dataElementConcept.property.preferredName"/>                           
                            </td>
                          </logic:present>
                          <logic:notPresent name="currCDE" property = "dataElementConcept.property"> 
                            <td class="OraFieldText" width='30%'  >
                             &nbsp;                   
                            </td>  
                          </logic:notPresent>
                     </logic:iterate> 
               </tr>       
               
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%"  >Property Concept Codes</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:present name="currCDE" property = "dataElementConcept.property">                         
                              <logic:present name="currCDE" property = "dataElementConcept.property.conceptDerivationRule">                             
                                <td class="OraFieldText" width='30%'  >
                                    <%=CDEDetailsUtils.getConceptCodes(currCDE.getDataElementConcept().getProperty().getConceptDerivationRule(),evsUrlThesaurus,"link",",")%>                                                            
                                </td>
                              </logic:present>
                              <logic:notPresent name="currCDE" property = "dataElementConcept.property.conceptDerivationRule"> 
                                <td class="OraFieldText" width='30%'  >
                                 &nbsp;                   
                                </td>                            
                              </logic:notPresent>
                          </logic:present>
                          <logic:notPresent name="currCDE" property = "dataElementConcept.property"> 
                            <td class="OraFieldText" width='30%'  >
                             &nbsp;                   
                            </td>                            
                          </logic:notPresent>
                     </logic:iterate> 
               </tr>    
    
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" >Origin</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                           <td class="OraFieldText" width='30%'  >
                            <bean:write name="currCDE" property="dataElementConcept.origin"/> 
                           </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%"  >Workflow Status</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                              <td class="OraFieldText" width='30%'  >
                              <bean:write name="currCDE" property="dataElementConcept.aslName"/>
                              </td>
                      </logic:iterate> 
               </tr>
            </table>    
            
            <!--DEC details End -->
            
             <!--ValueDomain details Start -->
            <br>
            <A NAME="valueDomain"></A>
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Value Domain</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>
           <table width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
             <tr class="OraTabledata">
                <td class="OraTableColumnHeader" width="10%" nowrap >Public ID</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width='30%' >
                                <bean:write name="currCDE" property="valueDomain.publicId"/>
                          </td>
                   </logic:iterate>             
             </tr> 
              <tr class="OraTabledata">
                <td class="OraTableColumnHeader" width="10%" nowrap >Long Name</td>
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                         <td class="OraFieldText" width='30%' >
                           <bean:write name="currCDE" property="valueDomain.longName"/>
                         </td>
                   </logic:iterate>    
              </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Preferred Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%' >
                             <bean:write name="currCDE" property="valueDomain.preferredName"/>
                            </td>
                    </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Definition</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <td class="OraFieldText" width='30%'  >
                           <bean:write name="currCDE" property="valueDomain.preferredDefinition"/>               
                          </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" nowrap >Data Type</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='30%'  >
                              <bean:write name="currCDE" property="valueDomain.datatype"/> 
                             </td>
                     </logic:iterate> 
               </tr> 
                <tr class="OraTabledata">
                   <td class="OraTableColumnHeader" width="10%" >Unit of Measure</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                             <td class="OraFieldText" width='30%'  >
                              <bean:write name="currCDE" property="valueDomain.unitOfMeasure"/> 
                             </td>                            
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%"  >Display Format</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%'  >
                             <bean:write name="currCDE" property="valueDomain.displayFormat"/>                           
                            </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%">Maximum Length</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%'>
                              <bean:write name="currCDE" property="valueDomain.maxLength"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%">Minimum Length</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%'>
                              <bean:write name="currCDE" property="valueDomain.minLength"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%">Decimal Place</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%'>
                              <bean:write name="currCDE" property="valueDomain.decimalPlace"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%">High Value</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%'>
                              <bean:write name="currCDE" property="valueDomain.highValue"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%">Low Value</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%'>
                              <bean:write name="currCDE" property="valueDomain.lowValue"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%">Value Domain Type</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%'>
                              <bean:write name="currCDE" property="valueDomain.VDType"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%">Conceptual Domain Preferred Name</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%'>
                              <bean:write name="currCDE" property="valueDomain.CDPrefName"/> 
                            </td>
                     </logic:iterate> 
               </tr> 
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%">Representation</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                      <logic:present name="currCDE" property="valueDomain.representation">
                        <td class="OraFieldText" width='30%'>
                          <bean:write name="currCDE" property="valueDomain.representation.longName"/>
                        </TD>
                      </logic:present>    
                      <logic:notPresent name="currCDE" property="valueDomain.representation">
                        <td class="OraFieldText" width='30%'>
                          &nbsp;
                        </TD>
                      </logic:notPresent>                         
                     </logic:iterate> 
               </tr>    
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%"  >Concept Codes</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:present name="currCDE" property = "valueDomain">                         
                              <logic:present name="currCDE" property = "valueDomain.conceptDerivationRule">                             
                                <td class="OraFieldText" width='30%'  >
                                    <%=CDEDetailsUtils.getConceptCodes(currCDE.getValueDomain().getConceptDerivationRule(),evsUrlThesaurus,"link",",")%>                                                            
                                </td>
                              </logic:present>
                              <logic:notPresent name="currCDE" property = "valueDomain.conceptDerivationRule"> 
                                <td class="OraFieldText" width='30%'  >
                                 &nbsp;                   
                                </td>                            
                              </logic:notPresent>
                          </logic:present>
                          <logic:notPresent name="currCDE" property = "valueDomain"> 
                            <td class="OraFieldText" width='30%'  >
                             &nbsp;                   
                            </td>                            
                          </logic:notPresent>
                     </logic:iterate> 
               </tr>                   
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%" >Origin</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                           <td class="OraFieldText" width='30%'  >
                            <bean:write name="currCDE" property="valueDomain.origin"/> 
                           </td>
                     </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%"  >Workflow Status</td>
                     <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                              <td class="OraFieldText" width='30%'  >
                              <bean:write name="currCDE" property="valueDomain.aslName"/>
                              </td>
                      </logic:iterate> 
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="10%">Version</td>
                   <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                            <td class="OraFieldText" width='30%'>
                              <bean:write name="currCDE" property="valueDomain.version"/> 
                            </td>
                     </logic:iterate> 
               </tr>                   
            </table>             
              <!--ValueDomain details End -->
              <!--Permissible Values Start-->
            <br>
            <A NAME="permissibleValues"></A>
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Permissible Values</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>

            <table cellSpacing=0 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align=center border=0>
               <tr >
                          <td width="10%" class="PrinterOraFieldText" >&nbsp;</td>
                          <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                          <logic:empty name="currCDE" property="valueDomain.validValues">
                            <td width="30%" class="PrinterOraFieldText" >&nbsp;</td>
                          </logic:empty>
                          <logic:notEmpty name="currCDE" property="valueDomain.validValues">
                            <bean:size id="vvSize" name="currCDE" property="valueDomain.validValues"/> 
                            <td width="30%" class="PrinterOraFieldText" ><%=vvSize%> Permissible values</td>
                          </logic:notEmpty> 
                          </logic:iterate>
                </tr>
            </table>
            <table cellSpacing=2 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align=center border=0>
             	<tr>
                   <TD vAlign=top width=10%>
                       &nbsp;
                    </TD>             	
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                   <logic:empty name="currCDE" property="valueDomain.validValues">
                       <td vAlign=top width="30%" class="PrinterOraFieldText" >
                          <table cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                            <TR class=OraTableColumnHeader>
                              <TH class=OraTableColumnHeader>Value</TH>
                              <TH class=OraTableColumnHeader>Value meaning</TH>
                              <TH class=OraTableColumnHeader>Description</TH>
                             </TR>
                             <TR class=OraTabledata>
                                <TD colspan=3>
                                  CDE does not Permissible Values
                                </TD>                         
                              </TR> 
                          </table>
                       </td>
                   </logic:empty>
                   <logic:notEmpty name="currCDE" property="valueDomain.validValues">
                       <td width="30%" class="PrinterOraFieldText" >
                          <table cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                            <TR class=OraTableColumnHeader>
                              <TH class=OraTableColumnHeader>Value</TH>
                              <TH class=OraTableColumnHeader>Value meaning</TH>
                              <TH class=OraTableColumnHeader>Description</TH>
                             </TR>
                             <logic:iterate id="currVV" name="currCDE" type="gov.nih.nci.ncicb.cadsr.resource.ValidValue" property="valueDomain.validValues" indexId="vvIndex" >                                                              
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
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Reference Documents</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>

            <table  cellSpacing=2 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align=center border=0>
             	<tr>
                   <TD vAlign=top width=10%>
                       &nbsp;
                    </TD>             	
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                   <logic:empty name="currCDE" property="refereceDocs">
                       <td vAlign=top width="30%" class="PrinterOraFieldText" >
                          <table cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
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
                       <td vAlign=top width="30%" class="PrinterOraFieldText" >
                          <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                            <TR class=OraTableColumnHeader>
                               <th class="OraTableColumnHeader">Document Name</th>
                               <th class="OraTableColumnHeader">Document Type</th>
                               <th class="OraTableColumnHeader">Document Text</th>
                             </TR>
                             <logic:iterate id="currRefDoc" name="currCDE" type="gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument" property="refereceDocs" indexId="rdIndex" >                                                              
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
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Classifications</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>

            <table  cellSpacing=2 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align=center border=0>
             	<tr>
                   <TD vAlign=top width=10%>
                       &nbsp;
                    </TD>             	
                 <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                   <logic:empty name="currCDE" property="classifications">
                       <td width="30%" class="PrinterOraFieldText" >
                          <table cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                            <TR class=OraTableColumnHeader>
                              <TH class=OraTableColumnHeader>CS* Preferred Name</TH>
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
                       <td vAlign=top width="30%" class="PrinterOraFieldText" >
                          <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                            <TR class=OraTableColumnHeader>
                              <TH class=OraTableColumnHeader>CS* Preferred Name</TH>
                              <TH class=OraTableColumnHeader>CS* Definition</TH>
                              <TH class=OraTableColumnHeader>CSI* Name</TH>
                              <TH class=OraTableColumnHeader>CSI* Type</TH>
                             </TR>
                             <logic:iterate id="currCS" name="currCDE" type="gov.nih.nci.ncicb.cadsr.resource.Classification" property="classifications" indexId="csIndex" >                                                              
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
            
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center">
              <tr>
                <td class="OraHeaderSubSub" width="100%">Data Element Derivation</td>
              </tr>
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>             
             
            <table cellSpacing=2 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align=center border=0>
               <tr >
                  <td width="10%" class="PrinterOraFieldText" >&nbsp;</td>
                  <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                       <logic:notPresent name="currCDE" property="derivedDataElement">
                           <td vAlign=top  width="30%" class="PrinterOraFieldText" >
                              <table cellSpacing=0 cellPadding=0  width="100%" align=center border=0 >
                                 <TR >
                                      <td width="30%" class="OraHeaderSubSub" >Derivation Details</td>                     
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
                           <td vAlign=top width="30%" class="PrinterOraFieldText" >
                              <table cellSpacing=0 cellPadding=0  width="100%" align=center border=0 >
                                 <TR >
                                      <td width="30%" class="OraHeaderSubSub" >Derivation Details</td>                     
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

            <table cellSpacing=2 cellPadding=0  width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align=center border=0>
               <tr >
                  <td width="10%" class="PrinterOraFieldText" >&nbsp;</td>
                  <logic:iterate id="currCDE" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" type="gov.nih.nci.ncicb.cadsr.resource.DataElement" property="cdeList" indexId="cdeIndex" >                                 
                       <logic:notPresent name="currCDE" property="derivedDataElement">
                           <td vAlign=top  width="30%" class="PrinterOraFieldText" >
                              <table cellSpacing=0 cellPadding=0  width="100%" align=center border=0 >
                                 <TR >
                                      <td width="30%" class="OraHeaderSubSub" >Component Data Elements</td>                     
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
                           <td vAlign=top width="30%" class="PrinterOraFieldText" >
                              <table cellSpacing=0 cellPadding=0  width="100%" align=center border=0 >
                                 <TR >
                                      <td width="30%" class="OraHeaderSubSub" >Component Data Elements</td>                     
                                  </TR> 
                              </table>                             
                              <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                                <TR class=OraTableColumnHeader>
                                   <th class="OraTableColumnHeader">Long Name</th>
                                   <th class="OraTableColumnHeader">Context</th>
                                   <th class="OraTableColumnHeader">Public ID</th>   
                                   <th class="OraTableColumnHeader">Version</th>
                                 </TR>
                                <logic:iterate id="derivation" name="currCDE" type="gov.nih.nci.ncicb.cadsr.resource.DataElementDerivation" property="derivedDataElement.dataElementDerivation" indexId="dedIndex" >                                                                                               
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
            <table cellpadding="0" cellspacing="0" width="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>%" align="center">
              <tr>
                <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
              </tr>  
            </table>   
            
    <%@ include file="compareCDEs_inc.jsp"%>
      <jsp:include page="../common/common_variable_length_bottom_border.jsp" flush="true">
        <jsp:param name="width" value="<%=CDECompareJspUtils.getTotalPageWidth(listSize)%>" />
      </jsp:include>       
    </html:form>

</body>
</html>