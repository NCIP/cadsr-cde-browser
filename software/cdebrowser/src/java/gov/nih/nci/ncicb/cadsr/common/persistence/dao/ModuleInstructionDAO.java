/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

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
