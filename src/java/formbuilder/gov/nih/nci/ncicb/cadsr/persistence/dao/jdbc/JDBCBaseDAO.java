package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.persistence.PersistenceContants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.BaseDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ConnectionException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.DAOCreateException;
import gov.nih.nci.ncicb.cadsr.security.oc4j.BaseUserManager;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import org.apache.commons.logging.LogFactory;

import org.springframework.jdbc.object.SqlFunction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;


public class JDBCBaseDAO extends BaseDAO implements PersistenceContants {
  public JDBCBaseDAO(ServiceLocator locator) {
    super(locator);
    log = LogFactory.getLog(JDBCBaseDAO.class.getName());
  }

  public DataSource getDataSource(String key) {
    if (getServiceLocator() != null) {
      return getServiceLocator().getDataSource(key);
    }

    return null;
  }

  public DataSource getDataSource() {
    DataSource ds = null;

    if (getServiceLocator() != null) {
      ds = getServiceLocator().getDataSource(getDataSourceKey());
    }

    if (log.isDebugEnabled()) {
      log.debug("Return DataSource =  " + ds);
    }

    return ds;
  }

  public String getDataSourceKey() {
    if (log.isDebugEnabled()) {
      log.debug("get DataSource key using key =  " + DATASOURCE_KEY);
    }

    String dsKey = getServiceLocator().getString(DATASOURCE_KEY);

    if (log.isDebugEnabled()) {
      log.debug("getDataSourceKey() =  " + dsKey);
    }

    return dsKey;
  }

  public boolean hasCreate(
    String username,
    String acType,
    String conteIdseq) {
    int[] inputTypes = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
    String sqlStmt =
      " SELECT cadsr_security_util.has_create_privilege(?,?,?) FROM DUAL ";
    SqlFunction hasCreateFunc =
      new SqlFunction(this.getDataSource(), sqlStmt, inputTypes, Types.VARCHAR);
    hasCreateFunc.compile();

    Object[] inputValues = { username, acType, conteIdseq };
    String retVal = (String) hasCreateFunc.runGeneric(inputValues);

    return StringUtils.toBoolean(retVal);
  }

  public boolean hasDelete(
    String username,
    String acIdseq) {
    int[] inputTypes = { Types.VARCHAR, Types.VARCHAR };
    String sqlStmt =
      " SELECT cadsr_security_util.has_delete_privilege(?,?) FROM DUAL ";
    SqlFunction hasDeleteFunc =
      new SqlFunction(this.getDataSource(), sqlStmt, inputTypes, Types.VARCHAR);
    hasDeleteFunc.compile();

    Object[] inputValues = { username, acIdseq };
    String retVal = (String) hasDeleteFunc.runGeneric(inputValues);

    return StringUtils.toBoolean(retVal);
  }

  public boolean hasUpdate(
    String username,
    String acIdseq) {
    int[] inputTypes = { Types.VARCHAR, Types.VARCHAR };
    String sqlStmt =
      " SELECT cadsr_security_util.has_update_privilege(?,?) FROM DUAL ";
    SqlFunction hasUpdateFunc =
      new SqlFunction(this.getDataSource(), sqlStmt, inputTypes, Types.VARCHAR);
    hasUpdateFunc.compile();

    Object[] inputValues = { username, acIdseq };
    String retVal = (String) hasUpdateFunc.runGeneric(inputValues);

    return StringUtils.toBoolean(retVal);
  }

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    //JDBCDAOFactory factory = (JDBCDAOFactory)new JDBCDAOFactory().getDAOFactory(locator);
    JDBCBaseDAO test = new JDBCBaseDAO(locator);

    boolean res;
    res = test.hasDelete("SBREX", "B046971C-6A89-5F47-E034-0003BA0B1A09");
    System.out.println("\n*****Delete Result 1: " + res);

    res = test.hasDelete("SBREXT", "99BA9DC8-2099-4E69-E034-080020C9C0E0");
    System.out.println("\n*****Delete Result 2: " + res);

    res =
      test.hasCreate(
        "SBREX", "CONCEPTUALDOM", "29A8FB18-0AB1-11D6-A42F-0010A4C1E842");
    System.out.println("\n*****Create Result 1: " + res);

    res =
      test.hasCreate(
        "SBREXT", "CONCEPTUALDOMAIN", "29A8FB18-0AB1-11D6-A42F-0010A4C1E842");
    System.out.println("\n*****Create Result 2: " + res);

    res = test.hasUpdate("SBREX", "29A8FB18-0AB1-11D6-A42F-0010A4C1E842");
    System.out.println("\n*****Update Result 1: " + res);

    res = test.hasUpdate("SBREXT", "29A8FB18-0AB1-11D6-A42F-0010A4C1E842");
    System.out.println("\n*****Update Result 2: " + res);
  }
}
