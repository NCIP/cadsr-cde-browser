package gov.nih.nci.ncicb.cadsr.cdebrowser.cdecart;

import java.sql.Timestamp;

public interface CDECartItem  {
  public String getId();
  public void setId(String id);

  public String getType();
  public void setType(String type);

  public Timestamp getCreatedDate();
  public String getCreatedBy();

  public void setCreatedBy(String user);
}