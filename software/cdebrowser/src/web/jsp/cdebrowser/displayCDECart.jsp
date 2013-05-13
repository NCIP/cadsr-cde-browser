<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@ page import="oracle.clex.process.jsp.GetInfoBean"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.*"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.struts.common.BrowserNavigationConstants"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>

<HTML>
	<HEAD>
		<TITLE>Display CDE Cart</TITLE>
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache" />
		<LINK REL=STYLESHEET TYPE="text/css"
			HREF="<%=request.getContextPath()%>/css/blaf.css">
		<SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/js/checkbox.js"></SCRIPT>
		<SCRIPT LANGUAGE="JavaScript">

var sItems = "selectedItems"; 
var dItems = "selectedItems";

function submitForm() {
  document.forms[0].submit();
}

function saveItems(sItems) {  
  if (validateSelection(sItems,'Please select at least one data element to save to your CDE Cart.')) {
   document.forms[0].method.value = 'addItems'
   submitForm();
   return true;
  }
}

function deleteItems(dItems) {
  if (validateSelection(dItems,'Please select at least one data element to delete from your CDE Cart.')) {
    document.forms[0].method.value = 'removeItems'
    submitForm();
  }
}

function ToggleSaveAll(e){
	if (e.checked) {
	    setChecked(1,'selectedSaveItems');
	}
	else {
	    setChecked(0,'selectedSaveItems');
	}
}

function ToggleDeleteAll(e){
	if (e.checked) {
	    setChecked(1,'selectedDeleteItems');
	}
	else {
	    setChecked(0,'selectedDeleteItems');
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



</SCRIPT>
	</HEAD>
	<BODY bgcolor="#ffffff" topmargin="0">

		<%
			String contextPath = StringEscapeUtils.escapeHtml(request.getContextPath());
			String urlPrefix = "";
			String downloadXMLURL = "javascript:fileDownloadWin('"+ contextPath+ "/jsp/cdebrowser/downloadXMLPage.jsp?src=cdeCart','xmlWin',500,200)";
			String downloadExcelURL = "javascript:fileDownloadWin('"+ contextPath+ "/jsp/cdebrowser/downloadExcelPage.jsp?src=cdeCart','excelWin',500,200)";
			String downloadPriorExcelURL = "javascript:fileDownloadWin('"+ contextPath+ "/jsp/cdebrowser/downloadExcelPage.jsp?src=cdeCartPrior','excelWin',500,200)";
			CDEBrowserParams params = CDEBrowserParams.getInstance();
		%>
		<jsp:include page="/jsp/common/common_cdebrowser_header_jsp_inc.jsp"
			flush="true">
			<jsp:param name="loginDestination"
				value="formCDECartAction.do?method=displayCDECart" />
			<jsp:param name="urlPrefix" value="" />
		</jsp:include>
		<jsp:include page="/jsp/common/tab_inc.jsp" flush="true">
			<jsp:param name="label" value="CDE&nbsp;Cart" />
			<jsp:param name="urlPrefix" value="" />
		</jsp:include>

		<table>
			<tr>
				<logic:present name="nciUser">
					<td align="left" class="AbbreviatedText">
						<bean:message key="cadsr.formbuilder.helpText.cart.secure" />
					</td>
				</logic:present>
				<logic:notPresent name="nciUser">
					<td align="left" class="AbbreviatedText">
						<bean:message key="cadsr.formbuilder.helpText.cart" />
					</td>
				</logic:notPresent>
			</tr>
		</table>

		<%@ include file="/jsp/cdebrowser/showMessages.jsp"%>
		<html:form action="/cdeCartAction.do">
			<html:hidden value=""
				property="<%=BrowserNavigationConstants.METHOD_PARAM%>" />
			<logic:present name="<%=CaDSRConstants.CDE_CART%>">
				<logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>"
					property="dataElements">
					<table cellpadding="0" cellspacing="0" width="80%" align="center">
						<tr>
							<td nowrap>
								<b><a href="<%=downloadPriorExcelURL%>"
									title="3.2.0.1 Version">[Download Data Elements to Prior
										Excel]</a> </b> &nbsp;&nbsp;
								<b><a href="<%=downloadExcelURL%>"
									title="3.2.0.2 Includes new content: Value Meaning Description in column BV, Value Meaning Concept(s) in column BW, Value Domain Representation in columns BI-BS.">
										[Download Data Elements to Excel]</a> </b> &nbsp;&nbsp;
								<b><a href="<%=downloadXMLURL%>">[Download Data Elements
										as XML]</a> </b> &nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td width="100%" nowrap>
								<img height=2 src="i/beigedot.gif" alt="beige dot" width="99%" align=top
									border=0>
							</td>
						</tr>
					</table>
				</logic:notEmpty>
				<table width="80%" align="center" cellpadding="1" cellspacing="1"
					border="0" class="OraBGAccentVeryDark">
					<tr class="OraTableColumnHeader">
						<logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>"
							property="dataElements">
							<logic:present name="nciUser">
								<th>
									Save
									<input type="checkbox" name="saveAllChk" value="yes"
										onClick="ToggleSaveAll(this)" />
								</th>
								<th>
									Delete
									<input type="checkbox" name="deleteAllChk" value="yes"
										onClick="ToggleDeleteAll(this)" />
								</th>
							</logic:present>
							<logic:notPresent name="nciUser">
								<th>
									<input type="checkbox" name="allChk" value="yes"
										onClick="ToggleAll(this)" />
								</th>
							</logic:notPresent>
						</logic:notEmpty>
						<th>
							Long Name
						</th>
						<th>
							Doc Text
						</th>
						<th>
							Context
						</th>
						<th>
							Registration Status
						</th>
						<th>
							Workflow Status
						</th>
						<th>
							Public Id
						</th>
						<th>
							Version
						</th>
					</tr>
					<logic:empty name="<%=CaDSRConstants.CDE_CART%>"
						property="dataElements">
						<tr class="OraTabledata">
							<td class="OraFieldText" colspan="7"> CDE Cart is empty.	</td>
						</tr>
						<logic:notPresent name="nciUser">
							<table width="20%" align="center" cellpadding="1" cellspacing="1"
								border="0">
								<TR>
									<td>
										&nbsp;
									</td>
								</TR>
								<tr>
									<td>
										<a href="javascript:retrieveSavedItems()"> <html:img
												src='<%=request.getContextPath() + "/i/retrieve.gif"%>' alt="retrieve" border="0"
												alt="Retrieve Saved Data Elements" /> </a>
									</td>
									<td>
										<html:link href='<%=request.getContextPath() + "/jsp/cdeBrowse.jsp?PageId=DataElementsGroup" %>' >
											<html:img page="/i/backButton.gif" border="0" alt="Back to Data Element Search" />
										</html:link>
									</td>
								</tr>
							</table>
						</logic:notPresent>
					</logic:empty>		
					<logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property="dataElements">
						<bean:size id="noOfItems" name="<%=CaDSRConstants.CDE_CART%>" property="dataElements" />
						<logic:iterate id="de" name="<%=CaDSRConstants.CDE_CART%>"
							type="gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem"
							property="dataElements">
							<% String deId = de.getId();
							   String detailsURL = "javascript:details('&p_de_idseq="+deId +"')";
							%>
							<tr class="OraTabledata">
								<logic:present name="nciUser">
								<script>
									sItems = "selectedSaveItems";
									dItems = "selectedDeleteItems";
								</script>
								<td>
									<logic:equal name="de" property="persistedInd" value="true">
              							&nbsp;       
            						</logic:equal>
									<logic:notEqual name="de" property="persistedInd" value="true">
										<input type="checkbox" name="selectedSaveItems" value="<%=de.getId()%>" />
									</logic:notEqual>
									</td>
									<td>
										<input type="checkbox" name="selectedDeleteItems" value="<%=de.getId()%>" />
									</td>
								</logic:present>
								<logic:notPresent name="nciUser">
									<td>
										<input type="checkbox" name="selectedItems"
											value="<%=de.getId()%>" />
									</td>
								</logic:notPresent>
								<td class="OraFieldText">
									<bean:write name="de" property="item.longName" />
								</td>
								<td class="OraFieldText">
									<bean:write name="de" property="item.longCDEName" />
								</td>
								<td class="OraFieldText">
									<bean:write name="de" property="item.contextName" />
								</td>
								<td class="OraFieldText">
									<bean:write name="de" property="item.registrationStatus" />
								</td>
								<td class="OraFieldText">
									<bean:write name="de" property="item.aslName" />
								</td>
								<td class="OraFieldText">
									<bean:write name="de" property="item.publicId" />
								</td>
								<td class="OraFieldText">
									<bean:write name="de" property="item.version" />
								</td>
							</tr>
						</logic:iterate>
							<tr class="OraTabledata">
								<td class="OraFieldText" colspan="9">
						  			<br/>Total items in cart: <b><bean:write name="noOfItems" /></b>
					        	</td>
							</tr>
						<br>
						<table width="40%" align="center" cellpadding="1" cellspacing="1"
							border="0">
							<tr>
								<logic:notPresent name="nciUser">
									<td>
										<a href="javascript:retrieveSavedItems()()"> <html:img
												src='<%=urlPrefix + "i/retrieve.gif"%>' border="0"
												alt="Retrieve Saved Data Elements" /> </a>
									</td>
								</logic:notPresent>
								<td>
									<a href="javascript:saveItems(sItems)"> <html:img
											src='<%=urlPrefix + "i/save.gif"%>' border="0"
											alt="Save Data Elements" /> </a>
								</td>
								<td>
									<a href="javascript:deleteItems(dItems)"> <html:img
											src='<%=urlPrefix + "i/deleteButton.gif"%>' border="0"
											alt="Remove Data Elements from CDE Cart " /> </a>
								</td>
								<td>
									<html:link page="/jsp/cdeBrowse.jsp?PageId=DataElementsGroup" >
										<html:img src='<%=urlPrefix + "i/backButton.gif"%>' border="0"
											alt="Back to Data Element Search" />
									</html:link>
								</td>
							</tr>
						</table>
					</logic:notEmpty>
			</logic:present>
					<table width="20%" align="center" cellpadding="1" cellspacing="1"
						border="0">
						<tr>
							<td>
								<br>
								<html:link page="/jsp/cdeBrowse.jsp?PageId=DataElementsGroup">
									<html:img page="/i/add_more_data_elements.gif" border="0" alt="Add more data elements" />
								</html:link>
							</td>
						</tr>
					</table>
		</html:form>
		<%@ include file="/jsp/common/common_bottom_border.jsp"%>
	</body>
</html>