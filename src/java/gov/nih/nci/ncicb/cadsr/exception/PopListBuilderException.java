package gov.nih.nci.ncicb.cadsr.exception;

public class PopListBuilderException extends NestedCheckedException  {

  public PopListBuilderException(String msg) {
    super(msg);
  }

  public PopListBuilderException(String msg, Exception ex) {
    super(msg,ex);
  }
}