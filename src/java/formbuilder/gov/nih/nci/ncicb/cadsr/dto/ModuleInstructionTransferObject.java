package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.ModuleInstruction;


public class ModuleInstructionTransferObject extends InstructionTransferObject
  implements ModuleInstruction {
  public ModuleInstructionTransferObject() {
  }

  public Module getModule() {
    return null;
  }

  public void setModule(Module mod) {
  }
}
