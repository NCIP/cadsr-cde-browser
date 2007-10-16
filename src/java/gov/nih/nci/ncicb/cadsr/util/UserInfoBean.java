package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

import healthtoolkit.beans.dbservice.DBBroker;

import java.sql.SQLException;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

/**
 * A Bean class.
 * <P>
 * @author Oracle Corporation
 */
public class UserInfoBean {
  private static Log log = LogFactory.getLog(UserInfoBean.class.getName());

  private String username = null;
  private boolean authenticated = false;

  /**
   * Constructor
   */
  public UserInfoBean() {
  }

  public void authenticate(HttpServletRequest request) {
    username = (String)SessionHelper.getValue(request,"username");
    if (username != null)
    {
      this.authenticated = true;
    }
  }

  public boolean userIsAuthenticated() {
    return this.authenticated;
  }

  public String getUsername() {
    return this.username;
  }

  public String getUsersOrg(DBBroker dBBroker) throws SQLException {

    Vector rsVector;
    String result;

    String sqlStmt =
      "select ogn.name " +
      "from sbr.organizations ogn,sbr.user_accounts uac " +
      "where ogn.org_idseq = uac.org_idseq " +
      "and ua_name = '" + username.toUpperCase() + "' " ;
    log.info(sqlStmt);

    rsVector = dBBroker.retrieveRecordDB(sqlStmt);
    SQLException e = dBBroker.getSQLException();
    if (e != null) {
      throw e;
    }

    if (rsVector.size() == 0){
      result = "";
    }
    else{
      result = (String)rsVector.elementAt(0);
    }

    return result;
  }

  
 
}
