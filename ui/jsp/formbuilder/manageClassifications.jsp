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
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = "";
      String pageUrl = "&PageId=DataElementsGroup";
      String protoLOVUrl= 
      "javascript:newWin('/cdebrowser/formLOVAction.do?method=getProtocolsLOV&idVar=" + FormConstants.PROTOCOLS_LOV_ID_FIELD + "&nameVar=" + FormConstants.PROTOCOLS_LOV_NAME_FIELD +pageUrl+"','protoLOV',700,600)";
      %>
    <%@ include file="/formbuilder/common_header_inc.jsp"%>
    <jsp:include page="/formbuilder/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Edit&nbsp;Form"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>


    <logic:present name="<%=FormConstants.CRF%>">
      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.name" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="longName"
              />
          </td>

          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.context" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="context.name"
              />
          </td>
        </tr>

        <tr>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.version" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="version"
              />
          </td>

          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.workflow" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="aslName"
              />
          </td>      
        </tr>
        <tr>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.type" />:</td>  
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="formType"
              />
          </td>        
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.protocol" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="protocol.longName"
              />
          </td>
        </tr>


        <tr>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.category" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="formCategory"
              />
          </td>
        </tr>     

        <tr>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.definition" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="preferredDefinition"
              />
           </td>
        </tr>
      </table>

      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr>
          <td class="OraHeaderSubSub" width="100%">Classifications</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>

      <table width="80%" align="center" cellpadding="1" cellspacing="1" bgcolor="#999966">
        <tr class="OraTableColumnHeader">
          <th>CS* Preferred Name</th>
          <th>CS* Definition</th>
          <th>CSI* Name</th>
          <th>CSI* Type</th>
        </tr>

        <logic:present name="<%= FormConstants.CLASSIFICATIONS %>">
          <logic:iterate id="classification" name="<%= FormConstants.CLASSIFICATIONS %>" >
            
            <tr class="OraTabledata">
              <td class="OraFieldText">
                <bean:write name="classification" property="classSchemeLongName"/>
              </td>
              <td class="OraFieldText">
                <bean:write name="classification" property="classSchemeDefinition"/>
              </td>
              <td class="OraFieldText">
                <bean:write name="classification" property="classSchemeItemName"/>
              </td>
              <td class="OraFieldText">
                <bean:write name="classification" property="classSchemeItemType"/>
              </td>
              <td class="OraFieldText">
                <html:link action='<%= "/removeClassification?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.REMOVE_CLASSIFICATION %>' paramId="<%= FormConstants.CS_CSI_ID%>" paramName="classification" paramProperty="acCsiIdseq">
                  <html:img src='<%=urlPrefix+"i/delete_item.gif"%>' border="0" alt="Remove"/>
                </html:link>
              </td>
            </tr>
          </logic:iterate>
        </logic:present>

        <logic:notPresent name="<%= FormConstants.CLASSIFICATIONS %>">
          <tr class="OraTabledata">
            <td colspan="4">There are no classifications for the selected CDE.</td>
          </tr>
        </logic:notPresent>
      
      <%@ include file="/formbuilder/manageClassifications_inc.jsp"%>    

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
</BODY>
</HTML>
