package gov.nih.nci.ncicb.cadsr.util;

import gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc.util.DataSourceUtil;
import gov.nih.nci.ncicb.cadsr.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.util.logging.LogFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Vector;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class DBUtil  {
  private static Log log = LogFactory.getLog(DBUtil.class.getName());

  private static final int INSERT_ERROR = -1;
  private static final int UPDATE_ERROR = -1;
  private Connection conn;
  private boolean isConnected = false;
		
  public DBUtil() {
    conn = null;
  }
  
/**
 *  This method returns a Connection obtained from the container using the
 *  datasource name specified as a parameter
*/
  public boolean getConnectionFromContainer(String dataSourceName)
                    throws NamingException,Exception {
    if (!isConnected) {
      InitialContext ic =null;
      try {
        ic = new InitialContext();
        DataSource ds = (DataSource)ic.lookup(dataSourceName);
        conn = ds.getConnection();
        //conn.setAutoCommit(true);
        isConnected = true;
        log.info
        ("Connected to the database successfully using datasource "+dataSourceName);
      }
      catch (NamingException ne) {
        log.error("Exception occurred in getConnectionFromContainer for DataSource name="+dataSourceName, ne);
        throw new NamingException
                ("Failed to lookup JDBC datasource. Check the datasource name");
      }
      catch (Exception e) {
        log.error("Exception occurred in getConnectionFromContainer", e);
        throw new Exception("Exception in getConnectionFromContainer() ");
      }
    }
    return isConnected;
  }
/**
 *  This method returns a Vector containing multiple DB records after executing
 *  the sql statement specified as the parameter
*/
	public Vector retrieveMultipleRecordsDB (String sqlStmt) throws SQLException {
		Vector rowData = null;
    Vector dataToReturn = null;
    int columnCount;
    Statement stmt = null;
    ResultSet	rs = null;
    boolean isThereResult =false;
     
		try {
      dataToReturn = new Vector();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sqlStmt);
      columnCount = rs.getMetaData().getColumnCount();
	  	isThereResult = rs.next();
      while (isThereResult) {
				rowData = new Vector();
  			for (int i = 0; i < columnCount; i++) {
		  		rowData.addElement(rs.getString(i + 1));
        }

				dataToReturn.addElement(rowData);
				isThereResult = rs.next();
			}
		}
    catch (SQLException sqle) {
			log.error("Exception in DBUtil.retrieveMultipleRecordsDB(String )");
			log.error("The statement executed : " + sqlStmt, sqle);
         
      throw sqle;
		}
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (stmt != null) {
        stmt.close();
        stmt = null;
      }
    }
		return dataToReturn;
  }

/**
 *   This method returns a Vector containing multiple DB records after executing
 *    the sql statement with the params specified
*/
	public Vector retrieveMultipleRecordsDB
                (String tableName, String[] tableFields, String whereClause)
                throws SQLException {

		Vector rowData = null;
    Vector dataToReturn = null;
    int columnCount;
    Statement stmt = null;
    ResultSet	rs = null;
    boolean isThereResult =false;
    String stmt_str = null;
		try {
      dataToReturn = new Vector();
      stmt = conn.createStatement();
  	  stmt_str = "select ";
  		for (int i = 0;i < tableFields.length; i++){
		  	if (i < tableFields.length - 1){
				  stmt_str += tableFields[i] + ", ";
  			}
	  		else{
			  	stmt_str += (String) tableFields[i] + " from " + tableName + " " + whereClause;
  			}
	  	}

   		log.debug("statement" + stmt_str);
      rs = stmt.executeQuery(stmt_str);
	  	isThereResult = rs.next();
      while (isThereResult) {
				rowData = new Vector();
  			for (int i = 0; i < tableFields.length; i++) {
          rowData.addElement(rs.getString(i + 1));
        }

				dataToReturn.addElement(rowData);
				isThereResult = rs.next();
			}

		}

    catch (SQLException sqle) {
			log.error("  Exception in DBUtil.retrieveMultipleRecordsDB()");
			log.error("  The statement executed : " + stmt_str, sqle);
      throw sqle;
		}
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (stmt != null) {
        stmt.close();
        stmt = null;
      }
    }
		return dataToReturn;
  }

/**
 *  This method returns a Vector containing one DB record
 *  after executing the sql statement specified as a parameter
 */
  public Vector retrieveRecordDB( String sqlStmt) throws SQLException{

    Vector rowData = null;
    Vector dataToReturn = null;
    int columnCount;
    Statement stmt = null;
    ResultSet	rs = null;
    boolean isThereResult =false;
		try {
      dataToReturn = new Vector();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sqlStmt);
      columnCount = rs.getMetaData().getColumnCount();
			isThereResult = rs.next();
			if (isThereResult== true) {
				for (int i = 0;i < columnCount; i++) {
					dataToReturn.addElement(rs.getString(i+1));
				}
			}
		}
    catch (SQLException sqle) {
			log.error("  Exception in DBUtil.retrieveRecordDB(String)");
			log.error("  The statement executed : " + sqlStmt, sqle);
      throw sqle;
		}
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (stmt != null) {
        stmt.close();
        stmt = null;
      }
    }
		return dataToReturn;
	}

  /**
 *   This method returns a Vector after executing the sql with
 *   the params specified
 */
  public Vector retrieveRecordDB( String tableName,
                                  String[] tableFields,
                                  String whereClause) throws SQLException {
    Vector rowData = null;
    Vector dataToReturn = null;
    int columnCount;
    Statement stmt = null;
    ResultSet	rs = null;
    boolean isThereResult =false;
    String stmt_str = null;
		try {
      dataToReturn = new Vector();
      stmt = conn.createStatement();
			stmt_str = "select ";
			for (int i = 0;i < tableFields.length; i++) {
				if (i < tableFields.length - 1) {
					stmt_str += tableFields[i] + ", ";
				} else {
					stmt_str += (String) tableFields[i] + " from " + tableName + " " + whereClause;
				}
			}

			rs = stmt.executeQuery(stmt_str);
			isThereResult = rs.next();

			if (isThereResult== true) {
				for (int i = 0;i < tableFields.length; i++) {
					dataToReturn.addElement(rs.getObject(i+1));
				}
			}

		}
    catch (SQLException sqle) {
			log.error("  Exception in DBUtil.retrieveRecordDB()");
			log.error("  The statement executed : " + stmt_str, sqle);
      throw sqle;
		}
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (stmt != null) {
        stmt.close();
        stmt = null;
      }
    }
		return dataToReturn;
	}

  /**
   *  Get unique id from the database
   *  idGenerator - an Oracle sequence number or store proc
   *
   */
    public String getUniqueId(String idGenerator) throws SQLException {
    String id=null;
    Statement stmt = null;
    ResultSet	rs = null;

    try {
      stmt = conn.createStatement();
      rs = stmt.executeQuery
        ("SELECT " + idGenerator + " FROM DUAL");
      rs.next();
      id = rs.getString(1);
		}
    catch(SQLException sqle) {
			log.error("Exception in getUniqueId()", sqle);
      throw sqle;
		}
    finally {
      if (rs != null) {
        rs.close();
        rs = null;
      }
      if (stmt != null) {
        stmt.close();
        stmt = null;
      }
    }
    return id;
  }

  
  public void returnConnection() throws SQLException {
    try {
      if (conn != null) {
        conn.close();
        isConnected = false;
      }
      log.error("Closing the Database connection ... ");
    } 
    catch (SQLException sqle) {
      log.error("Error occured in returing DB connection to the container", sqle);
      throw sqle;
    } 
  }

  public Connection getConnection(){
    return conn;
  }

  public ResultSet executeQuery(String sqlStmt) throws SQLException {
    Statement stmt = null;
    ResultSet	rs = null;
    try {
      stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                  ResultSet.CONCUR_READ_ONLY);
      rs = stmt.executeQuery(sqlStmt);
    } 
    catch (SQLException ex) {
      log.error("Exception occurred in executeQuery " + sqlStmt, ex);
      throw ex;
    } 
    return rs;
  }
  
  public static OracleConnection createOracleConnection(
    String dbURL,
    String username,
    String password) throws Exception {
    OracleDataSource ds = DataSourceUtil.getOracleDataSource(dbURL,username,password);
    return (OracleConnection)ds.getConnection();
    }

  public static void main(String[] args) {
    DBUtil dBUtil = new DBUtil();
  }
  
  
}