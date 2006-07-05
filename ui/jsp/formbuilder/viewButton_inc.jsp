

 <script LANGUAGE="Javascript">
<!---
function actionConfirm(message, url){
if(confirm(message)) location.href = url;
}
// --->
</SCRIPT>

      <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" >
        <tr align="center">
        
          <td >
		       <cde:secureIcon  formId="<%=FormConstants.CRF%>"
            formScope="<%=CaDSRConstants.SESSION_SCOPE%>"
            activeImageSource="i/selectForCopyButton.gif" 
		       	activeUrl='<%="/formToCopyAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_COPY%>' 
		   	   	formType="TEMPLATE"  
            role="<%=CaDSRConstants.CDE_MANAGER%>" 
		   	   	urlPrefix="<%=urlPrefix%>"
		   	   	paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		   	   	paramProperty="formIdseq"
		   	   	altMessage="Select for Copy" 
				/>   
          </td> 
          
          <td >
		       <cde:secureIcon  formId="<%=FormConstants.CRF%>" 
            formScope="<%=CaDSRConstants.SESSION_SCOPE%>" 
            activeImageSource="i/editButton.gif" 
		       		activeUrl='<%="/formToEditAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_EDIT%>' 
		   	   	role="<%=CaDSRConstants.CDE_MANAGER%>" 
		   	   	urlPrefix="<%=urlPrefix%>"
		   	   	paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		   	   	paramProperty="formIdseq"
		   	   	altMessage="Edit"
		   	   	target="_parent"            
            />		             
          </td>  
          

          
          <td >
		       <cde:secureIcon  formId="<%=FormConstants.CRF%>" 
           formScope="<%=CaDSRConstants.SESSION_SCOPE%>" 
           activeImageSource="i/deleteFormButton.gif" 
		       		activeUrl='<%="/formViewDeleteAction.do?"
                         +NavigationConstants.METHOD_PARAM+"="+NavigationConstants.DELETE_FORM%>'
		   	   	role="<%=CaDSRConstants.CDE_MANAGER%>" 
		   	   	urlPrefix="<%=urlPrefix%>"
		   	   	paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		   	   	paramProperty="formIdseq"
		   	   	altMessage="Delete"  
            confirmMessageKey="cadsr.formbuilder.form.delete.confirm"
		   	   	/>		                
          </td>  
          
     
          <!-- Publish Change Request -->
          <logic:notEqual value="GUEST" name="<%=CaDSRConstants.USER_KEY%>" property="username">
	             <td >
	  <%
	  	   	if (!isPublished) {
	  	   
	  %>
	                
	                 <cde:secureIcon  formId="<%=FormConstants.CRF%>" 
	                  formScope="<%=CaDSRConstants.SESSION_SCOPE%>" 
	                  activeImageSource="i/publish.gif" 
	                    activeUrl='<%="/formPublishAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.PUBLISH_FORM%>' 
	                  role="<%=CaDSRConstants.CDE_MANAGER%>" 
	                  urlPrefix="<%=urlPrefix%>"
	                  paramId = "<%=FormConstants.FORM_ID_SEQ%>"
	                  paramProperty="formIdseq"
	                  altMessage="Publish"
	                  target="_parent"            
	                  />		 
	                 
	   <%
	  	   	} else {
	  	   
	  %>  
	                
	                 <cde:secureIcon  formId="<%=FormConstants.CRF%>" 
	                  formScope="<%=CaDSRConstants.SESSION_SCOPE%>" 
	                  activeImageSource="i/unpublish.gif" 
	                    activeUrl='<%="/formPublishAction.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.UNPUBLISH_FORM%>' 
	                  role="<%=CaDSRConstants.CDE_MANAGER%>" 
	                  urlPrefix="<%=urlPrefix%>"
	                  paramId = "<%=FormConstants.FORM_ID_SEQ%>"
	                  paramProperty="formIdseq"
	                  altMessage="Publish"
	                  target="_parent"            
	                  />		
	      <%
	  	   	} 
	  	   
	  %>                    
	             </td>    
         </logic:notEqual>          
          
          <td >
		       <cde:secureIcon  formId="<%=FormConstants.CRF%>" 
                         formScope="<%=CaDSRConstants.SESSION_SCOPE%>" 
                          activeImageSource="i/sentinalalert.gif" 
		       		activeUrl='<%="/formAlertAction.do?"
                         +NavigationConstants.METHOD_PARAM+"="+NavigationConstants.SET_SENTINAL_ALERT%>'
		   	   	role="<%=CaDSRConstants.CDE_MANAGER%>" 
		   	   	urlPrefix="<%=urlPrefix%>"
		   	   	paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		   	   	paramProperty="formIdseq"
		   	   	altMessage="Set Sentinal Alert"  
		   	   	target="_parent" 
		   	   	/>		                
          </td>  


           
          </tr>
      </table>
      <table width="80%" align="center" cellpadding="0" cellspacing="1" border="0" >

          
          <tr align="center">  
         <td>&nbsp;
           <html:link action='<%="/viewReferenceDocs.do?"+NavigationConstants.METHOD_PARAM+"="+"viewReferenceDocs"%>'
             >
             <html:img src='<%=urlPrefix+"i/refdocs.gif"%>' border="0" alt="Reference Documents"/>
           </html:link>
          </td> 
          
            <td>
            <cde:secureIcon  formId="<%=FormConstants.CRF%>"
                formScope="<%=CaDSRConstants.SESSION_SCOPE%>"
                activeImageSource="i/designations.gif" 
                activeUrl='<%="/getContexts.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_CONTEXTS%>' 
                formType="TEMPLATE"  
                role="<%=CaDSRConstants.CDE_MANAGER%>" 
		urlPrefix="<%=urlPrefix%>"
		paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		paramProperty="formIdseq"
		altMessage="Select to designate Data Elements in the Form" 
            />   
           </td>  
           
         <td>
            <cde:secureIcon  formId="<%=FormConstants.CRF%>"
                formScope="<%=CaDSRConstants.SESSION_SCOPE%>"
                activeImageSource="i/version.gif" 
                activeUrl='<%="/getFormVersions.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_VERSIONS%>' 
                formType="TEMPLATE"  
                role="<%=CaDSRConstants.CDE_MANAGER%>" 
		urlPrefix="<%=urlPrefix%>"
		paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		paramProperty="formIdseq"
		altMessage="Form Versioning" 
            />   
           </td>              
           
         <td>
            <cde:secureIcon  formId="<%=FormConstants.CRF%>"
                formScope="<%=CaDSRConstants.SESSION_SCOPE%>"
                activeImageSource="i/createNewVersion.gif" 
                activeUrl='<%="/gotoCreateNewVersion.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GOTO_CREATE_NEW_VERSION_FORM%>' 
                formType="TEMPLATE"  
                role="<%=CaDSRConstants.CDE_MANAGER%>" 
		urlPrefix="<%=urlPrefix%>"
		paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		paramProperty="formIdseq"
		altMessage="Form Versioning" 
            />   
           </td>  
           
           <td >
             <html:link action='<%="/formSearchAction"%>' >				
             <html:img src='<%=urlPrefix+"i/backButton.gif"%>' border="0" alt="Done"/>
             </html:link>             
          </td>         
          
          </tr>
          
          <tr width="100%" align="right">
          <td colspan="9">
 	       <html:link action='<%="/formPrinterAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_FORM_TO_PRINT%>' paramId = "<%=FormConstants.FORM_ID_SEQ%>"
 				paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq"
 				target="_blank">
		Printer Friendly Version
	      </html:link>
	      &nbsp;&nbsp;
              <html:link action='<%="/excelDownload.do?"+NavigationConstants.METHOD_PARAM+"=downloadFormInExcel"%>' 
                paramId = "<%=FormConstants.FORM_ID_SEQ%>"
                paramName="<%=FormConstants.CRF%>" paramProperty="formIdseq"
                target="_parent" >
                Excel Download
              </html:link> 
        </td>


          
        </tr> 

        <tr></tr>
      </table>
      </table>
      