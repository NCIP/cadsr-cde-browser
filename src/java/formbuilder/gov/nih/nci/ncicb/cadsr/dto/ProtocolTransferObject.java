package gov.nih.nci.ncicb.cadsr.dto;
import java.sql.Date;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;

public class ProtocolTransferObject extends AdminComponentTransferObject implements Protocol 
{
  String protocolIdseq = null;
  String longName = null;
  String leadOrg = null;
  String protocolType = null;
  String protocolPhase = null;
  Date endDate = null;
  Date beginDate = null;
  
  
  public ProtocolTransferObject(String longName)
  {
      setLongName(longName);
  }

  public String getProtoIdseq()
  {
    return protocolIdseq;
  }

  public void setProtoIdseq(String protocolIdseq)
  {
    this.protocolIdseq = protocolIdseq;
  }

  public String getLeadOrg()
  {
    return leadOrg;
  }

  public void setLeadOrg(String leadOrg)
  {
    this.leadOrg = leadOrg;
  }

  public String getType()
  {
    return protocolType;
  }

  public void setType(String protocolType)
  {
    this.protocolType = protocolType;
  }

  public String getPhase()
  {
    return protocolPhase;
  }

  public void setPhase(String protocolPhase)
  {
    this.protocolPhase = protocolPhase;
  }

  public Date getBeginDate()
  {
    return beginDate;
  }

  public void setBeginDate(Date beginDate)
  {
    this.beginDate = beginDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public void setEndDate(Date endDate)
  {
    this.endDate = endDate;
  }
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR+"longName="+getLongName());  
    sb.append(ATTR_SEPARATOR+"protocolIdseq="+getProtoIdseq());
    sb.append(OBJ_SEPARATOR_END);  
    return sb.toString();
  }
}