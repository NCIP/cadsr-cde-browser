package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Instruction;


public class InstructionTransferObject extends AdminComponentTransferObject
  implements Instruction {
  public InstructionTransferObject() {
  }

  public int getDisplayOrder() {
    return 0;
  }

  public void setDisplayOrder(int order) {
  }

  public String getInstrIdseq() {
    return null;
  }

  public void setInstrIdseq(String idseq) {
  }
}
