package gov.nih.nci.ncicb.cadsr.resource.handler;

import oracle.cle.persistence.HandlerDefinition;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.crfbuilder.exception.FormBuilderException;

public interface FormValidValueHandler extends HandlerDefinition  {

  public FormValidValue findValueByPrimaryKey (String pk,Object sessionId) 
                                           throws FormBuilderException;

  public int deleteValueByPrimaryKey (String pk,Object sessionId) 
                                         throws FormBuilderException;

  public int createFormValidValue(FormValidValue value, Object sessionId) 
                           throws FormBuilderException;

  public int editFormValidValue(FormValidValue value, Object sessionId) 
                         throws FormBuilderException;
  
}