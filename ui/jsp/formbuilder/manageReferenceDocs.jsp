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
    <TITLE>Welcome to Form Builder..</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">

 function submitForm(methodName) {
       var f = document.forms[0];

       document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
       f.submit();

  }      
  
    </SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = "";
      String pageUrl = "&PageId=DataElementsGroup";
      %>
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Maintain&nbsp;Reference&nbsp;Documents"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

    <%@ include file="showMessages.jsp"%>
    <html:form action="/manageReferenceDocs.do">
    <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      
    <%@ include file="/formbuilder/refDocsButton_inc.jsp"%>
         
    <logic:present name="<%=FormConstants.CRF%>">
      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
        <tr class="OraTabledata">
          <td width="20%" class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.name" /></td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="longName"
              />
          </td>
        </tr>
        <tr class="OraTabledata">
          <td width="20%" class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.context" /></td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="context.name"
              />
          </td>
        </tr>

        <tr class="OraTabledata">
          <td width="20%" class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.version" /></td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="version"
              />
          </td>
        </tr>
        <tr class="OraTabledata">
          <td width="20%" class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.workflow" /></td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="aslName"
              />
          </td>      
        </tr>
        <tr class="OraTabledata">
          <td width="20%" class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.type" /></td>  
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="formType"
              />
          </td>        
        </tr>
        <tr class="OraTabledata">
          <td width="20%" class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.protocol" /></td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="protocol.longName"
              />
          </td>
        </tr>


        <tr class="OraTabledata">
          <td width="20%" class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.category" /></td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="formCategory"
              />
          </td>
        </tr>     

        <tr class="OraTabledata">
          <td width="20%" class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.definition" /></td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="preferredDefinition"
              />
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
          <td class="OraHeaderSubSub" width="100%">Reference Documents</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>
      

      
        <logic:notEmpty name="<%=FormConstants.CRF%>" property="refereceDocs">
          <logic:iterate id="refDoc" indexId="refDocIndex" name="<%=FormConstants.CRF%>" type="gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument" property="refereceDocs">
            <bean:size id="refDocSize" name="<%=FormConstants.CRF%>" property="refereceDocs"/>            
            <table width="77%" align="center" cellpadding="0" cellspacing="0" border="0" >
               <tr >
                <td   align="right">
                     <html:link action='<%="/createReferenceDoc.do?"+NavigationConstants.METHOD_PARAM+"=gotoCreateReferenceDoc"%>'
                       paramId = '<%="selectedRefDocId"%>'
                       paramName="refDocIndex"
                       >
                       <html:img src='<%="i/add.gif"%>' border="0" alt="Add new Reference Document"/>
                     </html:link>                                    
                 </td>  
               </tr>
            </table>
            <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
               <tr class="OraHeaderBlack">
                 <td  colspan="2">
                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                     <tr class="OraTabledata" >
                      <td  class="OraHeaderBlack" width="90%">
                        <html:text size="80" name="refDoc" property="docName">
                           maxlength="<%= Integer.toString(FormConstants.LONG_NAME_MAX_LENGTH)%>">
                        </html:text>                        
                      </td>
                      <td class="OraTableColumnHeader">
                        <table align="center" width="100%" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                            <tr class="OraTableColumnHeader">
                             <td  align="center">
                                <logic:notEqual value="<%= String.valueOf(refDocSize.intValue()-1) %>" name="refDocIndex">
                                  <a href="javascript:submitFormEdit('<%=NavigationConstants.MOVE_MODULE_DOWN%>','<%=refDocIndex%>')">
                                     <img src=i/down.gif border=0 alt="Down">
                                  </a>                                  
                                </logic:notEqual>
                             </td>
                             <td  align="center">
                                <logic:notEqual value="<%= String.valueOf(0) %>" name="refDocIndex">
                                  <a href="javascript:submitFormEdit('<%=NavigationConstants.MOVE_MODULE_UP%>','<%=refDocIndex%>')">
                                     <img src=i/up.gif border=0 alt="Up">
                                  </a>                           
                                </logic:notEqual> 
                              </td>
                              <td  align="center">
                                    <a href="javascript:submitFormEdit('<%=NavigationConstants.DELETE_MODULE%>','<%=1%>')">
                                       <img src=<%=urlPrefix%>i/delete.gif border=0 alt="Delete">
                                    </a>
                               </td>                              
                              </tr>
                         </table>
                      </td>
                     </tr>
                   </table>
                  </td>
                      
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    <bean:message key="cadsr.formbuilder.form.context"/> 
                  </td>
                  <td class="OraFieldText" nowrap>
                    <html:select styleClass="Dropdown" property="contextIdSeq">               
                      <html:options collection="<%=CaDSRConstants.USER_CONTEXTS%>" property="conteIdseq" labelProperty="name"/>
                    </html:select>
                  </td>
               </tr>  
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    URL 
                  </td>
                  <td class="OraFieldText" nowrap>
                    <html:text size="80" property="url">
                       maxlength="<%= Integer.toString(FormConstants.LONG_NAME_MAX_LENGTH)%>">
                    </html:text>
                  </td>
               </tr>    
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    Description 
                  </td
                  <td class="OraFieldText" nowrap>
                  <td  class="OraFieldText" size="80%" nowrap>
                    <html:text size="80" property="description">
                       maxlength="<%= Integer.toString(FormConstants.LONG_NAME_MAX_LENGTH)%>">
                   </html:text>
                  </td>
                  </td>
               </tr>                  
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    Attachments
                  </td>
                  <td class="OraFieldText">
                    <bean:size id="attachmentSize" name="refDoc" property="attachments"/>
                    <table table width="100%"  cellpadding="0" cellspacing="0" border="0" class="OraTabledata" >
                        <tr class="OraTabledata">
                          <td class="OraFieldText"  colspan="2">
                        <table width="10%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                            <tr class="OraTabledata">
                              <td align="center">
                                   <html:link action='<%="/manageReferenceDocs.do?"+NavigationConstants.METHOD_PARAM+"=gotoUploadDocument"%>'
                                     paramId = '<%="selectedRefDocId"%>'
                                     paramName="refDocIndex"
                                     >
                                     <html:img src='<%="i/add.gif"%>' border="0" alt="Upload Document"/>
                                   </html:link>                                    
                               </td> 
                              <logic:notEmpty name="refDoc" property="attachments">
                                <td align="center">
                                      <a href="javascript:submitFormEdit('<%=NavigationConstants.DELETE_MODULE%>','<%=1%>')">
                                         <img src=<%=urlPrefix%>i/delete.gif border=0 alt="Delete">
                                      </a>
                                 </td>
                              </logic:notEmpty>
                              </tr>
                           </table>                                  
                          </td>                          
                        </tr>                      
                      <logic:iterate id="attachment" indexId="attachmentIndex" name="refDoc" type="gov.nih.nci.ncicb.cadsr.resource.Attachment" property="attachments">
                        <tr class="OraTabledata">
                          <td width="5%" align="center" class="OraFieldText">
                            <input type="checkbox" />
                          </td>                        
                          <td class="OraFieldText" align="left">
                            <a href=" " target="_blank"
                              alt="View Document" >
                              <bean:write name="attachment" property="name"/>
                            </a>	                          
                          </td>
                        </tr>
                      </logic:iterate>                    
                    </table>                                       
                  </td>
               </tr>                   
             </table>
             
              <logic:notEqual value="<%= String.valueOf(refDocSize.intValue()-1) %>" name="refDocIndex">
                <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
                  <tr class>
                    <td >
                      &nbsp;
                    </td>
                  </tr> 
                </table>
              </logic:notEqual>  
              
           </logic:iterate>
         </logic:notEmpty>    
         
                <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
                  <tr class>
                    <td >
                      &nbsp;
                    </td>
                  </tr> 
                </table>
                
        <%@ include file="/formbuilder/refDocsButton_inc.jsp"%>
              
  </logic:present>
  </html:form>
  <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
    <tr class>
      <td >
        &nbsp;
      </td>
    </tr> 
  </table>
  
<logic:notPresent name="<%=FormConstants.CRF%>">Selected form has been deleted by a diffrent user </logic:notPresent>
<%@ include file="../common/common_bottom_border.jsp"%>
</BODY>
</HTML>
