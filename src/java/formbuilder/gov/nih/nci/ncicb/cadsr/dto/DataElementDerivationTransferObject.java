package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.DataElementDerivationType;
import gov.nih.nci.ncicb.cadsr.resource.DerivedDataElement;
import gov.nih.nci.ncicb.cadsr.resource.DataElementDerivation;
import gov.nih.nci.ncicb.cadsr.util.DebugStringBuffer;
import java.util.Collection;



public class DataElementDerivationTransferObject extends BaseTransferObject
  implements DataElementDerivation{
  private String cdrIdSeq;
  private int displayOrder;
  private DataElement derivedDataElement ;

  public DataElementDerivationTransferObject() {
  }


  public boolean equals(Object obj)
  {/*
   if(obj == null)
    return false;
   if(!(obj instanceof DerivedDataElement))
    return false;
   DerivedDataElement derivedDataElement = (DerivedDataElement)obj;
   if(derivedDataElement.getDdeIdseq().equals(this.getDdeIdSeq()))
   {
     return true;
   }*/
   return false;
 }
  public String toString() {
   return "";
  }


   public void setDisplayOrder(int displayOrder) {
      this.displayOrder = displayOrder;
   }


   public int getDisplayOrder() {
      return displayOrder;
   }


   public void setDerivedDataElement(DataElement derivedDataElement) {
      this.derivedDataElement = derivedDataElement;
   }


   public DataElement getDerivedDataElement() {
      return derivedDataElement;
   }


   public void setCdrIdSeq(String cdrIdSeq) {
      this.cdrIdSeq = cdrIdSeq;
   }


   public String getCdrIdSeq() {
      return cdrIdSeq;
   }



}
