package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.NavigationConstants;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.struts.action.DynaActionForm;

public class FormAction extends FormBuilderBaseDispatchAction 
{
  /**
   * Returns all forms for the given criteria.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @return 
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward getAllForms(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
  
    FormBuilderServiceDelegate service = getFormBuilderService();
    DynaActionForm searchForm = (DynaActionForm) form;
    String formLongName = (String)searchForm.get(this.FORM_LONG_NAME);
    String protocolIdSeq = (String)searchForm.get(this.PROTOCOL_ID_SEQ);
    String contextIdSeq = (String)searchForm.get(this.CONTEXT_ID_SEQ);
    String workflow = (String)searchForm.get(this.WORKFLOW);
    String categoryName = (String)searchForm.get(this.CATEGORY_NAME);
    String type = (String)searchForm.get(this.FORM_TYPE);    
    Collection forms = service.getAllForms(formLongName,protocolIdSeq,contextIdSeq, 
                        workflow,categoryName,type);
    setSessionObject(request,this.FORM_SEARCH_RESULTS,forms);
    return mapping.findForward(SUCCESS);
  }

  /**
   * This Action forwards to the default formbuilder home.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   * @return 
   * @throws IOException
   * @throws ServletException
   */
  public ActionForward sendHome(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
       return mapping.findForward(DEFAULT_HOME);  
  }
  
}