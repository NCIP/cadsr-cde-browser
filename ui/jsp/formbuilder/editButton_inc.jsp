<script LANGUAGE="Javascript">
<!---
function actionConfirm(message, url){
if(confirm(message)) location.href = url;
}
// --->
</SCRIPT>
      <table width="25%" align="center" cellpadding="1" cellspacing="1" border="0" >
        <tr >
         <td>
            <a href="javascript:submitFormToSave('<%=NavigationConstants.SAVE_FORM%>')">
                <html:img src='<%=urlPrefix+"i/save.gif"%>' border="0" alt="Save"/>
             </a> 
          </td>          
         <td>
           <html:link action='<%="/gotoManageClassifications.do?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.GET_CLASSIFICATIONS%>'
             paramId = "<%=FormConstants.FORM_ID_SEQ%>"
             paramName="<%=FormConstants.CRF%>"
             paramProperty="formIdseq"
             >
             <html:img src='<%=urlPrefix+"i/classifications.gif"%>' border="0" alt="Manage Classifications"/>
           </html:link>           
           </td>
         <td>
           <html:link action='<%="/manageReferenceDocs.do?"+NavigationConstants.METHOD_PARAM+"="+"manageReferenceDocs"%>'
             >
             <html:img src='<%=urlPrefix+"i/refdocs.gif"%>' border="0" alt="Manage Reference Documents"/>
           </html:link>
          </td>
          <td > 
            <a href="javascript:submitFormToSave('<%=NavigationConstants.CHECK_CHANGES_DONE%>')">
               <img src=<%=urlPrefix%>i/backButton.gif border=0 alt="Done">
            </a>              
          </td>                
          <td >
		       <cde:secureIcon  formId="<%=FormConstants.CRF%>" 
           formScope="<%=CaDSRConstants.SESSION_SCOPE%>" 
           activeImageSource="i/deleteFormButton.gif" 
		       		activeUrl='<%="/formEditDeleteAction.do?"
                         +NavigationConstants.METHOD_PARAM+"="+NavigationConstants.DELETE_FORM%>'
		   	   	role="<%=CaDSRConstants.CDE_MANAGER%>" 
		   	   	urlPrefix="<%=urlPrefix%>"
		   	   	paramId = "<%=FormConstants.FORM_ID_SEQ%>"
		   	   	paramProperty="formIdseq"
		   	   	altMessage="Delete"  
            confirmMessageKey="cadsr.formbuilder.form.delete.confirm"
		   	   	/>		                
          </td>  

          <td > 
            <html:link action='<%="/cancelFormEditAction?"+NavigationConstants.METHOD_PARAM+"="+NavigationConstants.CANCEL_FORM_EDIT%>'>
              <html:img src='<%=urlPrefix+"i/cancel.gif"%>' border="0" alt="Cancel"/>
	          </html:link>               
          </td>               
        </tr> 
                
      </table>