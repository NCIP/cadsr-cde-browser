/**
 * CompareCDEAction
 *
 * This class is the Action class for comparing CDEs side by side
  *
 * @release 3.0
 * @author: <a href=”mailto:jane.jiang@oracle.com”>Jane Jiang</a>
 * @date: 8/16/2005
 * @version: $Id: CompareCDEAction.java,v 1.5 2004-12-10 03:53:21 kakkodis Exp $
 */
package gov.nih.nci.ncicb.cadsr.cdebrowser.struts.actions;

import gov.nih.nci.ncicb.cadsr.cdebrowser.CDECompareList;
import gov.nih.nci.ncicb.cadsr.cdebrowser.struts.common.BrowserFormConstants;
import gov.nih.nci.ncicb.cadsr.cdebrowser.struts.common.BrowserNavigationConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormBuilderBaseDispatchAction;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AbstractDAOFactory;
import gov.nih.nci.ncicb.cadsr.persistence.dao.DerivedDataElementDAO;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;

import gov.nih.nci.ncicb.cadsr.resource.Classification;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.DerivedDataElement;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.resource.handler.ClassificationHandler;
import gov.nih.nci.ncicb.cadsr.resource.handler.DataElementHandler;
import gov.nih.nci.ncicb.cadsr.resource.handler.ValidValueHandler;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocatorFactory;
import gov.nih.nci.ncicb.cadsr.util.ContentTypeHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.nih.nci.ncicb.cadsr.cdebrowser.process.ProcessConstants;
import oracle.cle.persistence.HandlerFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


public class CompareCDEAction extends BrowserBaseDispatchAction {
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

    DynaActionForm compareForm = (DynaActionForm) form;

    String[] cdeIdseqArr = (String[]) compareForm.get(ProcessConstants.SELECT_DE);        
    CDECompareList cdeList = (CDECompareList)this.getSessionObject(request,CDE_COMPARE_LIST);
    if(cdeList==null)
    {     
        cdeList = new CDECompareList();
        setSessionObject(request,CDE_COMPARE_LIST,cdeList,true);
    }
    if(cdeIdseqArr!=null)
    {
      if(!cdeList.add(cdeIdseqArr))
      {
        //saveError("cadsr.cdebrowser.cdecompare.list.lessthantwo", request);
        if(cdeList.getCdeList().size()<2)
          return mapping.findForward("lessThanTwo");  
      }
    }
    if(cdeList.getCdeList().size()==0)
    {

      saveError("cadsr.cdebrowser.cdecompare.list.empty", request);
      return mapping.findForward(FAILURE);  
    }      
    if(cdeList.getCdeList().size()<2)
    {

      saveError("cadsr.cdebrowser.cdecompare.list.oneelement", request);
      return mapping.findForward(FAILURE);  
    }    

    ListIterator it = cdeList.listIterator();
    List cdeDetailList = new ArrayList();
    
    try
    {
      while(it.hasNext())
      {
        DataElement de = (DataElement)it.next();
        cdeDetailList.add(getDataElementDetails(de,request.getSession().getId()));
        
      }
      cdeList.setDetailCDEList(cdeDetailList);
    }
    catch (Exception e)
    {     
      Exception ex = e;
      saveError("cadsr.cdebrowser.cdecompare.compare.failed", request);
      return mapping.findForward(FAILURE);
    }
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
  public ActionForward addToCDECompareList(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    
    DynaActionForm compareForm = (DynaActionForm) form;
    String[] cdeIdseqArr = (String[]) compareForm.get(ProcessConstants.SELECT_DE);
    CDECompareList cdeList = (CDECompareList)this.getSessionObject(request,CDE_COMPARE_LIST);

    if(cdeList==null)
      {
        cdeList = new CDECompareList();
        setSessionObject(request,CDE_COMPARE_LIST,cdeList,true);
      }
      
    if(cdeIdseqArr!=null&&cdeList!=null)
      {
        if(cdeList.add(cdeIdseqArr))
        {
          saveMessage("cadsr.cdebrowser.cdecompare.list.add.success", request);                      
        }
        else
        {
          saveMessage("cadsr.cdebrowser.cdecompare.list.add.duplicate", request);
        }
      }
    else
    {
      saveError("cadsr.cdebrowser.cdecompare.list.empty", request);
    }
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
  public ActionForward removeFromCDECompareList(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    
    DynaActionForm compareForm = (DynaActionForm) form;
    String[] cdeIndexs = (String[]) compareForm.get(CDE_TO_REMOVE);
    CDECompareList cdeList = (CDECompareList)this.getSessionObject(request,CDE_COMPARE_LIST);

      
    if(cdeIndexs!=null&&cdeList!=null)
      {
       
       if(cdeList.remove(cdeIndexs))
        {
         if(!cdeList.isEmpty()&&cdeList.getCdeList().size()>1)
          saveMessage("cadsr.cdebrowser.cdecompare.list.remove.success", request);                      
        }
        else
        {
          saveError("cadsr.cdebrowser.cdecompare.list.remove.failed", request);
        }
      }
    else
    {
      //go back to homepage
      saveError("cadsr.cdebrowser.cdecompare.list.empty", request); 
      return mapping.findForward(FAILURE);      
    }
    if(cdeList!=null&&cdeList.isEmpty())
    {
      //go back to homepage
      saveMessage("cadsr.cdebrowser.cdecompare.list.empty", request); 
      return mapping.findForward("lessThanTwo");
    }
    if(cdeList.getCdeList().size()<2)
    {
      //go back to homepage
      saveMessage("cadsr.cdebrowser.cdecompare.list.oneelement", request); 
      return mapping.findForward("lessThanTwo");
    }    
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
  public ActionForward doneCDECompare(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    
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
  public ActionForward changeCompareOrder(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    
    
    DynaActionForm compareForm = (DynaActionForm) form;
    String[] newDisplayOrderArr = (String[]) compareForm.get(CDE_COMPARE_DISPAY_ORDER); 
    CDECompareList cdeList = (CDECompareList)this.getSessionObject(request,CDE_COMPARE_LIST);
    
    if(newDisplayOrderArr!=null&&cdeList!=null)
      {
        cdeList.setItemOrder(newDisplayOrderArr);
      }    
    
    return mapping.findForward(SUCCESS);
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
 private DataElement getDataElementDetails(DataElement de,String sessionId) throws Exception
 {
       DataElementHandler dh = (DataElementHandler) HandlerFactory.getHandler(DataElement.class);
       de = (DataElement) dh.findObject(de.getDeIdseq(), sessionId);
       if(de!=null)
       {
        ValidValueHandler validValueHandler =
            (ValidValueHandler)HandlerFactory.getHandler(ValidValue.class);
        List vvList = validValueHandler.getValidValues(de.getVdIdseq()
                                                   ,sessionId);
        de.getValueDomain().setValidValues(vvList);   
       }
       

      ClassificationHandler classificationHandler =
          (ClassificationHandler)HandlerFactory.getHandler(Classification.class);
      List classificationSchemes = classificationHandler.getClassificationSchemes(
                              de.getDeIdseq(),sessionId);
      de.setClassifications(classificationSchemes);
       
      ServiceLocator locator = 
      ServiceLocatorFactory.getLocator("gov.nih.nci.ncicb.cadsr.servicelocator.ejb.ServiceLocatorImpl");
      AbstractDAOFactory daoFactory = AbstractDAOFactory.getDAOFactory(locator);
      DerivedDataElementDAO ddeDAO = daoFactory.getDerivedDataElementDAO();
      DerivedDataElement dde = ddeDAO.findDerivedDataElement(de.getDeIdseq());
      de.setDerivedDataElement(dde);
    return de;      
 }
}
