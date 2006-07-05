package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;


import gov.nih.nci.ncicb.cadsr.domain.Question;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormConstants;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Context;
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


public class FormDesignationsAction
  extends FormBuilderSecureBaseDispatchAction {
  /**
   * Returns Complete form given an Id for Copy.
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
  public ActionForward getContexts(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    Form crf = (Form) getSessionObject(request, CRF);
    List cdeIdList = crf.getCDEIdList();
    if (cdeIdList!=null && !cdeIdList.isEmpty()){
        String contextIdSeq = null;
        Context context = crf.getContext();
        if (context!=null && context.getConteIdseq()!=null){
            contextIdSeq = context.getConteIdseq();  
        }
        try{
            FormBuilderServiceDelegate service = getFormBuilderService();
            Boolean result = service.isAllACDesignatedToContext(cdeIdList , contextIdSeq);
            if (result.booleanValue()){
                request.setAttribute(FormConstants.ALREADY_DESIGNATED, result);
            }    
            return mapping.findForward("success");
        }catch (FormBuilderException exp) {
              if (log.isErrorEnabled()) {
                log.error("Exception on service.isAllACDesignatedToContext ", exp);
              }

              saveError("cadsr.formbuilder.designation.fail", request);
              ActionForward forward =  mapping.findForward("failure");
              return forward;
        }     
    }else{
        saveMessage("cadsr.formbuilder.designation.nocde", request);
        return mapping.findForward("failure");
    }
  }


  public ActionForward saveDesignations(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    try{
        //DynaActionForm dynaForm = (DynaActionForm) form;
        //String cdeContextIdSeq = (String) dynaForm.get(CDE_CONTEXT_ID_SEQ);
        Form crf = (Form) getSessionObject(request, CRF);
        String cdeContextIdSeq = crf.getContext().getConteIdseq();

        FormBuilderServiceDelegate service = getFormBuilderService();
        List cdeList = crf.getCDEIdList();

        int result = service.saveDesignation(cdeContextIdSeq, cdeList);
      }
      catch (FormBuilderException exp) {
        if (log.isErrorEnabled()) {
          log.error("Exception on saveDesignations ", exp);
        }

        saveError("cadsr.formbuilder.designation.fail", request);
        ActionForward forward =  mapping.findForward("failure");
        return forward;
      }
      
      saveMessage("cadsr.formbuilder.designation.success", request);
      ActionForward forward =  mapping.findForward("success");
      return forward;
  }
  
  

  public ActionForward  cancelDesignations(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    return mapping.findForward("cancel");
  }

}
