package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.ModuleInstruction;

import java.util.Collection;


public interface ModuleInstructionDAO extends InstructionDAO {
  public int createModuleInstructionComponent(ModuleInstruction moduleInstr)
    throws DMLException;

  public Collection getModuleInstructions(String moduleId) throws DMLException;
}
