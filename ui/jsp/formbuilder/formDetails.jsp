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
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants" %>
<HTML>
  <HEAD>
    <TITLE>Welcome to Form Builder..</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css"/>
    <SCRIPT LANGUAGE="JavaScript">

</SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = "";

%>
    <%@ include file="../common/common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="View&nbsp;Form"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>
    <%@ include file="/formbuilder/viewButton_inc.jsp"%>
   
      <logic:messagesPresent message="true">
       <table width="80%" align="center">
        <html:messages id="message" 
          message="true">
            <tr align="center" >
               <td  align="left" class="OraErrorText" >
                <b><bean:write  name="message"/></b><br>
              </td>
            </tr>
        </html:messages> 
           <tr align="center" >
             <td>
                &nbsp;
            </td>
           </tr>        
       </table>
      </logic:messagesPresent>  
    
    <logic:present name="<%=FormConstants.CRF%>">
      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.name" />
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="longName"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td  class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.definition"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="preferredDefinition"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td  class="TableRowPromptTextLeft" width="20%">
            <bean:message key="cadsr.formbuilder.form.context"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="context.name"/>
          </td>
        </tr>
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.protocol"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="protocol.longName"/>
          </td>
        </tr>   
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.workflow"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="aslName"/>
          </td>
        </tr>  
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.category"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="formCategory"/>
          </td>
        </tr>  
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.form.type"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write name="<%=FormConstants.CRF%>" property="formType"/>
          </td>
        </tr> 
        <tr class="OraTabledata">
          <td class="TableRowPromptTextLeft"  width="20%">
            <bean:message key="cadsr.formbuilder.question.version"/>
          </td>                
          <td  class="OraFieldText">
            <bean:write  name="<%=FormConstants.CRF%>" property="version"/> 
          </td>
        </tr>                
      </table>
      <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        <tr >
          <td >
		&nbsp;
          </td>
        </tr>
      </table>      
            <logic:notEmpty name="<%=FormConstants.CRF%>" property = "modules">
              <logic:iterate id="module" name="<%=FormConstants.CRF%>" type="gov.nih.nci.ncicb.cadsr.resource.Module" property="modules">
                <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">               
                 <tr class="OraTableColumnHeader">                 
                    <td class="OraTableColumnHeader">
                      <bean:write name="module" property="longName"/>
                    </td>
                  </tr>
                  <logic:present name="module">
                  <logic:notEmpty name="module" property = "questions">
                    <tr class="OraTabledata">
                      <td>
                        <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraTabledata">      
                          <logic:iterate id="question" name="module" type="gov.nih.nci.ncicb.cadsr.resource.Question" property="questions">                           
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="50">&nbsp;</td>
                              <td height="1"  class="OraFieldText">                               
                              </td>                              
                            </tr>                             
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="50">&nbsp;</td>
                              <td class="UnderlineOraFieldText">
                                <bean:write name="question" property="longName"/>
                              </td>
                              <logic:present name="question" property = "dataElement">
                                <td align="center" width="70" class="UnderlineOraFieldText" >                        
 	    			                              <html:link page='<%="/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>'
                                             paramId = "p_de_idseq"
                                              paramName="question"
                                              paramProperty="dataElement.deIdseq"
                                              target="_blank">
                                          <bean:write name="question" property="dataElement.CDEId"/>
                                          </html:link>
                                  </td>
                              <td align="center" width="70" class="UnderlineOraFieldText">
                                	<bean:write name="question" property="dataElement.version"/>
                              </td>                                  
                              </logic:present>
                              <logic:notPresent name="question" property="dataElement">
                                  <td align="center" width="70" class="UnderlineOraFieldText">
                                   &nbsp;
                                  </td>
                                 <td align="center" width="70" class="UnderlineOraFieldText">
                                    &nbsp;
                                 </td>                              
                            </logic:notPresent>                                
                            </tr>                                                     
                            <logic:present name="question">
                            <logic:notEmpty name="question" property = "validValues">
                              <tr class="OraTabledata">
                                <td class="OraFieldText" width="50">&nbsp;</td>
                                <td>
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                    <logic:iterate id="validValue" name="question" type="gov.nih.nci.ncicb.cadsr.resource.FormValidValue" property="validValues">
                                      <tr  COLSPAN="3" class="OraTabledata">
                                        <td class="OraFieldText" width="50">&nbsp;</td>
                                        <td class="OraFieldText">
                                          <bean:write name="validValue" property="longName"/>
                                        </td>
                                      </tr>
                                    </logic:iterate><!-- valid Value-->
                                  </table>
                                </td>
                              </tr>
                            </logic:notEmpty>
                            </logic:present>
                          </logic:iterate><!-- Question-->
                        </table>
                      </td>
                    </tr>
                  </logic:notEmpty>
                  </logic:present>
                </table>

      		<table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        	   <tr class>
          	      <td >
			&nbsp;
          	      </td>
        	   </tr> 
        	</table>
                
              </logic:iterate><!-- Module-->
            </logic:notEmpty>
    </logic:present>
    <logic:notPresent name="<%=FormConstants.CRF%>">Selected form has been deleted by a diffrent user </logic:notPresent>
    <%@ include file="/formbuilder/viewButton_inc.jsp"%>
    <%@ include file="/common/common_bottom_border.jsp"%>
  </BODY>
</HTML>
