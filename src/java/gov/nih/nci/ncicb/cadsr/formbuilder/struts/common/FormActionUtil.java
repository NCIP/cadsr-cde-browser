package gov.nih.nci.ncicb.cadsr.formbuilder.struts.common;
import gov.nih.nci.ncicb.cadsr.resource.Orderable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FormActionUtil 
{
  public FormActionUtil()
  {
  }
  
  /**
   * increments the display order of Elements by 1 starting from "startIndex" of
   * the list
   *
   * @param List of Orderable elements
   * @param startIndex
   */
  public static void incrementDisplayOrder(
    List list,
    int startIndex) {
    ListIterator iterate = list.listIterator(startIndex);

    while (iterate.hasNext()) {
      Orderable orderableObj = (Orderable) iterate.next();
      int displayOrder = orderableObj.getDisplayOrder();
      if(displayOrder >++displayOrder)
        return;
      orderableObj.setDisplayOrder(++displayOrder);
    }
  }

  /**
   * Decrments the display order of Elements by 1 starting from "startIndex" of
   * the list
   *
   * @param List of Orderable elements
   * @param startIndex
   */
  public static void decrementDisplayOrder(
    List list,
    int startIndex) {
    ListIterator iterate = list.listIterator(startIndex);

    while (iterate.hasNext()) {
      Orderable orderable = (Orderable) iterate.next();
      int displayOrder = orderable.getDisplayOrder();
      if(displayOrder < --displayOrder)
        return;      
      orderable.setDisplayOrder(--displayOrder);
    }
  }

  public static void setInitDisplayOrders(List orderables)
  {
    int displayOrder = 0;
    for (Iterator it = orderables.iterator(); it.hasNext();) {
        Orderable element = (Orderable)it.next();
        element.setDisplayOrder(displayOrder);
        displayOrder++;
    }
  }  
}