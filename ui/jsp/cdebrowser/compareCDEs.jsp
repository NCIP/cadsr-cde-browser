










<HTML>
<HEAD>
<TITLE>Display CDE Cart</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK REL=STYLESHEET TYPE="text/css" HREF="/cdebrowser/css/blaf.css">
<SCRIPT LANGUAGE="JavaScript1.1" SRC="jsLib/checkbox.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--

function details(linkParms ){
  var urlString="search?dataElementDetails=9" + linkParms + "&PageId=DataElementsGroup"+"&queryDE=yes";
  newBrowserWin(urlString,'deDetails',800,600)

}

-->

</SCRIPT>
</HEAD>
<BODY bgcolor="#ffffff" topmargin="0">





<SCRIPT LANGUAGE="JavaScript1.1" SRC='/cdebrowser/jsLib/newWinJS.js'></SCRIPT>
<SCRIPT LANGUAGE="JavaScript1.1" SRC='/cdebrowser/jsLib/helpWinJS.js'></SCRIPT>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>

    <td align="left" nowrap>

    <img src="/cdebrowser/i/graphic6.gif" border="0">
    </td>
    <td align=right valign=top colspan=2 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 >
        <TR>
          <TD valign="TOP" align="CENTER" width="1%" colspan=1><A HREF="javascript:window.close()" TARGET="_top"><img src="/cdebrowser/i/icon_return.gif" height="32" width="32" border="0" alt="Back to Search Results"></A><br><font color=brown face=verdana size=1>&nbsp;Back&nbsp;</font></TD>
          <TD valign="TOP" align="RIGHT" width="1%" colspan=1><A HREF="javascript:newBrowserWin('/cdebrowser/common/help/cdeBrowserHelp.html','helpWin',700,600)">
          <img src="/cdebrowser/i/icon_help.gif" height="32" width="32" border="0" alt="Task Help"></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
        </TR>
      </TABLE>
    </td>
  </tr>
  <tr>
    <td align="left" class="OraInlineInfoText" nowrap>
       <logic:present name="nciUser">
        <bean:message key="user.greet" />
    	<bean:write name="nciUser" property="username"  scope="session"/>
       </logic:present>
    </td>
  </tr>
</TABLE>



<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
  <tr>
    <td width=98%>&nbsp;</td>
    <td valign=bottom align=right>
      <table border=0 cellpadding=0 cellspacing=0>
        <tr>


<TD bgcolor="#336699" width="1%" align=LEFT valign=TOP><IMG SRC="/cdebrowser/i/ctab_open.gif" height=21 width=18 border=0></TD>
<TD width=1% bgcolor="#336699"><b><font size="-1" face="Arial" color="#FFFFFF">Compare&nbsp;CDEs</font></b></TD>
<TD bgcolor="#336699" width="1%" align=RIGHT valign=TOP><IMG SRC="/cdebrowser/i/ctab_close.gif" height=21 width=12 border=0></TD>


</table>
</td>
</TR>
</TABLE>

<TABLE width=100% Cellpadding=0 Cellspacing=0 border=0>
<TR>
<td align=left valign=top width="1%" bgcolor="#336699"><img src="/cdebrowser/i/top_left.gif" width=4 height="25"></td>
<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>


<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>

<!-- add here --->

<TD align=left valign=center bgcolor="#336699" height=25 width="94%">&nbsp;</TD>
</tr>
</table>

<table  width=100% Cellpadding=0 Cellspacing=0 border=0>
<tr>
<td align=right valign=top width=49 height=21 bgcolor="#336699"><img src="/cdebrowser/i/left_end_bottom.gif" height=21 width=49></td>
<TD align=right valign=top bgcolor="#FFFFFF" height=21 width="100%"><img src="/cdebrowser/i/bottom_middle.gif" height=6 width=100%></TD>
<td align="LEFT" valign=top height=21 width=5  bgcolor="#FFFFFF"><img src="/cdebrowser/i/right_end_bottom.gif" height=7 width=5></td>
</TR>
</TABLE>



<table>
    <tr>
      <td align="left" class="AbbreviatedText">

      </td>
    </tr>
</table>






<table cellpadding="0" cellspacing="0" width="80%" align="center" border=0>
  <tr>
     <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
 <tr>
    <td class="OraHeaderSubSub" width="100%">Data Element</td>
  </tr>

</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="20%">Long Name</td>

    <td class="OraFieldText" width='40%'>Adverse Event Therapy 1</td>

    <td class="OraFieldText" width='40%'>Adverse Event Therapy 2</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="20%">Public ID</td>

    <td class="OraFieldText" width='40%'>2004104</td>

    <td class="OraFieldText" width='40%'>2004104</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="20%">Preferred Name</td>

    <td class="OraFieldText" width='40%'>AE_THERAPY 1</td>

    <td class="OraFieldText" width='40%'>AE_THERAPY 2</td>

  </tr>


 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Document Text</td>

    <td class="OraFieldText" width='40%'>Therapy 1</td>

    <td class="OraFieldText" width='40%'>Therapy 2</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Definition</td>

    <td class="OraFieldText" width='40%'>What additional therapy is required to treat the adverse event.</td>

    <td class="OraFieldText" width='40%'>What additional therapy is required to treat the adverse event.</td>

 </tr>



 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Context</td>

    <td class="OraFieldText" width='40%'>CCR  1</td>

    <td class="OraFieldText" width='40%'>CCR  2</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Workflow Status</td>

    <td class="OraFieldText" width='40%'>DRAFT NEW</td>

    <td class="OraFieldText" width='40%'>DRAFT NEW</td>

 </tr>
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Version</td>

    <td class="OraFieldText" width='40%'>3.1</td>

    <td class="OraFieldText" width='40%'>3.2</td>

 </tr>
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Origin</td>

    <td class="OraFieldText" width='40%'></td>

    <td class="OraFieldText" width='40%'></td>

 </tr>
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Registration Status</td>

    <td class="OraFieldText" width='40%'></td>

    <td class="OraFieldText" width='40%'></td>

 </tr>

</table>

<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
  <tr>
    <td class="OraHeaderSubSub" width="100%">Reference Documents</td>
  </tr>
</table>
<TABLE cellSpacing=5 cellPadding=2 width="80%" align=center border=0>
  <TBODY>
  <TR>
    <TD vAlign=top>
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>Document Name</TH>
          <TH class=OraTableColumnHeader>Document Type</TH></TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>ADDRESS </TD>
          <TD class=OraFieldText>LONG_NAME </TD></TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>CTEP_SHORT_NAME3169 </TD>
          <TD class=OraFieldText>HISTORIC SHORT CDE NAME
    </TD></TR></TBODY></TABLE></TD>
    <TD vAlign=top>
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>Document Name</TH>
          <TH class=OraTableColumnHeader>Document Type</TH></TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>CRF Text </TD>
          <TD class=OraFieldText>HISTORIC SHORT CDE NAME </TD></TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>CRF Text </TD>
          <TD class=OraFieldText>LONG_NAME </TD></TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>ADDRESS </TD>
          <TD class=OraFieldText>LONG_NAME </TD></TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>CTEP_SHORT_NAME3169 </TD>
          <TD class=OraFieldText>HISTORIC SHORT CDE NAME
    </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>

<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
  <tr>
    <td class="OraHeaderSubSub" width="100%">Designations</td>
  </tr>
</table>

<TABLE cellSpacing=0 cellPadding=2 width="80%" align=center border=0>
  <TBODY>
  <TR>
    <TD vAlign=top>
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="40%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>Name</TH>
          <TH class=OraTableColumnHeader>Type</TH>
          <TH class=OraTableColumnHeader>Context</TH></TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>ENGLISH </TD>
          <TD class=OraFieldText>CONTEXT NAME</TD>
          <TD class=OraFieldText>CTEP</TD></TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>ADDITIONAL_DIAGNOSES_SPECIFY</TD>
          <TD class=OraFieldText>USED_BY</TD>
          <TD class=OraFieldText>TEST</TD></TR></TBODY></TABLE></TD>
    <TD vAlign=top>
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="40%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>Name</TH>
          <TH class=OraTableColumnHeader>Type</TH>
          <TH class=OraTableColumnHeader>Context</TH></TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>ADDITIONAL_DIAGNOSES_SPECIFY</TD>
          <TD class=OraFieldText>USED_BY</TD>
          <TD class=OraFieldText>TEST</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>

<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
  <tr>
    <td class="OraHeaderSubSub" width="100%">Data Element Concept</td>
  </tr>

</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="20%">Long Name</td>

    <td class="OraFieldText" width='40%'>ADVERSE_EVENT/PATIENT_OUTCOME 1</td>

    <td class="OraFieldText" width='40%'>ADVERSE_EVENT/PATIENT_OUTCOME 2</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="20%">Public ID</td>

    <td class="OraFieldText" width='40%'>2004104</td>

    <td class="OraFieldText" width='40%'>2004104</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="20%">Preferred Name</td>

    <td class="OraFieldText" width='40%'>AE_THERAPY 1</td>

    <td class="OraFieldText" width='40%'>AE_THERAPY 2</td>

  </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="20%">Definition</td>

    <td class="OraFieldText" width='40%'>AE_THERAPY 1</td>

    <td class="OraFieldText" width='40%'>AE_THERAPY 2</td>

  </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Context</td>

    <td class="OraFieldText" width='40%'>CCR  1</td>

    <td class="OraFieldText" width='40%'>CCR  2</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Workflow Status</td>

    <td class="OraFieldText" width='40%'>DRAFT NEW</td>

    <td class="OraFieldText" width='40%'>DRAFT NEW</td>

 </tr>
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Version</td>

    <td class="OraFieldText" width='40%'>3.1</td>

    <td class="OraFieldText" width='40%'>3.2</td>

 </tr>
</table>

<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
  <tr>
    <td class="OraHeaderSubSub" width="100%">Value Domain Details</td>
  </tr>

</table>

<table width="80%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Public Id</td>

    <td class="OraFieldText" width='40%'>123234545</td>

    <td class="OraFieldText" width='40%'>123234545</td>

 </tr>
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Long Name</td>

    <td class="OraFieldText" width='40%'>ADVERSE_EVENT/PATIENT_OUTCO_VD</td>

    <td class="OraFieldText" width='40%'>ADVERSE_EVENT/PATIENT_OUTCO_VD</td>

 </tr>
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Preferred Name</td>

    <td class="OraFieldText" width='40%'>ADVERSE_EVENT/PATIENT_OUTCO_VD</td>

    <td class="OraFieldText" width='40%'>ADVERSE_EVENT/PATIENT_OUTCO_VD</td>

 </tr>
  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Definition</td>

     <td class="OraFieldText" width='40%'>ADVERSE_EVENT/PATIENT_OUTCO_VD</td>

     <td class="OraFieldText" width='40%'>ADVERSE_EVENT/PATIENT_OUTCO_VD</td>

 </tr>
  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Workflow Status</td>

     <td class="OraFieldText" width='40%'>Draft New</td>

     <td class="OraFieldText" width='40%'>Draft New</td>

 </tr>
  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Version</td>

     <td class="OraFieldText" width='40%'>2.31</td>

     <td class="OraFieldText" width='40%'>2.31</td>

 </tr>
  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Datatype</td>

     <td class="OraFieldText" width='40%'>Character</td>

     <td class="OraFieldText" width='40%'>Character</td>

 </tr>
  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Unit of Measure</td>

     <td class="OraFieldText" width='40%'></td>

     <td class="OraFieldText" width='40%'></td>

 </tr>
  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Display Format</td>

     <td class="OraFieldText" width='40%'></td>

     <td class="OraFieldText" width='40%'></td>

 </tr>

  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Maximum Length</td>

     <td class="OraFieldText" width='40%'>8</td>

     <td class="OraFieldText" width='40%'>8</td>

 </tr>
  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Minimum Length</td>

     <td class="OraFieldText" width='40%'></td>

     <td class="OraFieldText" width='40%'></td>

 </tr>

   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Decimal Place</td>

      <td class="OraFieldText" width='40%'></td>

      <td class="OraFieldText" width='40%'></td>

  </tr>
   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">High Value</td>

      <td class="OraFieldText" width='40%'></td>

      <td class="OraFieldText" width='40%'></td>

  </tr>
   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Low Value</td>

      <td class="OraFieldText" width='40%'>Character</td>

      <td class="OraFieldText" width='40%'>Character</td>

  </tr>
   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Value Domain Type</td>

      <td class="OraFieldText" width='40%'></td>

      <td class="OraFieldText" width='40%'></td>

  </tr>
   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Conceptual Domain Preferred Name</td>

      <td class="OraFieldText" width='40%'></td>

      <td class="OraFieldText" width='40%'></td>

  </tr>

   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Conceptual Domain Context Name</td>

      <td class="OraFieldText" width='40%'>8</td>

      <td class="OraFieldText" width='40%'>8</td>

  </tr>
   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Conceptual Domain Version</td>

      <td class="OraFieldText" width='40%'></td>

      <td class="OraFieldText" width='40%'></td>

 </tr>
 <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Origin</td>

      <td class="OraFieldText" width='40%'></td>

      <td class="OraFieldText" width='40%'></td>

 </tr>

</table>

<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
  <tr>
    <td class="OraHeaderSubSub" width="100%">Permissible Values</td>
  </tr>
</table>
<TABLE cellSpacing=5 cellPadding=2 width="80%" align=center border=0>
  <TBODY>
  <TR>
    <TD vAlign=top>
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>value</TH>
          <TH class=OraTableColumnHeader>value meaning</TH>
          <TH class=OraTableColumnHeader>Description</TH>
         </TR>

        <TR class=OraTabledata>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Alive</TD>
          <TD class=OraFieldText>Alive</TD>
          <TD class=OraFieldText>Alive</TD>
    </TR></TBODY></TABLE>
    </TD>
    <TD vAlign=top>
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>value</TH>
          <TH class=OraTableColumnHeader>value meaning</TH>
          <TH class=OraTableColumnHeader>Description</TH>
         </TR>

        <TR class=OraTabledata>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Alive</TD>
          <TD class=OraFieldText>Alive</TD>
          <TD class=OraFieldText>Alive</TD>
       </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
          <TD class=OraFieldText>Recovered /Alive</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
          <TD class=OraFieldText>Sequela /Alive</TD>
         </TR>

       </TBODY></TABLE></TD></TR></TBODY></TABLE>

<table cellpadding="0" cellspacing="0" width="80%" align="center">
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=0> </td>
  </tr>
  <tr>
    <td class="OraHeaderSubSub" width="100%">Classifications</td>
  </tr>
</table>
<TABLE cellSpacing=5 cellPadding=2 width="80%" align=center border=0>
  <TBODY>
  <TR>
    <TD vAlign=top>
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>CS* Preferred Name</TH>
          <TH class=OraTableColumnHeader>CS* Definition</TH>
          <TH class=OraTableColumnHeader>CSI* Name</TH>
          <TH class=OraTableColumnHeader>CSI* Type</TH>
         </TR>

        <TR class=OraTabledata>
          <TD class=OraFieldText>CATEGORY</TD>
          <TD class=OraFieldText>Type of Category</TD>
          <TD class=OraFieldText>Adverse Events</TD>
          <TD class=OraFieldText>CATEGORY_TYPE</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>DISEASE</TD>
          <TD class=OraFieldText>Type of Disease</TD>
          <TD class=OraFieldText>Breast</TD>
          <TD class=OraFieldText>DISEASE_TYPE</TD>
    </TR></TBODY></TABLE>
    </TD>
    <TD vAlign=top>
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>CS* Preferred Name</TH>
          <TH class=OraTableColumnHeader>CS* Definition</TH>
          <TH class=OraTableColumnHeader>CSI* Name</TH>
          <TH class=OraTableColumnHeader>CSI* Type</TH>
         </TR>

        <TR class=OraTabledata>
          <TD class=OraFieldText>CCR Implementation</TD>
          <TD class=OraFieldText>Applications within CCR implementing CDEs</TD>
          <TD class=OraFieldText>C3D</TD>
          <TD class=OraFieldText>USAGE_TYPE</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>C3D Domain</TD>
          <TD class=OraFieldText>Central Cancer Clinical Database</TD>
          <TD class=OraFieldText>CTMSv3.02</TD>
          <TD class=OraFieldText>C3D</TD>
    </TR></TBODY></TABLE>
    </TD></TR></TBODY></TABLE>

 <br>



<TABLE width=100% cellspacing=0 cellpadding=0 border=0>
<TR>
<TD valign=bottom width=99%><img src="/cdebrowser/i/bottom_shade.gif" height="6" width="100%"></TD>
<TD valign=bottom width="1%" align=right><img src="/cdebrowser/i/bottomblueright.gif"></TD>
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
<TD colspan=2><img src="/cdebrowser/i/bottom_middle.gif" height="6" width="100%"></TD>
</TR>
</TABLE>
</body>
</html>