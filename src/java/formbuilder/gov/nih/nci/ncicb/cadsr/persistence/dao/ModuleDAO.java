package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;

import java.util.Collection;


public interface ModuleDAO {
  /**
   * Gets all the questions in a module
   *
   * @param <b>moduleId</b> Idseq of the module component.
   *
   * @return <b>Collection</b> List of question objects
   */
  public Collection getQuestionsInAModule(String moduleId);

  /**
   * Creates a new module component (just the header info).
   *
   * @param <b>sourceModule</b> Module object
   *
   * @return <b>Module</b> Module object representing the new module.
   *
   * @throws <b>DMLException</b>
   */
  public Module createModuleComponent(Module sourceModule)
    throws DMLException;

  /**
   * Adds new questions to a module. 
   * Hyun: Don't implement this method
   *
   * @param <b>moduleId</b> Idseq of the form component.
   * @param <b>questions</b> Question objects to be added to the module
   *
   * @return <b>Module</b> Module object representing the module after addition
   *         of new questions.
   *
   * @throws <b>DMLException</b>
   */
  public Module addQuestions(
    String moduleId,
    Collection questions) throws DMLException;

  /**
   * Adds new question to a module. 
   * Hyun: Don't implement this method
   *
   * @param <b>moduleId</b> Idseq of the form component.
   * @param <b>question</b> Question object to be added to the module
   *
   * @return <b>Module</b> Module object representing the module after addition
   *         of new question.
   *
   * @throws <b>DMLException</b>
   */
  public Module addQuestion(
    String moduleId,
    Question question) throws DMLException;

  /**
   * Deletes the specified module and all its associated components.
   *
   * @param <b>moduleId</b> Idseq of the module component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteModule(String moduleId) throws DMLException;

  /**
   * Changes the display order of the specified module. Display order of the
   * other modules the form is also updated accordingly.
   * 
   * Hyun: Use stored procedure: sbrext_form_builder_pkg.remove_module
   *
   * @param <b>moduleId</b> Idseq of the module component.
   * @param <b>newDisplayOrder</b> New display order of the module component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateDisplayOrder(
    String moduleId,
    int newDisplayOrder) throws DMLException;

  /**
   * Changes the long name of a module.
   *
   * @param <b>moduleId</b> Idseq of the module component.
   * @param <b>newLongName</b> New long name of the module component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateModuleLongName(
    String moduleId,
    String newLongName) throws DMLException;
}
