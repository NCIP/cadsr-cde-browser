



<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/cdebrowser.tld" prefix="cde"%>


<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants"%>
<%@ page import="gov.nih.nci.ncicb.cadsr.CaDSRConstants"%>
<%@page import="gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderConstants" %>






<HTML>
<HEAD>
<TITLE>Display CDE Cart</TITLE>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<LINK REL=STYLESHEET TYPE="text/css" HREF="/cdebrowser/css/blaf.css">
<SCRIPT LANGUAGE="JavaScript1.1" SRC="jsLib/checkbox.js"></SCRIPT>



<SCRIPT LANGUAGE="JavaScript">
<!--

function redirect1(detailReqType, linkParms )
{
  var urlString ="/cdebrowser/search?dataElementDetails=9" + linkParms + "&PageId=DataElementsGroup"+"&queryDE=yes";
  newBrowserWin(urlString,'deDetails',800,600)
  
}

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
    <td align=right valign=top colspan=1 nowrap>
      <TABLE Cellpadding=0 Cellspacing=0 border=0 width=20% >
        <TR>          
          <TD valign="TOP" align="CENTER"  colspan=1><A HREF="<%="/cdebrowser/cdeBrowse.jsp?PageId=DataElementsGroup"%>" TARGET="_top"><IMG SRC="/cdebrowser/i/icon_home.gif" alt="Home" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Home&nbsp;</font></TD>
          <TD valign="TOP" align="CENTER"  colspan=1><A HREF="javascript:newBrowserWin('/cdebrowser/common/help/cdeBrowserHelp.html','helpWin',700,600)">
          <img src="/cdebrowser/i/icon_help.gif" height="32" width="32" border="0" alt="Task Help"></A><br><font color=brown face=verdana size=1>&nbsp;Help&nbsp;</font></TD>
         <logic:present name="nciUser">
            <TD valign="TOP" align="CENTER"  colspan=1><A HREF="<%="logout?FirstTimer=0"%>" TARGET="_top"><IMG SRC="/cdebrowser/i/logout.gif" alt="Logout" border=0  width=32 height=32></A><br><font color=brown face=verdana size=1>&nbsp;Logout&nbsp;</font></TD>
          </logic:present>        
        
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



<TABLE width=140% Cellpadding=0 Cellspacing=0 border=0>
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

<TABLE width=140% Cellpadding=0 Cellspacing=0 border=0>
<TR>
<td align=left valign=top width="1%" bgcolor="#336699"><img src="/cdebrowser/i/top_left.gif" width=4 height="25"></td>
<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>


<td align=left valign=top width="5%" bgcolor="#336699">&nbsp;</td>

<!-- add here --->

<TD align=left valign=center bgcolor="#336699" height=25 width="94%">&nbsp;</TD>
</tr>
</table>

<table  width=140% Cellpadding=0 Cellspacing=0 border=0>
<tr>
<td align=right valign=top width=49 height=21 bgcolor="#336699"><img src="/cdebrowser/i/left_end_bottom.gif" height=21 width=49></td>
<TD align=right valign=top bgcolor="#FFFFFF" height=21 width="100%"><img src="/cdebrowser/i/bottom_middle.gif" height=6 width=100%></TD>
<td align="LEFT" valign=top height=21 width=5  bgcolor="#FFFFFF"><img src="/cdebrowser/i/right_end_bottom.gif" height=7 width=5></td>
</TR>
</TABLE>


<table align="center" width=15% Cellpadding=0 Cellspacing=0 border=0>
    <tr>
        <TD valign="TOP" align="CENTER"  colspan=1><A HREF="<%="/cdebrowser/cdeBrowse.jsp?PageId=DataElementsGroup"%>" TARGET="_top"><img src="/cdebrowser/i/backButton.gif" border=0></A></TD>
        <TD valign="TOP" align="CENTER"  colspan=1>
              <html:link action='<%="/cdebrowser/CDECompareExcelDownload.do?"+NavigationConstants.METHOD_PARAM+"=downloadToExcel"%>' 
                 >
                <img src="/cdebrowser/i/excelDownload.gif" border=0>
              </html:link>          
        </TD>
    </tr>
</table>
<table>
    <tr>
      <td align="left" class="AbbreviatedText">
        
      </td>
    </tr>
</table>
<table cellpadding="0" cellspacing="0" width="90%" align="center" border=0>
 <tr class="OraTabledata">
    <td class="OraFieldText" ><a class="link" href="#dataElement">Data Element</a></td>
    <td class="OraFieldText" ><a class="link" href="#dataElementConcept">Data Element Concept</a></td>
    <td class="OraFieldText" ><a class="link" href="#valueDomain">Value Domain</a></td>
    <td class="OraFieldText" ><a class="link" href="#permissibleValues">Permissible Values</a></td>
    <td class="OraFieldText" ><a class="link" href="#referenceDocuments">Reference Documents</a></td>  
    <td class="OraFieldText" ><a class="link" href="#classifications">Classifications</a></td>
 </tr>
 </table>
<table>
    <tr>
      <td align="left" class="AbbreviatedText">

      </td>
    </tr>
</table>


<br>
<A NAME="dataElement"></A> 

<table cellpadding="0" cellspacing="0" width="140%" align="center" border="0">
  <tr>
    <td class="OraHeaderSubSub" width="10%">Data Element</td>     
    <td><input type="checkbox"  width="30%" onClick="ToggleAll(this)"/></td>
    <td><input type="checkbox"  width="30%" onClick="ToggleAll(this)"/></td>
    <td><input type="checkbox"  width="30%" onClick="ToggleAll(this)"/></td>
  </tr> 
</table>

<table cellpadding="0" cellspacing="0" align="center" width="100%"  border="0">
  <tr>
    <td width="8%" ><a href=" ">Check All</a></td>
    <td width="8%" ><a href=" ">Clear All</a></td>
     <td align="left">
          <img  src="/cdebrowser/i/remove_from_cde_comparelist.gif" >
      </td> 
     <td align="left">
              <html:link action='<%="/cdebrowser/CDECompareExcelDownload.do?"+NavigationConstants.METHOD_PARAM+"=gotoChangeCompareOrder"%>' 
                 >
                <img src="/cdebrowser/i/changeCompareOrder.gi" border=0>
              </html:link>            
      </td>          
  </tr>
</table>

<table cellpadding="0" cellspacing="0" width="140%" align="center">
  <tr>
    <td width="140%"><img height=2 src="i/beigedot.gif" width="99%" align=top border=1> </td>
  </tr>  
</table>

<table width="140%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Public ID</td>

    <td class="OraFieldText" width='30%'><a href="javascript:redirect1('dataElementDetails','&p_de_idseq=99BA9DC8-292F-4E69-E034-080020C9C0E0')">61250</a></td>

    <td class="OraFieldText" width='30%'><a href="javascript:redirect1('dataElementDetails','&p_de_idseq=99BA9DC8-292F-4E69-E034-080020C9C0E0')">2093</a></td>

   <td class="OraFieldText" width='30%'><a href="javascript:redirect1('dataElementDetails','&p_de_idseq=99BA9DC8-292F-4E69-E034-080020C9C0E0')">2093</a></td>
 
 </tr>
 
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Long Name</td>

    <td class="OraFieldText" width='30%'>Agent Dose Units</td>

    <td class="OraFieldText" width='30%'>Agent Dose UOM</td>
    
    <td class="OraFieldText" width='30%'>Agent Dose UOM</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Document Text</td>

    <td class="OraFieldText" width='30%'>Units</td>

    <td class="OraFieldText" width='30%'>Units</td>
    
    <td class="OraFieldText" width='30%'>Units</td>

 </tr>
 
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Definition</td>

    <td class="OraFieldText" width='30%'>Measurement units for each treatment agent (i.e., drug, antibody, etc.) </td>

    <td class="OraFieldText" width='30%'>the measurement units for each treatment agent (i.e., drug, antibody, etc.). </td>
    
    <td class="OraFieldText" width='30%'>the measurement units for each treatment agent (i.e., drug, antibody, etc.). </td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Owned by Context</td>

    <td class="OraFieldText" width='30%'>CTEP</td>

    <td class="OraFieldText" width='30%'>CTEP</td>
    
    <td class="OraFieldText" width='30%'>CTEP</td>

 </tr>
 
  <tr class="OraTabledata">
     <td class="OraTableColumnHeader" width="10%">Used by Context</td>
 
     <td class="OraFieldText" width='30%'>CCR</td>
 
     <td class="OraFieldText" width='30%'>CCR</td>
     
     <td class="OraFieldText" width='30%'>CCR</td>
 
 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Origin</td>

    <td class="OraFieldText" width='30%'></td>

    <td class="OraFieldText" width='30%'>Data element and all values comply with Clinical Data Update System (CDUS) v3.0 Release 2</td>

    <td class="OraFieldText" width='30%'>Data element and all values comply with Clinical Data Update System (CDUS) v3.0 Release 2</td>
 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Workflow Status</td>

    <td class="OraFieldText" width='30%'>RETIRED ARCHIVED</td>

    <td class="OraFieldText" width='30%'>RELEASED</td>
    
    <td class="OraFieldText" width='30%'>RELEASED</td>

 </tr>
 
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Registration Status</td>

    <td class="OraFieldText" width='30%'></td>

    <td class="OraFieldText" width='30%'>Qualified </td>
    
    <td class="OraFieldText" width='30%'>Qualified </td>

 </tr>
  
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Preferred Name</td>

    <td class="OraFieldText" width='30%'>AGENT_DOSE_UNITS(GYN)</td>

    <td class="OraFieldText" width='30%'>AGT_DOSE_UOM</td>
    
    <td class="OraFieldText" width='30%'>AGT_DOSE_UOM</td>
    
 </tr>


 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Version</td>

    <td class="OraFieldText" width='30%'>2.31</td>

    <td class="OraFieldText" width='30%'>3.0</td>
    
    <td class="OraFieldText" width='30%'>3.0</td>

 </tr>

</table>

<br>
<A NAME="dataElementConcept"></A>

<table cellpadding="0" cellspacing="0" width="140%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Data Element Concept</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
  </tr>  
</table>

<table width="140%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Public ID</td>

    <td class="OraFieldText" width='30%'>2008656</td>

    <td class="OraFieldText" width='30%'>2014029</td>
    
    <td class="OraFieldText" width='30%'>2014029</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Long Name</td>

    <td class="OraFieldText" width='30%'>AGENT DOSE UNITS</td>

    <td class="OraFieldText" width='30%'>Agent Quantity</td>
    
    <td class="OraFieldText" width='30%'>Agent Quantity</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Definition</td>

    <td class="OraFieldText" width='30%'>AGENT_DOSE_UNITS</td>

    <td class="OraFieldText" width='30%'>the amount of the agent or drug.</td>
    
    <td class="OraFieldText" width='30%'>the amount of the agent or drug.</td>

  </tr>
  
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Context</td>

    <td class="OraFieldText" width='30%'>CTEP</td>

    <td class="OraFieldText" width='30%'>CTEP</td>
    
    <td class="OraFieldText" width='30%'>CTEP</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Conceptual Domain Preferred Name</td>

    <td class="OraFieldText" width='30%'>CTEP</td>

    <td class="OraFieldText" width='30%'>TX_DOSES</td>
    
    <td class="OraFieldText" width='30%'>TX_DOSES</td>

 </tr>

 <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Object Class Preferred Name</td>
 
     <td class="OraFieldText" width='30%'>&nbsp;</td>
 
     <td class="OraFieldText" width='30%'>Agent</td>
     
     <td class="OraFieldText" width='30%'>Agent</td>
 
 </tr> 

  <tr class="OraTabledata">
        <td class="OraTableColumnHeader">Property Preferred Name</td>
    
        <td class="OraFieldText" width='30%'>Quantity</td>
    
        <td class="OraFieldText" width='30%'> </td>
        
        <td class="OraFieldText" width='30%'> </td>
    
 </tr> 

 <tr class="OraTabledata">
            <td class="OraTableColumnHeader">Origin</td>
        
            <td class="OraFieldText" width='30%'> </td>
        
            <td class="OraFieldText" width='30%'> </td>
            
            <td class="OraFieldText" width='30%'> </td>
        
 </tr> 

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Workflow Status</td>

    <td class="OraFieldText" width='30%'>DRAFT NEW</td>

    <td class="OraFieldText" width='30%'>RELEASED</td>
    
    <td class="OraFieldText" width='30%'>RELEASED</td>

 </tr>
 
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width="10%">Preferred Name</td>

    <td class="OraFieldText" width='30%'>AGENT_DOSE_UNITS</td>

    <td class="OraFieldText" width='30%'>AGT_QTY</td>
    
    <td class="OraFieldText" width='30%'>AGT_QTY</td>

  </tr>

</table>

<br>
<A NAME="valueDomain"></A>

<table cellpadding="0" cellspacing="0" width="140%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Value Domain</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
  </tr>  
</table>

<table width="140%" align="center" cellpadding="1" cellspacing="1" class="OraBGAccentVeryDark">
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader" width='10%'>Public ID</td>

    <td class="OraFieldText" width='30%'>2015315</td>

    <td class="OraFieldText" width='30%'>2015211</td>
    
    <td class="OraFieldText" width='30%'>2015211</td>

 </tr>
 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Long Name</td>

    <td class="OraFieldText" width='30%'>AGENT_DOSE_UNITS(GYN)_VD </td>

    <td class="OraFieldText" width='30%'>Agent Dose UOM</td>
    
    <td class="OraFieldText" width='30%'>Agent Dose UOM</td>

 </tr>

  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Definition</td>

     <td class="OraFieldText" width='30%'>AGENT_DOSE_UNITS(GYN)_VD</td>

     <td class="OraFieldText" width='30%'>the units in which the agent or drug is measured.</td>
     
     <td class="OraFieldText" width='30%'>the units in which the agent or drug is measured.</td>

 </tr>

  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Datatype</td>

     <td class="OraFieldText" width='30%'>Character</td>

     <td class="OraFieldText" width='30%'>Character</td>
     
     <td class="OraFieldText" width='30%'>Character</td>

 </tr>

  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Unit of Measure</td>

     <td class="OraFieldText" width='30%'></td>

     <td class="OraFieldText" width='30%'></td>
     
     <td class="OraFieldText" width='30%'></td>

 </tr>

  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Display Format</td>

     <td class="OraFieldText" width='30%'></td>

     <td class="OraFieldText" width='30%'></td>
     
     <td class="OraFieldText" width='30%'></td>

 </tr>

  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Maximum Length</td>

     <td class="OraFieldText" width='30%'>10</td>

     <td class="OraFieldText" width='30%'>15</td>
     
     <td class="OraFieldText" width='30%'>15</td>

 </tr>
  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Minimum Length</td>

     <td class="OraFieldText" width='30%'></td>

     <td class="OraFieldText" width='30%'></td>
     
     <td class="OraFieldText" width='30%'></td>

 </tr>

   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Decimal Place</td>

      <td class="OraFieldText" width='30%'></td>

      <td class="OraFieldText" width='30%'></td>
      
      <td class="OraFieldText" width='30%'></td>

  </tr>
   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">High Value</td>

      <td class="OraFieldText" width='30%'></td>

      <td class="OraFieldText" width='30%'></td>
      
      <td class="OraFieldText" width='30%'></td>

  </tr>
   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Low Value</td>

      <td class="OraFieldText" width='30%'>Character</td>

      <td class="OraFieldText" width='30%'>Character</td>
      
      <td class="OraFieldText" width='30%'>Character</td>

  </tr>
   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Value Domain Type</td>

      <td class="OraFieldText" width='30%'>Enumerated</td>

      <td class="OraFieldText" width='30%'>Enumerated</td>
      
      <td class="OraFieldText" width='30%'>Enumerated</td>

  </tr>
   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Conceptual Domain Preferred Name</td>

      <td class="OraFieldText" width='30%'>CTEP</td>

      <td class="OraFieldText" width='30%'>UOM</td>
      
      <td class="OraFieldText" width='30%'>UOM</td>

  </tr>

   <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Representation</td>

      <td class="OraFieldText" width='30%'>&nbsp;</td>

      <td class="OraFieldText" width='30%'>&nbsp;</td>
      
      <td class="OraFieldText" width='30%'>&nbsp;</td>

  </tr>  

 <tr class="OraTabledata">
      <td class="OraTableColumnHeader">Origin</td>

      <td class="OraFieldText" width='30%'></td>

      <td class="OraFieldText" width='30%'></td>
      
      <td class="OraFieldText" width='30%'></td>

 </tr>
 
  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Workflow Status</td>

     <td class="OraFieldText" width='30%'>Draft New</td>

     <td class="OraFieldText" width='30%'>RELEASED</td>

    <td class="OraFieldText" width='30%'>RELEASED</td>

 </tr>

 <tr class="OraTabledata">
    <td class="OraTableColumnHeader">Preferred Name</td>

    <td class="OraFieldText" width='30%'>AGENT_DOSE_UNITS(GYN)_VD </td>

    <td class="OraFieldText" width='30%'>AGT_UOM</td>
    
    <td class="OraFieldText" width='30%'>AGT_UOM</td>

 </tr>
 
  <tr class="OraTabledata">
     <td class="OraTableColumnHeader">Version</td>

     <td class="OraFieldText" width='30%'>2.31</td>

     <td class="OraFieldText" width='30%'>3.0</td>
     
     <td class="OraFieldText" width='30%'>3.0</td>

 </tr>


</table>

<br>
<A NAME="permissibleValues"></A>

<table cellpadding="0" cellspacing="0" width="140%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Permissible Values</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
  </tr>  
</table>

<TABLE cellSpacing=0 cellPadding=0 width="140%" align=center border=0>
   <TR >
          <TD width="10%" class="PrinterOraFieldText" >&nbsp;</TD>
          <TD width="30%" class="PrinterOraFieldText" >20 Permissible values</TD>
          <TD width="30%" class="PrinterOraFieldText" >20 Permissible values</TD>
          <TD width="30%" class="PrinterOraFieldText" >20 Permissible values</TD>
         
   </TR>
</TABLE>

<TABLE cellSpacing=1 cellPadding=1 width="140%" align=center border=0>
  <TBODY>
  <TR>
    <TD vAlign=top width=10%>
      &nbsp;
    </TD>
    <TD vAlign=top width=30%>
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>value</TH>
          <TH class=OraTableColumnHeader>value meaning</TH>
          <TH class=OraTableColumnHeader>Description</TH>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Ci</TD>
          <TD class=OraFieldText>CURIE</TD>
          <TD class=OraFieldText>CURIE</TD>
        </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Eq</TD>
          <TD class=OraFieldText>GRAM-EQUIVALENT WEIGHT</TD>
          <TD class=OraFieldText>GRAM-EQUIVALENT WEIGHT</TD>
       </TR>    
        <TR class=OraTabledata>
          <TD class=OraFieldText>Gy</TD>
          <TD class=OraFieldText>GRAYS</TD>
          <TD class=OraFieldText>GRAYS</TD>
       </TR>  
        <TR class=OraTabledata>
          <TD class=OraFieldText>Hz</TD>
          <TD class=OraFieldText>HERTZ</TD>
          <TD class=OraFieldText>HERTZ</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>IU</TD>
          <TD class=OraFieldText>INTERNATIONAL UNIT</TD>
          <TD class=OraFieldText>INTERNATIONAL UNIT</TD>
         </TR>
         <TR class=OraTabledata>
          <TD class=OraFieldText>JCM2</TD>
          <TD class=OraFieldText>IJOULES PER CENTIMETER SQUARE </TD>
          <TD class=OraFieldText>JOULES PER CENTIMETER SQUARE </TD>
         </TR> 
        <TR class=OraTabledata>
          <TD class=OraFieldText>L</TD>
          <TD class=OraFieldText>LITER</TD>
          <TD class=OraFieldText>LITER</TD>
         </TR>   
        <TR class=OraTabledata>
          <TD class=OraFieldText>MHz</TD>
          <TD class=OraFieldText>MEGAHERTZ</TD>
          <TD class=OraFieldText>MEGAHERTZ</TD>
         </TR>   
        <TR class=OraTabledata>
          <TD class=OraFieldText>MMM</TD>
          <TD class=OraFieldText>MILLIGRAMS PER MILLILITER PER MINUTE </TD>
          <TD class=OraFieldText>MILLIGRAMS PER MILLILITER PER MINUTE </TD>
         </TR>   
        <TR class=OraTabledata>
          <TD class=OraFieldText>MeV</TD>
          <TD class=OraFieldText>MILLION ELECTRON VOLTS</TD>
          <TD class=OraFieldText>MILLION ELECTRON VOLTS</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Mrad</TD>
          <TD class=OraFieldText>MEGARAD</TD>
          <TD class=OraFieldText>MEGARAD</TD>
         </TR>  
        <TR class=OraTabledata>
          <TD class=OraFieldText>N/A</TD>
          <TD class=OraFieldText>NOT APPLICABLE</TD>
          <TD class=OraFieldText>NOT APPLICABLE</TD>
         </TR>  
        <TR class=OraTabledata>
          <TD class=OraFieldText>Osmol</TD>
          <TD class=OraFieldText>OSMOLE</TD>
          <TD class=OraFieldText>OSMOLE</TD>
         </TR>   
        <TR class=OraTabledata>
          <TD class=OraFieldText>Pa</TD>
          <TD class=OraFieldText>PASCAL</TD>
          <TD class=OraFieldText>PASCAL</TD>
         </TR>  
        <TR class=OraTabledata>
          <TD class=OraFieldText>VP</TD>
          <TD class=OraFieldText>VIRAL PARTICLES</TD>
          <TD class=OraFieldText>VIRAL PARTICLES</TD>
         </TR>    
        <TR class=OraTabledata>
          <TD class=OraFieldText>cGy</TD>
          <TD class=OraFieldText>CENTIGRAYS</TD>
          <TD class=OraFieldText>CENTIGRAYS</TD>
         </TR>   
        <TR class=OraTabledata>
          <TD class=OraFieldText>cm</TD>
          <TD class=OraFieldText>CENTIMETER</TD>
          <TD class=OraFieldText>CENTIMETER</TD>
         </TR>   
        <TR class=OraTabledata>
          <TD class=OraFieldText>dL</TD>
          <TD class=OraFieldText>DECILITER</TD>
          <TD class=OraFieldText>DECILITER</TD>
         </TR> 
        <TR class=OraTabledata>
          <TD class=OraFieldText>dm</TD>
          <TD class=OraFieldText>DECIMETER</TD>
          <TD class=OraFieldText>DECIMETER</TD>
         </TR>   
        <TR class=OraTabledata>
          <TD class=OraFieldText>g</TD>
          <TD class=OraFieldText>GRAM</TD>
          <TD class=OraFieldText>GRAM</TD>
         </TR>          
    </TBODY></TABLE>
    </TD>
    <TD vAlign=top width="30%">
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>value</TH>
          <TH class=OraTableColumnHeader>value meaning</TH>
          <TH class=OraTableColumnHeader>Description</TH>
         </TR>

        <TR class=OraTabledata>
          <TD class=OraFieldText>J/cm^2</TD>
          <TD class=OraFieldText>JOULES PER CENTIMETER SQUARE</TD>
          <TD class=OraFieldText>JOULES PER CENTIMETER SQUARE</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Ci</TD>
          <TD class=OraFieldText>CURIE</TD>
          <TD class=OraFieldText>CURIE</TD>
        </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Eq</TD>
          <TD class=OraFieldText>GRAM-EQUIVALENT WEIGHT</TD>
          <TD class=OraFieldText>GRAM-EQUIVALENT WEIGHT</TD>
       </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Hz</TD>
          <TD class=OraFieldText>HERTZ</TD>
          <TD class=OraFieldText>HERTZ</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>IU</TD>
          <TD class=OraFieldText>INTERNATIONAL UNIT</TD>
          <TD class=OraFieldText>INTERNATIONAL UNIT</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>L</TD>
          <TD class=OraFieldText>LITER</TD>
          <TD class=OraFieldText>LITER</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>MeV</TD>
          <TD class=OraFieldText>MILLION ELECTRON VOLTS</TD>
          <TD class=OraFieldText>MILLION ELECTRON VOLTS</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Mrad</TD>
          <TD class=OraFieldText>MEGARAD </TD>
          <TD class=OraFieldText>MEGARAD</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>N/A</TD>
          <TD class=OraFieldText>NOT APPLICABLE</TD>
          <TD class=OraFieldText>NOT APPLICABLE </TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Osmol</TD>
          <TD class=OraFieldText>OSMOLE</TD>
          <TD class=OraFieldText>OSMOLE</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Pa</TD>
          <TD class=OraFieldText>PASCAL</TD>
          <TD class=OraFieldText>PASCAL</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>VP</TD>
          <TD class=OraFieldText>VIRAL PARTICLES</TD>
          <TD class=OraFieldText>VIRAL PARTICLES </TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>cm</TD>
          <TD class=OraFieldText>CENTIMETER</TD>
          <TD class=OraFieldText>CENTIMETER</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>dL</TD>
          <TD class=OraFieldText>DECILITER</TD>
          <TD class=OraFieldText>DECILITER</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>dm</TD>
          <TD class=OraFieldText>DECIMETER</TD>
          <TD class=OraFieldText>DECIMETER</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>g</TD>
          <TD class=OraFieldText>GRAM</TD>
          <TD class=OraFieldText>GRAM</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>gravity</TD>
          <TD class=OraFieldText>GRAVITY (IN CENTRIFUGATION) </TD>
          <TD class=OraFieldText>GRAVITY (IN CENTRIFUGATION) </TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>kHz</TD>
          <TD class=OraFieldText>KILOHERTZ</TD>
          <TD class=OraFieldText>KILOHERTZ </TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>kPa</TD>
          <TD class=OraFieldText>KILOPASCAL</TD>
          <TD class=OraFieldText>KILOPASCAL</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>keV</TD>
          <TD class=OraFieldText>KILO-ELECTRON VOLT </TD>
          <TD class=OraFieldText>KILO-ELECTRON VOLT </TD>
         </TR>
       </TBODY></TABLE></TD>

    <TD vAlign=top width="30%">
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>value</TH>
          <TH class=OraTableColumnHeader>value meaning</TH>
          <TH class=OraTableColumnHeader>Description</TH>
         </TR>

        <TR class=OraTabledata>
          <TD class=OraFieldText>J/cm^2</TD>
          <TD class=OraFieldText>JOULES PER CENTIMETER SQUARE</TD>
          <TD class=OraFieldText>JOULES PER CENTIMETER SQUARE</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Ci</TD>
          <TD class=OraFieldText>CURIE</TD>
          <TD class=OraFieldText>CURIE</TD>
        </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Eq</TD>
          <TD class=OraFieldText>GRAM-EQUIVALENT WEIGHT</TD>
          <TD class=OraFieldText>GRAM-EQUIVALENT WEIGHT</TD>
       </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Hz</TD>
          <TD class=OraFieldText>HERTZ</TD>
          <TD class=OraFieldText>HERTZ</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>IU</TD>
          <TD class=OraFieldText>INTERNATIONAL UNIT</TD>
          <TD class=OraFieldText>INTERNATIONAL UNIT</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>L</TD>
          <TD class=OraFieldText>LITER</TD>
          <TD class=OraFieldText>LITER</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>MeV</TD>
          <TD class=OraFieldText>MILLION ELECTRON VOLTS</TD>
          <TD class=OraFieldText>MILLION ELECTRON VOLTS</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Mrad</TD>
          <TD class=OraFieldText>MEGARAD </TD>
          <TD class=OraFieldText>MEGARAD</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>N/A</TD>
          <TD class=OraFieldText>NOT APPLICABLE</TD>
          <TD class=OraFieldText>NOT APPLICABLE </TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Osmol</TD>
          <TD class=OraFieldText>OSMOLE</TD>
          <TD class=OraFieldText>OSMOLE</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>Pa</TD>
          <TD class=OraFieldText>PASCAL</TD>
          <TD class=OraFieldText>PASCAL</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>VP</TD>
          <TD class=OraFieldText>VIRAL PARTICLES</TD>
          <TD class=OraFieldText>VIRAL PARTICLES </TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>cm</TD>
          <TD class=OraFieldText>CENTIMETER</TD>
          <TD class=OraFieldText>CENTIMETER</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>dL</TD>
          <TD class=OraFieldText>DECILITER</TD>
          <TD class=OraFieldText>DECILITER</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>dm</TD>
          <TD class=OraFieldText>DECIMETER</TD>
          <TD class=OraFieldText>DECIMETER</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>g</TD>
          <TD class=OraFieldText>GRAM</TD>
          <TD class=OraFieldText>GRAM</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>gravity</TD>
          <TD class=OraFieldText>GRAVITY (IN CENTRIFUGATION) </TD>
          <TD class=OraFieldText>GRAVITY (IN CENTRIFUGATION) </TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>kHz</TD>
          <TD class=OraFieldText>KILOHERTZ</TD>
          <TD class=OraFieldText>KILOHERTZ </TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>kPa</TD>
          <TD class=OraFieldText>KILOPASCAL</TD>
          <TD class=OraFieldText>KILOPASCAL</TD>
         </TR>
        <TR class=OraTabledata>
          <TD class=OraFieldText>keV</TD>
          <TD class=OraFieldText>KILO-ELECTRON VOLT </TD>
          <TD class=OraFieldText>KILO-ELECTRON VOLT </TD>
         </TR>
       </TBODY></TABLE></TD>       
       
       </TR></TBODY></TABLE>
       

<A NAME="referenceDocuments"></A> 
<br>
<table cellpadding="0" cellspacing="0" width="140%" align="center" >
  <tr>
    <td class="OraHeaderSubSub" width="100%">Reference Documents</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
  </tr>
</table>

<TABLE cellSpacing=1 cellPadding=1 width="140%" align=center border=0>
  <TBODY>
  <TR>
    <TD vAlign=top width=10%>
      &nbsp;
    </TD>

    <TD vAlign=top width="30%">

<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Document Name</th>
    <th class="OraTableColumnHeader">Document Type</th>
    <th class="OraTableColumnHeader">Document Text</th>
  </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">CRF Text </td>
        <td class="OraFieldText">LONG_NAME </td>
        <td class="OraFieldText">Units </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">CRF Text </td>
        <td class="OraFieldText">HISTORIC SHORT CDE NAME </td>
        <td class="OraFieldText">Agent Dose Units </td>
      </tr>

</table>

    </TD>
    
    <TD vAlign=top width="30%">
    
<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Document Name</th>
    <th class="OraTableColumnHeader">Document Type</th>
    <th class="OraTableColumnHeader">Document Text</th>
  </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">CRF Text </td>
        <td class="OraFieldText">HISTORIC SHORT CDE NAME </td>
        <td class="OraFieldText">Agent Dose Units </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">CRF Text </td>
        <td class="OraFieldText">LONG_NAME </td>
        <td class="OraFieldText">Units </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">Source </td>
        <td class="OraFieldText">DATA_ELEMENT_SOURCE </td>
        <td class="OraFieldText">Data element and all values comply with Clinical Data Update System (CDUS) v3.0 Release 2 </td>
      </tr>

</table>
    
    </TD>
    
    <TD vAlign=top width="30%">

<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  <tr class="OraTableColumnHeader">
    <th class="OraTableColumnHeader">Document Name</th>
    <th class="OraTableColumnHeader">Document Type</th>
    <th class="OraTableColumnHeader">Document Text</th>
  </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">CRF Text </td>
        <td class="OraFieldText">LONG_NAME </td>
        <td class="OraFieldText">Units </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">CRF Text </td>
        <td class="OraFieldText">HISTORIC SHORT CDE NAME </td>
        <td class="OraFieldText">Agent Dose Units </td>
      </tr>

</table>

    </TD>
    
    </TR></TBODY></TABLE>

<br> 
<A NAME="classifications"></A>

<table cellpadding="0" cellspacing="0" width="140%" align="center">
  <tr>
    <td class="OraHeaderSubSub" width="100%">Classifications</td>
  </tr>
  <tr>
    <td width="100%"><img height=1 src="i/beigedot.gif" width="99%" align=top border=1> </td>
  </tr>  
</table>
<TABLE cellSpacing=1 cellPadding=1 width="140%" align=center border=0>
  <TBODY>
  <TR>
      <TD vAlign=top width=10%>
        &nbsp;
    </TD>
    
    <TD vAlign=top width="30%">
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>CS* Preferred Name</TH>
          <TH class=OraTableColumnHeader>CS* Definition</TH>
          <TH class=OraTableColumnHeader>CSI* Name</TH>
          <TH class=OraTableColumnHeader>CSI* Type</TH>
         </TR>

      <tr class="OraTabledata">
        <td class="OraFieldText">CATEGORY </td>
        <td class="OraFieldText">Type of Category </td>
        <td class="OraFieldText">Treatment </td>
        <td class="OraFieldText">CATEGORY_TYPE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">DISEASE </td>
        <td class="OraFieldText">Type of Disease </td>
        <td class="OraFieldText">Gynecologic </td>
        <td class="OraFieldText">DISEASE_TYPE </td>
      </tr>
      
      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Primary Cervical </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Primary Endometrial </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Primary Ovarian </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>


      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Recurrent Gyn </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>
      <tr class="OraTabledata">
        <td class="OraFieldText">USAGE </td>
        <td class="OraFieldText">Type of Usage </td>
        <td class="OraFieldText">CLINICAL TRIALS </td>
        <td class="OraFieldText">USAGE_TYPE </td>
      </tr>
      
    </TBODY></TABLE>
    </TD>
    <TD vAlign=top width="30%" >
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>CS* Preferred Name</TH>
          <TH class=OraTableColumnHeader>CS* Definition</TH>
          <TH class=OraTableColumnHeader>CSI* Name</TH>
          <TH class=OraTableColumnHeader>CSI* Type</TH>
         </TR>


      <tr class="OraTabledata">
        <td class="OraFieldText">CATEGORY </td>
        <td class="OraFieldText">Type of Category </td>
        <td class="OraFieldText">Protocol/Admin. </td>
        <td class="OraFieldText">CATEGORY_TYPE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">CATEGORY </td>
        <td class="OraFieldText">Type of Category </td>
        <td class="OraFieldText">Treatment </td>
        <td class="OraFieldText">CATEGORY_TYPE </td>
      </tr>


      <tr class="OraTabledata">
        <td class="OraFieldText">CRF_DISEASE </td>
        <td class="OraFieldText">this scheme is used to classify by disease case report forms and their associated data elements.  Assignments to this scheme should not be made manually but occur through use of the CRF Loader and the CRT. </td>
        <td class="OraFieldText">Colorectal </td>
        <td class="OraFieldText">DISEASE_TYPE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">ALL Prev Untreated </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">AML Prev Untreated </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">APL Prev Untreated </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Adjuvant Esophageal </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Adjuvant Gastric </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Advanced Esophageal </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Advanced Gastric </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">CLL Prev Untreated </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">CML Prev Untreated </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">HCL Prev Untreated </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">MDS Prev Untreated </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">NSCLC 2nd Line </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Primary Cervical </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Primary Endometrial </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Primary Ovarian </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">SCLC 2nd Line </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">USAGE </td>
        <td class="OraFieldText">Type of Usage </td>
        <td class="OraFieldText">CLINICAL TRIALS </td>
        <td class="OraFieldText">USAGE_TYPE </td>
      </tr>    
    
    </TBODY></TABLE>
    </TD>
    
    <TD vAlign=top width="30%">
      <TABLE class=OraBGAccentVeryDark cellSpacing=1 cellPadding=1 width="100%"
      align=center border=0>
        <TBODY>
        
        <TR class=OraTableColumnHeader>
          <TH class=OraTableColumnHeader>CS* Preferred Name</TH>
          <TH class=OraTableColumnHeader>CS* Definition</TH>
          <TH class=OraTableColumnHeader>CSI* Name</TH>
          <TH class=OraTableColumnHeader>CSI* Type</TH>
         </TR>

      <tr class="OraTabledata">
        <td class="OraFieldText">CATEGORY </td>
        <td class="OraFieldText">Type of Category </td>
        <td class="OraFieldText">Treatment </td>
        <td class="OraFieldText">CATEGORY_TYPE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">DISEASE </td>
        <td class="OraFieldText">Type of Disease </td>
        <td class="OraFieldText">Gynecologic </td>
        <td class="OraFieldText">DISEASE_TYPE </td>
      </tr>
      
      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Primary Cervical </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Primary Endometrial </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>

      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Primary Ovarian </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>


      <tr class="OraTabledata">
        <td class="OraFieldText">TTU </td>
        <td class="OraFieldText">Trial Type Usages (CDE Disease Committees) </td>
        <td class="OraFieldText">Recurrent Gyn </td>
        <td class="OraFieldText">TRIAL_TYPE_USAGE </td>
      </tr>
      <tr class="OraTabledata">
        <td class="OraFieldText">USAGE </td>
        <td class="OraFieldText">Type of Usage </td>
        <td class="OraFieldText">CLINICAL TRIALS </td>
        <td class="OraFieldText">USAGE_TYPE </td>
      </tr>
      
    </TBODY></TABLE>
    </TD>
    
    </TR>
    
    </TBODY></TABLE>

<table cellpadding="0" cellspacing="0" width="140%" align="center">

  <tr>
    <td width="100%"><img height=2 src="i/beigedot.gif" width="99%" align=top border=1> </td>
  </tr>  
</table> 
<table width=100% Cellpadding=0 Cellspacing=0 border=0>
    <tr>
      <td align="center" >
        <img src="/cdebrowser/i/backButton.gif" >
      </td>
    </tr>
</table>


<TABLE width=140% cellspacing=0 cellpadding=0 border=0>
<TR>
<TD valign=bottom width=99%><img src="/cdebrowser/i/bottom_shade.gif" height="6" width="100%"></TD>
<TD valign=bottom width="1%" align=right><img src="/cdebrowser/i/bottomblueright.gif"></TD>
</TR>
</TABLE>
<TABLE width=140% cellspacing=0 cellpadding=0 bgcolor="#336699" border=0>
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