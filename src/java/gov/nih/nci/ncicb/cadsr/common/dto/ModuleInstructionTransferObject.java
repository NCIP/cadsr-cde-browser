package gov.nih.nci.ncicb.cadsr.common.dto;

import gov.nih.nci.ncicb.cadsr.common.resource.Module;
import gov.nih.nci.ncicb.cadsr.common.resource.ModuleInstruction;


public class ModuleInstructionTransferObject extends InstructionTransferObject
  implements ModuleInstruction {

  private Module module = null;
  
  public ModuleInstructionTransferObject() {
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module mod) {
    this.module = mod;
  }
  
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(OBJ_SEPARATOR_END);
    
    Module module = getModule();

    if (module != null) {
      sb.append(ATTR_SEPARATOR + "Module=" + module);
    }
    else {
      sb.append(ATTR_SEPARATOR + "Module=" + null);
    }

    sb.append(OBJ_SEPARATOR_END);

    return sb.toString();
  }
  
}
