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
<html:hidden value="" property="<%=UmlBrowserNavigationConstants.METHOD_PARAM%>"/>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td align="left" nowrap>

    <html:img page="/i/graphic6.gif" border="0" />
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
          <td valign="top" align="left" width="100%" class="AbbreviatedTextBold">
            All Associations displayed below are for Object Class "<bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="longName"/>".
            Details of this class can be seen under "Object Class" tab.
            Outgoing associations have "<bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="longName"/>" as the source Object Class;
            Incoming associations have "<bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="longName"/>" as the target Object Class;
            Bidirection associations have "<bean:write name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" property="longName"/>" as target and source Object Class.
          </td>
        </tr>  
</table>

      <cde:ocrNavigation
              navigationListId="<%=UmlBrowserFormConstants.OCR_NAVIGATION_BEAN%>"   
              outGoingImage="/i/outgoing.gif"
              inCommingImage="/i/incomming.gif"
              biDirectionalImage="/i/bidirectional.gif"
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
           List subs = ObjectExtractor.getSubProjects(currOutgoingOCR);
           pageContext.setAttribute("subprojects",subs);
           List packages = ObjectExtractor.getPackages(currOutgoingOCR);
           pageContext.setAttribute("packages",packages);                   
        %> 
 
       <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
        <tr class=OraTabledata>
         <td class=OraFieldText>
             <table vAlign=top width="100%">
                 <tr  class=OraTabledata align="left">
                    <td class=OraFieldText colspan="2">
                        <a href="javascript:navigateOCR('<%=currOutgoingOCR.getTarget().getId()%>','<%=ocrIndex%>','<%=UmlBrowserFormConstants.OUT_GOING_OCRS%>')">Navigate</a>
                    </td>         
                 </tr>
                <TR>
                 <td width ="50%">     
                  <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                    <TR class=OraTabledata>
                       <td class="TableRowPromptTextLeft" width="40%" >Target Object Class</td>
                       <td class="OraFieldText">
                          <bean:write name="currOutgoingOCR" property="target.longName"/>
                        </td>
                    </tr>
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="40%" >Long Name</td>
                       <td class="OraFieldText">
                          <bean:write name="currOutgoingOCR" property="longName"/>
                       </td>
                    </tr>
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="40%" >Prefferd Definition</td>
                       <td class="OraFieldText">
                         <bean:write name="currOutgoingOCR" property="preferredDefinition"/>
                       </td>
                    </tr>
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="40%">Workflow Status</td>
                       <td class="OraFieldText">
                        <bean:write name="currOutgoingOCR" property="workflowStatus"/>
                       </td>
                    </tr>  
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="40%">Version</td>
                       <td class="OraFieldText">
                         <bean:write name="currOutgoingOCR" property="version"/>
                       </td>
                    </tr>  
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="40%">Context</td>
                       <td class="OraFieldText">
                         <bean:write name="currOutgoingOCR" property="context.name"/>
                       </td>
                    </tr>                
                  </table>  
                 </td>
                 <td width="50%" vAlign=top>
                  <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                    <TR class=OraTabledata>
                       <td class="TableRowPromptTextLeft" width="40%">Source Multiplicity</td>
                       <td class="OraFieldText">
                          <%=OCUtils.getSourceMultiplicityDisplayString(currOutgoingOCR)%>
                       </td>
                    </tr>
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="40%">Source Role</td>
                       <td class="OraFieldText">
                         <bean:write name="currOutgoingOCR" property="sourceRole"/>
                       </td>
                    </tr>
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="40%">Target Multiplicity</td>
                       <td class="OraFieldText">
                        <%=OCUtils.getSourceMultiplicityDisplayString(currOutgoingOCR)%>
                       </td>
                    </tr>
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="40%">Target Role</td>
                       <td class="OraFieldText">
                         <bean:write name="currOutgoingOCR" property="targetRole"/>
                      </td>
                    </tr>
                    <TR class=OraTabledata>
                       <td class="OraTableColumnHeader" width="40%">Relationship Type</td>
                       <td class="OraFieldText">
                         <bean:write name="currOutgoingOCR" property="type"/>
                       </td>
                    </tr>                
                  </table>             
                 </td>
              </TR>            

              <tr>
                <td vAlign=top width="50%">
                  <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                    <TR class=OraTableColumnHeader>
                       <th class="OraTableColumnHeader" width="50%" >Project</th>
                       <th class="OraTableColumnHeader">Sub Project</th>
                     </TR>   
                    <logic:notEmpty  name="subprojects" > 
                      <logic:iterate id="currSubProject" name="subprojects" type="gov.nih.nci.ncicb.cadsr.resource.Project" indexId="subIndex" >                                 
                        <TR class=OraTabledata>
                           <td class="OraFieldText">
                             <bean:write name="currSubProject" property="parent.longName"/>
                            </td>
                           <td class="OraFieldText">
                             <bean:write name="currSubProject" property="longName"/>
                            </td>
                         </TR>  
                     </logic:iterate>
                    </logic:notEmpty>
                   <logic:empty  name="subprojects" > 
                        <TR class=OraTabledata>
                           <td colspan=2 class="OraFieldText">Currently not used by any Projects/Subprojects</td>
                         </TR>  
                    </logic:empty>                    
                  </table>
                </td>
                <td vAlign=top width="50%">
                  <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=right border=0 class="OraBGAccentVeryDark">
                    <TR class=OraTabledata>
                       <th class="OraTableColumnHeader" width="50%" >Project</th>
                       <th class="OraTableColumnHeader">Package</th>
                     </TR>   
                    <logic:notEmpty  name="packages" > 
                      <logic:iterate id="currPackage" name="packages" type="gov.nih.nci.ncicb.cadsr.resource.OCRPackage" indexId="subIndex" >                                                      
                        <TR class=OraTabledata>
                           <td class="OraFieldText">
                             <bean:write name="currPackage" property="parent.longName"/>
                          </td>
                           <td class="OraFieldText">
                             <bean:write name="currPackage" property="longName"/>
                           </td>
                         </TR>   
                      </logic:iterate>
                  </logic:notEmpty>
                   <logic:empty  name="packages" > 
                        <TR class=OraTabledata>
                           <td colspan=2 class="OraFieldText">Currently not assigened to any Packages</td>
                         </TR>  
                    </logic:empty>                    
                  </table>
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
               List subs = ObjectExtractor.getSubProjects(currIncommingOCR);
               pageContext.setAttribute("subprojects",subs);
               List packages = ObjectExtractor.getPackages(currIncommingOCR);
               pageContext.setAttribute("packages",packages);                   
            %> 
     
           <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
            <tr class=OraTabledata>
             <td class=OraFieldText>
                 <table vAlign=top width="100%">
                     <tr  class=OraTabledata align="left">
                        <td class=OraFieldText colspan="2">
                         <a href="javascript:navigateOCR('<%=currIncommingOCR.getTarget().getId()%>','<%=ocrIndex%>','<%=UmlBrowserFormConstants.IN_COMMING_OCRS%>')">Navigate</a>
                        </td>         
                     </tr>
                    <TR>
                     <td width ="50%">     
                      <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTabledata>
                           <td class="TableRowPromptTextLeft" width="40%" >Target Object Class</td>
                           <td class="OraFieldText">
                              <bean:write name="currIncommingOCR" property="target.longName"/>
                            </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%" >Long Name</td>
                           <td class="OraFieldText">
                              <bean:write name="currIncommingOCR" property="longName"/>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%" >Prefferd Definition</td>
                           <td class="OraFieldText">
                             <bean:write name="currIncommingOCR" property="preferredDefinition"/>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Workflow Status</td>
                           <td class="OraFieldText">
                            <bean:write name="currIncommingOCR" property="workflowStatus"/>
                           </td>
                        </tr>  
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Version</td>
                           <td class="OraFieldText">
                             <bean:write name="currIncommingOCR" property="version"/>
                           </td>
                        </tr>  
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Context</td>
                           <td class="OraFieldText">
                             <bean:write name="currIncommingOCR" property="context.name"/>
                           </td>
                        </tr>                
                      </table>  
                     </td>
                     <td width="50%" vAlign=top>
                      <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTabledata>
                           <td class="TableRowPromptTextLeft" width="40%">Source Multiplicity</td>
                           <td class="OraFieldText">
                              <%=OCUtils.getSourceMultiplicityDisplayString(currIncommingOCR)%>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Source Role</td>
                           <td class="OraFieldText">
                             <bean:write name="currOutgoingOCR" property="sourceRole"/>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Target Multiplicity</td>
                           <td class="OraFieldText">
                            <%=OCUtils.getSourceMultiplicityDisplayString(currIncommingOCR)%>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Target Role</td>
                           <td class="OraFieldText">
                             <bean:write name="currIncommingOCR" property="targetRole"/>
                          </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Relationship Type</td>
                           <td class="OraFieldText">
                             <bean:write name="currIncommingOCR" property="type"/>
                           </td>
                        </tr>                
                      </table>             
                     </td>
                  </TR>            
    
                  <tr>
                    <td vAlign=top width="50%">
                      <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTableColumnHeader>
                           <th class="OraTableColumnHeader" width="50%" >Project</th>
                           <th class="OraTableColumnHeader">Sub Project</th>
                         </TR>   
                        <logic:notEmpty  name="subprojects" > 
                          <logic:iterate id="currSubProject" name="subprojects" type="gov.nih.nci.ncicb.cadsr.resource.Project" indexId="subIndex" >                                 
                            <TR class=OraTabledata>
                               <td class="OraFieldText">
                                 <bean:write name="currSubProject" property="parent.longName"/>
                                </td>
                               <td class="OraFieldText">
                                 <bean:write name="currSubProject" property="longName"/>
                                </td>
                             </TR>  
                         </logic:iterate>
                        </logic:notEmpty>
                       <logic:empty  name="subprojects" > 
                            <TR class=OraTabledata>
                               <td colspan=2 class="OraFieldText">Currently not used by any Projects/Subprojects</td>
                             </TR>  
                        </logic:empty>                    
                      </table>
                    </td>
                    <td vAlign=top width="50%">
                      <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=right border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTabledata>
                           <th class="OraTableColumnHeader" width="50%" >Project</th>
                           <th class="OraTableColumnHeader">Package</th>
                         </TR>   
                        <logic:notEmpty  name="packages" > 
                          <logic:iterate id="currPackage" name="packages" type="gov.nih.nci.ncicb.cadsr.resource.OCRPackage" indexId="subIndex" >                                                      
                            <TR class=OraTabledata>
                               <td class="OraFieldText">
                                 <bean:write name="currPackage" property="parent.longName"/>
                              </td>
                               <td class="OraFieldText">
                                 <bean:write name="currPackage" property="longName"/>
                               </td>
                             </TR>   
                          </logic:iterate>
                      </logic:notEmpty>
                       <logic:empty  name="packages" > 
                            <TR class=OraTabledata>
                               <td colspan=2 class="OraFieldText">Currently not assigened to any Packages</td>
                             </TR>  
                        </logic:empty>                    
                      </table>
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
        <logic:notEmpty  name="<%=UmlBrowserFormConstants.BIDIRECTIONAL_OCRS%>" scope="session">  
          <logic:iterate id="currBidirectionalOCR" name="<%=UmlBrowserFormConstants.BIDIRECTIONAL_OCRS%>" type="gov.nih.nci.ncicb.cadsr.domain.ObjectClassRelationship" indexId="ocrIndex" >                                 
             <%
               List subs = ObjectExtractor.getSubProjects(currBidirectionalOCR);
               pageContext.setAttribute("subprojects",subs);
               List packages = ObjectExtractor.getPackages(currBidirectionalOCR);
               pageContext.setAttribute("packages",packages);                   
            %> 
     
           <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=center border=0 class="OraBGAccentVeryDark">
            <tr class=OraTabledata>
             <td class=OraFieldText>
                 <table vAlign=top width="100%">
                     <tr  class=OraTabledata align="left">
                        <td class=OraFieldText colspan="2">
                         <a href="javascript:navigateOCR('<%=currBidirectionalOCR.getTarget().getId()%>','<%=ocrIndex%>','<%=UmlBrowserFormConstants.BIDIRECTIONAL_OCRS%>')">Navigate</a>
                        </td>         
                     </tr>
                    <TR>
                     <td width ="50%">     
                      <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTabledata>
                           <td class="TableRowPromptTextLeft" width="40%" >Target Object Class</td>
                           <td class="OraFieldText">
                              <bean:write name="currBidirectionalOCR" property="target.longName"/>
                            </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%" >Long Name</td>
                           <td class="OraFieldText">
                              <bean:write name="currBidirectionalOCR" property="longName"/>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%" >Prefferd Definition</td>
                           <td class="OraFieldText">
                             <bean:write name="currBidirectionalOCR" property="preferredDefinition"/>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Workflow Status</td>
                           <td class="OraFieldText">
                            <bean:write name="currBidirectionalOCR" property="workflowStatus"/>
                           </td>
                        </tr>  
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Version</td>
                           <td class="OraFieldText">
                             <bean:write name="currBidirectionalOCR" property="version"/>
                           </td>
                        </tr>  
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Context</td>
                           <td class="OraFieldText">
                             <bean:write name="currBidirectionalOCR" property="context.name"/>
                           </td>
                        </tr>                
                      </table>  
                     </td>
                     <td width="50%" vAlign=top>
                      <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTabledata>
                           <td class="TableRowPromptTextLeft" width="40%">Source Multiplicity</td>
                           <td class="OraFieldText">
                              <%=OCUtils.getSourceMultiplicityDisplayString(currBidirectionalOCR)%>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Source Role</td>
                           <td class="OraFieldText">
                             <bean:write name="currBidirectionalOCR" property="sourceRole"/>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Target Multiplicity</td>
                           <td class="OraFieldText">
                            <%=OCUtils.getSourceMultiplicityDisplayString(currBidirectionalOCR)%>
                           </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Target Role</td>
                           <td class="OraFieldText">
                             <bean:write name="currBidirectionalOCR" property="targetRole"/>
                          </td>
                        </tr>
                        <TR class=OraTabledata>
                           <td class="OraTableColumnHeader" width="40%">Relationship Type</td>
                           <td class="OraFieldText">
                             <bean:write name="currBidirectionalOCR" property="type"/>
                           </td>
                        </tr>                
                      </table>             
                     </td>
                  </TR>            
    
                  <tr>
                    <td vAlign=top width="50%">
                      <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=left border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTableColumnHeader>
                           <th class="OraTableColumnHeader" width="50%" >Project</th>
                           <th class="OraTableColumnHeader">Sub Project</th>
                         </TR>   
                        <logic:notEmpty  name="subprojects" > 
                          <logic:iterate id="currSubProject" name="subprojects" type="gov.nih.nci.ncicb.cadsr.resource.Project" indexId="subIndex" >                                 
                            <TR class=OraTabledata>
                               <td class="OraFieldText">
                                 <bean:write name="currSubProject" property="parent.longName"/>
                                </td>
                               <td class="OraFieldText">
                                 <bean:write name="currSubProject" property="longName"/>
                                </td>
                             </TR>  
                         </logic:iterate>
                        </logic:notEmpty>
                       <logic:empty  name="subprojects" > 
                            <TR class=OraTabledata>
                               <td colspan=2 class="OraFieldText">Currently not used by any Projects/Subprojects</td>
                             </TR>  
                        </logic:empty>                    
                      </table>
                    </td>
                    <td vAlign=top width="50%">
                      <table vAlign=top cellSpacing=1 cellPadding=1  width="100%" align=right border=0 class="OraBGAccentVeryDark">
                        <TR class=OraTabledata>
                           <th class="OraTableColumnHeader" width="50%" >Project</th>
                           <th class="OraTableColumnHeader">Package</th>
                         </TR>   
                        <logic:notEmpty  name="packages" > 
                          <logic:iterate id="currPackage" name="packages" type="gov.nih.nci.ncicb.cadsr.resource.OCRPackage" indexId="subIndex" >                                                      
                            <TR class=OraTabledata>
                               <td class="OraFieldText">
                                 <bean:write name="currPackage" property="parent.longName"/>
                              </td>
                               <td class="OraFieldText">
                                 <bean:write name="currPackage" property="longName"/>
                               </td>
                             </TR>   
                          </logic:iterate>
                      </logic:notEmpty>
                       <logic:empty  name="packages" > 
                            <TR class=OraTabledata>
                               <td colspan=2 class="OraFieldText">Currently not assigened to any Packages</td>
                             </TR>  
                        </logic:empty>                    
                      </table>
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
