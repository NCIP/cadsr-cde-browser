package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.resource.QuestionInstruction;

import java.util.Collection;
import java.util.List;


public interface QuestionInstructionDAO extends InstructionDAO {
  public int createInstruction(Instruction formInstr, String parentId)
    throws DMLException;
    
  public List getInstructions(String questionID);  
}
