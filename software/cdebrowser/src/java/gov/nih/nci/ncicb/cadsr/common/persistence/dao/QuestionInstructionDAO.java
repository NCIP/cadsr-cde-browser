package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.common.resource.QuestionInstruction;

import java.util.Collection;
import java.util.List;


public interface QuestionInstructionDAO extends InstructionDAO {
  public int createInstruction(Instruction formInstr, String parentId)
    throws DMLException;
    
  public List getInstructions(String questionID);  
}
