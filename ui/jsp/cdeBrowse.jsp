<%@ page errorPage="/common/systemError.jsp" %> 
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@page contentType="text/html;charset=windows-1252"%>
<%@page import="java.util.*"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.contexttree.TreeConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.ProcessConstants"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@page import="gov.nih.nci.ncicb.cadsr.common.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.resource.Context"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@page import="java.util.List" %>

<cde:checkAccess
  role="<%=CaDSRConstants.CDE_MANAGER%>"
  key="accessValue"
  contextName="<%=Context.CTEP%>"
  />

<jsp:useBean id="currInfoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="currInfoBean" property="session" value="<%=session %>"/>

<%

  //Publish Change Order
  String ctepUser = StringEscapeUtils.escapeJavaScript((String)pageContext.getAttribute("accessValue"));

  String performQuery = StringEscapeUtils.escapeJavaScript(request.getParameter("performQuery"));
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
  
  //Preferences
  String brContextExcludeTestStr = null;
  CDEBrowserParams params = CDEBrowserParams.getInstance();
  DataElementSearchBean searchBean = null;
  try{
  
    searchBean = (DataElementSearchBean)currInfoBean.getInfo("desb");
  }
  catch(Exception ex){}
  
  //Search Pref
  boolean excludeTestContext = false;
  boolean excludeTrainingContext = false;
  if(searchBean==null)
  {
    excludeTestContext = new Boolean(params.getExcludeTestContext()).booleanValue();
    excludeTrainingContext = new Boolean(params.getExcludeTrainingContext()).booleanValue();
  }
  else
  {
    excludeTestContext = searchBean.isExcludeTestContext();
    excludeTrainingContext = searchBean.isExcludeTrainingContext();
  }
   String contextToExclude = StringEscapeUtils.escapeJavaScript("");
 
    if(excludeTestContext)
    {
      contextToExclude=" '"+CaDSRConstants.CONTEXT_TEST+"'";
    }
    if(excludeTrainingContext)
    {
       if(contextToExclude.equals(""))
       {
         contextToExclude=" '"+CaDSRConstants.CONTEXT_TRAINING+"'";
       }
       else
       {
         contextToExclude = contextToExclude+", '"+CaDSRConstants.CONTEXT_TRAINING+"' ";
       }
    }  
  
  String brContextExcludeListParamStr = "";
  if(excludeTestContext||excludeTrainingContext)
  {
     brContextExcludeListParamStr =  TreeConstants.BR_CONTEXT_EXCLUDE_LIST_STR+":" 
                     + contextToExclude+";" ;
  }


  String pageId = StringEscapeUtils.escapeJavaScript(request.getParameter("PageId"));
  String treeURL;
  String browserURL;
  String extraURLParams = StringEscapeUtils.escapeJavaScript("");
  String treeParams = StringEscapeUtils.escapeJavaScript("");
  //get the source, module and question index
  String modIndex = StringEscapeUtils.escapeJavaScript("");
  String quesIndex = StringEscapeUtils.escapeJavaScript("");
  String src = StringEscapeUtils.escapeJavaScript(request.getParameter("src"));
  if (src == null || src.equals(""))
  {	    
	  Hashtable srcParams = TreeUtils.parseParameters(StringEscapeUtils.escapeJavaScript((String)request.getSession().getAttribute("paramsTree")));
	  if (srcParams.containsKey("src")) 
	  {
	    src = (String)srcParams.get("src");
	    modIndex = StringEscapeUtils.escapeJavaScript((String)srcParams.get("moduleIndex"));
	    quesIndex = StringEscapeUtils.escapeJavaScript((String)srcParams.get("questionIndex"));
	  }
  }
  else
  {
	modIndex = StringEscapeUtils.escapeJavaScript(request.getParameter("moduleIndex"));
	quesIndex = StringEscapeUtils.escapeJavaScript(request.getParameter("questionIndex"));
  }
  
  if (src != null&&!src.equals("")) {
    extraURLParams += StringEscapeUtils.escapeJavaScript("&src="+src+"&moduleIndex="+modIndex+"&questionIndex="+quesIndex);
    treeParams += StringEscapeUtils.escapeJavaScript(treeParams + ";src:"+src + ";" + "questionIndex:" + quesIndex
                  + ";moduleIndex:"+modIndex);
  }

  treeURL = "/treeLoader.jsp?&treeParams="+TreeConstants.TREE_TYPE_URL_PARAM +":" +TreeConstants.DE_SEARCH_TREE + ";"+
      brContextExcludeListParamStr +
      TreeConstants.FUNCTION_NAME_URL_PARAM + ":" +
      TreeConstants.DE_SEARCH_FUNCTION + treeParams +
      "&treeName=deTree";


  if (performQuery != null ) {
    extraURLParams += StringEscapeUtils.escapeJavaScript("&performQuery="+performQuery); 
  }
      
  if (pageId == null) {
   browserURL = "/search?"+StringEscapeUtils.escapeJavaScript("FirstTimer=0")+extraURLParams;
  }
  else {
    treeURL = treeURL + StringEscapeUtils.escapeJavaScript("&PageId="+pageId);
    browserURL = "/search?"+StringEscapeUtils.escapeJavaScript("PageId="+pageId+"&FirstTimer=0"+extraURLParams);
  }

  if((cachedDeList!=null||showCached!=null) && (performQuery == null)) {
    pageContext.setAttribute("resultsPresent",new Boolean("true"));
    browserURL = "/cdebrowser/dataElementsSearch.jsp?"+StringEscapeUtils.escapeJavaScript("performQuery=cached"+"&FirstTimer=0"+extraURLParams);
  }
%>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252">

<TITLE>
CDE Browser
</TITLE>
</HEAD>


<frameset rows="7%,*">
   <html:frame page="/common/topHeader1.jsp" frameborder="0" scrolling = "no" frameName="tree_header"/>
  <frameset cols="25%,*">
    <frameset rows="15%,*">
       <html:frame page="/common/tree_hdr.html" frameborder="0" scrolling = "no" frameName="tree_header"/>
       <html:frame page="<%=treeURL%>" frameborder="0"  frameName="tree"/>
    </frameset>    
      <html:frame page="<%=browserURL%>" frameborder="0" frameName="body"/>
   </frameset>   
 </frameset>
</HTML>
