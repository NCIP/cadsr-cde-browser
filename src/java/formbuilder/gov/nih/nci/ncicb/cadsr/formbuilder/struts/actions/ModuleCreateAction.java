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

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleInstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.ModuleInstruction;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;


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

	int displayOrder = ((Integer)dynaForm.get(DISPLAY_ORDER)).intValue();
	
	Form f = (Form)getSessionObject(request, CRF);

	newModule.setForm(f);
	newModule.setLongName((String)dynaForm.get(MODULE_LONG_NAME));
	newModule.setAslName("DRAFT NEW");
	newModule.setVersion(new Float(1.0));
	newModule.setCreatedBy(request.getRemoteUser());
	newModule.setDisplayOrder(displayOrder);
	newModule.setQuestions(new ArrayList());
	
	newModInst.setLongName((String)dynaForm.get(MODULE_INSTRUCTION_LONG_NAME));
	newModInst.setAslName("DRAFT NEW");
	newModInst.setVersion(new Float(1.0));
	newModInst.setCreatedBy(request.getRemoteUser());

	Form crf = (Form) getSessionObject(request, CRF);
	
	List modules = crf.getModules();

	if(displayOrder < modules.size()) {
	    modules.add(displayOrder, newModule);
	    FormActionUtil.incrementDisplayOrder(modules, displayOrder + 1);
	} else {
	    modules.add(newModule);
	}

	return mapping.findForward("toFormEdit");

    }

}
  
