package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import java.util.List;
import java.sql.Date;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;

public class FormTransferObject extends BaseTransferObject implements Form 
{
  protected String preferredName;
  
  public FormTransferObject()
  {
  }

  public String getFormIdseq()
  {
    return null;
  }

  public void setFormIdseq(String p0)
  {
  }

  public String getFormType()
  {
    return null;
  }

  public void setFormType(String p0)
  {
  }

  public Protocol getProtocol()
  {
    return null;
  }

  public String getProtoIdseq()
  {
    return null;
  }

  public void setProtoIdseq(String p0)
  {
  }

  public List getModules()
  {
    return null;
  }

  public void setModules(List p0)
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

  public String getPreferredName()
  {
    return preferredName;
  }

  public void setPreferredName(String p0)
  {
    preferredName=p0;
  }

  public String getLongName()
  {
    return null;
  }

  public void setLongName(String p0)
  {
  }

  public Float getVersion()
  {
    return null;
  }

  public void setVersion(Float p0)
  {
  }

  public String getPreferredDefinition()
  {
    return null;
  }

  public void setPreferredDefinition(String p0)
  {
  }

  public String getAslName()
  {
    return null;
  }

  public void setAslName(String p0)
  {
  }

  public String getLatestVersionInd()
  {
    return null;
  }

  public void setLatestVersionInd(String p0)
  {
  }

  public String getDeletedInd()
  {
    return null;
  }

  public void setDeletedInd(String p0)
  {
  }

  public String getConteIdseq()
  {
    return null;
  }

  public void setConteIdseq(String p0)
  {
  }

  public Context getContext()
  {
    return null;
  }

  public void setContext(Context p0)
  {
  }

  public List getRefereceDocs()
  {
    return null;
  }

  public List getDesignations()
  {
    return null;
  }

  public int getPublicId()
  {
    return 0;
  }

  public void setPublicId(int p0)
  {
  }

  public String getOrigin()
  {
    return null;
  }

  public void setOrigin(String p0)
  {
  }
}