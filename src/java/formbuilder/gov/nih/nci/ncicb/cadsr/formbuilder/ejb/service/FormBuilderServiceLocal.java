package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;

import java.util.Collection;
import java.util.Map;


public interface FormBuilderServiceLocal  {
  public Collection getAllForms(
    String formLongName,
    String protocolIdSeq,
    String contextIdSeq,
    String workflow,
    String categoryName,
    String type,
    String classificationIdSeq) throws DMLException;

  public Form getFormDetails(String formPK) throws DMLException;

  public Form getFormRow(String formPK) ;

  public Form updateForm(
    Form formHeader,
    Collection updatedModules,
    Collection deletedModules) ;


  public Form copyForm(
    String sourceFormPK,
    Form newForm) throws DMLException;

  public Form editFormRow(String formPK) throws DMLException;

  public int deleteForm(String formPK) throws DMLException;

  public Form createModule(
    String formPK,
    Module module) throws DMLException;

  public int removeModule(
    String formPK,
    String modulePK) throws DMLException;

  public Form copyModules(
    String formPK,
    Collection modules) throws DMLException;

  public Form createQuestions(
    String modulePK,
    Collection questions) throws DMLException;

  public Form removeQuestions(
    String modulePK,
    Collection questions) throws DMLException;

  public Form copyQuestions(
    String modulePK,
    Collection questions) throws DMLException;

  public Form createValidValues(
    String modulePK,
    Collection validValues) throws DMLException;

  public Form removeValidValues(
    String modulePK,
    Collection validValues) throws DMLException;

  public Form copyValidValues(
    String modulePK,
    Collection validValues) throws DMLException;

  public Collection getAllContexts() throws DMLException;

  public Collection getAllFormCategories() throws DMLException;

  public Collection getStatusesForACType(String acType)
    throws DMLException;

  public boolean validateUser(
    String username,
    String password) throws DMLException;

  public CDECart retrieveCDECart();

  public int addToCDECart(Collection items);

  public int removeFromCDECart(Collection items);

  public int updateDEAssociation(
    String questionId,
    String deId,
    String newLongName,
    String username);
    
  public Map getValidValues(Collection vdIdSeqs);
}
