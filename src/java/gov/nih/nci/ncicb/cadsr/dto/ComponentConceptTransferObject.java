package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.resource.Concept;


public class ComponentConceptTransferObject implements ComponentConcept {
  private String idseq = null;
  private Concept concept = null;
  private int displayOrder;

  public ComponentConceptTransferObject() {
  }

  /**
   * Get the Id value.
   *
   * @return the Id value.
   */
  public String getIdseq() {
    return idseq;
  }

  public void setIdseq(String newId) {
    idseq = newId;
  }
  
  public Concept getConcept() {
    return concept;
  }

  /**
   * Get the Order value.
   *
   * @return the Order value.
   */
  public int getDisplayOrder() {
    return displayOrder;
  }

  /**
   * Set the Order value.
   *
   * @param newOrder The new Order value.
   */
  public void setDisplayOrder(int newOrder) {
    displayOrder = newOrder;
  }

  public void setConcept(Concept newConcept) {
    concept = newConcept;
  }
}