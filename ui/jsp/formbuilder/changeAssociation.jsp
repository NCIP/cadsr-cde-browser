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
<TITLE>Change Associations</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK REL="STYLESHEET" TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css"/>
<SCRIPT LANGUAGE="JavaScript">
  function validate(fo) {
	var len = fo.elements.length;
        var oneChecked = false;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
            if (e.name == "selectedText") {
              if(e.checked == true) {
                 oneChecked=true;
                 i = len;
              }
            }            
        }    
        if(oneChecked == true) {
          return true;
        } else {
          alert("Please Select a Data Element");
          return false;
        }
  }

  function submitForm() {
     var f = document.forms[0];
     if(validate(f) == true)
       f.submit();
  }
  
function details(linkParms ){
  var urlString="search?dataElementDetails=9" + linkParms + "&PageId=DataElementsGroup"+"&queryDE=yes";
  newBrowserWin(urlString,'deDetails',800,600)
  
}

</SCRIPT>
</HEAD>
<BODY topmargin=0 bgcolor="#ffffff" topmargin="0">

<% 
  String urlPrefix = "";
%>
<%@ include file="../common/in_process_common_header_inc.jsp"%>
<jsp:include page="../common/tab_inc.jsp" flush="true">
  <jsp:param name="label" value="Change&nbsp;Association"/>
  <jsp:param name="urlPrefix" value=""/>
</jsp:include>

<html:form action='<%= "/changeAssociation?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.CHANGE_DE_ASSOCIATION %>' >

<html:hidden property="<%= FormConstants.QUESTION_INDEX %>"/>

<logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
  <%@ include file="changeAssociation_inc.jsp" %>
</logic:notEmpty>

<logic:present name="<%=CaDSRConstants.CDE_CART%>">
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
    <tr class="OraTableColumnHeader">
      <th scope="col"><bean:message key="cadsr.formbuilder.question.longName"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.comments"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.context"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.registration"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.workflow"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.publicID"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.version"/></th>
    </tr>
  <logic:empty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <tr class="OraTabledata">
        <td class="OraFieldText" colspan="7">
          CDE Cart is empty. 
        </td>
    </tr>
  </table>
  </logic:empty>
  <logic:notEmpty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
    <logic:iterate id="de" name="<%=CaDSRConstants.CDE_CART%>" type="gov.nih.nci.ncicb.cadsr.resource.CDECartItem" property="dataElements" indexId="itemId">
<%
      String deId = de.getId();
      String detailsURL = "javascript:details('&p_de_idseq="+deId +"')";
%>
      <tr class="OraTabledata">
        <td class="OraFieldText">
          <html:radio property="selectedText" value="<%= itemId + \",\" + de.getItem().getLongName() %>"/>
            <a href="<%= detailsURL %>">
          <bean:write name="de" property="item.longName"/>
        </a>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.longCDEName"/>
          <%--
          <logic:notEmpty name="de" property="item.longCDEName">
            <html:radio property="selectedText" value="<%= itemId + \",\" + ((gov.nih.nci.ncicb.cadsr.resource.DataElement)de.getItem()).getLongCDEName() %>"/>
              <bean:write name="de" property="item.longCDEName"/>
          </logic:notEmpty>
          --%>
        </td>
        <td class="OraFieldText">
          <bean:write name="de" property="item.contextName"/>
        </td>
        <td class="OraFieldText">
	  <bean:write name="de" property="item.registrationStatus"/>
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
    <% String rem = (String)request.getAttribute("removeButton");
      if(rem == null || !rem.equals("no")) {
      %>
      <tr>
        <td colspan="8">
          <html:radio property="selectedText" value="false"/><bean:message key="cadsr.formbuilder.question.noAssociation"/>
        </td>
      </tr>
      <% } %>
    </table>
    <br>
  <%@ include file="changeAssociation_inc.jsp" %>
  </logic:notEmpty>

    <table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
      
      <tr >
        <td>
          <a href='<%= "cdeBrowse.jsp?src=gotoChangeAssociation&amp;moduleIndex=" +  request.getParameter("moduleIndex") + "&amp;questionIndex=" + request.getParameter("questionIndex") %>'><html:img src='<%=urlPrefix+"i/add_more_data_elements.gif"%>' border="0" alt="Add more data elements"/></a>
          </td>
      <logic:empty name="<%=CaDSRConstants.CDE_CART%>" property = "dataElements">
        <td>
          <html:link action='<%= "/cancelAction?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.GET_MODULE_TO_EDIT %>'>
            <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
          </html:link>             
        </td>
      </logic:empty>
        </tr>
      </table>    
</logic:present>
</html:form>
<%@ include file="../common/common_bottom_border.jsp"%>
</body>
</html:html>