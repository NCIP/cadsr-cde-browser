package gov.nih.nci.ncicb.cadsr.cdebrowser.struts.actions;

import gov.nih.nci.ncicb.cadsr.common.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.common.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.common.struts.formbeans.CDECartFormBean;
import gov.nih.nci.ncicb.cadsr.objectCart.CDECart;

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
			CDECart sessionCart = (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);			
			this.setSessionObject(request, CaDSRConstants.CDE_CART, sessionCart);			
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
			CDECart sessionCart = (CDECart) this.getSessionObject(request, CaDSRConstants.CDE_CART);

			if(user!= null){
				return mapping.findForward("deleteSuccess");
			}
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
		}else{
			selectedSaveItems = myForm.getSelectedSaveItems();
		}		
		myForm.setSelectedSaveItems(selectedSaveItems);		

		return mapping.findForward("saveSuccess");
	}	
}
