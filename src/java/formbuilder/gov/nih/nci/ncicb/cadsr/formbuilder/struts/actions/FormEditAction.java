package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Orderable;

import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

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
      dynaFormEditForm.set(
        this.PROTOCOL_ID_SEQ, crf.getProtocol().getProtoIdseq());
      dynaFormEditForm.set(
        this.PROTOCOLS_LOV_NAME_FIELD, crf.getProtocol().getLongName());
      dynaFormEditForm.set(CATEGORY_NAME, crf.getFormCategory());
      dynaFormEditForm.set(
        this.FORM_TYPE, crf.getFormType());
      dynaFormEditForm.set(CATEGORY_NAME, crf.getFormCategory());
      dynaFormEditForm.set(this.WORKFLOW, crf.getAslName());
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
          Collection updatedModules = (Collection)getSessionObject(request,FORM_EDIT_UPDATED_MODULES);
          Collection deletedModules = (Collection)getSessionObject(request,FORM_EDIT_DELETED_MODULES);
          Collection addedModules = (Collection)getSessionObject(request,FORM_EDIT_ADDED_MODULES);
          Form updatedCrf = service.updateForm(crf.getFormIdseq(),header, updatedModules, deletedModules,addedModules);
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
          Collection updatedModules = (Collection)getSessionObject(request,FORM_EDIT_UPDATED_MODULES);
          Collection deletedModules = (Collection)getSessionObject(request,FORM_EDIT_DELETED_MODULES);
          Collection addedModules = (Collection)getSessionObject(request,FORM_EDIT_ADDED_MODULES);
          Form updatedCrf = service.updateForm(crf.getFormIdseq(),header, updatedModules, deletedModules,addedModules);
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

    if ((longName != null) && (clonedCrf.getLongName() != null)) {
       header.setLongName(longName);
      if (!longName.equals(clonedCrf.getLongName())) {
        headerUpdate = true;
      }
    }
    else if(longName==null)
    {
       header.setLongName(null);
       headerUpdate = true;
    }

    String contextIdSeq = (String) editForm.get(CONTEXT_ID_SEQ);
    String orgContextIdSeq = null;

    if (clonedCrf.getContext() != null) {
      orgContextIdSeq = clonedCrf.getContext().getConteIdseq();
    }

    if ((contextIdSeq != null) && (orgContextIdSeq != null)) {
      Context context = new ContextTransferObject();
      context.setConteIdseq(contextIdSeq);
      header.setContext(context);
      if (!contextIdSeq.equals(orgContextIdSeq)) {
        headerUpdate = true;
      }
    }

    String protocolIdSeq = (String) editForm.get(PROTOCOL_ID_SEQ);
    String orgProtocolIdSeq = null;

    if (clonedCrf.getProtocol() != null) {
      orgProtocolIdSeq = clonedCrf.getProtocol().getProtoIdseq();
    }

    if ((protocolIdSeq != null) && (orgProtocolIdSeq != null)) {
     Protocol protocol = new ProtocolTransferObject();
     protocol.setProtoIdseq(protocolIdSeq);
      header.setProtocol(protocol);
      if (!orgProtocolIdSeq.equals(protocolIdSeq)) {
        headerUpdate = true;
      }
    }
    else if(protocolIdSeq==null)
    {
      header.setProtocol(null);
      headerUpdate = true;
    }

    String workflow = (String) editForm.get(WORKFLOW);
    String orgWorkflow = clonedCrf.getAslName();
   if ((workflow != null) && orgWorkflow!=null) {
      header.setAslName(workflow);
      if (!workflow.equals(orgWorkflow)) {
        headerUpdate = true;
      }
    }

    String categoryName = (String) editForm.get(CATEGORY_NAME);
    String orgCategoryName = clonedCrf.getFormCategory();
   if ((categoryName != null) && orgCategoryName!=null) {
      header.setFormCategory(categoryName);
      if (!categoryName.equals(orgCategoryName)) {
        headerUpdate = true;
      }
    }
   else if(categoryName==null)
   {
     header.setFormCategory(null);
     headerUpdate = true;
   }

    String formType = (String) editForm.get(this.FORM_TYPE);
    String orgFormType = clonedCrf.getFormType();
   if ((formType != null) && orgFormType!=null) {
      header.setFormType(formType);
      if (!formType.equals(orgFormType)) {
        headerUpdate = true;
      }
    }

    String preferredDef = (String) editForm.get(PREFERRED_DEFINITION);
    String orgPreferredDef = clonedCrf.getPreferredDefinition();
    if ((preferredDef != null) && orgPreferredDef != null) {
      header.setPreferredDefinition(preferredDef);
      if (!preferredDef.equals(clonedCrf.getPreferredDefinition())) {
        headerUpdate = true;
      }
    }
    else if(preferredDef != null)
    {
      header.setPreferredDefinition(null);
      headerUpdate = true;
    }

   List updatedModules =
       getUpdatedModules(clonedCrf.getModules(), crf.getModules());
   List addedModules =
       getAddedModules( crf.getModules());

    if(!headerUpdate)
       header=null;
    if (
        header!=null || ((deletedModules != null) && !deletedModules.isEmpty()) ||
          !updatedModules.isEmpty()||!addedModules.isEmpty()) {
        setSessionObject(request,FORM_EDIT_HEADER,header,true);
        setSessionObject(request,FORM_EDIT_UPDATED_MODULES,updatedModules,true);
        setSessionObject(request,FORM_EDIT_DELETED_MODULES,deletedModules,true);
        setSessionObject(request,FORM_EDIT_ADDED_MODULES,addedModules,true);
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
}
