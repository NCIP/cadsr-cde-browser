
   
   <logic:notEmpty name="<%=FormConstants.FORM_SEARCH_RESULTS%>">
        <bean:define id="pageBean" name="<%=FormConstants.FORM_SEARCH_RESULTS_PAGINATION%>" type="gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean"/>
        <cde:pagination name="top" textClassName="OraFieldText" selectClassName="LOVField" formIndex="0" pageSize="40" 
                     beanId = "<%=FormConstants.FORM_SEARCH_RESULTS_PAGINATION%>" 
                     actionURL="/cdebrowser/pageAction.do"/>
                     
        <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTableColumnHeader">
          	<th class="OraTableColumnHeader" nowrap>Action</th>
          	<th class="OraTableColumnHeader" nowrap>Long Name</th>
          	<th class="OraTableColumnHeader" nowrap>Type</th>
          	<th class="OraTableColumnHeader" nowrap>Workflow Status</th>         	
          </tr>        
          <logic:iterate id="form" name="<%=FormConstants.FORM_SEARCH_RESULTS%>" 
          	type="gov.nih.nci.ncicb.cadsr.resource.Form"
                offset="<%=pageBean.getOffset()%>"
                length="<%=pageBean.getPageSize()%>">
            <tr class="OraTabledata">
                <td class="OraFieldText">
                  <table cellspacing="1" cellpadding="1"  border="0" width="100%">
                    <tr >                   
                      <td >
                      	<a href='test'/><img src="i/view.gif" border=0 alt='View'></a>
                      </td>
		      <td >
		       	<cde:secureIcon  formId="form" activeImageSource="i/edit.gif" activeUrl="test" 
		            role="<%=CaDSRConstants.CDE_MANAGER%>" altMessage="Edit"/>
		       </td>
		       <td>
		          <cde:secureIcon  formId="form" activeImageSource="i/copy.gif" activeUrl="test" 
		   	   	role="<%=CaDSRConstants.CDE_MANAGER%>" altMessage="Copy"/>
		        </td>
		        <td >
		           <cde:secureIcon  formId="form" activeImageSource="i/delete.gif" activeUrl="test"
		           	role="<%=CaDSRConstants.CDE_MANAGER%>" altMessage="Delete"/>
		        </td>
                       </tr>
                    </table>
                   </td>                
          	<td class="OraFieldText">
          		<html:link page="/formAction.do" paramName="form" paramProperty="preferredName">
            			<bean:write name="form" property="longName"/><br>
          		</html:link>    
          	</td>
          	<td class="OraFieldText">
          		<bean:write name="form" property="formType"/><br>
          	</td>
          	<td class="OraFieldText">
          		<bean:write name="form" property="aslName"/><br>
          	</td>          	
            </tr>
          </logic:iterate>
        </table>
        <cde:pagination name="bottom" textClassName="OraFieldText" selectClassName="LOVField" formIndex="0" pageSize="40" 
                     beanId = "<%=FormConstants.FORM_SEARCH_RESULTS_PAGINATION%>" 
                     actionURL="/cdebrowser/pageAction.do"/>        
        </logic:notEmpty>
        <logic:empty name="<%=FormConstants.FORM_SEARCH_RESULTS%>">
	<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  	  <tr class="OraTabledata">
         	<td ><bean:message key="cadsr.formbuilder.empty.search.results"/></td>
  	  </tr>
  	</table>        
                 
        </logic:empty>
