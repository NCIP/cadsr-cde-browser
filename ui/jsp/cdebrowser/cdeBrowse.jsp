<%@ page contentType="text/html;charset=windows-1252"%>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeConstants " %>
<%
  CDEBrowserParams params = CDEBrowserParams.getInstance("cdebrowser");
  String pageId = request.getParameter("PageId");
  String treeURL;
  String browserURL;
  String extraURLParams = "";
  String treeParams = "";

  String src = request.getParameter("src");
  if (src != null) {
    String modIndex = request.getParameter("moduleIndex");
    String quesIndex = request.getParameter("questionIndex");
    extraURLParams += "&src="+src+"&moduleIndex="+modIndex+"&questionIndex="+quesIndex;
    treeParams += treeParams + ";src:"+src + ";" + "questionIndex:" + quesIndex
                  + ";moduleIndex:"+modIndex;
  }

  treeURL = "WebTreeLoader.jsp?treeClass=gov.nih.nci.ncicb.cadsr.cdebrowser.tree.CDEBrowserTree"+
      "&treeParams="+TreeConstants.TREE_TYPE_URL_PARAM +":" + 
      TreeConstants.DE_SEARCH_TREE + ";" +
      TreeConstants.FUNCTION_NAME_URL_PARAM + ":" +
      TreeConstants.DE_SEARCH_FUNCTION + treeParams +
      "&skin=CDEBrowser1";
      
  if (pageId == null) {
   browserURL = "search?FirstTimer=0"+extraURLParams;
  }
  else {
    treeURL = treeURL + "&PageId="+pageId;
    browserURL = "search?PageId="+pageId+"&FirstTimer=0"+extraURLParams;
  }
%>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">

<TITLE>
CDE Browser
</TITLE>
</HEAD>
  <frameset cols="25%,*">
    <frameset rows="15%,*">
       <frame src="cdebrowserCommon_html/tree_hdr.html" frameborder="0" name="tree_header" scrolling = "no">
       <frame src="<%=treeURL%>" frameborder="0" name="tree">
    </frameset>
    <frame src="<%=browserURL%>" frameborder="0" name="body">
  </frameset>
</HTML>
