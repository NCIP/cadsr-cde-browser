<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
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
			HREF="<%=request.getContextPath()%>/css/blafModified.css">
		<LINK REL=STYLESHEET TYPE="text/css"
			HREF="<%=request.getContextPath()%>/css/btn.css">
		<SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/js/checkbox.js"></SCRIPT>
		<SCRIPT LANGUAGE="JavaScript" SRC="<%=request.getContextPath()%>/js/btn.js"></SCRIPT>
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
function saveItemsNewCart(sItems) {  
  if (validateSelection(sItems,'Please select at least one data element to save to your CDE Cart.') && 
  	validateNewCartName()) {
   document.forms[0].method.value = 'addNewCart'
   submitForm();
   return true;
  }
}
function setModChecked(val,chkName,chkValue) {
  dml=document.forms[0];
  len = dml.elements.length; 
  var i=0;
  for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name==chkName) {
    chkVal = dml.elements[i].value;
    if (chkVal.match('^'+chkValue+':') == chkValue+':'){
    	dml.elements[i].checked=val;
    }
   }
  }
}
function validateNewCartName ( )
{
    valid = true;

    if ( document.forms[0].newCartName.value == "" )
    {
        alert ( "Please fill in the 'New Cart Name' to create new cart." );
        valid = false;
    }

    return valid;
}

function deleteItems(dItems) {
  if (validateSelection(dItems,'Please select at least one data element to delete from your CDE Cart.')) {
    document.forms[0].method.value = 'removeItems'
    submitForm();
  }
}

function ToggleSaveAll(e,cartId){
	if (e.checked) {
	    setModChecked(1,'selectedSaveItems',cartId);
	}
	else {
	    setModChecked(0,'selectedSaveItems',cartId);
	}
}

function ToggleDeleteAll(e,cartId){
	if (e.checked) {
	    setModChecked(1,'selectedDeleteItems',cartId);
	}
	else {
	    setModChecked(0,'selectedDeleteItems',cartId);
	}
}
function ToggleAll(e, name){
	if (e.checked) {
	    setChecked(1,name);
	}
	else {
	    setChecked(0,name);
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
			String downloadXMLURL = "";
			String downloadExcelURL = "";
			String downloadPriorExcelURL = "";
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
			<c:forEach items="${cdeCart}" var="cart">			
			<c:if test="${ not empty cart }">
				<% String sCartName = ((gov.nih.nci.ncicb.cadsr.objectCart.CDECart)pageContext.getAttribute("cart")).getCartName();
				   String sCartId = ((gov.nih.nci.ncicb.cadsr.objectCart.CDECart)pageContext.getAttribute("cart")).getCartId();
				
				%>
				<c:if test="${ not empty cart.dataElements }"> 
					<table cellpadding="0" cellspacing="0" width="80%" align="center">
						<tr>
						<!--  TODO: FIX DL URLS -->
							<td nowrap>
								<%
									downloadXMLURL = "javascript:fileDownloadWin('"+ contextPath+ "/jsp/cdebrowser/downloadXMLPage.jsp?src=cdeCart','xmlWin',500,200)";
									downloadExcelURL = "javascript:fileDownloadWin('"+ contextPath+ "/jsp/cdebrowser/downloadExcelPage.jsp?src=cdeCart','excelWin',500,200)";
									downloadPriorExcelURL = "javascript:fileDownloadWin('"+ contextPath+ "/jsp/cdebrowser/downloadExcelPage.jsp?src=cdeCartPrior','excelWin',500,200)";
			
								 %>							
							
								<b><a class="anchor" href="<%=downloadPriorExcelURL%>"
									title="3.2.0.1 Version">[Download Data Elements to Prior
										Excel]</a> </b> &nbsp;&nbsp;
								<b><a class="anchor" href="<%=downloadExcelURL%>"
									title="3.2.0.2 Includes new content: Value Meaning Description in column BV, Value Meaning Concept(s) in column BW, Value Domain Representation in columns BI-BS.">
										[Download Data Elements to Excel]</a> </b> &nbsp;&nbsp;
								<b><a class="anchor" href="<%=downloadXMLURL%>">[Download Data Elements
										as XML]</a> </b> &nbsp;&nbsp;
							</td>
						</tr>
						<tr>
							<td width="100%" nowrap>
								<img height="2" src="i/beigedot.gif" width="99%" align="top"
									border="0">
							</td>
						</tr>
					</table>
				</c:if>
				<table width="80%" align="center" cellpadding="1" cellspacing="1"
					border="0" class="OraBGAccentVeryDark">
					<tr class="OraTableColumnHeader">
						<c:if test="${ not empty cart.dataElements }">
							<logic:present name="nciUser">
								<th>
									Save
									<input type="checkbox" name="saveAllChk:<%=sCartId%>" value="yes"
										onClick="ToggleSaveAll(this, '<%=sCartId%>')" />
								</th>
								<th>
									Delete
									<input type="checkbox" name="deleteAllChk:<%=sCartId%>" value="yes"
										onClick="ToggleDeleteAll(this, '<%=sCartId%>')" />
								</th>
							</logic:present>
							<logic:notPresent name="nciUser">
								<th>
									<input type="checkbox" name="allChk:<%=sCartId%>" value="yes"
										onClick="ToggleAll(this,'<%=sCartId%>')" />
								</th>
							</logic:notPresent>
						</c:if>
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
					
					
					<c:if test="${ empty cart.dataElements }">
						<tr class="OraTabledata">
							<td class="OraFieldText" colspan="7"> <bean:write name="cart" property="cartName"/> is empty.	</td>
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
												src='<%=request.getContextPath() + "/i/retrieve.gif"%>' border="0"
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
					</c:if>		
					<!--Cart is now ArrayList<CDECart> -->	
					<c:if test="${ not empty cart.dataElements }">
						<c:forEach items="${cart.dataElements}" var="de">				

							<% String deId = ((gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem)pageContext.getAttribute("de")).getId();
							   String detailsURL = "javascript:details('&p_de_idseq="+deId +"')";
							%>
							<tr class="OraTabledata">
								<logic:present name="nciUser">
								<script>
									sItems = "selectedSaveItems";
									dItems = "selectedDeleteItems";
								</script>
								<td>
									<c:if test="${de.persistedInd == true}">
              							&nbsp;       
            						</c:if>
									<c:if test="${de.persistedInd != true}">
										<input type="checkbox" name="selectedSaveItems" value="<%=sCartId%>:<%=deId%>" />
									</c:if>
									</td>
									<td>
										<input type="checkbox" name="selectedDeleteItems" value="<%=sCartId%>:<%=deId%>" />
									</td>
								</logic:present>
								<logic:notPresent name="nciUser">
									<td>
										<input type="checkbox" name="selectedItems"
											value="<%=sCartId%>:<%=deId%>" />
									</td>
								</logic:notPresent>
								<td class="OraFieldText">
									<c:out value="${de.item.longName}" />
								</td>
								<td class="OraFieldText">
									<c:out value="${de.item.longCDEName}" />
								</td>
								<td class="OraFieldText">
									<c:out value="${de.item.contextName}" />
								</td>
								<td class="OraFieldText">
									<c:out value="${de.item.registrationStatus}" />
								</td>
								<td class="OraFieldText">
									<c:out value="${de.item.aslName}" />
								</td>
								<td class="OraFieldText">
									<c:out value="${de.item.publicId}" />
								</td>
								<td class="OraFieldText">
									<c:out value="${de.item.version}" />
								</td>
							</tr>
						</c:forEach>
						
						<br>
						<table width="40%" align="center" cellpadding="1" cellspacing="1"
							border="1">
							<tr>
								<logic:notPresent name="nciUser">
									<td>
										<a href="javascript:retrieveSavedItems()()"> <html:img
												src='<%=urlPrefix + "i/retrieve.gif"%>' border="0"
												alt="Retrieve Saved Data Elements" /> </a>
									</td>
									<td>
								</logic:notPresent>
								<logic:present name="nciUser">
									<td colspan="2">
								</logic:present>
									<a href="javascript:saveItems(sItems)" class="btn blue">Save</a>
								</td>
								<td>
									<a href="javascript:deleteItems(dItems)" class="btn blue">Delete</a>
								</td>				
							</tr>
							<tr>
								<logic:notPresent name="nciUser">
									<td colspan="2">
										<input type="text" name="newCartName"/>
									</td>
								</logic:notPresent>
								<logic:present name="nciUser">
									<td>
										<input type="text" name="newCartName"/>
									</td>
								</logic:present>
								<td>
									<a href="javascript:saveItemsNewCart(sItems)" class="btn blue">Save by Name</a>
								</td>
								<td>
									<html:link page="/jsp/cdeBrowse.jsp?PageId=DataElementsGroup" >
										<html:img src='<%=urlPrefix + "i/backButton.gif"%>' border="0"
											alt="Back to Data Element Search" />
									</html:link>
								</td>
							</tr>
						</table>
					</c:if>		
					</table>
			</c:if>
			</c:forEach>
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