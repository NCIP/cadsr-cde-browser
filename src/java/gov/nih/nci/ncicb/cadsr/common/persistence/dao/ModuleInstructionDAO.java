package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.resource.Instruction;
import java.util.Collection;
import java.util.List;


public interface ModuleInstructionDAO extends InstructionDAO {
  /**
   * Creates a new module instruction component (just the header info).
   *
   * @param <b>moduleInstr</b> ModuleInstruction object
   *
   * @return <b>String</b> Primary Key of the new Module Instruction
   *
   * @throws <b>DMLException</b>
   */
  public int createInstruction(Instruction moduleInstr, String parentId)
    throws DMLException;

  public List getInstructions(String moduleID);
}
