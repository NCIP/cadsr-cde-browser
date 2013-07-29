<%--L
  Copyright Oracle Inc, SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
L--%>

<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="gov.nih.nci.ncicb.cadsr.common.util.*"%>

<%
	CDEBrowserParams params = CDEBrowserParams.getInstance();
%>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=WINDOWS-1252">
<LINK REL=STYLESHEET TYPE="text/css" HREF="<%=request.getContextPath()%>/css/blaf.css">
<TITLE>
Data Element Details
</TITLE>
</HEAD>
<BODY topmargin="0">



<SCRIPT LANGUAGE="JavaScript">
<!--
function redirect1(detailReqType, linkParms )
{
  document.location.href = "<%=StringEscapeUtils.escapeJavaScript("search?dataElementDetails=")%>" + linkParms;
  
}
function goPage(pageInfo) {
  document.location.href = "<%=StringEscapeUtils.escapeJavaScript("search?searchDataElements=&")%>"+pageInfo;
  
}
  
//-->
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--

function tabClicked(tabNum,urlParams) {
   document.location.href=urlParams + "&tabClicked=" + tabNum +"&PageId=GetDetailsGroup";
}

function subTabClicked(tabNum,urlParams) {
   document.location.href=urlParams + "&subTabClicked=" + tabNum + "&PageId=GetDetailsGroup";
}


//-->
</SCRIPT>

<SCRIPT LANGUAGE="JavaScript1.1" SRC='/js/newWinJS.js'></SCRIPT>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
    <td align="left" nowrap>

      <img src="i/details_banner.gif" alt="details bannert" border="0">
    </td>

    <td align=right valign=top colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="javascript:window.close()" TARGET="_top"><img src="i/icon_return.gif" height="32" width="32" border="0" alt="Back"> </A><br><font color=brown face=verdana size=1>&nbsp;Back&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="<%=params.getCdeBrowserHelpUrl()%>" target="_blank"><img src="i/icon_help.gif" height="32" width="32" border="0" alt="Task Help"></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
        </TR>
      </TABLE>
    </td>
  </tr>


</TABLE>
<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width=98%>&nbsp;</td>
    <td valign=bottom align=right>
      <table border=0 cellpadding=0 cellspacing=0>
        <tr>




<TD bgcolor="#336699" width="1%" align=LEFT valign=TOP><img src="i/ctab_open.gif" alt="c tab open" height="21" width="18" border="0"></TD>
<TD width=1% bgcolor="#336699"><b><font size="-1" face="Arial" color="#FFFFFF">Data&nbsp;Element</font></b></TD>
<TD bgcolor="#336699" width="1%" align=RIGHT valign=TOP><img src="i/ctab_close.gif" alt="c tab close" height="21" width="12" border="0"></TD>

<TD bgcolor="#B6B687" width="1%" align=LEFT valign=TOP><img src="i/tab_open.gif" alt="tab open" height="21" width="13" border="0"></TD>
<TD width=1% bgcolor="#B6B687"><A HREF="javascript:tabClicked(1, 'search?dataElementConceptDetails=9')"><font size="-1" face="Arial" color="#000000">Data&nbsp;Element&nbsp;Concept</font></A></TD>
<TD bgcolor="#B6B687" width="1%" align=RIGHT valign=TOP><htmlImg page="i/tab_close.gif" alt="tab close" height="21" width="10" border="0" /></TD>


<TD bgcolor="#B6B687" width="1%" align=LEFT valign=TOP><img src="i/tab_open.gif" alt="tab open" height="21" width="13" border="0"></TD>
<TD width=1% bgcolor="#B6B687"><A HREF="javascript:tabClicked(2, 'search?listValidValuesForDataElements=9')"><font size="-1" face="Arial" color="#000000">Permissible&nbsp;Values</font></A></TD>
<TD bgcolor="#B6B687" width="1%" align=RIGHT valign=TOP><htmlImg page="/i/tab_close.gif" alt="tab close" height="21" width="10" border="0" /></TD>


<TD bgcolor="#B6B687" width="1%" align=LEFT valign=TOP><img src="i/tab_open.gif" alt="tab open" height="21" width="13" border="0"></TD>
<TD width=1% bgcolor="#B6B687"><A HREF="javascript:tabClicked(3, 'search?classificationsForDataElements=9')"><font size="-1" face="Arial" color="#000000">Classifications</font></A></TD>
<TD bgcolor="#B6B687" width="1%" align=RIGHT valign=TOP><htmlImg page="i/tab_close.gif" alt="tab close" height="21" width="10" border="0" /></TD>


<TD bgcolor="#B6B687" width="1%" align=LEFT valign=TOP><img src="i/tab_open.gif" alt="tab open" height="21" width="13" border="0"></TD>
<TD width=1% bgcolor="#B6B687"><A HREF="javascript:tabClicked(4, 'search?protocolsForDataElements=9')"><font size="-1" face="Arial" color="#000000">Form Usage</font></A></TD>
<TD bgcolor="#B6B687" width="1%" align=RIGHT valign=TOP><htmlImg page="i/tab_close.gif" alt="tab close" height="21" width="10" border="0" /></TD>


<TD bgcolor="#B6B687" width="1%" align=LEFT valign=TOP><img src="i/tab_open.gif" alt="tab open" height="21" width="13" border="0"></TD>
<TD width=1% bgcolor="#B6B687"><A HREF="javascript:tabClicked(5, 'search?dataElementDerivations=9')"><font size="-1" face="Arial" color="#000000">Data&nbsp;Element&nbsp;Derivation</font></A></TD>
<TD bgcolor="#B6B687" width="1%" align=RIGHT valign=TOP><htmlImg page="i/tab_close.gif" alt="tab close" height="21" width="10" border="0" /></TD>



</table>
</td>
</TR>
</TABLE>
<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
<TR>
<td align=left valign=top width="1%" bgcolor="#336699"><img src="i/top_left.gif" alt="top left" height="25" width="4"></td>
<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>


<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>


<TD align=left valign=center bgcolor="#336699" height=25 width="94%">&nbsp;</TD>
</tr>
</table>


<table  width=100% Cellpadding=0 Cellspacing=0 border=0>
<tr>
<td align=right valign=top width=49 height=21 bgcolor="#336699"><img src="i/left_end_bottom.gif" alt="left end bottom" height="21" width="49"></td>
<TD align=right valign=top bgcolor="#FFFFFF" height=21 width="100%"><img src="i/bottom_middle.gif" alt="bottom middle" height="6" width="100%"></TD>
<td align="LEFT" valign=top height=21 width=5  bgcolor="#FFFFFF"><img src="i/right_end_bottom.gif" alt="right end bottom" height="7" width="5"></td>
</TR>
</TABLE>

<form method="POST" ENCTYPE="application/x-www-form-urlencoded" action="/cdebrowser/search">
<input type="HIDDEN" name="PageId" value="GetDetailsGroup"/>

<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Data Element Details</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" alt="beige dot" width="99%" align=top border=0> </td>
  </tr>
</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">

 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Public ID:</td>
    <td class="OraFieldText">3473604</td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Short Name:</td>
    <td class="OraFieldText">ADDITIONAL_DIAGNOSES_SPFY_ABL1</td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText" width="20%">Long Name:</td>
    <td class="OraFieldText">ADDITIONAL DIAGNOSES, SPECIFY ABL1 Gene</td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Preferred Question Text:</td>
    <td class="OraFieldText"></td>
 </tr>
 
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Definition:</td>
    <td class="OraFieldText">ADDITIONAL_DIAGNOSES_SPECIFY_The ABL1 gene (ABL family) at 9q34.1 encodes a ubiquitous 145 kD nuclear/cytoplasmic non-receptor protein tyrosine kinase implicated in cell differentiation, division, adhesion, and stress response. ABL1 plays a role in myeloid hematopoiesis. Alternatively spliced exon 1a specifies nuclear ABL1 that enhances target gene transcription. ABL nuclear activity is regulated by CDC2 phosphorylation and by RB1. RB inhibits the ABL kinase. ABL transactivates the p73 gene and phosphorylates the p73 substrate. Alternatively spliced exon 1b encodes myristylated, plasma membrane-associated ABL1. PEST phosphatases negatively regulate ABL and PSTPIP1 is an ABL substrate. The ABL SH3 domain negatively regulates its kinase activity. The N-terminus mediates autoregulation and its loss is oncogenic. Fused BCR-ABL oncogenes are responsible for CML, AML, and ALL. (NCI) </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Value Domain:</td>
    <td class="OraFieldText">ABL1 </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Data Element Concept:</td>
    <td class="OraFieldText">ADDITIONAL_DIAGNOSES_SPECIFY </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Context:</td>
    <td class="OraFieldText">TEST </td>
 </tr>

 <tr class="OraTabledata">
    <td class="TableRowPromptText">Workflow Status:</td>
    <td class="OraFieldText">DRAFT NEW </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Version:</td>
    <td class="OraFieldText">1.0 </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Origin:</td>
    <td class="OraFieldText"> </td>
 </tr>
 <tr class="OraTabledata">
    <td class="TableRowPromptText">Registration Status:</td>
    <td class="OraFieldText"> </td>
 </tr>
 
</table>

<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%">Reference Documents</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" alt="beige dot" width="99%" align=top border=0> </td>
  </tr>
</table>
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Document Name</th>
    <th class="OraTableColumnHeader">Document Type</th>
    <th class="OraTableColumnHeader">Document Text</th>
    <th class="OraTableColumnHeader">URL</th>
  </tr>

       <tr class="OraTabledata">
         <td colspan="5">There are no reference documents for the selected CDE.</td>
       </tr>

</table>

<br>
<table cellpadding="0" cellspacing="0" width="80%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%">Alternate Names</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" alt="beige dot" width="99%" align=top border=0> </td>
  </tr>
</table>
<table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Name</th>
    <th class="OraTableColumnHeader">Type</th>
    <th class="OraTableColumnHeader">Context</th>
    <th class="OraTableColumnHeader">Language</th>
  </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">ENGLISH </td>
        <td class="OraFieldText">CONTEXT NAME </td>
        <td class="OraFieldText">TEST </td>
        <td class="OraFieldText">ENGLISH </td>
      </tr>

</table>


</form>

<TABLE width=100% cellspacing=0 cellpadding=0 border=0>
<TR>
<TD valign=bottom width=99%><img src="i/bottom_shade.gif" alt="bottom shade" height="6" width="100%"></TD>
<TD valign=bottom width="1%" align=right><img src="i/bottomblueright.gif" alt="bottom blue right"></TD>
</TR>
</TABLE>
<TABLE width=100% cellspacing=0 cellpadding=0 bgcolor="#336699" border=0>
<TR>
<TD width="60%" align="LEFT">
<FONT face="Arial" color="WHITE" size="-2">User: </FONT>
<FONT face="Arial" size="-1" color="#CCCC99">
  
  
    Public User    
  
</FONT>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<FONT color="white" size=-2 face=arial>Version 2.1.1</FONT>
</TD>

</TR>
<TR>
<TD colspan=2><img src="i/bottom_middle.gif" alt="bottom middle" height="6" width="100%"></TD>
</TR>
</TABLE>

</BODY>
</HTML>

