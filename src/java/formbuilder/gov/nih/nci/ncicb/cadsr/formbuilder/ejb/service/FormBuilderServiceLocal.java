package gov.nih.nci.ncicb.cadsr.formbuilder.ejb.service;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;

import java.util.Collection;


public interface FormBuilderServiceLocal {

  public Collection getAllForms(String formLongName, String protocolIdSeq, String contextIdSeq, 
    String workflow, String categoryName, String type) throws DMLException;
  
  public Form getFormDetails(String formPK) throws DMLException;

  public Form getFormRow(String formPK) throws DMLException;

  public Form copyForm(Form form) throws DMLException;

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
}
