package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.CDECartItemTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.CDECartTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.util.DTOTransformer;

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


public class SecureCDECartAction extends FormBuilderSecureBaseDispatchAction {
  public ActionForward addQuestion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm dynaForm = (DynaActionForm) form;
    String[] selectedItems = (String[]) dynaForm.get(SELECTED_ITEMS);

    CDECart sessionCart =
      (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);

    Form crf = (Form) getSessionObject(request, CRF);
    Module module = (Module) getSessionObject(request, MODULE);
    List questions = module.getQuestions();

    Collection col = sessionCart.getDataElements();
    ArrayList al = new ArrayList(col);

    int displayOrder = Integer.parseInt((String) dynaForm.get(QUESTION_INDEX));

    for (int i = 0; i < selectedItems.length; i++) {
      DataElement de =
        (DataElement) ((CDECartItem) al.get(Integer.parseInt(selectedItems[i]))).getItem();

      Question q = new QuestionTransferObject();
      module.setForm(crf);
      q.setModule(module);

      List values = de.getValueDomain().getValidValues();
      List newValidValues = DTOTransformer.toFormValidValueList(values, q);
      q.setQuesIdseq(new Date().getTime() + "" + i);
      q.setValidValues(newValidValues);
      q.setDataElement(de);
      q.setLongName(de.getLongName());
      q.setVersion(crf.getVersion());
      q.setAslName(crf.getAslName());
      q.setPreferredDefinition(de.getPreferredDefinition());
      q.setContext(crf.getContext());

      q.setDisplayOrder(displayOrder);

      if (displayOrder < questions.size()) {
        questions.add(displayOrder, q);

        FormActionUtil.incrementDisplayOrder(questions, displayOrder + 1);
      }
      else {
        questions.add(q);
      }
    }

    return mapping.findForward("success");
  }

  public ActionForward changeAssociation(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    CDECart cart = new CDECartTransferObject();

    DynaActionForm dynaForm = (DynaActionForm) form;
    String selectedText = (String) dynaForm.get("selectedText");

    DataElement de = null;
    List newValidValues = null;

    int questionIndex =
      Integer.parseInt((String) dynaForm.get("questionIndex"));

    List questions =
      ((Module) getSessionObject(request, MODULE)).getQuestions();
    CDECart sessionCart =
      (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
    Question q = (Question) questions.get(questionIndex);

    if ((selectedText == null) | (selectedText.length() == 0)) {
    }
    else {
      int ind = selectedText.indexOf(',');
      int deIndex = Integer.parseInt(selectedText.substring(0, ind).trim());
      String newLongName = "";

      if (selectedText.length() > ind) {
        newLongName = selectedText.substring(ind + 1).trim();
      }

      Collection col = sessionCart.getDataElements();
      ArrayList al = new ArrayList(col);

      de = (DataElement) ((CDECartItem) al.get(deIndex)).getItem();

      List values = de.getValueDomain().getValidValues();
      newValidValues = DTOTransformer.toFormValidValueList(values, q);

      q.setLongName(newLongName);
    }

    q.setDataElement(de);
    q.setValidValues(newValidValues);

    return mapping.findForward("success");
  }

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

      cart = service.retrieveCDECart();

      //Merge two carts
      //sessionCart.mergeCart(cart);
      if (sessionCart != null) {
        cart.mergeCart(sessionCart);
      }

      sessionCart = null;
      this.setSessionObject(request, CaDSRConstants.CDE_CART, cart);
    }
    catch (FormBuilderException exp) {
      if (log.isDebugEnabled()) {
        log.debug("Exception on displayCDECart =  " + exp);
      }
    }

    return mapping.findForward(SUCCESS);
  }

  /**
   * Adds items to CDE Cart.
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
    try {
      FormBuilderServiceDelegate service = getFormBuilderService();
      CDECartFormBean myForm = (CDECartFormBean) form;
      String[] selectedSaveItems = myForm.getSelectedSaveItems();
      Collection items = new ArrayList();

      for (int i = 0; i < selectedSaveItems.length; i++) {
        CDECartItem cartItem = new CDECartItemTransferObject();
        cartItem.setId(selectedSaveItems[i]);
        cartItem.setType("DATAELEMENT");
        items.add(cartItem);
      }

      service.addToCDECart(items);

      CDECart cart =
        (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
    }
    catch (FormBuilderException exp) {
      if (log.isDebugEnabled()) {
        log.debug("Exception on addItems =  " + exp);
      }
    }

    return mapping.findForward("addDeleteSuccess");
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
    try {
      FormBuilderServiceDelegate service = getFormBuilderService();
      CDECartFormBean myForm = (CDECartFormBean) form;
      String[] selectedDeleteItems = myForm.getSelectedDeleteItems();
      Collection savedItems = new ArrayList();

      //Collection unsavedItems = new ArrayList();
      Collection items = new ArrayList();

      //Get the cart in the session
      CDECart sessionCart =
        (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
      CDECartItem item = null;

      for (int i = 0; i < selectedDeleteItems.length; i++) {
        item = sessionCart.findDataElement(selectedDeleteItems[i]);

        if (item.getPersistedInd()) {
          savedItems.add(selectedDeleteItems[i]);
        }

        items.add(selectedDeleteItems[i]);
      }

      service.removeFromCDECart(savedItems);
      sessionCart.removeDataElements(items);
    }
    catch (FormBuilderException exp) {
      if (log.isDebugEnabled()) {
        log.debug("Exception on removeItems =  " + exp);
      }
    }

    return mapping.findForward("addDeleteSuccess");
  }
}
