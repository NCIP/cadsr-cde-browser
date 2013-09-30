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

package gov.nih.nci.ncicb.cadsr.common.util;

import gov.nih.nci.ncicb.cadsr.common.util.logging.Log;
import gov.nih.nci.ncicb.cadsr.common.util.logging.LogFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper  {
  private static Log log = LogFactory.getLog(DBHelper.class.getName());
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

}