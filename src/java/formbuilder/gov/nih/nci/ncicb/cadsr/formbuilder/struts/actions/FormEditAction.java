package gov.nih.nci.ncicb.cadsr.formbuilder.struts.actions;

import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.formbuilder.service.FormBuilderServiceDelegate;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.common.FormActionUtil;
import gov.nih.nci.ncicb.cadsr.formbuilder.struts.formbeans.FormBuilderBaseDynaFormBean;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Orderable;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


public class FormEditAction extends FormBuilderBaseDispatchAction {
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
      setSessionObject(request, CLONED_CRF, clonedCrf);
    }
    catch (FormBuilderException exp) {
      if (log.isDebugEnabled()) {
        log.debug("Exception on getFormForEdit =  " + exp);
      }
    }
    catch (CloneNotSupportedException clexp) {
      if (log.isDebugEnabled()) {
        log.debug("Exception on Clone =  " + clexp);
      }
    }

    if ((crf != null) && (formEditForm != null)) {
      FormBuilderBaseDynaFormBean dynaFormEditForm =
        (FormBuilderBaseDynaFormBean) formEditForm;
      dynaFormEditForm.clear();
      dynaFormEditForm.set(this.FORM_ID_SEQ, crf.getFormIdseq());
      dynaFormEditForm.set(this.FORM_LONG_NAME, crf.getLongName());
      dynaFormEditForm.set(
        this.PREFERRED_DEFINITION, crf.getPreferredDefinition());
      dynaFormEditForm.set(CONTEXT_ID_SEQ, crf.getContext().getConteIdseq());
      dynaFormEditForm.set(
        this.PROTOCOL_ID_SEQ, crf.getProtocol().getProtoIdseq());
      dynaFormEditForm.set(
        this.PROTOCOLS_LOV_NAME_FIELD, crf.getProtocol().getLongName());
    }

    if (log.isDebugEnabled()) {
      log.debug("crf =  " + crf);
      log.debug("Cloned crf =  " + clonedCrf);
    }

    setSessionObject(request, DELETED_MODULES, null);

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
      log.info("Move up Module ");
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
      log.info("Move Down Module ");
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

    setSessionObject(request, DELETED_MODULES, deletedModules);

    if (log.isDebugEnabled()) {
      printDisplayOrder(modules);
    }

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

    if (log.isDebugEnabled()) {
      printDisplayOrder(modules);
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
        saveMessage(ERROR_FORM_DELETE_FAILED, request);
        saveMessage(exp.getErrorCode(), request);
        return mapping.findForward(FAILURE);
      }    
    setSessionObject(request, DELETED_MODULES, null);
    setSessionObject(request, CLONED_CRF, null);
    setSessionObject(request, CRF, null);
    saveMessage("cadsr.formbuilder.form.delete.success", request);
    ActionForward forward = mapping.findForward(SUCCESS);
    return forward;
  }

  /**
   * Save Form. Persists only if there are changes to the form header or
   * display order of modules have been changed Deleted Modules are deleted;
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
    DynaActionForm editForm = (DynaActionForm) form;
    Form clonedCrf = (Form) getSessionObject(request, CLONED_CRF);
    Form crf = (Form) getSessionObject(request, CRF);
    List deletedModules = (List) getSessionObject(request, DELETED_MODULES);
    Form header = new FormTransferObject();

    //Add the header info into TransferObj after checking for update
    boolean headerUpdate = false;
    header.setFormIdseq((String) editForm.get(FORM_ID_SEQ));

    String longName = (String) editForm.get(FORM_LONG_NAME);

    if ((longName != null) && (clonedCrf.getLongName() != null)) {
      if (!longName.equals(clonedCrf.getLongName())) {
        header.setLongName(longName);
        headerUpdate = true;
      }
    }

    String contextIdSeq = (String) editForm.get(CONTEXT_ID_SEQ);
    String orgContextIdSeq = null;

    if (clonedCrf.getContext() != null) {
      orgContextIdSeq = clonedCrf.getContext().getConteIdseq();
    }

    if ((contextIdSeq != null) && (orgContextIdSeq != null)) {
      if (!contextIdSeq.equals(orgContextIdSeq)) {
        header.setConteIdseq(contextIdSeq);
        headerUpdate = true;
      }
    }

    String protocolIdSeq = (String) editForm.get(PROTOCOL_ID_SEQ);
    String orgProtocolIdSeq = null;

    if (clonedCrf.getProtocol() != null) {
      orgProtocolIdSeq = clonedCrf.getProtocol().getProtoIdseq();
    }

    if ((protocolIdSeq != null) && (orgProtocolIdSeq != null)) {
      if (!orgProtocolIdSeq.equals(protocolIdSeq)) {
        header.setProtoIdseq(protocolIdSeq);
        headerUpdate = true;
      }
    }

    String preferredDef = (String) editForm.get(PREFERRED_DEFINITION);

    if ((preferredDef != null) && (clonedCrf.getPreferredDefinition() != null)) {
      if (!preferredDef.equals(clonedCrf.getPreferredDefinition())) {
        header.setPreferredDefinition(preferredDef);
        headerUpdate = true;
      }
    }

    List updatedModules =
      getUpdatedModules(clonedCrf.getModules(), crf.getModules());
    FormBuilderServiceDelegate service = getFormBuilderService();
    boolean formUpdated = false;

    if (
      headerUpdate || ((deletedModules != null) && !deletedModules.isEmpty()) ||
          !updatedModules.isEmpty()) {
      if (log.isDebugEnabled()) {
        log.debug("***Form need to be Persisted   ");
      }

      try {
        Form updatedCrf = service.updateForm(header, updatedModules, deletedModules);
        setSessionObject(request,CRF, updatedCrf);
        formUpdated = true;
      }
      catch (FormBuilderException exp) {
        if (log.isDebugEnabled()) {
          log.debug("Exception on service.updateForm=  " + exp);
        }

        saveMessage(ERROR_FORM_SAVE_FAILED, request);
        saveMessage(exp.getErrorCode(), request);
        return mapping.findForward(FAILURE);
      }
    }

    if (formUpdated) { // move to FormDetails Page
      saveMessage("cadsr.formbuilder.form.edit.save.success", request);
      setSessionObject(request, DELETED_MODULES, null);
      setSessionObject(request, CLONED_CRF, null);
      return mapping.findForward(SUCCESS);
    }
    else { // stay on the same Page
      saveMessage("cadsr.formbuilder.form.edit.nochange", request);
      return mapping.findForward(FORM_EDIT);
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

      if (orgModule != null) {
        if (orgModule.getDisplayOrder() != newModule.getDisplayOrder()) {
          updatedModules.add(newModule);
        }
      }
      else {
        updatedModules.add(newModule);
      }
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
