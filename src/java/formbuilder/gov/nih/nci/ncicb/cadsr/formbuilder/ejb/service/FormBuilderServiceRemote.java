package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service;

import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;

import java.rmi.RemoteException;

import java.util.Collection;


public interface FormBuilderServiceRemote {
  public Collection getAllForms(
    String formLongName,
    String protocolIdSeq,
    String contextIdSeq,
    String workflow,
    String categoryName,
    String type,
    String classificationIdSeq) throws  RemoteException;

  public Form getFormDetails(String formPK)
    throws  RemoteException;

  public Form updateForm(
    Form formHeader,
    Collection updatedModules,
    Collection deletedModules) throws  RemoteException;


  public Form getFormRow(String formPK) throws  RemoteException;

  public Form copyForm(
    String sourceFormPK,
    Form newForm) throws DMLException, RemoteException;

  public Form editFormRow(String formPK) throws  RemoteException;

  public int deleteForm(String formPK) throws  RemoteException;

  public Form createModule(
    String formPK,
    Module module) throws  RemoteException;

  public int removeModule(
    String formPK,
    String modulePK) throws  RemoteException;

  public Form copyModules(
    String formPK,
    Collection modules) throws  RemoteException;

  public Form createQuestions(
    String modulePK,
    Collection questions) throws  RemoteException;

  public Form removeQuestions(
    String modulePK,
    Collection questions) throws  RemoteException;

  public Form copyQuestions(
    String modulePK,
    Collection questions) throws  RemoteException;

  public Form createValidValues(
    String modulePK,
    Collection validValues) throws  RemoteException;

  public Form removeValidValues(
    String modulePK,
    Collection validValues) throws  RemoteException;

  public Form copyValidValues(
    String modulePK,
    Collection validValues) throws  RemoteException;

  public Collection getAllContexts() throws  RemoteException;

  public Collection getAllFormCategories() throws  RemoteException;

  public Collection getStatusesForACType(String acType)
    throws  RemoteException;

  public boolean validateUser(
    String username,
    String password) throws  RemoteException;


  public CDECart retrieveCDECart()
    throws DMLException, RemoteException;

  public int addToCDECart(CDECartItem item)
    throws DMLException, RemoteException;

  public int removeFromCDECart(String itemId)
    throws DMLException, RemoteException;

  /**
   * public Collection getContextsForUserAndRole( String username, String role)
   * throws DMLException, RemoteException;
   */

}
