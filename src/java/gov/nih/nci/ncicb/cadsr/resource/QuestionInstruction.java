package gov.nih.nci.ncicb.cadsr.resource;

public interface QuestionInstruction extends Instruction  {
  public Question getQuestion();
  public void setQuestion(Question term);
}