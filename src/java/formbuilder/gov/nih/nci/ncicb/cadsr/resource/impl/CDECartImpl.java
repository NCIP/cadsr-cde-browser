package gov.nih.nci.ncicb.cadsr.resource.impl;
import java.util.Collection;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;
import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.cdecart.CDECartItemComparator;
import java.util.*;
import java.io.Serializable;

public class CDECartImpl implements CDECart, Serializable  {
  private Map deCart;
  private Map formCart;
  private CDECartItemComparator itemComparator;
  public CDECartImpl() {
    deCart = new HashMap();
    formCart = new HashMap();
    itemComparator = new CDECartItemComparator ();
  }

  public Collection getDataElements() {
    Collection items = deCart.values();
    List itemList = new ArrayList(items);
    Collections.sort(itemList,itemComparator);
    return itemList;
  }

  public void setDataElements(Collection items) {
    Iterator itemIter = items.iterator();
    while (itemIter.hasNext()) {
      setDataElement((CDECartItem)itemIter.next());
    }
  }

  public void setDataElement(CDECartItem item) {
    if (!deCart.containsKey(item.getId()))
      deCart.put(item.getId(), item);
  }

  public Collection getForms() {
    return formCart.values();
  }

  public void setForm(CDECartItem form) {
    if (!formCart.containsKey(form.getId()))
      formCart.put(form.getId(), form);
  }

  public void setForms(Collection forms) {
    Iterator itemIter = forms.iterator();
    while (itemIter.hasNext()) {
      setForm((CDECartItem)itemIter.next());
    }
  }

  public void mergeCart(CDECart cart) {
    Collection deColl = cart.getDataElements();
    setDataElements(deColl);
    //Collection formColl = cart.getForms();
    //setForms(formColl);
  }

  public void removeDataElement(String itemId) {
    deCart.remove(itemId);
  }

  public void removeDataElements(Collection items) {
    Iterator itemIter = items.iterator();
    while (itemIter.hasNext()) {
      removeDataElement((String)itemIter.next());
    }
  }

  public CDECartItem findDataElement(String itemId) {
    CDECartItem item = null;
    if (deCart.containsKey(itemId))
      item = (CDECartItem)deCart.get(itemId);
    return item; 
  }
}