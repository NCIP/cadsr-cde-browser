package gov.nih.nci.ncicb.cadsr.dto;
import java.io.Serializable;
import java.sql.Date;
import gov.nih.nci.ncicb.cadsr.resource.Audit;

public class BaseTransferObject implements Serializable, Audit
{
  public BaseTransferObject()
  {
  }

  public String getCreatedBy()
  {
    return null;
  }

  public void setCreatedBy(String p0)
  {
  }

  public Date getDateCreated()
  {
    return null;
  }

  public void setDateCreated(Date p0)
  {
  }

  public String getModifiedBy()
  {
    return null;
  }

  public void setModifiedBy(String p0)
  {
  }

  public Date getDateModified()
  {
    return null;
  }

  public void setDateModified(Date p0)
  {
  }
}