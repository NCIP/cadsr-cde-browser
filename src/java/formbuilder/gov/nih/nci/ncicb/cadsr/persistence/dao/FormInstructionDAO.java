package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.FormInstruction;

import java.util.Collection;


public interface FormInstructionDAO extends InstructionDAO {
  public int createFormInstructionComponent(FormInstruction formInstr)
    throws DMLException;

  public Collection getFormInstructions(String formId) throws DMLException;
}
