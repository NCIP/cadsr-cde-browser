package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;

import java.sql.Date;

import java.util.List;


public class QuestionTransferObject extends AdminComponentTransferObject
  implements Question {
  protected Form crf;
  protected Module module;
  protected List validValues;
  protected String quesIdseq;
  protected int displayOrder;
  protected String deIdseq;
  protected DataElement dataElement;

  public QuestionTransferObject() {
    idseq = quesIdseq;
  }

  public String getQuesIdseq() {
    return quesIdseq;
  }

  public void setQuesIdseq(String idseq) {
    this.quesIdseq = idseq;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public Form getForm() {
    return crf;
  }

  public void setForm(Form crf) {
    this.crf = crf;
  }

  public List getValidValues() {
    return validValues;
  }

  public void setValidValues(List validValues) {
    this.validValues = validValues;
  }

  public int getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(int dispOrder) {
    this.displayOrder = dispOrder;
  }

  public DataElement getDataElement() {
    return dataElement;
  }

  public void setDataElement(DataElement dataElement) {
    this.dataElement = dataElement;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR + "quesIdseq=" + getQuesIdseq());
    sb.append(ATTR_SEPARATOR + "displayOrder=" + getDisplayOrder());

    List validValues = getValidValues();

    if (validValues != null) {
      sb.append(ATTR_SEPARATOR + "ValidValues=" + validValues);
    }
    else {
      sb.append(ATTR_SEPARATOR + "ValidValues=" + null);
    }

    sb.append(OBJ_SEPARATOR_END);

    DataElement dataElement = getDataElement();

    if (dataElement != null) {
      sb.append(ATTR_SEPARATOR + "DataElement=" + dataElement);
    }
    else {
      sb.append(ATTR_SEPARATOR + "DataElement=" + null);
    }

    sb.append(OBJ_SEPARATOR_END);

    return sb.toString();
  }
}
