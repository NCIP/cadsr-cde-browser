<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>
<%@ page import="oracle.clex.process.jsp.GetInfoBean "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.html.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.util.* "%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<HTML>
  <HEAD>
    <TITLE>Add Classifications</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css"/>
  </HEAD>
  <BODY topmargin=0 bgcolor="#ffffff">
    <%   
      int nbOfClassifications = 3;
      String urlPrefix = "";
      String pageUrl = "&PageId=DataElementsGroup";
      String contextPath = request.getContextPath();
      String[] csLOVUrl = new String[3];
      for(int i=0; i<nbOfClassifications; i++) 
      csLOVUrl[i] = "javascript:newWin('"+contextPath+"/search?classificationsLOV=9&idVar=jspClassification[" + i + "]&nameVar=txtClassSchemeItem" + i + pageUrl+"','csLOV',700,600)";

      %>
    <%@ include file="/formbuilder/common_header_inc.jsp"%>
    <jsp:include page="/formbuilder/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Add&nbsp;Classifications"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>


    <logic:present name="<%=FormConstants.CRF%>">
      <html:form action='<%="/addClassifications?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.ADD_CLASSIFICATIONS%>'>

  <table cellspacing="2" cellpadding="3" border="0" width="100%">

    <% for(int i=0; i<nbOfClassifications; i++) { %>

    <tr>
      <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.classification"/>:</td>
      <td class="OraFieldText" nowrap>
      <input type="text" 
        name="<%= FormConstants.CSI_NAME + i%>" 
        disabled="true"
        readonly="true" 
        size="19"
        styleClass="LOVField"
        onfocus="this.blur();"/>
        &nbsp;
        <a href="<%=csLOVUrl[i]%>"><img src="<%=urlPrefix%>i/search_light.gif" border="0" alt="Search for Classification Scheme Items"></a>&nbsp;
        <a href="javascript:clearClassSchemeItem()"><i>Clear</i></a>
        
        <html:hidden  property='<%=FormConstants.CS_CSI_ID + "[" + i + "]"%>'/>
      </td>
    </tr>

    <% } %>
  </table>

      
      <%@ include file="/formbuilder/addClassifications_inc.jsp"%>    

      </html:form>
    </logic:present>

    <logic:notPresent name="<%=FormConstants.CRF%>">Selected form has been deleted by a diffrent user </logic:notPresent>

    <%@ include file="../common/common_bottom_border.jsp"%>

  </BODY>
</HTML>
