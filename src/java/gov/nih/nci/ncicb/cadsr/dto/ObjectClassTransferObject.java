package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.resource.ObjectClass;

public class ObjectClassTransferObject  extends AdminComponentTransferObject
 implements ObjectClass
{
  private String name=null;
  private String qualifier=null;
  private ConceptDerivationRule conceptDerivationRule;
  
  public ObjectClassTransferObject()
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
  
  public String getQualifier()
  {
    return qualifier;
  }
  public void setQualifier(String newQualifier)
  {
    qualifier=newQualifier;
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