package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.util.DebugStringBuffer;
import java.sql.Date;


public class ProtocolTransferObject extends AdminComponentTransferObject
  implements Protocol {
  String protocolIdseq = null;
  String longName = null;
  String leadOrg = null;
  String protocolType = null;
  String protocolPhase = null;
  Date endDate = null;
  Date beginDate = null;

  public ProtocolTransferObject() {
  }
  public ProtocolTransferObject(String longName) {
    setLongName(longName);
  }

  public String getProtoIdseq() {
    return protocolIdseq;
  }

  public void setProtoIdseq(String protocolIdseq) {
    this.protocolIdseq = protocolIdseq;
  }

  public String getLeadOrg() {
    return leadOrg;
  }

  public void setLeadOrg(String leadOrg) {
    this.leadOrg = leadOrg;
  }

  public String getType() {
    return protocolType;
  }

  public void setType(String protocolType) {
    this.protocolType = protocolType;
  }

  public String getPhase() {
    return protocolPhase;
  }

  public void setPhase(String protocolPhase) {
    this.protocolPhase = protocolPhase;
  }

  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  /**
   * This equals method only compares the Idseq to define equals
   * @param obj
   * @return 
   */  
 public boolean equals(Object obj)
 {
   if(obj == null)
    return false;
   if(!(obj instanceof Protocol))
    return false;
   Protocol protocol = (Protocol)obj;

  if(this.getProtoIdseq().equals(protocol.getProtoIdseq()))
      return true;
    else
      return false;
 }
  public String toString()
  {
    DebugStringBuffer sb = new DebugStringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR+"longName="+getLongName(),getLongName());
    sb.append(ATTR_SEPARATOR+"protocolIdseq="+getProtoIdseq(),getProtoIdseq());

    sb.append(OBJ_SEPARATOR_END);

    return sb.toString();
  }
}
