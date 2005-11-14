package gov.nih.nci.ncicb.cadsr.resource;

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
}