/**
 * CompareCDEAction
 *
 * This class is the Action class for comparing CDEs side by side
  *
 * @release 3.0
 * @author: <a href=”mailto:jane.jiang@oracle.com”>Jane Jiang</a>
 * @date: 8/16/2005
 * @version: $Id: CompareCDEAction.java,v 1.2 2004-10-11 21:18:26 kakkodis Exp $
 */
package gov.nih.nci.ncicb.cadsr.cdebrowser.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormBuilderBaseDispatchAction;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;

import gov.nih.nci.ncicb.cadsr.util.ContentTypeHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


public class CompareCDEAction extends FormBuilderBaseDispatchAction {
  /**
   * Displays CDE Cart.
   *
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   *
   * @return
   *
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward compareCDEs(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    //CDECart cart = new CDECartTransferObject();
    CDECart cart = null;

    DynaActionForm searchForm = (DynaActionForm) form;
    Integer numberSelected = Integer.valueOf(request.getParameter("numberSelected"));
    this.setSessionObject(request, "compareCDESize", numberSelected);
    return mapping.findForward(SUCCESS);
  }
  
  
  /**
   * 
   *
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   *
   * @return
   *
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward gotoChangeCompareOrder(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    return mapping.findForward("gotoChangeCompareOrder");
  }  
  public ActionForward downloadToExcel(ActionMapping mapping,
        ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws IOException, ServletException {
        
        DynaActionForm hrefCRFForm = (DynaActionForm) form;

        
        File f = new File("D:\\projects\\NCI3\\cdebrowser_other\\excel_template\\exceldownloadCDECompare.xls");
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
