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
<html:html>
<HEAD>
<TITLE>Display CDE Cart</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK REL="STYLESHEET" TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css"/>
<SCRIPT LANGUAGE="JavaScript">

</SCRIPT>
</HEAD>
<BODY bgcolor="#ffffff" topmargin="0">

<% 
  String urlPrefix = "";
%>
<%@ include file="../common/common_header_inc.jsp"%>
<jsp:include page="../common/tab_inc.jsp" flush="true">
  <jsp:param name="label" value="CDE&nbsp;Cart"/>
  <jsp:param name="urlPrefix" value=""/>
</jsp:include>

<html:form action='<%= "/changeAssociation?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.CHANGE_DE_ASSOCIATION %>' >

<html:hidden property="<%= FormConstants.QUESTION_INDEX %>"/>

<logic:present name="<%=CaDSRConstants.CDE_CART%>">
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
    <tr class="OraTableColumnHeader">
      <th scope="col"><bean:message key="cadsr.formbuilder.question.preferredName"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.longName"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.comments"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.context"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.usedByContext"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.workflow"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.publicID"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.version"/></th>
    </tr>
  <logic:empty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <tr class="OraTabledata">
        <td class="OraFieldText">
          CDE Cart is empty. 
        </td>
    </tr>
  </table>
  </logic:empty>
  <logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <logic:iterate id="de" name="<%=CaDSRConstants.CDE_CART%>" type="gov.nih.nci.ncicb.cadsr.resource.CDECartItem" property="dataElements" indexId="itemId">
      <tr class="OraTabledata">
        <td class="OraFieldText">
          <html:radio property="selectedText" value="<%= itemId + \",\" + de.getItem().getPreferredName() %>"/>
          <bean:write name="de" property="item.preferredName"/>
        </td>
        <td class="OraFieldText">
          <html:radio property="selectedText" value="<%= itemId + \",\" + de.getItem().getLongName() %>"/>
          <bean:write name="de" property="item.longName"/>
        </td>
        <td class="OraFieldText">
          <logic:notEmpty name="de" property="item.longCDEName">
            <html:radio property="selectedText" value="<%= itemId + \",\" + ((gov.nih.nci.ncicb.cadsr.resource.DataElement)de.getItem()).getLongCDEName() %>"/>
              <bean:write name="de" property="item.longCDEName"/>
          </logic:notEmpty>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.contextName"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.contextName"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.aslName"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.publicId"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.version"/>
        </td>
      </tr>
    </logic:iterate>
      <tr>
        <td colspan="8">
          <html:radio property="selectedText" value=""/><bean:message key="cadsr.formbuilder.question.noAssociation"/>
        </td>
      </tr>
    </table>
    <br>

      <%@ include file="changeAssociation_inc.jsp" %>
    <table width="50%" align="center" cellpadding="1" cellspacing="1" border="0" >
      <tr >
        <td >
          <html:link href="">Add More Data Elements to Cart</html:link>
        </td>
      </tr>
    </table>    
  </logic:notEmpty>
</logic:present>
</html:form>
<%@ include file="../common/common_bottom_border.jsp"%>
</body>
</html:html>