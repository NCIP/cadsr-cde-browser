package gov.nih.nci.ncicb.cadsr.formbuilder.service;

import gov.nih.nci.ncicb.cadsr.formbuilder.common.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;

import java.util.Collection;


public interface FormBuilderServiceDelegate {

  public Collection getAllForms(String formName, String protocol, String context, 
    String workflow, String category, String type);
  
  public Form getFormDetails(String formPK) throws FormBuilderException;

  public Form getFormRow(String formPK) throws FormBuilderException;

  public Form copyForm(Form form) throws FormBuilderException;

  public Form editFormRow(String formPK) throws FormBuilderException;

  public int deleteForm(String formPK) throws FormBuilderException;

  public Form createModule(
    String formPK,
    Module module) throws FormBuilderException;

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
}
