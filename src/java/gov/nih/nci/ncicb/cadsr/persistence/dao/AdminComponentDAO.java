package gov.nih.nci.ncicb.cadsr.persistence.dao;

import java.util.Collection;


public interface AdminComponentDAO {
  /**
   * Updates long name of an admin component.
   */
  public int updateLongName(
    String adminIdseq
   ,String newLongName
   ,String username);

  /**
   * Utility method to derive preferred name using the long name.
   *
   * @param <b>longName</b> Long name of the admin component
   *
   * @return <b>String</b> Derived preferred name
   */
  public String generatePreferredName(String longName);

  /**
   * Checks if a user can update an admin component.
   */
  public boolean hasUpdate(
    String username,
    String acIdseq);

  /**
   * Checks if a user can delete an admin component.
   */
  public boolean hasDelete(
    String username,
    String acIdseq);

  /**
   * Checks if a user can create a particular type of an admin component in a
   * context
   */
  public boolean hasCreate(
    String username,
    String acType,
    String conteIdseq);

  /**
   * Assigns the specified classification to an admin component
   *
   * @param <b>acId</b> Idseq of an admin component
   * @param <b>csCsiId</b> csCsiId
   *
   * @return <b>int</b> 1 - success; 0 - failure
   */
  public int assignClassification(
    String acId,
    String csCsiId);

  /**
   * Removes the specified classification assignment for an admin component
   *
   * @param <b>acCsiId</b> acCsiId
   *
   * @return <b>int</b> 1 - success; 0 - failure
   */
  public int removeClassification(String acCsiId);

  /**
   * Retrieves all the assigned classifications for an admin component
   *
   * @param <b>acId</b> Idseq of an admin component
   *
   * @return <b>Collection</b> Collection of CSITransferObject
   */
  public Collection retrieveClassifications(String acId);
}
