

 <script LANGUAGE="Javascript">
<!---
function actionConfirm(message, url){
if(confirm(message)) location.href = url;
}
// --->
</SCRIPT>
<%@ include file="showMessages.jsp" %>
   
   <logic:notEmpty name="<%=FormConstants.SKIP_FORM_SEARCH_RESULTS%>">
   
         <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0">
               <tr>
                 <td align="left" class="OraTableColumnHeaderNoBG" width="10%" nowrap>Sort order :</td>
                 <td align="left" class="CDEBrowserPageContext">
                  <cde:sorableColumnHeaderBreadcrumb
                          sortableColumnHeaderBeanId="<%=FormConstants.SKIP_FORM_SEARCH_RESULT_COMPARATOR%>" 
                          separator=">>" 
                          showDefault="Y"
                          labelMapping="longName,Long Name,contextName,Context,formType,Type,protocolLongName,Protocol,aslName,Workflow Status"
                          defaultText=" (Default) "
                          ascendingText=" [Ascending]"
                          descendingText=" [Descending]"                          
                   />           
                 </td> 
           
               </tr>
        </table>     
        
        <bean:define id="pageBean" name="<%=FormConstants.SKIP_FORM_SEARCH_RESULTS_PAGINATION%>" 
        	type="gov.nih.nci.ncicb.cadsr.common.jsp.bean.PaginationBean"/>
        <cde:pagination name="top" textClassName="OraFieldText" selectClassName="LOVField" formIndex="0" pageSize="40" 
                     beanId = "<%=FormConstants.SKIP_FORM_SEARCH_RESULTS_PAGINATION%>" 
                     actionURL="pageAction.do"
        	     previousOnImage="i/prev_on.gif"
        	     previousOffImage="i/prev_off.gif"
        	     nextOnImage="i/next_on.gif"
        	     nextOffImage="i/next_off.gif"
        	     urlPrefix="<%=urlPrefix%>"
        	     /> 
                
        <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTableColumnHeader">
          	<th class="OraTableColumnHeader" nowrap>Action</th>
          	<th class="OraTableColumnHeader" nowrap>
		        <cde:sortableColumnHeader
            sortableColumnHeaderBeanId="<%=FormConstants.SKIP_FORM_SEARCH_RESULTS_PAGINATION%>" 
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
            sortableColumnHeaderBeanId="<%=FormConstants.SKIP_FORM_SEARCH_RESULTS_PAGINATION%>" 
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
            sortableColumnHeaderBeanId="<%=FormConstants.SKIP_FORM_SEARCH_RESULTS_PAGINATION%>" 
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
            sortableColumnHeaderBeanId="<%=FormConstants.SKIP_FORM_SEARCH_RESULTS_PAGINATION%>" 
		       	actionUrl='<%="/sortFormSearchAction.do?"+NavigationConstants.METHOD_PARAM+"=sortResult"%>' 
		   	   	columnHeader="Protocol" 
            orderParamId="sortOrder" 
		   	   	sortFieldId="sortField"
		   	   	sortFieldValue = "protocolLongName"
		   	   	target="_parent"
            />   
          	
            
            </th>
          	<th class="OraTableColumnHeader" nowrap>
			          
		        <cde:sortableColumnHeader
            sortableColumnHeaderBeanId="<%=FormConstants.SKIP_FORM_SEARCH_RESULTS_PAGINATION%>" 
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
          <logic:iterate id="form" name="<%=FormConstants.SKIP_FORM_SEARCH_RESULTS%>" 
          	type="gov.nih.nci.ncicb.cadsr.common.resource.Form"
                offset="<%=Integer.toString(pageBean.getOffset())%>"
                length="<%=Integer.toString(pageBean.getPageSize())%>">
            <tr class="OraTabledata">  
             <td width="120">
              <table  >
               <tr>               

		  <td width="100%" class="OraTabledata" align=center>
                  <html:link action='<%="/formbuilder/skipAction?"+NavigationConstants.METHOD_PARAM+"="+"NavigationConstants.CREATE_SKIP_PATTERN"%>'
                        >
                       Skip to this form
                  </html:link>&nbsp;		           	
		  </td> 
	       </tr>
	      </table>
	      </td>
          	<td class="OraFieldText">
                  <html:link action='<%="/formbuilder/skipAction?"+NavigationConstants.METHOD_PARAM+"="+"NavigationConstants.SKIP_TO_FORM_LOCATION"%>'
                       >
                       <bean:write name="form" property="longName"/>
                  </html:link>&nbsp;           	        		    
          	</td>
          	<td class="OraFieldText">
          		<bean:write name="form" property="context.name"/><br>
          	</td>            
          	<td class="OraFieldText">
          		<bean:write name="form" property="formType"/><br>
          	</td>
          	<td class="OraFieldText">
             <logic:present name="form" property="protocol">
          		<bean:write name="form" property="protocol.longName"/><br>
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
                     beanId = "<%=FormConstants.SKIP_FORM_SEARCH_RESULTS_PAGINATION%>" 
                     actionURL="pageAction.do"
        	     previousOnImage="i/prev_on.gif"
        	     previousOffImage="i/prev_off.gif"
        	     nextOnImage="i/next_on.gif"
        	     nextOffImage="i/next_off.gif"
        	     urlPrefix="<%=urlPrefix%>"
        	     /> 
       
        </logic:notEmpty>
        <logic:empty name="<%=FormConstants.SKIP_FORM_SEARCH_RESULTS%>">
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
