package gov.nih.nci.ncicb.cadsr.crfbuilder.exception;

import gov.nih.nci.ncicb.cadsr.exception.NestedCheckedException;

public class FormBuilderException extends NestedCheckedException {

  public FormBuilderException(String msg) {
    super(msg);
  }

  public FormBuilderException(String msg, Exception ex) {
    super(msg,ex);
  }
}