package gov.nih.nci.ncicb.cadsr.resource;

public interface FormValidValue extends AdminComponent {
  public String getValueIdseq();
  public void setValueIdseq(String idseq);

  public Question getQuestion();
  public void setQuestion(Question term);

  public String getVpIdseq();
  public void setVpIdseq(String vpIdseq);
  
  public int getDisplayOrder();
  public void setDisplayOrder(int dispOrder);
  
  public Object clone() throws CloneNotSupportedException ;
}