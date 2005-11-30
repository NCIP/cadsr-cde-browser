
package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.dto.FormInstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.jsp.bean.PaginationBean;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
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
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;


public class CopyModuleAction extends FormBuilderSecureBaseDispatchAction {


  /**
   * Prepares the Add Module page
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
  public ActionForward goToModuleSearch(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    DynaActionForm dynaForm = (DynaActionForm)form;
    Integer displayOrder = (Integer)dynaForm.get(DISPLAY_ORDER);
    setSessionObject(request,IN_PROCESS,"true",true);
    setSessionObject(request,MODULE_DISPLAY_ORDER_TO_COPY,displayOrder,true);
    return mapping.findForward("framedSearchResultsPage");

  }
    
    /**
     * Copy the selected Module
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
    public ActionForward setSelectedFormAsModuleCopyForm(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm)form;
      FormBuilderBaseDynaFormBean formBean  = (FormBuilderBaseDynaFormBean)form;
      String formIdSeq = (String) formBean.get(FORM_ID_SEQ);
         Form crf = null; 
         try {
             crf = getFormBuilderService().getFormDetails(formIdSeq);
             setSessionObject(request,MODULE_COPY_FORM,crf,true);
         }
         catch (FormBuilderException exp) {
           if (log.isErrorEnabled()) {
             log.error("Exception getting CRF", exp);
           }      
           saveError(ERROR_FORM_RETRIEVE, request);
           saveError(ERROR_FORM_DOES_NOT_EXIST, request);
           return mapping.findForward(FAILURE);
         }
         //Set the form as skip source
        
         return mapping.findForward("gotoSelectModule");

    }

    /**
     * Copy selected Module to form
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
    public ActionForward copySelectedModuleToForm(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm)form;
      
      Form copyForm = (Form)getSessionObject(request,MODULE_COPY_FORM);
      int selectedDisplayOrder = ((Integer)dynaForm.get(MODULE_INDEX)).intValue();
      List copyFormModules = copyForm.getModules();
      Module copiedModule = (Module) copyFormModules.get(selectedDisplayOrder); 
      Module copiedModuleClone = null;
      try
      {
         copiedModuleClone = (Module)copiedModule.clone();
      }
      catch (CloneNotSupportedException clexp) {
          if (log.isErrorEnabled()) {
            log.error("Exception while colneing Module " + copiedModule,clexp);
          }
         saveError("cadsr.formbuilder.module.copy.failure", request);
         return mapping.findForward("toFormEdit");
      } 
        
      int displayOrderToCopy= ((Integer)getSessionObject(request,MODULE_DISPLAY_ORDER_TO_COPY)).intValue();
      Form crf = (Form)getSessionObject(request,CRF);
        
      List desModules = crf.getModules();
      
                                          
      if(desModules == null) {
          desModules = new ArrayList();
          desModules.add(copiedModuleClone);
          crf.setModules(desModules);
        }

      if(displayOrderToCopy < desModules.size()) {
          desModules.add(displayOrderToCopy, copiedModuleClone);

        } else {
            desModules.add(copiedModuleClone);
        }
        FormActionUtil.setInitDisplayOrders(desModules); //This is done to set display order in a sequential order 
                                                        
        // Jump to the update location on the screen
        request.setAttribute(CaDSRConstants.ANCHOR,"M"+displayOrderToCopy);
            
        //Remove attributes
        removeSessionObject(request,MODULE_DISPLAY_ORDER_TO_COPY);
        removeSessionObject(request,MODULE_COPY_FORM);
        removeSessionObject(request,IN_PROCESS);

        saveMessage("cadsr.formbuilder.module.copy.success", request);
        return mapping.findForward("toFormEdit");

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
  public ActionForward goToSelectedModuleList(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    DynaActionForm dynaForm = (DynaActionForm)form;
    Module newModule = new ModuleTransferObject();

      return mapping.findForward("toModuleSearch");
  }
    /**
     * Copy selected Module to module list from copy serach
     * if not in search mode copy the module from current crf
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
    public ActionForward copySelectedModuleToList(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm)form;
      
      Form copyForm = (Form)getSessionObject(request,MODULE_COPY_FORM);
      if(copyForm==null)
          copyForm = (Form)getSessionObject(request,CRF);
      int selectedDisplayOrder = ((Integer)dynaForm.get(MODULE_INDEX)).intValue();
      List copyFormModules = copyForm.getModules();
      Module copiedModule = (Module) copyFormModules.get(selectedDisplayOrder); 
      Module copiedModuleClone = null;
      try
      {
         copiedModuleClone = (Module)copiedModule.clone();
      }
      catch (CloneNotSupportedException clexp) {
          if (log.isErrorEnabled()) {
            log.error("Exception while colneing Module " + copiedModule,clexp);
          }
         saveError("cadsr.formbuilder.module.copy.moduleList.failure", request);
          return mapping.findForward("framedSearchResultsPage");
      } 
        
      
      List<Module> moduleList = (List<Module>)getSessionObject(request,MODULE_LIST);
        
                                          
      if(moduleList == null) {
          moduleList = new ArrayList<Module>();          
        }
       moduleList.add(copiedModuleClone);
       this.setSessionObject(request,MODULE_LIST,moduleList,true);                                             
        // Jump to the update location on the screen
        request.setAttribute(CaDSRConstants.ANCHOR,"M"+selectedDisplayOrder);
            
        saveMessage("cadsr.formbuilder.module.copy.moduleList.success", request);
        return mapping.findForward("doneCopyToList");

    }
    /**
     * View Modules in the module list
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
    public ActionForward gotoCopyFromModuleList(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm)form;
      Integer displayOrder = (Integer)dynaForm.get(DISPLAY_ORDER);
      setSessionObject(request,MODULE_DISPLAY_ORDER_TO_COPY,displayOrder,true);      
       return mapping.findForward("viewModuleList");
      }
    /**
     * View Modules in the module list
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
    public ActionForward viewModuleList(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm)form;
      
       return mapping.findForward("viewModuleList");
      }
    /**
     * View Modules in the module list
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
    public ActionForward doneViewModuleList(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm)form;
      
       return mapping.findForward("done");
      }     
    /**
     * Cancel and go back to Form serach
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
    public ActionForward cancelModuleSelection(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm)form;
      removeSessionObject(request,MODULE_COPY_FORM);

       return mapping.findForward("framedSearchResultsPage");
      }
      
    /**
     * Cancel and go back to Form serach
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
    public ActionForward cancelModuleFormSearch(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm)form;
    
      Object displayOrder = getSessionObject(request,MODULE_DISPLAY_ORDER_TO_COPY);
      request.setAttribute(DISPLAY_ORDER,displayOrder);
      
      removeSessionObject(request,MODULE_DISPLAY_ORDER_TO_COPY);
      removeSessionObject(request,MODULE_COPY_FORM);
      removeSessionObject(request,IN_PROCESS);
          return mapping.findForward("toFormEdit");
      }   
      

    /**
     * Delete Modules from List
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
    public ActionForward deleteElementsFromModuleList(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm)form;
      List<Module> moduleList = (List<Module>)getSessionObject(request,MODULE_LIST);
    
      String[] selectedIndexes = (String[])request.getParameterValues(SELECTED_ITEMS);
       for(int i=selectedIndexes.length-1;i>-1;--i)
          {
            int currIndex = (new Integer(selectedIndexes[i])).intValue();
            if ((moduleList != null) && (moduleList.size() > 0)) {
                 moduleList.remove(currIndex);
            }
          }
          return mapping.findForward("doneDelete");
      }      
      
    /**
     * Copy Modules from List
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
    public ActionForward copyFromModuleList(
      ActionMapping mapping,
      ActionForm form,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {

      DynaActionForm dynaForm = (DynaActionForm)form;
      List<Module> moduleList = (List<Module>)getSessionObject(request,MODULE_LIST);
      try
      {
          String[] selectedIndexes = (String[])request.getParameterValues(SELECTED_ITEMS);
          List copiedModules = new ArrayList();
           for(int i=selectedIndexes.length-1;i>-1;--i)
              {
                int currIndex = (new Integer(selectedIndexes[i])).intValue();
                Module currModule= moduleList.get(currIndex);
                Module copiedModuleClone = (Module)currModule.clone();
                copiedModules.add(copiedModuleClone);
              }      
              
              
          int displayOrderToCopy= ((Integer)getSessionObject(request,MODULE_DISPLAY_ORDER_TO_COPY)).intValue();
          Form crf = (Form)getSessionObject(request,CRF);
            
          List desModules = crf.getModules();
          
                                              
          if(desModules == null) {
          
              crf.setModules(copiedModules);
            }

          if(displayOrderToCopy < desModules.size()) {
              desModules.add(displayOrderToCopy, copiedModules);

            } else {
                desModules.add(copiedModules);
            }
            FormActionUtil.setInitDisplayOrders(crf.getModules()); //This is done to set display order in a sequential order 
                                                            
            // Jump to the update location on the screen
            request.setAttribute(CaDSRConstants.ANCHOR,"M"+displayOrderToCopy);
                
            //Remove attributes
            removeSessionObject(request,MODULE_DISPLAY_ORDER_TO_COPY);
            removeSessionObject(request,MODULE_COPY_FORM);
            removeSessionObject(request,IN_PROCESS);

            saveMessage("cadsr.formbuilder.module.copy.success", request);
            return mapping.findForward("toFormEdit");              
         
      }
      catch (CloneNotSupportedException clexp) {
          if (log.isErrorEnabled()) {
            log.error("Exception while colneing Module " ,clexp);
          }
         saveError("cadsr.formbuilder.module.copy.failure", request);
         return mapping.findForward("toFormEdit");
      } 

      }       
}
