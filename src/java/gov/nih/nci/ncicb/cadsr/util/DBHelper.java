package gov.nih.nci.ncicb.cadsr.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper  {
  public DBHelper() {
  }

  /**
   *  Get unique id from the database
   *  idGenerator - an Oracle sequence number or store proc
   *
   */
  public static String getUniqueId(Connection conn, 
                                   String idGenerator) throws SQLException {
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
      System.out.println("Exception in DBHelper.getUniqueId()");
      sqle.printStackTrace();
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

}