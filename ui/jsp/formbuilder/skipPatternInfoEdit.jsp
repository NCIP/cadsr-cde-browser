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
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.resource.*"%>
<HTML>
  <HEAD>
    <TITLE>Formbuilder: Skip Pattern.</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">
    
function submitForm(methodName) {
     document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
     document.forms[0].submit();
}      
    </SCRIPT>
    

    <%
    TriggerAction triggerAction = (TriggerAction)request.getSession().getAttribute(FormConstants.SKIP_PATTERN);
    String skipSourceType = FormJspUtil.getFormElementType(triggerAction.getActionSource());
    pageContext.setAttribute("skipSourceType",skipSourceType);
    pageContext.setAttribute("skipSource",triggerAction.getActionSource());
    
    String skipTargetType = FormJspUtil.getFormElementType(triggerAction.getActionTarget());
    pageContext.setAttribute("skipTargetType",skipTargetType);
    pageContext.setAttribute("skipTarget",triggerAction.getActionTarget());    

    %>

  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Skip&nbsp;Pattern"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>



      <html:form action="/formbuilder/skipAction.do"> 
       <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      <%@ include file="showMessages.jsp" %>


        <table width="70%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="100%" nowrap>
                Skip from
            </td>
          </tr>
          <tr>
            <td>
            <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">


             
             <logic:equal value="<%=FormJspUtil.MODULE%>" name="skipSourceType">
              <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Module Name
              </td>
              <td  class="OraFieldText" width="80%" nowrap>
                    <bean:write  name="skipSource" property="longName"/> 
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
                    <td  width="85%" class="OraFieldText" >
                      <bean:write  name="skipSource" property="question.longName"/> 
                    </td>
                   <td width="7%" align=right>
                        <html:link page='<%="/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>'
                                paramId = "p_de_idseq"
                                paramName="skipSource"
                                paramProperty="question.dataElement.deIdseq"
                                target="_blank">
                         <bean:write name="skipSource" property="question.dataElement.CDEId"/>
                       </html:link> 
                    </td>    
                    <td  width="7%" class="OraFieldText" nowrap align=right>
                      <bean:write name="skipSource" property="question.dataElement.version"/>
                    </td>                    
                  </tr>
                 </table>
              </td>
               </logic:present>
  
              <logic:notPresent name="skipSource" property="question.dataElement">
               <td  class="OraFieldText" width="70%" >
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
             </logic:equal>               

                    
            </table>
           </td>
          </tr>
       </table>

       <br>


        <table width="70%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="100%" nowrap>
                Skip to
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" >

             <logic:equal value="<%=FormJspUtil.FORM%>" name="skipTargetType">
             <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Form <bean:message key="cadsr.formbuilder.form.longName"/>
              </td>
              <td  class="OraFieldText" width="70%" nowrap>
                    <bean:write  name="skipTarget" property="longName"/> 
               </td>
              <td  class="OraFieldText" width="10%" nowrap>
                    [Edit] 
               </td>               
             </tr>               
             </logic:equal>
             
             <logic:equal value="<%=FormJspUtil.MODULE%>" name="skipTargetType">
              <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Module Name
              </td>
              <td  class="OraFieldText" width="80%" nowrap>
                    <bean:write  name="skipTarget" property="longName"/> 
               </td>
             </tr>   
             <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Form <bean:message key="cadsr.formbuilder.form.longName"/>
              </td>
              <td  class="OraFieldText" width="80%" nowrap>
                    <bean:write  name="skipTarget" property="form.longName"/> 
               </td>
             </tr>               
             </logic:equal>   
             
             <logic:equal value="<%=FormJspUtil.QUESTION%>" name="skipTargetType">
              <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Question Name
              </td>
              <logic:present name="skipTarget" property="dataElement">
              <td  class="OraFieldText" width="70%" nowrap>
                <table width="100%">
                  <tr>
                    <td  width="90%" class="OraFieldText" >
                      <bean:write  name="skipTarget" property="longName"/> 
                    </td>
                   <td width="5%" align=right>
                        <html:link page='<%="/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>'
                                paramId = "p_de_idseq"
                                paramName="skipTarget"
                                paramProperty="dataElement.deIdseq"
                                target="_blank">
                         <bean:write name="skipTarget" property="dataElement.CDEId"/>
                       </html:link> 
                    </td>    
                    <td  width="5%" class="OraFieldText" nowrap align=right>
                      <bean:write name="skipTarget" property="dataElement.version"/>
                    </td>                    
                  </tr>
                 </table>
              </td>
               </logic:present>
  
              <logic:notPresent name="skipTarget" property="dataElement">
               <td  class="OraFieldText" width="70%" >
                <bean:write  name="skipTarget" property="longName"/> 
                </td>                   
              </logic:notPresent>
               
               <td  align="center" class="OraFieldText" width="10%" nowrap>
                  <html:link action='<%="/formbuilder/skipAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.SET_CURRENT_FORM_AS_TARGET_FORM%>'
                     >                   
                    [Edit] 
                  </html:link>
               </td>                   
             </tr>   
              <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Module Name
              </td>
              <td  class="OraFieldText" width="70%" nowrap>
                    <bean:write  name="skipTarget" property="module.longName"/> 
               </td>
               <td   align="center" class="OraFieldText" width="10%" nowrap>
                  <html:link action='<%="/formbuilder/skipAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.SET_CURRENT_FORM_AS_TARGET_FORM%>'
                     >                   
                    [Edit] 
                  </html:link>
               </td>                   
             </tr>   
             <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Form <bean:message key="cadsr.formbuilder.form.longName"/>
              </td>
              <td  class="OraFieldText" width="70%" nowrap>
                    <bean:write  name="skipTarget" property="module.form.longName"/> 
               </td>
               <td   align="center" class="OraFieldText" width="10%" nowrap>
                  <html:link action='<%="/formbuilder/skipAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.SKIP_TO_FORM_SEARCH%>'
                       >                 
                    [Edit] 
                  </html:link>&nbsp;                          
               </td>                   
             </tr>               
             </logic:equal>                       

           
                </table>
             </td>
            </tr>
       </table> 
       

       <br>


        <table width="70%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="100%" nowrap>
                Using Protocols 
            </td>
          </tr>
          <tr>
            <td>
              <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" >

                <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="5%" nowrap>
                    <input type="checkbox" name="" value=""> 
                  </td>
                  <td  class="OraFieldText" width="90%" nowrap>
                      CTMS Version 3.0 
                  </td>            
                </tr>       
                <tr class="OraTabledata">
                  <td class="OraTableColumnHeader" width="5%" nowrap>
                     <input type="checkbox" name="" value=""> 
                  </td>
                  <td  class="OraFieldText" width="90%" nowrap>
                      NETTRIALS
                  </td>            
                </tr>                    
                </table>
             </td>
            </tr>
       </table>        

       <br>
       
        <table width="70%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTabledata">
            <td class="OraTableColumnHeader" width="100%" nowrap>
                Using Classifications 
            </td>
          </tr>
          <tr>
            <td>
            
     <table width="100%" align="center" cellpadding="1" cellspacing="1" bgcolor="#999966">
        <tr class="OraTableColumnHeader">
          <th scope="col"> </th>
          <th scope="col">CS* Short Name</th>
          <th scope="col">CS* Definition</th>
          <th scope="col">CSI* Name</th>
          <th scope="col">CSI* Type</th>
        </tr>

            <tr class="OraTabledata">
               <td class="OraTableColumnHeader" width="5%" nowrap>
                     <input type="checkbox" name="" value=""> 
               </td>            
              <td class="OraFieldText">
                Type of Disease     
              </td>
              <td class="OraFieldText">
                Type of Disease 
              </td>
              <td class="OraFieldText">
                Multiple Myeloma
              </td>
              <td class="OraFieldText">
                DISEASE_TYPE  
              </td>
            </tr>
            
            <tr class="OraTabledata">
               <td class="OraTableColumnHeader" width="5%" nowrap>
                     <input type="checkbox" name="" value=""> 
               </td>            
              <td class="OraFieldText">
                C3D Domain    
              </td>
              <td class="OraFieldText">
                Cancer Centralized Clinical Database
              </td>
              <td class="OraFieldText">
                caBIG
              </td>
              <td class="OraFieldText">
                USAGE_TYPE
              </td>
            </tr>
                    
     </table>
     
             </td>
            </tr>
       </table>        

       <br>       
        <table width="70%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
           <tr class="OraTabledata">
              <td class="OraTableColumnHeader" width="20%" nowrap>
                Skip insrtruction
              </td>
              <td  class="OraFieldText" width="80%" nowrap>
		<input size="80%" type="text" />
               </td>
          </tr>
       </table>        
       <br>       
      <%@ include file="/formbuilder/skipPatternInfoEdit_inc.jsp"%>    

      </html:form>

<%@ include file="/common/common_bottom_border.jsp"%>

</BODY>
</HTML>
