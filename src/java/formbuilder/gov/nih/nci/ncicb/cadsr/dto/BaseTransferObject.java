package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Audit;

import java.io.Serializable;

import java.sql.Date;


public class BaseTransferObject implements Serializable, Audit {
  protected String createdBy;
	protected Date createdDate;
	protected String modifiedBy;
	protected Date modifiedDate;

  public BaseTransferObject() {
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String p0) {
    this.createdBy = p0;
  }

  public Date getDateCreated() {
    return createdDate;
  }

  public void setDateCreated(Date p0) {
    this.createdDate = p0;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String p0) {
    this.modifiedBy = p0;
  }

  public Date getDateModified() {
    return modifiedDate;
  }

  public void setDateModified(Date p0) {
    this.modifiedDate = p0;
  }

  public String toString() {
    return " ";
  }
}
