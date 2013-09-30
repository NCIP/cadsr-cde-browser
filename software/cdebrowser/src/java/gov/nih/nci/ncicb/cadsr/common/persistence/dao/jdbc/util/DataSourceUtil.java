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

package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.util;

import oracle.jdbc.pool.OracleDataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


public class DataSourceUtil {
  public DataSourceUtil() {
  }

  public static DataSource getDriverManagerDS(
    String driverClassName,
    String url,
    String userName,
    String password) {
    return new DriverManagerDataSource(
      driverClassName, url, userName, password);
  }

  public static OracleDataSource getOracleDataSource(
    String serverName,
    String databaseName,
    int portNumber,
    String username,
    String password) throws Exception {
    OracleDataSource ds = new OracleDataSource();
    ds.setDriverType("thin");
    ds.setServerName(serverName);
    ds.setPortNumber(portNumber);
    ds.setDatabaseName(databaseName);
    ds.setUser(username);
    ds.setPassword(password);

    return ds;
  }

  public static OracleDataSource getOracleDataSource(
    String dbURL,
    String username,
    String password) throws Exception {
    OracleDataSource ds = new OracleDataSource();
    ds.setURL(dbURL);
    ds.setUser(username);
    ds.setPassword(password);

    return ds;
  }
}
