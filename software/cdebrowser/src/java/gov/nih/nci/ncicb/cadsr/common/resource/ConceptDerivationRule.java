package gov.nih.nci.ncicb.cadsr.common.resource;
import java.util.List;

public interface ConceptDerivationRule {
  /**
   * Get the Id value.
   *
   * @return the Id value.
   */
  public String getIdseq();

  public String getName();

  /**
   * Get the Type value.
   *
   * @return the Type value.
   */
  public String getType();

  /**
   * Set the Type value.
   *
   * @param newType The new Type value.
   */
  public void setType(String newType);

  /**
   * Set the Id value.
   *
   * @param newId The new Id value.
   */
  public void setIdseq(String newId);

  public void setName(String name);
  
  public void setComponentConcepts(List componentConcepts);

  public List getComponentConcepts();
  
  public void addComponentConcept(ComponentConcept comp);
  
  public void setRule(String rule);


  public String getRule();


  public void setMethods(String methods);


  public String getMethods();


  public void setConcatenationChar(String concatenationChar);


  public String getConcatenationChar();
  
}
