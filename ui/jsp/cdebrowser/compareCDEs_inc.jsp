

<table align="center" width=15% Cellpadding=0 Cellspacing=4 border=0>
    <tr>
        <TD valign="TOP" align="CENTER"  colspan=1>
          <a href="javascript:done()">
             <img src="../i/backButton.gif" border=0 alt="Done">
          </a>            
       <td align="left">
          <a href="javascript:removeFromCompareList()">
             <img src="../i/remove_from_cde_comparelist.gif" border=0 alt="Remove CDEs from the compare list">
          </a>              
        </td> 
       <td align="left">
          <a href="javascript:changeDisplayOrder()">
             <img src="../i/changeCompareOrder.gif" border=0 alt="Change the order in which the CDEs are compared">
          </a>          
        </td>
        <TD valign="TOP" align="CENTER"  colspan=1>
		  <html:link action='<%="/cdebrowser/CDECompareExcelDownload.do?"+NavigationConstants.METHOD_PARAM+"=downloadToExcel"%>'>
		    <img src="../i/excelDownload.gif" border=0>
		  </html:link>          
        </TD>
        
    </tr>
</table>