package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Question;

public class FormValidValueTransferObject extends AdminComponentTransferObject
  implements FormValidValue {
  
  private String valueIdseq;
  private Question term;
  private String vpIdseq;
  private int dispOrder;

  public FormValidValueTransferObject() {
  }

  public String getValueIdseq() 
  {
    return valueIdseq;
  }
  
  public void setValueIdseq(String idseq) 
  {
    this.valueIdseq = idseq;
  }

  public Question getQuestion() 
  {
    return term;
  }
  
  public void setQuestion(Question term) 
  {
    this.term = term;
  }

  public String getVpIdseq() 
  {
    return vpIdseq;
  }
  
  public void setVpIdseq(String vpIdseq) 
  {
    this.vpIdseq = vpIdseq;
  }

  public int getDisplayOrder() {
    return dispOrder;
  }

  public void setDisplayOrder(int dispOrder) {
    this.dispOrder = dispOrder;
  }
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR+"valueIdseq="+getValueIdseq()); 
    sb.append(ATTR_SEPARATOR+"displayOrder="+getDisplayOrder()); 
    sb.append(OBJ_SEPARATOR_END);  
    return sb.toString();  
  }
}
