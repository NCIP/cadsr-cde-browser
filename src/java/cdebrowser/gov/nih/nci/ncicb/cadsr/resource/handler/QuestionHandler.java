package gov.nih.nci.ncicb.cadsr.resource.handler;

import oracle.cle.persistence.HandlerDefinition;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.crfbuilder.exception.FormBuilderException;

public interface QuestionHandler extends HandlerDefinition  {

  public Question findQuestionByPrimaryKey (String pk,Object sessionId) 
                                           throws FormBuilderException;

  public int deleteQuestionByPrimaryKey (String pk,Object sessionId) 
                                         throws FormBuilderException;

  public int createQuestion(Question term, Object sessionId) 
                           throws FormBuilderException;

  public int editQuestion(Question term, Object sessionId) 
                         throws FormBuilderException;

  public int addValidValues(FormValidValue [] values)
                            throws FormBuilderException;
}