package gov.nih.nci.ncicb.cadsr.util;

import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.naming.*;

public class ConnectionHelper  {
  private String dataSourceName = null;
  private Connection conn = null;
  private PooledConnection pooledConn = null;
  public ConnectionHelper(String dataSourceName) {
    this.dataSourceName = dataSourceName;
  }
  public Connection getConnection() {
  InitialContext ic =null;
    try {
      ic = new InitialContext();
      DataSource ds = (DataSource)ic.lookup(dataSourceName);
      conn = ds.getConnection();
      //conn.setAutoCommit(true);
      System.out.println
      ("Connected to the database successfully using datasource "+dataSourceName);
    }
    catch (NamingException ne) {
      System.out.println("Failed to lookup JDBC datasource");
      ne.printStackTrace();
    }
    catch (Exception e) {
			System.out.println("  Exception in ConnectionHelper.getConnection()");
      e.printStackTrace();
    }
    return conn;
  }

  public void closeConnection() {
  try {
    if (conn != null) {
      conn.close();
    }
  } 
  catch (Exception ex) {
    System.out.println("  Exception in ConnectionHelper.closeConnection()");
    ex.printStackTrace();
  } 
  finally {
  }
  
  }

  public static void main(String[] args) {
    ConnectionHelper connectionHelper = new ConnectionHelper("jdbc/SBREXT_CBPRODCoreDS");
    Connection c = connectionHelper.getConnection();
  }
}