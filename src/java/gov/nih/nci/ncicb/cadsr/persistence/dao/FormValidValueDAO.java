package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;

import java.util.Collection;
import java.util.List;


public interface FormValidValueDAO {
  /**
   * Creates a new form valid value component (just the header info).
   *
   * @param <b>newValidValue</b> FormValidValue object
   *
   * @return Id of the new Form Valid Value.
   *
   * @throws <b>DMLException</b>
   */
  public String createFormValidValueComponent(FormValidValue newValidValue,String parentId,
   String userName)
    throws DMLException;

  /**
   * Creates new form valid value components (just the header info).
   *
   * @param <b>validValues</b> Collection of FormValidValue objects.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public void createFormValidValueComponents (List validValues,String parentId)
    throws DMLException;

  /**
   * Changes the display order of the specified form valid value. Display order
   * of the other valid values in the question is also updated accordingly.
   *
   * @param <b>validValueId</b> Idseq of the form valid value component.
   * @param <b>newDisplayOrder</b> New display order of the form valid value
   *        component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateDisplayOrder(
    String validValueId,
    int newDisplayOrder, String username) throws DMLException;

  /**
   * Deletes the specified form valid value and all its associated components.
   *
   * @param <b>validValueId</b> Idseq of the form valid value component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteFormValidValue(String validValueId)
    throws DMLException;
    
  public int updateValueMeaning(String vvIdSeq, String updatedValueMeaningText, 
                            String updatedValueMeaningDesc, String userName)
    throws DMLException;   
}
