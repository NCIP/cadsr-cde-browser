package gov.nih.nci.ncicb.cadsr.resource;

public interface FormValidValue extends AdminComponent,Orderable {
  public String getValueIdseq();
  public void setValueIdseq(String idseq);

  public Question getQuestion();
  public void setQuestion(Question term);

  public String getVpIdseq();
  public void setVpIdseq(String vpIdseq);
  
  
  public Object clone() throws CloneNotSupportedException ;
}