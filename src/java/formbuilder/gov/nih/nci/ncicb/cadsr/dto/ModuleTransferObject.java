package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;

import java.sql.Date;

import java.util.List;


public class ModuleTransferObject extends AdminComponentTransferObject
  implements Module {
  private Form crf;
  private List terms;
  private String moduleIdseq;
  private int dispOrder;

  public ModuleTransferObject() {
    idseq = moduleIdseq;
  }

  public String getModuleIdseq() {
    return moduleIdseq;
  }

  public void setModuleIdseq(String idseq) {
    this.moduleIdseq = idseq;
  }

  public Form getForm() {
    return crf;
  }

  public void setForm(Form crf) {
    this.crf = crf;
  }

  public List getQuestions() {
    return terms;
  }

  public void setQuestions(List terms) {
    this.terms = terms;
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
    sb.append(ATTR_SEPARATOR+"moduleIdseq="+getModuleIdseq()); 
    sb.append(ATTR_SEPARATOR+"displayOrder="+getDisplayOrder()); 
    List questions = getQuestions();
    if(questions!=null) 
    {      
      sb.append(ATTR_SEPARATOR+"Questions="+questions);
    } 
    else
    {
      sb.append(ATTR_SEPARATOR+"Questions="+null);
    }    
    sb.append(OBJ_SEPARATOR_END);  
    return sb.toString();
  }  
}
