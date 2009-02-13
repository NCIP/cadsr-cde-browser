package gov.nih.nci.ncicb.cadsr.common.persistence.bc4j;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import java.util.List;
import java.sql.Date;
import gov.nih.nci.ncicb.cadsr.common.resource.QuestContent;
import gov.nih.nci.ncicb.cadsr.common.dto.base.AdminComponentTransferObject;

public class QuestContentValueObject extends AdminComponentTransferObject 
                                     implements QuestContent  {
  protected String _qcIdseq;
  protected String _qtlName;
  public QuestContentValueObject() {
    super();
    _qcIdseq = null;
    _qtlName = null;
  }

  public String getQcIdseq() {
    return _qcIdseq;
  }

  public void setQcIdseq(String qcIdseq) {
    this._qcIdseq = qcIdseq;
  }

  public String getQuestContentType() {
    return _qtlName;
  }

  public void setQuestContentType(String qtlName) {
    this._qtlName = qtlName;
  }

  
}