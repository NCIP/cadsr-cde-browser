package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Audit;

import java.io.Serializable;

import java.sql.Date;
import java.sql.Timestamp;


public class BaseTransferObject implements Serializable, Audit {
  protected String createdBy;
	protected Timestamp createdDate;
	protected String modifiedBy;
	protected Timestamp modifiedDate;

  public BaseTransferObject() {
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String p0) {
    this.createdBy = p0;
  }

  public Timestamp getDateCreated() {
    return createdDate;
  }

  public void setDateCreated(Timestamp p0) {
    this.createdDate = p0;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String p0) {
    this.modifiedBy = p0;
  }

  public Timestamp getDateModified() {
    return modifiedDate;
  }

  public void setDateModified(Timestamp p0) {
    this.modifiedDate = p0;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(ATTR_SEPARATOR + "createdBy=" + getCreatedBy());
    if (this.getDateCreated() == null)
      sb.append(ATTR_SEPARATOR + "dateCreated=null");
    else
      sb.append(ATTR_SEPARATOR + "dateCreated=" + getDateCreated().toString());
    sb.append(ATTR_SEPARATOR + "modifiedBy=" + getModifiedBy());
    if (this.getDateModified() == null)
      sb.append(ATTR_SEPARATOR + "dateModified=null");
    else 
      sb.append(ATTR_SEPARATOR + "dateModified=" + getDateModified().toString());
    
    return sb.toString();
    //return " ";
  }
}
