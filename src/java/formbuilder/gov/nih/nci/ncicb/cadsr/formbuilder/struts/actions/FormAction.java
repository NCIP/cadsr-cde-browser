package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Form;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class FormAction extends FormBuilderBaseDispatchAction {
  /**
   * Returns all forms for the given criteria.
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
  public ActionForward getAllForms(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    //Set the lookup values in the session
    setInitLookupValues(request);

    FormBuilderServiceDelegate service = getFormBuilderService();
    DynaActionForm searchForm = (DynaActionForm) form;
    String formLongName = (String) searchForm.get(this.FORM_LONG_NAME);
    String protocolIdSeq = (String) searchForm.get(this.PROTOCOL_ID_SEQ);
    String contextIdSeq = (String) searchForm.get(this.CONTEXT_ID_SEQ);
    String workflow = (String) searchForm.get(this.WORKFLOW);
    String categoryName = (String) searchForm.get(this.CATEGORY_NAME);
    String type = (String) searchForm.get(this.FORM_TYPE);
    String classificationIdSeq = (String) searchForm.get(this.CS_CSI_ID);

    Collection forms = null;

    forms =
      service.getAllForms(
        formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName, type,
        classificationIdSeq);
    setSessionObject(request, this.FORM_SEARCH_RESULTS, forms);

    //Initialize and add the PagenationBean to the Session
    PaginationBean pb = new PaginationBean();

    if (forms != null) {
      pb.setListSize(forms.size());
    }

    setSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION, pb);

    return mapping.findForward(SUCCESS);
  }

  /**
   * Returns Complete form given an Id.
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
  public ActionForward getFormDetails(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    try
    {
      setFormForAction(form,request);
    }
    catch (FormBuilderException exp)
    {
      if (log.isDebugEnabled()) {
        log.debug("Exception on getFormBy Id =  " + exp);
      }      
    }
    return mapping.findForward(SUCCESS);
  }

  /**
   * Returns Complete form given an Id for Edit.
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
  public ActionForward getFormToEdit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    try
    {
      setFormForAction(form,request);
    }
    catch (FormBuilderException exp)
    {
      if (log.isDebugEnabled()) {
        log.debug("Exception on getFormForEdit =  " + exp);
      }      
    }
    return mapping.findForward(SUCCESS);
  }

  /**
   * Returns Complete form given an Id for Copy.
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
  public ActionForward getFormToCopy(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    try
    {
      setFormForAction(form,request);
    }
    catch (FormBuilderException exp)
    {
      if (log.isDebugEnabled()) {
        log.debug("Exception on getFormForEdit =  " + exp);
      }      
    }
    return mapping.findForward(SUCCESS);
  }

  /**
   * Delete Form.
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
  public ActionForward deleteForm(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm hrefCRFForm = (DynaActionForm) form;
    String formIdSeq = (String) hrefCRFForm.get(FORM_ID_SEQ);
    
    if (log.isDebugEnabled())
        log.info("Delete Form With Id "+formIdSeq);
    return mapping.findForward(SUCCESS);
  }

  /**
   * Returns Complete form given an Id for Copy.
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
  public ActionForward getPrinterVersion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    try
    {
      Form crf = (Form)this.getSessionObject(request,CRF);
      if(form==null)
        setFormForAction(form,request);
    }
    catch (FormBuilderException exp)
    {
      if (log.isDebugEnabled()) {
        log.debug("Exception on getFormForEdit =  " + exp);
      }      
    }
    return mapping.findForward(SUCCESS);
  }

  /**
   * Returns all forms for the search criteria specified by clicking on a tree
   * node.
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
  public ActionForward getAllFormsForTreeNode(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    String protocolIdSeq = "";
    String nodeType = request.getParameter("P_PARAM_TYPE");
    String nodeIdSeq = request.getParameter("P_IDSEQ");
    String contextIdSeq = request.getParameter("P_CONTE_IDSEQ");

    if ("PROTOCOL".equals(nodeType)) {
      protocolIdSeq = nodeIdSeq;
    }

    FormBuilderBaseDynaFormBean searchForm = (FormBuilderBaseDynaFormBean) form;
    searchForm.clear();
    searchForm.set(this.PROTOCOL_ID_SEQ, protocolIdSeq);
    searchForm.set(this.CONTEXT_ID_SEQ, contextIdSeq);

    return this.getAllForms(mapping, form, request, response);
  }

  /**
   * This Action forwards to the default formbuilder home.
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
  public ActionForward sendHome(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    setInitLookupValues(request);

    return mapping.findForward(DEFAULT_HOME);
  }
  /**
   * If a iconForm(DynaForm) exist then get the FormDetails for the
   * formIdSeq is retrived. Otherwise it is assumed the specific form in session
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.* 
   */
  private void setFormForAction(ActionForm form,HttpServletRequest request) throws FormBuilderException
  {
    FormBuilderServiceDelegate service = getFormBuilderService();
    DynaActionForm hrefCRFForm = (DynaActionForm) form;
    Form crf = null;

    if (hrefCRFForm != null) {
      String formIdSeq = (String) hrefCRFForm.get(FORM_ID_SEQ);
      crf = service.getFormDetails(formIdSeq);
      setSessionObject(request, CRF, crf);  
    }
  }
}
