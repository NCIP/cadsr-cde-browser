package gov.nih.nci.ncicb.cadsr.resource;

public interface Instruction  extends AdminComponent {
  public int getDisplayOrder();
  public void setDisplayOrder(int order);

  public String getInstrIdseq();
  public void setInstrIdseq(String idseq);
}