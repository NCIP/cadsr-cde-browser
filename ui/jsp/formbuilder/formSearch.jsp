<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<HTML>
<HEAD>
<TITLE>Welcome to Form Builder..</TITLE>
</HEAD>
<BODY bgcolor="#ffffff">
<CENTER>
  <H4><FONT color="#993333" size="6">Form Serach  </FONT></H4>
  <table cellspacing="2" cellpadding="3" border="0">
    <tr>
      <td><a href="/cdebrowser/logout">Logout</a></td>
    </tr>
  </table>
</CENTER>
<P>&nbsp;</P>
<html:form action="/formAction.do">
  <table cellspacing="2" cellpadding="3" border="0">
    <tr>
      <td>FormName</td>
      <td>
        <html:text property="formName"></html:text>           
      </td>
    </tr>
    <tr>
      <td>Protocol</td>
      <td>
        <html:text property="protocol"></html:text>          
      </td>
    </tr>
    <tr>
      <td>Context</td>
      <td>
        <html:text property="context"></html:text>           
      </td>
    </tr>
    <tr>
      <td>Workflow</td>
      <td>
        <html:text property="workflow"></html:text>           
      </td>
    </tr>
    <tr>
      <td>Category</td>
      <td>
        <html:text property="category"></html:text>          
      </td>
    </tr>    
    <tr>
      <td>Type</td>
      <td>      
        <html:text property="type"></html:text>             
      </td>
    </tr>      
    <tr>
      <td>
        <html:hidden value="getAllForms" property="method"/>
      </td>
      <td>
        <html:submit></html:submit>
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>

      </td>
    </tr>
  </table>
  <P> <logic:present name="FormSearchResults">
        <logic:iterate id="form" name="FormSearchResults" type="gov.nih.nci.ncicb.cadsr.resource.Form">
          <html:link page="/formAction.do" paramName="form" paramProperty="preferredName">
            <bean:write name="form" property="preferredName"/><br>
          </html:link>
      
        </logic:iterate>
        </logic:present></P>
  <P>
    
  </P>
</html:form>
<P>&nbsp;</P>
<P>&nbsp;</P>

</BODY>
</HTML>
