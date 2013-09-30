<%--L
  Copyright SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.

  Portions of this source file not modified since 2008 are covered by:

  Copyright 2000-2008 Oracle, Inc.

  Distributed under the caBIG Software License.  For details see
  http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
L--%>

<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.ocbrowser.struts.common.OCBrowserNavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.ocbrowser.struts.common.OCBrowserFormConstants"%>

<bean:define id="ocid" name="<%=OCBrowserFormConstants.OBJECT_CLASS%>" property="id" scope="session"/>
<%
	String urlPrefix = request.getContextPath();
	String label = pageContext.getRequest().getParameter("label");
	String[] allLabels = {"Object&nbsp;Class","Associations"};
  String obclassurl = urlPrefix+"/ocbrowser/ocDetailsAction.do?"+OCBrowserNavigationConstants.METHOD_PARAM+"="+OCBrowserNavigationConstants.OC_DETAILS+"&"+OCBrowserFormConstants.OC_IDSEQ+"="+ocid;
  String ocrurl = urlPrefix+"/ocbrowser/ocrDetailsAction.do?"+OCBrowserNavigationConstants.METHOD_PARAM+"="+OCBrowserNavigationConstants.OCR_DETAILS+"&"+OCBrowserFormConstants.OC_IDSEQ+"="+ocid+"&"+OCBrowserFormConstants.RESET_CRUMBS+"=false";
  String[] urls = {obclassurl,ocrurl};
%>


<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
    <td width=98%>&nbsp;</td>
    <td valign=bottom align=right>
      <table border=0 cellpadding=0 cellspacing=0>
        <tr>

<% for(int i=0;i<allLabels.length;i++)
 { %>

 <%
    if(allLabels[i].equals(label))
    {
 %>
      <TD bgcolor="#336699" width="1%" align=LEFT valign=TOP><IMG SRC="<%=urlPrefix%>/i/ctab_open.gif" alt="c tab open" height=21 width=18 border=0></TD>
      <TD width=1% bgcolor="#336699"><b><font size="-1" face="Arial" color="#FFFFFF">
            <%=allLabels[i]%></font></b>
      </TD>
      <TD bgcolor="#336699" width="1%" align=RIGHT valign=TOP><IMG SRC="<%=urlPrefix%>/i/ctab_close.gif" alt="c tab close" height=21 width=12 border=0></TD>
 <% } else { %>
      <TD bgcolor="#B6B687" width="1%" align=LEFT valign=TOP><IMG SRC="<%=urlPrefix%>/i/tab_open.gif" alt="tab open" height=21 width=18 border=0></TD>
      <TD width=1% bgcolor="#B6B687"><font size="-1" face="Arial" color="#000000">
          <a href="<%=urls[i]%>"><%=allLabels[i]%> </a></font></TD>
      <TD bgcolor="#B6B687" width="1%" align=RIGHT valign=TOP><IMG SRC="<%=urlPrefix%>/i/tab_close.gif" alt="tab close" height=21 width=12 border=0></TD>
<% } %>
<% }%>
</tr>
</table>

</table>
</td>
</TR>
</TABLE>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
<TR>
<td align=left valign=top width="1%" bgcolor="#336699"><img src="<%=urlPrefix%>/i/top_left.gif" alt="top left" width=4 height="25"></td>
<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>


<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>

<!-- add here --->

<TD align=left valign=center bgcolor="#336699" height=25 width="94%">&nbsp;</TD>
</tr>
</table>

<table  width=100% Cellpadding=0 Cellspacing=0 border=0>
<tr>
<td align=right valign=top width=49 height=21 bgcolor="#336699"><img src="<%=urlPrefix%>/i/left_end_bottom.gif" alt="left end bottom" height=21 width=49></td>
<TD align=right valign=top bgcolor="#FFFFFF" height=21 width="100%"><img src="<%=urlPrefix%>/i/bottom_middle.gif" alt="middle bottom" height=6 width=100%></TD>
<td align="LEFT" valign=top height=21 width=5  bgcolor="#FFFFFF"><img src="<%=urlPrefix%>/i/right_end_bottom.gif" alt="right end bottom" height=7 width=5></td>
</TR>
</TABLE>

