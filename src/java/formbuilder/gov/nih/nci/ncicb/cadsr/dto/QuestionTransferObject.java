package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;

import java.sql.Date;

import java.util.List;


public class QuestionTransferObject extends AdminComponentTransferObject
  implements Question {
  private Form crf;
  private Module module;
  private List validValues;
  private String quesIdseq;
  private int dispOrder;

  public QuestionTransferObject() {
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
    return dispOrder;
  }

  public void setDisplayOrder(int dispOrder) {
    this.dispOrder = dispOrder;
  }
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR+"quesIdseq="+getQuesIdseq()); 
    sb.append(ATTR_SEPARATOR+"displayOrder="+getDisplayOrder()); 
    List validValues = getValidValues();
    if(validValues!=null) 
    {      
      sb.append(ATTR_SEPARATOR+"ValidValues="+validValues);
    } 
    else
    {
      sb.append(ATTR_SEPARATOR+"ValidValues="+null);
    }    
    sb.append(OBJ_SEPARATOR_END);  
    return sb.toString();
  }    
}
