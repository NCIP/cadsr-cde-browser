package gov.nih.nci.ncicb.cadsr.dto.bc4j;

import gov.nih.nci.ncicb.cadsr.dto.base.AdminComponentTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.ModulesViewRowImpl;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;

import java.io.Serializable;

import java.util.List;


public class BC4JModuleTransferObject extends AdminComponentTransferObject
  implements Module, Serializable {

  private String modIdseq;
  private List terms;
  private ModulesViewRowImpl moduleRow;
  private int displayOrder;

  public BC4JModuleTransferObject() {
    super();
    idseq = modIdseq;
  }
  public BC4JModuleTransferObject(ModulesViewRowImpl moduleRow) {
    super();
    this.moduleRow = moduleRow;
    modIdseq = moduleRow.getQcIdseq();
    idseq = modIdseq;
    preferredDefinition = moduleRow.getPreferredDefinition();
    preferredName = moduleRow.getPreferredName();
    longName = checkForNull(moduleRow.getLongName());

    //createdBy = formRow.getCreatedBy();
    //createdDate = (Date)dataElementsViewRowImpl.getDateCreated().getData();
    //modifiedBy = formRow.getModifiedBy();
    //modifiedDate = (Date)(dataElementsViewRowImpl.getDateModified().getData());
    aslName = moduleRow.getAslName();
    version = new Float(moduleRow.getVersion().floatValue());
    deletedInd = checkForNull(moduleRow.getDeletedInd());
    latestVerInd = checkForNull(moduleRow.getLatestVersionInd());
    displayOrder = moduleRow.getDisplayOrder().intValue();

    terms = moduleRow.getQuestionTranferObjects();
    context = moduleRow.getContextTransferObject();
  }

  public String getModuleIdseq() {
    return modIdseq;
  }

  public void setModuleIdseq(String idseq) {
    modIdseq = idseq;
  }

  public Form getForm() {
    return null;
  }

  public void setForm(Form crf) {
  }

  public List getQuestions() {
    return terms;
  }

  public void setQuestions(List terms) {
  }

  public int getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(int dispOrder) {
    this.displayOrder = dispOrder;
  }
}
