package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValueInstruction;


public class FormValidValueInstructionTransferObject
  extends InstructionTransferObject implements FormValidValueInstruction {

  private FormValidValue formValidValue = null;
  
  public FormValidValueInstructionTransferObject() {
  }

  public FormValidValue getFormValidValue() {
    return formValidValue;
  }

  public void setFormValidValue(FormValidValue value) {
    this.formValidValue = value;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(OBJ_SEPARATOR_END);
    
    FormValidValue formValidValue = getFormValidValue();

    if (formValidValue != null) {
      sb.append(ATTR_SEPARATOR + "FormValidValue=" + formValidValue);
    }
    else {
      sb.append(ATTR_SEPARATOR + "FormValidValue=" + null);
    }

    sb.append(OBJ_SEPARATOR_END);

    return sb.toString();
  }
  

}
