package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.ValidValue;

public class ValidValueTransferObject implements ValidValue {

  String vdIdseq;
  String shortMeaning;
  String shortMeaningDescription;
  String shortMeaningValue;
  String description;
  
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
  
  public Object clone() throws CloneNotSupportedException {
    return null;
  }
  
 public boolean equals(Object obj)
 {
   if(obj == null)
    return false;
   if(!(obj instanceof ValidValue))
    return false;
   ValidValue vv = (ValidValue)obj;
   if(getShortMeaningValue().equals(vv.getShortMeaningValue()))
   {
     return true;
   }
   return false;
 }
  
}