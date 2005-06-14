<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>

<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.umlbrowser.struts.common.UmlBrowserFormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.umlbrowser.struts.common.UmlBrowserNavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.umlbrowser.util.OCUtils"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.umlbrowser.util.ObjectExtractor"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Collection"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.domain.ObjectClass"%>


<%@ page contentType="text/html;charset=windows-1252"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/jsLib/checkbox.js"></SCRIPT>
<title>OCR Details</title>
</head>
<body topmargin="0">
<SCRIPT LANGUAGE="JavaScript">
<!--
function navigateOCR(ocId,ocrIndex,direction) {
  document.forms[0].<%=UmlBrowserNavigationConstants.METHOD_PARAM%>.value="<%=UmlBrowserNavigationConstants.NAVIGATE_OCR%>";     
  document.forms[0].<%=UmlBrowserFormConstants.OC_IDSEQ%>.value=ocId;
  document.forms[0].<%=UmlBrowserFormConstants.OCR_DIRECTION%>.value=direction;
  document.forms[0].<%=UmlBrowserFormConstants.OCR_INDEX%>.value=ocrIndex;
  document.forms[0].target="_parent";
  document.forms[0].submit();
}
//-->
</SCRIPT>

<% String contextPath = request.getContextPath();
  String clearurl = contextPath+"/umlbrowser/clearNavigationPathAction.do?"+UmlBrowserNavigationConstants.METHOD_PARAM+"="+UmlBrowserNavigationConstants.CLEAR_NAVIGATION_PATH;
%>

<html:form action="/umlbrowser/navigateOCRAction.do">

<html:hidden property="<%=UmlBrowserFormConstants.OC_IDSEQ%>"/>
<html:hidden property="<%=UmlBrowserFormConstants.OCR_DIRECTION%>"/>
<html:hidden property="<%=UmlBrowserFormConstants.OCR_INDEX%>"/>
<html:hidden property="<%=UmlBrowserFormConstants.OCR_BR_CRUMBS_INDEX%>"/>
<html:hidden value="" property="<%=UmlBrowserNavigationConstants.METHOD_PARAM%>"/>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td align="left" nowrap>

    <html:img page="/i/objectClassBanner.gif" border="0" />
    </td>

    <td align=right valign=top colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="javascript:newBrowserWin('<%=request.getContextPath()%>/common/help/cdeBrowserHelp.html','helpWin',700,600)"><html:img page="/i/icon_help.gif" alt="Task Help" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
         <logic:present name="nciUser">
            <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%="logout?FirstTimer=0"%>" TARGET="_top"><html:img page="/i/logout.gif" alt="Logout" border="0"  width="32" height="32" /></A><br><font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font></TD>
          </logic:present>
        </TR>
      </TABLE>
    </td>
  </tr>

</TABLE>


<jsp:include page="mltitab_inc.jsp" flush="true">
	<jsp:param name="label" value="Associations" />
</jsp:include>   

<table cellpadding="0" cellspacing="0" width="100%" align="center" border=0>
        <tr valign="top">    
          <td valign="top" align="left" width="100%" class="AbbreviatedText">
            All Associations displayed below are for Object Class "<bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="longName"/>".
            Details of this class can be seen under "Object Class" tab.
            Outgoing associations have "<bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="longName"/>" as the source Object Class;
            Incoming associations have "<bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="longName"/>" as the target Object Class;
            Bidirection associations have "<bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="longName"/>" as target or source Object Class.
          </td>
        </tr>  
</table>
        
        <table valign="top" width="90%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="TableRowPromptText" width="20%">Public ID:</td>
            <td class="OraFieldText">
              <bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="publicId"/>
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="TableRowPromptText" width="20%">Long Name:</td>
            <td class="OraFieldText">
              <bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="longName"/>
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="TableRowPromptText" width="20%">Preferred Name:</td>
            <td class="OraFieldText">
              <bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="preferredName"/>
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="TableRowPromptText" width="20%">Context:</td>
            <td class="OraFieldText">
              <bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="context.name"/>
            </td>
          </tr>
          <tr class="OraTabledata">
            <td class="TableRowPromptText" width="20%">Version:</td>
            <td class="OraFieldText">
              <bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="version"/>
            </td>
          </tr>
        </table>
        <br>
        <table vAlign=top cellSpacing=1 cellPadding=1  width="90%" align=center border=0 class="OraBGAccentVeryDark">
          <TR class=OraTableColumnHeader>
             <th class="OraTableColumnHeader"  >Alternate Names</th>
             <th class="OraTableColumnHeader">Type</th>
             <th class="OraTableColumnHeader">Context</th>
           </TR>   
          <logic:notEmpty  name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="alternateNames" > 
            <logic:iterate id="alternateName" name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="alternateNames" type="gov.nih.nci.ncicb.cadsr.domain.AlternateName" indexId="nameIndex" >                                 
              <TR class=OraTabledata>
                 <td class="OraFieldText">
                   <bean:write name="alternateName" property="name"/>
                  </td>
                 <td class="OraFieldText">
                   <bean:write name="alternateName" property="type"/>
                  </td>
                 <td class="OraFieldText">
                   <bean:write name="alternateName" property="context.name"/>
                  </td>                     
               </TR>  
           </logic:iterate>
          </logic:notEmpty>
         <logic:empty  name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="alternateNames" > 
              <TR class=OraTabledata>
                 <td colspan=3 class="OraFieldText">No Alternate names for this Object Class exist</td>
               </TR>  
          </logic:empty>                    
        </table>  
        <br>
        
      <cde:ocrNavigation
              navigationListId="<%=UmlBrowserFormConstants.OCR_NAVIGATION_BEAN%>"   
              outGoingImage="/i/outgoing.gif"
              inCommingImage="/i/incomming.gif"
              biDirectionalImage="/i/bidirectional.gif"
              scope="session"
       />           
                 
      <table cellpadding="0" cellspacing="0" width="90%" align="center" border=0>
         <tr >
           <td>&nbsp;</td>
         </tr>
         <tr >
            <td  ><a class="link" href="#outgoing">Outgoing Associations</a></td>
            <td  ><a class="link" href="#incoming">Incoming Associations</a></td>
            <td  ><a class="link" href="#bidirectionl">Bidirection Associations</a></td>   
            <logic:present  name="<%=UmlBrowserFormConstants.OCR_NAVIGATION_BEAN%>" >
              <bean:size id="size" name="<%=UmlBrowserFormConstants.OCR_NAVIGATION_BEAN%>" />
              <logic:greaterThan value="1" name="size">
                 <td><a href="<%=clearurl%>">Clear navigation path</a></td>
              </logic:greaterThan>              
            </logic:present>
         </tr>
         <tr >
           <td>&nbsp;</td>
         </tr>         
      </table>

     
  <!-- Out going start -->
      <A NAME="outgoing"></A> 
      <table cellpadding="0" cellspacing="0" width="100%" align="center">
        <tr>
          <td class="OraHeaderSubSub" width="100%">Outgoing Associations</td>
        </tr>
            <tr >
               <td width="100%" ><img height=1 src="<%=contextPath%>/i/beigedot.gif" width="99%" align=top border=0> </td>
            </tr> 
      </table>        
    <logic:notEmpty  name="<%=UmlBrowserFormConstants.OUT_GOING_OCRS%>" scope="session">  
      <logic:iterate id="currOutgoingOCR" name="<%=UmlBrowserFormConstants.OUT_GOING_OCRS%>" type="gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship" indexId="ocrIndex" >                                 
         <%
           Collection projects = ObjectExtractor.getProjects(currOutgoingOCR);
           pageContext.setAttribute("projects",projects);                  
        %> 
 
       <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
        <tr class=OraTabledata>
         <td class=OraFieldText>
             <table vAlign=top width="100%">
                 <tr  class=OraTabledata align="left">
                 <% if(OCUtils.isNavigationAllowed(request,UmlBrowserFormConstants.OCR_NAVIGATION_BEAN,currOutgoingOCR))
                 {
                 %>
                    <td class=OraFieldText colspan="2">
                        <a href="javascript:navigateOCR('<%=currOutgoingOCR.getTarget().getId()%>','<%=ocrIndex%>','<%=UmlBrowserFormConstants.OUT_GOING_OCRS%>')">Navigate to this association</a>
                    </td>              
                 <%}else{%>
                      <td class=OraFieldText colspan="2">
                        &nbsp;
                     </td>                
                <% }%>
                 </tr>
                  <tr  class=OraTabledata align="center">
                     <td class=OraFieldText colspan="2" align="center">
                       <cde:DefineNavigationCrumbs
                            beanId="<%=UmlBrowserFormConstants.OUT_GOING_OCRS+ocrIndex%>"   
                            direction="<%=UmlBrowserFormConstants.OUT_GOING_OCRS%>"
                            ocrId="currOutgoingOCR"
                       />                     
                       <cde:ocrNavigation
                            navigationListId="<%=UmlBrowserFormConstants.OUT_GOING_OCRS+ocrIndex%>"   
                            outGoingImage="/i/outgoing.gif"
                            inCommingImage="/i/incomming.gif"
                            biDirectionalImage="/i/bidirectional.gif"
                            scope="page"
                            activeNodes="false"
                       />                     
                     </td>
                 </tr>                
                 
       
                <TR>
                 <td width ="100%">     
                  <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                    <TR class=OraTabledata>
                       <td class="TableRowPromptTextLeft" width="20%" >Target Object Class</td>
                       <td class="OraFieldText">
                          <bean:write name="currOutgoingOCR" property="target.longName"/>
                        </td>
                    </tr>
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="20%">Relationship Type</td>
                       <td class="OraFieldText">
                         <bean:write name="currOutgoingOCR" property="type"/>
                       </td>
                    </tr>                     
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="20%" >Long Name</td>
                       <td class="OraFieldText">
                          <bean:write name="currOutgoingOCR" property="longName"/>
                       </td>
                    </tr>
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="20%" >Preferred Definition</td>
                       <td class="OraFieldText">
                         <bean:write name="currOutgoingOCR" property="preferredDefinition"/>
                       </td>
                    </tr>
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="20%">Workflow Status</td>
                       <td class="OraFieldText">
                        <bean:write name="currOutgoingOCR" property="workflowStatus"/>
                       </td>
                    </tr>  
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="20%">Version</td>
                       <td class="OraFieldText">
                         <bean:write name="currOutgoingOCR" property="version"/>
                       </td>
                    </tr>  
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="20%">Context</td>
                       <td class="OraFieldText">
                         <bean:write name="currOutgoingOCR" property="context.name"/>
                       </td>
                    </tr>                
                  </table>  
                 </td>

              </TR> 
              <tr>
               <td colspan=2>
                 <table cellpadding="0" cellspacing="0" width="100%" align="center">
                   <tr>
                     <td class="OraHeaderSubSubSub" width="100%">&nbsp;</td>
                   </tr>
                 </table>
               </td>
              </tr>              
              <tr>
               <td colspan=2>
                 <table cellpadding="0" cellspacing="0" width="100%" align="center">
                   <tr>
                     <td class="OraHeaderSubSubSub" width="100%">Alternate Names for Object Class: <bean:write name="currOutgoingOCR" property="target.longName"/></td>
                   </tr>
                 </table>
               </td>
              </tr>
              <tr>
                 <td colspan=2>
                  <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                    <TR class=OraTableColumnHeader>
                       <th class="OraTableColumnHeader"  >Alternate Names</th>
                       <th class="OraTableColumnHeader">Type</th>
                       <th class="OraTableColumnHeader">Context</th>
                     </TR>   
                    <logic:notEmpty  name="currOutgoingOCR" property="target.alternateNames" > 
                      <logic:iterate id="alternateName" name="currOutgoingOCR" property="target.alternateNames" type="gov.nih.nci.ncicb.cadsr.domain.AlternateName" indexId="nameIndex" >                                 
                        <TR class=OraTabledata>
                           <td class="OraFieldText">
                             <bean:write name="alternateName" property="name"/>
                            </td>
                           <td class="OraFieldText">
                             <bean:write name="alternateName" property="type"/>
                            </td>
                           <td class="OraFieldText">
                             <bean:write name="alternateName" property="context.name"/>
                            </td>                     
                         </TR>  
                     </logic:iterate>
                    </logic:notEmpty>
                   <logic:empty  name="currOutgoingOCR" property="target.alternateNames" > 
                        <TR class=OraTabledata>
                           <td colspan=3 class="OraFieldText">No Alternate names for this Object Class exist</td>
                         </TR>  
                    </logic:empty>                    
                  </table> 
                </td>
               </tr>
              <tr>
               <td colspan=2>
                 <table cellpadding="0" cellspacing="0" width="100%" align="center">
                   <tr>
                     <td class="OraHeaderSubSubSub" width="100%">&nbsp;</td>
                   </tr>
                 </table>
               </td>
              </tr>                
               <tr>
                <td class="OraHeaderSubSubSub" vAlign=bottom align=left colspan=2 width="100%"> 
                  Using Projects
                </td>
               </tr>
              <tr>
                <td vAlign=top colspan=2 width="100%">
                 <logic:notEmpty  name="projects" >
     
                  <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                   <TR class=OraTabledata>
                    <td class="OraFieldText">                         
                         <logic:iterate id="currProject" name="projects" type="gov.nih.nci.ncicb.cadsr.resource.Project" indexId="prIndex" >
                           <UL>
                                 <li class="OraFieldText"><bean:write name="currProject" property="name"/>(Project)</li>
                                 <logic:notEmpty  name="currProject" property="children">
                                  <logic:iterate id="currSubProject" name="currProject" property="children" type="gov.nih.nci.ncicb.cadsr.resource.Project" indexId="subIndex" >
                                     <ul>
                                       <li  class="OraFieldText"><bean:write name="currSubProject" property="name"/>(SubProject)</li>
                                       <logic:notEmpty  name="currSubProject" property="packages">
                                        <ul>
                                        <logic:iterate id="currPackage" name="currSubProject" property="packages" type="gov.nih.nci.ncicb.cadsr.resource.OCRPackage" indexId="pkIndex" >
                                             <li class="OraFieldText"> <bean:write name="currPackage" property="name"/>(Package)</li>                                                  
                                        </logic:iterate>
                                        </ul>
                                       </logic:notEmpty> 
                                      </ul>
                                  </logic:iterate>                               
                                 </logic:notEmpty>
                           </UL>
                         </logic:iterate>
                     </td>
                    </tr>
                    </table>
                    </logic:notEmpty>                    
                     <logic:empty  name="projects" > 
                       <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTabledata>
                           <td  class="OraFieldText">Currently not used by any Projects</td>
                         </TR>  
                        </table>
                      </logic:empty>
                  </td>
              </tr>
              
             </table>  
          </td>
       </tr>
      </table>
      <br>
     </logic:iterate>
    </logic:notEmpty>
    <logic:empty  name="<%=UmlBrowserFormConstants.OUT_GOING_OCRS%>" scope="session">  
       <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
        <TR class=OraTabledata>
           <td colspan=2 class="OraFieldText">No outgoing associations exits for this Object Class</td>
        </TR> 
       </table>
    </logic:empty>
      <!-- Out going end -->

      <br>
      <!-- Incoming Start -->
      <A NAME="incoming"></A> 
            
      <table cellpadding="0" cellspacing="0" width="100%" align="center">
        <tr>
          <td class="OraHeaderSubSub" width="100%">Incoming Associations</td>
        </tr>
        <tr  >
           <td width="100%" ><img height=1 src="<%=contextPath%>/i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>  
      </table>   
        <logic:notEmpty  name="<%=UmlBrowserFormConstants.IN_COMMING_OCRS%>" scope="session">  
          <logic:iterate id="currIncommingOCR" name="<%=UmlBrowserFormConstants.IN_COMMING_OCRS%>" type="gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship" indexId="ocrIndex" >                                 
             <%
               Collection projects = ObjectExtractor.getProjects(currIncommingOCR);     
               pageContext.setAttribute("projects",projects);
            %> 
     
           <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
            <tr class=OraTabledata>
             <td class=OraFieldText>
                 <table vAlign=top width="100%">
                     <tr  class=OraTabledata align="left">  
                       <% if(OCUtils.isNavigationAllowed(request,UmlBrowserFormConstants.OCR_NAVIGATION_BEAN,currIncommingOCR))
                       {
                       %>
                          <td class=OraFieldText colspan="2">
                              <a href="javascript:navigateOCR('<%=currIncommingOCR.getSource().getId()%>','<%=ocrIndex%>','<%=UmlBrowserFormConstants.IN_COMMING_OCRS%>')">Navigate to this association</a>
                          </td>              
                       <%}else{%>
                            <td class=OraFieldText colspan="2">
                              &nbsp;
                           </td>                
                      <% }%>                                           
                     </tr>
                  <tr  class=OraTabledata align="center">
                     <td class=OraFieldText colspan="2" align="center">
                       <cde:DefineNavigationCrumbs
                            beanId="<%=UmlBrowserFormConstants.IN_COMMING_OCRS+ocrIndex%>"   
                            direction="<%=UmlBrowserFormConstants.IN_COMMING_OCRS%>"
                            ocrId="currIncommingOCR"
                       />                     
                       <cde:ocrNavigation
                            navigationListId="<%=UmlBrowserFormConstants.IN_COMMING_OCRS+ocrIndex%>"   
                            outGoingImage="/i/outgoing.gif"
                            inCommingImage="/i/incomming.gif"
                            biDirectionalImage="/i/bidirectional.gif"
                            scope="page"
                            activeNodes="false"
                       />                     
                     </td>
                 </tr>                        
                    <TR>
                     <td width ="100%">     
                      <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTabledata>
                           <td class="TableRowPromptTextLeft" width="20%" >Source Object Class</td>
                           <td class="OraFieldText">
                              <bean:write name="currIncommingOCR" property="source.longName"/>
                            </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%">Relationship Type</td>
                           <td class="OraFieldText">
                             <bean:write name="currIncommingOCR" property="type"/>
                           </td>
                        </tr>                         
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%" >Long Name</td>
                           <td class="OraFieldText">
                              <bean:write name="currIncommingOCR" property="longName"/>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%" >Preferred Definition</td>
                           <td class="OraFieldText">
                             <bean:write name="currIncommingOCR" property="preferredDefinition"/>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%">Workflow Status</td>
                           <td class="OraFieldText">
                            <bean:write name="currIncommingOCR" property="workflowStatus"/>
                           </td>
                        </tr>  
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%">Version</td>
                           <td class="OraFieldText">
                             <bean:write name="currIncommingOCR" property="version"/>
                           </td>
                        </tr>  
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%">Context</td>
                           <td class="OraFieldText">
                             <bean:write name="currIncommingOCR" property="context.name"/>
                           </td>
                        </tr>                
                      </table>  
                     </td>

                  </TR>  
                  
              <tr>
               <td colspan=2>
                 <table cellpadding="0" cellspacing="0" width="100%" align="center">
                   <tr>
                     <td class="OraHeaderSubSubSub" width="100%">&nbsp;</td>
                   </tr>
                 </table>
               </td>
              </tr>              
              <tr>
               <td colspan=2>
                 <table cellpadding="0" cellspacing="0" width="100%" align="center">
                   <tr>
                     <td class="OraHeaderSubSubSub" width="100%">Alternate Names for Object Class: <bean:write name="currIncommingOCR" property="source.longName"/></td>
                   </tr>
                 </table>
               </td>
              </tr>
              
                  <tr>
                   <td colspan=2>
                    <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                      <TR class=OraTableColumnHeader>
                         <th class="OraTableColumnHeader"  >Alternate Names</th>
                         <th class="OraTableColumnHeader">Type</th>
                         <th class="OraTableColumnHeader">Context</th>
                       </TR>   
                      <logic:notEmpty  name="currIncommingOCR" property="source.alternateNames" > 
                        <logic:iterate id="alternateName" name="currIncommingOCR" property="source.alternateNames" type="gov.nih.nci.ncicb.cadsr.domain.AlternateName" indexId="nameIndex" >                                 
                          <TR class=OraTabledata>
                             <td class="OraFieldText">
                               <bean:write name="alternateName" property="name"/>
                              </td>
                             <td class="OraFieldText">
                               <bean:write name="alternateName" property="type"/>
                              </td>
                             <td class="OraFieldText">
                               <bean:write name="alternateName" property="context.name"/>
                              </td>                     
                           </TR>  
                       </logic:iterate>
                      </logic:notEmpty>
                     <logic:empty  name="currIncommingOCR" property="source.alternateNames" > 
                          <TR class=OraTabledata>
                             <td colspan=3 class="OraFieldText">No Alternate names for this Object Class exist</td>
                           </TR>  
                      </logic:empty>                    
                    </table> 
                  </td>
               </tr> 
              <tr>
               <td colspan=2>
                 <table cellpadding="0" cellspacing="0" width="100%" align="center">
                   <tr>
                     <td class="OraHeaderSubSubSub" width="100%">&nbsp;</td>
                   </tr>
                 </table>
               </td>
              </tr> 
               <tr>
                <td class="OraHeaderSubSubSub" vAlign=bottom align=left colspan=2 width="100%"> 
                  Using Projects
                </td>
               </tr>
              <tr>
                <td vAlign=top colspan=2 width="100%">
                 <logic:notEmpty  name="projects" >
     
                  <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                   <TR class=OraTabledata>
                    <td class="OraFieldText">                         
                         <logic:iterate id="currProject" name="projects" type="gov.nih.nci.ncicb.cadsr.resource.Project" indexId="prIndex" >
                           <UL>
                                 <li class="OraFieldText"><bean:write name="currProject" property="name"/>(Project)</li>
                                 <logic:notEmpty  name="currProject" property="children">
                                  <logic:iterate id="currSubProject" name="currProject" property="children" type="gov.nih.nci.ncicb.cadsr.resource.Project" indexId="subIndex" >
                                     <ul>
                                       <li  class="OraFieldText"><bean:write name="currSubProject" property="name"/>(SubProject)</li>
                                       <logic:notEmpty  name="currSubProject" property="packages">
                                        <ul>
                                        <logic:iterate id="currPackage" name="currSubProject" property="packages" type="gov.nih.nci.ncicb.cadsr.resource.OCRPackage" indexId="pkIndex" >
                                             <li class="OraFieldText"> <bean:write name="currPackage" property="name"/>(Package)</li>                                                  
                                        </logic:iterate>
                                        </ul>
                                       </logic:notEmpty> 
                                      </ul>
                                  </logic:iterate>                               
                                 </logic:notEmpty>
                           </UL>
                         </logic:iterate>
                     </td>
                    </tr>
                    </table>
                    </logic:notEmpty>                    
                     <logic:empty  name="projects" > 
                       <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTabledata>
                           <td  class="OraFieldText">Currently not used by any Projects</td>
                         </TR>  
                        </table>
                      </logic:empty> 
                      </td>
                  </tr>
                  
                 </table>  
              </td>
           </tr>
          </table>
          <br>
         </logic:iterate>
        </logic:notEmpty>
        <logic:empty  name="<%=UmlBrowserFormConstants.IN_COMMING_OCRS%>" scope="session">  
           <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
            <TR class=OraTabledata>
               <td colspan=2 class="OraFieldText">No incoming associations exits for this Object Class</td>
            </TR> 
           </table>
        </logic:empty>        
      <!--  Ingoing end -->      
      
      <br>
      <A NAME="bidirectionl"></A> 
            
      <table cellpadding="0" cellspacing="0" width="100%" align="center">
        <tr>
          <td class="OraHeaderSubSub" width="100%">Bidiectional Associations</td>
        </tr>
        <tr >
           <td width="100%" ><img height=1 src="<%=contextPath%>/i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>  
      </table>   
      <%
        ObjectClass currObjClass = (ObjectClass)request.getSession().getAttribute(UmlBrowserFormConstants.OBJECT_CLASS);
      %>
        <logic:notEmpty  name="<%=UmlBrowserFormConstants.BIDIRECTIONAL_OCRS%>" scope="session">  
          <logic:iterate id="currBidirectionalOCR" name="<%=UmlBrowserFormConstants.BIDIRECTIONAL_OCRS%>" type="gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship" indexId="ocrIndex" >                                 
             <%
               Collection projects = ObjectExtractor.getProjects(currBidirectionalOCR);   
               pageContext.setAttribute("projects",projects);
            %> 
           <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
            <tr class=OraTabledata>
             <td class=OraFieldText>
                 <table vAlign=top width="100%">
                     <tr  class=OraTabledata align="left"> 
                     
                         <% if(OCUtils.isNavigationAllowed(request,UmlBrowserFormConstants.OCR_NAVIGATION_BEAN,currBidirectionalOCR))
                         {
                         %>
                            <td class=OraFieldText colspan="2">
                                <a href="javascript:navigateOCR('<%=OCUtils.getBiderectionalTarget(currBidirectionalOCR,currObjClass).getId()%>','<%=ocrIndex%>','<%=UmlBrowserFormConstants.BIDIRECTIONAL_OCRS%>')">Navigate to this association</a>
                            </td>              
                         <%}else{%>
                              <td class=OraFieldText colspan="2">
                                &nbsp;
                             </td>                
                        <% }%>                                                
                     </tr>
                      <tr  class=OraTabledata align="center">
                         <td class=OraFieldText colspan="2" align="center">
                           <cde:DefineNavigationCrumbs
                                beanId="<%=UmlBrowserFormConstants.BIDIRECTIONAL_OCRS+ocrIndex%>"   
                                direction="<%=UmlBrowserFormConstants.BIDIRECTIONAL_OCRS%>"
                                ocrId="currBidirectionalOCR"
                           />                     
                           <cde:ocrNavigation
                                navigationListId="<%=UmlBrowserFormConstants.BIDIRECTIONAL_OCRS+ocrIndex%>"   
                                outGoingImage="/i/outgoing.gif"
                                inCommingImage="/i/incomming.gif"
                                biDirectionalImage="/i/bidirectional.gif"
                                scope="page"
                                activeNodes="false"
                           />                     
                         </td>
                     </tr>                      
                    <TR>
                     <td width ="100%">     
                      <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTabledata>
                          <% ObjectClass biTargetOC = OCUtils.getBiderectionalTarget(currBidirectionalOCR,currObjClass);
                             pageContext.setAttribute("biTargetOC",biTargetOC); %>
                           <td class="TableRowPromptTextLeft" width="20%" >Associated Object Class
                            </td>
                           <td class="OraFieldText">
                              
                              <%=biTargetOC.getLongName()%>                             
                            </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%">Relationship Type</td>
                           <td class="OraFieldText">
                             <bean:write name="currBidirectionalOCR" property="type"/>
                           </td>
                        </tr>                         
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%" >Long Name</td>
                           <td class="OraFieldText">
                              <bean:write name="currBidirectionalOCR" property="longName"/>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%" >Preferred Definition</td>
                           <td class="OraFieldText">
                             <bean:write name="currBidirectionalOCR" property="preferredDefinition"/>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%">Workflow Status</td>
                           <td class="OraFieldText">
                            <bean:write name="currBidirectionalOCR" property="workflowStatus"/>
                           </td>
                        </tr>  
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%">Version</td>
                           <td class="OraFieldText">
                             <bean:write name="currBidirectionalOCR" property="version"/>
                           </td>
                        </tr>  
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="20%">Context</td>
                           <td class="OraFieldText">
                             <bean:write name="currBidirectionalOCR" property="context.name"/>
                           </td>
                        </tr>                
                      </table>  
                     </td>

                  </TR>  
              <tr>
               <td colspan=2>
                 <table cellpadding="0" cellspacing="0" width="100%" align="center">
                   <tr>
                     <td class="OraHeaderSubSubSub" width="100%">&nbsp;</td>
                   </tr>
                 </table>
               </td>
              </tr>              
              <tr>
               <td colspan=2>
                 <table cellpadding="0" cellspacing="0" width="100%" align="center">
                   <tr>
                     <td class="OraHeaderSubSubSub" width="100%">Alternate Names for Object Class: <bean:write name="biTargetOC" property="longName"/></td>
                   </tr>
                 </table>
               </td>
              </tr>
              
                  <tr>
                   <td colspan=2>
                    <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                      <TR class=OraTableColumnHeader>
                         <th class="OraTableColumnHeader"  >Alternate Names</th>
                         <th class="OraTableColumnHeader">Type</th>
                         <th class="OraTableColumnHeader">Context</th>
                       </TR>   
                      <logic:notEmpty  name="biTargetOC" property="alternateNames" > 
                        <logic:iterate id="alternateName" name="biTargetOC" property="alternateNames" type="gov.nih.nci.ncicb.cadsr.domain.AlternateName" indexId="nameIndex" >                                 
                          <TR class=OraTabledata>
                             <td class="OraFieldText">
                               <bean:write name="alternateName" property="name"/>
                              </td>
                             <td class="OraFieldText">
                               <bean:write name="alternateName" property="type"/>
                              </td>
                             <td class="OraFieldText">
                               <bean:write name="alternateName" property="context.name"/>
                              </td>                     
                           </TR>  
                       </logic:iterate>
                      </logic:notEmpty>
                     <logic:empty  name="biTargetOC" property="alternateNames" > 
                          <TR class=OraTabledata>
                             <td colspan=3 class="OraFieldText">No Alternate names for this Object Class exist</td>
                           </TR>  
                      </logic:empty>                    
                    </table> 
                  </td>
               </tr> 
              <tr>
               <td colspan=2>
                 <table cellpadding="0" cellspacing="0" width="100%" align="center">
                   <tr>
                     <td class="OraHeaderSubSubSub" width="100%">&nbsp;</td>
                   </tr>
                 </table>
               </td>
              </tr>                
               <tr>
                <td class="OraHeaderSubSubSub" vAlign=bottom align=left colspan=2 width="100%"> 
                  Using Projects
                </td>
               </tr>
              <tr>
                <td vAlign=top colspan=2 width="100%">
                 <logic:notEmpty  name="projects" >
     
                  <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                   <TR class=OraTabledata>
                    <td class="OraFieldText">                         
                         <logic:iterate id="currProject" name="projects" type="gov.nih.nci.ncicb.cadsr.resource.Project" indexId="prIndex" >
                           <UL>
                                 <li class="OraFieldText"><bean:write name="currProject" property="name"/>(Project)</li>
                                 <logic:notEmpty  name="currProject" property="children">
                                  <logic:iterate id="currSubProject" name="currProject" property="children" type="gov.nih.nci.ncicb.cadsr.resource.Project" indexId="subIndex" >
                                     <ul>
                                       <li  class="OraFieldText"><bean:write name="currSubProject" property="name"/>(SubProject)</li>
                                       <logic:notEmpty  name="currSubProject" property="packages">
                                        <ul>
                                        <logic:iterate id="currPackage" name="currSubProject" property="packages" type="gov.nih.nci.ncicb.cadsr.resource.OCRPackage" indexId="pkIndex" >
                                             <li class="OraFieldText"> <bean:write name="currPackage" property="name"/>(Package)</li>                                                  
                                        </logic:iterate>
                                        </ul>
                                       </logic:notEmpty> 
                                      </ul>
                                  </logic:iterate>                               
                                 </logic:notEmpty>
                           </UL>
                         </logic:iterate>
                     </td>
                    </tr>
                    </table>
                    </logic:notEmpty>                    
                     <logic:empty  name="projects" > 
                       <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTabledata>
                           <td  class="OraFieldText">Currently not used by any Projects</td>
                         </TR>  
                        </table>
                      </logic:empty>
                      </td>
                  </tr>
                  
                 </table>  
              </td>
           </tr>
          </table>
          <br>
         </logic:iterate>
        </logic:notEmpty>
        <logic:empty  name="<%=UmlBrowserFormConstants.BIDIRECTIONAL_OCRS%>" scope="session">  
           <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
            <TR class=OraTabledata>
               <td colspan=2 class="OraFieldText">No bidirectional associations exits for this Object Class</td>
            </TR> 
           </table>
        </logic:empty>        
        <br>
        <br>
      <!--  Ingoing end -->       
</html:form>
</body>
</html>
