package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
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
   * This is the main action called from the Struts framework.
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
    String formLongName = (String)searchForm.get("formLongName");
    String protocolIdSeq = (String)searchForm.get("protocolIdSeq");
    String contextIdSeq = (String)searchForm.get("contextIdSeq");
    String workflow = (String)searchForm.get("workflow");
    String categoryName = (String)searchForm.get("categoryName");
    String type = (String)searchForm.get("type");    
    Collection forms = service.getAllForms(formLongName,protocolIdSeq,contextIdSeq, 
                        workflow,categoryName,type);
    setSessionObject(request,"FormSearchResults",forms);
    System.out.println("In ActionForm");
    return mapping.findForward("success");
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
       return mapping.findForward("defaultHome");  
  }
  
}