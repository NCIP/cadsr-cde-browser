package gov.nih.nci.ncicb.cadsr.resource;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;

import java.sql.Date;
import java.sql.Timestamp;


public interface Audit extends CaDSRConstants,Cloneable {
  public String getCreatedBy();

  public void setCreatedBy(String pCreatedBy);

  public Timestamp getDateCreated();

  public void setDateCreated(Timestamp pCreatedDate);

  public String getModifiedBy();

  public void setModifiedBy(String pModifiedBy);

  public Timestamp getDateModified();

  public void setDateModified(Timestamp pModifiedDate);

  
  public String toString();
}
