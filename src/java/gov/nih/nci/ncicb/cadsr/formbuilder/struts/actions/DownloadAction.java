package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;



import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.util.ContentTypeHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DownloadAction extends FormBuilderSecureBaseDispatchAction {

   
    public ActionForward downloadFormInExcel(ActionMapping mapping,
        ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws IOException, ServletException {
        
        DynaActionForm hrefCRFForm = (DynaActionForm) form;
        String formIdSeq = (String) hrefCRFForm.get(FORM_ID_SEQ);
        
        FormBuilderServiceDelegate service = getFormBuilderService();
        Form crf = null;
        try {
          crf = service.getFormDetails(formIdSeq);
        }
        catch (FormBuilderException exp) {
          if (log.isErrorEnabled()) {
            log.error("Exception getting CRF", exp);
          }      
          saveError(ERROR_FORM_RETRIEVE, request);
          saveError(ERROR_FORM_DOES_NOT_EXIST, request);
          return mapping.findForward(FAILURE);
        }  
        
        File f = new File("D:\\projects\\NCI3\\cdebrowser_other\\excel_template\\exceldownload.xls");
        String ctype = ContentTypeHelper.getContentType(f.getName());

			  response.setContentType(ctype);			
        response.setContentLength((int)f.length());			
        response.addHeader("Content-Disposition", "attachment; filename=" + f.getName());
        response.addHeader("Pragma", "No-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Expires", "0");    
     try{
			// create buffer			
        byte[] buffer = new byte[1024];			
        int r = 0;			
        // write out file			
        FileInputStream fin = new FileInputStream(f);			
        OutputStream out = response.getOutputStream();			
        while((r = fin.read(buffer, 0, buffer.length)) != -1) 
          {				
           out.write(buffer, 0, r);			
          }			
        try {				
          fin.close();				
          out.flush();				
          out.close();			
          } catch(Exception e) {}			
        out = null;			
        fin = null;			
        buffer = null;		
      } catch(Exception ex) 
       {		
          String msg = ex.getMessage();			
          response.setContentType("text/html");			
          response.setContentLength(msg.length());			
          PrintWriter out = response.getWriter();			
          out.println("Unexpected error");			
          out.flush();			
          out.close();		
        }        
        return null;
    }
    

    
}
