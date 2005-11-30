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
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.resource.Form"%>
<%@ page import="java.util.*"%>
 
 <HTML>
  <HEAD>
    <TITLE>Formbuilder: Module list</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/jsLib/checkbox.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript">
    
    
    


function submitModuleListEdit(methodName) {
   var selectedItems = '<%=FormConstants.SELECTED_ITEMS%>';
   var selectedElements = selectedItems;
   var selectedSize = getNumberOfSelectedItems(selectedElements);
  if (validateSelection(selectedElements,'Please select at least one Module to delete'))
   {
        document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value=methodName;
        document.forms[0].submit();
        return true;
 
    }
 }
 

 
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

  function CheckAll(checkSetName)
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
	    if (e.name == checkSetName) {
		Check(e);
	    }
	}

    }
  
  function ClearAll(checkSetName)
    {
	var fo = document.forms[0];
	var len = fo.elements.length;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
            if (e.name == checkSetName) {
		Clear(e);
	    }
	}
    }

  function validate(fo) {
	var len = fo.elements.length;
        var oneChecked = false;
	for (var i = 0; i < len; i++) {
	    var e = fo.elements[i];
            if (e.name == "<%= FormConstants.SELECTED_ITEMS %>") {
              if(e.checked == true) {
                 oneChecked=true;
                 i = len;
              }
            }            
        }    
        if(oneChecked == true) {
          return true;
        } else {
          alert("Please Select at least one Data Element");
          return false;
        }
  }
</SCRIPT>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = request.getContextPath();
     int dummyInstructionDisplayCount = 3;

%>

    <html:form action="/formbuilder/copyFromModuleList">
      <html:hidden value="" property="<%=NavigationConstants.METHOD_PARAM%>"/>
      <html:hidden property="<%=FormConstants.MODULE_INDEX%>"/> 
      
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Selected&nbsp;Module&nbsp;list"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>


    <%@ include file="showMessages.jsp" %>
    
     
        <%@ include file="/formbuilder/copyFromModuleList_inc.jsp"%>    

        <%@ include file="/formbuilder/moduleListModuleDetails_inc.jsp"%>
                                      

     <br>
        <%@ include file="/formbuilder/copyFromModuleList_inc.jsp"%>     
   </html:form>
    <%@ include file="/common/common_bottom_border.jsp"%>
  </BODY>
</HTML>            