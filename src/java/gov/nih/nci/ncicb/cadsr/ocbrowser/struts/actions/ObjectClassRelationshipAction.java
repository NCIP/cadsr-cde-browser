package gov.nih.nci.ncicb.cadsr.ocbrowser.struts.actions;
import gov.nih.nci.cadsr.domain.AdministeredComponentClassSchemeItem;
import gov.nih.nci.cadsr.domain.Definition;
import gov.nih.nci.cadsr.domain.DefinitionClassSchemeItem;
import gov.nih.nci.cadsr.domain.Designation;
import gov.nih.nci.cadsr.domain.DesignationClassSchemeItem;
import gov.nih.nci.cadsr.domain.ObjectClass;
import gov.nih.nci.cadsr.domain.ObjectClassRelationship;
import gov.nih.nci.ncicb.cadsr.common.jsp.bean.OCRNavigationBean;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.service.OCBrowserService;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.struts.common.OCBrowserFormConstants;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.struts.common.OCBrowserNavigationConstants;
import gov.nih.nci.ncicb.cadsr.common.ocbrowser.util.OCUtils;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocatorException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ObjectClassRelationshipAction extends OCBrowserBaseDispatchAction
implements OCBrowserFormConstants,OCBrowserNavigationConstants
{

	protected static Log log = LogFactory.getLog(ObjectClassRelationshipAction.class.getName());

	/**
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
	public ActionForward getObjectClassRelationships(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		DynaActionForm dynaForm = (DynaActionForm) form;
		String obcIdSeq = (String) dynaForm.get(OC_IDSEQ);
		String cscsiIdSeq = request.getParameter("cscsiId");;
		if(cscsiIdSeq == null){
			cscsiIdSeq = (String)request.getSession().getAttribute("cscsiId");
		}
		//System.out.println("--- ocr for With object class " + obcIdSeq);
		//System.out.println("--- ocr for cscsi with Id: "+cscsiIdSeq);
		/*if (log.isDebugEnabled()) {
    		log.info("ocr for With object class " + obcIdSeq);
    	}*/
		try {

			OCBrowserService service = this.getApplicationServiceLocator().findOCBrowserService();

			List ocrs = service.getAssociationsForOC(obcIdSeq);
			Map ocrMap = null;
			//Adding Filter if OCB invoked from UMLB
			if(!(cscsiIdSeq == null || ("").equals(cscsiIdSeq.trim()))){
				List ocrFinalList = new ArrayList();
				//System.out.println("---Size of ocrs: "+ocrs.size());
				for (Object o:ocrs){		    	  
					ObjectClassRelationship ocr = (ObjectClassRelationship)o;
					Collection ocrACCSI = ocr.getAdministeredComponentClassSchemeItemCollection();		    	  
					for (Object ob:ocrACCSI){		    		  
						AdministeredComponentClassSchemeItem accsi = (AdministeredComponentClassSchemeItem)ob;
						if (cscsiIdSeq.equalsIgnoreCase(accsi.getClassSchemeClassSchemeItem().getId())){
							ocrFinalList.add(ocr);
						}
					}	    	  
				}
				//System.out.println("---Size of ocrs: "+ocrs.size()+" ---Size of ocrFinalList: "+ocrFinalList.size());
				ocrMap = OCUtils.sortByOCRTypes(ocrFinalList,obcIdSeq);
				//End of filter
			}else{
				//System.out.println("--- Ocrs List: "+ocrs.size());
				ocrMap = OCUtils.sortByOCRTypes(ocrs,obcIdSeq);
			}

			ObjectClass objClass = service.getObjectClass(obcIdSeq);

			//Adding Filter if OCB invoked from UMLB
			if(!(cscsiIdSeq == null || ("").equals(cscsiIdSeq.trim()))){
				//--Filtering Classification 
				Collection acCSICollection = new ArrayList();
				//System.out.println("---Size of objClass.getAdministeredComponentClassSchemeItemCollection() : "+objClass.getAdministeredComponentClassSchemeItemCollection().size());
				for (Object o: objClass.getAdministeredComponentClassSchemeItemCollection()){
					AdministeredComponentClassSchemeItem accsi = (AdministeredComponentClassSchemeItem)o;
					if(cscsiIdSeq.equalsIgnoreCase(accsi.getClassSchemeClassSchemeItem().getId())){
						acCSICollection.add(accsi);
					}
				}
				//System.out.println("----Size of acCSICollection: "+acCSICollection.size());      
				objClass.setAdministeredComponentClassSchemeItemCollection(acCSICollection);
				//-- End of Classification Filter
				//-- Filtering Alternate Names
				Collection designationCollection = new ArrayList();    	  
				//System.out.println("---Size of objectClass.getDesignationCollection(): "+objClass.getDesignationCollection().size());
				for(Object ob: objClass.getDesignationCollection()){
					Designation desg = (Designation)ob;
					for(Object ob1: desg.getDesignationClassSchemeItemCollection()){
						DesignationClassSchemeItem desCSI = (DesignationClassSchemeItem)ob1;
						if(cscsiIdSeq.equalsIgnoreCase(desCSI.getClassSchemeClassSchemeItem().getId())){
							designationCollection.add(desg);
						}
					}
				}
				//System.out.println("----Size of designationCollection"+designationCollection.size()); 
				objClass.setDesignationCollection(designationCollection);
				//-- End of Alternate Names Filter
				//-- Filtering Alternate Definitions
				Collection definitionCollection = new ArrayList();
				//System.out.println("--Size of objectClass.getDefinitionCollection(): "+objClass.getDefinitionCollection().size());
				for (Object objDef: objClass.getDefinitionCollection()){
					Definition def = (Definition)objDef ;    		  
					for(Object obj1: def.getDefinitionClassSchemeItemCollection()){
						DefinitionClassSchemeItem defCSI = (DefinitionClassSchemeItem)obj1;
						if(cscsiIdSeq.equalsIgnoreCase(defCSI.getClassSchemeClassSchemeItem().getId())){
							definitionCollection.add(def);
						}
					}    		  
				}
				//System.out.println("--- Size of definitionCollection: "+definitionCollection.size());
				objClass.setDefinitionCollection(definitionCollection);
				setSessionObject(request,"cscsiId",cscsiIdSeq,true);
				//-- End Alternate Definitions
			}//End of filter


			setSessionObject(request,OBJECT_CLASS,objClass,true);
			setSessionObject(request,OUT_GOING_OCRS,ocrMap.get(OUT_GOING_OCRS),true);
			setSessionObject(request,IN_COMMING_OCRS,ocrMap.get(IN_COMMING_OCRS),true);
			setSessionObject(request,BIDIRECTIONAL_OCRS,ocrMap.get(BIDIRECTIONAL_OCRS),true);
		}
		catch (ServiceLocatorException exp) {
			if (log.isErrorEnabled()) {
				log.error("Exception on getObjectClassRelationships obid= "+obcIdSeq );
			}
			return mapping.findForward(FAILURE);
		}    
		return mapping.findForward(SUCCESS);
	}

	/**
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
	public ActionForward clearNavigationPath(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		setSessionObject(request,OCR_NAVIGATION_BEAN,null);


		return mapping.findForward(SUCCESS);
	}

	/**
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
	public ActionForward navigateOCR(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		DynaActionForm dynaForm = (DynaActionForm) form;
		String obcIdSeq = (String) dynaForm.get(OC_IDSEQ);
		String ocrIndex = (String) dynaForm.get(OCR_INDEX);
		String direction = (String) dynaForm.get(OCR_DIRECTION);
		Integer crumbsIndex = (Integer) dynaForm.get(OCR_BR_CRUMBS_INDEX);
		if (log.isDebugEnabled()) {
			log.info("ocr for With object class " + obcIdSeq);
			log.info("ocr index " + ocrIndex);
			log.info("direction " + direction);
		}

		try {

			//Get ocr navigation crumbs
			LinkedList crumbs = (LinkedList)getSessionObject(request,OCR_NAVIGATION_BEAN);
			if(crumbs==null)
			{
				crumbs = new LinkedList();
				setSessionObject(request,OCR_NAVIGATION_BEAN,crumbs,true);
			}
			if(crumbs.isEmpty())
			{
				OCRNavigationBean bean = new OCRNavigationBean();
				ObjectClass currObjectClass = (ObjectClass)getSessionObject(request,OBJECT_CLASS);
				bean.setObjectClass(currObjectClass);
				crumbs.add(bean);
			}
			else
			{
				//Set the OCR_NAVIGATION_BEAN to current navigation path
				int currSize = crumbs.size();
				int currIndex = crumbsIndex.intValue();
				boolean nodesRemoved = false;
				for(int i=currIndex;i<currSize-1;++i)
				{
					crumbs.removeLast();
					nodesRemoved=true;
				}
				if(nodesRemoved)
				{
					OCRNavigationBean newLastNavBean = (OCRNavigationBean)crumbs.getLast();
					newLastNavBean.setOcr(null);
					newLastNavBean.setShowDirection(false);
				}
			}
			OCRNavigationBean lastNavBean = (OCRNavigationBean)crumbs.getLast();
			//Make sure same object is not navigated // need review
			if(lastNavBean.getObjectClass().getId()!=obcIdSeq)
			{
				//get the list of ocrs depending on the direction clicked
				List oldList= (List)getSessionObject(request,direction);
				ObjectClassRelationship navigatedOCR = (ObjectClassRelationship)oldList.get(Integer.parseInt(ocrIndex));

				OCBrowserService service = this.getApplicationServiceLocator().findOCBrowserService();

				ObjectClass objClass = service.getObjectClass(obcIdSeq);
				List ocrs = service.getAssociationsForOC(obcIdSeq);
				//Set the current OCRID
				dynaForm.set(CURR_OCR_IDSEQ,navigatedOCR.getId());


				Map ocrMap = OCUtils.sortByOCRTypes(ocrs,obcIdSeq);

				setSessionObject(request,OBJECT_CLASS,objClass,true);
				setSessionObject(request,OUT_GOING_OCRS,ocrMap.get(OUT_GOING_OCRS),true);
				setSessionObject(request,IN_COMMING_OCRS,ocrMap.get(IN_COMMING_OCRS),true);
				setSessionObject(request,BIDIRECTIONAL_OCRS,ocrMap.get(BIDIRECTIONAL_OCRS),true);

				//Update old bean
				lastNavBean.setOcr(navigatedOCR);
				lastNavBean.setDirection(direction);
				lastNavBean.setShowDirection(true);

				//Add new link
				OCRNavigationBean bean = new OCRNavigationBean();
				bean.setObjectClass(objClass);
				crumbs.add(bean);
				//set the crumbs index
				dynaForm.set(OCR_BR_CRUMBS_INDEX,new Integer(crumbs.size()-1));              
			}
		}
		catch (ServiceLocatorException exp) {
			if (log.isErrorEnabled()) {
				log.error("Exception on getObjectClassRelationships obid= "+obcIdSeq );
			}
			return mapping.findForward(FAILURE);
		}
		return mapping.findForward(SUCCESS);
	}
}