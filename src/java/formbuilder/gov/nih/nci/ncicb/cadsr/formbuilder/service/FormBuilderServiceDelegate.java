package gov.nih.nci.ncicb.cadsr.formbuilder.service;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.ModuleInstruction;

import java.util.Collection;


public interface FormBuilderServiceDelegate {
  public Collection getAllForms(
    String formName,
    String protocol,
    String context,
    String workflow,
    String category,
    String type,
    String classificationIdSeq);

  public Form getFormDetails(String formPK) throws FormBuilderException;

  public Form updateForm(
    Form formHeader,
    Collection updatedModules,
    Collection deletedModules) throws FormBuilderException;

  public Form getFormRow(String formPK) throws FormBuilderException;

  public Form copyForm(
    String sourceFormPK,
    Form newForm) throws FormBuilderException;

  public Form editFormRow(String formPK) throws FormBuilderException;

  public int deleteForm(String formPK) throws FormBuilderException;

  public String createModule(
    Module module,
    ModuleInstruction moduleInstruction) throws FormBuilderException;

  public int removeModule(
    String formPK,
    String modulePK) throws FormBuilderException;

  public Form copyModules(
    String formPK,
    Collection modules) throws FormBuilderException;

  public Form createQuestions(
    String modulePK,
    Collection questions) throws FormBuilderException;

  public Form removeQuestions(
    String modulePK,
    Collection questions) throws FormBuilderException;

  public Form copyQuestions(
    String modulePK,
    Collection questions) throws FormBuilderException;

  public Form createValidValues(
    String modulePK,
    Collection validValues) throws FormBuilderException;

  public Form removeValidValues(
    String modulePK,
    Collection validValues) throws FormBuilderException;

  public Form copyValidValues(
    String modulePK,
    Collection validValues) throws FormBuilderException;

  public Collection getAllContexts();

  public Collection getAllFormCategories();

  public Collection getStatusesForACType(String acType);

  public boolean validateUser(
    String username,
    String password) throws FormBuilderException;

  public CDECart retrieveCDECart() throws FormBuilderException;

  public int addToCDECart(Collection items) throws FormBuilderException;

  public int removeFromCDECart(Collection items) throws FormBuilderException;

  public int updateDEAssociation(
    String questionId,
    String deId,
    String newLongName,
    String username) throws FormBuilderException;
}
