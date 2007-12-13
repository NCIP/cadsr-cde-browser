<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%//@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.jsp.util.CDEDetailsUtils" %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.StringUtils" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.ocbrowser.struts.common.OCBrowserNavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.ocbrowser.struts.common.OCBrowserFormConstants"%>

<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="cdebrowserCommon_html/SessionAuth.html"%>

<%
  DataElement de = (DataElement)infoBean.getInfo("de");
  pageContext.setAttribute("de",de);
  DataElementConcept dec = de.getDataElementConcept();
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  String pageId = infoBean.getPageId();
  String pageName = PageConstants.PAGEID;
  String pageUrl = "&"+pageName+"="+pageId;
  CDEBrowserParams params = CDEBrowserParams.getInstance();

  String socVersion="";
  if(dec.getObjectClass()!=null)
  {
    Float ocVersion = dec.getObjectClass().getVersion();
    if (ocVersion.floatValue() == 0.00f) socVersion = "";
    else socVersion = ocVersion.toString();
  }
  
  String sptVersion ="";  
  if(dec.getProperty()!=null)
  {
    Float ptVersion = dec.getProperty().getVersion();
    if (ptVersion.floatValue() == 0.00f) sptVersion = "";
    else sptVersion = ptVersion.toString();
  }
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
Data Element Concept
</TITLE>
</HEAD>
<BODY topmargin="0">



<SCRIPT LANGUAGE="JavaScript">
<!--
function redirect1(detailReqType, linkParms )
{
  document.location.href="search?dataElementDetails=" + linkParms;
  
}
function goPage(pageInfo) {
  document.location.href = "search?searchDataElements=&"+pageInfo;
  
}
  
//-->
</SCRIPT>
<%@ include  file="cdebrowserCommon_html/tab_include.html" %>
<form method="POST" ENCTYPE="application/x-www-form-urlencoded" action="<%= infoBean.getStringInfo("controller") %>">
<input type="HIDDEN" name="<%= PageConstants.PAGEID %>" value="<%= infoBean.getPageId()%>"/>
<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Selected Data Element</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Public ID:</td>
    <td class="OraFieldText"><%=de.getCDEId()%></td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Version:</td>
    <td class="OraFieldText"><%=de.getVersion()%> </td>
 </tr>
 
  <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Long Name:</td>
    <td class="OraFieldText"><%=de.getLongName()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Short Name:</td>
    <td class="OraFieldText"><%=de.getPreferredName()%></td>
 </tr>
 

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Preferred Question Text:</td>
    <td class="OraFieldText"><%=de.getLongCDEName()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Definition:</td>
    <td class="OraFieldText"><%=de.getPreferredDefinition()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Workflow Status:</td>
    <td class="OraFieldText"><%=de.getAslName()%> </td>
 </tr>


 
</table>
<br>

<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Data Element Concept Details</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">

  <tr class="OraTabledata">
    <td class="TableRowPromptText">Public ID:</td>
    <td class="OraFieldText"><%=dec.getPublicId()%></td>
 </tr>
 
  <tr class="OraTabledata">
    <td class="TableRowPromptText">Version:</td>
    <td class="OraFieldText"><%=dec.getVersion()%> </td>
 </tr>
 
  <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Long Name:</td>
    <td class="OraFieldText"><%=dec.getLongName()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Short Name:</td>
    <td class="OraFieldText"><%=dec.getPreferredName()%></td>
 </tr>
 

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Definition:</td>
    <td class="OraFieldText"><%=dec.getPreferredDefinition()%> </td>
 </tr>

  <tr class="OraTabledata">
    <td class="TableRowPromptText">Context:</td>
    <td class="OraFieldText"><%=dec.getContextName()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Workflow Status:</td>
    <td class="OraFieldText"><%=dec.getAslName()%> </td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Conceptual Domain Public ID:</td>
    <td class="OraFieldText"><%=dec.getCDPublicId()%></td>
 </tr>
 <tr class="OraTabledata">   
   <td class="TableRowPromptText">Conceptual Domain Short Name:</td>
    <td class="OraFieldText"><%=dec.getCDPrefName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Conceptual Domain Context Name:</td>
    <td class="OraFieldText"><%=dec.getCDContextName()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Conceptual Domain Version:</td>
    <td class="OraFieldText"><%=dec.getCDVersion()%> </td>
 </tr>
 
  <tr class="OraTabledata">
    <td class="TableRowPromptText">Origin:</td>
    <td class="OraFieldText"><%=dec.getOrigin()%> </td>
 </tr>
 </table>

<logic:present name="de" property = "dataElementConcept.objectClass">
    <% ObjectClass objClass = dec.getObjectClass(); %>
    <%
      String contextPath = request.getContextPath();
      String ocId = objClass.getIdseq();
      String ocDetailsWindow= 
        "javascript:newBrowserWin('"+contextPath+"/ocbrowser/ocDetailsAction.do?"+OCBrowserNavigationConstants.METHOD_PARAM+"="+OCBrowserNavigationConstants.OC_DETAILS+"&"+OCBrowserFormConstants.RESET_CRUMBS+"=true&"+OCBrowserFormConstants.OC_IDSEQ+"="+ocId+"',"+"'OCDetails',800,600)";
    
    %>
         <br>
         <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
          <tr  valign="bottom" >
           <td class="OraHeaderSubSubSub" width="80%">Object Class</td>
           <td class="OraHeaderSubSubSub" width="20%"><a href="<%=ocDetailsWindow%>"> More Details </a></td>
          </tr>
         </table>
         <table valign="top"  width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
         <tr class="OraTabledata">
            <td class="TableRowPromptText"  width="20%" >Public ID:</td>
            <td class="OraFieldText"><%=objClass.getPublicId()%></td>
         </tr>
         <tr class="OraTabledata">
            <td class="TableRowPromptText"  width="20%" >Version:</td>
            <td class="OraFieldText"><%=socVersion%> </td>
         </tr>
         
         <tr class="OraTabledata"> 
            <td class="TableRowPromptText"  width="20%" >Long Name:</td>
            <td class="OraFieldText"><%=objClass.getLongName()%> </td>
         </tr>         
         <tr class="OraTabledata"> 
            <td class="TableRowPromptText"  width="20%" >Short Name:</td>
            <td class="OraFieldText"><%=objClass.getPreferredName()%> </td>
         </tr>
         <tr class="OraTabledata">
            <td class="TableRowPromptText"  width="20%" >Context:</td>
            <td class="OraFieldText"><%=objClass.getContext().getName()%> </td>
         </tr>

         <tr class="OraTabledata">
            <td class="TableRowPromptText"  width="20%" >Qualifier:</td>
            <td class="OraFieldText"><%=objClass.getQualifier()%> </td>
         </tr>
         </table>
         
        <logic:present name="de" property = "dataElementConcept.objectClass.conceptDerivationRule">         
            <% ConceptDerivationRule ocdr = objClass.getConceptDerivationRule(); %>
            <!-- Taken out at Denises request TT#1240            
               <br>
              <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
                <tr  valign="bottom" >
                  <td class="OraHeaderSubSubSub" width="100%">Object Class Concept Derivation Rule</td>
                </tr>
             </table>
             <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
               <tr class="OraTabledata">
                  <td class="TableRowPromptText"  width="20%" >Name:</td>
                  <td class="OraFieldText"><%=ocdr.getName()%> </td>
               </tr>
               <tr class="OraTabledata">
                  <td class="TableRowPromptText"  width="20%" >Type:</td>
                  <td class="OraFieldText"><%=ocdr.getType()%> </td>
               </tr>
               <tr class="OraTabledata">
                  <td class="TableRowPromptText"  width="20%">Rule:</td>
                  <td class="OraFieldText"><%=ocdr.getRule()%> </td>
               </tr>  
               <tr class="OraTabledata">
                  <td class="TableRowPromptText"  width="20%" >Methods:</td>
                  <td class="OraFieldText"><%=ocdr.getMethods()%> </td>
               </tr>  
               <tr class="OraTabledata">
                  <td class="TableRowPromptText"  width="20%" >Concatenation Character:</td>
                  <td class="OraFieldText"><%=ocdr.getConcatenationChar()%> </td>
               </tr>                 
             </table>
             -->
              <logic:present name="de" property = "dataElementConcept.objectClass.conceptDerivationRule.componentConcepts">                    
                   <br>
                  <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
                        <tr  valign="bottom" >
                          <td class="OraHeaderSubSubSub" width="100%">Object Class Concepts</td>
                        </tr>
                     </table>
                    <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
                        <tr class="OraTabledata">
                          <td class="OraTableColumnHeader">Concept Name</td>
                          <td class="OraTableColumnHeader">Concept Code</td>
                          <td class="OraTableColumnHeader">Public ID</td>                          
                          <td class="OraTableColumnHeader">Definition Source</td>
                          <td class="OraTableColumnHeader">EVS Source</td>   
                          <td class="OraTableColumnHeader">Primary</td>
                        </tr>   
                       <logic:iterate id="comp" name="de" type="gov.nih.nci.ncicb.cadsr.common.resource.ComponentConcept" property="dataElementConcept.objectClass.conceptDerivationRule.componentConcepts" indexId="ccIndex" >                                 
                        <tr class="OraTabledata">
                           <td class="OraFieldText"><%=comp.getConcept().getLongName()%> </td>
                           <td class="OraFieldText">
                                <%=CDEDetailsUtils.getConceptCodeUrl(comp.getConcept(),params,"link",",")%>                                  
                           </td>
                           <td class="OraFieldText"><%=comp.getConcept().getPublicId()%> </td> 
                           <td class="OraFieldText"><%=comp.getConcept().getDefinitionSource()%> </td> 
                           <td class="OraFieldText"><%=comp.getConcept().getEvsSource()%> </td>
                           <td class="OraFieldText"><%=StringUtils.booleanToStr(comp.getIsPrimary())%> </td>
                        </tr>
                       </logic:iterate>
                    </table>                      
             </logic:present>
            <logic:notPresent name="de" property = "dataElementConcept.objectClass.conceptDerivationRule.componentConcepts"> 
                       <br>
                       <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
                        <tr  valign="bottom" >
                         <td class="OraHeaderSubSubSub" width="100%">Object Class Concepts</td>
                        </tr>
                       </table>
                       <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
                         <tr class="OraTabledata">
                            <td  width="20%" >Object Class does not have any Concepts.</td>
                         </tr>
                         </tr>                 
                       </table>    
              </logic:notPresent >             
      </logic:present>  
      <logic:notPresent name="de" property = "dataElementConcept.objectClass.conceptDerivationRule"> 
               <br>
               <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
                <tr  valign="bottom" >
                 <td class="OraHeaderSubSubSub" width="100%">Object Class Concepts</td>
                </tr>
               </table>
               <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
                 <tr class="OraTabledata">
                    <td  width="20%" >Object Class does not have any Concepts.</td>
                 </tr>
                 </tr>                 
               </table>    
       </logic:notPresent >       
 </logic:present>
 <logic:notPresent name="de" property = "dataElementConcept.objectClass"> 
         <br>
         <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
          <tr  valign="bottom" >
           <td class="OraHeaderSubSubSub" width="100%">Object Class</td>
          </tr>
         </table>
         <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
           <tr class="OraTabledata">
              <td   width="20%" >Data Element Concept does not have an Object Class.</td>
           </tr>
           </tr>                 
         </table>    
 </logic:notPresent> 

 <logic:present name="de" property = "dataElementConcept.property">    
     <% Property prop = dec.getProperty(); %>
         <br>
         <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
          <tr  valign="bottom" >
           <td class="OraHeaderSubSubSub" width="100%">Property</td>
          </tr>
         </table>
          <table valign="top"  width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
             <td class="TableRowPromptText"  width="20%" >Public ID:</td>
             <td class="OraFieldText"><%=prop.getPublicId()%></td>
          </tr>
          <tr class="OraTabledata">
             <td class="TableRowPromptText"  width="20%" >Version:</td>
             <td class="OraFieldText"><%=sptVersion%> </td>
          </tr>
          <tr class="OraTabledata"> 
             <td class="TableRowPromptText"  width="20%" >Long Name:</td>
             <td class="OraFieldText"><%=prop.getLongName()%> </td>
          </tr>          
          <tr class="OraTabledata"> 
             <td class="TableRowPromptText"  width="20%" >Short Name:</td>
             <td class="OraFieldText"><%=prop.getPreferredName()%> </td>
          </tr>
          <tr class="OraTabledata">
             <td class="TableRowPromptText"  width="20%" >Context:</td>
             <td class="OraFieldText"><%=prop.getContext().getName()%> </td>
          </tr>

          <tr class="OraTabledata">
             <td class="TableRowPromptText"  width="20%" >Qualifier:</td>
             <td class="OraFieldText"><%=prop.getQualifier()%> </td>
          </tr>
          </table>
          
         <logic:present name="de" property = "dataElementConcept.property.conceptDerivationRule">         
             <% ConceptDerivationRule ocdr = prop.getConceptDerivationRule(); %>
             <!-- Taken out at Denises request TT#1240             
                <br>
               <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
                 <tr  valign="bottom" >
                   <td class="OraHeaderSubSubSub" width="100%">Property Concept Derivation Rule</td>
                 </tr>
              </table>
              <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
                <tr class="OraTabledata">
                   <td class="TableRowPromptText"  width="20%" >Name:</td>
                   <td class="OraFieldText"><%=ocdr.getName()%> </td>
                </tr>
                <tr class="OraTabledata">
                   <td class="TableRowPromptText"  width="20%" >Type:</td>
                   <td class="OraFieldText"><%=ocdr.getType()%> </td>
                </tr>
                <tr class="OraTabledata">
                   <td class="TableRowPromptText"  width="20%">Rule:</td>
                   <td class="OraFieldText"><%=ocdr.getRule()%> </td>
                </tr>  
                <tr class="OraTabledata">
                   <td class="TableRowPromptText"  width="20%" >Methods:</td>
                   <td class="OraFieldText"><%=ocdr.getMethods()%> </td>
                </tr>  
                <tr class="OraTabledata">
                   <td class="TableRowPromptText"  width="20%" >Concatenation Character:</td>
                   <td class="OraFieldText"><%=ocdr.getConcatenationChar()%> </td>
                </tr>                 
              </table>
              -->
               <logic:present name="de" property = "dataElementConcept.property.conceptDerivationRule.componentConcepts">                    
                    <br>
                   <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
                         <tr  valign="bottom" >
                           <td class="OraHeaderSubSubSub" width="100%">Property Concepts</td>
                         </tr>
                      </table>
                     <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
                         <tr class="OraTabledata">
                           <td class="OraTableColumnHeader">Concept Name</td>
                           <td class="OraTableColumnHeader">Concept Code</td>
                           <td class="OraTableColumnHeader">Public ID</td>                          
                           <td class="OraTableColumnHeader">Definition Source</td>
                           <td class="OraTableColumnHeader">EVS Source</td>
                           <td class="OraTableColumnHeader">Primary</td>
                         </tr>   
                        <logic:iterate id="comp" name="de" type="gov.nih.nci.ncicb.cadsr.common.resource.ComponentConcept" property="dataElementConcept.property.conceptDerivationRule.componentConcepts" indexId="ccIndex" >                                 
                         <tr class="OraTabledata">
                            <td class="OraFieldText"><%=comp.getConcept().getLongName()%> </td>
                            <td class="OraFieldText">
                                <%=CDEDetailsUtils.getConceptCodeUrl(comp.getConcept(),params,"link",",")%>                              
                            </td>
                            <td class="OraFieldText"><%=comp.getConcept().getPublicId()%> </td> 
                            <td class="OraFieldText"><%=comp.getConcept().getDefinitionSource()%> </td> 
                            <td class="OraFieldText"><%=comp.getConcept().getEvsSource()%> </td>
                            <td class="OraFieldText"><%=StringUtils.booleanToStr(comp.getIsPrimary())%> </td>
                         </tr>
                        </logic:iterate>
                     </table>                      
              </logic:present>
              <logic:notPresent name="de" property = "dataElementConcept.property.conceptDerivationRule.componentConcepts"> 
                       <br>
                       <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
                        <tr  valign="bottom" >
                         <td class="OraHeaderSubSubSub" width="100%">Property Concepts</td>
                        </tr>
                       </table>
                       <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
                         <tr class="OraTabledata">
                            <td  width="20%" >Property does not have any Concepts.</td>
                         </tr>
                         </tr>                 
                       </table>    
               </logic:notPresent >               
       </logic:present>
       <logic:notPresent name="de" property = "dataElementConcept.property.conceptDerivationRule"> 
               <br>
               <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
                <tr  valign="bottom" >
                 <td class="OraHeaderSubSubSub" width="100%">Property Concepts</td>
                </tr>
               </table>
               <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
                 <tr class="OraTabledata">
                    <td  width="20%" >Property does not have any Concepts.</td>
                 </tr>
                 </tr>                 
               </table>    
       </logic:notPresent >        
 </logic:present>
 <logic:notPresent name="de" property = "dataElementConcept.property"> 
         <br>
         <table valign="bottom" cellpadding="0" cellspacing="0" width="80%" align="center">
          <tr  valign="bottom" >
           <td class="OraHeaderSubSubSub" width="100%">Property</td>
          </tr>
         </table>
         <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
           <tr class="OraTabledata">
              <td   width="20%" >Data Element Concept does not have a Property.</td>
           </tr>
           </tr>                 
         </table>    
 </logic:notPresent > 
 
</form>

<%@ include file="../common/common_bottom_border.jsp"%>

</BODY>
</HTML>

