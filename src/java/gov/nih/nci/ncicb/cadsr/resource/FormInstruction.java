package gov.nih.nci.ncicb.cadsr.resource;

public interface FormInstruction extends Instruction {
  public Form getForm();
  public void setForm(Form frm);
}