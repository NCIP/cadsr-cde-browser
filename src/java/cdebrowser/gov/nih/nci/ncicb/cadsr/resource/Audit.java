package gov.nih.nci.ncicb.cadsr.resource;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import java.sql.Date;

public interface Audit extends CaDSRConstants
{
  public String getCreatedBy();
  public void setCreatedBy(String pCreatedBy);

  public Date getDateCreated();
  public void setDateCreated(Date pCreatedDate);

  public String getModifiedBy();
  public void setModifiedBy(String pModifiedBy);

  public Date getDateModified();
  public void setDateModified(Date pModifiedDate);

  public String toString();

}