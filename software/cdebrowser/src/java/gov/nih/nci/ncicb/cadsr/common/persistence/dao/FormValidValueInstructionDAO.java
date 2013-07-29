/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.resource.Instruction;

import java.util.Collection;
import java.util.List;


public interface FormValidValueInstructionDAO extends InstructionDAO {


  /**
   * Creates a new form valid value instruction component (just the header info).
   *
   * @param <b>valueInstr</b> FormValidValueInstruction object
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */

  public int createInstruction(Instruction vvInstr, String parentId)
    throws DMLException;

  public List getInstructions(String vvID)
    throws DMLException;
   
}
