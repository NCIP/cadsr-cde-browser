<%@ page contentType="text/html;charset=windows-1252"%>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeConstants " %>
<%
  String treeURL;
  String formbuilderURL;
    treeURL = 
      "WebTreeLoader.jsp?treeClass=gov.nih.nci.ncicb.cadsr.cdebrowser.tree.CDEBrowserTree"+
      "&treeParams="+TreeConstants.TREE_TYPE_URL_PARAM +":" + 
      TreeConstants.FORM_SEARCH_TREE + ";" +
      TreeConstants.FUNCTION_NAME_URL_PARAM + ":" +
      TreeConstants.FORM_SEARCH_FUNCTION +
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
  <frameset cols="25%,*">
    <frameset rows="15%,*">
       <frame src="cdebrowserCommon_html/tree_hdr.html" frameborder="0" name="tree_header" scrolling = "no">
       <frame src="<%=treeURL%>" frameborder="0" name="tree">
    </frameset>
    <frame src="<%=formbuilderURL%>" frameborder="0" name="body">
  </frameset>
</HTML>
