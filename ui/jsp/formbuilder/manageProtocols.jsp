<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.resource.Form"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.CaDSRConstants"%>
<HTML>
  <HEAD>
    <TITLE>Manage Protocols</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <% String urlPrefix = "";
       String pageUrl = "&PageId=DataElementsGroup";
       String contextPath = request.getContextPath();
        String protoLOVUrl = contextPath +
       "/formLOVAction.do?method=getProtocolsLOV&idVar=protocolIdSeq&nameVar=protocolLongName&chkContext=true"+pageUrl;
    %>

    <SCRIPT LANGUAGE="JavaScript">
        function clearProtocol() {
          document.forms[0].protocolLongName.value = "";
          document.forms[0].protocolIdSeq.value = "";
        }
        function gotoProtocolsLOV(contextId) {
             //var dest = "<%= protoLOVUrl %>" + '&contextIdSeq=' + contextId;
             var dest = "<%= protoLOVUrl %>";
             newWin(dest, 'ProtocolsLOV', 700, 600);
        }
    </SCRIPT>
    <%@ include file="../common/in_process_common_header_inc.jsp"%>
    <jsp:include page="../common/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Manage&nbsp;Protocols"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>

    <%@ include file="showMessages.jsp"%>

    <logic:present name="<%=FormConstants.CRF%>" >
      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap width="20%" ><bean:message key="cadsr.formbuilder.form.name" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="longName"
              />
          </td>
        </tr>
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap width="20%" ><bean:message key="cadsr.formbuilder.form.context" />:</td>
          <td class="OraFieldText" nowrap >
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="context.name"
              />
          </td>
        </tr>

        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap width="20%" ><bean:message key="cadsr.formbuilder.form.version" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="version"
              />
          </td>
        </tr>
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap width="20%" ><bean:message key="cadsr.formbuilder.form.workflow" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="aslName"
              />
          </td>      
        </tr>
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap width="20%" ><bean:message key="cadsr.formbuilder.form.type" />:</td>  
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="formType"
              />
          </td>        
        </tr>
        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap width="20%" ><bean:message key="cadsr.formbuilder.form.category" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="formCategory"
              />
          </td>
        </tr>     

        <tr class="OraTabledata">
          <td class="OraTableColumnHeader" nowrap width="20%" ><bean:message key="cadsr.formbuilder.form.definition" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:write
              name="<%= FormConstants.CRF %>"
              property="preferredDefinition"
              />
           </td>
        </tr>
      </table>

      <table cellpadding="0" cellspacing="0" width="80%" align="center">
        <tr >
          <td >
            &nbsp;
          </td>
        </tr>         
        <tr>
          <td class="OraHeaderSubSub" width="100%">Protocols</td>
        </tr>
        <tr>
          <td><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
        </tr>
      </table>

      <table width="80%" align="center" cellpadding="1" cellspacing="1" bgcolor="#999966">
        <tr class="OraTableColumnHeader">
          <th scope="col">Long Name</th>
          <th scope="col">Preferred Name</th>
          <th scope="col">Context</th>
          <th scope="col">Workflow Status</th>
          <th scope="col">Definition</th>
          <th scope="col">Lead Organization</th>
          <th scope="col">Protocol ID</th>
          <th scope="col">DELETE</th>          
        </tr>
        <logic:present name="<%=FormConstants.CRF%>" >
          <logic:notPresent name="<%=FormConstants.CRF%>" property="protocols">
          <tr class="OraTabledata">
            No protocols.
          </tr>
        </logic:notPresent>
        
          <logic:present name="<%=FormConstants.CRF%>" property="protocols">

          <logic:equal name="<%=FormConstants.CRF%>" property="protocols.empty" value="false">
          <logic:iterate id="protocol" indexId="protocolIndex" name="<%=FormConstants.CRF%>" type="gov.nih.nci.ncicb.cadsr.common.resource.Protocol" property="protocols">          
            <tr class="OraTabledata">
              <td class="OraFieldText">
                <bean:write name="protocol" property="longName"/>
              </td>
              <td class="OraFieldText">
                <bean:write name="protocol" property="preferredName"/>
              </td>
              <td class="OraFieldText">
                <bean:write name="protocol" property="context.name"/>
              </td>
              <td class="OraFieldText">
                <bean:write name="protocol" property="aslName"/>
              </td>
              <td class="OraFieldText">
                <bean:write name="protocol" property="preferredDefinition"/>
              </td>
              <td class="OraFieldText">
                <bean:write name="protocol" property="leadOrg"/>
              </td>
              <td class="OraFieldText">
                <bean:write name="protocol" property="protocolId"/>
              </td>
                <td class="OraFieldText" align="center">
                    <html:link action='<%= "/removeProtocol?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.REMOVE_PROTOCOL %>' paramId="<%= FormConstants.PROTOCOL_ID_SEQ%>" paramName="protocol" paramProperty="protoIdseq">
                      <html:img src='<%=urlPrefix+"i/delete.gif"%>' border="0" alt="Remove"/>
                    </html:link>
                 </td>
              </tr>
        </logic:iterate>
       </logic:equal>
       <logic:notEqual name="<%=FormConstants.CRF%>" property="protocols.empty" value="false">
	       <tr class="OraTabledata">
	       <td class="OraFieldText" colspan="8" >
	       No Protocols
	       </tr>
       </logic:notEqual>
    </logic:present>       
    </logic:present>
    </table>
    </logic:present>
  
  <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
    <tr class>
      <td >
        &nbsp;
      </td>
    </tr> 
  </table>

<%--add new protocols--%>
  <html:form action='<%="/addProtocol?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.ADD_PROTOCOL%>'>
  <table cellspacing="2" cellpadding="3" border="0" width="80%" >
    <tr>
      <td class="OraFieldtitlebold" nowrap>Add New Protocol:</td>
      <td class="OraFieldText" nowrap>
      <input type=hidden name="protocolIdSeq" 
        readonly="true" 
        size="19"
        styleClass="LOVField"
        />
      <input type=text name="protocolLongName" 
        readonly="true" 
        size="19"
        styleClass="LOVField"
        />
      <input type=hidden name="preferredName" 
        readonly="true" 
        size="19"
        styleClass="LOVField"
        />
      <input type=hidden name="context" 
        readonly="true" 
        size="19"
        styleClass="LOVField"
        />
      <input type=hidden name="aslName" 
        readonly="true" 
        size="19"
        styleClass="LOVField"
        />
      <input type=hidden name="definition" 
        readonly="true" 
        size="19"
        styleClass="LOVField"
        />
      <input type=hidden name="leadOrg" 
        readonly="true" 
        size="19"
        styleClass="LOVField"
        />
      <input type=hidden name="protocolId" 
        readonly="true" 
        size="19"
        styleClass="LOVField"
        />
        &nbsp;
         <a href="javascript:gotoProtocolsLOV('<%=((Form)session.getAttribute(FormConstants.CRF)).getConteIdseq()%>')">
            <img src="<%=urlPrefix%>i/search_light.gif" border="0" alt="Search for Protocol Items">
        </a>
        <a href="javascript:clearProtocol()">
            <i>Clear</i>
        </a>
      </td>
    </tr>
  </table>
  </html:form>

<%--done button--%>
<table width="20%" align="center" cellpadding="1" cellspacing="1" border="0" >
  <tr >
    <td>
      <a href="javascript:document.forms[0].submit();">
        <html:img src='<%=urlPrefix+"i/update_button.gif"%>' border="0" alt="Update"/>
      </a>
    </td>
    <td >
        <html:link action='<%= "/doneProtocol?" + NavigationConstants.METHOD_PARAM + "=" + NavigationConstants.DONE_PROTOCOL %>'>
            <html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Done"/>
          </html:link>    
    </td>           
  </tr> 
  <tr >
    <td >
      &nbsp;
    </td>                          
  </tr>         
  
</table>

<logic:notPresent name="<%=FormConstants.CRF%>">
    Selected form has been deleted by a diffrent user 
</logic:notPresent>

<%@ include file="../common/common_bottom_border.jsp"%>
</BODY>
</HTML>
