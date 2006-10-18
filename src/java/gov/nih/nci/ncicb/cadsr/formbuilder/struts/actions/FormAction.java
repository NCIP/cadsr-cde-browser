package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.util.StringPropertyComparator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;


public class FormAction extends FormBuilderSecureBaseDispatchAction {

    /**
     * set a session object.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @param name
     * @return
     * @throws Exception
     */
    protected ActionForward dispatchMethod(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response,
      String name) throws Exception {
          ActionForward forward = super.dispatchMethod(mapping, form, request, response, name);
          request.getSession().setAttribute("Initialized", "true");
      return forward;
      }


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
    String proptocolName = (String)searchForm.get(this.PROTOCOLS_LOV_NAME_FIELD);
    String contextIdSeq = (String) searchForm.get(this.CONTEXT_ID_SEQ);
    String workflow = (String) searchForm.get(this.WORKFLOW);
    String categoryName = (String) searchForm.get(this.CATEGORY_NAME);
    String type = (String) searchForm.get(this.FORM_TYPE);
    String csCsiIdSeq = (String) searchForm.get(this.CS_CSI_ID);
    String csIdseq = (String) searchForm.get(this.CS_ID);
    String publicId = (String)searchForm.get(this.PUBLIC_ID);
    String version = (String)searchForm.get(this.LATEST_VERSION_INDICATOR);
    String moduleLongName = (String)searchForm.get(this.MODULE_LONG_NAME);
    String cdePublicId = (String)searchForm.get(this.CDE_PUBLIC_ID); 

   //Set the Context Name
   
   List contexts = (List)this.getSessionObject(request,this.ALL_CONTEXTS);
   Context currContext = getContextForId(contexts,contextIdSeq);
   if(currContext!=null)
    searchForm.set(this.CONTEXT_NAME,currContext.getName());
   
   Collection forms = null;
   String nodeType = request.getParameter("P_PARAM_TYPE");
    if(nodeType!=null&&nodeType.equals("CLASSIFICATION"))
    {
      forms = service.getAllFormsForClassification(csIdseq);
    }
    else if(nodeType!=null&&nodeType.equals("PUBLISHING_PROTOCOL"))
    {
      forms = service.getAllPublishedFormsForProtocol(protocolIdSeq);
    }
    else
    {
    forms =
      service.getAllForms(
        formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName, type,
        csCsiIdSeq,
        publicId, version, moduleLongName,cdePublicId,
        (NCIUser)getSessionObject(request,this.USER_KEY));

    }
    setSessionObject(request, this.FORM_SEARCH_RESULTS, forms,true);    
    //Initialize and add the PagenationBean to the Session
    PaginationBean pb = new PaginationBean();

    if (forms != null) {
      pb.setListSize(forms.size());
    }
    Form aForm = null;
    if(forms.size()>0)
    {
      Object[] formArr = forms.toArray();
      aForm=(Form)formArr[0];
      StringPropertyComparator comparator = new StringPropertyComparator(aForm.getClass());
      comparator.setPrimary("longName");
      comparator.setOrder(comparator.ASCENDING);
      Collections.sort((List)forms,comparator);
      setSessionObject(request,FORM_SEARCH_RESULT_COMPARATOR,comparator);      
    }
      
    setSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION, pb,true);
    setSessionObject(request, ANCHOR, "results",true);  
    return mapping.findForward(SUCCESS);
  }

  /**
   * Clear the cache for a new search.
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
  public ActionForward newSearch(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    //Set the lookup values in the session
    setInitLookupValues(request);
    DynaActionForm searchForm = (DynaActionForm) form;
    FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;
    formBean.clear();
    removeSessionObject(request, this.FORM_SEARCH_RESULTS);
    removeSessionObject(request, this.FORM_SEARCH_RESULTS_PAGINATION);
    return mapping.findForward(SUCCESS);
  }
  
    /**
   * Sorts search results by fieldName
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
  public ActionForward sortResult(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    //Set the lookup values in the session
    setInitLookupValues(request);
    DynaActionForm searchForm = (DynaActionForm) form;
    String sortField = (String) searchForm.get("sortField");
    Integer sortOrder = (Integer) searchForm.get("sortOrder");
    List forms = (List)getSessionObject(request,FORM_SEARCH_RESULTS);
    StringPropertyComparator comparator = (StringPropertyComparator)getSessionObject(request,FORM_SEARCH_RESULT_COMPARATOR);
    comparator.setRelativePrimary(sortField);
    comparator.setOrder(sortOrder.intValue());
    //Initialize and add the PagenationBean to the Session
    PaginationBean pb = new PaginationBean();
    if (forms != null) {
      pb.setListSize(forms.size());
    }
    Collections.sort(forms,comparator);
    setSessionObject(request, FORM_SEARCH_RESULTS_PAGINATION, pb,true);
    setSessionObject(request, ANCHOR, "results",true);  
    return mapping.findForward(SUCCESS);
  }
  
  /**
   * Sets the parameters into a map. This action method need to called before
   * forwarding framed jsps since each frame has its own request object.
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
  public ActionForward initMessageKeys(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    Enumeration names = request.getAttributeNames();
    Map params = new HashMap();
    ActionMessages messages =
      (ActionMessages) request.getAttribute(Globals.MESSAGE_KEY);

    ActionErrors errors =
      (ActionErrors) request.getAttribute(Globals.ERROR_KEY);

    if ((messages != null) && !messages.isEmpty()) {
      Iterator mesgIt = messages.get(ActionMessages.GLOBAL_MESSAGE);
      StringBuffer strBuff = new StringBuffer();

      while (mesgIt.hasNext()) {
        ActionMessage mesg = (ActionMessage) mesgIt.next();
        strBuff.append(mesg.getKey());

        if (mesgIt.hasNext()) {
          strBuff.append(",");
        }
      }      
     params.put(ActionMessages.GLOBAL_MESSAGE, strBuff.toString());
    }
    if ((errors != null) && !errors.isEmpty()) {
      Iterator errorIt = errors.get(ActionErrors.GLOBAL_ERROR);
      StringBuffer strBuff = new StringBuffer();

      while (errorIt.hasNext()) {
        ActionError error = (ActionError)errorIt.next();
        strBuff.append(error.getKey());

        if (errorIt.hasNext()) {
          strBuff.append(",");
        }
      }      

      params.put(ActionErrors.GLOBAL_ERROR, strBuff.toString());
    }
    request.setAttribute("requestMap", params);

    return mapping.findForward(SUCCESS);
  }

  /**
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
  public ActionForward setMessagesFormKeys(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    Map params = new HashMap();
    String stringMessages = request.getParameter(ActionMessages.GLOBAL_MESSAGE);

    if ((stringMessages != null) && !stringMessages.equals("")) {
      String[] mesgArr = StringUtils.tokenizeCSVList(stringMessages);
  
      for (int i = 0; i < mesgArr.length; i++) {
        this.saveMessage(mesgArr[i], request);
      }
    }

    String stringErrors = request.getParameter(ActionErrors.GLOBAL_ERROR);
    if ((stringErrors != null) && !stringErrors.equals("")) {
      String[] errorArr = StringUtils.tokenizeCSVList(stringErrors);
  
      for (int i = 0; i < errorArr.length; i++) {
        this.saveError(errorArr[i], request);
      }
    }

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
    setInitLookupValues(request);
    try {
       Object displayOrderToCopy = getSessionObject(request,MODULE_DISPLAY_ORDER_TO_COPY);
       
       if (displayOrderToCopy != null) {
          return mapping.findForward("setModuleCopyForm");
       }
      setFormForAction(form, request);

    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception getting CRF", exp);
      }      
      saveError(ERROR_FORM_RETRIEVE, request);
      saveError(ERROR_FORM_DOES_NOT_EXIST, request);
      return mapping.findForward(FAILURE);
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
  public ActionForward getPrinterVersion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    try {
      Form crf = (Form) this.getSessionObject(request, CRF);

      if (form == null) {
        setFormForAction(form, request);
      }
    }
    catch (FormBuilderException exp) {
      saveError(ERROR_FORM_RETRIEVE, request);
      saveError(ERROR_FORM_DOES_NOT_EXIST, request);
      if (log.isErrorEnabled()) {
        log.error("Exception on getFormForEdit ", exp);
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
    String protocolLongName = "";
    String formIdSeq = "";
    String csCsiIdSeq = "";
    String csIdseq = "";    
    String nodeType = request.getParameter("P_PARAM_TYPE");
    String nodeIdSeq = request.getParameter("P_IDSEQ");
    String contextIdSeq = request.getParameter("P_CONTE_IDSEQ");
    if (contextIdSeq == null) contextIdSeq = "";
    String csiName = "";

    if ("PROTOCOL".equals(nodeType)||"PUBLISHING_PROTOCOL".equals(nodeType)) {
      protocolIdSeq = nodeIdSeq;
      protocolLongName = request.getParameter("protocolLongName");
    }
    else if ("CRF".equals(nodeType) || "TEMPLATE".equals(nodeType))
      formIdSeq = nodeIdSeq;
    else if ("CSI".equals(nodeType)) {
      csCsiIdSeq = nodeIdSeq;
      csiName = request.getParameter("csiName");
      //Publishing Change Order
      contextIdSeq = "";
    }
    //Publish Change Order
    else if("CLASSIFICATION".equals(nodeType))
    {
      csIdseq=nodeIdSeq;
      contextIdSeq = "";
    }

    FormBuilderBaseDynaFormBean searchForm = (FormBuilderBaseDynaFormBean) form;
    searchForm.clear();
    searchForm.set(this.PROTOCOL_ID_SEQ, protocolIdSeq);
    searchForm.set(this.PROTOCOLS_LOV_NAME_FIELD, protocolLongName);
    searchForm.set(this.CONTEXT_ID_SEQ, contextIdSeq);
    searchForm.set(this.FORM_ID_SEQ,formIdSeq);
    searchForm.set("jspClassification",csCsiIdSeq);
    searchForm.set("txtClassSchemeItem",csiName);
    //Publish Change Order
    searchForm.set(this.CS_ID,nodeIdSeq);
    
    setSessionObject(request, ANCHOR, "results",true);  
    
    if ("CRF".equals(nodeType) || "TEMPLATE".equals(nodeType))
      return this.getFormDetails(mapping, form, request, response);
    else
      return this.getAllForms(mapping, form, request, response);
      
    
  }
  
  /**
   * Clear the screen for a new Search
   * Clears the struts form and also the resultSet of the previous search
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
  public ActionForward clearFormSearch(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    
    FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;
    formBean.clear();
    formBean.set(FormConstants.LATEST_VERSION_INDICATOR, FormConstants.LATEST_VERSION);  //default 
    removeSessionObject(request,FORM_SEARCH_RESULTS);
    return mapping.findForward(SUCCESS);
    }
  private Context getContextForId(List contexts,String contextIdSeq)
  {
    if(contexts==null||contextIdSeq==null)
      return null;
    ListIterator listIt = contexts.listIterator();
    while(listIt.hasNext())
    {
      Context currContext = (Context)listIt.next();
      if(currContext.getConteIdseq().equals(contextIdSeq))
      {
        return currContext;
      }
    }
    return null;
  }
}
  
