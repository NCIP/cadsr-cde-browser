package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.QuestionInstruction;


public class QuestionInstructionTransferObject extends InstructionTransferObject
  implements QuestionInstruction {
  public QuestionInstructionTransferObject() {
  }

  public Question getQuestion() {
    return null;
  }

  public void setQuestion(Question term) {
  }
}
