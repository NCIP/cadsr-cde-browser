<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.*"%>
<%@ page import="java.util.*"%>
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
      <jsp:param name="label" value="Reference&nbsp;Documents"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

    <%@ include file="showMessages.jsp"%>
    <html:form action="/manageReferenceDocs.do">
    <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      
    <%@ include file="/formbuilder/refDocsViewButton_inc.jsp"%>

  
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
          <td width="20%" class="OraTableColumnHeader" nowrap><bean:message key="cadsr.formbuilder.form.protocols.longName" /></td>
          <td class="OraFieldText" nowrap>
           <bean:define name="<%=FormConstants.CRF%>" property="protocols" id="protocols"/>
            <%=FormJspUtil.getDelimitedProtocolLongNames((List)protocols,  "<br/>")%>                
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
          <logic:iterate id="refDoc" indexId="refDocIndex" name="<%=FormConstants.CRF%>" type="gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument" property="refereceDocs">
            <bean:size id="refDocSize" name="<%=FormConstants.CRF%>" property="refereceDocs"/>            

            <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentVeryDark">
               <tr class="OraHeaderBlack">
                 <td  colspan="2">
                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                     <tr class="OraHeaderBlack" >
                      <td>
                        <bean:write
                           name="refDoc"
                           property="docName"
                         />                        
                      </td>
                     </tr>
                   </table>
                  </td>
                      
               </tr>
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    <bean:message key="cadsr.formbuilder.form.type"/> 
                  </td>
                  <td class="OraFieldText" nowrap>
                        <bean:write
                           name="refDoc"
                           property="docType"
                         />   
                  </td>
               </tr>  

               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    <bean:message key="cadsr.formbuilder.form.context"/> 
                  </td>
                  <td class="OraFieldText" nowrap>
                        <bean:write
                           name="refDoc"
                           property="context.name"
                         />   
                  </td>
               </tr>  
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    URL 
                  </td>
                  <td class="OraFieldText" nowrap>
                    <a href="<bean:write name="refDoc" property="url"/>" target="AuxWindow"  >
                    <bean:write name="refDoc" property="url"/>
                    </a>
                  </td>
               </tr>    
               <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="20%" nowrap>
                    Description 
                  </td
                  <td class="OraFieldText" nowrap>
                  <td  class="OraFieldText" size="80%" nowrap>
                        <bean:write
                           name="refDoc"
                           property="docText"
                         />   
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
                      <logic:iterate id="attachment" indexId="attachmentIndex" name="refDoc" type="gov.nih.nci.ncicb.cadsr.common.resource.Attachment" property="attachments">
                        <tr class="OraTabledata">                       
                          <td class="OraFieldText" align="left">
              <html:link action='<%="/viewReferenceDocAttchment.do?"+NavigationConstants.METHOD_PARAM+"=viewReferenceDocAttchment"%>' 
                paramId = "<%=FormConstants.REFERENCE_DOC_ATTACHMENT_NAME%>"
                paramName="attachment" paramProperty="name"
                target="_blank" >
                <bean:write name="attachment" property="name"/>
              </html:link>                 
           <logic:equal name="attachment" property="name" value="<%=(String) request.getSession().getAttribute(FormConstants.REFDOCS_TEMPLATE_ATT_NAME)%>">
             <font class="AbbreviatedText">(Can be downloaded from CDE Browser using "Download Template" link)</font>
            </logic:equal>
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
                
        <%@ include file="/formbuilder/refDocsViewButton_inc.jsp"%>
              
  </logic:present>
  </html:form>

  
<logic:notPresent name="<%=FormConstants.CRF%>">Selected form has been deleted by a diffrent user </logic:notPresent>
<%@ include file="../common/common_bottom_border.jsp"%>
</BODY>
</HTML>
