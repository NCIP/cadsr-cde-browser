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
<SCRIPT LANGUAGE="JavaScript1.1" SRC="jsLib/checkbox.js"></SCRIPT>
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

function ToggleSaveAll(e){
	if (e.checked) {
	    setChecked(1,'selectedSaveItems');
	}
	else {
	    setChecked(0,'selectedSaveItems');
	}
}

function ToggleAll(e){
	if (e.checked) {
	    setChecked(1,'selectedItems');
	}
	else {
	    setChecked(0,'selectedItems');
	}
}

function details(linkParms ){
  var urlString="search?dataElementDetails=9" + linkParms + "&PageId=DataElementsGroup"+"&queryDE=yes";
  newBrowserWin(urlString,'deDetails',800,600)
  
}

function retrieveSavedItems() {
  document.location.href = "formCDECartAction.do?method=displayCDECart";
}

-->

</SCRIPT>
</HEAD>
<BODY bgcolor="#ffffff" topmargin="0">

<% 
  String urlPrefix = "";
  String downloadXMLURL = "javascript:fileDownloadWin('downloadXMLPage.jsp?src=cdeCart','xmlWin',500,200)";
  String downloadExcelURL = "javascript:fileDownloadWin('downloadExcelPage.jsp?src=cdeCart','excelWin',500,200)";
%>
<jsp:include page="common/common_header_jsp_inc.jsp" flush="true">
  <jsp:param name="loginDestination" value="formCDECartAction.do?method=displayCDECart"/>
  <jsp:param name="urlPrefix" value=""/>
</jsp:include>
<jsp:include page="common/tab_inc.jsp" flush="true">
  <jsp:param name="label" value="CDE&nbsp;Cart"/>
  <jsp:param name="urlPrefix" value=""/>
</jsp:include>

<html:form action="/cdeCartAction.do">
<html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
<logic:present name="<%=CaDSRConstants.CDE_CART%>">
  <logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <table cellpadding="0" cellspacing="0" width="80%" align="center">
      <tr>
        <td nowrap>
          <b><a href="<%=downloadExcelURL%>" >[Download Data Elements to Excel]</a></b> &nbsp;&nbsp;
          <b><a href="<%=downloadXMLURL%>" >[Download Data Elements as XML]</a></b> &nbsp;&nbsp;
        </td>
      </tr>
      <tr>
        <td width="100%" nowrap><img height=2 src="i/beigedot.gif" width="99%" align=top border=0> </td>
      </tr>
    </table>
  </logic:notEmpty>
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
    <tr class="OraTableColumnHeader">
    <logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
      <th><input type="checkbox" name="allChk" value="yes" onClick="ToggleAll(this)"/></th>
    </logic:notEmpty>
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
        <td class="OraFieldText" colspan="7">
          CDE Cart is empty. 
        </td>
    </tr>
    <table width="40%" align="center" cellpadding="1" cellspacing="1" border="0">
      <TR>
        <td>&nbsp;</td>
      </TR>
      <tr>
        <td>
          <a href="javascript:retrieveSavedItems()()">
            <html:img src='<%=urlPrefix+"i/retrieve.gif"%>' border="0" alt="Retrieve Saved Data Elements"/> 
          </a>
        </td>
        <td >
          <html:link href="cdeBrowse.jsp?PageId=DataElementsGroup">				
            <html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Back to Data Element Search"/>
          </html:link>             
        </td>
      </tr>
    </table>
  </logic:empty>
  <logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <logic:iterate id="de" name="<%=CaDSRConstants.CDE_CART%>" type="gov.nih.nci.ncicb.cadsr.resource.CDECartItem" property="dataElements">
      <tr class="OraTabledata">
        <td>
          <input type="checkbox" name="selectedItems" value="<%=de.getId()%>"/>
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
    <table width="40%" align="center" cellpadding="1" cellspacing="1" border="0">
      <tr>
        <td>
          <a href="javascript:retrieveSavedItems()()">
            <html:img src='<%=urlPrefix+"i/retrieve.gif"%>' border="0" alt="Retrieve Saved Data Elements"/> 
          </a>
        </td>
        <td>
          <a href="javascript:saveItems()">
            <html:img src='<%=urlPrefix+"i/save.gif"%>' border="0" alt="Save Data Elements"/> 
          </a>
        </td>   
        <td>
          <a href="javascript:deleteItems()">
            <html:img src='<%=urlPrefix+"i/deleteButton.gif"%>' border="0" alt="Remove Data Elements from CDE Cart "/> 
          </a>
        </td>
        
        <td >
          <html:link href="cdeBrowse.jsp?PageId=DataElementsGroup">				
            <html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Back to Data Element Search"/>
          </html:link>             
        </td>
      </tr>
    </table>
  </logic:notEmpty>
</logic:present>

<logic:notPresent name="<%=CaDSRConstants.CDE_CART%>">

</logic:notPresent>
</html:form>
<%@ include file="common/common_bottom_border.jsp"%>
</body>
</html>