package gov.nih.nci.ncicb.cadsr.database;

import gov.nih.nci.ncicb.cadsr.util.DBUtil;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public abstract class AbstractSqlQuery  {
  protected long startRowNum;
  protected long endRowNum;
  protected long totalRowCount;
  protected ResultSet rs = null;
  protected Statement stmt;
  protected PreparedStatement pstmt;

  public AbstractSqlQuery() {
  }

  public AbstractSqlQuery(String sqlStmt, DBUtil dbUtil) {
  }

  protected ResultSet executeQuery() throws SQLException {
    //ResultSet rs = null;
    try {
      
    } 
    catch (Exception ex) {
      ex.printStackTrace();
    } 
    finally {
    }
    return rs; 
  }

  protected abstract ResultReader getRowsInRange();

  protected long getStartRowNum (){
    return startRowNum;
  }

  protected void setStartRowNum(long lStart) {
    startRowNum = lStart;
  }

  protected long getEndRowNum() {
    return endRowNum;
  }

  protected void setEndRowNum(long lEnd) {
    endRowNum = lEnd;
  }

  protected void releaseResources() {
    try {
      if (rs != null) rs.close();
      if (stmt != null) stmt.close();
      if (pstmt != null) pstmt.close();
    } 
    catch (Exception ex) {
      System.out.println("Exception occured in releaseResources(): "+ex.getMessage());
      ex.printStackTrace();
    } 
    
  }
}