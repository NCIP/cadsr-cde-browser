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
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<SCRIPT LANGUAGE="JavaScript1.1" SRC="jsLib/checkbox.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--

function details(linkParms ){
  var urlString="search?dataElementDetails=9" + linkParms + "&PageId=DataElementsGroup"+"&queryDE=yes";
  newBrowserWin(urlString,'deDetails',800,600)
  
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
<jsp:include page="cdebrowserCommon_html/common_tab_inc.jsp" flush="true">
</jsp:include>
<jsp:include page="../common/tab_inc.jsp" flush="true">
  <jsp:param name="label" value="Compare&nbsp;CDEs"/>
  <jsp:param name="urlPrefix" value=""/>
</jsp:include>
<%
  int numberCompared =((Integer) pageContext.getSession().getAttribute("compareCDESize")).intValue();
  int width = 80 / numberCompared;
  String colWidth = String.valueOf(width) + "%";
  %>
<table>
    <tr>    
      <td align="left" class="AbbreviatedText">
        <bean:message key="cadsr.formbuilder.helpText.cart"/>
      </td>
    </tr>  
</table> 

<%@ include file="../formbuilder/showMessages.jsp" %>

<table width="80%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Long Name:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'>Adverse Event Therapy <%=i+1%></td>
    <% } %>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Public ID:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'>2004104</td>
    <% } %>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Preferred Name:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'>AE_THERAPY <%=i+1%></td>
    <% } %>
  </tr>
 

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Document Text:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'>Therapy <%=i+1%></td>
    <% } %>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Definition:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'>What additional therapy is required to treat the adverse event.</td>
    <% } %>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Value Domain:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'>AE_THERAPY</td>
    <% } %>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Data Element Concept:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'>AE_THERAPY</td>
    <% } %>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Context:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'>CCR  <%=i+1%></td>
    <% } %>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Workflow Status:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'>DRAFT NEW</td>
    <% } %>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Version:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'>3.<%=i+1%></td>
    <% } %>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Origin:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'></td>
    <% } %>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Registration Status:</td>
    <% for (int i=0; i< numberCompared; i++) { %>
    <td class="OraFieldText" width='<%=colWidth%>'></td>
    <% } %>
 </tr>
</table>
 <br>


<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%">Reference Documents</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>
<table width="80%" align="center" cellpadding="2" cellspacing="5" border="0" >
<tr>
<td valign="top">
<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Document Name</th>
    <th class="OraTableColumnHeader">Document Type</th>
  </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">ADDRESS </td>
        <td class="OraFieldText">LONG_NAME </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">CTEP_SHORT_NAME3169 </td>
        <td class="OraFieldText">HISTORIC SHORT CDE NAME </td>
      </tr>

</table>
</td>
<td valign="top">
<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Document Name</th>
    <th class="OraTableColumnHeader">Document Type</th>
  </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">CRF Text </td>
        <td class="OraFieldText">HISTORIC SHORT CDE NAME </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">CRF Text </td>
        <td class="OraFieldText">LONG_NAME </td>
      </tr>
      <tr class="OraTabledata">
        <td class="OraFieldText">ADDRESS </td>
        <td class="OraFieldText">LONG_NAME </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">CTEP_SHORT_NAME3169 </td>
        <td class="OraFieldText">HISTORIC SHORT CDE NAME </td>
      </tr>

</table>
</td>
</tr>
</table>

<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%">Designations</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>
<table width="80%" align="center" cellpadding="2" cellspacing="0" border="0" >
<tr>
<td valign="top">
<table width="40%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Name</th>
    <th class="OraTableColumnHeader">Type</th>
    <th class="OraTableColumnHeader">Context</th>
  </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">ENGLISH </td>
        <td class="OraFieldText">CONTEXT NAME</td>
        <td class="OraFieldText">CTEP</td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">ADDITIONAL_DIAGNOSES_SPECIFY</td>
        <td class="OraFieldText">USED_BY</td>
        <td class="OraFieldText">TEST</td>
      </tr>

</table>
</td>
<td valign="top">
<table width="40%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Name</th>
    <th class="OraTableColumnHeader">Type</th>
    <th class="OraTableColumnHeader">Context</th>
  </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">ADDITIONAL_DIAGNOSES_SPECIFY</td>
        <td class="OraFieldText">USED_BY</td>
        <td class="OraFieldText">TEST</td>
      </tr>

</table>
</td>
</tr>
</table>

<%@ include file="../common/common_bottom_border.jsp"%>
</body>
</html>