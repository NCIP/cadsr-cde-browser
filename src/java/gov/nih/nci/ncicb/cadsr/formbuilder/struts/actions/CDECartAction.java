package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.resource.impl.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CDECartAction extends FormBuilderBaseDispatchAction {
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
  public ActionForward displayCDECart(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    //CDECart cart = new CDECartTransferObject();
    CDECart cart = null;
    
    try {
      FormBuilderServiceDelegate service = getFormBuilderService();
      NCIUser user =
        (NCIUser) this.getSessionObject(request, CaDSRConstants.USER_KEY);

      //Get the cart in the session
      CDECart sessionCart =
        (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);

      if (user != null) {
        cart = service.retrieveCDECart(user.getUsername());

        //Merge two carts
        sessionCart.mergeCart(cart);
      }
    }
    catch (FormBuilderException exp) {
      if (log.isErrorEnabled()) {
        log.error("Exception on displayCDECart ", exp);
      }
      saveError(exp.getErrorCode(), request);
    }

    return mapping.findForward(SUCCESS);
  }

  /**
   * Delete items from the CDE Cart.
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
  public ActionForward removeItems(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    CDECartFormBean myForm = (CDECartFormBean) form;
    String[] selectedDeleteItems = myForm.getSelectedItems();
    Collection items = new ArrayList();

    //Get the cart in the session
    CDECart sessionCart =
      (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);

    for (int i = 0; i < selectedDeleteItems.length; i++) {
      items.add(selectedDeleteItems[i]);
    }

    sessionCart.removeDataElements(items);
    saveMessage("cadsr.cdecart.delete.success",request);

    return mapping.findForward("success");
  }

  /**
   * Add items to the CDE Cart.
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
  public ActionForward addItems(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    CDECartFormBean myForm = (CDECartFormBean) form;
    String[] selectedSaveItems = myForm.getSelectedItems();
    myForm.setSelectedSaveItems(selectedSaveItems);

    return mapping.findForward("saveSuccess");
  }
}
