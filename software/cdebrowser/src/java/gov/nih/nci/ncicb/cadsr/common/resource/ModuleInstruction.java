package gov.nih.nci.ncicb.cadsr.common.resource;

public interface ModuleInstruction extends Instruction {
  public Module getModule();
  public void setModule(Module mod);
}