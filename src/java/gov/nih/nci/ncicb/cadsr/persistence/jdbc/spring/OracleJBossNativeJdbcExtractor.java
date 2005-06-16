package gov.nih.nci.ncicb.cadsr.persistence.jdbc.spring;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;

import org.springframework.jdbc.support.nativejdbc.JBossNativeJdbcExtractor;

/**
 * This class is extention of spring class that allows to get an handle to the 
 * underlying jdbc objects instead of jboss wrapper classes.
 * This class is a specialization to allow
 * accesss to Oracle specific objects
 */
public class OracleJBossNativeJdbcExtractor extends JBossNativeJdbcExtractor 
{
  public OracleJBossNativeJdbcExtractor()
  {
  }
  public OracleConnection doGetOracleConnection(Connection conn) throws SQLException
  {
    Connection obj = doGetNativeConnection(conn);
    return (OracleConnection)obj;
  }
  
  public OracleStatement getOracleStatement(Statement stmt) throws SQLException 
  {
    return (OracleStatement)getNativeStatement(stmt);
  }
  public OraclePreparedStatement getOraclePreparedStatement(PreparedStatement ps) throws SQLException 
  {
    return (OraclePreparedStatement)getNativePreparedStatement(ps);
  }
  public OracleCallableStatement getOracleCallableStatement(CallableStatement cs) throws SQLException 
  {
    return (OracleCallableStatement)getNativePreparedStatement(cs);
  }
  public OracleResultSet getoracleResultSet(ResultSet rs) throws SQLException 
  {
    return (OracleResultSet)getNativeResultSet(rs);
  }
}