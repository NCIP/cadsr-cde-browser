package gov.nih.nci.ncicb.cadsr.common.resource;

public interface QuestContent extends AdminComponent  {
  public String getQcIdseq();
  public void setQcIdseq(String qcIdseq);

  public String getQuestContentType();
  public void setQuestContentType(String qtlName);
}