package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.resource.Instruction;
import java.util.Collection;
import java.util.List;


public interface InstructionDAO {

  public int createInstruction(Instruction instruction, String parentId,String type, String rlType)
    throws DMLException;

  public int createInstruction(Instruction instruction, String parentId)
    throws DMLException;    
    
  public int deleteInstruction(String instructionId) throws DMLException;

  public int updateInstruction(Instruction newInstruction) throws DMLException;
    
  public List getInstructions(String componentId, String type);
  
  public List getInstructions(String componentId);
  
}
