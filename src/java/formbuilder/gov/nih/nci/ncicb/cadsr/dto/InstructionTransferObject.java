package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Instruction;


public class InstructionTransferObject extends AdminComponentTransferObject
  implements Instruction {

  private int displayOrder;
  private String instrIdseq = null;
  
  public InstructionTransferObject() {
  }

  public int getDisplayOrder() {
    return displayOrder;
  }

  public void setDisplayOrder(int order) {
    this.displayOrder = order;
  }

  public String getInstrIdseq() {
    return instrIdseq;
  }

  public void setInstrIdseq(String idseq) {
    this.instrIdseq = idseq;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR + "displayOrder=" + getDisplayOrder());
    sb.append(ATTR_SEPARATOR + "instrIdseq=" + getInstrIdseq());
    sb.append(OBJ_SEPARATOR_END);

    return sb.toString();
  }
  
}
