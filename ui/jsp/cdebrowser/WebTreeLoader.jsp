<%
  	// get parameters
    String treeClass      = (String) request.getParameter("treeClass");
    String treeParams     = (String) request.getParameter("treeParams");
    String skin           = (String) request.getParameter("skin");
    String treeDirective  = (String) request.getParameter("treeDirective");
    String treeAction     = (String) request.getParameter("treeAction");
    String treeName       = (String) request.getParameter("treeName");
    
    // URL encode parameters
    if (treeClass != null) treeClass = java.net.URLEncoder.encode(treeClass);    
    if (treeParams != null) treeParams = java.net.URLEncoder.encode(treeParams);    
    if (skin != null) skin = java.net.URLEncoder.encode(skin);    
    if (treeDirective != null) treeDirective = java.net.URLEncoder.encode(treeDirective);    

    
%>

<script language="JavaScript1.2">
  var now = new Date();
  var glob = now.getHours()+now.getSeconds()+now.getMilliseconds();
  window.document.write("Building tree, please wait...");  
  var targetURL = "WebTree.jsp?<%= treeAction != null?("treeAction="+treeAction + "&"):treeAction%>treeName=<%= request.getParameter("treeName") %>&treeClass=<%=treeClass%>&treeParams=<%=treeParams%>&skin=<%=skin%>&treeDirective=<%=treeDirective%>&glob="+glob + "&treeName=<%=treeName%>";
  window.location.href = targetURL;     
</script>
