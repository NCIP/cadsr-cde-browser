
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
<%@ page import="gov.nih.nci.ncicb.cadsr.util.* " %>

<html>
  <head>
    <TITLE>Object Class Details</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/jsLib/checkbox.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript1.1" SRC='<html:rewrite page="/jsLib/newWinJS.js"/>'></SCRIPT> 
  </head>
  
  <body topmargin="0">
        <%@ include  file="common_header_inc.jsp" %>
    
    <%  String contextPath = request.getContextPath();
        CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
    %>
    
 
 
    <html:form action="/umlbrowser/ocDetailsAction.do">
       <html:hidden property="<%=UmlBrowserFormConstants.OC_IDSEQ%>"/>
       <html:hidden value="" property="<%=UmlBrowserNavigationConstants.METHOD_PARAM%>"/>
        
       
       
       <jsp:include page="mltitab_inc.jsp" flush="true">
         <jsp:param name="label" value="Object&nbsp;Class"/>
       </jsp:include>
       <table cellpadding="0" cellspacing="0" width="80%" align="center" border="0">
         <tr>
           <td>&nbsp;</td>
         </tr>
         <tr>
           <td>
             <a class="link" href="#objectClass">Object Class Details</a>
           </td>
           <td>
             <a class="link" href="#concepts">Concepts</a>
           </td>
           <td>
             <a class="link" href="#inheritance">Inheritance</a>
           </td>          
           <td>
             <a class="link" href="#classification">Classification</a>
           </td>          
           <td>
             <a class="link" href="#alternateNames">Alternate Names</a>
           </td>
           <td>
             <a class="link" href="#alternateDefinitions">Alternate Definitions</a>
           </td>
         </tr>
         <tr>
           <td>&nbsp;</td>
         </tr>
       </table>
       <logic:present name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>">
         <bean:define id="oc" name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>" scope="session"/>
         <br>
         <A NAME="objectClass"/>
         <table cellpadding="0" cellspacing="0" width="100%" align="center">
           <tr>
             <td class="OraHeaderSubSub" width="100%">Object Class Details</td>
           </tr>
           <tr>
             <td width="100%">
               <img height="1" src="<%=contextPath%>/i/beigedot.gif" width="99%" align="top" border="0"/>
             </td>
           </tr>
         </table>
         <table valign="top" width="90%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
           <tr class="OraTabledata">
             <td class="TableRowPromptText" width="20%">Public ID:</td>
             <td class="OraFieldText">
               <bean:write name="oc" property="publicId"/>
             </td>
           </tr>
           <tr class="OraTabledata">
             <td class="TableRowPromptText" width="20%">Long Name:</td>
             <td class="OraFieldText">
               <bean:write name="oc" property="longName"/>
             </td>
           </tr>
           <tr class="OraTabledata">
             <td class="TableRowPromptText" width="20%">Preferred Name:</td>
             <td class="OraFieldText">
               <bean:write name="oc" property="preferredName"/>
             </td>
           </tr>
           <tr class="OraTabledata">
             <td class="TableRowPromptText" width="20%">Context:</td>
             <td class="OraFieldText">
               <bean:write name="oc" property="context.name"/>
             </td>
           </tr>
           <tr class="OraTabledata">
             <td class="TableRowPromptText" width="20%">Version:</td>
             <td class="OraFieldText">
               <bean:write name="oc" property="version"/>
             </td>
           </tr>
         </table>
         <br>
         <A NAME="concepts"/>
         <table cellpadding="0" cellspacing="0" width="100%" align="center">
           <tr>
             <td class="OraHeaderSubSubSub" width="100%">Concepts</td>
           </tr>
           <tr>
             <td width="100%">
               <img height="1" src="<%=contextPath%>/i/beigedot.gif" width="99%" align="top" border="0"/>
             </td>
           </tr>
         </table>
         <logic:present name="oc" property="conceptDerivationRule">
           <logic:present name="oc" property="conceptDerivationRule.componentConcepts">
             <table width="90%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
               <tr class="OraTabledata">
                 <td class="OraTableColumnHeader">Concept Name</td>
                 <td class="OraTableColumnHeader">Concept Code</td>
                 <td class="OraTableColumnHeader">Public ID</td>
                 <td class="OraTableColumnHeader">Definition Source</td>
                 <td class="OraTableColumnHeader">EVS Source</td>
                 <td class="OraTableColumnHeader">Primary</td>
               </tr>
               <logic:iterate id="comp" name="oc" type="gov.nih.nci.ncicb.cadsr.domain.ComponentConcept" property="conceptDerivationRule.componentConcepts" indexId="ccIndex">
                 <tr class="OraTabledata">
                   <td class="OraFieldText">
                     <bean:write name="comp" property="concept.longName"/>
                   </td>
                   <td class="OraFieldText">
                      <%=OCUtils.getConceptCodeUrl(comp.getConcept(),params,"link")%>
                   </td>
                   <td class="OraFieldText">
                     <bean:write name="comp" property="concept.publicId"/>
                   </td>
                   <td class="OraFieldText">
                     <bean:write name="comp" property="concept.definitionSource"/>
                   </td>
                   <td class="OraFieldText"><bean:write name="comp" property="concept.evsSource"/></td>
                   <td class="OraFieldText"><bean:write name="comp" property="primaryFlag"/></td>
                 </tr>
               </logic:iterate>
             </table>
           </logic:present>
           <logic:notPresent name="oc" property="conceptDerivationRule.componentConcepts">
             <br>
             <table valign="bottom" cellpadding="0" cellspacing="0" width="90%" align="center">
               <tr valign="bottom">
                 <td class="OraHeaderSubSubSub" width="100%">Object Class Concepts</td>
               </tr>
             </table>
             <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
               <tr class="OraTabledata">
                 <td width="20%">Object Class does not have any Concepts.</td>
               </tr>
             </table>
           </logic:notPresent>
         </logic:present>
         <logic:notPresent name="oc" property="conceptDerivationRule">
           <br>
           <table valign="bottom" cellpadding="0" cellspacing="0" width="90%" align="center">
             <tr valign="bottom">
               <td class="OraHeaderSubSubSub" width="100%">Object Class Concepts</td>
             </tr>
           </table>
           <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
             <tr class="OraTabledata">
               <td width="20%">Object Class does not have any Concepts.</td>
             </tr>
           </table>
         </logic:notPresent>
         <!-- end of concepts -->
         
         
         <A NAME="inheritance"/>
         <br>
         <table cellpadding="0" cellspacing="0" width="100%" align="center">
           <tr>
             <td class="OraHeaderSubSubSub" width="100%">Inheritance</td>
           </tr>
           <tr>
             <td width="100%">
               <img height="1" src="<%=contextPath%>/i/beigedot.gif" width="90%" align="top" border="0"/>
             </td>
           </tr>
         </table>
         <table valign="top" width="90%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">             
               <logic:notEmpty name="<%=UmlBrowserFormConstants.SUPER_OBJECT_CLASSES%>" > 
                <TR class="OraTabledata">
                 <td class="OraFieldText"> 
                  <% String space = "&nbsp;&nbsp;"; %>
                  <bean:size id="size" name="<%=UmlBrowserFormConstants.SUPER_OBJECT_CLASSES%>"/>
                   <table>
                    <logic:iterate id="soc" name="<%=UmlBrowserFormConstants.SUPER_OBJECT_CLASSES%>"  type="gov.nih.nci.ncicb.cadsr.domain.ObjectClass" indexId="index" >                                                           
                     <% 
                      String urlPrefix = request.getContextPath();
                      String obclassurl = urlPrefix+"/umlbrowser/ocDetailsAction.do?"+UmlBrowserNavigationConstants.METHOD_PARAM+"="
                       +UmlBrowserNavigationConstants.OC_DETAILS
                       +"&"+UmlBrowserFormConstants.OC_IDSEQ+"="+soc.getId()
                       +"&"+UmlBrowserFormConstants.RESET_CRUMBS+"=true";
                     %>
                     <TR class="OraTabledata">
                      <td class="OraFieldText"> 
                       <% if(index.intValue()==0) {%>
                          <%=space%>
                           <a href="<%=obclassurl%>"> 
                             <bean:write name="soc" property="longName"/>(<bean:write name="soc" property="publicId"/>)
                            </a>                                           
                       <% } else if(index.intValue()!=size.intValue()-1) {%>
                         <%=space%><IMG src="<%=contextPath%>/i/inherit.gif" ALT="extended by">
                           <a href="<%=obclassurl%>"> 
                             <bean:write name="soc" property="longName"/>(<bean:write name="soc" property="publicId"/>)
                            </a>
                       <% } else { %>
                             <%=space%><IMG src="<%=contextPath%>/i/inherit.gif" ALT="extended by">
                             <bean:write name="soc" property="longName"/>(<bean:write name="soc" property="publicId"/>)
                       <% } 
                        space = space+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; %>
                       </td>
                     </tr>
                   </logic:iterate> 
                  </table>
                 </td>
                </tr>
             </logic:notEmpty> 
             <logic:empty name="<%=UmlBrowserFormConstants.SUPER_OBJECT_CLASSES%>" >
              <TR class="OraTabledata">
               <td colspan="2" class="OraFieldText">Does not Inherit from any Object Class</td>
              </TR>
             </logic:empty>
         </table>         
                   
         <!-- Classifications -->
         <A NAME="classification"/>
         <br>                
 	        <table cellpadding="0" cellspacing="0" width="100%" align="center">
 	          <tr>
 	            <td class="OraHeaderSubSubSub" width="100%">Classifications</td>
 	          </tr>
 	          <tr>
 	            <td width="100%">
 	              <img height="1" src="<%=contextPath%>/i/beigedot.gif" width="99%" align="top" border="0"/>
 	            </td>
 	          </tr>
             </table>
             <table vAlign="top" cellSpacing="1" cellPadding="1" width="90%" align="center" border="0" class="OraBGAccentVeryDark">
               <TR class="OraTableColumnHeader">
                 <th class="OraTableColumnHeader">CS* Preferred Name</th>
                 <th class="OraTableColumnHeader">CS* Definition</th>
                 <th class="OraTableColumnHeader">CS* Public ID</th>
                 <th class="OraTableColumnHeader">CSI* Name</th>
                 <th class="OraTableColumnHeader">CSI* Type</th>
               </TR>
                 <logic:notEmpty name="oc" property="acCsCsis">
                   <logic:iterate id="accscsi" name="oc" property="acCsCsis" type="gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem" >                                          
                       <TR class="OraTabledata">
                         <td class="OraFieldText">
                           <bean:write name="accscsi" property="csCsi.cs.preferredName"/>
                         </td>
                         <td class="OraFieldText">
                           <bean:write name="accscsi" property="csCsi.cs.preferredDefinition"/>
                         </td>
                         <td class="OraFieldText">
                           <bean:write name="accscsi" property="csCsi.cs.publicId"/>
                         </td>                        
                         <td class="OraFieldText">
                           <bean:write name="accscsi" property="csCsi.csi.name"/>
                         </td>
                         <td class="OraFieldText">
                           <bean:write name="accscsi" property="csCsi.csi.type"/>
                         </td>                        
                       </TR> 
                     </logic:iterate>
                   </logic:notEmpty>  
                   <logic:empty name="oc" property="acCsCsis">
                     <TR class="OraTabledata">
                       <td colspan="5" class="OraFieldText">No Classification exist for this Object Class</td>
                     </TR>
                   </logic:empty>                               
                </table>
                          
                          
         <A NAME="alternateNames"/>
         <br>
         <table cellpadding="0" cellspacing="0" width="100%" align="center">
           <tr>
             <td class="OraHeaderSubSubSub" width="100%">Alternate Names</td>
           </tr>
           <tr>
             <td width="100%">
               <img height="1" src="<%=contextPath%>/i/beigedot.gif" width="90%" align="top" border="0"/>
             </td>
           </tr>
         </table>
         <logic:notEmpty name="oc" property="alternateNames">
           <logic:iterate id="alternateName" name="oc" property="alternateNames" type="gov.nih.nci.ncicb.cadsr.domain.AlternateName" indexId="nameIndex">
             <table vAlign="top" cellSpacing="1" cellPadding="1" width="90%" align="center" border="0" class="OraBGAccentVeryDark">
                <tr class=OraTabledata>
                 <td class=OraFieldText >
                   <table vAlign="top" width="100%">
                      <tr class=OraTabledata>
                       <td class=OraFieldText >
                       <table vAlign="top" cellSpacing="1" cellPadding="1" width="100%" align="center" border="0" class="OraBGAccentVeryDark">
                         <TR class="OraTableColumnHeader">
                           <th class="OraTableColumnHeader">Name</th>
                           <th class="OraTableColumnHeader">Type</th>
                           <th class="OraTableColumnHeader">Context</th>
                         </TR>
                             <TR class="OraTabledata">
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
                       </table>
                     </td>
                   </tr>                  
                    <TR class="OraTabledata">
                      <td class="OraHeaderSubSubSub" width="100%">Classifications</td>
                    </tr>  
                    <tr class=OraTabledata>
                     <td class=OraFieldText >
                       <table vAlign="top" cellSpacing="1" cellPadding="1" width="100%" align="center" border="0" class="OraBGAccentVeryDark">
                         <TR class="OraTableColumnHeader">
                           <th class="OraTableColumnHeader">CS* Preferred Name</th>
                           <th class="OraTableColumnHeader">CS* Definition</th>
                           <th class="OraTableColumnHeader">CS* Public ID</th>
                           <th class="OraTableColumnHeader">CSI* Name</th>
                           <th class="OraTableColumnHeader">CSI* Type</th>
                         </TR>
                           <logic:notEmpty name="alternateName" property="csCsis">
                             <logic:iterate id="acscsi" name="alternateName" property="csCsis" type="gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem" >                                          
                                 <TR class="OraTabledata">
                                   <td class="OraFieldText">
                                     <bean:write name="acscsi" property="cs.preferredName"/>
                                   </td>
                                   <td class="OraFieldText">
                                     <bean:write name="acscsi" property="cs.preferredDefinition"/>
                                   </td>
                                   <td class="OraFieldText">
                                     <bean:write name="acscsi" property="cs.publicId"/>
                                   </td>                        
                                   <td class="OraFieldText">
                                     <bean:write name="acscsi" property="csi.name"/>
                                   </td>
                                   <td class="OraFieldText">
                                     <bean:write name="acscsi" property="csi.type"/>
                                   </td>                                     
                                 </TR> 
                               </logic:iterate>
                             </logic:notEmpty>  
                             <logic:empty name="alternateName" property="csCsis">
                               <TR class="OraTabledata">
                                 <td colspan="5" class="OraFieldText">No Classification exist for this Definition</td>
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
           <logic:empty name="oc" property="alternateNames">
             <table vAlign="top" cellSpacing="1" cellPadding="1" width="90%" align="center" border="0" class="OraBGAccentVeryDark">          
               <TR class="OraTabledata">
                 <td colspan="3" class="OraFieldText">No Alternate names for this Object Class exist</td>
               </TR>
             </table>
           </logic:empty>
         </table>
         
         
         
         <A NAME="alternateDefinitions"/>
         <br>
         <table cellpadding="0" cellspacing="0" width="100%" align="center">
           <tr>
             <td class="OraHeaderSubSubSub" width="100%">Alternate Definitions</td>
           </tr>
           <tr>
             <td width="100%">
               <img height="1" src="<%=contextPath%>/i/beigedot.gif" width="90%" align="top" border="0"/>
             </td>
           </tr>
         </table>
         <logic:notEmpty name="oc" property="definitions">
           <logic:iterate id="definition" name="oc" property="definitions" type="gov.nih.nci.ncicb.cadsr.domain.Definition" indexId="defIndex">
             <table vAlign="top" cellSpacing="1" cellPadding="1" width="90%" align="center" border="0" class="OraBGAccentVeryDark">
                <tr class=OraTabledata>
                 <td class=OraFieldText >
                   <table vAlign="top" width="100%">
                      <tr class=OraTabledata>
                       <td class=OraFieldText >
                       <table vAlign="top" cellSpacing="1" cellPadding="1" width="100%" align="center" border="0" class="OraBGAccentVeryDark">
                         <TR class="OraTableColumnHeader">
                           <th class="OraTableColumnHeader">Definition</th>
                           <th class="OraTableColumnHeader">Type</th>
                           <th class="OraTableColumnHeader">Context</th>
                         </TR>
                             <TR class="OraTabledata">
                               <td class="OraFieldText">
                                 <bean:write name="definition" property="definition"/>
                               </td>
                               <td class="OraFieldText">
                                 <bean:write name="definition" property="type"/>
                               </td>
                               <td class="OraFieldText">
                                 <bean:write name="definition" property="context.name"/>
                               </td>
                             </TR>            
                       </table>
                     </td>
                   </tr>                  
                    <TR class="OraTabledata">
                      <td class="OraHeaderSubSubSub" width="100%">Classifications</td>
                    </tr>  
                    <tr class=OraTabledata>
                     <td class=OraFieldText >
                       <table vAlign="top" cellSpacing="1" cellPadding="1" width="100%" align="center" border="0" class="OraBGAccentVeryDark">
                         <TR class="OraTableColumnHeader">
                           <th class="OraTableColumnHeader">CS* Preferred Name</th>
                           <th class="OraTableColumnHeader">CS* Definition</th>
                           <th class="OraTableColumnHeader">CS* Public ID</th>
                           <th class="OraTableColumnHeader">CSI* Name</th>
                           <th class="OraTableColumnHeader">CSI* Type</th>
                         </TR>
                           <logic:notEmpty name="definition" property="csCsis">
                             <logic:iterate id="dcscsi" name="definition" property="csCsis" type="gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem" >                                          
                                 <TR class="OraTabledata">
                                   <td class="OraFieldText">
                                     <bean:write name="dcscsi" property="cs.preferredName"/>
                                   </td>
                                   <td class="OraFieldText">
                                     <bean:write name="dcscsi" property="cs.preferredDefinition"/>
                                   </td>
                                   <td class="OraFieldText">
                                     <bean:write name="dcscsi" property="cs.publicId"/>
                                   </td>                        
                                   <td class="OraFieldText">
                                     <bean:write name="dcscsi" property="csi.name"/>
                                   </td>
                                   <td class="OraFieldText">
                                     <bean:write name="dcscsi" property="csi.type"/>
                                   </td>  
                                 </TR> 
                               </logic:iterate>
                             </logic:notEmpty>  
                             <logic:empty name="definition" property="csCsis">
                               <TR class="OraTabledata">
                                 <td colspan="5" class="OraFieldText">No Classification exist for this Definition</td>
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
           <logic:empty name="oc" property="definitions">
            <table vAlign="top" cellSpacing="1" cellPadding="1" width="90%" align="center" border="0" class="OraBGAccentVeryDark">          
             <TR class="OraTabledata">
               <td colspan="3" class="OraFieldText">Definitions does not exist for this Object Class</td>
             </TR>
           </table>
           </logic:empty>
         </table>
       </logic:present>
       <logic:notPresent name="<%=UmlBrowserFormConstants.OBJECT_CLASS%>">
         <br>
         <table valign="bottom" cellpadding="0" cellspacing="0" width="90%" align="center">
           <tr valign="bottom">
             <td class="OraHeaderSubSubSub" width="100%">Object Class</td>
           </tr>
         </table>
         <table width="80%" align="center" cellpadding="4" cellspacing="1" class="OraBGAccentVeryDark">
           <tr class="OraTabledata">
             <td width="20%">Object Class does not exist</td>
           </tr>
         </table>
       </logic:notPresent>
       <br>
       <br>
    </html:form>
  </body>
</html>

