package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.util.DebugStringBuffer;

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
  
  /**
   * Clones the object
   * Makes a deep copy of the Valivalues
   * sets Question to null;
   * @return 
   */
  public Object clone() throws CloneNotSupportedException {
     FormValidValue copy = null;
      copy = (FormValidValue)super.clone();
      copy.setQuestion(null);   
      return copy;
  }  
 
   /**
   * This equals method only compares the Idseq to define equals
   * @param obj
   * @return 
   */ 
 public boolean equals(Object obj)
 {
   if(obj == null)
    return false;
   if(!(obj instanceof FormValidValue))
    return false;
   FormValidValue vv = (FormValidValue)obj;

   if(getLongName().equalsIgnoreCase(vv.getLongName()))
      return true;
    else
      return false;
 
 }  
  public String toString()
  {
    DebugStringBuffer sb = new DebugStringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR+"valueIdseq="+getValueIdseq(),getValueIdseq()); 
    sb.append(ATTR_SEPARATOR+"displayOrder="+getDisplayOrder()); 
    sb.append(OBJ_SEPARATOR_END);  
    return sb.toString();  
  }
}
