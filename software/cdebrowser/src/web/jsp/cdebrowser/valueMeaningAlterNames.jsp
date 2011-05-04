<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@page import="java.util.*" %>
<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.* " %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>


<html>
  <head>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
    <title>Value Meaning Details</title>
    
    <SCRIPT LANGUAGE="JavaScript1.1">
    function passBack(alter, columnName) {
	    var objForm0 = opener.document.forms[0];	    
            var objAlter = objForm0[columnName + '[' + <%=request.getParameter("vvColumnIndex")%> + ']'];	    
	    objAlter.value = alter;
	    close();
    }
    </SCRIPT>
<script type="text/javascript" src="/CDEBrowser/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true">
    </script>
<link rel="stylesheet" type="text/css" href="/CDEBrowser/js/dojo/dijit/themes/claro/claro.css" />
<style type="text/css">
	@import url("/CDEBrowser/js/dojo/dojox/grid/enhanced/resources/claroEnhancedGrid.css");
	@import url("/CDEBrowser/js/dojo/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css");
</style>
</head>
  <body>

    <% 
    
    ValueMeaning vm = (ValueMeaning)request.getAttribute(CaDSRConstants.VALUE_MEANING_OBJ);
    config.getServletContext().setAttribute("vm", vm);
  %>

<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%" colspan="2">Value Meaning Alternate Names and Definitions</td>
  </tr>
  <tr>
    <td width="100%" colspan="2"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
</table>
<p>
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
      <tr class="OraTabledata">
        <td class="OraTableColumnHeader" width="20%">VM Public Id</td>
        <td class="OraFieldText"><%=vm.getPublicId()%></td>
      </tr>
      <tr class="OraTabledata">
        <td class="OraTableColumnHeader" width="20%">VM Version</td>
        <td class="OraFieldText"><%=vm.getVersion()%></td>
      </tr>
      <tr class="OraTabledata">
        <td class="OraTableColumnHeader" width="20%">VM Context</td>
        <td class="OraFieldText"><%=vm.getContext().getName()%></td>
      </tr>
      <tr class="OraTabledata">
        <td class="OraTableColumnHeader" width="20%">VM Long Name</td>
        <td class="OraFieldText"><%=vm.getLongName()%>
	<br>
        <% String longName = vm.getLongName();
           if (longName!=null){
		longName = StringUtils.strReplace(longName, "\"","&quot;");
	      	longName = StringUtils.strReplace(longName, "\'",  "&acute;");
	   }   	
	%>      		
        <logic:notPresent name="CDEBrowser">
       	<a href="javascript:passBack('<%=longName%>', 'formsValueMeaningTexts')">Select as Value Meaning Text</a>
        </logic:notPresent>
       	</td>

      </tr>
      <tr class="OraTabledata">
        <td class="OraTableColumnHeader">VM Preferred Definition</td>
        <td class="OraFieldText"><%=vm.getPreferredDefinition()%>
	<br>
        <% String desc = vm.getPreferredDefinition();
           if (desc!=null){
		desc = StringUtils.strReplace(desc, "\"","&quot;");
	      	desc = StringUtils.strReplace(desc, "\'",  "&acute;");
	   }   	
	%>      		
        <logic:notPresent name="CDEBrowser">
       	<a href="javascript:passBack('<%=desc%>', 'formsValueMeaningDescs')">Select as Value Meaning Desc.</a>
        </logic:notPresent>
       	</td>

      </tr>
      <tr class="OraTabledata">
        <td class="OraTableColumnHeader" width="20%">VM Workflow Status</td>
        <td class="OraFieldText"><%=vm.getAslName()%></td>
      </tr>
</table>
<p>
<%
    List altNames = vm.getDesignations();
    List altDefs = vm.getDefinitions();
    
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

	var struct1 = [{field: "type", name: "Type", width: "100px", 
		styles: "font-family:Arial, Helvetica, Geneva, sans-serif; font-size:10pt; background-color: #f7f7e7;", 
		headerStyles: "FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: #336699; FONT-FAMILY: Arial, Helvetica, Geneva, sans-serif; background-color: #CCCC99; background-image: none"},
{field: "value", name: "Value", width: "200px", 
			styles: "font-family:Arial, Helvetica, Geneva, sans-serif; font-size:10pt;background-color: #f7f7e7;", 
			headerStyles: "FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: #336699; FONT-FAMILY: Arial, Helvetica, Geneva, sans-serif; background-color: #CCCC99; background-image: none"},
{field: "context", name: "Context", width: "100px", 
				styles: "font-family:Arial, Helvetica, Geneva, sans-serif; font-size:10pt; background-color: #f7f7e7;", 
				headerStyles: "FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: #336699; FONT-FAMILY: Arial, Helvetica, Geneva, sans-serif; background-color: #CCCC99; background-image: none"},
{field: "CS", name: "<center>Classification Schemes<br/><table align='center'><col width='50%'><col width='30%'><col width='20%'><tr><td class='TableRowPromptText'><center>Class Scheme Item</center></td><td class='TableRowPromptText'><center>Class Scheme</center></td><td class='TableRowPromptText'><center>Class Scheme WF Status</center></td></tr></table></center>", 
		width: "400px", 
		styles: "font-family:Arial, Helvetica, Geneva, sans-serif; font-size:10pt; background-color: #f7f7e7;", 
		headerStyles: "FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: #336699; FONT-FAMILY: Arial, Helvetica, Geneva, sans-serif; background-color: #CCCC99; background-image: none"}];
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
<logic:notEmpty name="vm" property="designations">
	<logic:iterate id="des" name="vm" property="designations" type="gov.nih.nci.ncicb.cadsr.common.resource.Designation">
	      <tr class="OraTabledata">
	        <td class="OraFieldText">Alt Name</td>
			<td class="OraFieldText">&lt;label title='<bean:write name="des" property="type" />'&gt;<bean:write name="des" property="name" />&lt;/label&gt; </td>
	        <td class="OraFieldText"><bean:write name="des" property="context.name" /> </td>
	        <td class="OraFieldText">
				<logic:notEmpty name="des" property="csCsis">
					&lt;table width="400px" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDarkFixWidth"&gt;
						&lt;col width="50%"&gt;
						&lt;col width="30%"&gt;
						&lt;col width="20%"&gt;
						<logic:iterate id="cs" name="des" indexId="idx" property="csCsis" type="gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem">
							&lt;tr class="OraTabledata"&gt;
								&lt;td class="OraFieldText"&gt;<bean:write name="cs" property="classSchemeItemName" />&lt;/td&gt;
								&lt;td class="OraFieldText"&gt;<bean:write name="cs" property="classSchemeLongName" />&lt;/td&gt;
								&lt;td class="OraFieldText"&gt;<bean:write name="cs" property="classSchemeWfStatus" />&lt;/td&gt;
							&lt;/tr&gt;
						</logic:iterate>
					&lt;/table&gt;
				</logic:notEmpty>
			</td>
	      </tr>
	</logic:iterate>
</logic:notEmpty>  

<logic:notEmpty name="vm" property="definitions">
	<logic:iterate id="def" name="vm" property="definitions" type="gov.nih.nci.ncicb.cadsr.common.resource.Definition">
	      <tr class="OraTabledata">
			<td class="OraFieldText">Alt Definition</td>
	        <td class="OraFieldText">&lt;label title='<bean:write name="def" property="type" />'&gt;<bean:write name="def" property="definition" />&lt;/label&gt; </td>
	        <td class="OraFieldText"><bean:write name="def" property="context.name" /> </td>
	        <td class="OraFieldText">
				<logic:notEmpty name="def" property="csCsis">
					&lt;table width="400px" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDarkFixWidth"&gt;
						&lt;col width="50%"&gt;
						&lt;col width="30%"&gt;
						&lt;col width="20%"&gt;
						<logic:iterate id="cs" name="def" indexId="idx" property="csCsis" type="gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem">
							&lt;tr class="OraTabledata"&gt;
								&lt;td class="OraFieldText"&gt;<bean:write name="cs" property="classSchemeItemName" />&lt;/td&gt;
								&lt;td class="OraFieldText"&gt;<bean:write name="cs" property="classSchemeLongName" />&lt;/td&gt;
								&lt;td class="OraFieldText"&gt;<bean:write name="cs" property="classSchemeWfStatus" />&lt;/td&gt;
							&lt;/tr&gt;
						</logic:iterate>
					&lt;/table&gt;
				</logic:notEmpty>
			</td>
	      </tr>
	</logic:iterate>
</logic:notEmpty>  

</tbody>
</table>
<div dojoType="dojox.data.HtmlStore" dataId="altNames" jsId="htmlStor"></div>

<div style="{width: 100%; height:400px}">
<div id="nameDefGrid" dojoType="dojox.grid.EnhancedGrid" store="htmlStor" query="{}" escapeHTMLInData="false"
				selectable="true" structure= "struct1" plugins="{dnd: true, nestedSorting: true}" 
				rowSelector="0px" elasticView="2" style="{width: 100%; height:400px; background-color: #f7f7e7;}"
				noDataMessage="There are no alternate names or definitions for the selected VM"></div>
</div>

</td></tr>
</table >

</body>
</html>