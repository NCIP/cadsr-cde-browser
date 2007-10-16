package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.resource.Representation;

public class RepresentationTransferObject extends AdminComponentTransferObject implements Representation
{
    private String name=null;
    private ConceptDerivationRule conceptDerivationRule;
    
    public RepresentationTransferObject()
    {
    }
    public String getName()
    {
      return name;
    }
    public void setName(String newName)
    {
      name=newName;
    }
    
    
    public ConceptDerivationRule getConceptDerivationRule()
    {
      return conceptDerivationRule;
    }
    public void setConceptDerivationRule(ConceptDerivationRule newConceptDerivationRule)
    {
      conceptDerivationRule=newConceptDerivationRule;
    }
}