package gov.nih.nci.ncicb.cadsr.common.resource;

public interface ContactCommunication {
   public void setType(String type);

   public String getType();

   public void setValue(String value);

   public String getValue() ;

   public void setId(String id) ;

   public String getId();
   
   public void setRankOrder(int rankOrder);

   public int getRankOrder() ;
   
}
