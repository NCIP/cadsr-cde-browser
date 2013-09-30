<%--L
  Copyright SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.

  Portions of this source file not modified since 2008 are covered by:

  Copyright 2000-2008 Oracle, Inc.

  Distributed under the caBIG Software License.  For details see
  http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
L--%>

<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/cdebrowser.tld" prefix="cde"%>

<%@page import="javax.servlet.http.* " %>
<%@page import="javax.servlet.* " %>
<%//@page import="gov.nih.nci.ncicb.cadsr.cdebrowser.* " %>
<%@page import="gov.nih.nci.ncicb.cadsr.common.util.* " %>
<%@page import="oracle.clex.process.jsp.GetInfoBean " %>
<%@page import="oracle.clex.process.PageConstants " %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>

<jsp:useBean id="infoBean" class="oracle.clex.process.jsp.GetInfoBean"/>
<jsp:setProperty name="infoBean" property="session" value="<%=session %>"/>

<%@include  file="/jsp/cdebrowser/cdebrowserCommon_html/SessionAuth.html"%>

<%
  TabInfoBean tib = (TabInfoBean)infoBean.getInfo("tib");
  UserErrorMessage uem = (UserErrorMessage)infoBean.getInfo("uem");
  String pageId = StringEscapeUtils.escapeHtml(infoBean.getPageId());
  String pageName = StringEscapeUtils.escapeHtml(PageConstants.PAGEID);
  String pageUrl = "&"+StringEscapeUtils.escapeHtml(pageName+"="+pageId);
  CDEBrowserParams params = CDEBrowserParams.getInstance(); 
%>

<%
  
  String msgAlign = "CENTER";
  if (uem!=null && uem.getMsgTechnical().length() > 0) {
    msgAlign = "LEFT";
  }
  else
  {
	  uem=new UserErrorMessage();
  }
  
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Expires" CONTENT="Thu, 01 Dec 1994 16:00:00 GMT">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
</HEAD>
<BODY topmargin="0">
<%@ include  file="/jsp/cdebrowser/cdebrowserCommon_html/tab_include_lov.html" %>

<CENTER>
<FORM>
<input type="HIDDEN" name="<%= PageConstants.PAGEID %>" value="<%= StringEscapeUtils.escapeHtml(infoBean.getPageId())%>"/>
<TABLE WIDTH="90%">
<TR>
<TD ALIGN="<%= msgAlign %>">
  <H2><font face="Arial, Helvetica, sans-serif" color="red"><%= uem.getMsgOverview() %></font></H2>
</TD>
</TR>
<TR>
<TD ALIGN="<%= msgAlign %>">
  <H3><font face="Arial, Helvetica, sans-serif"><%= uem.getMsgText() %></font></H3>
</TD>
</TR>
<TR>
<TD ALIGN="<%= msgAlign %>">
  <H4><%= uem.getMsgHelp() %></H4>
</TD>
</TR>
<TR>
<TD ALIGN="<%= msgAlign %>">
  <H4><%= uem.getMsgLink() %></H4>
</TD>
</TR>
<TR>
<TD ALIGN="<%= msgAlign %>">
  <%
    if (uem.getMsgTechnical().length() > 0) {
  %>
      <HR>
  <%
    }
  %>
</TD>
</TR>
<TR>
<TD ALIGN="<%= msgAlign %>">
  <font size="-1">
<%= uem.getMsgTechnical() %>
</font>
</TD>
</TR>
</TABLE>
</FORM>
</CENTER>
<%@ include file="/jsp/common/common_bottom_border.jsp"%>

</BODY>
</HTML>

