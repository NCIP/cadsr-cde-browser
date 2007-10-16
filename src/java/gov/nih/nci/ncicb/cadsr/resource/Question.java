package gov.nih.nci.ncicb.cadsr.resource;

import java.util.List;

public interface Question extends FormElement,Orderable,Instructionable   {
  public String getQuesIdseq();
  public void setQuesIdseq(String idseq);

  public Module getModule();
  public void setModule (Module block);

  public Form getForm();
  public void setForm(Form crf);

  public List getValidValues();
  public void setValidValues(List values);

  public DataElement getDataElement();
  public void setDataElement(DataElement dataElement);

  public String getDefaultValue();
  public void setDefaultValue(String defaultValue);
  
  public FormValidValue getDefaultValidValue();
  public void setDefaultValidValue(FormValidValue vv);
  
  public List<QuestionRepitition> getQuestionRepititions();
  public void setQuestionRepitition(List<QuestionRepitition> repeats);  
  
  //added for eDCI
  public boolean isMandatory();
  public void setMandatory(boolean mandatory);  
  
  public Object clone() throws CloneNotSupportedException ;
  
}