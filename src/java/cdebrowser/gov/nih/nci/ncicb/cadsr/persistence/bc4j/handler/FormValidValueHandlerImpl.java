package gov.nih.nci.ncicb.cadsr.persistence.bc4j.handler;

import oracle.cle.persistence.Handler;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.crfbuilder.exception.FormBuilderException;
import gov.nih.nci.ncicb.cadsr.resource.handler.FormValidValueHandler;

public class FormValidValueHandlerImpl extends Handler 
                                       implements FormValidValueHandler  {

  public FormValidValueHandlerImpl() {
  }

  public Class getReferenceClass(){
    return FormValidValue.class;
  }

  public FormValidValue findValueByPrimaryKey(String pk, Object sessionId) 
                                             throws FormBuilderException {
    return null;
  }

  public int deleteValueByPrimaryKey(String pk, Object sessionId) 
                                    throws FormBuilderException {
    return 0;
  }

  public int createFormValidValue(FormValidValue value, Object sessionId) 
                                 throws FormBuilderException {
    return 0;
  }

  public int editFormValidValue(FormValidValue value, Object sessionId) 
                               throws FormBuilderException {
    return 0;
  }

  public static void main(String[] args) {
    FormValidValueHandlerImpl fh = new FormValidValueHandlerImpl();
  }
}