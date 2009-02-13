package gov.nih.nci.ncicb.cadsr.common.resource;

public interface Representation extends AdminComponent
{
    public String getName();
    public void setName(String newName);
    
    public ConceptDerivationRule getConceptDerivationRule();
    public void setConceptDerivationRule(ConceptDerivationRule newConceptDerivationRule);  
     
}