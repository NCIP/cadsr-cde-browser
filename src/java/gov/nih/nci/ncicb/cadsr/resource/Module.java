package gov.nih.nci.ncicb.cadsr.resource;

import java.util.List;

public interface Module extends FormElement,Orderable,Instructionable  {
  public String getModuleIdseq();
  public void setModuleIdseq(String idseq);

  public Form getForm();
  public void setForm(Form crf);

  public List getQuestions();
  public void setQuestions(List terms);

  public Object clone() throws CloneNotSupportedException ; 
}