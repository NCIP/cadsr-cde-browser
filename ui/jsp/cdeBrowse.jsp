<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeConstants " %>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants"%>
<%@page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>

<!--Publish ChangeOrder_-->
<%@page import="gov.nih.nci.ncicb.cadsr.resource.Context"%>

<%@ page import="java.util.List" %>

<cde:checkAccess
  role="<%=CaDSRConstants.CDE_MANAGER%>"
  key="accessValue"
  contextName="<%=Context.CTEP%>"
  />

<jsp:useBean id="currInfoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="currInfoBean" property="session" value="<%=session %>"/>

<%

  //Publish Change Order
  String ctepUser = (String)pageContext.getAttribute("accessValue");
  
  String performQuery = request.getParameter("performQuery");
  SessionUtils.setPreviousSessionValues(request);
  List cachedDeList = null;
  Boolean showCached = null;
  try{
  	cachedDeList = (List)currInfoBean.getInfo(ProcessConstants.ALL_DATA_ELEMENTS);
  	showCached = (Boolean)session.getAttribute("showCached");
  	if(showCached!=null)
  	    if(!showCached.booleanValue())
  	    {
  	      cachedDeList=null;
  	      showCached=null;
  	    }
  	
  }
  catch(Exception ex){}
    
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

  treeURL = "/common/WebTreeLoader.jsp?treeClass=gov.nih.nci.ncicb.cadsr.cdebrowser.tree.CDEBrowserTree"+
      "&treeParams="+TreeConstants.TREE_TYPE_URL_PARAM +":" + 
      TreeConstants.DE_SEARCH_TREE + ";" +
      TreeConstants.CTEP_USER_FLAG + ":" +
      ctepUser +  ";"+            
      TreeConstants.FUNCTION_NAME_URL_PARAM + ":" +
      TreeConstants.DE_SEARCH_FUNCTION + treeParams +
      "&skin=CDEBrowser1";

  if (performQuery != null ) {
    extraURLParams += "&performQuery="+performQuery; 
  }
      
  if (pageId == null) {
   browserURL = "/search?FirstTimer=0"+extraURLParams;
  }
  else {
    treeURL = treeURL + "&PageId="+pageId;
    browserURL = "/search?PageId="+pageId+"&FirstTimer=0"+extraURLParams;
  }

  if((cachedDeList!=null||showCached!=null) && (performQuery == null)) {
    pageContext.setAttribute("resultsPresent",new Boolean("true"));
    browserURL = "/cdebrowser/dataElementsSearch.jsp?performQuery=cached"+"&FirstTimer=0"+extraURLParams;
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
       <html:frame page="/common/tree_hdr.html" frameborder="0" scrolling = "no" frameName="tree_header"/>
       <html:frame page="<%=treeURL%>" frameborder="0"  frameName="tree"/>
    </frameset>    
      <html:frame page="<%=browserURL%>" frameborder="0" frameName="body"/>
   </frameset>
</HTML>
