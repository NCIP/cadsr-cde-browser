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
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css"/>
    <SCRIPT LANGUAGE="JavaScript">

</SCRIPT>
  </HEAD>
  <BODY bgcolor="#ffffff">
    <% String urlPrefix = "";

%>
    <%@ include file="/formbuilder/common_header_inc.jsp"%>
    <jsp:include page="/formbuilder/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="View&nbsp;Form"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>
    <logic:present name="<%=FormConstants.CRF%>">
      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
        <tr class="OraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.form.name" />
          </td>                
          <td  class="OraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="longName"/>
          </td>
        </tr>
        <tr class="OraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.form.definition" />
          </td>                
          <td  class="OraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="preferredDefinition"/>
          </td>
        </tr>
        <tr class="OraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.form.context" />
          </td>                
          <td  class="OraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="context.name"/>
          </td>
        </tr>
        <tr class="OraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.form.protocol" />
          </td>                
          <td  class="OraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="protocol.longName"/>
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
                        <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">      
                          <logic:iterate id="question" name="module" type="gov.nih.nci.ncicb.cadsr.resource.Question" property="questions">                           
                            <tr class="OraTabledata">
                              <td class="OraFieldText" width="50">&nbsp;</td>
                              <td height="1"  bgcolor="#F7F7E7" bordercolor="#C0C087">                               
                              </td>
                            </tr>                             <tr class="OraTabledata">
                              <td class="OraFieldText" width="50">&nbsp;</td>
                              <td class="UnderlineOraFieldText">
                                <bean:write name="question" property="longName"/>
                              </td>
                            </tr>                                                     
                            <logic:present name="question">
                            <logic:notEmpty name="question" property = "validValues">
                              <tr class="OraTabledata">
                                <td class="OraFieldText" width="50">&nbsp;</td>
                                <td>
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                                    <logic:iterate id="validValue" name="question" type="gov.nih.nci.ncicb.cadsr.resource.FormValidValue" property="validValues">
                                      <tr class="OraTabledata">
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
    <P>&nbsp;</P>
    <P>&nbsp;</P>
  </BODY>
</HTML>
