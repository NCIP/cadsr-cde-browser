package gov.nih.nci.ncicb.cadsr.util;

public interface SortableColumnHeader
{
 public static int ASCENDING=1;
 public static int DESCENDING=-1;
 public void setSecondary(String secondary);
 public String getSecondary();
 public void setPrimary(String primary);
 public String getPrimary();
 public int getOrder();
 public void setOrder(int order);
}