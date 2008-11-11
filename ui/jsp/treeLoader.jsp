<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>

<%
  	// get parameters
    String treeParams     = StringEscapeUtils.escapeHtml((String) request.getParameter("treeParams"));
    String skin           = StringEscapeUtils.escapeHtml((String) request.getParameter("skin"));
    String treeDirective  = StringEscapeUtils.escapeHtml((String) request.getParameter("treeDirective"));
    String treeAction     = StringEscapeUtils.escapeHtml((String) request.getParameter("treeAction"));
    String treeName       = StringEscapeUtils.escapeHtml((String) request.getParameter("treeName"));
    request.getSession().setAttribute("paramsTree", treeParams);
    
    // URL encode parameters
    if (treeParams != null) treeParams = StringEscapeUtils.escapeHtml(java.net.URLEncoder.encode(treeParams));    
    if (skin != null) skin = StringEscapeUtils.escapeHtml(java.net.URLEncoder.encode(skin));    
    if (treeDirective != null) treeDirective = StringEscapeUtils.escapeHtml(java.net.URLEncoder.encode(treeDirective));    
    request.getSession().setAttribute("treeTypeName", treeName);

%>
<script language="JavaScript1.2">
  var now = new Date();    
  var glob = now.getHours()+now.getSeconds()+now.getMilliseconds();    
  window.document.write("Building tree, please wait...");  
  var targetURL = "tree2.jsf?treeName=<%=treeName%>&treeParams=<%=treeParams%>&skin=<%=skin%>&treeDirective=<%=treeDirective%>&treeName=<%=treeName%>";
  window.location.href = targetURL;     
</script>
