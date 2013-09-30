<%--L
  Copyright SAIC-F Inc.

  Distributed under the OSI-approved BSD 3-Clause License.
  See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.

  Portions of this source file not modified since 2008 are covered by:

  Copyright 2000-2008 Oracle, Inc.

  Distributed under the caBIG Software License.  For details see
  http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
L--%>

<logic:messagesPresent >
  <table width="100%" align="center">
    <html:messages id="error" >
      <logic:present name="error">
      <tr align="center" >
        <td  align="left" class="OraErrorText" >         
          <b><bean:write  name="error"/></b><br>
        </td>
      </tr>
      </logic:present>      
    </html:messages>           
  </table>
</logic:messagesPresent>  
<logic:messagesPresent message="true">
  <table width="100%" align="center">
    <html:messages id="message" 
      message="true">
      <logic:present name="message">
      <tr align="center" >
        <td  align="left" class="MessageText" >        
          <b><bean:write  name="message"/></b><br>
        </td>
      </tr>
     </logic:present>
    </html:messages>      
  </table>
</logic:messagesPresent>  

