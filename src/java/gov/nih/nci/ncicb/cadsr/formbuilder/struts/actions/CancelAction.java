package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CancelAction extends FormBuilderSecureBaseDispatchAction {
    public ActionForward getModuleToEdit(ActionMapping mapping,
        ActionForm form, HttpServletRequest request,
        HttpServletResponse response) throws IOException, ServletException {
        

       String questionIndexStr = (String)request.getParameter(QUESTION_INDEX);
       int questionIndex = 0;
       if(questionIndexStr!=null)
        questionIndex = Integer.parseInt(questionIndexStr);
    // Jump to the update location on the screen
      request.setAttribute(CaDSRConstants.ANCHOR,"Q"+questionIndex);
        
        return mapping.findForward("gotoModuleEdit");
    }

    public ActionForward getFormToEdit(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
       String displayOrderStr = (String)request.getParameter(DISPLAY_ORDER);
       int displayOrder = 0;
       if(displayOrderStr!=null)
        displayOrder = Integer.parseInt(displayOrderStr);
    // Jump to the update location on the screen
      request.setAttribute(CaDSRConstants.ANCHOR,"M"+displayOrder);

        
        return mapping.findForward("gotoFormEdit");
    }

    public ActionForward gotoSearch(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

//         ((DynaActionForm]) form).set(METHOD_PARAM, GET_ALL_FORMS_METHOD);

        return mapping.findForward("gotoSearch");
    }

    public ActionForward gotoManageClassifications(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

//         ((DynaActionForm]) form).set(METHOD_PARAM, GET_ALL_FORMS_METHOD);

        return mapping.findForward("gotoManageClassifications");
    }

}
