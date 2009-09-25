package gov.nih.nci.ncicb.cadsr.common.resource;

public interface Property extends AdminComponent
{
  public String getName();
  public void setName(String newName);
  
  public String getQualifier();
  public void setQualifier(String newQualifier);  
  
  public ConceptDerivationRule getConceptDerivationRule();
  public void setConceptDerivationRule(ConceptDerivationRule newConceptDerivationRule);  

}