package gov.nih.nci.ncicb.cadsr.common.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
  public boolean isColumnNumeric(String columnName) {
    Set<String> numericNames = new HashSet(Arrays.asList(NUMERIC_COLUMNS));
    return numericNames.contains(columnName);
  }

}