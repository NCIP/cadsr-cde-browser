package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormInstruction;


public class FormInstructionTransferObject extends InstructionTransferObject
  implements FormInstruction {

  private Form form;
  
  public FormInstructionTransferObject() {
  }

  public Form getForm() {
    return form;
  }

  public void setForm(Form frm) {
    this.form = frm;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(OBJ_SEPARATOR_END);
    
    Form form = getForm();

    if (form != null) {
      sb.append(ATTR_SEPARATOR + "Form=" + form);
    }
    else {
      sb.append(ATTR_SEPARATOR + "Form=" + null);
    }

    sb.append(OBJ_SEPARATOR_END);

    return sb.toString();
  }
  

}
