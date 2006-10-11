package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;
import gov.nih.nci.ncicb.cadsr.resource.ValueMeaning;

import java.util.Collection;

public class ValidValueTransferObject implements ValidValue {

  protected String vdIdseq;
  protected String vpIdseq;
  protected String shortMeaning;
  protected String shortMeaningDescription;
  protected String shortMeaningValue;
  protected String description;
  protected Collection instructions = null;
  protected ConceptDerivationRule conceptDerivationRule = null;
  protected ValueMeaning valueMeaning = null;
  
  public ValidValueTransferObject() {
  }


  public String getVdIdseq() {
    return vdIdseq;
  }

  public void setVdIdseq(String aVdIdseq) {
    this.vdIdseq = vdIdseq;    
  }

  public String getShortMeaning() {
    return shortMeaning;
  }

  public void setShortMeaning(String shortMeaning) {
    this.shortMeaning = shortMeaning;
  }

  public String getShortMeaningDescription() {
    return shortMeaningDescription;
  }

  public void setShortMeaningDescription(String aShortMeaningDescription) {
    this.shortMeaningDescription = shortMeaningDescription;
  }

  public String getShortMeaningValue() {
    return shortMeaningValue;
  }

  public void setShortMeaningValue(String shortMeaningValue) {
    this.shortMeaningValue = shortMeaningValue;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
 
  public Collection getInstructions()
  {
    return instructions;
  }
  public void setInstructions(Collection newInstructions)
  {
    instructions=newInstructions;
  }
  public Object clone() throws CloneNotSupportedException {
    return null;
  }

  /**
   * This equals method only compares the Idseq to define equals
   * @param obj
   * @return 
   */  
 public boolean equals(Object obj)
 {
   if(obj == null)
    return false;
   if(!(obj instanceof ValidValue))
    return false;
   ValidValue vv = (ValidValue)obj;

  if(this.getShortMeaningValue().equalsIgnoreCase(vv.getShortMeaningValue()))
      return true;
    else
      return false;
 }

  public String getVpIdseq() {
    return vpIdseq;
  }

  public void setVpIdseq(String aVpIdseq) {
    vpIdseq = aVpIdseq;
  }
    public ConceptDerivationRule getConceptDerivationRule()
   {
     return conceptDerivationRule;
   }
   public void setConceptDerivationRule(ConceptDerivationRule rule)
   {
     conceptDerivationRule = rule;
   } 
   
    public void setValueMeaning(ValueMeaning vm){
        valueMeaning = vm;
    }
    
    public ValueMeaning getValueMeaning() {
        return valueMeaning;
    }
}