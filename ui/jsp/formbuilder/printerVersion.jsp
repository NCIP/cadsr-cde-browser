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
<%@ page import="gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.*"%>
<%@ page import="java.util.*"%>
<HTML>
  <HEAD>
    <TITLE>Formbuilder: Printer friendly form</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK rel="stylesheet" TYPE="text/css" HREF="<html:rewrite page='/css/blaf.css' />">
    <SCRIPT LANGUAGE="JavaScript">

</SCRIPT>
  </HEAD>
  <body  bgcolor="#ffffff">
    <% String urlPrefix = "";

	%>
    <logic:present name="<%=FormConstants.CRF%>">
      <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentBlack">
        <tr >
          <td  class="PrinterOraTableColumnHeader" width="20%">
            <bean:message key="cadsr.formbuilder.form.longName" />
          </td>                
          <td width="80%" class="PrinterOraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="longName"/>
          </td>
        </tr>
        <tr class="PrinterOraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.form.definition" />
          </td>                
          <td width="80%"  class="PrinterOraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="preferredDefinition"/>
          </td>
        </tr>
        <tr class="PrinterOraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.form.context" />
          </td>                
          <td  class="PrinterOraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="context.name"/>
          </td>
        </tr>
        <tr class="PrinterOraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.form.protocols.longName" />
          </td>                
          <td  class="PrinterOraTableColumnHeader">
           <bean:define name="<%=FormConstants.CRF%>" property="protocols" id="protocols"/>
            <%=FormJspUtil.getDelimitedProtocolLongNames((List)protocols,  "<br/>")%>                
          </td>
        </tr>  
        <tr class="PrinterOraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.form.workflow"/>
          </td>                
          <td  class="PrinterOraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="aslName"/>
          </td>
        </tr>  
        <tr class="PrinterOraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.form.category"/>
          </td>                
          <td  class="PrinterOraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="formCategory"/>
          </td>
        </tr>  
        <tr class="PrinterOraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.form.type"/>
          </td>                
          <td  class="PrinterOraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="formType"/>
          </td>
         </tr>
        <tr class="PrinterOraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.form.publicID"/>
          </td>                
          <td  class="PrinterOraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="publicId"/>
          </td>
         </tr>          
        <tr class="PrinterOraTableColumnHeader">
          <td  width="20%">
            <bean:message key="cadsr.formbuilder.question.version"/>
          </td>                
          <td  class="PrinterOraTableColumnHeader">
            <bean:write name="<%=FormConstants.CRF%>" property="version"/>
          </td>
         </tr>         
        <logic:present name="<%=FormConstants.CRF%>" property="instruction">
          <tr class="PrinterOraTableColumnHeader">
            <td   width="20%">
              <bean:message key="cadsr.formbuilder.form.header.instruction"/>
            </td>                
            <td  class="PrinterOraTableColumnHeader">
              <bean:write  name="<%=FormConstants.CRF%>" property="instruction.longName"/>
            </td>
          </tr>
        </logic:present>
        <logic:present name="<%=FormConstants.CRF%>" property="footerInstruction">     
          <tr class="PrinterOraTableColumnHeader">
            <td class="PrinterOraTableColumnHeader"  width="20%">
              <bean:message key="cadsr.formbuilder.form.footer.instruction"/>
            </td>                
            <td  class="PrinterOraTableColumnHeader">
              <bean:write  name="<%=FormConstants.CRF%>" property="footerInstruction.longName"/>
            </td>
          </tr> 
        </logic:present>              
      </table>
      <table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        <tr >
          <td >
		&nbsp;
          </td>
        </tr>
      </table>      
            <logic:notEmpty name="<%=FormConstants.CRF%>" property = "modules">
              <logic:iterate id="module" name="<%=FormConstants.CRF%>" type="gov.nih.nci.ncicb.cadsr.resource.Module" property="modules">
                <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentBlack">               
                 <tr class="PrinterOraTableColumnHeader">                 
                    <td class="PrinterOraTableColumnHeader">
                      <bean:write name="module" property="longName"/>
                    </td>
                  </tr>
                   <logic:present name="module" property="instruction">                   
                      <tr class="PrinterOraTabledata" >  
                       <td colspan="2">
                           <table width="100%" align="center" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentBlack" >
                             <tr class="PrinterOraTabledata">
                              <td class="PrinterOraTableColumnHeader" width="10%" nowrap>
                                <bean:message key="cadsr.formbuilder.form.instruction"/> 
                             </td>
                             <td class="PrinterOraTabledata">
                               <bean:write  name="module" property="instruction.longName"/>
                             </td>
                            </tr>
                           </table>
                       </td>
                      </tr>
                   </logic:present>                    
                  <logic:present name="module">
                  <logic:notEmpty name="module" property = "questions">
                    <tr class="PrinterOraTabledata">
                      <td>
                        <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" >      
                          <logic:iterate id="question" name="module" type="gov.nih.nci.ncicb.cadsr.resource.Question" property="questions">                           
                            <tr class="PrinterOraTabledata">
                              <td class="PrinterOraFieldText" width="50">&nbsp;</td>
                              <td class="PrinterOraFieldText" height="1"  >                               
                              </td>
                            </tr>                              
                            <tr class="PrinterOraTabledata">
                              <td class="PrinterOraFieldText" width="50">&nbsp;</td>
                              <td class="PrinterUnderlineOraFieldText">
                                <bean:write name="question" property="longName"/>
                              </td>
                              <logic:present name="question" property = "dataElement">
                                <td align="center" width="70" class="PrinterUnderlineOraFieldText" >
				                          <bean:write name="question" property="dataElement.CDEId"/>
	    			                    </td>
                              <td align="center" width="70" class="PrinterUnderlineOraFieldText">
                                	<bean:write name="question" property="dataElement.version"/>
                              </td>                                
                              </logic:present>
                              <logic:notPresent name="question" property="dataElement">
                                  <td align="center" width="70" class="PrinterUnderlineOraFieldText">
                                   &nbsp;
                                  </td>
                                 <td align="center" width="70" class="PrinterUnderlineOraFieldText">
                                    &nbsp;
                                 </td>                              
                            </logic:notPresent>                                
                            </tr>    
                            <logic:present name="question" property="instruction">
                              <tr class="PrinterOraTableColumnHeader">
                                 <td class="PrinterOraTableColumnHeader" width="50">&nbsp;</td>
                                  <td class="PrinterOraTableColumnHeader" colspan="2">                              
                                   <table align="center" width="100%" cellpadding="0" cellspacing="1" border="0" class="OraBGAccentBlack" >
                                     <tr class="PrinterOraTabledata">
                                      <td class="PrinterOraTableColumnHeader" width="10%" nowrap>
                                        <bean:message key="cadsr.formbuilder.form.instruction"/>
                                     </td>
                                     <td class="PrinterOraFieldText">
                                       <bean:write  name="question" property="instruction.longName"/>
                                     </td>
                                    </tr>
                                   </table>                                                            
                                 </td>
                               </tr> 
                            </logic:present>                            
                            <logic:present name="question">
                            <logic:notEmpty name="question" property = "validValues">
                              <tr class="PrinterOraTabledata">
                                <td class="PrinterOraFieldText" width="50">&nbsp;</td>
                                <td>
                                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentBlack">
                                    <logic:iterate id="validValue" name="question" type="gov.nih.nci.ncicb.cadsr.resource.FormValidValue" property="validValues">
                                      <tr COLSPAN="3" class="PrinterOraTabledata">
                                        <td class="PrinterOraFieldText" width="50">&nbsp;</td>
                                        <td class="PrinterOraFieldText">
                                          <bean:write name="validValue" property="longName"/>
                                        </td>
                                      </tr>
                                      <tr   class="PrinterOraTabledata">
                                        <td class="PrinterOraFieldText" width="50">&nbsp;</td>
                                        <td >                                      
                                        <% if(question.getDataElement()!=null|| validValue.getInstruction()!=null){%>
                                          <table align="center" width="100%" cellpadding="1" cellspacing="1" border="0"  class="OraBGAccentBlack" >                          
                                            <logic:present name="question" property="dataElement">
                                               <tr class="PrinterOraTabledata" >
                                                 <td  class="PrinterOraTableColumnHeader" width="10%" nowrap >
                                                   <bean:message key="cadsr.formbuilder.valueMeaning.name" /></td>
                                                 <td class="PrinterOraFieldText" >
                                                  <bean:write name="validValue" property="shortMeaning"/></td>                                          
                                               </tr>  
                                            </logic:present>
                                              <logic:present name="validValue" property="instruction">                
                                                 <tr class="PrinterOraTabledata" >
                                                  <td class="PrinterOraTableColumnHeader" width="10%" nowrap>
                                                    <bean:message key="cadsr.formbuilder.form.instruction"/> 
                                                 </td>
                                                 <td class="PrinterOraFieldText">
                                                   <bean:write  name="validValue" property="instruction.longName"/>
                                                 </td>
                                                </tr>   
                                              </logic:present>  
                                           </table>   
                                          <%}%>    
                                        </td>
                                      </tr>                                              
                                    </logic:iterate><!-- valid Value-->
                                  </table>
                                </td>
                              </tr>
                            </logic:notEmpty>
                            </logic:present>
                          </logic:iterate><!-- Question-->
                        </table>
                      </td>
                    </tr>
                  </logic:notEmpty>
                  </logic:present>
                </table>

      		<table width="80%" align="center" cellpadding="0" cellspacing="0" border="0" >
        	   <tr class>
          	      <td >
			&nbsp;
          	      </td>
        	   </tr> 
        	</table>
                
              </logic:iterate><!-- Module-->
            </logic:notEmpty>
    </logic:present>
    <logic:notPresent name="<%=FormConstants.CRF%>">Selected form has been deleted by a diffrent user </logic:notPresent>
  </BODY>
</HTML>
