package gov.nih.nci.ncicb.cadsr.resource.impl;
import gov.nih.nci.ncicb.cadsr.resource.CDECartItem;

import java.sql.Timestamp;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.AdminComponent;
import java.io.Serializable;


public class CDECartItemImpl implements CDECartItem, Serializable {
  protected String id;
  protected String type;
  protected Timestamp createdDate;
  protected String createdBy;
  protected DataElement cde;
  protected AdminComponent ac;
  protected boolean deletedInd;
  protected boolean persistedInd;

  public CDECartItemImpl() {
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
    if (((CDECartItem)obj).getId().equals(id)) {
      return true;
    }
    else {
      return false;
    }
  }

  public int hashCode() {
    return 59878489;
  }

  public AdminComponent getItem() {
    return ac;
  }

  public void setItem(AdminComponent ac) {
    this.ac = ac;
    id = ac.getIdseq();
  }

  public boolean getDeletedInd() {
    return deletedInd;
  }

  public void setDeletedInd(boolean ind) {
    deletedInd = ind;
  }

  public boolean getPersistedInd() {
    return persistedInd;
  }

  public void setPersistedInd(boolean ind) {
    persistedInd = ind;
  }
}
