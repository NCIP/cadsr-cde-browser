package gov.nih.nci.ncicb.cadsr.resource;

public interface ModuleInstruction extends Instruction {
  public Module getModule();
  public void setModule(Module mod);
}