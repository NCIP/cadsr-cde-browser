package gov.nih.nci.ncicb.cadsr.resource;
import java.sql.Date;

public interface Protocol extends AdminComponent {
  public String getProtoIdseq();
  public void setProtoIdseq(String sProtoIdseq);

  public String getLeadOrg();
  public void setLeadOrg(String sLeadOrg);

  public String getType();
  public void setType(String sType);

  public String getPhase();
  public void setPhase(String sPhase);

  public Date getBeginDate();
  public void setBeginDate(Date dBeginDate);

  public Date getEndDate();
  public void setEndDate(Date dEndDate);
  
  public Object clone() throws CloneNotSupportedException ;
}