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
<TITLE>Add Question</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK REL="STYLESHEET" TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css"/>
<SCRIPT LANGUAGE="JavaScript">
  function switchAll(e)
  {
	if (e.checked) {
	    CheckAll();
	}
	else {
	    ClearAll();
	}
  }
  function Check(e)
    {
	e.checked = true;
    }

  function Clear(e)
    {
	e.checked = false;
    }  

  function CheckAll()
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
	    if (e.name == "<%= FormConstants.SELECTED_ITEMS %>") {
		Check(e);
	    }
	}
	fo.toggleAll.checked = true;
    }
  
  function ClearAll()
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
            if (e.name == "<%= FormConstants.SELECTED_ITEMS %>") {
		Clear(e);
	    }
	}
	fo.toggleAll.checked = false;
    }
  
</SCRIPT>
</HEAD>
<BODY topmargin=0 bgcolor="#ffffff" topmargin="0">

<% 
  String urlPrefix = "";
%>
<%@ include file="../common/common_header_inc.jsp"%>
<jsp:include page="../common/tab_inc.jsp" flush="true">
  <jsp:param name="label" value="Add&nbsp;Question"/>
  <jsp:param name="urlPrefix" value=""/>
</jsp:include>

<html:form action='<%= "/addQuestion?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.ADD_QUESTION %>' >

<%@ include file="addQuestion_inc.jsp" %>

<html:hidden property="<%= FormConstants.QUESTION_INDEX %>"/>

<logic:present name="<%=CaDSRConstants.CDE_CART%>">
  <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
    <tr class="OraTableColumnHeader">
      <th scope="col"><input type="checkbox" name="toggleAll" title="<bean:message key="cadsr.formbuilder.selectAll"/>" onclick="switchAll(this)"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.preferredName"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.longName"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.comments"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.context"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.usedByContext"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.workflow"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.publicID"/></th>
      <th scope="col"><bean:message key="cadsr.formbuilder.question.version"/></th>
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
    <logic:iterate id="de" name="<%=CaDSRConstants.CDE_CART%>" type="gov.nih.nci.ncicb.cadsr.resource.CDECartItem" property="dataElements" indexId="itemId">
      <tr class="OraTabledata">
        <td class="OraFieldText">
          <html:checkbox property="<%= FormConstants.SELECTED_ITEMS %>" value="<%= itemId %>"/>
        </td>
        <td>
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
          <bean:write name="de" property="item.usingContexts"/>
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
    <tr class="OraTabledata">
      <td colspan=9 class="OraFieldText">
        <a href="javascript:CheckAll()">Check All</a>&nbsp;-&nbsp;<a href="javascript:ClearAll()">Clear All</a>
      </td>
    </tr>
    </table>
    <br>

      <%@ include file="addQuestion_inc.jsp" %>
    <table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >

      <tr >
        <td>
          <a href='<%= "cdeBrowse.jsp?src=gotoAddQuestion&amp;moduleIndex=" +  request.getParameter("moduleIndex") + "&amp;questionIndex=" + request.getParameter("questionIndex")+"&PageId=DataElementsGroup" %>'><html:img src='<%=urlPrefix+"i/add_more_data_elements.gif"%>' border="0" alt="Add more data elements"/></a>
        </td>
      </tr>
    </table>    
  </logic:notEmpty>
</logic:present>
</html:form>
<%@ include file="../common/common_bottom_border.jsp"%>
</body>
</html:html>