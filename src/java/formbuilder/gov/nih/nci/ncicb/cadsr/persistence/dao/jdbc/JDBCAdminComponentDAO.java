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
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;

import org.apache.commons.logging.LogFactory; 

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.dao.DataIntegrityViolationException;

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
    String adminIdseq
   ,String newLongName
   ,String username) {
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

  /**
   * Assigns the specified classification to an admin component
   *
   * @param <b>acId</b> Idseq of an admin component
   * @param <b>csCsiId</b> csCsiId
   *
   * @return <b>int</b> 1 - success; 0 - failure
   */
  public int assignClassification(
    String acId,
    String csCsiId) {
    
    try {
      InsertAcCsi insertAcCsi =
	new InsertAcCsi(this.getDataSource());
      int res = insertAcCsi.insertOneAcCsiRecord(csCsiId, acId);
      
    } catch (DataIntegrityViolationException e) {
      throw new DMLException(
        "Did not succeed. Classification is already assigned.");
    }

    return 1;
  }

  /**
   * Removes the specified classification assignment for an admin component
   *
   * @param <b>acCsiId</b> acCsiId
   *
   * @return <b>int</b> 1 - success; 0 - failure
   */
  public int removeClassification(String acCsiId) {
    DeleteAcCsi deleteAcCsi =
      new DeleteAcCsi(this.getDataSource());
    int res = deleteAcCsi.deleteOneAcCsiRecord(acCsiId);

    if (res != 1) {
      throw new DMLException(
        "Did not succeed removing classification for an AC.");
    }

    return 1;
  }

  /**
   * Retrieves all the assigned classifications for an admin component
   *
   * @param <b>acId</b> Idseq of an admin component
   *
   * @return <b>Collection</b> Collection of CSITransferObject
   */
  public Collection retrieveClassifications(String acId) {

    ClassificationQuery classificationQuery = new ClassificationQuery();
    classificationQuery.setDataSource(getDataSource());
    classificationQuery.setSql();

    return classificationQuery.execute(acId);
  }


  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    JDBCAdminComponentDAO jdbcAdminComponentDAO = 
      new JDBCAdminComponentDAO(locator);

    /*  
    int res = jdbcAdminComponentDAO.assignClassification(
      "99BA9DC8-2357-4E69-E034-080020C9C0E0", 
      "29A8FB30-0AB1-11D6-A42F-0010A4C1E842"); // acId, csCsiId
    System.out.println ("res = " + res);
    */
    /*
    int deleteRes = jdbcAdminComponentDAO.removeClassification
      ("D66B85B6-4EDA-469B-E034-0003BA0B1A09");
    System.out.println ("deleteRes = " + deleteRes);
    */
    Collection csito = jdbcAdminComponentDAO.retrieveClassifications(
      "29A8FB19-0AB1-11D6-A42F-0010A4C1E842");
    System.out.println (csito);
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

  /**
   * Inner class that insert a record in the ac_csi table. 
   * 
   */
  private class InsertAcCsi extends SqlUpdate {
    public InsertAcCsi(DataSource ds) {
      String insertSql =
        " INSERT INTO ac_csi (ac_csi_idseq, cs_csi_idseq, ac_idseq) " +
        " VALUES " + " (?, ?, ?) ";

      this.setDataSource(ds);
      this.setSql(insertSql);
      declareParameter(new SqlParameter("ac_csi_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("cs_cs_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("ac_idseq", Types.VARCHAR));
      compile();
    }

    protected int insertOneAcCsiRecord(
      String csCsId,
      String acId) {

      String acCsiId = generateGUID();

      Object[] obj =
        new Object[] {
          acCsiId,
          csCsId,
          acId
        };

      int res = update(obj);

      return res;
    }
  }

  /**
   * Inner class that delete a record in the ac_csi table. 
   * 
   */
  private class DeleteAcCsi extends SqlUpdate {
    public DeleteAcCsi(DataSource ds) {
      String deleteSql =
        " DELETE FROM ac_csi WHERE ac_csi_idseq = ? ";

      this.setDataSource(ds);
      this.setSql(deleteSql);
      declareParameter(new SqlParameter("ac_csi_idseq", Types.VARCHAR));
      compile();
    }

    protected int deleteOneAcCsiRecord(
      String acCsiId) {
      Object[] obj =
        new Object[] {
          acCsiId
        };

      int res = update(obj);

      return res;
    }
  }

  /**
   * Inner class to get all classifications that belong to
   * the specified data element
   */
  class ClassificationQuery extends MappingSqlQuery {
    ClassificationQuery() {
      super();
    }

    public void setSql() {
      super.setSql(
        "SELECT csi.csi_name, csi.csitl_name, csi.csi_idseq, " + 
        "       cscsi.cs_csi_idseq, cs.preferred_definition, cs.long_name, " + 
        "        accsi.ac_csi_idseq, cs.cs_idseq " + 
        " FROM ac_csi accsi, cs_csi cscsi, " + 
        "      class_scheme_items csi, classification_schemes cs  " + 
        " WHERE accsi.ac_idseq = ?  " + 
        " AND   accsi.cs_csi_idseq = cscsi.cs_csi_idseq " + 
        " AND   cscsi.csi_idseq = csi.csi_idseq " +
        " AND   cscsi.cs_idseq = cs.cs_idseq " );

      declareParameter(new SqlParameter("AC_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      CSITransferObject csito = new CSITransferObject();

      csito.setClassSchemeItemName(rs.getString(1)); 
      csito.setClassSchemeItemType(rs.getString(2));
      csito.setCsiIdseq(rs.getString(3));
      csito.setCsCsiIdseq(rs.getString(4));
      csito.setClassSchemeDefinition(rs.getString(5));
      csito.setClassSchemeLongName(rs.getString(6));
      csito.setAcCsiIdseq(rs.getString(7));
      csito.setCsIdseq(rs.getString(8));
      return csito;
    }
  }

  
}
