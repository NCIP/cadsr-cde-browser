package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.BaseDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ConnectionException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.DAOCreateException;
import gov.nih.nci.ncicb.cadsr.security.oc4j.BaseUserManager;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import org.apache.commons.logging.LogFactory;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;


public class JDBCBaseDAO extends BaseDAO implements PersistenceConstants {
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
    return DATASOURCE_LOCATION_KEY;
  }

  /**
   * Check if the user has a create privilege on the administered component
   * within the context
   *
   * @param username corresponds to the login user name.
   * @param acType corresponds to the administered component type
   * @param conteIdseq corresponds to the context id seq
   *
   * @return <b>boolean</b> true indicates that user has an create privilege
   *         and false when the user has not
   */
  public boolean hasCreate(
    String username,
    String acType,
    String conteIdseq) {
    HasCreateQuery hasCreate = new HasCreateQuery(this.getDataSource());
    String retValue = hasCreate.execute(username, acType, conteIdseq);

    return StringUtils.toBoolean(retValue);
  }

  /**
   * Check if the user has a delete privilege on the administered component
   *
   * @param username corresponds to the login user name.
   * @param acIdseq corresponds to the administered component id seq
   *
   * @return <b>boolean</b> true indicates that user has an delete privilege
   *         and false when the user has not
   */
  public boolean hasDelete(
    String username,
    String acIdseq) {
    HasDeleteQuery hasDelete = new HasDeleteQuery(this.getDataSource());
    String retValue = hasDelete.execute(username, acIdseq);

    return StringUtils.toBoolean(retValue);
  }

  /**
   * Check if the user has an update privilege on the administered component
   *
   * @param username corresponds to the login user name.
   * @param acIdseq corresponds to the administered component id seq
   *
   * @return <b>boolean</b> true indicates that user has an update privilege
   *         and false when the user has not
   */
  public boolean hasUpdate(
    String username,
    String acIdseq) {
    HasUpdateQuery hasUpdate = new HasUpdateQuery(this.getDataSource());
    String retValue = hasUpdate.execute(username, acIdseq);

    return StringUtils.toBoolean(retValue);
  }

  /**
   * Utility method to get global unique identifier used as a primary key in
   * all caDSR tables
   *
   * @return <b>String</b> global unique identifier (length 36)
   */
  public String generateGUID() {
    String guid = null;
    GUIDGenerator gen = new GUIDGenerator(this.getDataSource());
    guid = gen.getGUID();

    return guid;
  }

  /**
   * Utility method to derive preferred name using the long name.
   *
   * @param <b>longName</b> Long name of the admin component
   *
   * @return <b>String</b> Derived preferred name
   */
  public String generatePreferredName(String longName) {
    String prefName = null;
    PreferredNameGenerator gen =
      new PreferredNameGenerator(this.getDataSource());
    prefName = gen.getPreferredName(longName);

    return prefName;
  }

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    //JDBCDAOFactory factory = (JDBCDAOFactory)new JDBCDAOFactory().getDAOFactory(locator);
    JDBCBaseDAO test = new JDBCBaseDAO(locator);

    /*boolean res;
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
       System.out.println("\n*****Update Result 2: " + res);*/
    System.out.println("Preferred Name: " + test.generatePreferredName("my long name test test"));
  }

  /**
   * Inner class that checks if the user has a create privilege on the
   * administered component within the context
   */
  private class HasCreateQuery extends StoredProcedure {
    public HasCreateQuery(DataSource ds) {
      super(ds, "cadsr_security_util.has_create_privilege");
      setFunction(true);
      declareParameter(new SqlOutParameter("returnValue", Types.VARCHAR));
      declareParameter(new SqlParameter("p_ua_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_actl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_conte_idseq", Types.VARCHAR));
      compile();
    }

    public String execute(
      String username,
      String acType,
      String conteIdseq) {
      Map in = new HashMap();
      in.put("p_ua_name", username);
      in.put("p_actl_name", acType);
      in.put("p_conte_idseq", conteIdseq);

      Map out = execute(in);
      String retValue = (String) out.get("returnValue");

      return retValue;
    }
  }

  /**
   * Inner class that checks if the user has a delete privilege on the
   * administered component
   */
  private class HasDeleteQuery extends StoredProcedure {
    public HasDeleteQuery(DataSource ds) {
      super(ds, "cadsr_security_util.has_delete_privilege");
      setFunction(true);
      declareParameter(new SqlOutParameter("returnValue", Types.VARCHAR));
      declareParameter(new SqlParameter("p_ua_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_ac_idseq", Types.VARCHAR));
      compile();
    }

    public String execute(
      String username,
      String acIdseq) {
      Map in = new HashMap();
      in.put("p_ua_name", username);
      in.put("p_ac_idseq", acIdseq);

      Map out = execute(in);
      String retValue = (String) out.get("returnValue");

      return retValue;
    }
  }

  /**
   * Inner class that checks if the user has an update privilege on the
   * administered component
   */
  private class HasUpdateQuery extends StoredProcedure {
    public HasUpdateQuery(DataSource ds) {
      super(ds, "cadsr_security_util.has_update_privilege");
      setFunction(true);
      declareParameter(new SqlOutParameter("returnValue", Types.VARCHAR));
      declareParameter(new SqlParameter("p_ua_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_ac_idseq", Types.VARCHAR));
      compile();
    }

    public String execute(
      String username,
      String acIdseq) {
      Map in = new HashMap();
      in.put("p_ua_name", username);
      in.put("p_ac_idseq", acIdseq);

      Map out = execute(in);
      String retValue = (String) out.get("returnValue");

      return retValue;
    }
  }

  /**
   * Inner class to get global unique identifier
   */
  private class GUIDGenerator extends StoredProcedure {
    public GUIDGenerator(DataSource ds) {
      super(ds, PersistenceConstants.IDSEQ_GENERATOR);
      setFunction(true);
      declareParameter(new SqlOutParameter("returnValue", Types.VARCHAR));
      compile();
    }

    public String getGUID() {
      Map in = new HashMap();
      Map out = execute(in);
      String retValue = (String) out.get("returnValue");

      return retValue;
    }
  }

  /**
   * Inner class to get preferred name
   */
  private class PreferredNameGenerator extends StoredProcedure {
    public PreferredNameGenerator(DataSource ds) {
      super(ds, "set_name.set_qc_name");
      setFunction(true);
      declareParameter(new SqlOutParameter("returnValue", Types.VARCHAR));
      declareParameter(new SqlParameter("name", Types.VARCHAR));
      compile();
    }

    public String getPreferredName(String longName) {
      Map in = new HashMap();
      in.put("name", longName);

      Map out = execute(in);
      String retValue = (String) out.get("returnValue");

      return retValue;
    }
  }
}
