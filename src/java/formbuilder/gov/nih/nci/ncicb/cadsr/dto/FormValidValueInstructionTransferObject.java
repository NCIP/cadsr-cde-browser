package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValueInstruction;


public class FormValidValueInstructionTransferObject
  extends InstructionTransferObject implements FormValidValueInstruction {
  public FormValidValueInstructionTransferObject() {
  }

  public FormValidValue getFormValidValue() {
    return null;
  }

  public void setFormValidValue(FormValidValue value) {
  }
}
