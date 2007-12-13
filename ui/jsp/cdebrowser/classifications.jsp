<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%//@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.html.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.* " %>
<%@page import="java.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.ProcessConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.domain.ReferenceDocument" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.domain.ReferenceDocumentAttachment"%>


<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="cdebrowserCommon_html/SessionAuth.html"%>

<%
  Vector classificationVector = (Vector)infoBean.getInfo(ProcessConstants.CLASSIFICATION_VECTOR);
  DataElement de = (DataElement)infoBean.getInfo("de");
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  Map csRefDocs = (Map) infoBean.getInfo("csRefDocs");
  Map csiRefDocs = (Map) infoBean.getInfo("csiRefDocs");
  Map csContacts = (Map) infoBean.getInfo("csContacts");
  String pageId = infoBean.getPageId();
  String pageName = PageConstants.PAGEID;
  String pageUrl = "&"+pageName+"="+pageId;
  HTMLPageScroller scroller = (HTMLPageScroller)
                infoBean.getInfo(ProcessConstants.DE_CS_PAGE_SCROLLER);
  String scrollerHTML = scroller.getScrollerHTML();
%>


<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
Classifications
</TITLE>
</HEAD>
<BODY topmargin="0">
<%
String csId = "";
String contactInfo = "";
Iterator csConIter = csContacts.keySet().iterator();
while (csConIter.hasNext()) {
	csId = (String) csConIter.next();
	contactInfo = (String) csContacts.get(csId);
	if (contactInfo != null && contactInfo.trim().length()>0)
	   contactInfo = "Contact Information<br>" + contactInfo;
	else
	   contactInfo = "No contact information available";
%>
<div id="<%=csId%>" class="tip"><%=contactInfo%></div>
<% }
%>
<script type="text/javascript" src="/CDEBrowser/jsLib/tooltip.js"></script>
<link rel="stylesheet" href="/CDEBrowser/css/tooltip.css" type="text/css" />
<SCRIPT LANGUAGE="JavaScript">
<!--
function goPage(pgNum,urlInfo) {
  document.location.href= "search?classificationsForDataElements=9&tabClicked=3&pageNum="+pgNum+"<%= pageUrl %>"+urlInfo;
}
function listChanged(urlInfo) {
  var pgNum = document.forms[0].cs_pages.options[document.forms[0].cs_pages.selectedIndex].value
  document.location.href= "search?classificationsForDataElements=9&tabClicked=3&newSearch=no&pageNum="+pgNum+"<%= pageUrl %>"+urlInfo;
}
  
//-->
</SCRIPT>
<%@ include  file="cdebrowserCommon_html/tab_include.html" %>
<form method="POST" ENCTYPE="application/x-www-form-urlencoded" action="<%= infoBean.getStringInfo("controller") %>">
<input type="HIDDEN" name="<%= PageConstants.PAGEID %>" value="<%= infoBean.getPageId()%>"/>
<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Selected Data Element</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" bgcolor="#999966">

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Public ID:</td>
    <td class="OraFieldText"><%=de.getCDEId()%></td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Version:</td>
    <td class="OraFieldText"><%=de.getVersion()%> </td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Long Name:</td>
    <td class="OraFieldText"><%=de.getLongName()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Short Name:</td>
    <td class="OraFieldText"><%=de.getPreferredName()%></td>
 </tr>
 


 <tr class="OraTabledata">
    <td class="TableRowPromptText">Preferred Question Text:</td>
    <td class="OraFieldText"><%=de.getLongCDEName()%></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Definition:</td>
    <td class="OraFieldText"><%=de.getPreferredDefinition()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Workflow Status:</td>
    <td class="OraFieldText"><%=de.getAslName()%> </td>
 </tr>


 
</table>
<br>


<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Classifications</td>
  </tr>
  <tr>
    <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
  <tr>
    <td><font size="-2" color="#336699">*CS:Classification Scheme&nbsp;&nbsp; CSI:Classification Scheme Item</font></td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0">
  <tr><td align="right"><%=scrollerHTML%></td></tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" bgcolor="#999966">
  <tr class="OraTableColumnHeader">
    <th>CS* Long Name</th>
    <th>CS* Definition</th>
    <th>CS* Public ID</th>
    <th>CS* Version</th>
    <th>CSI* Name</th>
    <th>CSI* Type</th>
  </tr>
<%
  Classification classification;
  int classificationCount = classificationVector.size();
  if (classificationCount > 0) {
    for (int i=0;i<classificationCount; i++) {
      classification = (Classification)classificationVector.elementAt(i);
  %>
      <tr class="OraTabledata">
        <td class="OraFieldText" onmouseout="popUp(event,'<%=classification.getCsIdseq()%>')" onmouseover="popUp(event,'<%=classification.getCsIdseq()%>')" onclick="return false"><%=classification.getClassSchemeLongName()%> </td>
        <td class="OraFieldText"><%=classification.getClassSchemeDefinition()%> </td>
        <td class="OraFieldText">
          <%= classification.getClassSchemePublicId()%>
         </td>
        <td class="OraFieldText"><%=classification.getCsVersion()%> </td>
        <td class="OraFieldText"><%=classification.getClassSchemeItemName()%> </td>
        <td class="OraFieldText"><%=classification.getClassSchemeItemType()%> </td>
      </tr>
<%
    }
  }
  else {
%>
       <tr class="OraTabledata">
         <td colspan="6">There are no classifications for the selected CDE.</td>
       </tr>
<%
  }
%>
</table>
<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Classifications Scheme Reference Documents</td>
  </tr>
  <tr>
    <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
  <tr>
    <td><font size="-2" color="#336699">*CS:Classification Scheme&nbsp;&nbsp; </font></td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" bgcolor="#999966">
  <tr class="OraTableColumnHeader">
    <th>CS* Long Name</th>
    <th>CS* Version</th>
   <th>Document Name</th>
   <th>Document Type</th>
   <th>Document Text</th>
   <th>URL</th>
   <th>Attachments</th>
  </tr>
<%
  if (csRefDocs!= null &&  csRefDocs.keySet().size() >0) {
    Iterator csIter = csRefDocs.keySet().iterator(); 
    while (csIter.hasNext()) {
       Classification cs = (Classification) csIter.next(); 
       List refDocs =(List) csRefDocs.get(cs);
       for (int i=0;i<refDocs.size(); i++) {
         ReferenceDocument refDoc = (ReferenceDocument)refDocs.get(i);
 %>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=cs.getClassSchemeLongName()%></td>
        <td class="OraFieldText"><%=cs.getCsVersion()%></td>
        <td class="OraFieldText"><%=refDoc.getName()%> </td>
        <td class="OraFieldText"><%= refDoc.getType()%> </td>
        <td class="OraFieldText">
        <% if (refDoc.getText() != null) { %>
        <%=refDoc.getText()%>  
        <%} %>
        </td>
        <td class="OraFieldText">   
        <% if (refDoc.getUrl() != null) { %>
        <a href="<%=refDoc.getUrl()%>" target="AuxWindow"  >
                    <%=refDoc.getUrl()%>
                    </a>
        <%} %>
         </td>
         <td>
         
         <% Iterator attIter = refDoc.getAttachments().iterator();
         while (attIter.hasNext()) { 
            ReferenceDocumentAttachment refDocAtt = (ReferenceDocumentAttachment) attIter.next(); %>
                 <a href='<%=request.getContextPath()+ "/ocbrowser/viewRefDocAttchment.do?"+NavigationConstants.METHOD_PARAM+"=viewReferenceDocAttchment&"
                 +FormConstants.REFERENCE_DOC_ATTACHMENT_NAME+"="+refDocAtt.getName()%>' 
                   target="_blank" > <%=refDocAtt.getName()%>
                 </a>                 
                        <br>
<%
    }
%>         
          </td>
 </tr>
 <%
      }
  }
  }
  else { %>
  
       <tr class="OraTabledata">
         <td colspan="7">There are no reference documents for the classifications.</td>
       </tr>
  
  <%
  }
%>
</table>

<br>

<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Classification Scheme Item Reference Document</td>
  </tr>
  <tr>
    <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
  <tr>
    <td><font size="-2" color="#336699">CSI:Classification Scheme Item</font></td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" bgcolor="#999966">
  <tr class="OraTableColumnHeader">
    <th>CSI* Name</th>
   <th>Document Name</th>
   <th>Document Type</th>
   <th>Document Text</th>
   <th>URL</th>
   <th>Attachments</th>
  </tr>
<%
  if (csiRefDocs!= null &&  csiRefDocs.keySet().size() >0) {
    Iterator csiIter = csiRefDocs.keySet().iterator(); 
    while (csiIter.hasNext()) {
       String csiName = (String) csiIter.next(); 
       List refDocs =(List) csiRefDocs.get(csiName);
       for (int i=0;i<refDocs.size(); i++) {
         ReferenceDocument refDoc = (ReferenceDocument)refDocs.get(i);
 %>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=csiName%></td>
        <td class="OraFieldText"><%=refDoc.getName()%> </td>
        <td class="OraFieldText"><%= refDoc.getType()%> </td>
        <td class="OraFieldText">
        <% if (refDoc.getText() != null) { %>
        <%=refDoc.getText()%>  
        <%} %>
        <td class="OraFieldText">   </td>
        <% if (refDoc.getUrl() != null) { %>
        <a href="<%=refDoc.getUrl()%>" target="AuxWindow"  >
                    <%=refDoc.getUrl()%>
                    </a>
        <%} %>
         </td>
         <td>
         
         <% Iterator attIter = refDoc.getAttachments().iterator();
         while (attIter.hasNext()) { 
            ReferenceDocumentAttachment refDocAtt = (ReferenceDocumentAttachment) attIter.next(); %>
                 <a href='<%=request.getContextPath()+ "/ocbrowser/viewRefDocAttchment.do?"+NavigationConstants.METHOD_PARAM+"=viewReferenceDocAttchment&"
                 +FormConstants.REFERENCE_DOC_ATTACHMENT_NAME+"="+refDocAtt.getName()%>' 
                   target="_blank" > <%=refDocAtt.getName()%>
                 </a>                 
                        <br>
<%
    }
%>         
          </td>
 </tr>
 <%
      }
  }
  }
  else { %>
  
       <tr class="OraTabledata">
         <td colspan="6">There are no reference documents for the classifications.</td>
       </tr>
  
  <%
  }
%>
</table>


</form>

<%@ include file="../common/common_bottom_border.jsp"%>

</BODY>
</HTML>
