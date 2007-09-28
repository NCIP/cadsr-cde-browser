/**
 *
 * This class is the Action class for Admin functions
  *
 * @release 3.0
 * @date: 8/16/2005
 */
package gov.nih.nci.ncicb.cadsr.admin.actions;



import gov.nih.nci.ncicb.cadsr.struts.common.BaseDispatchAction;
import gov.nih.nci.ncicb.cadsr.util.CDEBrowserParams;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


public class AdminAction extends BaseDispatchAction {

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
  public ActionForward cdebrowser(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    DynaActionForm compareForm = (DynaActionForm)form;

    try
    {
      CDEBrowserParams.reloadInstance(request.getRemoteUser());
      saveMessage("cadsr.admin.cdebrowser.reload.properties.success", request);
    }
    catch (Exception e)
    {
      saveError("cadsr.admin.cdebrowser.reload.properties.fail", request);
    }
    
    
    return mapping.findForward(SUCCESS);
  }

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
