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

import java.sql.Connection;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.object.SqlFunction;
import java.sql.Types;

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
    // TODO:  Implement this gov.nih.nci.ncicb.cadsr.persistence.dao.BaseDAO abstract method
    return false;
  }

  public boolean hasDelete(
    String username,
    String acIdseq) {
    // TODO:  Implement this gov.nih.nci.ncicb.cadsr.persistence.dao.BaseDAO abstract method
    int [] inputTypes = {Types.VARCHAR,Types.VARCHAR};
    String sqlStmt = " SELECT cadsr_security_util.has_delete_privilege(?,?) FROM DUAL ";
    DataSource ds = this.getDataSource();
    SqlFunction hasDeleteFunc = new SqlFunction(this.getDataSource(),sqlStmt,inputTypes,Types.VARCHAR);
    hasDeleteFunc.compile();
    Object [] inputValues = {username,acIdseq};
    String retVal = (String)hasDeleteFunc.runGeneric(inputValues);
    boolean b = false;
    if (retVal.equals("Yes")) b =true;
    else if (retVal.equals("No")) b = false;
    return b;
  }

  public boolean hasUpdate(
    String username,
    String acIdseq) {
    // TODO:  Implement this gov.nih.nci.ncicb.cadsr.persistence.dao.BaseDAO abstract method
    return false;
  }

   public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    //JDBCDAOFactory factory = (JDBCDAOFactory)new JDBCDAOFactory().getDAOFactory(locator);
    JDBCBaseDAO test = new JDBCBaseDAO(locator);
    boolean b = test.hasDelete("SBREXT","B046971C-6A89-5F47-E034-0003BA0B1A09");
    System.out.println("Result: "+b);
    
  }  
}
