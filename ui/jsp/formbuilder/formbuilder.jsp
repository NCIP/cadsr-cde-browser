<%@ page contentType="text/html;charset=windows-1252"%>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@page import="gov.nih.nci.ncicb.cadsr.resource.Context"%>
<%@page import="java.util.HashMap " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeConstants " %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%
  String treeURL;
  String formbuilderURL;
    treeURL = 
      "WebTreeLoader.jsp?treeClass=gov.nih.nci.ncicb.cadsr.cdebrowser.tree.CDEBrowserTree"+
      "&treeParams="+TreeConstants.TREE_TYPE_URL_PARAM +":" + 
      TreeConstants.FORM_SEARCH_TREE + ";" +
      TreeConstants.FUNCTION_NAME_URL_PARAM + ":" +
      TreeConstants.FORM_SEARCH_FUNCTION +
      "&treeName=formTree" +
      "&skin=CDEBrowser1";
    formbuilderURL = "formbuilder/formSearch.jsp";
 
%>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<TITLE>
FormBuilder
</TITLE>
</HEAD>
  <jsp:useBean id="requestMap" scope="request" class="java.util.HashMap" />
  
  </jsp:useBean>
  <cde:checkAccess
  role="<%=CaDSRConstants.CDE_MANAGER%>"
  key="accessValue"
  contextName="<%=Context.CTEP%>"
  />
  
  <frameset cols="25%,*">
    <frameset rows="15%,*">
       <html:frame page="/cdebrowserCommon_html/tree_hdr.html"
              name="requestMap"
              frameborder="0"
              frameName="tree_header"
              scrolling = "no"/>
       <html:frame page='<%="/"+treeURL%>'
              name="requestMap"
              frameborder="0"
              frameName="tree"
              />              
    </frameset>
       <html:frame action="/setMessagesForFrameAction?method=setMessagesFormKeys"
              name="requestMap"
              frameborder="0"
              frameName="body"
              />      
  </frameset>
   
</HTML>
