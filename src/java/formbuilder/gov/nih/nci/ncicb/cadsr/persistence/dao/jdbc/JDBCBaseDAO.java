package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.persistence.PersistenceContants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.BaseDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ConnectionException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.DAOCreateException;
import gov.nih.nci.ncicb.cadsr.security.oc4j.BaseUserManager;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;

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
    // TODO:  Implement this gov.nih.nci.ncicb.cadsr.persistence.dao.BaseDAO abstract method
    return false;
  }

  public boolean hasDelete(
    String username,
    String acIdseq) {
    // TODO:  Implement this gov.nih.nci.ncicb.cadsr.persistence.dao.BaseDAO abstract method
    return false;
  }

  public boolean hasUpdate(
    String username,
    String acIdseq) {
    // TODO:  Implement this gov.nih.nci.ncicb.cadsr.persistence.dao.BaseDAO abstract method
    return false;
  }
}
