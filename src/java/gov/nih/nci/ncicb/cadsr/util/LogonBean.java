package gov.nih.nci.ncicb.cadsr.util;

import java.sql.*;
import java.util.Vector;
import javax.servlet.http.*;
import healthtoolkit.beans.dbservice.*;
import healthtoolkit.utils.*;

/**
 *
 * @author Alan M. Levine, Oracle Corporation
 */
public class LogonBean extends Object {

  private String targetJsp = null;
  private String logonMsg = "";
  private String username = "";
  private String password = "";
  private DBBroker dBBroker = null;

  /**
   * Constructor
   */
  public LogonBean(
    HttpServletRequest request,
    HttpServletResponse response,
    DBBroker dBBroker,
    String successJsp) throws SQLException  {

    this.dBBroker = dBBroker;
    username = request.getParameter("username");
    password = request.getParameter("password");

    if (username == null) {
      username = "";
    }
    else {
      username = username.toUpperCase();
    }
    if (password == null) {
      password = "";
    }

    if (username.equals("") || password.equals("")) {
      this.targetJsp = "logon.jsp";
      String first = request.getParameter("first");
      if (first != null)
      {
        logonMsg = "";
        SessionHelper.cleanSession(request);
      }
      else
      {
        logonMsg = "<FONT COLOR=\"RED\">Please enter a valid username and password.</FONT>";
      }
      SessionHelper.putValue(request,"lb",this);
    }
    else if (this.validate(username, password, request, response)) {
      this.targetJsp = successJsp;
    }
    else {
      targetJsp = "logon.jsp";
      SessionHelper.putValue(request,"lb",this);
    }

    
  }

  private boolean validate (String uname, String pwd, HttpServletRequest request, HttpServletResponse response) throws SQLException {

    boolean validFlag = false;
    //Validating the user by establishing a connection to the database
    DBBroker myDBBroker = new DBBroker();
    //Uncomment next line to access CBDEV
    //boolean connectionStatus = myDBBroker.connectDB("jdbc:oracle:thin:@cbiodb2-d.nci.nih.gov:1521:CBDEV",username,password);
    
    boolean connectionStatus = myDBBroker.connectDB("jdbc:oracle:thin:@cbiodb2-d.nci.nih.gov:1521:CBTEST",username,password);
    Connection myConn = myDBBroker.getConnection();
    System.out.println("Connection Status " + connectionStatus);

    if (connectionStatus){
      validFlag = true;
      SessionHelper.putValue(request,"username",username);
      SessionHelper.putValue(request,"password",password);
      try{
        myConn.close();
        System.out.println("Connection closed Successfully");
        validFlag = true;
      }
      catch (SQLException sqle) {
        System.out.println("Exception in closing DB Connection");
        this.logonMsg = "Internal error occured while communicating with the database. Please try again";
      }
      
    }
    else {
      logonMsg = "<FONT COLOR=\"RED\">Invalid username or password, please try again.</FONT>";
    }
    return validFlag;
  }

  public String getJsp() {
    return targetJsp;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getLogonMsg() {
    return logonMsg;
  }
  public void setLogonMsg(String s) {
    logonMsg = s;
  }

}
