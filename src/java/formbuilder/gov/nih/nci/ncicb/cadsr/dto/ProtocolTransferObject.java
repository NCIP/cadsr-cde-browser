package gov.nih.nci.ncicb.cadsr.dto;
import java.sql.Date;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;

public class ProtocolTransferObject extends AdminComponentTransferObject implements Protocol 
{
  
  public ProtocolTransferObject(String longName)
  {
      setLongName(longName);
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
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR+"longName="+getLongName());  
    sb.append(OBJ_SEPARATOR_END);  
    return sb.toString();
  }
}