package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import java.util.List;
import java.sql.Date;
import gov.nih.nci.ncicb.cadsr.resource.AdminComponent;

public class AdminComponentTransferObject extends BaseTransferObject implements AdminComponent 
{

  private String preferredName;
  private Context context;
  private String longName;
  private String aslName;
  
  public AdminComponentTransferObject()
  {
  }

  public String getPreferredName()
  {
    return preferredName;
  }

  public void setPreferredName(String newPreferredName)
  {
    preferredName=newPreferredName;
  }

  public String getLongName()
  {
    return longName;
  }

  public void setLongName(String newLongName)
  {
    longName=newLongName;
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
    return aslName;
  }

  public void setAslName(String newAslName)
  {
    aslName=newAslName;
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
    return context;
  }

  public void setContext(Context newContext)
  {
    context=newContext;
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