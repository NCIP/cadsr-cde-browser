package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.InstructionChangesTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.resource.InstructionChanges;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Orderable;

import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


public class FormEditAction extends FormBuilderSecureBaseDispatchAction {

  private static String FORM_EDIT_HEADER = "formEditHeader";
  private static String FORM_EDIT_UPDATED_MODULES = "formEditUpdatedModules";
  private static String FORM_EDIT_DELETED_MODULES = "formEditDeletedModules";
  private static String FORM_EDIT_ADDED_MODULES = "formEditAddedModules";
  private static String FORM_EDIT_INSTRUCTION_CHANGES = "formEditInstructionChanges";  

  /**
   * Returns Complete form given an Id for Edit.
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
  public ActionForward getFormToEdit(
    ActionMapping mapping,
    ActionForm formEditForm,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    Form crf = null;
    Form clonedCrf = null;
    setInitLookupValues(request);

    try {
      crf = setFormForAction(formEditForm, request);
      clonedCrf = (Form) crf.clone();
      setSessionObject(request, CLONED_CRF, clonedCrf,true);
    }
    catch (FormBuilderException exp) {
      saveError(ERROR_FORM_RETRIEVE, request);
      saveError(ERROR_FORM_DOES_NOT_EXIST, request);    
      saveError(exp.getErrorCode(),request);
      if (log.isErrorEnabled()) {
        log.error("Exception on getFormForEdit form " + crf,exp);
      }
      return mapping.findForward(FAILURE);
    }
    catch (CloneNotSupportedException clexp) {
      if (log.isErrorEnabled()) {
        log.error("Exception while colneing crf " + crf,clexp);
      }
      saveError(this.ERROR_FORM_RETRIEVE,request);
      return mapping.findForward(FAILURE);
    }

    if ((crf != null) && (formEditForm != null)) {
      FormBuilderBaseDynaFormBean dynaFormEditForm =
        (FormBuilderBaseDynaFormBean) formEditForm;
      dynaFormEditForm.clear();
      dynaFormEditForm.set(FORM_ID_SEQ, crf.getFormIdseq());
      dynaFormEditForm.set(FORM_LONG_NAME, crf.getLongName());
      dynaFormEditForm.set(
        this.PREFERRED_DEFINITION, crf.getPreferredDefinition());
      dynaFormEditForm.set(CONTEXT_ID_SEQ, crf.getContext().getConteIdseq());
      
      if(crf.getProtocol()!=null)
      {
        dynaFormEditForm.set(
          this.PROTOCOL_ID_SEQ, crf.getProtocol().getProtoIdseq());
        dynaFormEditForm.set(
          this.PROTOCOLS_LOV_NAME_FIELD, crf.getProtocol().getLongName());
      }
      dynaFormEditForm.set(CATEGORY_NAME, crf.getFormCategory());
      dynaFormEditForm.set(
        this.FORM_TYPE, crf.getFormType());
      dynaFormEditForm.set(CATEGORY_NAME, crf.getFormCategory());
      dynaFormEditForm.set(this.WORKFLOW, crf.getAslName());
      
      //Instructions
      if(crf.getInstruction()!=null)
      {
        dynaFormEditForm.set(this.FORM_HEADER_INSTRUCTION, crf.getInstruction().getLongName());
      }
      if(crf.getFooterInstruction()!=null)
      {
        dynaFormEditForm.set(this.FORM_FOOTER_INSTRUCTION, crf.getFooterInstruction().getLongName());
      }      

    }

    removeSessionObject(request, DELETED_MODULES);

    return mapping.findForward(SUCCESS);
  }

  /**
   * Swap the display order of the Module with the previous Module.
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
  public ActionForward moveModuleUp(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm editForm = (DynaActionForm) form;
    Integer moduleIndex = (Integer) editForm.get(MODULE_INDEX);
    int currModuleIndex = moduleIndex.intValue();
    Form crf = (Form) getSessionObject(request, CRF);

    List modules = crf.getModules();

    if ((modules != null) && (modules.size() > 1)) {
      Module currModule = (Module) modules.get(currModuleIndex);
      Module prvModule = (Module) modules.get(currModuleIndex - 1);
      int currModuleDisplayOrder = currModule.getDisplayOrder();
      currModule.setDisplayOrder(prvModule.getDisplayOrder());
      prvModule.setDisplayOrder(currModuleDisplayOrder);
      modules.remove(currModuleIndex);
      modules.add(currModuleIndex - 1, currModule);
    }

    if (log.isDebugEnabled()) {
      log.debug("Move up Module ");
    }

    return mapping.findForward(FORM_EDIT);
  }

  /**
   * Swap the display order of the Module with the next Module.
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
  public ActionForward moveModuleDown(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm editForm = (DynaActionForm) form;
    Integer moduleIndex = (Integer) editForm.get(MODULE_INDEX);
    int currModuleIndex = moduleIndex.intValue();

    Form crf = (Form) getSessionObject(request, CRF);

    List modules = crf.getModules();

    if ((modules != null) && (modules.size() > 1)) {
      Module currModule = (Module) modules.get(currModuleIndex);
      Module nextModule = (Module) modules.get(currModuleIndex + 1);
      int currModuleDisplayOrder = currModule.getDisplayOrder();
      currModule.setDisplayOrder(nextModule.getDisplayOrder());
      nextModule.setDisplayOrder(currModuleDisplayOrder);
      modules.remove(currModuleIndex);
      modules.add(currModuleIndex + 1, currModule);
    }

    if (log.isDebugEnabled()) {
      log.debug("Move Down Module ");
    }

    return mapping.findForward(FORM_EDIT);
  }

  /**
   * Deletes the module of specified index and adds the Module to deleted list.
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
  public ActionForward deleteModule(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm editForm = (DynaActionForm) form;
    Integer moduleIndex = (Integer) editForm.get(MODULE_INDEX);

    Form crf = (Form) getSessionObject(request, CRF);
    List deletedModules = (List) getSessionObject(request, DELETED_MODULES);

    if (deletedModules == null) {
      deletedModules = new ArrayList();
    }

    List modules = crf.getModules();

    if ((modules != null) && (modules.size() > 0)) {
      if (log.isDebugEnabled()) {
        printDisplayOrder(modules);
      }

      Module deletedModule = (Module) modules.remove(moduleIndex.intValue());
      FormActionUtil.decrementDisplayOrder(modules, moduleIndex.intValue());
      deletedModules.add(deletedModule);
    }

    setSessionObject(request, DELETED_MODULES, deletedModules,true);


    return mapping.findForward(FORM_EDIT);
  }

  /**
   * Add Module from deleted list.
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
  public ActionForward addFromDeletedList(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm editForm = (DynaActionForm) form;
    Integer moduleIndex = (Integer) editForm.get(MODULE_INDEX);

    String addDeletedModuleIdSeq =
      (String) editForm.get(ADD_DELETED_MODULE_IDSEQ);

    Form crf = (Form) getSessionObject(request, CRF);
    List deletedModules = (List) getSessionObject(request, DELETED_MODULES);

    List modules = crf.getModules();

    if ((modules != null) && (moduleIndex != null) && (deletedModules != null)) {
      Module moduleToAdd =
        removeModuleFromList(addDeletedModuleIdSeq, deletedModules);

      if (log.isDebugEnabled()) {
        printDisplayOrder(modules);
      }

      if ((moduleIndex.intValue() < modules.size()) && (moduleToAdd != null)) {
        Module currModule = (Module) modules.get(moduleIndex.intValue());
        int displayOrder = currModule.getDisplayOrder();
        FormActionUtil.incrementDisplayOrder(modules, moduleIndex.intValue());
        moduleToAdd.setDisplayOrder(displayOrder);
        modules.add((moduleIndex.intValue()), moduleToAdd);
      }
      else {
        int newDisplayOrder = 0;

        if (moduleIndex.intValue() != 0) {
          Module lastModule = (Module) modules.get(modules.size() - 1);
          newDisplayOrder = lastModule.getDisplayOrder() + 1;
        }

        moduleToAdd.setDisplayOrder(newDisplayOrder);
        modules.add(moduleToAdd);
      }
    }

    return mapping.findForward(FORM_EDIT);
  }

  /**
   * Delete Form.
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
  public ActionForward deleteForm(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    DynaActionForm hrefCRFForm = (DynaActionForm) form;
    String formIdSeq = (String) hrefCRFForm.get(FORM_ID_SEQ);
    if (log.isDebugEnabled()) {
      log.info("Delete Form With Id " + formIdSeq);
    }
    FormBuilderServiceDelegate service = getFormBuilderService();
    try {
        service.deleteForm(formIdSeq);
      }
    catch (FormBuilderException exp) {
        if (log.isDebugEnabled()) {
          log.debug("Exception on delete  " + exp);
        }
        saveError(ERROR_FORM_DELETE_FAILED, request);
        saveError(exp.getErrorCode(), request);
        return mapping.findForward(FAILURE);
      }
    removeSessionObject(request, DELETED_MODULES);
    removeSessionObject(request, CLONED_CRF);
    removeSessionObject(request, CRF);
    saveMessage("cadsr.formbuilder.form.delete.success", request);
    ActionForward forward = mapping.findForward(SUCCESS);
    return forward;
  }


  /**
   * Save Changes
   *
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
  public ActionForward saveForm(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    Form crf = (Form) getSessionObject(request, CRF);
    boolean hasUpdate  = setValuesForUpdate(mapping,form,request);
    if(hasUpdate)
    {
        try {
          FormBuilderServiceDelegate service = getFormBuilderService();
          Form header = (Form)getSessionObject(request,FORM_EDIT_HEADER);

          if(header!=null)
          {
            String type = header.getFormType();
            if(type.equalsIgnoreCase(PersistenceConstants.FORM_TYPE_CRF))
            {
             
             /** 
              * if(header.getProtocol()==null)
                {
                   saveError("cadsr.formbuilder.form.edit.form.noProtocol", request);
                   return mapping.findForward(FAILURE);
                }
                **/
            }
            else
            {
              if(header.getProtocol()!=null)
                {
                   saveError("cadsr.formbuilder.form.edit.form.template.protocol", request);
                   return mapping.findForward(FAILURE);
                }              
            }
          }
          
          Collection updatedModules = (Collection)getSessionObject(request,FORM_EDIT_UPDATED_MODULES);
          Collection deletedModules = (Collection)getSessionObject(request,FORM_EDIT_DELETED_MODULES);
          Collection addedModules = (Collection)getSessionObject(request,FORM_EDIT_ADDED_MODULES);
          InstructionChanges instrChanges = (InstructionChanges)getSessionObject(request,FORM_EDIT_INSTRUCTION_CHANGES);
          Form updatedCrf = service.updateForm(crf.getFormIdseq(),header, updatedModules, deletedModules,addedModules,instrChanges);
          setSessionObject(request,CRF, updatedCrf,true);
          Form clonedCrf = (Form) updatedCrf.clone();
          setSessionObject(request, CLONED_CRF, clonedCrf,true);
        }
        catch (FormBuilderException exp) {
          if (log.isErrorEnabled()) {
            log.error("Exception While saving the form " + crf,exp);
          }
          saveError(ERROR_FORM_SAVE_FAILED, request);
          saveError(exp.getErrorCode(), request);
          return mapping.findForward(FAILURE);
        }
        catch (CloneNotSupportedException exp) {
          saveError(ERROR_FORM_SAVE_FAILED, request);
          if (log.isErrorEnabled()) {
            log.error("On save, Exception on cloneing crf " + crf,exp);
          }
          return mapping.findForward(FAILURE);
        }
        removeSessionObject(request, DELETED_MODULES);
        saveMessage("cadsr.formbuilder.form.edit.save.success", request);
        return mapping.findForward(SUCCESS);
       }
    else
    {
      saveMessage("cadsr.formbuilder.form.edit.nochange", request);
       return mapping.findForward(FORM_EDIT);
    }
    }


  /**
   * Save Changes Module Edit
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
  public ActionForward checkChangesModuleEdit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    boolean hasUpdate  = setValuesForUpdate(mapping,form,request);
      if(hasUpdate)
      {
        return mapping.findForward(SAVE_CONFIRM_MODULE_EDIT);
      }
      else
      {
        return mapping.findForward(MODULE_EDIT);
      }
    }
    
  /**
   * Save Changes Module Edit
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
  public ActionForward checkChangesDone(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    boolean hasUpdate  = setValuesForUpdate(mapping,form,request);
      if(hasUpdate)
      {
        return mapping.findForward(SAVE_CONFIRM_DONE);
      }
      else
      {
        return mapping.findForward(SEARCH_RESULTS);
      }
    }
    
  /**
   * Save Changes Module Edit
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
  public ActionForward saveFormChanges(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    Form crf = (Form) getSessionObject(request, CRF);

        try {
          FormBuilderServiceDelegate service = getFormBuilderService();
          Form header = (Form)getSessionObject(request,FORM_EDIT_HEADER);
          if(header!=null)
          {
            String type = header.getFormType();
            if(type.equalsIgnoreCase(PersistenceConstants.FORM_TYPE_CRF))
            {
             /** if(header.getProtocol()==null)
                {
                   saveError("cadsr.formbuilder.form.edit.form.noProtocol", request);
                   return mapping.findForward(FAILURE);
                }
                **/
            }
            else
            {
              if(header.getProtocol()!=null)
                {
                   saveError("cadsr.formbuilder.form.edit.form.template.protocol", request);
                   return mapping.findForward(FAILURE);
                }              
            }
          }
          Collection updatedModules = (Collection)getSessionObject(request,FORM_EDIT_UPDATED_MODULES);
          Collection deletedModules = (Collection)getSessionObject(request,FORM_EDIT_DELETED_MODULES);
          Collection addedModules = (Collection)getSessionObject(request,FORM_EDIT_ADDED_MODULES);
          InstructionChanges instrChanges = (InstructionChanges)getSessionObject(request,FORM_EDIT_INSTRUCTION_CHANGES);          
          Form updatedCrf = service.updateForm(crf.getFormIdseq(),header, updatedModules
                                                  , deletedModules,addedModules,instrChanges);
          setSessionObject(request,CRF, updatedCrf);
          Form clonedCrf = (Form) updatedCrf.clone();
          setSessionObject(request, CLONED_CRF, clonedCrf);
        }
        catch (FormBuilderException exp) {
          if (log.isErrorEnabled()) {
            log.error("Exception while saveing form =  " + crf,exp);
          }
          saveError(ERROR_FORM_SAVE_FAILED, request);
          saveError(exp.getErrorCode(), request);
          return mapping.findForward(FAILURE);
        }
        catch (CloneNotSupportedException exp) {
          saveError(ERROR_FORM_SAVE_FAILED, request);
          if (log.isErrorEnabled()) {
            log.error("On save, Exception on cloneing crf " + crf,exp);
          }
          return mapping.findForward(FAILURE);
        }
        saveMessage("cadsr.formbuilder.form.edit.save.success", request);
        removeSessionObject(request, DELETED_MODULES);
        return mapping.findForward(SUCCESS);

    }
  /**
   * Cancel Edit and back to Search results
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
  public ActionForward cancelFormEdit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    FormBuilderBaseDynaFormBean editForm = (FormBuilderBaseDynaFormBean) form;
    removeSessionObject(request, DELETED_MODULES);
    removeSessionObject(request, CLONED_CRF);
    editForm.clear();
    return mapping.findForward(SUCCESS);

    }

  /**
   * Cancel Save before ModuleEdit
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
  public ActionForward cancelFormChangesModuleEdit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {
    FormBuilderBaseDynaFormBean editForm = (FormBuilderBaseDynaFormBean) form;
    removeSessionObject(request, FORM_EDIT_HEADER);
    removeSessionObject(request, FORM_EDIT_UPDATED_MODULES);
    removeSessionObject(request, FORM_EDIT_DELETED_MODULES);
    removeSessionObject(request, FORM_EDIT_ADDED_MODULES);
    removeSessionObject(request, FORM_EDIT_INSTRUCTION_CHANGES);
    

    return mapping.findForward(FORM_EDIT);

    }

  /**
   * Check if there are updated to form and set the value in the request
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
  private boolean setValuesForUpdate(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request)  {
    DynaActionForm editForm = (DynaActionForm) form;
    Form clonedCrf = (Form) getSessionObject(request, CLONED_CRF);
    Form crf = (Form) getSessionObject(request, CRF);
    List deletedModules = (List) getSessionObject(request, DELETED_MODULES);
    Form header = new FormTransferObject();

    //Add the header info into TransferObj after checking for update
    boolean headerUpdate = false;
    header.setFormIdseq((String) editForm.get(FORM_ID_SEQ));
    header.setPreferredName(clonedCrf.getPreferredName());
    String longName = (String) editForm.get(FORM_LONG_NAME);

    if (hasValue(longName) ) {
       header.setLongName(longName);
      if (hasValue(clonedCrf.getLongName())&&!longName.equals(clonedCrf.getLongName())) {
        headerUpdate = true;
      }
      else if (!hasValue(clonedCrf.getLongName()))
      {
        headerUpdate = true;
      }      
    }

    String contextIdSeq = (String) editForm.get(CONTEXT_ID_SEQ);
    String orgContextIdSeq = null;

    if (clonedCrf.getContext() != null) {
      orgContextIdSeq = clonedCrf.getContext().getConteIdseq();
    }

    if (hasValue(contextIdSeq) ) {
      Context context = new ContextTransferObject();
      context.setConteIdseq(contextIdSeq);
      header.setContext(context);
      if (hasValue(orgContextIdSeq)&&!contextIdSeq.equals(orgContextIdSeq)) {
        headerUpdate = true;
      }
      else if (!hasValue(orgContextIdSeq))
      {
        headerUpdate = true;
      }        
    }

    String protocolIdSeq = (String) editForm.get(PROTOCOL_ID_SEQ);
    String orgProtocolIdSeq = null;

    if (clonedCrf.getProtocol() != null) {
      orgProtocolIdSeq = clonedCrf.getProtocol().getProtoIdseq();
    }

    if (hasValue(protocolIdSeq)) {
     Protocol protocol = new ProtocolTransferObject();
     protocol.setProtoIdseq(protocolIdSeq);
      header.setProtocol(protocol);
      if (hasValue(orgProtocolIdSeq)&&!orgProtocolIdSeq.equals(protocolIdSeq)) {
        headerUpdate = true;
      }
      else if (!hasValue(orgProtocolIdSeq))
      {
        headerUpdate = true;
      }
    }
    else
    {
      header.setProtocol(null);
      if(hasValue(orgProtocolIdSeq))
          headerUpdate = true;
    }

    String workflow = (String) editForm.get(WORKFLOW);
    String orgWorkflow = clonedCrf.getAslName();
   if (hasValue(workflow) ) {
      header.setAslName(workflow);
      if (hasValue(orgWorkflow)&&!workflow.equals(orgWorkflow)) {
        headerUpdate = true;
      }
      else if (!hasValue(orgWorkflow))
      {
        headerUpdate = true;
      }       
    }
    else 
    {
      header.setAslName(null);
      if(hasValue(orgWorkflow))
        headerUpdate = true;
    }

    String categoryName = (String) editForm.get(CATEGORY_NAME);
    String orgCategoryName = clonedCrf.getFormCategory();
   if (hasValue(categoryName) ) {
      header.setFormCategory(categoryName);
      if (hasValue(orgCategoryName)&&!categoryName.equals(orgCategoryName)) {
        headerUpdate = true;
      }
      else if (!hasValue(orgCategoryName))
      {
        headerUpdate = true;
      }         
    }
   else
   {
     header.setFormCategory(null);
     if(hasValue(orgCategoryName))
      headerUpdate = true;
   }

    String formType = (String) editForm.get(this.FORM_TYPE);
    String orgFormType = clonedCrf.getFormType();
   if (hasValue(formType) ) {
      header.setFormType(formType);
      if (hasValue(orgFormType)&&!formType.equals(orgFormType)) {
        headerUpdate = true;
      }
      else if (!hasValue(orgFormType))
      {
        headerUpdate = true;
      }       
    }
   else 
   {
     header.setFormType(null);
     if(hasValue(orgFormType))
        headerUpdate = true;
   }    

    String preferredDef = (String) editForm.get(PREFERRED_DEFINITION);
    String orgPreferredDef = clonedCrf.getPreferredDefinition();
    if (hasValue(preferredDef) ) {
      header.setPreferredDefinition(preferredDef);
      if (hasValue(orgPreferredDef)&&!preferredDef.equals(clonedCrf.getPreferredDefinition())) {
        headerUpdate = true;
      }
      else if (!hasValue(orgPreferredDef))
      {
        headerUpdate = true;
      }          
    }
    else 
    {
      header.setPreferredDefinition(null);
      if(hasValue(orgPreferredDef))
        headerUpdate = true;
    }
    
    String headerInsterStr = (String) editForm.get(FORM_HEADER_INSTRUCTION);
    InstructionChanges instrChanges = new InstructionChangesTransferObject();
    Map headerInstrChanges  = getInstructionChanges(instrChanges.getFormHeaderInstructionChanges()
                              , headerInsterStr , clonedCrf.getInstruction(),crf);
    instrChanges.setFormHeaderInstructionChanges(headerInstrChanges);  
    
    String footerInsterStr = (String) editForm.get(FORM_FOOTER_INSTRUCTION);
    Map footerInstrChanges  = getInstructionChanges(instrChanges.getFormFooterInstructionChanges()
                              , footerInsterStr , clonedCrf.getFooterInstruction(),crf);
    instrChanges.setFormFooterInstructionChanges(footerInstrChanges);                              
                              
                              

   List updatedModules =
       getUpdatedModules(clonedCrf.getModules(), crf.getModules());
   List addedModules =
       getAddedModules( crf.getModules());

    if(!headerUpdate)
       header=null;
    if (
        header!=null || ((deletedModules != null) && !deletedModules.isEmpty()) ||
          !updatedModules.isEmpty()||!addedModules.isEmpty()||!instrChanges.isEmpty()) {
        setSessionObject(request,FORM_EDIT_HEADER,header,true);
        setSessionObject(request,FORM_EDIT_UPDATED_MODULES,updatedModules,true);
        setSessionObject(request,FORM_EDIT_DELETED_MODULES,deletedModules,true);
        setSessionObject(request,FORM_EDIT_ADDED_MODULES,addedModules,true);
        setSessionObject(request,FORM_EDIT_INSTRUCTION_CHANGES,instrChanges,true);
        return true;
      }
    else
    {
      return false;
    }
  }

   /**
   * Removes the module given by "moduleIdSeq" from the module list
   *
   * @param moduleIdSeq
   * @param modules
   *
   * @return the removed module
   */
  protected void removeFormEditChanges(HttpServletRequest request)
  {
        removeSessionObject(request,FORM_EDIT_HEADER);
        removeSessionObject(request,FORM_EDIT_UPDATED_MODULES);
        removeSessionObject(request,FORM_EDIT_DELETED_MODULES);
        removeSessionObject(request,FORM_EDIT_ADDED_MODULES);
        removeSessionObject(request,FORM_EDIT_INSTRUCTION_CHANGES);
        
  }
  /**
   * Removes the module given by "moduleIdSeq" from the module list
   *
   * @param moduleIdSeq
   * @param modules
   *
   * @return the removed module
   */
  protected Module removeModuleFromList(
    String moduleIdSeq,
    List modules) {
    ListIterator iterate = modules.listIterator();

    while (iterate.hasNext()) {
      Module module = (Module) iterate.next();

      if (module.getModuleIdseq().equals(moduleIdSeq)) {
        iterate.remove();

        return module;
      }
    }

    return null;
  }

  /**
   * Gets the module given by "moduleIdSeq" from the module list
   *
   * @param moduleIdSeq
   * @param modules
   *
   * @return the  module else returns null;
   */
  protected Module getModuleFromList(
    String moduleIdSeq,
    List modules) {
    ListIterator iterate = modules.listIterator();

    while (iterate.hasNext()) {
      Module module = (Module) iterate.next();

      if (module.getModuleIdseq().equals(moduleIdSeq)) {
        return module;
      }
    }

    return null;
  }


  /**
   * Gets new modules added
   *
   * @param orgModules
   * @param newModules
   *
   * @return a list containg the modules with changed display order and the
   *         newly added modules. Returns empty list if "newModules" is null;
   *         If no modules present returns empty list;
   */
  protected List getAddedModules(
    List newModules) {
    List addedModules = new ArrayList();

    ListIterator newIterate = newModules.listIterator();

    while (newIterate.hasNext()) {
      Module newModule = (Module) newIterate.next();
      if(newModule.getModuleIdseq()==null)
      {
        addedModules.add(newModule);
      }
    }
    return addedModules;
  }

  /**
   * Compares the display order of the modules from the original form and the
   * edited form.
   *
   * @param orgModules
   * @param newModules
   *
   * @return a list containg the modules with changed display order and the
   *         newly added modules. Returns empty list if "newModules" is null;
   *         If no modules present returns empty list;
   */
  protected List getUpdatedModules(
    List orgModules,
    List newModules) {
    List updatedModules = new ArrayList();

    if (orgModules == null) {
      return newModules;
    }

    if (newModules == null) {
      return updatedModules;
    }

    ListIterator newIterate = newModules.listIterator();

    while (newIterate.hasNext()) {
      Module newModule = (Module) newIterate.next();
      Module orgModule =
        (Module) getModuleFromList(newModule.getModuleIdseq(), orgModules);

      if (orgModule != null&&newModule.getModuleIdseq()!=null) {
        if (orgModule.getDisplayOrder() != newModule.getDisplayOrder()) {
          updatedModules.add(newModule);
        }
      }
    //  else {
    //    updatedModules.add(newModule);
    //  }
    }

    return updatedModules;
  }

  /**
   * Prints the Display Order of Modules
   *
   * @param modules
   */
  protected void printDisplayOrder(List modules) {
    ListIterator iterate = modules.listIterator();
    log.debug("Module Display order");

    while (iterate.hasNext()) {
      Module module = (Module) iterate.next();
      log.debug(module.getLongName() + "-->" + module.getDisplayOrder());
    }
  }
  
  private Map getInstructionChanges(Map mainMap, String currInstrStr , Instruction orgInstr, Form parent)
  {
    if(mainMap==null)
           mainMap = new HashMap(); 
       if(currInstrStr==null&&orgInstr!=null)
       {
         List toDelete = (List)mainMap.get(InstructionChanges.DELETED_INSTRUCTIONS);
         if(toDelete==null)
         {
            toDelete = new ArrayList();       
         }
         toDelete.add(orgInstr);
         mainMap.put(InstructionChanges.DELETED_INSTRUCTIONS,toDelete);
         return mainMap;
       }
       if(currInstrStr!=null&&orgInstr==null)
       {
         if (!currInstrStr.trim().equals(""))
         {
            Instruction instr = new InstructionTransferObject();
            instr.setLongName(currInstrStr);
            instr.setDisplayOrder(0);
            instr.setVersion(new Float(1));
            instr.setAslName("DRAFT NEW");
            instr.setContext(parent.getContext());        
            instr.setPreferredDefinition(currInstrStr);
    
           Map newInstrs = (Map)mainMap.get(InstructionChanges.NEW_INSTRUCTION_MAP);
           if(newInstrs==null)
           {
              newInstrs = new HashMap();       
           }
           newInstrs.put(parent.getFormIdseq(),instr);
           mainMap.put(InstructionChanges.NEW_INSTRUCTION_MAP,newInstrs);
           return mainMap;     
         }
       }
       if(currInstrStr!=null&&orgInstr!=null)
       {
         if(!currInstrStr.equals(orgInstr.getLongName())&&!currInstrStr.trim().equals(""))
           {
             List updatedInstrs = (List)mainMap.get(InstructionChanges.UPDATED_INSTRUCTIONS);
             if(updatedInstrs==null)
             {
                updatedInstrs = new ArrayList();       
             }
             orgInstr.setLongName(currInstrStr);
             updatedInstrs.add(orgInstr);
             mainMap.put(InstructionChanges.UPDATED_INSTRUCTIONS,updatedInstrs);
             return mainMap;     
           }
           else if(!currInstrStr.equals(orgInstr.getLongName())&&currInstrStr.trim().equals(""))
           {
             List toDelete = (List)mainMap.get(InstructionChanges.DELETED_INSTRUCTIONS);
             if(toDelete==null)
             {
                toDelete = new ArrayList();       
             }
             toDelete.add(orgInstr);
             mainMap.put(InstructionChanges.DELETED_INSTRUCTIONS,toDelete);
             return mainMap;       
           }
       }   
       return mainMap;
      }
}
