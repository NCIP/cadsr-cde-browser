package gov.nih.nci.ncicb.cadsr.util;

public class SimpleSortableColumnHeader implements SortableColumnHeader {
   public SimpleSortableColumnHeader() {
   }
   
   private String primary, secondary;
   private int order=ASCENDING;


   public void setPrimary(String primary) {
      this.primary = primary;
   }


   public String getPrimary() {
      return primary;
   }


   public void setSecondary(String secondary) {
      this.secondary = secondary;
   }


   public String getSecondary() {
      return secondary;
   }


   public void setOrder(int order) {
      this.order = order;
   }


   public int getOrder() {
      return order;
   }

}