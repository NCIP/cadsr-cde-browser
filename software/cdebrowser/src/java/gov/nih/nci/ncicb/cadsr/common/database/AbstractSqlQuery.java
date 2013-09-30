/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.common.database;

import gov.nih.nci.ncicb.cadsr.common.util.DBUtil;
import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractSqlQuery  {
  private static Log log = LogFactory.getLog(AbstractSqlQuery.class.getName());
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
      log.error("Exception caught executing query", ex);
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
      log.error("Exception occured in releaseResources(): ", ex);
    } 
    
  }
}