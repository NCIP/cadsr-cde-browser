package gov.nih.nci.ncicb.cadsr.resource;

import java.util.List;

public interface Module extends AdminComponent {
  public String getModuleIdseq();
  public void setModuleIdseq(String idseq);

  public Form getForm();
  public void setForm(Form crf);

  public List getQuestions();
  public void setQuestions(List terms);

  public int getDisplayOrder();
  public void setDisplayOrder(int dispOrder);
  public Object clone() throws CloneNotSupportedException ; 
}