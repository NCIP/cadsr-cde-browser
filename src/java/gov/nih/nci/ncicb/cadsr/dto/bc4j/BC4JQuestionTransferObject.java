package gov.nih.nci.ncicb.cadsr.dto.bc4j;

import gov.nih.nci.ncicb.cadsr.dto.base.AdminComponentTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.QuestionsViewRowImpl;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import java.io.Serializable;

import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision: 1.1 $
 */
public class BC4JQuestionTransferObject extends QuestionTransferObject {
  
  /**
   * Creates a new BC4JQuestionTransferObject object.
   *
   * @param termRow DOCUMENT ME!
   */
  public BC4JQuestionTransferObject(QuestionsViewRowImpl termRow) {
    quesIdseq = termRow.getQcIdseq();
    idseq = quesIdseq;
    preferredDefinition = termRow.getPreferredDefinition();
    preferredName = termRow.getPreferredName();
    longName = StringUtils.replaceNull(termRow.getLongName());

    //createdBy = formRow.getCreatedBy();
    //createdDate = (Date)dataElementsViewRowImpl.getDateCreated().getData();
    //modifiedBy = formRow.getModifiedBy();
    //modifiedDate = (Date)(dataElementsViewRowImpl.getDateModified().getData());
    aslName = termRow.getAslName();
    version = new Float(termRow.getVersion().floatValue());

    //deletedInd = StringUtils.replaceNull(termRow.getDeletedInd());
    //latestVerInd = StringUtils.replaceNull(termRow.getLatestVersionInd());
    displayOrder = termRow.getDisplayOrder().intValue();

    validValues = termRow.getFormVVTranferObjects();
    context = termRow.getContextTransferObject();
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public String getQuesIdseq() {
    return quesIdseq;
  }

  /**
   * DOCUMENT ME!
   *
   * @param idseq DOCUMENT ME!
   */
  public void setQuesIdseq(String idseq) {
    quesIdseq = idseq;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public Module getModule() {
    return null;
  }

  /**
   * DOCUMENT ME!
   *
   * @param block DOCUMENT ME!
   */
  public void setModule(Module block) {
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public Form getForm() {
    return null;
  }

  /**
   * DOCUMENT ME!
   *
   * @param crf DOCUMENT ME!
   */
  public void setForm(Form crf) {
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public List getValidValues() {
    return validValues;
  }

  /**
   * DOCUMENT ME!
   *
   * @param values DOCUMENT ME!
   */
  public void setValidValues(List values) {
  }
}
