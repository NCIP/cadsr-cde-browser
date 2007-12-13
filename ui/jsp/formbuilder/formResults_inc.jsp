

 <script LANGUAGE="Javascript">
<!---
function actionConfirm(message, url){
if(confirm(message)) location.href = url;
}
// --->
</SCRIPT>
<%@ include file="showMessages.jsp" %>
   
   <logic:notEmpty name="<%=FormConstants.FORM_SEARCH_RESULTS%>">
   
         <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0">
               <tr>
                 <td align="left" class="OraTableColumnHeaderNoBG" width="10%" nowrap>Sort order :</td>
                 <td align="left" class="CDEBrowserPageContext">
                  <cde:sorableColumnHeaderBreadcrumb
                          sortableColumnHeaderBeanId="<%=FormConstants.FORM_SEARCH_RESULT_COMPARATOR%>" 
                          separator=">>" 
                          showDefault="Y"
                          labelMapping="longName,Long Name,contextName,Context,formType,Type,protocolLongName,Protocol Long Name(s),aslName,Workflow Status"
                          defaultText=" (Default) "
                          ascendingText=" [Ascending]"
                          descendingText=" [Descending]"                          
                   />           
                 </td> 
           
               </tr>
        </table>     
        
        <bean:define id="pageBean" name="<%=FormConstants.FORM_SEARCH_RESULTS_PAGINATION%>" 
        	type="gov.nih.nci.ncicb.cadsr.common.jsp.bean.PaginationBean"/>
        <cde:pagination name="top" textClassName="OraFieldText" selectClassName="LOVField" formIndex="0" pageSize="40" 
                     beanId = "<%=FormConstants.FORM_SEARCH_RESULTS_PAGINATION%>" 
                     actionURL="pageAction.do"
        	     previousOnImage="i/prev_on.gif"
        	     previousOffImage="i/prev_off.gif"
        	     nextOnImage="i/next_on.gif"
        	     nextOffImage="i/next_off.gif"
        	     urlPrefix="<%=urlPrefix%>"
        	     /> 
                
        <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTableColumnHeader">
            <logic:notPresent name="<%=FormConstants.IN_PROCESS%>"> 
               <th class="OraTableColumnHeader" nowrap>Action</th>
            </logic:notPresent>        
               
          	
          	<th class="OraTableColumnHeader" nowrap>
		        <cde:sortableColumnHeader
            sortableColumnHeaderBeanId="<%=FormConstants.FORM_SEARCH_RESULT_COMPARATOR%>" 
		       	actionUrl='<%="/sortFormSearchAction.do?"+NavigationConstants.METHOD_PARAM+"=sortResult"%>' 
		   	   	columnHeader="Long Name" 
            orderParamId="sortOrder" 
		   	   	sortFieldId="sortField"
		   	   	sortFieldValue = "longName"
		   	   	target="_parent"
            />   
            </th>             
            <th class="OraTableColumnHeader" nowrap>
		        <cde:sortableColumnHeader
            sortableColumnHeaderBeanId="<%=FormConstants.FORM_SEARCH_RESULT_COMPARATOR%>" 
		       	actionUrl='<%="/sortFormSearchAction.do?"+NavigationConstants.METHOD_PARAM+"=sortResult"%>' 
		   	   	columnHeader="Context" 
            orderParamId="sortOrder" 
		   	   	sortFieldId="sortField"
		   	   	sortFieldValue = "contextName"
		   	   	target="_parent"
            />   
            </th>
            <th class="OraTableColumnHeader" nowrap>
	    <cde:sortableColumnHeader
            sortableColumnHeaderBeanId="<%=FormConstants.FORM_SEARCH_RESULT_COMPARATOR%>" 
		       	actionUrl='<%="/sortFormSearchAction.do?"+NavigationConstants.METHOD_PARAM+"=sortResult"%>' 
		   	   	columnHeader="Type" 
            orderParamId="sortOrder" 
		   	   	sortFieldId="sortField"
		   	   	sortFieldValue = "formType"
		   	   	target="_parent"
            />   
          	
            </th>
            <th class="OraTableColumnHeader" nowrap>
	    <cde:sortableColumnHeader
            sortableColumnHeaderBeanId="<%=FormConstants.FORM_SEARCH_RESULT_COMPARATOR%>" 
		       	actionUrl='<%="/sortFormSearchAction.do?"+NavigationConstants.METHOD_PARAM+"=sortResult"%>' 
		   	   	columnHeader="Protocol Long Name(s)" 
            orderParamId="sortOrder" 
		   	   	sortFieldId="sortField"
		   	   	sortFieldValue = "delimitedProtocolLongNames"
		   	   	target="_parent"
            />   
          	
            
            </th>
          	<th class="OraTableColumnHeader" nowrap>
			          
		        <cde:sortableColumnHeader
            sortableColumnHeaderBeanId="<%=FormConstants.FORM_SEARCH_RESULT_COMPARATOR%>" 
		       	actionUrl='<%="/sortFormSearchAction.do?"+NavigationConstants.METHOD_PARAM+"=sortResult"%>' 
		   	   	columnHeader="Workflow Status" 
            orderParamId="sortOrder" 
		   	   	sortFieldId="sortField"
		   	   	sortFieldValue = "aslName"
		   	   	target="_parent"
            />                 
            </th>    
            <th class="OraTableColumnHeader" nowrap>
              Public ID
            </th>
            <th class="OraTableColumnHeader" nowrap>
              Version
            </th>            
          </tr>        
          <logic:iterate id="form" name="<%=FormConstants.FORM_SEARCH_RESULTS%>" 
          	type="gov.nih.nci.ncicb.cadsr.common.resource.Form"
                offset="<%=Integer.toString(pageBean.getOffset())%>"
                length="<%=Integer.toString(pageBean.getPageSize())%>">
            <tr class="OraTabledata">  
            <logic:notPresent name="<%=FormConstants.IN_PROCESS%>"> 
                    <td width="100">
                    <table  >
                    <tr>               
                    <td width="25" class="OraTabledata" align=center>
                    
                    <html:link action='<%="/excelDownload.do?"+NavigationConstants.METHOD_PARAM+"=downloadFormInExcel"%>' 
                    paramId = "<%=FormConstants.FORM_ID_SEQ%>"
                    paramName="form" paramProperty="formIdseq"
                    target="_parent" >
                    <html:img src='<%=urlPrefix+"i/excel-icon.jpg"%>' border="0" alt="Excel Download"/>
                    </html:link>                 
                    
                    </td>
                      <td width="25" class="OraTabledata" align=center>
                           <cde:secureIcon  formId="form" 
                    formScope="<%=CaDSRConstants.PAGE_SCOPE%>"
                    activeImageSource="i/copy.gif" 
                            activeUrl='<%="/formToCopyAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_COPY%>' 
                                    formType="TEMPLATE" 
                    role="<%=CaDSRConstants.CDE_MANAGER%>" 
                                    urlPrefix="<%=urlPrefix%>"
                                    paramId = "<%=FormConstants.FORM_ID_SEQ%>"
                                    paramProperty="formIdseq"
                    inactiveImageSource="i/copy_inactive.gif"
                                    altMessage="Select for Copy"
                                    target="_parent"
                    />            
                     </td>                  
                      <td width="25" class="OraTabledata" align=center>
                           <cde:secureIcon  formId="form" 
                    formScope="<%=CaDSRConstants.PAGE_SCOPE%>" 
                    activeImageSource="i/edit.gif" 
                                    activeUrl='<%="/formToEditAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_EDIT%>' 
                                    role="<%=CaDSRConstants.CDE_MANAGER%>" 
                                    urlPrefix="<%=urlPrefix%>"
                                    paramId = "<%=FormConstants.FORM_ID_SEQ%>"
                                    paramProperty="formIdseq"
                    inactiveImageSource="i/edit_inactive.gif"
                                    altMessage="Edit"
                                    target="_parent"
                    />		            
                      </td>
                     <td width="25"  class="OraTabledata" align=center>
                           <cde:secureIcon  formId="form" 
                    formScope="<%=CaDSRConstants.PAGE_SCOPE%>" 
                    activeImageSource="i/delete.gif" 
                                    activeUrl='<%="/formHrefDeleteAction.do?"
                             +NavigationConstants.METHOD_PARAM+"="+NavigationConstants.DELETE_FORM%>'
                                    role="<%=CaDSRConstants.CDE_MANAGER%>" 
                                    urlPrefix="<%=urlPrefix%>"
                                    paramId = "<%=FormConstants.FORM_ID_SEQ%>"
                                    paramProperty="formIdseq"
                    inactiveImageSource="i/delete_inactive.gif"
                                    altMessage="Delete"
                    confirmMessageKey="cadsr.formbuilder.form.delete.confirm"
                                    />		           	
                    </td> 
                    </tr>
                    </table>
                    </td>
                    <td class="OraFieldText">
                            <html:link action='<%="/formDetailsAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_DETAILS%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
                                    paramName="form" paramProperty="formIdseq"
                                    target="_parent" >
                            <bean:write name="form" property="longName"/>
                            </html:link>          		    
                    </td>
            </logic:notPresent>    
            
    
        <logic:present name="<%=FormConstants.IN_PROCESS%>"> 
            <logic:present name="<%=FormConstants.SKIP_PATTERN%>"> 
          	<td class="OraFieldText">
 			<html:link action='<%="/formbuilder/skipAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.SET_SELECTED_FORM_AS_TARGET_FORM%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="form" paramProperty="formIdseq"
 				target="_parent" >
			<bean:write name="form" property="longName"/>
			</html:link>          		    
          	</td>
            </logic:present>  
            
            <logic:present name="<%=FormConstants.MODULE_DISPLAY_ORDER_TO_COPY%>"> 
          	<td class="OraFieldText">
 			<html:link action='<%="/formbuilder/moduleSearch?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.SET_SELECTED_FORM_AS_MODULE_COPY_FORM%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="form" paramProperty="formIdseq"
 				target="_parent" >
			<bean:write name="form" property="longName"/>
			</html:link>          		    
          	</td>
            </logic:present>              
        </logic:present>

          	<td class="OraFieldText">
          		<bean:write name="form" property="context.name"/><br>
          	</td>            
          	<td class="OraFieldText">
          		<bean:write name="form" property="formType"/><br>
          	</td>
          	<td class="OraFieldText">
             <logic:present name="form" property="delimitedProtocolLongNames">
               <%=FormJspUtil.getDelimitedProtocolLongNames(form.getProtocols(), "<br/><br/>")%>
             </logic:present>
             <logic:notPresent name="form" property="protocol">
               &nbsp;
             </logic:notPresent>
          	</td>             
          	<td class="OraFieldText">
          		<bean:write name="form" property="aslName"/><br>
          	</td>          
          	<td class="OraFieldText">
          		<bean:write name="form" property="publicId"/><br>
          	</td>   
          	<td class="OraFieldText">
          		<bean:write name="form" property="version"/><br>
          	</td>               
            </tr>
          </logic:iterate>
        </table>
        <cde:pagination name="bottom" textClassName="OraFieldText" selectClassName="LOVField" formIndex="0" pageSize="40" 
                     beanId = "<%=FormConstants.FORM_SEARCH_RESULTS_PAGINATION%>" 
                     actionURL="pageAction.do"
        	     previousOnImage="i/prev_on.gif"
        	     previousOffImage="i/prev_off.gif"
        	     nextOnImage="i/next_on.gif"
        	     nextOffImage="i/next_off.gif"
        	     urlPrefix="<%=urlPrefix%>"
        	     /> 
       
        </logic:notEmpty>
        <logic:empty name="<%=FormConstants.FORM_SEARCH_RESULTS%>">
	       <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  	      <tr class="OraTableColumnHeader">
          	<th class="OraTableColumnHeader" nowrap>Action</th>
          	<th class="OraTableColumnHeader" nowrap>Long Name</th>
            <th class="OraTableColumnHeader" nowrap>Context</th>
          	<th class="OraTableColumnHeader" nowrap>Type</th>
            <th class="OraTableColumnHeader" nowrap>Protocol</th>
          	<th class="OraTableColumnHeader" nowrap>Workflow Status</th>
            <th class="OraTableColumnHeader" nowrap>Public ID</th> 
            <th class="OraTableColumnHeader" nowrap>Version</th> 
          </tr>
      <tr class="OraTabledata" >
         	<td colspan="8" ><bean:message key="cadsr.formbuilder.empty.search.results"/></td>
  	  </tr>
  	</table>        
                 
        </logic:empty>
