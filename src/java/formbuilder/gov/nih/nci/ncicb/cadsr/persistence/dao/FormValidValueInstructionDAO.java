package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValueInstruction;

import java.util.Collection;


public interface FormValidValueInstructionDAO extends InstructionDAO {
  public int createValueInstructionComponent(
    FormValidValueInstruction valueInstr) throws DMLException;

  public Collection getValueInstructions(String formValidValueId)
    throws DMLException;
}
