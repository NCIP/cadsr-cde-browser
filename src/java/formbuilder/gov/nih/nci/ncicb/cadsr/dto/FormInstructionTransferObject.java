package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormInstruction;


public class FormInstructionTransferObject extends InstructionTransferObject
  implements FormInstruction {
  public FormInstructionTransferObject() {
  }

  public Form getForm() {
    return null;
  }

  public void setForm(Form frm) {
  }
}
