package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.CDECart;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;


public class CDECartTransferObject implements CDECart {
  protected Set dataElements;
  protected Set forms;
  
  public CDECartTransferObject() {
    dataElements = new HashSet();
    forms = new HashSet();
  }

  public Collection getDataElements() {
    return dataElements;
  }

  public void setDataElements(
    Collection items) {
    dataElements.addAll(items);
  }

  public Collection getForms() {
    return forms;
  }

  public void setForm(CDECartItem form) {
    forms.add(form);
  }

  public void setDataElement(CDECartItem item) {
    dataElements.add(item);
  }

  public void setForms(Collection forms) {
    forms.addAll(forms);
  }

  public void mergeCart(CDECart cart) {
    Collection deColl = cart.getDataElements();
    this.dataElements.addAll(deColl);
    this.forms.addAll(cart.getForms());
  }

  public static void main(String[] args) { 
    CDECartTransferObject c = new CDECartTransferObject();
    Collection l = new ArrayList();
    CDECartItem i1 = new CDECartItemTransferObject();
    CDECartItem i2 = new CDECartItemTransferObject();

    i1.setId("2");
    i2.setId("1");
    l.add(i1);
    l.add(i2);

    c.setDataElements(l);

    System.out.println("Cart size: "+c.getDataElements().size());
    System.out.println("Hashcode of i1: "+i1.hashCode());
    System.out.println("Hashcode of i2: "+i2.hashCode());
  }
  
}
