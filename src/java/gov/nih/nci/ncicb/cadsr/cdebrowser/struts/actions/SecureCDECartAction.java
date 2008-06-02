package gov.nih.nci.ncicb.cadsr.cdebrowser.struts.actions;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorException;
import gov.nih.nci.ncicb.cadsr.common.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;
import gov.nih.nci.ncicb.cadsr.objectCart.impl.CDECartOCImpl;
import gov.nih.nci.objectCart.client.ClientManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class SecureCDECartAction extends BrowserSecureBaseDispatchAction {

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

		try {			
			String userName = getLoggedInUsername(request);			

			NCIUser user = (NCIUser) this.getSessionObject(request, CaDSRConstants.USER_KEY);

			CDECart sessionCart = (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
			CDECartOCImpl tempSessionCart = (CDECartOCImpl)sessionCart;

			ClientManager cManager = ClientManager.getInstance();
			CDECart userCart = null;
			if(cManager.isInitialized()){
				userCart = new CDECartOCImpl(cManager,userName,CaDSRConstants.CDE_CART,CaDSRConstants.CDE_CARTSCHEME);
			}
			if(userCart != null){
				sessionCart.mergeCart(userCart);   	  
			}      
			this.setSessionObject(request, CaDSRConstants.CDE_CART, sessionCart);
		}catch (ServiceLocatorException exp) {
			if (log.isErrorEnabled()) {
				log.error("Exception on displayCDECart", exp);
			}
			saveMessage(exp.getErrorCode(), request);      
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
			String userName = getLoggedInUsername(request);
			CDECart sessionCart = (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
			CDECartFormBean myForm = (CDECartFormBean) form;
			String[] selectedSaveItems = myForm.getSelectedSaveItems();
			Collection<CDECartItem> items = new ArrayList<CDECartItem> ();

			ClientManager cManager = ClientManager.getInstance();
			CDECart userCart = null;

			if(cManager.isInitialized()){
				userCart = new CDECartOCImpl(cManager,userName,CaDSRConstants.CDE_CART,CaDSRConstants.CDE_CARTSCHEME);
			}

			for (int i = 0; i < selectedSaveItems.length; i++) {
				CDECartItem cartItem = sessionCart.findDataElement(selectedSaveItems[i]);

				cartItem.setPersistedInd(true);
				items.add(cartItem);
			}      
			//sessionCart.mergeDataElements(items);      
			userCart.mergeDataElements(items);

			saveMessage("cadsr.cdecart.save.success",request);
		}catch (ServiceLocatorException exp) {
			if (log.isErrorEnabled()) {
				log.error("Exception on addItems " , exp);
			}
			saveMessage(exp.getErrorCode(), request);      
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
			String userName = getLoggedInUsername(request);

			CDECartFormBean myForm = (CDECartFormBean) form;
			String[] selectedDeleteItems = myForm.getSelectedDeleteItems();      
			Collection items = new ArrayList();

			//Get the cart in the session
			CDECart sessionCart = (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);
			CDECartItem item = null;

			ClientManager cManager = ClientManager.getInstance();
			CDECart userCart = null;      
			if(cManager.isInitialized()){
				userCart = new CDECartOCImpl(cManager,userName,CaDSRConstants.CDE_CART,CaDSRConstants.CDE_CARTSCHEME);
			}

			for (int i = 0; i < selectedDeleteItems.length; i++) {
				item = sessionCart.findDataElement(selectedDeleteItems[i]);        
				items.add(selectedDeleteItems[i]);
			}

			sessionCart.removeDataElements(items);      
			userCart.removeDataElements(items);

			saveMessage("cadsr.cdecart.delete.success",request);
		}
		catch (ServiceLocatorException exp) {
			if (log.isErrorEnabled()) {
				log.error("Exception on removeItems " , exp);
			}
			saveMessage(exp.getErrorCode(), request);      
		}    
		return mapping.findForward("addDeleteSuccess");
	} 

}
