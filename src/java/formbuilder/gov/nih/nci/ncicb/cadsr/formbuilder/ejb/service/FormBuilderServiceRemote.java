package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;

import java.rmi.RemoteException;

import java.util.Collection;


public interface FormBuilderServiceRemote {

  public Collection getFormsByContext(String context)
     throws DMLException, RemoteException;
  
  public Form getFormDetails(String formPK)
    throws DMLException, RemoteException;
  public Form getFormRow(String formPK)
    throws DMLException, RemoteException;
  public Form copyForm(Form form)
    throws DMLException, RemoteException;
  public Form editFormRow(String formPK)
    throws DMLException, RemoteException;
  public int deleteForm(String formPK)
    throws DMLException, RemoteException;
  public Form createModule(String formPK, Module module)
    throws DMLException, RemoteException;
  public int removeModule(String formPK, String modulePK)
    throws DMLException, RemoteException;
  public Form copyModules(String formPK, Collection modules)
    throws DMLException, RemoteException;
  public Form createQuestions(String modulePK, Collection questions)
    throws DMLException, RemoteException;
  public Form removeQuestions(String modulePK, Collection questions)
    throws DMLException, RemoteException;
  public Form copyQuestions(String modulePK, Collection questions)
    throws DMLException, RemoteException;
  public Form createValidValues(String modulePK, Collection validValues)
    throws DMLException, RemoteException;
  public Form removeValidValues(String modulePK, Collection validValues)
    throws DMLException, RemoteException;
  public Form copyValidValues(String modulePK, Collection validValues)
    throws DMLException, RemoteException;
}
