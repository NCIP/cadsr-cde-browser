<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://ajaxanywhere.sourceforge.net/" prefix="aa" %>
<%@ taglib uri="http://jsf-comp.sourceforge.net/aa" prefix="jcaa" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.util.* "%>
<%@ page import="java.util.*"%>
<%@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.tree.TreeConstants " %>
<%@ page import="net.sf.jsfcomp.aa.tree.AaTreeTag"%>
<f:view>
    <t:document>
        <t:documentHead>
            <%
// get parameters
    String treeParams = request.getParameter("treeParams");
    if (treeParams == null || treeParams.equals(""))
    	treeParams = (String)request.getSession().getAttribute("paramsTree");
    String treeName = null;
    String callerParams = "";
    Hashtable params = null;
    try {
        params = TreeUtils.parseParameters(treeParams);
        String src = null;
        if (params.containsKey("src")) {
          src = (String)params.get("src");
          String modIndex = (String)params.get("moduleIndex");
          String quesIndex = (String)params.get("questionIndex");
          callerParams += "&src="+src+"&moduleIndex="+modIndex+"&questionIndex="+quesIndex;
          }
          
         treeName =(String) request.getSession().getAttribute("treeTypeName");
    } 
    catch (Exception ex) {
      System.out.println("Error: "+ex.getMessage());;
    } 
    
%>
<link rel="stylesheet" type="text/css" href="css/TreeBrowser.css"/>
<script language="JavaScript1.2"
        src="common/skins/CDEBrowser1/JavaScript.js"></script>
<script src="jsLib/aa/aa.js"></script>
<script language="JavaScript1.2">
  <!--
  function performAction(urlParams){
    var frm = findFrameByName('body');
    document.body.style.cursor = "wait";
    frm.document.body.style.cursor = "wait";
    if ("<%=treeName%>" == "formTree")
     frm.document.location = "/CDEBrowser/formSearchAction.do?method=getAllFormsForTreeNode&"+urlParams;
    else
     frm.document.location = "<%=request.getContextPath()%>" + "/search?" + urlParams + "<%=callerParams%>";
   }
  function performFormAction(urlParams){
    var frm = findFrameByName('body');
    document.body.style.cursor = "wait";
    frm.document.body.style.cursor = "wait";
    if ("<%=treeName%>" == "formTree")
     top.document.location = "/CDEBrowser/formDetailsAction.do?method=getFormDetails&"+urlParams;
    else
     frm.document.location = "<%=request.getContextPath()%>" + "/search?" + urlParams + "<%=callerParams%>";
   }

  //-->
  </script>
        </t:documentHead>
        <t:documentBody>
            <h:form id="cdeBrowserTree">
                <h:commandLink value="Refresh tree"
                               action="#{treeBacker.refreshTree}"/>
                <br/>
                <br/>
                <!-- Expand/Collapse Handled By Server -->
 <aa:zoneJSF id="treeZone">
      <jcaa:aaTree id="cdeBrowserTree" value="#{treeBacker.treeModel}"
            ajaxZone="treeZone"
            var="node" varNodeToggler="t" clientSideToggle="false"
                         binding="#{treeBacker.tree}">
                    <f:facet name="Context Folder">
                        <h:panelGroup style="white-space:nowrap;">
                            <t:graphicImage alt="Context" title="Context"
                                            value="/i/yellow-folder-open.png"
                                            rendered="#{t.nodeExpanded}"
                                            border="0"/>
                            <t:graphicImage value="/i/yellow-folder-closed.png"
                                            alt="Context" title="Context"
                                            rendered="#{!t.nodeExpanded}"
                                            border="0"/>
                            <h:outputLink 
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Context">
                        <h:panelGroup style="white-space:nowrap;">
                            <h:commandLink immediate="true"
                                           action="#{treeBacker.selectedNode}"
                                           actionListener="#{t.setNodeSelected}">
                                <t:graphicImage value="/i/yellow-folder-closed.png"
                                                border="0"/>
                                <f:param name="docNum"
                                         value="#{node.identifier}"/>
                            </h:commandLink>
                            <h:outputLink 
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Folder">
                        <h:panelGroup style="white-space:nowrap;">
                            <h:commandLink immediate="true"
                                           action="#{treeBacker.selectedNode}"
                                           actionListener="#{t.setNodeSelected}">
                                <t:graphicImage value="/i/yellow-folder-closed.png"
                                                border="0"/>
                                <f:param name="docNum"
                                         value="#{node.identifier}"/>
                            </h:commandLink>
                            <h:outputText value="#{node.description}"
                                          styleClass="treeNode"/>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Template">
                        <h:panelGroup style="white-space:nowrap;">
                            <t:graphicImage value="/i/leaf.gif" alt="Template"
                      title="Template" border="0"/>
                            <h:outputLink id="templateLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Classifications">
                        <h:panelGroup style="white-space:nowrap;">
                           <t:graphicImage alt="Classification" title="Classification"
                                            value="/i/yellow-folder-open.png"
                                            border="0"/>
                            <h:outputLink id="csLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                 styleClass="treeNode" title="#{node.toolTip}"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Container">
                        <h:panelGroup style="white-space:nowrap;">
                           <t:graphicImage alt="Container" title="Container"
                                            value="/i/container.png"
                                            border="0"/>
                            <h:outputLink id="csLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                 styleClass="treeNode" title="#{node.toolTip}"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Classification Scheme Item">
                        <h:panelGroup style="white-space:nowrap;">
                           <t:graphicImage alt="Classification Scheme Item" title="CSI"
                                            value="/i/csi.png"
                                            border="0"/>
                            <h:outputLink id="csiLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                 styleClass="treeNode" title="#{node.toolTip}"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Registration Status">
                        <h:panelGroup style="white-space:nowrap;">
                            <t:graphicImage value="/i/regStatus.gif"
                                            title="Registration Staus"
                                            alt="Registration Staus"
                                            border="0"/>
                            <h:outputLink id="rsLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Form">
                        <h:panelGroup style="white-space:nowrap;">
                            <t:graphicImage value="/i/leaf.gif" title="Form"
                                            alt="Form" border="0"/>
                            <h:outputLink id="formLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                    <f:facet name="Protocol">
                        <h:panelGroup style="white-space:nowrap;">
                            <t:graphicImage value="/i/protocol.gif"
                                            title="Protocol" alt="Protocol"
                                            border="0"/>
                            <h:outputLink id="protocolLink"
                                          value="#{node.action}">
                                <h:outputText value="#{node.description}"
                                              styleClass="treeNode"/>
                            </h:outputLink>
                        </h:panelGroup>
                    </f:facet>
                </jcaa:aaTree>
          </aa:zoneJSF>
       </h:form>
<script type="text/javascript"> 
ajaxAnywhere.getZonesToReload = function(url, submitButton) {
  return "treeZone"
}

ajaxAnywhere.formName = "cdeBrowserTree"; 
ajaxAnywhere.bindById();
//ajaxAnywhere.substituteFormSubmitFunction();
//ajaxAnywhere.substituteSubmitButtons();
</script>
        </t:documentBody>
    </t:document>
</f:view>

<script type="text/javascript"> 

	function addLoadEvent(func) {
	  var oldonload = window.onload;
	  if (typeof window.onload != 'function') {
	    window.onload = func;
	  } else {
	    window.onload = function() {
	      if (oldonload) {
	        oldonload();
	      }
	      func();
	    }
	  }
	}

  // Fix autoscroll for frame
  <%
    String autoScroll = request.getParameter("autoScroll");
    if (autoScroll != null && !"".equals(autoScroll)) {
        %>
	    addLoadEvent(function() {
  			parent.frames['tree'].scrollTo(<%=autoScroll%>);
  		});
        <%
    }
  %>
</script>

<!--
<span id=cnt>0</span> seconds since last page refresh. 
<script> 

var sec=0; function counter(){ 

setTimeout("counter();",1000); document.getElementById("cnt").innerHTML = sec++; 

} counter(); 

</script> 

-->

