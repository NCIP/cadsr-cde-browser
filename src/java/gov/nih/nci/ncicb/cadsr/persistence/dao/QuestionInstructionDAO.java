package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.QuestionInstruction;

import java.util.Collection;


public interface QuestionInstructionDAO extends InstructionDAO {
  public int createQuestionInstructionComponent(QuestionInstruction quesInstr)
    throws DMLException;

  public Collection getQuestionInstructions(String questionId)
    throws DMLException;
}
