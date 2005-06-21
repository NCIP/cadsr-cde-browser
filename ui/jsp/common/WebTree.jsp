<%@ page import="javax.swing.tree.*" %>
<%@ page import="java.util.*" %>
<%@ page import="gov.nih.nci.ncicb.webtree.*" %>
<%@ page import="java.io.*"%>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeConstants " %>
<%@page import="gov.nih.nci.ncicb.cadsr.util.TreeUtils " %>
<%@page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>

<%
  	// get parameters
    String treeClass      = (String) request.getParameter("treeClass");
    String tName = (String) request.getParameter("treeName");
    String treeParams     = (String) request.getParameter("treeParams");
  	String targetId       = (String) request.getParameter("targetId");
   	String treeAction     = (String) request.getParameter("treeAction");
    String skin           = (String) request.getParameter("skin");
    String treeDirective  = (String) request.getParameter("treeDirective");
    
    // cross browser parameter null checks
    if (treeAction != null && treeAction.equals("null")) treeAction = null;
    if (skin != null && skin.equals("null")) skin = null;
    if (skin == null) skin = "default";
    Hashtable params = null;
    String callerParams = "";
    try {
      params = TreeUtils.parseParameters(treeParams);
      String treeType = (String)params.get(TreeConstants.TREE_TYPE_URL_PARAM);
      
      if (treeType.equals(TreeConstants.DE_SEARCH_TREE)) {
        String src = null;
        if (params.containsKey("src")) {
          src = (String)params.get("src");
          String modIndex = (String)params.get("moduleIndex");
          String quesIndex = (String)params.get("questionIndex");
          callerParams += "&src="+src+"&moduleIndex="+modIndex+"&questionIndex="+quesIndex;
        }
      }
    } 
    catch (Exception ex) {
      System.out.println("Error: "+ex.getMessage());;
    } 
    
    
%>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="skins/<%=skin%>/TreeBrowser.css"/>
  <script language="JavaScript1.2" src="skins/<%=skin%>/JavaScript.js"></script>
  <script language="JavaScript1.2">
  <!--
  function performAction(urlParams){
    var frm = findFrameByName('body');
    document.body.style.cursor = "wait";
    frm.document.body.style.cursor = "wait";
    frm.document.location = "<%=request.getContextPath()%>" + "/search?" + urlParams + "<%=callerParams%>";
  }
  //-->
  </script>
</head>
<body>

<%
  try 
  {   
    BDWebTree webTree = null;    
    HttpSession userSession = request.getSession(false);     

    String treeName = "webTree";

    if((tName != null) && (tName.equals("formTree"))) 
      treeName = "formTree";

    // get current web tree from session 
    if (userSession != null) {
      webTree = (BDWebTree) userSession.getAttribute(treeName); 
      
  	//Change Order Publish
  	String refreshIndicator = (String)session.getAttribute(CaDSRConstants.TREE_REFRESH_INDICATOR);
    session.removeAttribute(CaDSRConstants.TREE_REFRESH_INDICATOR);
  	if(refreshIndicator!=null&&refreshIndicator.equals(CaDSRConstants.YES))
  		 treeAction="refresh";      
}

    //  if no treeAction specified, build a new tree 
    //  only if it doesn't exist

    if (treeAction == null)
    {
         userSession = request.getSession(true);
        
        // construct new web tree
        if(webTree == null)
          webTree = new BDWebTree(treeClass, treeParams);
    } else if(treeAction.equals("refresh")) {
        webTree = new BDWebTree(treeClass, treeParams);
        treeAction = null;
    }

    if (webTree != null)
    {
      // ** build/update display tree **

      DefaultMutableTreeNode displayTreeRoot = webTree.getDisplayTree(treeAction, targetId, userSession, treeDirective);

      // set updated tree in session   
      userSession.setAttribute(treeName, webTree);      
%>   
<table  border="0">
  <tr>
    <%
      //      String treeURL = "WebTree.jsp?treeName=" + 
      //      request.getParameter("treeName") +  
      //      "&treeAction=refresh" + 
      //      "&treeClass=" + treeClass + 
      //      "&treeParams=" + treeParams + 
      //      "&skin=" + skin + "&treeDirective=" + 
      //      treeDirective;

      String treeURL = "WebTreeLoader.jsp?treeClass=gov.nih.nci.ncicb.cadsr.cdebrowser.tree.CDEBrowserTree"+
            "&treeName=" + treeName +
            "&treeAction=refresh" + 
            "&treeParams="+ treeParams + 
            "&skin=CDEBrowser1";
      %>

      <td ><a href="<%= treeURL %>">Refresh tree</a></td>
  </tr>
<%    // render display tree

      Enumeration displayTree = displayTreeRoot.preorderEnumeration();

      while (displayTree.hasMoreElements())  
      {
        DefaultMutableTreeNode displayNode = (DefaultMutableTreeNode) displayTree.nextElement();
        WebNode displayWebNode = (WebNode) displayNode.getUserObject();
        TreeUtils.setBreadCrumbsInfo(displayNode,displayWebNode);
        if (displayWebNode != null)
        {
          String myName = displayWebNode.getName();
          String myId   = displayWebNode.getId();
        
          // get info used for mouse-over hover pop-up info
          String webNodeInfo = displayWebNode.getInfo();

          // check for highligh match to show background color highlight
          if (treeAction != null && treeAction.equals("highlight") && myId.equals(targetId))
          { %>          
          <tr  class="highlightNode">
<%        }
          else
          {     %>
         <tr >
<%        }     %>
    <td nowrap>
      <table border=0>
         <tr>
<%        // check for highlight match to show marker chevrons
          if (treeAction != null && treeAction.equals("highlight") && myId.equals(targetId) && displayNode.getLevel() > 0)
          { %>
            <td   nowrap width="<%=20*displayNode.getLevel()%>-3">>>><img src="images/transparent.gif" border=0 width="<%=20*displayNode.getLevel()%>-3" height="1"/></td>
<%        } 
          else
          { %>           
            <td   nowrap width="<%=20*displayNode.getLevel()%>"><img src="images/transparent.gif" border=0 width="<%=20*displayNode.getLevel()%>" height="1"/></td>
<%        } %>         
<%
          // render node icon and associated url
          if (displayWebNode.getIconAction().length() > 0)
          {
            // this web node has an override icon graphic and action, use that instead of the automated folder and leaf icons %>
            <td   nowrap width="40" align="right"><a href="<%=displayWebNode.getIconAction()%>"><img src="skins/<%=skin%>/images/<%=displayWebNode.getIconGraphic()%>" vspace="0" hspace="0" border="0" height="20" width="30" alt="<%=webNodeInfo%>"/></a></td>            
<%        }              
          else if (displayWebNode.hasChildren() && !displayNode.isLeaf())
          { %>
  <td   nowrap width="40" align="right"><a href="WebTree.jsp?targetId=<%=java.net.URLEncoder.encode(myId)%>&treeParams=<%=treeParams%>&treeName=<%=treeName%>&treeAction=collapse&skin=<%=skin%>#<%=java.net.URLEncoder.encode(myId)%>"><img src="skins/<%=skin%>/images/folderOpen.gif" vspace="0" hspace="0" border="0"  alt="<%=webNodeInfo%>"/></a></div></td>      
<%        }
          else if (displayWebNode.hasChildren())           
          { 
            // no children currently displayed, but has children %>
            <td   nowrap width="40" align="right"><a href="WebTree.jsp?targetId=<%=java.net.URLEncoder.encode(myId)%>&treeParams=<%=treeParams%>&treeName=<%=treeName%>&amp;treeAction=expand&skin=<%=skin%>#<%=java.net.URLEncoder.encode(myId)%>"><img src="skins/<%=skin%>/images/folderClosed.gif" vspace="0" hspace="0" border="0"  alt="<%=webNodeInfo%>"/></a></div></td>      
<%        }   
          else
          {
            // no children display, and has not children at all, this is a leaf %>
            <td   nowrap width="40" align="right"><img src="skins/<%=skin%>/images/leaf.gif" vspace="0" hspace="0" border="0"  alt="<%=webNodeInfo%>"/></td>      
<%        }   
 
          // render name with action (if defined)
          String linkAction = displayWebNode.getAction();

          if (linkAction.length() > 0)
          { %>
            <td  nowrap><a name="<%=myId%>" href="<%=linkAction%>"><%=myName%></a></td>                
<%        }  
          else
          { %>
            <td nowrap  class="treeNode"><a name="<%=myId%>" onFocus="blur();"></a><%=myName%></td>                
<%        } %>                                 
          </tr>
        </table>
     </td>    
  </tr>  
<%      } // end webNode is null check
      } // end while loop
%> 
</table>
<%
    } 
    else
    { 
      // null webTree object 
%>
      <table>
      <tr><td>&nbsp;</td></tr>   
      <tr><td><b>Your Tree Session has Expired!</b></td></tr>
      <tr><td>Click the link below to close this window.</td></tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td><a target="_top" href="javascript:top.window.close()">Close Window</a></td></tr>   
      <tr><td>&nbsp;</td></tr>
      </table>              
<%  }
  } // end try
  catch (Exception e)
  {
  e.printStackTrace(System.out);
 %>   
      <table>
      <tr><td>&nbsp;</td></tr>   
      <tr><td><b>Server Error!</b></td></tr>
      <tr><td>&nbsp;</td></tr>
      <tr><td>Click the link below to close this window.</td></tr> 
      <tr><td>&nbsp;</td></tr>   
      <tr><td><a target="_top" href="javascript:top.window.close()">Close Window</a></td></tr>   
      <tr><td>&nbsp;</td></tr>   
      <tr valign="bottom"><td>Exception Raised : <%=e.toString()%></td></tr>
      <% e.printStackTrace(); %>
      </table>
 <%
  } // end catch
 %>  
   </body>   
</html>


