<%--L
  Copyright Oracle Inc, SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
L--%>

<%@ page errorPage="/jsp/common/systemError.jsp" %> 
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>
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
  
  //Preferences
  String brContextExcludeTestStr = null;
  CDEBrowserParams params = CDEBrowserParams.getInstance();
  //DataElementSearchBean searchBean = null;
  //try{
  
    //searchBean = (DataElementSearchBean)currInfoBean.getInfo("desb");
        
  //}
  //catch(Exception ex){}
  
  //Search Pref
  //boolean excludeTestContext = false;
  //boolean excludeTrainingContext = false;
  //if(searchBean==null)
  //{
    //excludeTestContext = new Boolean(params.getExcludeTestContext()).booleanValue();
    //System.out.println(">>> cdeBrowse.jsp: from params excludeTestContext is  "+excludeTestContext);   
    //excludeTrainingContext = new Boolean(params.getExcludeTrainingContext()).booleanValue();
    //System.out.println(">>> cdeBrowse.jsp: from params excludeTrainingContext is  "+excludeTrainingContext);
  //}
  //else
  //{
    //excludeTestContext = searchBean.isExcludeTestContext();
    //System.out.println(">>> cdeBrowse.jsp: from searchBean excludeTestContext is  "+excludeTestContext);
    //excludeTrainingContext = searchBean.isExcludeTrainingContext();
    //System.out.println(">>> cdeBrowse.jsp: from seaerchBean excludeTrainingContext is  "+excludeTrainingContext);
  //}
   //String contextToExclude = StringEscapeUtils.escapeJavaScript("");
 
    //if(excludeTestContext)
    //{
      //contextToExclude=" '"+CaDSRConstants.CONTEXT_TEST+"'";
    //}
    //if(excludeTrainingContext)
    //{
       //if(contextToExclude.equals(""))
       //{
         //contextToExclude=" '"+CaDSRConstants.CONTEXT_TRAINING+"'";
       //}
       //else
       //{
         //contextToExclude = contextToExclude+", '"+CaDSRConstants.CONTEXT_TRAINING+"' ";
       //}
    //}  
  //System.out.println(">>> cdeBrowse.jsp: contextToExclude : "+contextToExclude);
  //String brContextExcludeListParamStr = "";
  //if(excludeTestContext||excludeTrainingContext)
  //{
     //brContextExcludeListParamStr =  TreeConstants.BR_CONTEXT_EXCLUDE_LIST_STR+":" 
       //              + contextToExclude+";" ;
      //System.out.println(">>> cdeBrowse.jsp: brContextExcludeListParamStr is  "+brContextExcludeListParamStr);
  //}


  String pageId = StringEscapeUtils.escapeHtml(request.getParameter("PageId"));
  String treeURL;
  String browserURL;
  String extraURLParams = StringEscapeUtils.escapeHtml("");
  String treeParams = StringEscapeUtils.escapeHtml("");
  //get the source, module and question index
  //String modIndex = StringEscapeUtils.escapeHtml("");
  //String quesIndex = StringEscapeUtils.escapeHtml("");
  String src = StringEscapeUtils.escapeHtml(request.getParameter("src"));
  if (src == null || src.equals(""))
  {	    
	  Hashtable srcParams = TreeUtils.parseParameters(StringEscapeUtils.escapeHtml((String)request.getSession().getAttribute("paramsTree")));
	  if (srcParams.containsKey("src")) 
	  {
	    src = (String)srcParams.get("src");
	    //modIndex = StringEscapeUtils.escapeHtml((String)srcParams.get("moduleIndex"));
	    //quesIndex = StringEscapeUtils.escapeHtml((String)srcParams.get("questionIndex"));
	  }
  }
  else
  {
	//modIndex = StringEscapeUtils.escapeHtml(request.getParameter("moduleIndex"));
	//quesIndex = StringEscapeUtils.escapeHtml(request.getParameter("questionIndex"));
  }
  
  if (src != null&&!src.equals("")) {
    extraURLParams += "&"+StringEscapeUtils.escapeHtml("src="+src);//+"&moduleIndex="+modIndex+"&questionIndex="+quesIndex);
    treeParams += treeParams + ";"+StringEscapeUtils.escapeHtml("src:"+src); //+ ";" + "questionIndex:" + quesIndex + ";moduleIndex:"+modIndex);
  }

  treeURL = "/jsp/treeLoader.jsp?&treeParams="+TreeConstants.TREE_TYPE_URL_PARAM +":" +TreeConstants.DE_SEARCH_TREE + ";"+
      //brContextExcludeListParamStr +
      TreeConstants.FUNCTION_NAME_URL_PARAM + ":" +
      TreeConstants.DE_SEARCH_FUNCTION + treeParams +
      "&"+"treeName=deTree";


  if (performQuery != null ) {
    extraURLParams += "&"+StringEscapeUtils.escapeHtml("performQuery="+performQuery); 
  }
      
  if (pageId == null) {
   browserURL = "/search?"+StringEscapeUtils.escapeHtml("FirstTimer=0")+extraURLParams;
  }
  else {
    treeURL = treeURL + "&"+StringEscapeUtils.escapeHtml("PageId="+pageId);
    browserURL = "/search?"+StringEscapeUtils.escapeHtml("PageId="+pageId)+"&"+StringEscapeUtils.escapeHtml("FirstTimer=0"+extraURLParams);
  }

  if((cachedDeList!=null||showCached!=null) && (performQuery == null)) {
    pageContext.setAttribute("resultsPresent",new Boolean("true"));
    browserURL = "/jsp/cdebrowser/dataElementsSearch.jsp?"+StringEscapeUtils.escapeHtml("performQuery=cached")+"&"+StringEscapeUtils.escapeHtml("FirstTimer=0")+extraURLParams;
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
   <html:frame page="/jsp/common/topHeader1.jsp" frameborder="0" scrolling = "no" frameName="tree_header" title="Top header frame"/>
  <frameset cols="25%,*">
    <frameset rows="15%,*">
       <html:frame page="/jsp/common/tree_hdr.jsp" frameborder="0" scrolling = "no" frameName="tree_header" title="Tree header frame"/>       
       <html:frame page="<%=treeURL%>" frameborder="0"  frameName="tree" title="Tree loader frame"/>       
    </frameset>    	   	
      <html:frame page="<%=browserURL%>" frameborder="0" frameName="body" title="Tree body frame"/>      
   </frameset>   
 </frameset>
</HTML>
