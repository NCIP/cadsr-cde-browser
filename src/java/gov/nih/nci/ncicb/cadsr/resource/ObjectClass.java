package gov.nih.nci.ncicb.cadsr.resource;

public interface ObjectClass extends AdminComponent
{
  public String getName();
  public void setName(String newName);
  
  public String getQualifier();
  public void setQualifier(String newQualifier);

  public ConceptDerivationRule getConceptDerivationRule();
  public void setConceptDerivationRule(ConceptDerivationRule newConceptDerivationRule);  
  
}