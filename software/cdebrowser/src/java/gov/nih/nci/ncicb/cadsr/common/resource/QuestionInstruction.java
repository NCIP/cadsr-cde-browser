package gov.nih.nci.ncicb.cadsr.common.resource;

public interface QuestionInstruction extends Instruction  {
  public Question getQuestion();
  public void setQuestion(Question term);
}