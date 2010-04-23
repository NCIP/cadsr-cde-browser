package gov.nih.nci.ncicb.cadsr.cdebrowser.struts.actions;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECartItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class CDECartAction extends BrowserBaseDispatchAction {
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
			NCIUser user = (NCIUser) this.getSessionObject(request, CaDSRConstants.USER_KEY);	      
			if(user!= null){				
				return mapping.findForward("secureSuccess");
			}			
			//Get the cart in the session
			ArrayList<CDECart> sessionCarts = (ArrayList<CDECart>) this.getSessionObject(request, CaDSRConstants.CDE_CART);
			int i = 0;
			for (CDECart sessionCart: sessionCarts) {
				
				Collection<CDECartItem> cartItems = sessionCart.getDataElements();
				Collection<CDECartItem> items = new ArrayList<CDECartItem> ();
				
				for(Object o:cartItems){
					CDECartItem item = (CDECartItem)o;
					item.setPersistedInd(false);
					items.add(item);
				}
				sessionCart.mergeDataElements(items);
				sessionCarts.set(i, sessionCart);
				i++;
			}
			
			this.setSessionObject(request, CaDSRConstants.CDE_CART, sessionCarts);			
		}catch (Exception exp) {
			if (log.isErrorEnabled()) {
				log.error("Exception on displayCDECart.....", exp);
			}
			exp.printStackTrace();			
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
		try{
			Collection items = new ArrayList();		
			NCIUser user = (NCIUser) this.getSessionObject(request, CaDSRConstants.USER_KEY);	      
			ArrayList<CDECart> sessionCarts = (ArrayList<CDECart>)this.getSessionObject(request, CaDSRConstants.CDE_CART);


			if(user!= null){
				return mapping.findForward("deleteSuccess");
			}
			CDECart sessionCart = sessionCarts.get(0);
			CDECartFormBean myForm = (CDECartFormBean) form;
			String[] selectedDeleteItems = {};
			selectedDeleteItems = myForm.getSelectedItems();
			for (int i = 0; i < selectedDeleteItems.length; i++) {
				items.add(selectedDeleteItems[i]);
			}		
			sessionCart.removeDataElements(items);
		}catch (Exception e){
			if (log.isErrorEnabled()) {
				log.error("Exception on removeItems.....", e);
			}
			e.printStackTrace();
		}
		return mapping.findForward(SUCCESS);
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
		String[] selectedSaveItems = {};
		if(myForm.getSelectedItems()!= null && myForm.getSelectedItems().length > 0){
			selectedSaveItems = myForm.getSelectedItems();			
			myForm.setSelectedItems(null);
		}else{
			selectedSaveItems = myForm.getSelectedSaveItems();
		}		
		myForm.setSelectedSaveItems(selectedSaveItems);		

		return mapping.findForward("saveSuccess");
	}	
	
	/**
	 * Add new Cart.
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
	public ActionForward addNewCart(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		CDECartFormBean myForm = (CDECartFormBean) form;
		String[] selectedSaveItems = {};
		if(myForm.getSelectedItems()!= null && myForm.getSelectedItems().length > 0){
			selectedSaveItems = myForm.getSelectedItems();			
			myForm.setSelectedItems(null);
		}else{
			selectedSaveItems = myForm.getSelectedSaveItems();
		}		
		myForm.setSelectedSaveItems(selectedSaveItems);		

		return mapping.findForward("newCartSuccess");
	}
	
	/**
	 * Delete Cart.
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
	public ActionForward deleteCart(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		CDECartFormBean myForm = (CDECartFormBean) form;
		

		return mapping.findForward("deleteCartSuccess");
	}
}
