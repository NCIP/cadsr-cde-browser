package gov.nih.nci.ncicb.cadsr.common.resource;

import java.util.List;

public interface Module extends FormElement,Orderable,Instructionable  {
  public String getModuleIdseq();
  public void setModuleIdseq(String idseq);

  public Form getForm();
  public void setForm(Form crf);

  public List<Question> getQuestions();
  public void setQuestions(List<Question> terms);

  public Object clone() throws CloneNotSupportedException ;

    public void setNumberOfRepeats(int repeats);

    public int getNumberOfRepeats();
}