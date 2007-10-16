package gov.nih.nci.ncicb.cadsr.resource;

public interface Instruction  extends AdminComponent {
  
  public int getDisplayOrder();
  public void setDisplayOrder(int order);

  public String getIdseq();
  public void setIdseq(String idseq);
  
  public Object clone() throws CloneNotSupportedException ;
  
}