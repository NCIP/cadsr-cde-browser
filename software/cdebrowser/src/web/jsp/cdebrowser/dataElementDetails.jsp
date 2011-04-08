<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%//@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.* " %>
<%@page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="/jsp/cdebrowser/cdebrowserCommon_html/SessionAuth.html"%>

<%
  DataElement de = (DataElement)infoBean.getInfo("de");
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  String pageId = StringEscapeUtils.escapeJavaScript(infoBean.getPageId());
  String pageName = StringEscapeUtils.escapeJavaScript(PageConstants.PAGEID);
  String pageUrl = "&"+StringEscapeUtils.escapeJavaScript(pageName+"="+pageId);
  CDEBrowserParams params = CDEBrowserParams.getInstance();    
  config.getServletContext().setAttribute("de", de);
  String appPath = request.getRequestURL().toString().replace(request.getServletPath(),"");
  String directURL = appPath+"/search?elementDetails=9&FirstTimer=0&publicId="+de.getPublicId()+"&version="+de.getVersion();
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
Data Element Details
</TITLE>
<SCRIPT LANGUAGE="JavaScript1.1" SRC="/CDEBrowser/js/checkbox.js"></SCRIPT>
<script type="text/javascript" src="/CDEBrowser/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true">
    </script>
<link rel="stylesheet" type="text/css" href="/CDEBrowser/js/dojo/dijit/themes/claro/claro.css" />
<style type="text/css">
	@import url("/CDEBrowser/js/dojo/dojox/grid/enhanced/resources/claroEnhancedGrid.css");
	@import url("/CDEBrowser/js/dojo/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css");
</style>
</HEAD>
<BODY topmargin="0">



<SCRIPT LANGUAGE="JavaScript">
<!--
function redirect1(detailReqType, linkParms )
{
  document.location.href="search?dataElementDetails=" + linkParms;
  
}
function goPage(pageInfo) {
  document.location.href = "<%=StringEscapeUtils.escapeJavaScript("search?searchDataElements=")+"&"%>"+pageInfo;
  
}


function anotherDataElementDetails(linkParms, version )
{
  var urlString="/CDEBrowser/" + "<%=StringEscapeUtils.escapeJavaScript("search?dataElementDetails=9")%>" + linkParms + "<%="&"+StringEscapeUtils.escapeHtml("PageId=GetDetailsGroup")+"&"+StringEscapeUtils.escapeHtml("queryDE=yes")%>";
  
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
<input type="HIDDEN" name="<%= PageConstants.PAGEID %>" value="<%= StringEscapeUtils.escapeJavaScript(infoBean.getPageId())%>"/>

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
<tr class="OraTabledata">
    <td class="TableRowPromptText">Direct Link:</td>
    <td class="OraFieldText"><a href="<%= directURL %>"><%= directURL %></a> </td>
 </tr>
 
</table>


<%
    List altNames = de.getDesignations();
    List altDefs = de.getDefinitions();
    
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

<script type="text/javascript">
	dojo.require("dojox.grid.EnhancedGrid");
	dojo.require("dojox.data.HtmlStore");
	dojo.require("dojox.grid.enhanced.plugins.DnD");
	dojo.require("dojox.grid.enhanced.plugins.NestedSorting");
	
	var struct1 = [{field: "type", name: "Type", width: "100px"},
	               {field: "value", name: "Value", width: "200px"},
					{field: "context", name: "Context", width: "100px"},
					{field: "CS", name: "<center>Classification Schemes<br/><table align='center'><col width='70%'><col width='30%'><tr><td><center>Classification Scheme</center></td><td><center>Classification Scheme Item</center></td></tr></table></center>", width: "400px"}];
</script>
                
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
<tr class="OraTabledata">
	<td class="OraHeaderSubSubSub" width="100%" colspan="4">Alternate Names & Definitions</td>
</tr>
<tr class="OraTabledata"><td width="100%" colspan="4" >
	<table id="altNames" width="100%" align="center" cellpadding="1" cellspacing="1" border="0" style="{display: none}">
	<thead>  
	<tr class="OraTableColumnHeader">
	    <th class="OraTableColumnHeader">type</th>
	    <th class="OraTableColumnHeader">value</th>
	    <th class="OraTableColumnHeader">context</th>
		<th class="OraTableColumnHeader">CS</th>
	  </tr>
	</thead>
	<tbody>
<logic:notEmpty name="de" property="designations">
	<logic:iterate id="des" name="de" property="designations" type="gov.nih.nci.ncicb.cadsr.common.resource.Designation">
	      <tr class="OraTabledata">
	        <td class="OraFieldText">Alt Name</td>
			<td class="OraFieldText">&lt;label title='<bean:write name="des" property="type" />'&gt;<bean:write name="des" property="name" />&lt;/label&gt; </td>
	        <td class="OraFieldText"><bean:write name="des" property="context.name" /> </td>
	        <td class="OraFieldText">
				<logic:notEmpty name="des" property="csCsis">
					&lt;table width="400px" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDarkFixWidth"&gt;
						&lt;col width="70%"&gt;
						&lt;col width="30%"&gt;
						<logic:iterate id="cs" name="des" indexId="idx" property="csCsis" type="gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem">
							&lt;tr class="OraTabledata"&gt;
								&lt;td class="OraFieldText"&gt;<bean:write name="cs" property="classSchemeItemName" />&lt;/td&gt;
								&lt;td class="OraFieldText"&gt;<bean:write name="cs" property="classSchemeLongName" />&lt;/td&gt;
							&lt;/tr&gt;
						</logic:iterate>
					&lt;/table&gt;
				</logic:notEmpty>
			</td>
	      </tr>
	</logic:iterate>
</logic:notEmpty>  
<logic:empty name="de" property="designations">
	<tr class="OraTabledata">
        <td colspan="4">There are no alternate names for the selected CDE.</td>
      </tr>
</logic:empty>
<logic:notEmpty name="de" property="definitions">
	<logic:iterate id="def" name="de" property="definitions" type="gov.nih.nci.ncicb.cadsr.common.resource.Definition">
	      <tr class="OraTabledata">
			<td class="OraFieldText">Alt Definition</td>
	        <td class="OraFieldText">&lt;label title='<bean:write name="def" property="type" />'&gt;<bean:write name="def" property="definition" />&lt;/label&gt; </td>
	        <td class="OraFieldText"><bean:write name="def" property="context.name" /> </td>
	        <td class="OraFieldText">
				<logic:notEmpty name="def" property="csCsis">
					&lt;table width="400px" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDarkFixWidth"&gt;
						&lt;col width="70%"&gt;
						&lt;col width="30%"&gt;
						<logic:iterate id="cs" name="def" indexId="idx" property="csCsis" type="gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem">
							&lt;tr class="OraTabledata"&gt;
								&lt;td class="OraFieldText"&gt;<bean:write name="cs" property="classSchemeItemName" />&lt;/td&gt;
								&lt;td class="OraFieldText"&gt;<bean:write name="cs" property="classSchemeLongName" />&lt;/td&gt;
							&lt;/tr&gt;
						</logic:iterate>
					&lt;/table&gt;
				</logic:notEmpty>
			</td>
	      </tr>
	</logic:iterate>
</logic:notEmpty>  
<logic:empty name="de" property="definitions">
	<tr class="OraTabledata">
        <td colspan="4">There are no alternate definitions for the selected CDE.</td>
      </tr>
</logic:empty>
</tbody>
</table>
<div dojoType="dojox.data.HtmlStore" dataId="altNames" jsId="htmlStor"></div>

<div style="{width: 100%; height:400px}">
<div dojoType="dojox.grid.EnhancedGrid" store="htmlStor" query="{}" escapeHTMLInData="false"
				selectable="true" structure= "struct1" plugins="{dnd: true, nestedSorting: true}"></div>
</div>

</td></tr>
</table >


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

<%@ include file="/jsp/common/common_bottom_border.jsp"%>

</BODY>
</HTML>

