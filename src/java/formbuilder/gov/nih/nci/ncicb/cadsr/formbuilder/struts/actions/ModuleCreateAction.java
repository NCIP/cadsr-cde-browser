package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import java.io.IOException;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleInstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.ModuleInstruction;


public class ModuleCreateAction extends FormBuilderBaseDispatchAction {


    /**
     * Prepares the Create Module page
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
    public ActionForward goToCreateModule(
				  ActionMapping mapping,
				  ActionForm form,
				  HttpServletRequest request,
				  HttpServletResponse response) throws IOException, ServletException {

	DynaActionForm dynaForm = (DynaActionForm)form;

	return mapping.findForward("toCreateModule");

    }


    /**
     * Create a module
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
    public ActionForward createModule(
				  ActionMapping mapping,
				  ActionForm form,
				  HttpServletRequest request,
				  HttpServletResponse response) throws IOException, ServletException {

	DynaActionForm dynaForm = (DynaActionForm)form;
	Module newModule = new ModuleTransferObject();
	ModuleInstruction newModInst = new ModuleInstructionTransferObject();

	Form f = (Form)getSessionObject(request, CRF);

	try
	    {
		newModule.setForm(f);
		newModule.setLongName((String)dynaForm.get(MODULE_LONG_NAME));
		newModule.setAslName("DRAFT NEW");
		newModule.setVersion(new Float(1.0));
		newModule.setCreatedBy(request.getRemoteUser());
		newModule.setDisplayOrder(((Integer)dynaForm.get(DISPLAY_ORDER)).intValue());

		newModInst.setLongName((String)dynaForm.get(MODULE_INSTRUCTION_LONG_NAME));
		newModInst.setAslName("DRAFT NEW");
		newModInst.setVersion(new Float(1.0));
		newModInst.setCreatedBy(request.getRemoteUser());
		
		FormBuilderServiceDelegate service = getFormBuilderService();
		service.createModule(newModule, newModInst);
	    }
	catch (FormBuilderException exp)
	    {
		if (log.isDebugEnabled()) {
		    log.debug("Exception on copyForm =  " + exp);
		}      
	    }
	return mapping.findForward("toFormEdit");

    }

}
  
