package gov.nih.nci.ncicb.cadsr.common.cdebrowser.userexception;
import java.io.*;

public class DocumentNotFoundException extends Exception implements Serializable {
  String msg = "";
  public DocumentNotFoundException() {
  }
  public DocumentNotFoundException(String message) {
    msg = message;
  }
  public String getMessage(){
    return msg;
  }
}