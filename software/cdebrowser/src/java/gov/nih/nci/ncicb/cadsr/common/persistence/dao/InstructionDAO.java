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
