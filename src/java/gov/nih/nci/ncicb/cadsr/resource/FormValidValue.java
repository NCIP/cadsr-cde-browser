package gov.nih.nci.ncicb.cadsr.resource;

import java.util.List;

public interface FormValidValue extends FormElement,Orderable,Instructionable {
  public String getValueIdseq();
  public void setValueIdseq(String idseq);

  public Question getQuestion();
  public void setQuestion(Question term);

  public String getVpIdseq();
  public void setVpIdseq(String vpIdseq);


  public Object clone() throws CloneNotSupportedException ;

  public String getShortMeaning();
  public void setShortMeaning(String shortMeaning);

  //added for making value meaning an administered components;
  public String getFormValueMeaningText();
  public void setFormValueMeaningText(String vmText);
  public String getFormValueMeaningDesc();
  public void setFormValueMeaningDesc(String desc);
  
   public void setValueMeaning(ValueMeaning valueMeaning);
   public ValueMeaning getValueMeaning();
}