package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

import healthtoolkit.beans.dbservice.*;

import java.sql.*;

import javax.servlet.http.*;

/**
 *
 * @author Alan M. Levine, Oracle Corporation
 */
public class LogonBean extends Object {
  private static Log log = LogFactory.getLog(LogonBean.class.getName());

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
    log.info("Connection Status " + connectionStatus);

    if (connectionStatus){
      validFlag = true;
      SessionHelper.putValue(request,"username",username);
      SessionHelper.putValue(request,"password",password);
      try{
        myConn.close();
        log.info("Connection closed Successfully");
        validFlag = true;
      }
      catch (SQLException sqle) {
        log.error("Exception in closing DB Connection", sqle);
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
