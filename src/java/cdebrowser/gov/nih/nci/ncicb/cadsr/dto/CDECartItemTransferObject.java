package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.cdebrowser.cdecart.CDECartItem;

import java.sql.Timestamp;


public class CDECartItemTransferObject implements CDECartItem {
  protected String id;
  protected String type;
  protected Timestamp createdDate;
  protected String createdBy;

  public CDECartItemTransferObject() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String user) {
    createdBy = user;
  }

  public boolean equals(Object obj) {
    if (((CDECartItem) obj).getId().equals(id)) {
      return true;
    }
    else {
      return false;
    }
  }

  public int hashCode() {
    return 59878489;
  }
}
