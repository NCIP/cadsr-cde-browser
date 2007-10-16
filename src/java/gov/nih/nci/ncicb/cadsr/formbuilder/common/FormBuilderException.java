package gov.nih.nci.ncicb.cadsr.formbuilder.common;

import gov.nih.nci.ncicb.cadsr.exception.NestedCheckedException;
import java.lang.reflect.InvocationTargetException;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
public class FormBuilderException extends NestedCheckedException {
  public FormBuilderException(String msg) {
    super(msg);
  }
 public FormBuilderException(InvocationTargetException exp)
 {
   super("Method",exp.getTargetException());
 }
  public FormBuilderException(DMLException exp)
 {
   super("Method",exp);
 }
  public FormBuilderException(
    String msg,
    Throwable cause) {
    super(msg, cause);
  }
  
}
