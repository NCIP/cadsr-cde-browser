<logic:messagesPresent >
  <table width="100%" align="center">
    <html:messages id="error" >
      <tr align="center" >
        <td  align="left" class="OraErrorText" >
          <b><bean:write  name="error"/></b><br>
        </td>
      </tr>
    </html:messages>           
  </table>
</logic:messagesPresent>  
<logic:messagesPresent message="true">
  <table width="100%" align="center">
    <html:messages id="message" 
      message="true">
      <tr align="center" >
        <td  align="left" class="MessageText" >
          <b><bean:write  name="message"/></b><br>
        </td>
      </tr>
    </html:messages>      
  </table>
</logic:messagesPresent>  

