package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants;

import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import java.util.List;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.actions.DispatchAction;

public class PaginationAction extends FormBuilderBaseDispatchAction
{
  /**
   * Test Method adds a Collection to session.
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
  public ActionForward init(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    
    ArrayList list = new ArrayList();
    list.add("1");
    list.add("2");
    list.add("3");
    list.add("4");
    list.add("5");
    list.add("6");
    list.add("7");
    list.add("8");
    list.add("9");
    list.add("10");
    list.add("11");
    list.add("12");
    setSessionObject(request, this.FORM_SEARCH_RESULTS, list);
    PaginationBean pb = new PaginationBean();
    pb.setListSize(list.size());
    setSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION, pb,true);
    return mapping.findForward(SUCCESS);
    }
  
      /**
   * Sets the PagenationBean to next page.
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
  public ActionForward next(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    PaginationBean pb = (PaginationBean)getSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION);
    pb.next();
    setSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION, pb,true);
    setSessionObject(request, ANCHOR, "results",true);  
    return mapping.findForward(SUCCESS);
    }
    
   /* Sets the PagenationBean to previous page.
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
  public ActionForward previous(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    PaginationBean pb = (PaginationBean)getSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION);
    pb.previous();
    setSessionObject(request, ANCHOR, "results",true);  
    setSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION, pb,true);
    return mapping.findForward(SUCCESS);
    }    
   /* Sets the PagenationBean to new page index.
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
  public ActionForward setPageIndex(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    
    String pageIndexStr = request.getParameter(PAGEINDEX);
    int pageIndex=0;
    if(pageIndexStr!=null)
    {
      pageIndex= new Integer(pageIndexStr).intValue();
    }
    PaginationBean pb = (PaginationBean)getSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION);
    pb.setPageIndex(pageIndex);
    setSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION, pb,true);
    setSessionObject(request, ANCHOR, "results",true);  
    return mapping.findForward(SUCCESS);
    }        
}