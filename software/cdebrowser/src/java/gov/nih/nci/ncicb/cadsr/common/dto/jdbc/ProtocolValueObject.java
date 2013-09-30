/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.common.dto.jdbc;
import java.sql.Date;
import gov.nih.nci.ncicb.cadsr.common.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.common.dto.base.AdminComponentTransferObject;

public class ProtocolValueObject extends AdminComponentTransferObject 
                                         implements Protocol  {
  protected String _protoIdseq;
  protected String _leadOrg;
  protected String _phase;
  protected String _type;
  protected Date _beginDate;
  protected Date _endDate;
  
  protected String protocolId = null;

  public ProtocolValueObject() {
    idseq = _protoIdseq;
  }

  public String getProtoIdseq() {
    return _protoIdseq;
  }

  public void setProtoIdseq(String sProtoIdseq) {
    this._protoIdseq = sProtoIdseq;
  }

  public String getLeadOrg() {
    return _leadOrg;
  }

  public void setLeadOrg(String sLeadOrg) {
    this._leadOrg = sLeadOrg;
  }

  public String getType() {
    return _type;
  }

  public void setType(String sType) {
    this._type = sType;
  }

  public String getPhase() {
    return _phase;
  }

  public void setPhase(String sPhase) {
    this._phase = sPhase;
  }

  public Date getBeginDate() {
    return _beginDate;
  }

  public void setBeginDate(Date dBeginDate) {
    this._beginDate = dBeginDate;
  }

  public Date getEndDate() {
    return _endDate;
  }

  public void setEndDate(Date dEndDate) {
    this._endDate = dEndDate;
  }
    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }

    public String getProtocolId() {
        return protocolId;
    }
}