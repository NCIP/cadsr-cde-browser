package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.CDECartItemTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.CDECartTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import gov.nih.nci.ncicb.cadsr.resource.Module;
import java.util.Iterator;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import java.util.List;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;


public class SecureCDECartAction extends FormBuilderBaseDispatchAction {

    public ActionForward changeAssociation(
					   ActionMapping mapping,
					   ActionForm form,
					   HttpServletRequest request,
					   HttpServletResponse response) throws IOException, ServletException {

	CDECart cart = new CDECartTransferObject();
	    
	DynaActionForm dynaForm = (DynaActionForm)form;
	String selectedText = (String)dynaForm.get("selectedText");
	    
	int ind = selectedText.indexOf(',');
	int deIndex = Integer.parseInt(selectedText.substring(0, ind).trim());
	String newLongName = "";
	if(selectedText.length() > ind)
	    newLongName = selectedText.substring(ind+1).trim();

	int questionIndex = Integer.parseInt((String)dynaForm.get("questionIndex"));

	List questions = ((Module)getSessionObject(request, MODULE)).getQuestions();
	CDECart sessionCart =
	    (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);

	Collection col = sessionCart.getDataElements();
	ArrayList al = new ArrayList(col);
 	DataElement de = (DataElement)((CDECartItem)al.get(deIndex)).getItem();

	Question q = (Question)questions.get(questionIndex);
	    
 	q.setDataElement(de);
	q.setLongName(newLongName);

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
	CDECart cart = new CDECartTransferObject();

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
	    if (sessionCart != null)
		cart.mergeCart(sessionCart);

	    sessionCart = null;
	    this.setSessionObject(request,CaDSRConstants.CDE_CART,cart);
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
    public ActionForward removeItems(
				     ActionMapping mapping,
				     ActionForm form,
				     HttpServletRequest request,
				     HttpServletResponse response) throws IOException, ServletException {
	try {
	    FormBuilderServiceDelegate service = getFormBuilderService();
	    CDECartFormBean myForm = (CDECartFormBean) form;
	    String[] selectedDeleteItems = myForm.getSelectedDeleteItems();
	    Collection items = new ArrayList();

	    for (int i = 0; i < selectedDeleteItems.length; i++) {
		items.add(selectedDeleteItems[i]);
	    }

	    service.removeFromCDECart(items);
	}
	catch (FormBuilderException exp) {
	    if (log.isDebugEnabled()) {
		log.debug("Exception on addItems =  " + exp);
	    }
	}

	return mapping.findForward("addDeleteSuccess");
    }
}
