package gov.nih.nci.ncicb.cadsr.util;

public class ConnectException extends Exception 
{
  private String userMessage = null;
  
  public ConnectException(String msg)
  {
    userMessage = msg;
  }

  public String getUserMessage()
  {
    return userMessage;
  }
}