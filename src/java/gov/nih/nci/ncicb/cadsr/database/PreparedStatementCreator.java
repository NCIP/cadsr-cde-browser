package gov.nih.nci.ncicb.cadsr.database;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Types;

import oracle.jdbc.OracleStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OraclePreparedStatement;

import java.util.Map;
import java.util.HashMap;


public class PreparedStatementCreator  {
  private OraclePreparedStatement pstmt;
  private OraclePreparedStatement rowCountPstmt;
  private static Map parameterMap = new HashMap(10);
  private int [] parameterTypes;
  private String [] columnNames;
  private int [] columnTypes;
  private int rangeSize;
  private long rangeStart;
  private String selectFromWhereClause;
  private String orderByClause;
  private String completeSqlStmt;
  private ResultSet queryResult;
  private long currRowIndex = 0;

  static {
    parameterMap.put("Types.VARCHAR","setString");
    parameterMap.put("Types.INTEGER","setInt");
    parameterMap.put("Types.FLOAT","setFloat");
    parameterMap.put("Types.DATE","setDate");
  }
  public PreparedStatementCreator(String [] queryClauses
                                ,String [] columnNames
                                ,int [] columnTypes
                                ,Connection conn) throws SQLException{
    selectFromWhereClause = queryClauses[0];
    orderByClause = queryClauses[1];
    if (orderByClause != null) {
      completeSqlStmt = selectFromWhereClause + " ORDER BY " +orderByClause;
    }
    else {
      completeSqlStmt = selectFromWhereClause;
    }
    String countStmt = " SELECT COUNT(1) FROM ( "+ selectFromWhereClause + " ) ";
    
    pstmt = (OraclePreparedStatement) conn.prepareStatement(completeSqlStmt);
    rowCountPstmt = (OraclePreparedStatement) 
                    conn.prepareStatement(countStmt);
    //rowCountPstmt.defineColumnType(1,Types.);
    for(int i=0; i < columnTypes.length; i++) {
      pstmt.defineColumnType(i+1,columnTypes[i]);
    }
    this.parameterTypes = parameterTypes;
    this.columnNames = columnNames;
    this.columnTypes = columnTypes;
  }

  public void setBindVariableValues(Object [] parameterValues) 
              throws SQLException {
    for(int i=0; i < parameterValues.length; i++) {
      processParameter(parameterTypes[i],parameterValues[i],i+1);
    }
  }
  public void executeQuery() throws SQLException {
    queryResult = pstmt.executeQuery();
  }

  private void processParameter(int parameterType, 
                                Object val, 
                                int parameterIndex) throws SQLException {
    switch (parameterType)  {
      case Types.VARCHAR: {
        pstmt.setString(parameterIndex,(String)val);
        rowCountPstmt.setString(parameterIndex,(String)val);
      }
      break;

      case Types.INTEGER: {
        pstmt.setInt(parameterIndex,Integer.parseInt((String)val));
        rowCountPstmt.setInt(parameterIndex,Integer.parseInt((String)val));
      }
      break;

      case Types.FLOAT: {
        pstmt.setFloat(parameterIndex,Float.parseFloat((String)val));
        rowCountPstmt.setFloat(parameterIndex,Float.parseFloat((String)val));
      }
      break;
        
      default: {
        pstmt.setObject(parameterIndex,val);
        rowCountPstmt.setObject(parameterIndex,val);
      }
      break;
    }
    
  }

  public long getTotalRowCount() throws SQLException {
    long count = -1;
    ResultSet r = rowCountPstmt.executeQuery();
    while (r.next()) {
      count = r.getInt(1);
    }

    return count;
  }

  public void setRangeStart(long startRow) {
    rangeStart = startRow;
  }

  public void setRangeSize(int size) {
    rangeSize = size;
  }

  public long getRangeStart() {
    return rangeStart;
  }

  public int setRangeSize() {
    return rangeSize;
  }

  public SqlQueryResult [] getAllRowsInRange() throws SQLException{
    SqlQueryResult [] rows = new SqlQueryResult [rangeSize];
    long startRow = rangeStart;
    long endRow = rangeStart + rangeSize;
    long index = 0;
    while (queryResult.next() ) {
      if (index >= startRow && index <endRow ) {
        SqlQueryResult row = new SqlQueryResult ();
        row.setAttribute(1,processQueryResult(queryResult,1));
      }

      index++;
    }
    

    return rows;
  }

  private Object processQueryResult(ResultSet queryResult
                                   ,int columnIndex) throws SQLException {
    int columnType = columnTypes[columnIndex];
    Object val = null;
    switch (columnType)  {

      case Types.VARCHAR: {
        val= queryResult.getString(columnIndex);
      }
      break;

      case Types.INTEGER: {
        val = new Integer(queryResult.getInt(columnIndex));
      }
      break;

      case Types.FLOAT: {
        val = new Float (queryResult.getFloat(columnIndex));
      }
      break;
        
      default: {
        val = queryResult.getObject(columnIndex);
      }
      break;
    }
    return val;
  }
}