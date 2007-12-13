<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%//@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.* " %>
<%@page import="java.util.*" %>

<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="cdebrowserCommon_html/SessionAuth.html"%>

<%
  DataElement de = (DataElement)infoBean.getInfo("de");
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  String pageId = infoBean.getPageId();
  String pageName = PageConstants.PAGEID;
  String pageUrl = "&"+pageName+"="+pageId;
    
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
Data Element Details
</TITLE>
<SCRIPT LANGUAGE="JavaScript1.1" SRC="/CDEBrowser/jsLib/checkbox.js"></SCRIPT>
</HEAD>
<BODY topmargin="0">



<SCRIPT LANGUAGE="JavaScript">
<!--
function redirect1(detailReqType, linkParms )
{
  document.location.href="search?dataElementDetails=" + linkParms;
  
}
function goPage(pageInfo) {
  document.location.href = "search?searchDataElements=&"+pageInfo;
  
}


function anotherDataElementDetails(linkParms, version )
{
  var urlString="/CDEBrowser/search?dataElementDetails=9" + linkParms + "&PageId=GetDetailsGroup"+"&queryDE=yes";
  
  //remove the dot. javascript does not like dot.
  var temp = new Array();
  temp = version.split('.');
  
  var versionStr = temp[0]+'_'+temp[1];
  var myWindowName = "deDetails_v" + versionStr;
  
  newBrowserWin(urlString, myWindowName, 800, 600);
}

  
//-->
</SCRIPT>
<%@ include  file="cdebrowserCommon_html/tab_include.html" %>
<form method="POST" ENCTYPE="application/x-www-form-urlencoded" action="<%= infoBean.getStringInfo("controller") %>">
<input type="HIDDEN" name="<%= PageConstants.PAGEID %>" value="<%= infoBean.getPageId()%>"/>

<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Data Element Details</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">

 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Public ID:</td>
    <td class="OraFieldText"><%=de.getPublicId()%></td>
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
    <td class="TableRowPromptText">Value Domain:</td>
    <td class="OraFieldText"><%=de.getValueDomain().getLongName()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Data Element Concept:</td>
    <td class="OraFieldText"><%=de.getDataElementConcept().getLongName()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Context:</td>
    <td class="OraFieldText"><%=de.getContextName()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Workflow Status:</td>
    <td class="OraFieldText"><%=de.getAslName()%> </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Origin:</td>
    <td class="OraFieldText"><%=de.getOrigin()%> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Registration Status:</td>
    <td class="OraFieldText"><%=de.getRegistrationStatus()%> </td>
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
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Document Name</th>
    <th class="OraTableColumnHeader">Document Type</th>
    <th class="OraTableColumnHeader">Document Text</th>
    <th class="OraTableColumnHeader">Context</th>
    <th class="OraTableColumnHeader">URL</th>
  </tr>
<%
  ReferenceDocument rd;
  List refDocs = de.getRefereceDocs();
  int numberOfDocs = refDocs.size();
  if (numberOfDocs > 0) {
    for (int i=0;i<numberOfDocs; i++) {
      rd = (ReferenceDocument)refDocs.get(i);
%>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=rd.getDocName()%> </td>
        <td class="OraFieldText"><%=rd.getDocType()%> </td>
        <td class="OraFieldText"><%=rd.getDocText()%> </td>
        <td class="OraFieldText">
         <% if (rd.getContext() != null) {
         %><%=rd.getContext().getName()%> <% } %></td>
        <td class="OraFieldText"><a href="<%=rd.getUrl()%>" target="AuxWindow"> <%=rd.getUrl()%> </a></td>
      </tr>
<%
    }
  }
  else {
%>
       <tr class="OraTabledata">
         <td colspan="5">There are no reference documents for the selected CDE.</td>
       </tr>
<%
  }
%>
</table>
<%
    List altNames = de.getDesignations();
    List altDefs = de.getDefinitions();
    
    HashMap idToCscsi = new HashMap();
    HashMap csToAltName = new HashMap();
    HashMap csToAltDef = new HashMap();
    List altNamesNoCS = new ArrayList();
    List altDefsNoCS = new ArrayList();
    if (altNames !=null)
    for (int i=0; i<altNames.size(); i++) {
    	Designation des = (Designation) altNames.get(i);
    	if (des.getCsCsis() == null || des.getCsCsis().size() == 0) 
    	   altNamesNoCS.add(des);
    	else {
		Iterator iter = des.getCsCsis().iterator();

		while (iter.hasNext()) {
		   ClassSchemeItem cscsi= (ClassSchemeItem) iter.next();
		   idToCscsi.put(cscsi.getCsCsiIdseq(), cscsi);
		   if (csToAltName.get(cscsi.getCsCsiIdseq()) == null)
			csToAltName.put(cscsi.getCsCsiIdseq(), new ArrayList());
		   ((ArrayList) csToAltName.get(cscsi.getCsCsiIdseq())).add(des);
		}
    	}
    }
  
  if (altDefs != null)
    for (int i=0; i<altDefs.size(); i++) {
      Definition definition = (Definition) altDefs.get(i);
	if (definition.getCsCsis() == null || definition.getCsCsis().size() == 0) 
	   altDefsNoCS.add(definition);
	else {
	  Iterator iter = definition.getCsCsis().iterator();
	  while (iter.hasNext()) {
	    ClassSchemeItem cscsi= (ClassSchemeItem) iter.next();
	    idToCscsi.put(cscsi.getCsCsiIdseq(), cscsi);
	    if (csToAltDef.get(cscsi.getCsCsiIdseq()) == null)
		csToAltDef.put(cscsi.getCsCsiIdseq(), new ArrayList());
	    ((ArrayList) csToAltDef.get(cscsi.getCsCsiIdseq())).add(definition);
	}
    	}
    }
    
    if (altNamesNoCS.size() >0) {
    	idToCscsi.put("null", null);
    	csToAltName.put("null", altNamesNoCS);
    }
    	
    if (altDefsNoCS.size() >0) {
    	idToCscsi.put("null", null);
    	csToAltDef.put("null", altDefsNoCS);
    }
  %>

<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%">Alternate Names and Definitions</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>
<p>
<% Iterator csiIter = idToCscsi.keySet().iterator();
while (csiIter.hasNext()) {
    String csiId = (String) csiIter.next();
    ClassSchemeItem currCSI = (ClassSchemeItem) idToCscsi.get(csiId);
    List currAltNames = (List) csToAltName.get(csiId);
    List currAltDefs = (List) csToAltDef.get(csiId);
 %>   
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
<% if (currCSI != null) { %>
<TR class="OraTableColumnHeader">
 <th class="OraTableColumnHeader">CS* Long Name</th>
 <th class="OraTableColumnHeader">CS* Definition</th>
 <th class="OraTableColumnHeader">CSI* Name</th>
 <th class="OraTableColumnHeader">CSI* Type</th>
</TR>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=currCSI.getClassSchemeLongName()%> </td>
        <td class="OraFieldText"><%=currCSI.getClassSchemeDefinition()%> </td>
        <td class="OraFieldText"><%=currCSI.getClassSchemeItemName()%> </td>
        <td class="OraFieldText"><%=currCSI.getClassSchemeItemType()%> </td>
      </tr>
<%} %>

<tr class="OraTabledata">
<td class="OraHeaderSubSubSub" width="100%" colspan=4>Alternate Names </td>
</tr>
<tr class="OraTabledata"><td width="100%" colspan=4>
<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Name</th>
    <th class="OraTableColumnHeader">Type</th>
    <th class="OraTableColumnHeader">Context</th>
    <th class="OraTableColumnHeader">Language</th>
  </tr>
<%
  Designation des = null;
  if (currAltNames!=null && currAltNames.size() > 0) {
    for (int i=0;i<currAltNames.size(); i++) {
      des = (Designation)currAltNames.get(i);
%>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=des.getName()%> </td>
        <td class="OraFieldText"><%=des.getType()%> </td>
        <td class="OraFieldText"><%=des.getContext().getName()%> </td>
        <td class="OraFieldText"><%=des.getLanguage()%> </td>
      </tr>
<%
    }
  }
  else {
%>
       <tr class="OraTabledata">
         <td colspan=4">There are no alternate names for the selected CDE.</td>
       </tr>
<%
  }
%>

</table> 
</td></tr>
<tr class="OraTabledata">
<td class="OraHeaderSubSubSub" width="100%" colspan=4>Alternate Definitions </td>
</tr>
<tr class="OraTabledata"><td width="100%" colspan=4>
<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Name</th>
    <th class="OraTableColumnHeader">Type</th>
    <th class="OraTableColumnHeader">Context</th>
  </tr>
<%
  if (currAltDefs != null && currAltDefs.size() > 0) {
    for (int i=0;i<currAltDefs.size(); i++) {
      Definition def = (Definition)currAltDefs.get(i);
%>
      <tr class="OraTabledata">
        <td class="OraFieldText"><%=def.getDefinition()%> </td>
        <td class="OraFieldText"><%=def.getType()%> </td>
        <td class="OraFieldText"><%=def.getContext().getName()%> </td>
      </tr>
<%
    }
  }
  else {
%>
       <tr class="OraTabledata">
         <td colspan=4">There are no alternate definitions for the selected CDE.</td>
       </tr>
<%
  }
%>

</table> 
</td></tr>
</table>
<p>
<%} %>












<%--start all versions--%>
<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%">Other Versions</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>


<% List otherVersions = de.getOtherVersions();
   if (otherVersions==null || otherVersions.isEmpty()){
%>
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
<TR class="OraTableColumnHeader">
 <th class="OraTableColumnHeader">Version</th>
 <th class="OraTableColumnHeader">Long Name</th>
 <th class="OraTableColumnHeader">Workflow Status</th>
 <th class="OraTableColumnHeader">Registration Status</th>
 <th class="OraTableColumnHeader">Context</th>
</TR>
<tr class="OraTabledata">
   <td colspan="5" class="OraFieldText">
   No other versions available
   </td>
</tr>
</table>
<%
   }else{
    Iterator it = otherVersions.iterator();
    while (it.hasNext()) 
    {
    DataElement thisDe = (DataElement) it.next();
    List csiList = thisDe.getClassifications() ==null? null: (List)thisDe.getClassifications(); 
 %>   
<p>
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
<TR class="OraTableColumnHeader">
 <th class="OraTableColumnHeader">Version</th>
 <th class="OraTableColumnHeader">Long Name</th>
 <th class="OraTableColumnHeader">Workflow Status</th>
 <th class="OraTableColumnHeader">Registration Status</th>
 <th class="OraTableColumnHeader">Context</th>
</TR>
      <tr class="OraTabledata">
      <td class="OraFieldText">
      	<a href ="javascript:anotherDataElementDetails('<%="&p_de_idseq=" + thisDe.getDeIdseq()%>','<%=thisDe.getVersion()%>')">           
          <%=thisDe.getVersion()%> 
	</a>  
      </td>
        <td class="OraFieldText"><%=thisDe.getLongName()%> </td>
        <td class="OraFieldText"><%=thisDe.getAslName()%> </td>
        <td class="OraFieldText"><%=thisDe.getRegistrationStatus()%> </td>
        <td class="OraFieldText"><%=thisDe.getContext().getName()%> </td>
      </tr>
	<%--classifications--%>
	<tr class="OraTabledata">
	<td class="OraHeaderSubSubSub" width="100%" colspan=5>Classifications </td>
	</tr>
<%--	<tr class="OraFieldText">
	    <td colspan="5" ><font size="-2" color="#336699">*CS:Classification Scheme&nbsp;&nbsp; CSI:Classification Scheme Item</font></td>
	</tr>
--%>
	<tr class="OraTabledata">
	<td width="100%" colspan=5>
        <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
	  <tr class="OraTableColumnHeader">
	    <th>CS* Long Name</th>
	    <th>CS* Definition</th>
	    <th>CS* Public ID</th>
	    <th>CSI* Name</th>
	    <th>CSI* Type</th>
	  </tr>

	<% 
	if ( csiList ==null || csiList.isEmpty() ){
	%> <tr class="OraTabledata"> 
	   <td colspan="5" class="OraFieldText">There are no classifications for the selected CDE
	   </td>
	   </tr>
	<%}
	 else
	 {	  
	  int classificationCount = csiList.size();
	  Classification csi = null;
          for (int i=0; i < classificationCount; i++) {
	     csi = (Classification)(csiList.get(i));
	%>
	      <tr class="OraTabledata">
		<td class="OraFieldText"><%=csi.getClassSchemeLongName()%> </td>
		<td class="OraFieldText"><%=csi.getClassSchemeDefinition()%> </td>
		<td class="OraFieldText">
		  <%= csi.getClassSchemePublicId()%>
		 </td>
		<td class="OraFieldText"><%=csi.getClassSchemeItemName()%> </td>
		<td class="OraFieldText"><%=csi.getClassSchemeItemType()%> </td>
	      </tr>
	<%
	    }//end of for
	  }  
	%>
	</table>
        </td>
	</tr>
  </table>
  </p>
<%
 }//end of while
}//end of else
%>      

</form>

<%@ include file="../common/common_bottom_border.jsp"%>

</BODY>
</HTML>

