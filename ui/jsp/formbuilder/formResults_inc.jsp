
   <logic:notEmpty name="<%=FormConstants.FORM_SEARCH_RESULTS%>">
        <bean:define id="pageBean" name="<%=FormConstants.FORM_SEARCH_RESULTS_PAGINATION%>" 
        	type="gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean"/>
        <cde:pagination name="top" textClassName="OraFieldText" selectClassName="LOVField" formIndex="0" pageSize="40" 
                     beanId = "<%=FormConstants.FORM_SEARCH_RESULTS_PAGINATION%>" 
                     actionURL="/cdebrowser/pageAction.do"
        	     previousOnImage="i/prev_on.gif"
        	     previousOffImage="i/prev_off.gif"
        	     nextOnImage="i/next_on.gif"
        	     nextOffImage="i/next_off.gif"
        	     urlPrefix="<%=urlPrefix%>"
        	     /> 
                     
        <table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
          <tr class="OraTableColumnHeader">
          	<th class="OraTableColumnHeader" nowrap>View</th>
          	<th class="OraTableColumnHeader" nowrap>Copy</th>
          	<th class="OraTableColumnHeader" nowrap>Edit</th>
          	<th class="OraTableColumnHeader" nowrap>Delete</th>
          	<th class="OraTableColumnHeader" nowrap>Long Name</th>
          	<th class="OraTableColumnHeader" nowrap>Type</th>
          	<th class="OraTableColumnHeader" nowrap>Workflow Status</th>         	
          </tr>        
          <logic:iterate id="form" name="<%=FormConstants.FORM_SEARCH_RESULTS%>" 
          	type="gov.nih.nci.ncicb.cadsr.resource.Form"
                offset="<%=pageBean.getOffset()%>"
                length="<%=pageBean.getPageSize()%>">
            <tr class="OraTabledata">
                   
                  <td align=center>
 			<html:link action='<%="/formDetailsAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_DETAILS%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="form" paramProperty="formIdseq"
 				target="_parent">
			   <html:img src='<%=urlPrefix+"i/view.gif"%>' border="0" alt="View"/>
			</html:link>  			
                  </td>
		  <td align=center>
		       <cde:secureIcon  formId="form" activeImageSource="i/copy.gif" 
		       		activeUrl='<%="/formToCopyAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_COPY%>' 
		   	   	role="<%=CaDSRConstants.CDE_MANAGER%>" 
		   	   	urlPrefix="<%=urlPrefix%>"
		   	   	paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		   	   	paramProperty="formIdseq"
		   	   	altMessage="Copy"
		   	   	target="_parent"/>
		 </td>                  
		  <td align=center>
		       <cde:secureIcon  formId="form" activeImageSource="i/edit.gif" 
		       		activeUrl='<%="/formToEditAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_EDIT%>' 
		   	   	role="<%=CaDSRConstants.CDE_MANAGER%>" 
		   	   	urlPrefix="<%=urlPrefix%>"
		   	   	paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		   	   	paramProperty="formIdseq"
		   	   	altMessage="Edit"
		   	   	target="_parent"/>		            
		  </td>
		 <td align=center>
		       <cde:secureIcon  formId="form" activeImageSource="i/delete.gif" 
		       		activeUrl='<%="/formDeleteAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.DELETE_FORM%>' 
		   	   	role="<%=CaDSRConstants.CDE_MANAGER%>" 
		   	   	urlPrefix="<%=urlPrefix%>"
		   	   	paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		   	   	paramProperty="formIdseq"
		   	   	altMessage="Delete"
		   	   	target="_parent"/>		           	
		</td>                
          	<td class="OraFieldText">
 			<html:link action='<%="/formDetailsAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_DETAILS%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="form" paramProperty="formIdseq"
 				target="_parent" >
			<bean:write name="form" property="longName"/>
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
                     actionURL="/cdebrowser/pageAction.do"
        	     previousOnImage="i/prev_on.gif"
        	     previousOffImage="i/prev_off.gif"
        	     nextOnImage="i/next_on.gif"
        	     nextOffImage="i/next_off.gif"
        	     urlPrefix="<%=urlPrefix%>"
        	     /> 
       
        </logic:notEmpty>
        <logic:empty name="<%=FormConstants.FORM_SEARCH_RESULTS%>">
	<table width="100%" align="center" cellpadding="1" cellspacing="1" border="0" class="OraBGAccentVeryDark">
  	  <tr class="OraTabledata">
         	<td ><bean:message key="cadsr.formbuilder.empty.search.results"/></td>
  	  </tr>
  	</table>        
                 
        </logic:empty>
