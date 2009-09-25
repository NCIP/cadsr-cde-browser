package gov.nih.nci.ncicb.cadsr.common.util;

import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;
import javax.sql.PooledConnection;

public class ConnectionHelper  {
  private static Log log = LogFactory.getLog(ConnectionHelper.class.getName());
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
      log.info
      ("Connected to the database successfully using datasource "+dataSourceName);
    }
    catch (NamingException ne) {
      log.error("Failed to lookup JDBC datasource", ne);
    }
    catch (Exception e) {
		log.error("Exception in getConnection()", e);
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
    log.error("Exception in ConnectionHelper.closeConnection()", ex);
  } 
  finally {
  }
  
  }
}