package gov.nih.nci.ncicb.cadsr.common.dto;

import gov.nih.nci.ncicb.cadsr.common.dto.BaseTransferObject;
import gov.nih.nci.ncicb.cadsr.common.resource.ContactCommunication;


public class ContactCommunicationTransferObject extends BaseTransferObject
  implements ContactCommunication{
   private String type;
   private String value;
   private String id;
   private int rankOrder;
   public ContactCommunicationTransferObject() {
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getType() {
      return type;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getValue() {
      return value;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getId() {
      return id;
   }

   public void setRankOrder(int rankOrder) {
      this.rankOrder = rankOrder;
   }

   public int getRankOrder() {
      return rankOrder;
   }
}
