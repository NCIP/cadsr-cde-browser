package gov.nih.nci.ncicb.cadsr.util;

public class SimpleSortableColumnHeader implements SortableColumnHeader {
   public SimpleSortableColumnHeader() {
   }
   
   private String primary, secondary, tertiary;
   private boolean defaultOrder;
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

  public String getTertiary()
  {
    return tertiary;
  }
  public void setTertiary(String tertiary)
  {
    this.tertiary=tertiary;
  }

   public void setOrder(int order) {
      this.order = order;
   }


   public int getOrder() {
      return order;
   }

  public boolean isDefaultOrder()
  {
    return defaultOrder;
  }

  public void setDefaultOrder(boolean defaultOrder)
  {
    this.defaultOrder = defaultOrder;
  }

}