package gov.nih.nci.ncicb.cadsr.dto;
import java.sql.Date;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;

public class ProtocolTransferObject extends AdminComponentTransferObject implements Protocol 
{
  private String name;
  
  public ProtocolTransferObject(String newName)
  {
      name=newName;
  }

  public String getProtoIdseq()
  {
    return null;
  }

  public void setProtoIdseq(String p0)
  {
  }

  public String getLeadOrg()
  {
    return null;
  }

  public void setLeadOrg(String p0)
  {
  }

  public String getType()
  {
    return null;
  }

  public void setType(String p0)
  {
  }

  public String getPhase()
  {
    return null;
  }

  public void setPhase(String p0)
  {
  }

  public Date getBeginDate()
  {
    return null;
  }

  public void setBeginDate(Date p0)
  {
  }

  public Date getEndDate()
  {
    return null;
  }

  public void setEndDate(Date p0)
  {
  }
}