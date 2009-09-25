package gov.nih.nci.ncicb.cadsr.common.util;

import gov.nih.nci.ncicb.cadsr.common.exception.JDBCConnectionException;
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;


public class DBConnectionUtils {
  private static Log log = LogFactory.getLog(DBConnectionUtils.class.getName());
  public static Connection getConnectionUsingUserInfo(ApplicationParameters ap)
    throws JDBCConnectionException {
    Connection conn = null;

    try {
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      conn =
        DriverManager.getConnection(
          ap.getDBUrl(), ap.getUser(), ap.getPassword());
    }
    catch (SQLException e) {
      throw new JDBCConnectionException("Unable to connect to the database", e);
    }

    return conn;
  }

  public static Connection getConnectionUsingDataSourceInfo(
    ApplicationParameters ap) throws JDBCConnectionException {
    Connection conn = null;
    InitialContext ic = null;

    try {
      String dataSourceName = ap.getDataSourceName();
      ic = new InitialContext();

      DataSource ds = (DataSource) ic.lookup(dataSourceName);
      conn = ds.getConnection();
      log.info(
        "Connected to the database successfully using datasource " +
        dataSourceName);
    }
    catch (NamingException ne) {
      log.error("Failed to lookup JDBC datasource", ne);
      throw new JDBCConnectionException("Failed to lookup JDBC datasource", ne);
    }
    catch (Exception e) {
      log.error("Exception occurred", e);
      throw new JDBCConnectionException("Unknown exception", e);
    }

    return conn;
  }

  public static Connection getConnectionUsingDataSourceInfo(
    String dataSourceName) throws JDBCConnectionException {
    Connection conn = null;
    InitialContext ic = null;

    try {
      ic = new InitialContext();

      DataSource ds = (DataSource) ic.lookup(dataSourceName);
      conn = ds.getConnection();
      log.info(
        "Connected to the database successfully using datasource " +
        dataSourceName);
    }
    catch (NamingException ne) {
      log.error("Failed to lookup JDBC datasource", ne);
      throw new JDBCConnectionException("Failed to lookup JDBC datasource", ne);
    }
    catch (Exception e) {
      log.error("Exception occurred in getConnectionUsingDataSourceInfo", e);
      throw new JDBCConnectionException("Unknown exception", e);
    }

    return conn;
  }

  public static Connection getConnectionUsingJDBC(
    String uname,
    String pwd) {
    Connection conn = null;
    boolean connectStatus = false;
    String dbURL = "jdbc:oracle:thin:@cbiodb2-d.nci.nih.gov:1521:CBDEV";
    uname = "sbrext";
    pwd = "jjuser";

    try {
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      conn = DriverManager.getConnection(dbURL, uname, pwd);
      connectStatus = true;
    }
    catch (Exception e) {
      connectStatus = false;
      log.error("Exception occurred in getConnectionUsingJDBC", e);
    }

    //return connectStatus;
    return conn;
  }
}
