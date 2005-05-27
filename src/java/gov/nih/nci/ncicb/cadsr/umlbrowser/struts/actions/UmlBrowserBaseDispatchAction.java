package gov.nih.nci.ncicb.cadsr.umlbrowser.struts.actions;
import gov.nih.nci.ncicb.cadsr.struts.common.BaseDispatchAction;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class UmlBrowserBaseDispatchAction extends BaseDispatchAction 
{

  /**
   * This Action forwards to the default umplbrowser home.
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

    return mapping.findForward("cdebrowserHome");
  }
  

    

}