/**
 * CompareCDEAction
 *
 * This class is the Action class for comparing CDEs side by side
  *
 * @release 3.0
 * @author: <a href=”mailto:jane.jiang@oracle.com”>Jane Jiang</a>
 * @date: 8/16/2005
 * @version: $Id: CompareCDEAction.java,v 1.1 2004-09-03 20:03:02 jiangja Exp $
 */
package gov.nih.nci.ncicb.cadsr.cdebrowser.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions.FormBuilderBaseDispatchAction;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;

import java.io.IOException;

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

}
