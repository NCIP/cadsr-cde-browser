package gov.nih.nci.ncicb.cadsr.resource;
import java.util.List;

public interface ComponentConcept extends Orderable {
  /**
   * Get the Id value.
   *
   * @return the Id value.
   */
  public String getIdseq();

  public Concept getConcept();

  public void setConcept(Concept newConcept);

  /**
   * Set the Id value.
   *
   * @param newId The new Id value.
   */
  public void setIdseq(String newId);
  
  public boolean getIsPrimary();
  
  public void setIsPrimary(boolean isPrimary);

}
