<%@ page contentType="text/html;charset=windows-1252"%>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%
  CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
  String treeURL;
  String formbuilderURL;
    treeURL = "WebTreeLoader.jsp?treeClass=gov.nih.nci.ncicb.cadsr.cdebrowser.tree.CDEBrowserTree&skin=CDEBrowser1";
    formbuilderURL = "formbuilder/formSearch.jsp";
 
%>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<TITLE>
CDE Browser
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
