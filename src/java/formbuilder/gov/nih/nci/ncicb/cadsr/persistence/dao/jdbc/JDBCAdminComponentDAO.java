package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AdminComponentDAO;
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
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;


public class JDBCAdminComponentDAO extends JDBCBaseDAO
  implements AdminComponentDAO {
  private static PreferredNameGenerator nameGen = null;
  private static HasCreateQuery hasCreateQry = null;
  private static HasUpdateQuery hasUpdateQry = null;
  private static HasDeleteQuery hasDeleteQry = null;

  public JDBCAdminComponentDAO(ServiceLocator locator) {
    super(locator);
  }

  public int updateLongName(
    String adminIdseq,
    String newLongName) {
    return 0;
  }

  public String generatePreferredName(String longName) {
    return this.getNameGen().getPreferredName(longName);
  }

  public boolean hasUpdate(
    String username,
    String acIdseq) {
    String retValue = this.getHasUpdateQry().execute(username, acIdseq);

    return StringUtils.toBoolean(retValue);
  }

  public boolean hasDelete(
    String username,
    String acIdseq) {
    String retValue = this.getHasDeleteQry().execute(username, acIdseq);

    return StringUtils.toBoolean(retValue);
  }

  public boolean hasCreate(
    String username,
    String acType,
    String conteIdseq) {
    String retValue =
      this.getHasCreateQry().execute(username, acType, conteIdseq);

    return StringUtils.toBoolean(retValue);
  }

  private JDBCAdminComponentDAO.HasCreateQuery getHasCreateQry() {
    if (hasCreateQry == null) {
      hasCreateQry = new HasCreateQuery(this.getDataSource());
    }

    return hasCreateQry;
  }

  private JDBCAdminComponentDAO.HasDeleteQuery getHasDeleteQry() {
    if (hasDeleteQry == null) {
      hasDeleteQry = new HasDeleteQuery(this.getDataSource());
    }

    return hasDeleteQry;
  }

  private JDBCAdminComponentDAO.HasUpdateQuery getHasUpdateQry() {
    if (hasUpdateQry == null) {
      hasUpdateQry = new HasUpdateQuery(this.getDataSource());
    }

    return hasUpdateQry;
  }

  private JDBCAdminComponentDAO.PreferredNameGenerator getNameGen() {
    if (nameGen == null) {
      nameGen = new PreferredNameGenerator(this.getDataSource());
    }

    return nameGen;
  }

  public int assignClassification(
    String acId,
    String csCsiId) {
    return 0;
  }

  public int removeClassification(String acCsiId) {
    return 0;
  }

  public Collection retrieveClassifications(String acId) {
    return null;
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
