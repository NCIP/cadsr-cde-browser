package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import java.util.Collection;


public interface InstructionDAO {
  /**
   * Deletes the specified instruction and all its associated components.
   *
   * @param <b>instructionId</b> Idseq of the instruction component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteInstruction(String instructionId) throws DMLException;

  /**
   * Changes the long name of an instruction.
   *
   * @param <b>instructionId</b> Idseq of the instruction component.
   * @param <b>newLongName</b> New long name of the instruction component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateInstruction(
    String instructionId,
    String newLongName) throws DMLException;

  public int updateDisplayOrder(
    String instructionId,
    int newDisplayOrder) throws DMLException;
    
  public Collection getInstructions(
    String componentId);
}
