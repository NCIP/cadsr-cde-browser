package gov.nih.nci.ncicb.cadsr.cdebrowser.cdecart;

import gov.nih.nci.ncicb.cadsr.cdebrowser.cdecart.CDECartItem;

import java.util.Collection;

public interface CDECart  {
  public Collection getDataElements();
  public void setDataElements(Collection items);
  public void setDataElement(CDECartItem item);

  public Collection getForms();
  public void setForm(CDECartItem form);
}