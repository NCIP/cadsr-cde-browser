package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.Form;

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
   * Creates a new form by copying an existing form
   *
   * @param <b>sourceForm</b> Form that is being copied.
   * @param <b>newForm</b> Specifies data for new form
   *
   * @return <b>Form</b> Form object representing the new form.
   *
   * @throws <b>DMLException</b>
   */
  public Form copyForm(
    Form sourceForm,
    Form newForm) throws DMLException;

  /**
   * Creates a new form component (just the header info).
   *
   * @param <b>sourceForm</b> Form object
   *
   * @return <b>Form</b> Form object representing the new form.
   *
   * @throws <b>DMLException</b>
   */
  public Form createFormComponent(Form sourceForm) throws DMLException;

  /**
   * Adds new modules to a form.
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
   * Finds a form based on the primary key.
   *
   * @param <b>formId</b> Idseq of the form component.
   *
   * @return <b>Form</b> Form object representing the form.
   *
   * @throws <b>DMLException</b>
   */
  public Form findFormByPrimaryKey(String formId) throws DMLException;
}
