package gov.nih.nci.ncicb.cadsr.resource.handler;

import oracle.cle.persistence.HandlerDefinition;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.crfbuilder.exception.FormBuilderException;

public interface ModuleHandler extends HandlerDefinition  {

  public Module findModuleByPrimaryKey (String pk,Object sessionId) 
                                      throws FormBuilderException;

  public int deleteModuleByPrimaryKey (String pk,Object sessionId) 
                                       throws FormBuilderException;

  public int createModule(Module block, Object sessionId) 
                         throws FormBuilderException;

  public int editModule(Module block, Object sessionId) 
                       throws FormBuilderException;

  public int addQuestions(Question [] terms)throws FormBuilderException;;
}