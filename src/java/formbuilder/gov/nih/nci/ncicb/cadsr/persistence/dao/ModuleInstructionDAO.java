package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.ModuleInstruction;

import java.util.Collection;


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
  public String createModuleInstructionComponent(ModuleInstruction moduleInstr)
    throws DMLException;

  public Collection getModuleInstructions(String moduleId) throws DMLException;
}
