</SCRIPT>

 <script LANGUAGE="Javascript">
<!---
function actionConfirm(message, url){
if(confirm(message)) location.href = url;
}
// --->
</SCRIPT>
<%@ include file="showMessages.jsp" %>
   
   <logic:notEmpty name="<%=FormConstants.FORM_SEARCH_RESULTS%>">
        <bean:define id="pageBean" name="<%=FormConstants.FORM_SEARCH_RESULTS_PAGINATION%>" 
        	type="gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean"/>
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
          	<th class="OraTableColumnHeader" nowrap>Action</th>
          	<th class="OraTableColumnHeader" nowrap>Long Name</th>
            <th class="OraTableColumnHeader" nowrap>Context</th>
          	<th class="OraTableColumnHeader" nowrap>Type</th>
            <th class="OraTableColumnHeader" nowrap>Protocol</th>
          	<th class="OraTableColumnHeader" nowrap>Workflow Status</th>         	
          </tr>        
          <logic:iterate id="form" name="<%=FormConstants.FORM_SEARCH_RESULTS%>" 
          	type="gov.nih.nci.ncicb.cadsr.resource.Form"
                offset="<%=pageBean.getOffset()%>"
                length="<%=pageBean.getPageSize()%>">
            <tr class="OraTabledata">
             <td width="100">
              <table  >
               <tr>
               
              <td width="25" class="OraTabledata" align=center>
                <a href="search?viewTemplate=9&templateIdseq=<%= form.getFormIdseq() %>&PageId=DataElementsGroup" target="_blank">
                  <html:img src='<%=urlPrefix+"i/dload-sm.gif"%>' border="0" alt="Download"/>
		            </a>			
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
          </tr>
      <tr class="OraTabledata" >
         	<td colspan="6" ><bean:message key="cadsr.formbuilder.empty.search.results"/></td>
  	  </tr>
  	</table>        
                 
        </logic:empty>
