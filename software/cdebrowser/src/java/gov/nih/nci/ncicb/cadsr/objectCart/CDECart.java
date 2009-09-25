package gov.nih.nci.ncicb.cadsr.objectCart;

import java.util.Collection;
import java.util.Date;

public interface CDECart  {
  public Collection getDataElements();
  public void setDataElements(Collection items);
  public void setDataElement(CDECartItem item);

  public Collection getForms();
  public void setForm(CDECartItem form);
  public void setForms(Collection forms);

  public void removeDataElement(String itemId);
  public void removeDataElements(Collection items);

  public CDECartItem findDataElement(String itemId);

  public void mergeCart(CDECart cart);
  public void mergeDataElements(Collection items);
  
  public void associateCart(String userId);
  
  public void expireCart();
  public void expireCart(Date expireDate);
}