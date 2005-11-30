package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;


import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
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
import gov.nih.nci.ncicb.cadsr.resource.Version;
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


public class FormVersionAction
  extends FormBuilderSecureBaseDispatchAction {
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
  public ActionForward getFormVersions(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    Form crf = (Form)getSessionObject(request, CRF);
     
    if (crf == null){
        return mapping.findForward("failure");
    }
    
    try{
        int publicId = crf.getPublicId();
        FormBuilderServiceDelegate service = getFormBuilderService();
        List formVersions = service.getFormVersions(publicId);
        if (formVersions.size() <2 ){
            return mapping.findForward("gotoCreateNewVersion");
        }
        
        request.setAttribute(FormConstants.FORM_VERSION_LIST, formVersions);        
        for (int i=0; i<formVersions.size(); i++){
            Version aVersion = (Version)formVersions.get(i);
            if (aVersion.isLatestVersionIndicator()){
                setSessionObject(request, OLD_LATEST_VERSION, aVersion);
                break;
            }    
        }//end of for
        return mapping.findForward("success");
    }catch (FormBuilderException ex){
        if (log.isErrorEnabled()) {
          log.error("Exception on getFormVersions ", ex);
        }

        saveError(ex.getErrorCode(), request);
        ActionForward forward =  mapping.findForward("failure");
        return forward;
    }
  }


    public ActionForward saveLatestVersion(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm) form;
      String latestVersionId = (String) dynaForm.get(LATEST_VERSION_ID);
      String[] formIds = (String[]) dynaForm.get("id");

      //changeNote is a String[];
      String[] changeNotes = (String[])dynaForm.get(CHANGE_NOTE);
      
      Version newVersion = new Version();
      newVersion.setId(latestVersionId);
      newVersion.setLatestVersionIndicator(true);

      //get the oldVersion into session
      Version oldVersion = (Version)getSessionObject(request, OLD_LATEST_VERSION);
      
      for (int i=0; i<formIds.length; i++){
          if ( formIds[i].equals(latestVersionId) ){
              newVersion.setChangeNote(changeNotes[i]);
          }          
          if ((oldVersion!=null) && (formIds[i].equals(oldVersion.getId()))){
              oldVersion.setChangeNote(changeNotes[i]);
          }
      }
      

      try{ 
          FormBuilderServiceDelegate service = getFormBuilderService();

          if (oldVersion==null || !latestVersionId.equals(oldVersion.getId())){
              service.setLatestVersion(oldVersion, newVersion);
              //reload the form.
              Form newCRF = service.getFormDetails(newVersion.getId());
              setSessionObject(request, CRF, newCRF);    
              request.setAttribute("showCached", CaDSRConstants.YES);
              saveMessage("cadsr.formbuilder.latest.version.change.success", request);
          }else{
              saveMessage("cadsr.formbuilder.latest.version.nochange", request);
          }    
          
          return mapping.findForward("success");
      }catch (FormBuilderException ex){
          if (log.isErrorEnabled()) {
            log.error("Exception on saveLatestVersion ", ex);
          }

          saveError("cadsr.formbuilder.latest.version.change.fail", request);
          ActionForward forward =  mapping.findForward("failure");
          return forward;
      }
    }

    
      /**
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
/*      public ActionForward gotoCreateNewVersion(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest request,
        HttpServletResponse response) throws IOException, ServletException {

        return mapping.findForward("success");
      }
*/      
      
  public ActionForward saveNewVersion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    try{
        DynaActionForm dynaForm = (DynaActionForm) form;
        Form crf = (Form)getSessionObject(request, CRF);
        
        if (crf == null || !validateVersion(dynaForm, crf, request)){
            return mapping.findForward("failure");
        }
        
        String editNewFormStr = (String) dynaForm.get(EDIT_FORM_INDICATOR);
        Float newVersionNumber = (Float) dynaForm.get(NEW_VERSION_NUMBER);
        String changeNote = (String)dynaForm.get(CHANGE_NOTE);
        boolean editNewFormIndicator = "true".equalsIgnoreCase(editNewFormStr);

        FormBuilderServiceDelegate service = getFormBuilderService();
        String newFormIdSeq = service.createNewFormVersion(crf.getFormIdseq(), newVersionNumber, changeNote);
        saveMessage("cadsr.formbuilder.create.version.success", request);
        
        if (editNewFormIndicator){
            Form newCRF = service.getFormDetails(newFormIdSeq);
            setSessionObject(request, CRF, newCRF);    
            request.setAttribute("showCached", CaDSRConstants.YES);
            return mapping.findForward("successEditNew");            
        }
      }
      catch (FormBuilderException exp) {
        if (log.isErrorEnabled()) {
          log.error("Exception on saveNewVersion ", exp);
        }

        saveError(exp.getErrorCode(), request);
        ActionForward forward =  mapping.findForward("failure");
        return forward;
      }

      ActionForward forward =  mapping.findForward("success");
      return forward;
  }



  public ActionForward  cancelNewVersion(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    return mapping.findForward("cancel");
  }
  
    public ActionForward  cancelLatestVersion(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      removeSessionObject(request, FormConstants.OLD_LATEST_VERSION);
      return mapping.findForward("success");
    }

    //validate the new version number
    private boolean validateVersion(DynaActionForm dynaForm, Form crf, HttpServletRequest request){
        
        Float newVersionNumber = (Float) dynaForm.get(NEW_VERSION_NUMBER);
        if (crf == null){
            return false;
        }
        if (newVersionNumber.floatValue()>crf.getVersion()){
            return true;
        }else{
            saveError("cadsr.formbuilder.create.version.validation_fail", request);
            return false;
        }
    }//end of validateVersion
}