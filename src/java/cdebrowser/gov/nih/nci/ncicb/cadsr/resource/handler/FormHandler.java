package gov.nih.nci.ncicb.cadsr.resource.handler;

import oracle.cle.persistence.HandlerDefinition;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.crfbuilder.exception.FormBuilderException;

public interface FormHandler extends HandlerDefinition  {

  public Form findFormByPrimaryKey (String pk,Object sessionId) 
                                   throws FormBuilderException;

  public int deleteFormByPrimaryKey (String pk,Object sessionId) 
                                   throws FormBuilderException;

  public int createForm(Form crf, Object sessionId) throws FormBuilderException;

  public int editForm(Form crf, Object sessionId) throws FormBuilderException;

  public String copyForm(Form sourceForm,
                         Form targetForm,
                         Object sessionId) throws FormBuilderException;

  public int addModules(Module [] blocks)throws FormBuilderException;;
}