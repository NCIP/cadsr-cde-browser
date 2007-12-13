<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>

<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<HTML>
  <HEAD>
    <TITLE>Formbuilder: Skip Pattern.</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">
    
      
    </SCRIPT>
    <%
    TriggerAction triggerAction = (TriggerAction)request.getSession().getAttribute(FormConstants.SKIP_PATTERN);
    String skipSourceType = FormJspUtil.getFormElementType(triggerAction.getActionSource());
    pageContext.setAttribute("skipSourceType",skipSourceType);
    pageContext.setAttribute("skipSource",triggerAction.getActionSource());
    
    %>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Skip&nbsp;Pattern"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>
<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
         
      </td>
    </tr>  
</table> 


      <%@ include file="showMessages.jsp" %>

     <html:form action="/formbuilder/skipAction.do">
     
        <table width="70%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="100%" nowrap>
                Skip from
            </td>
          </tr>
          
          <tr>
            <td>
            <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
             <logic:equal value="<%=FormJspUtil.FORM%>" name="skipSourceType">
             <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Form <bean:message key="cadsr.formbuilder.form.longName"/>
              </td>
              <td  class="OraFieldText" width="80%" nowrap>
                    <bean:write  name="skipSource" property="longName"/> 
               </td>
             </tr>               
             </logic:equal>
             <logic:equal value="<%=FormJspUtil.MODULE%>" name="skipSourceType">
              <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Module Name
              </td>
              <td  class="OraFieldText" width="80%" nowrap>
                    <bean:write  name="skipSource" property="longName"/> 
               </td>
             </tr>   
             <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Form <bean:message key="cadsr.formbuilder.form.longName"/>
              </td>
              <td  class="OraFieldText" width="80%" nowrap>
                    <bean:write  name="skipSource" property="form.longName"/> 
               </td>
             </tr>               
             </logic:equal>    
             <logic:equal value="<%=FormJspUtil.VALIDVALUE%>" name="skipSourceType">
             <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Valid Value
              </td>
              <td  class="OraFieldText" width="80%" nowrap>
                    <bean:write  name="skipSource" property="longName"/> 
               </td>
             </tr>   
              <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
               Question
              </td>
              <logic:present name="skipSource" property="question.dataElement">
              <td  class="OraFieldText" width="70%" nowrap>
                <table width="100%">
                  <tr>
                    <td  width="90%" class="OraFieldText" nowrap>
                      <bean:write  name="skipSource" property="question.longName"/> 
                    </td>
                   <td width="5%" align=right>
                        <html:link page='<%="/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>'
                                paramId = "p_de_idseq"
                                paramName="skipSource"
                                paramProperty="question.dataElement.deIdseq"
                                target="_blank">
                         <bean:write name="skipSource" property="question.dataElement.CDEId"/>
                       </html:link> 
                    </td>    
                    <td  width="5%" class="OraFieldText" nowrap align=right>
                      <bean:write name="skipSource" property="question.dataElement.version"/>
                    </td>                    
                  </tr>
                 </table>
              </td>
               </logic:present>
  
              <logic:notPresent name="skipSource" property="question.dataElement">
               <td  class="OraFieldText" width="70%" nowrap>
                <bean:write  name="skipSource" property="question.longName"/>
                </td>                   
              </logic:notPresent>
             </tr>                
              <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Module Name
              </td>
              <td  class="OraFieldText" width="80%" nowrap>
                    <bean:write  name="skipSource" property="question.module.longName"/> 
               </td>
             </tr>   
             <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Form <bean:message key="cadsr.formbuilder.form.longName"/>
              </td>
              <td  class="OraFieldText" width="80%" nowrap>
                    <bean:write  name="skipSource" property="question.module.form.longName"/> 
               </td>
             </tr>               
             </logic:equal>                                               
            </table>
           </td>
          </tr>
       </table>
    </html:form>       
       <br>    
       <br>      
      <%@ include file="/formbuilder/skipPatternInfoCreate_inc.jsp"%>    

<%@ include file="/common/common_bottom_border.jsp"%>

</BODY>
</HTML>
