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
<TITLE>Display CDE Cart</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK REL="STYLESHEET" TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css"/>
<SCRIPT LANGUAGE="JavaScript">
<!--

function submitForm() {
  document.forms[0].submit();
}

function saveItems() {
  document.forms[0].method.value = 'addItems'
  submitForm();
}

function deleteItems() {
  document.forms[0].method.value = 'removeItems'
  submitForm();
}


-->

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

<html:form action="/formCDECartAction.do">
<html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
<logic:present name="<%=CaDSRConstants.CDE_CART%>">
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
    <tr class="OraTableColumnHeader">
      <th>Save<input type="checkbox" name="saveAllChk" value="yes"/> </th>
      <th>Delete<input type="checkbox" name="deleteAllChk" value="yes"/></th>
      <th>Public Id</th>
      <th>Preferred Name</th>
      <th>Long Name</th>
      <th>Doc Text</th>
      <th>Context</th>
      <th>Version</th>
      <th>Workflow Status</th>
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
    <logic:iterate id="de" name="<%=CaDSRConstants.CDE_CART%>" type="gov.nih.nci.ncicb.cadsr.resource.CDECartItem" property="dataElements">
      <tr class="OraTabledata">
      	<td>
            <logic:equal name="de" property="persistedInd" value="true">
              &nbsp;       
            </logic:equal>
            <logic:notEqual name="de" property="persistedInd" value="true">
              <input type="checkbox" name="selectedSaveItems" value="<%=de.getId()%>"/>
            </logic:notEqual>
        </td>
        <td>
          <input type="checkbox" name="selectedDeleteItems" value="<%=de.getId()%>"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.publicId"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.preferredName"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.longName"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.longCDEName"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.contextName"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.version"/>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.aslName"/>
        </td>
      </tr>
    </logic:iterate>
    </table>
    <br>
    <table width="30%" align="center" cellpadding="1" cellspacing="1" border="0">
      <tr>
        <td>
          <a href="javascript:saveItems()">
            <html:img src='<%=urlPrefix+"i/save.gif"%>' border="0" alt="Save"/> 
          </a>
        </td>   
        <td>
          <a href="javascript:deleteItems()">
            <html:img src='<%=urlPrefix+"i/deleteButton.gif"%>' border="0" alt="Delete"/> 
          </a>
        </td> 
        <td >
          <html:link href="cdeBrowse.jsp?PageId=DataElementsGroup">				
            <html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Back"/>
          </html:link>             
        </td> 
      </tr>
    </table>
  </logic:notEmpty>
  
</logic:present>

<logic:notPresent name="<%=CaDSRConstants.CDE_CART%>">

</logic:notPresent>
</html:form>
<%@ include file="../common/common_bottom_border.jsp"%>
</body>
</html>