package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;

import java.util.Collection;
import java.util.List;


public interface FormDAO {
  public Collection getAllForms(
    String formLongName,
    String protocolIdSeq,
    String contextIdSeq,
    String workflow,
    String categoryName,
    String type,
    String classificationIdseq);

  /**
   * Gets all the modules in a form
   *
   * @param <b>formId</b> Idseq of the form component.
   *
   * @return <b>Collection</b> List of module objects
   */
  public Collection getModulesInAForm(String formId);

  /**
   * Creates a new form by copying an existing form.
   * Hyun: Use stored procedure: sbrext_form_builder_pkg.copy_crf 
   * @param <b>sourceFormId</b> Idseq of the form that is being copied.
   * @param <b>newForm</b> Specifies data for new form
   *
   * @return <b>String</b> Idseq of the new form.
   *
   * @throws <b>DMLException</b>
   */
  public String copyForm(
    String sourceFormId,
    Form newForm) throws DMLException;

  /**
   * Creates a new form component (just the header info).
   *
   * @param <b>sourceForm</b> Form object
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int createFormComponent(Form sourceForm) throws DMLException;

  /**
   * Adds new modules to a form. 
   * Hyun: Don't implement this method
   *
   * @param <b>formId</b> Idseq of the form component.
   * @param <b>modules</b> Module objects to be added to the form
   *
   * @return <b>Form</b> Form object representing the form after addition of
   *         new modules.
   *
   * @throws <b>DMLException</b>
   */
  public Form addModules(
    String formId,
    Collection modules) throws DMLException;

  /**
   * Adds new module to a form. 
   * Hyun: Don't implement this method
   *
   * @param <b>formId</b> Idseq of the form component.
   * @param <b>module</b> Module object to be added to the form
   *
   * @return <b>Form</b> Form object representing the form after addition of
   *         new module.
   *
   * @throws <b>DMLException</b>
   */
  public Form addModule(
    String formId,
    Module module) throws DMLException;

  /**
   * Finds a form based on the primary key.
   *
   * @param <b>formId</b> Idseq of the form component.
   *
   * @return <b>Form</b> Form object representing the form.
   *
   * @throws <b>DMLException</b>
   */
  public Form findFormByPrimaryKey(String formId) throws DMLException;

  /**
   * Changes (updates) a form component (just the header info).
   *
   * @param <b>newForm</b> Form object
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateFormComponent(Form newForm) throws DMLException;

  /**
   * Deletes the entire form including all the components associated with it.
   * @param <b>formId</b> Idseq of the form component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteForm(String formId) throws DMLException;
}
