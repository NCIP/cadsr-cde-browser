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
<HTML>
  <HEAD>
    <TITLE>Welcome to Form Builder..</TITLE>
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
    <LINK REL="STYLESHEET" TYPE="text/css" HREF="cdebrowserCommon_html/blaf.css"/>
    <SCRIPT LANGUAGE="JavaScript">
      
    </SCRIPT>
  </HEAD>
  <BODY bgcolor="#ffffff">
    <% String urlPrefix = "";
      String pageUrl = "&PageId=DataElementsGroup";
      String protoLOVUrl= 
      "javascript:newWin('/cdebrowser/formLOVAction.do?method=getProtocolsLOV&idVar=" + FormConstants.PROTOCOLS_LOV_ID_FIELD + "&nameVar=" + FormConstants.PROTOCOLS_LOV_NAME_FIELD +pageUrl+"','protoLOV',700,600)";
      %>
    <%@ include file="/formbuilder/common_header_inc.jsp"%>
    <jsp:include page="/formbuilder/tab_inc.jsp" flush="true">
      <jsp:param name="label" value="Edit&nbsp;Form"/>
      <jsp:param name="urlPrefix" value=""/>
    </jsp:include>


    <logic:present name="<%=FormConstants.CRF%>">
      <html:form action='<%="/formToCopyAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.FORM_COPY%>'>
      <table cellspacing="2" cellpadding="3" border="0" width="100%">
        <tr>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.name" />:</td>
          <td class="OraFieldText" nowrap>
            <html:text 
              property="<%= FormConstants.FORM_LONG_NAME %>"
              size="30"
              />
          </td>

          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.context" />:</td>
          <td class="OraFieldText" nowrap>
            <bean:define id="context" scope="session" name="<%= FormConstants.CRF %>" property="<%= FormConstants.CRF_CONTEXT %>" toScope="page"/>

            <html:select styleClass="Dropdown" name="context" property="<%=FormConstants.CRF_CONTEXT_ID_SEQ%>">
              <html:options collection="<%=FormConstants.ALL_CONTEXTS%>" 
                property="conteIdseq" labelProperty="name" />
            </html:select>
          </td>
        </tr>

        <tr>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.version" />:</td>
          <td class="OraFieldText" nowrap>
            <html:text 
              property="<%= FormConstants.FORM_VERSION %>"
              size="10"
              />
          </td>

          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.workflow" />:</td>
          <td class="OraFieldText" nowrap>
            <html:select styleClass = "Dropdown" property="<%=FormConstants.WORKFLOW%>">
              <html:options name="<%=FormConstants.ALL_WORKFLOWS%>"/>
            </html:select>        
          </td>      
        </tr>
        <tr>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.type" />:</td>  
          <td class="OraFieldText" nowrap>
            <html:select styleClass="Dropdown" name="<%= FormConstants.CRF %>" property="<%=FormConstants.FORM_TYPE%>">
              <html:options name="<%=FormConstants.ALL_FORM_TYPES%>" /> 
            </html:select> 
          </td>        
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.protocol" />:</td>
          <td class="OraFieldText" nowrap>
            <html:text 
              property="<%= FormConstants.PROTOCOLS_LOV_NAME_FIELD %>"
              readonly="true" 
              size="19"
              styleClass="LOVField"
              onfocus="this.blur();"
              />
              &nbsp;<a href="<%=protoLOVUrl%>"><img src="<%=urlPrefix%>i/search_light.gif" border="0" alt="Search for Classification Scheme Items"></a>&nbsp;
            <a href="javascript:clearProtocol()"><i>Clear</i></a>
            <html:hidden  property="<%=FormConstants.PROTOCOLS_LOV_ID_FIELD%>"/>
          </td>
        </tr>


        <tr>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.category" />:</td>
          <td class="OraFieldText" nowrap>
            <html:select styleClass = "Dropdown" name="<%= FormConstants.CRF %>" property="<%=FormConstants.FORM_CATEGORY%>">
              <html:options name="<%=FormConstants.ALL_FORM_CATEGORIES%>" /> \
            </html:select> 
          </td>
        </tr>     

        <tr>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.definition" />:</td>
          <td class="OraFieldText" nowrap>
            <html:textarea 
              property="<%= FormConstants.PREFERRED_DEFINITION %>"
              name="<%= FormConstants.CRF %>"
              cols="30"
              rows="5"
              />
           </td>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.comments" />:</td>
          <td class="OraFieldText" nowrap>
            <html:textarea 
              property="<%= FormConstants.FORM_COMMENTS %>"
              cols="30"
              rows="5"
              />
           </td>
        </tr>


        <tr>
          <td class="OraFieldtitlebold" nowrap><bean:message key="cadsr.formbuilder.form.confirmCopy" />:</td>
          <td class="OraFieldText" nowrap>
            <html:checkbox property="<%= FormConstants.FORM_GOTO_EDIT %>"/>
          </td>
        </tr>

      </table>
      
      <%@ include file="/formbuilder/copyButton_inc.jsp"%>    

      </html:form>

      <logic:notEmpty name="<%=FormConstants.CRF%>" property = "modules">
        <logic:iterate id="module" indexId="moduleIndex" name="<%=FormConstants.CRF%>" type="gov.nih.nci.ncicb.cadsr.resource.Module" property="modules">
          <bean:size id="moduleSize" name="<%=FormConstants.CRF%>" property="modules"  />
          <table width="80%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">               
          <tr class="OraTableColumnHeader">
            <td class="OraTableColumnHeader">
              <table width="100%" align="right" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                <tr class="OraTableColumnHeader" >
                  <td>
                    <bean:write name="module" property="longName"/>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
          <logic:present name="module">
            <logic:notEmpty name="module" property = "questions">
              <tr class="OraTabledata">
                <td>
                  <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" >      
                  <logic:iterate id="question" name="module" type="gov.nih.nci.ncicb.cadsr.resource.Question" property="questions">                           
                  <tr class="OraTabledata">
                    <td class="OraFieldText" width="50">&nbsp;</td>
                    <td height="1"  class="OraFieldText">                               
                  </td>                              
                </tr>                           
                <tr class="OraTabledata">
                  <td class="OraFieldText" width="50">&nbsp;</td>
                  <td class="UnderlineOraFieldText">
                    <bean:write name="question" property="longName"/>
                  </td>
                  <logic:present name="question" property = "dataElement">
                    <td align="center" width="70" class="UnderlineOraFieldText" >
                      <html:link page='<%="/search?dataElementDetails=9&PageId=DataElementsGroup&queryDE=yes"%>'
                        paramId = "p_de_idseq"
                        paramName="question"
                        paramProperty="dataElement.deIdseq"
                        target="_blank">
                        <bean:write name="question" property="dataElement.CDEId"/>
                      </html:link>
                    </td>
                  </logic:present>
                  <td align="center" width="70" class="UnderlineOraFieldText">
                    <bean:write name="question" property="dataElement.version"/>
                  </td>                              
                </tr>                                                     
                <logic:present name="question">
                  <logic:notEmpty name="question" property = "validValues">
                    <tr class="OraTabledata">
                      <td class="OraFieldText" width="50">&nbsp;</td>
                      <td>
                        <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="OraBGAccentVeryDark">
                          <logic:iterate id="validValue" name="question" type="gov.nih.nci.ncicb.cadsr.resource.FormValidValue" property="validValues">
                            <tr COLSPAN="3" class="OraTabledata">
                              <td class="OraFieldText" width="50">&nbsp;</td>
                              <td class="OraFieldText">
                                <bean:write name="validValue" property="longName"/>
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
