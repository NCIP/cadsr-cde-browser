package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormInstruction;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.ModuleInstruction;

import java.rmi.RemoteException;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface FormBuilderServiceRemote {
  public Collection getAllForms(
    String formLongName,
    String protocolIdSeq,
    String contextIdSeq,
    String workflow,
    String categoryName,
    String type,
    String classificationIdSeq) throws RemoteException;

  public Form getFormDetails(String formPK) throws RemoteException;

  public Form updateForm(
    Form formHeader,
    Collection updatedModules,
    Collection deletedModules) throws RemoteException;

  public Module updateModule(
       String moduleIdSeq,
       Module moduleHeader,
       Collection updatedQuestions,
       Collection deletedQuestions,
       Collection newQuestions,
       Map updatedValidValues,
       Map addedValidValues,
       Map deletedValidValues) throws RemoteException;
       
  public Form getFormRow(String formPK) throws RemoteException;

  public Form copyForm(
    String sourceFormPK,
    Form newForm) throws RemoteException;

  public Form editFormRow(String formPK) throws RemoteException;

  public int deleteForm(String formPK) throws RemoteException;

  /**
   * Creates a Module
   *
   * @param module a <code>Module</code> value
   * @param modInstrustion a <code>ModuleInstruction</code> value
   *
   * @return The PK of the newly created Module
   *
   * @exception RemoteException if an error occurs
   */
  public String createModule(
    Module module,
    ModuleInstruction modInstrustion) throws RemoteException;

  public int removeModule(
    String formPK,
    String modulePK) throws RemoteException;

  public Form copyModules(
    String formPK,
    Collection modules) throws RemoteException;

  public Form createQuestions(
    String modulePK,
    Collection questions) throws RemoteException;

  public Form removeQuestions(
    String modulePK,
    Collection questions) throws RemoteException;

  public Form copyQuestions(
    String modulePK,
    Collection questions) throws RemoteException;

  public Form createValidValues(
    String modulePK,
    Collection validValues) throws RemoteException;

  public Form removeValidValues(
    String modulePK,
    Collection validValues) throws RemoteException;

  public Form copyValidValues(
    String modulePK,
    Collection validValues) throws RemoteException;

  public Collection getAllContexts() throws RemoteException;

  public Collection getAllFormCategories() throws RemoteException;

  public Collection getStatusesForACType(String acType)
    throws RemoteException;

  public boolean validateUser(
    String username,
    String password) throws RemoteException;

  public CDECart retrieveCDECart() throws RemoteException;

  public int addToCDECart(Collection items) throws RemoteException;

  public int removeFromCDECart(Collection items) throws RemoteException;

  public int updateDEAssociation(
    String questionId,
    String deId,
    String newLongName,
    String username) throws RemoteException;

  public Map getValidValues(Collection vdIdSeqs) throws RemoteException;

  public Form createForm(
    Form form,
    FormInstruction formHeaderInstruction,
    FormInstruction formFooterInstruction);
  
}
