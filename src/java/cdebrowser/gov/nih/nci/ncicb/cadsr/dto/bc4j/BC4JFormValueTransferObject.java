package gov.nih.nci.ncicb.cadsr.dto.bc4j;

import gov.nih.nci.ncicb.cadsr.dto.base.AdminComponentTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.FormValidValuesViewRowImpl;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;

import java.io.Serializable;

import java.util.List;


public class BC4JFormValueTransferObject extends AdminComponentTransferObject
  implements FormValidValue {
  private String vvIdseq;
  private int displayOrder;
  private String vpIdseq;

  public BC4JFormValueTransferObject(FormValidValuesViewRowImpl vvRow) {
    vvIdseq = vvRow.getQcIdseq();
    idseq = vvIdseq;
    preferredDefinition = vvRow.getPreferredDefinition();
    preferredName = vvRow.getPreferredName();
    longName = checkForNull(vvRow.getLongName());

    //createdBy = formRow.getCreatedBy();
    //createdDate = (Date)dataElementsViewRowImpl.getDateCreated().getData();
    //modifiedBy = formRow.getModifiedBy();
    //modifiedDate = (Date)(dataElementsViewRowImpl.getDateModified().getData());
    aslName = vvRow.getAslName();
    version = new Float(vvRow.getVersion().floatValue());
    vpIdseq = vvRow.getVpIdseq();

    //deletedInd = checkForNull(vvRow.getDeletedInd());
    //latestVerInd = checkForNull(vvRow.getLatestVersionInd());
    displayOrder = vvRow.getDisplayOrder().intValue();
    context = vvRow.getContextTransferObject();
  }

  public String getValueIdseq() {
    return vvIdseq;
  }

  public void setValueIdseq(String idseq) {
    vvIdseq = idseq;
  }

  public Question getQuestion() {
    return null;
  }

  public void setQuestion(Question term) {
  }

  public String getVpIdseq() {
    return vpIdseq;
  }

  public void setVpIdseq(String vpIdseq) {
  }

  public int getDisplayOrder() {
    return 0;
  }

  public void setDisplayOrder(int dispOrder) {
  }
  public String getShortMeaning(){
  //dummy implementation
    return null;
  }
  public void setShortMeaning(String shortMeaning){
  //dummy implementation
  }
}
