
<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants"%>
<%@page import="gov.nih.nci.ncicb.cadsr.resource.* "%>
<%@page import="gov.nih.nci.ncicb.cadsr.html.* " %>
<%@page import="java.util.List "%>
<%@page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.cdebrowser.struts.common.BrowserNavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.cdebrowser.struts.common.BrowserFormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.cdebrowser.CDECompareList"%>
<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%
  DESearchQueryBuilder queryBuilder = (DESearchQueryBuilder) infoBean.getInfo(ProcessConstants.DE_SEARCH_QUERY_BUILDER);
  pageContext.getSession().setAttribute("showCached",new Boolean("true"));
  if (queryBuilder != null)
  pageContext.getSession().setAttribute(ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR, 
  queryBuilder.getSortColumnHeader());
  DataElementSearchBean desb = (DataElementSearchBean)infoBean.getInfo("desb");
  List deList = (List)infoBean.getInfo(ProcessConstants.ALL_DATA_ELEMENTS);
  HTMLPageScroller myScroller = (HTMLPageScroller)infoBean.getInfo(ProcessConstants.DE_SEARCH_PAGE_SCROLLER);

  HTMLPageScroller topScroller = (HTMLPageScroller)infoBean.getInfo(
                           ProcessConstants.DE_SEARCH_TOP_PAGE_SCROLLER);
  
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tibSearchDE");
  String pageId = infoBean.getPageId();
  String pageName = PageConstants.PAGEID;
  String pageUrl = "&"+pageName+"="+pageId;
  //String latestVer = desb.getSearchStr(6);
  String latestVer = desb.getLatVersionInd();
  String paramIdseq = (String)infoBean.getInfo("P_IDSEQ");
  //release 3.0, change URL for cde browser jsp
  String contextPath = request.getContextPath() + "/cdebrowser";
  
  if (paramIdseq == null) paramIdseq = "";
  String paramType = (String)infoBean.getInfo("P_PARAM_TYPE");
  if (paramType == null) paramType = "";
  String templateURL = "search?viewTemplate=9&templateIdseq="+paramIdseq+pageUrl;
  //String downloadXMLURL = "javascript:newDownloadWin('search?xmlDownload=9"+pageUrl+"','downloadWin',10,10)";
  String downloadXMLURL = "javascript:fileDownloadWin('" + contextPath + "/downloadXMLPage.jsp?src=deSearch','xmlWin',500,200)";
  //String downloadExcelURL = "javascript:newDownloadWin('search?excelDownload=9"+pageUrl+"','downloadWin',10,10)";
  String downloadExcelURL = "javascript:fileDownloadWin('" + contextPath + "/downloadExcelPage.jsp?src=deSearch','excelWin',500,200)";
  String valueDomainLOVUrl= "javascript:newWin('" + request.getContextPath() + "/search?valueDomainsLOV=9&idVar=jspValueDomain&nameVar=txtValueDomain"+pageUrl+"','vdLOV',700,600)";
  String decLOVUrl= "javascript:newWin('" + request.getContextPath() + "/search?dataElementConceptsLOV=9&idVar=jspDataElementConcept&nameVar=txtDataElementConcept"+pageUrl+"','decLOV',700,600)";
  String csLOVUrl= "javascript:newBrowserWin('" + request.getContextPath() + "/search?classificationsLOV=9&idVar=jspClassification&nameVar=txtClassSchemeItem"+pageUrl+"','csLOV',700,600)";

  
  String txtDataElementConcept = desb.getDECPrefName();
  String txtValueDomain = desb.getVDPrefName();
  String txtClassSchemeItem = desb.getCSIName();
  String pageContextInfo = "";
  if (!paramIdseq.equals("") && !paramType.equals("")){
    CDEBrowserPageContext pg = (CDEBrowserPageContext)infoBean.getInfo(ProcessConstants.PAGE_CONTEXT);
    pageContextInfo = pg.getPageContextDisplayText();
  }
  String firstDisplay = (String)infoBean.getInfo("NOT_FIRST_DISPLAY");
  if (firstDisplay ==null) firstDisplay = "";

  String queryFlag = request.getParameter("performQuery");
  if (queryFlag == null) queryFlag = "";


  String doneURL = "";

  String src = request.getParameter("src");
  if (src == null)
    src="";
  String modIndex = "";
  String quesIndex = "";
  String urlParams = "";
  String newSearchURL = request.getContextPath() + "/cdeBrowse.jsp?performQuery=newSearch&PageId=DataElementsGroup";
  
  if ((src != null) && (!"".equals(src))) {
    modIndex = request.getParameter("moduleIndex");
    quesIndex = request.getParameter("questionIndex");
    doneURL= request.getContextPath()+"/"+src+".do?method=displayCDECart&moduleIndex="+modIndex+"&questionIndex="+quesIndex;
    urlParams = "&src="+src+"&method=displayCDECart&moduleIndex="+modIndex+"&questionIndex="+quesIndex;
    newSearchURL += urlParams;
  }
  
  String cdeCompareSizeStr="0";
  
  CDECompareList compareList = (CDECompareList)(pageContext.getSession().getAttribute("CDE_COMPARE_LIST"));
  
//  if(compareList!=null)
//  {
//  	List list = compareList.getCdeList();
//        cdeCompareSizeStr = (new Integer(list.size())).toString();
//  }

  String submitFunction ="submitForm()";
  Object advancedSearch = (pageContext.getSession().getAttribute(BrowserFormConstants.BROWSER_SCREEN_TYPE_ADVANCED));
  if(advancedSearch==null)
    { 
  	submitFunction = "submitSimpleForm()";
    }
  
    
  
%>



<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<META HTTP-EQUIV="Expires" CONTENT="Thu, 01 Dec 1994 16:00:00 GMT">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
Data Elements Search - Data Elements
</TITLE>
<SCRIPT LANGUAGE="JavaScript1.1" SRC="<%=request.getContextPath()%>/jsLib/checkbox.js"></SCRIPT>

</HEAD>

<BODY  bgcolor="#ffffff" onLoad="turnOff()" topmargin="0" >
<SCRIPT LANGUAGE="JavaScript">
<!--
function redirect1(detailReqType, linkParms )
{
  var urlString="<%=request.getContextPath()%>/search?dataElementDetails=9" + linkParms + "<%= pageUrl %>"+"&queryDE=yes";
  newBrowserWin(urlString,'deDetails',800,600)
  
}
function goPage(pageNumber, pageInfo) {
  document.location.href ="<%=request.getContextPath()%>" + "/search?searchDataElements=9&"+pageInfo+"&deSearchPageNum="+pageNumber+ "<%= pageUrl %>"+"<%= urlParams %>";
    
}
function clearValueDomain() {
  document.forms[0].jspValueDomain.value = "";
  document.forms[0].txtValueDomain.value = "";
}
function clearDataElementConcept() {
  document.forms[0].jspDataElementConcept.value = "";
  document.forms[0].txtDataElementConcept.value = "";
}
function clearClassSchemeItem() {
  document.forms[0].jspClassification.value = "";
  document.forms[0].txtClassSchemeItem.value = "";
}
function submitForm() {
  if ((document.forms[0].jspAltName.value != "") &&
    (document.forms[0].altName.selectedIndex <=0))
    {
       alert("Please select Alternate Name Type(s) for Alternate Name search");
    }
  else {
     document.forms[0].submit();
  }
}

function submitSimpleForm() {
     if(document.forms[0].jspBasicSearchType.selectedIndex==1)
     {
        document.forms[0].jspCdeId.value=document.forms[0].jspSimpleKeyword.value;
        document.forms[0].jspKeyword.value="";
        
     }
     else
     {
       document.forms[0].jspKeyword.value=document.forms[0].jspSimpleKeyword.value;
       document.forms[0].jspCdeId.value="";
     }
     
     document.forms[0].submit();
  }


function clearSimpleForm() {
  document.forms[0].jspSimpleKeyword.value = "";
  document.forms[0].jspBasicSearchType.selectedIndex=0;
}

function clearForm() {
  var version = document.forms[0].jspLatestVersion
  clearValueDomain();
  clearDataElementConcept();
  clearClassSchemeItem();
  document.forms[0].jspKeyword.value = "";
  document.forms[0].jspAltName.value = "";
  document.forms[0].jspCdeId.value = "";
  document.forms[0].jspValidValue.value = "";

  document.forms[0].jspObjectClass.value = "";
  document.forms[0].jspProperty.value = "";
  document.forms[0].jspConceptCode.value = "";
  document.forms[0].jspConceptName.value = "";
  
  unselect(document.forms[0].jspSearchIn);
  document.forms[0].jspSearchIn.options[1].selected  = true;

  unselect(document.forms[0].jspStatus);
  document.forms[0].jspStatus.options[0].selected  = true;
  
  unselect(document.forms[0].regStatus);
  document.forms[0].regStatus.options[0].selected  = true;
  
  unselect(document.forms[0].contextUse);
  document.forms[0].contextUse.options[2].selected  = true;
  
  if(document.forms[0].altName.options.selected)
  {
    unselect(document.forms[0].altName);
  }
  else
  {
    unselect(document.forms[0].altName);
  }
  
  document.forms[0].jspLatestVersion[0].checked=true

}

function unselect(val)
{
   for (i=0; i < val.options.length ; i++)
    {
	val.options[i].selected  = false;
    }
}

function turnOn() {
  document.body.style.cursor = "wait";
}

function turnOff() {
  document.body.style.cursor = "default";
  if ("<%= paramType %>" != "") {
    var treeFrame = findFrameByName('tree');
    treeFrame.document.body.style.cursor = "default";
  }
}

turnOn();

function findFrameByName(strName) {
   return findFrame(top, strName);
}

function findFrame(doc, strName) {
 if (doc.frames.length == 0) return;

 for (var i = 0; i != doc.frames.length; i++)
   if (doc.frames[i].name == strName)
     return doc.frames[i];
   else {
     var frm = findFrame(doc.frames[i].window, strName);

     if ( frm  )
       return frm;
   }

 return top;
}


function ToggleAll(e){
	if (e.checked) {
	    setChecked(1,'selectDE');
	}
	else {
	    setChecked(0,'selectDE');
	}
}

function updateCart() {
  if (validateSelection('selectDE','Please select at least one data element to add to the CDE Cart')) {
    document.forms[0].performQuery.value = "addToCart";
    document.forms[0].submit();
    return true;
  }
}

function updateCompareList() {
  if (validateSelection('selectDE','Please select at least one data element to add to the Compare Cart')) {
    document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value="<%=BrowserNavigationConstants.ADD_TO_CDE_COMPARE_LIST%>";    
    document.forms[0].action='<%=request.getContextPath()%>/cdebrowser/addToCompareListAction.do';    
    document.forms[0].submit();
    return true;
  }
}



function compareCDEs(size) {

    document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value="<%=BrowserNavigationConstants.COMPARE_CDE%>";    
    document.forms[0].action='<%=request.getContextPath()%>/cdebrowser/compareCDEAction.do';      
    document.forms[0].target="_self";
    var cdeCompareSizeStr = size;
    var selected = getNumberOfSelectedItems("selectDE");
    if(cdeCompareSizeStr>1)
    {
      document.forms[0].target="_parent"
    }
    if(selected>1)
    {
      document.forms[0].target="_parent"
    }
    if((cdeCompareSizeStr+selected)>1)
    {
      document.forms[0].target="_parent"
    } 

    document.forms[0].submit();
    
}

function changeScreenType(type) {
  document.forms[0].<%=BrowserFormConstants.BROWSER_SEARCH_SCREEN_TYPE%>.value = type;
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value="<%=BrowserNavigationConstants.CHANGE_SCREEN_TYPE%>";
  document.forms[0].action='<%=request.getContextPath()%>/cdebrowser/screenTypeAction.do';        
  document.forms[0].submit();
}

function done() {
  top.location.href = "<%=doneURL%>";
}

function newSearch(){
  top.location.href= "<%=newSearchURL%>";
}


function gotoCDESearchPrefs() {
  document.forms[0].<%=NavigationConstants.METHOD_PARAM%>.value="<%=BrowserNavigationConstants.GOTO_CDE_SEARCH_PREF%>";
  document.forms[0].action='<%=request.getContextPath()%>/cdebrowser/cdeSearchPrefAction.do';        
  document.forms[0].target="_parent";
  document.forms[0].submit();
}
//-->
</SCRIPT>




<form action="<%= infoBean.getStringInfo("controller") %>" METHOD="POST" NAME="searchForm" onkeypress="if(window.event.keyCode==13){<%=submitFunction%>};">
<INPUT TYPE="HIDDEN" NAME="<%=NavigationConstants.METHOD_PARAM%>" > 
<INPUT TYPE="HIDDEN" NAME="NOT_FIRST_DISPLAY" VALUE="1">
<INPUT TYPE="HIDDEN" NAME="SEARCH" VALUE="1">
<INPUT TYPE="HIDDEN" NAME="SEARCH" VALUE="1">
<INPUT TYPE="HIDDEN" NAME="performQuery" VALUE="yes">
<INPUT TYPE="HIDDEN" NAME="src" VALUE="<%=src%>">
<INPUT TYPE="HIDDEN" NAME="moduleIndex" VALUE="<%=modIndex%>">
<INPUT TYPE="HIDDEN" NAME="questionIndex" VALUE="<%=quesIndex%>">
<input type="HIDDEN" name="<%= PageConstants.PAGEID %>" value="<%= infoBean.getPageId()%>"/>
<!--screenType-->
<INPUT TYPE="HIDDEN" NAME="<%=BrowserFormConstants.BROWSER_SEARCH_SCREEN_TYPE%>" >
 
<%@ include  file="cdebrowserCommon_html/tab_include_search.html" %>


<%@ include file="../formbuilder/showMessages.jsp" %>   
<table>
 <%
      if (request.getAttribute(ProcessConstants.CDE_CART_ADD_SUCCESS) != null) {
    %>
    
        <tr align="center" >
          <td  align="left" class="MessageText" >        
            <b><%=request.getAttribute(ProcessConstants.CDE_CART_ADD_SUCCESS)%></b><br>
          </td>
        </tr>
    <%
      }
      if (!"".equals(src)) {
    %>
    
        <tr align="center" >
          <td  align="left" class="MessageText" >        
            <b>To get back to the cart please click the done button.</b><br>
          </td>
        </tr>
    <% 
      }
    %>
</table>

<logic:present name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>">
    <bean:size id="listSize" name="<%=BrowserFormConstants.CDE_COMPARE_LIST%>" property="cdeList"/>
<%
    cdeCompareSizeStr = listSize.toString();
%>
</logic:present>

<logic:present name="<%=BrowserFormConstants.BROWSER_SCREEN_TYPE_ADVANCED%>">
      <%@ include file="advancedSearch_inc.jsp"%>
</logic:present>

<logic:notPresent name="<%=BrowserFormConstants.BROWSER_SCREEN_TYPE_ADVANCED%>">
      <%@ include file="simpleSearch_inc.jsp"%>
</logic:notPresent>
<br>
<A NAME="results"></A>


<%
  if (!queryFlag.equals("")) {
    if (deList!=null&&deList.size()!=0) {
%>
<table width="100%"   border="0">
   <tr>
           <td  nowrap>&nbsp;</td>
   </tr>
  <tr valign="bottom">
     <td  valign="bottom" class="OraHeaderSubSub" width="100%" align="left" nowrap>Search Results</td>
  </tr>
  <tr valign="top">
    <td valign="top" width="100%" nowrap >
      <html:img height="1" page="/i/beigedot.gif" width="99%" align="top" border="0" />
    </td>
  </tr>
</table>

<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">

  <tr valign="top">
    <td nowrap>
      <b><a href="<%=downloadExcelURL%>" >[Download Data Elements to Excel]</a></b> &nbsp;&nbsp;
      <b><a href="<%=downloadXMLURL%>" >[Download Data Elements as XML]</a></b> &nbsp;&nbsp;
<%
      if ((paramType!=null)&&(paramType.equals("CRF")||paramType.equals("TEMPLATE"))){
%>
      <b><a href="<%=templateURL%>" target="_blank">[Download Template]</a></b>&nbsp;&nbsp;
      
<%
      }
%>
    </td>
  </tr>
   <tr>
           <td  colspan=2 nowrap>&nbsp;</td>
   </tr>
</table>

<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0">
    <tr>
      <td align="left" class="OraTableColumnHeaderNoBG" width="10%" nowrap>Sort order :</td>
      <td align="left" class="CDEBrowserPageContext">
       <cde:sorableColumnHeaderBreadcrumb
               sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
               separator=">>" 
               showDefault="Y"
               labelMapping="doc_text,Document Text,long_name,Long Name,name,Owned By,de_usedby,Used By Context,registration_status,Registration Status,display_order,Registration Status,asl_name,Workflow Status,de_cdeid,Public ID,de_version,Version,wkflow_order,Workflow Status"
               defaultText=" (Default) "
               ascendingText=" [Ascending]"
               descendingText=" [Descending]"
        />           
      </td>
      
    <logic:notPresent name="<%=SortableColumnHeader.DEFAULT_SORT_ORDER%>">
        <td align="right" width="20%" nowrap>
           <a href="<%="javascript:"+submitFunction%>" >Reset to default sort order</a>
        </td>
     </logic:notPresent>     

    </tr>
</table>

<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0">
    <tr>
      <td align="left" width="20%" ><a href="javascript:updateCart()"><html:img page="/i/AddToCDECart.gif" border="0" /></a></td>
      <td align="left" width="20%" ><a href="javascript:updateCompareList()"><html:img page="/i/addToCDECompareList.gif" border="0" /></a></td>
      <td align="left" width="20%" ><a href="javascript:compareCDEs(<%=cdeCompareSizeStr%>)"><html:img page="/i/compareCDEs.gif" border="0" /></a></td>
      <td align="right"><%=topScroller.getScrollerHTML()%></td>
    </tr>
</table>

        
<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader"><input type="checkbox" name="deList" value="yes" onClick="ToggleAll(this)"/></th>
  	 <th class="OraTableColumnHeader" nowrap>
 		        <cde:sortableColumnHeader
              sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
 	       	  actionUrl='<%="/search?performQuery=sortResults" + pageUrl + urlParams %>'
     	   	  columnHeader="Long Name" 
              orderParamId="sortOrder" 
     	   	  sortFieldId="sortField"
       	     sortFieldValue = "long_name"
            />   
    </th>
    <th class="OraTableColumnHeader">
 	      <cde:sortableColumnHeader
              sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
 	       	  actionUrl='<%="/search?performQuery=sortResults" + pageUrl + urlParams %>'
     	   	  columnHeader="Document Text" 
              orderParamId="sortOrder" 
     	   	  sortFieldId="sortField"
       	     sortFieldValue = "doc_text"
            />       
    
    </th>
    <th class="OraTableColumnHeader">
     	      <cde:sortableColumnHeader
                  sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
     	       	  actionUrl='<%="/search?performQuery=sortResults" + pageUrl + urlParams %>'
         	   	  columnHeader="Owned By" 
                  orderParamId="sortOrder" 
         	   	  sortFieldId="sortField"
           	     sortFieldValue = "name"
                />       
    </th>
    <th class="OraTableColumnHeader">
         	      <cde:sortableColumnHeader
                      sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
         	       	  actionUrl='<%="/search?performQuery=sortResults" + pageUrl+ urlParams%>'
             	   	  columnHeader="Used By Context" 
                      orderParamId="sortOrder" 
             	   	  sortFieldId="sortField"
               	     sortFieldValue = "de_usedby"
                    />       

    
    </th>
    <th class="OraTableColumnHeader" nowrap>
 	      <cde:sortableColumnHeader
              sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
 	       	  actionUrl='<%="/search?performQuery=sortResults" + pageUrl + urlParams%>'
     	   	  columnHeader="Registration Status" 
              orderParamId="sortOrder" 
     	   	  sortFieldId="sortField"
       	     sortFieldValue = "registration_status"
            />   
    </th>
    <th class="OraTableColumnHeader" nowrap>
 	      <cde:sortableColumnHeader
              sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
 	       	  actionUrl='<%="/search?performQuery=sortResults" + pageUrl + urlParams %>'
     	   	  columnHeader="Workflow Status" 
              orderParamId="sortOrder" 
     	   	  sortFieldId="sortField"
       	     sortFieldValue = "asl_name"
            />   
    </th>
    <th class="OraTableColumnHeader">
  	      <cde:sortableColumnHeader
              sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
 	       	  actionUrl='<%="/search?performQuery=sortResults" + pageUrl + urlParams %>'
     	   	  columnHeader="Public ID" 
              orderParamId="sortOrder" 
     	   	  sortFieldId="sortField"
       	     sortFieldValue = "de_cdeid"
            />   
   
    </th>
    <th class="OraTableColumnHeader">
  	      <cde:sortableColumnHeader
              sortableColumnHeaderBeanId="<%=ProcessConstants.CDE_SEARCH_RESULT_COMPARATOR%>" 
 	       	  actionUrl='<%="/search?performQuery=sortResults" + pageUrl + urlParams %>'
     	   	  columnHeader="Version" 
              orderParamId="sortOrder" 
     	   	  sortFieldId="sortField"
       	     sortFieldValue = "de_version"
            />   
    
    </th>
  </tr>
<%
      String pagesDropDown = myScroller.getScrollerHTML();
      for (int i=0; i <deList.size(); i++) {
        DataElement de = (DataElement)deList.get(i);
%>
  <tr class="OraTabledata">
    <td class="OraTableCellSelect"><input type="checkbox" name="selectDE" value="<%=de.getDeIdseq()%>"/></td>
    <td class="OraFieldText"><a href="javascript:redirect1('dataElementDetails','&p_de_idseq=<%=de.getDeIdseq()%>')"><%=de.getLongName()%></a></td>
    <td class="OraFieldText"><%=de.getLongCDEName()%> </td>
    <td class="OraFieldText"><%=de.getContextName()%> </td>
    <td class="OraFieldText"><%=de.getUsingContexts()%> </td>
    <td class="OraFieldText"><%=de.getRegistrationStatus()%> </td>
    <td class="OraFieldText"><%=de.getAslName()%> </td>
    <td class="OraFieldText"><%=de.getCDEId()%> </td>
    <td class="OraFieldText"><%=de.getVersion()%> </td>
    
  </tr>
<%
      }
%>
  </table>
  <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0">
    <tr><td align="right"><%=pagesDropDown%></td></tr>
  </table>
<%
    }
    else if(deList!=null&&deList.size()==0) {
    pageContext.getSession().setAttribute("showCached",new Boolean("true"));
%>

<table width="100%"   border="0">
   <tr>
           <td  nowrap>&nbsp;</td>
   </tr>
  <tr valign="bottom">
     <td  valign="bottom" class="OraHeaderSubSub" width="100%" align="left" nowrap>Search Results</td>
  </tr>
  <tr valign="top">
    <td valign="top" width="100%" nowrap >
      <html:img height="1" page="/i/beigedot.gif" width="99%" align="top" border="0" />
    </td>
  </tr>
</table>


<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Long Name</th>
    <th class="OraTableColumnHeader">Document Text</th>
    <th class="OraTableColumnHeader">Owned By</th>
    <th class="OraTableColumnHeader">Used By Context</th>
    <th class="OraTableColumnHeader">Registration</th>
    <th class="OraTableColumnHeader">Workflow Status</th>
    <th class="OraTableColumnHeader">Public ID</th>
    <th class="OraTableColumnHeader">Version</th>
  </tr>
  <tr class="OraTabledata">
         <td colspan="8">No data elements matching the search criteria found.</td>
  </tr>
  </table>
<%
    }
  }
%>
</FORM>


<%@ include file="../common/common_bottom_border.jsp"%>

</BODY>
</HTML>