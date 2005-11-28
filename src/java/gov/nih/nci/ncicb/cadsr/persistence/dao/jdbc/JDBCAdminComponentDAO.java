package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.AttachmentTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.AdminComponentDAO;
import gov.nih.nci.ncicb.cadsr.resource.Attachment;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;


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
    String retValue = this.getHasUpdateQry().execute(username.toUpperCase(), acIdseq);

    return StringUtils.toBoolean(retValue);
  }

  public boolean hasDelete(
    String username,
    String acIdseq) {
    String retValue = this.getHasDeleteQry().execute(username.toUpperCase(), acIdseq);

    return StringUtils.toBoolean(retValue);
  }

  public boolean hasCreate(
    String username,
    String acType,
    String conteIdseq) {
    String retValue =
      this.getHasCreateQry().execute(username.toUpperCase(), acType, conteIdseq);

    return StringUtils.toBoolean(retValue);
  }

  /**
   * Gets all ReferenceDocuments for a AdminComp
   *
   * @return <b>Collection</b> Collection of ReferenceDocumentTransferObjects
   */
  public List getAllReferenceDocuments(String adminComponentId,String docType) {
     List col = new ArrayList();
     ReferenceDocumentsQuery query = new ReferenceDocumentsQuery();
     query.setDataSource(getDataSource());
     query.setSql(adminComponentId,docType);
     col = query.execute();

     Iterator iter = col.iterator();
     while (iter.hasNext())
     {
       ReferenceDocument ref = (ReferenceDocument) iter.next();
       ref.setAttachments(this.getAllReferenceDocumentAttachments(ref.getDocIDSeq()));
     }
     return col;
  }

  private List getAllReferenceDocumentAttachments(String refDocId)
  {
     ReferenceAttachmentsQuery query = new ReferenceAttachmentsQuery(getDataSource());
     return query.execute(refDocId);

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

        DMLException dmlExp = new DMLException("Did not succeed. Classification is already assigned.");
        dmlExp.setErrorCode(ERROR_DUPLICATE_CLASSIFICATION);
         throw dmlExp;
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
         DMLException dmlExp = new DMLException("Did not succeed removing classification for an AC.");
	       dmlExp.setErrorCode(ERROR_REMOVEING_CLASSIFICATION);
           throw dmlExp;
    }

    return 1;
  }

  /**
   * Removes the specified classification assignment for an admin component
   * give cscsiIdseq and adminComponent Idseq
   * @param <b>acCsiId</b> acCsiId
   *
   * @return <b>int</b> 1 - success; 0 - failure
   */
  public int removeClassification(String cscsiIdseq, String acId) {

    DeleteCSIForAdminComp deleteAcCsi =
      new DeleteCSIForAdminComp(this.getDataSource());
    int res = deleteAcCsi.deleteAcClassification(cscsiIdseq,acId);

    if (res != 1) {
         DMLException dmlExp = new DMLException("Did not succeed removing classification for an AC.");
	       dmlExp.setErrorCode(ERROR_REMOVEING_CLASSIFICATION);
           throw dmlExp;
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

// Publish ChangeOrder
  /**
   * Check if the Admin Component is published
   *
   * @param <b>acId</b> Idseq of an admin component
   *
   * @return <b>Collection</b> Collection of CSITransferObject
   */
  public boolean isClassifiedForPublish(String acId,String conteIdSeq) {

    PublishClassificationQuery query = new PublishClassificationQuery(getDataSource());
    Integer count =  (Integer)query.isPublished(acId,conteIdSeq);
    if(count.intValue()>0)
      return true;
    else
     return false;
  }

  /**
   * Gets all CSI by funtion and admin Component type
   * 
   * @param <b>acId</b> Idseq of an admin component
   *    
   *    
   * @param csType
   * @param csiType
   * @param contextIdseq
   * @return 
   */
  public List getCSIByType(String csType, String csiType, String contextIdseq)
    {
      CSCSIsByTypeQuery query = new CSCSIsByTypeQuery(getDataSource());
      return query.getCSCSIs(csType,csiType,contextIdseq);
    }
  /**
   * Gets all CSI by classification and classification item type
   *
   * @param <b>csType</b> type of classification
   * @param <b>csiType</b> type of classification item
   *
   *
   */
  public List getAllCSIByType(String csType, String csiType)
    {
      AllCSCSIsByTypeQuery query = new AllCSCSIsByTypeQuery(getDataSource());
      return query.getCSCSIs(csType,csiType);
    }
    
    
    /**
   * Gets all CSI for CTEP
   *
   * @param 
   *
   *
   */
  public List getCSIForContextId(String contextId)
    {
      
      CSCSIsByContextIDQuery query = new CSCSIsByContextIDQuery(getDataSource());
      return query.getCSCSIs(contextId);
    }

    /**
   * Gets all CSCSI
   *
   * @param 
   *
   *
   */
  public List getCSCSIHierarchy()
    {
      
      CSCSIsHierQuery query = new CSCSIsHierQuery();
      query.setDataSource(getDataSource());
      query.setSql();
      return query.execute();
    }

  /**
   * Gets all CSCSI by type
   *
   * @param csType
   * @param csiType
   *
   */
  public List getCSCSIHierarchyByType(String csType, String csiType)
    {
      
      CSCSIsHierQueryByType query = new CSCSIsHierQueryByType(getDataSource());
      return query.getCSCSIs(csType,csiType);
    }

  public int designate(String contextIdSeq, List acIdList){
      int res = 0;
       DesignateCDE designateCDE =
          new DesignateCDE(this.getDataSource());
       res = designateCDE.designate(contextIdSeq, acIdList);
      return res;
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
    Collection csito = jdbcAdminComponentDAO.retrieveClassifications(
      "29A8FB19-0AB1-11D6-A42F-0010A4C1E842");
    */
    
    
    Collection csito = jdbcAdminComponentDAO.getCSCSIHierarchy();
   System.out.println (csito.size());
    
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
     * Inner class that insert a record in the DESIGNATIONS table.
     *
     */
    private class DesignateCDE extends SqlUpdate {
      //constants.
      private static final String DESIGNATIONS_COL_NAME = "'UNKNOWN_NAME'";
      private static final String DESIGNATIONS_COL_LAE_NAME = "'ENGLISH'";
    
      public DesignateCDE(DataSource ds) {
        String insertSql =
          " INSERT INTO designations (desig_idseq, ac_idseq, conte_idseq, name, detl_name, lae_name) " +
          " (select ?, ?, ?, PREFERRED_NAME, 'USED_BY', 'ENGLISH' from administered_components where " + 
          " ac_idseq = ?)";

        this.setDataSource(ds);
        this.setSql(insertSql);
        declareParameter(new SqlParameter("desig_idseq", Types.VARCHAR));
        declareParameter(new SqlParameter("ac_idseq", Types.VARCHAR));
        declareParameter(new SqlParameter("conte_idseq", Types.VARCHAR));
        declareParameter(new SqlParameter("ac_idseq", Types.VARCHAR));
        compile();
      }

      protected int designate(
        String contextIdSeq, 
        List acIdList){
        
        //sanity check
        if (acIdList == null ||  acIdList.size() == 0){
            log.info("ac ID list is null or empty list. Nothing is designated.");
            return 0;
        }

        Object[] obj =
               new Object[] {
                  "",
                  "",
                  contextIdSeq,
                  ""
               }; 
        int total = 0; 
        int res = 0;
        Iterator it = acIdList.iterator();
        while (it.hasNext()){
            obj[0] = generateGUID();
            obj[1] = (String)it.next();
            obj[3] = obj[1];
            try{
                res = update(obj);
            } catch (DataIntegrityViolationException e) {
              //log info but will not throw exception
              log.info("ac ID " + obj[1] + " is already designated to context ID " + contextIdSeq);
            }    
            total += res;
        }
        return total;
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

  /**
   * Inner class to get all ReferenceDocuments that belong to
   * the specified Admin Component
   */
  class ReferenceDocumentsQuery extends MappingSqlQuery {
    ReferenceDocumentsQuery() {
      super();
    }

    public void setSql(String adminCompId,String docType) {
      super.setSql(
        "SELECT ref.name, ref.dctl_name, ref.ac_idseq, " +
        "       ref.rd_idseq, ref.url, ref.doc_text, " +
        " ref.conte_idseq, con.name, ref.display_order" +
        " FROM reference_documents ref, contexts con" +
        " WHERE ref.ac_idseq = '" +adminCompId+"'"+
//        " AND   ref.DCTL_NAME = '"+ docType+"'" +
        " AND ref.conte_idseq = con.conte_idseq " +
        " order by ref.display_order"
        );
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      ReferenceDocument refDoc = new ReferenceDocumentTransferObject();

      refDoc.setDocName(rs.getString(1));
      refDoc.setDocType(rs.getString(2));
      refDoc.setDocIDSeq(rs.getString(4));
      refDoc.setUrl(rs.getString(5));
      refDoc.setDocText(rs.getString(6));
      refDoc.setDisplayOrder(rs.getInt(9));

      ContextTransferObject contextTransferObject = new ContextTransferObject();
      contextTransferObject.setConteIdseq(rs.getString(7)); //CONTE_IDSEQ
      contextTransferObject.setName(rs.getString(8)); // CONTEXT_NAME
      refDoc.setContext(contextTransferObject);
      return refDoc;
    }
  }
  /**
   * Inner class to get all ReferenceDocuments that belong to
   * the specified Admin Component
   */
  class ReferenceAttachmentsQuery extends MappingSqlQuery {
    ReferenceAttachmentsQuery(DataSource ds) {
    super( ds,
      "SELECT refb.name, refb.mime_type, refb.doc_size, refb.DATE_CREATED" +
        " FROM reference_blobs refb" +
        " WHERE refb.rd_idseq = ?" +
        " order by date_created " );
      declareParameter(new SqlParameter("refDocId", Types.VARCHAR));
      compile();
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      Attachment attachment = new AttachmentTransferObject();

      attachment.setName(rs.getString(1));
      attachment.setMimeType(rs.getString(2));
      attachment.setDocSize(rs.getLong(3));
      attachment.setDateCreated(rs.getTimestamp(4));
      return attachment;
    }
  }


  //Change Order
  class PublishClassificationQuery extends MappingSqlQuery {
    PublishClassificationQuery(DataSource ds) {
      super(
        ds,
        "SELECT COUNT(*) from published_forms_view   "+
        " WHERE QC_idseq = ?  and publish_conte_idseq = ?" );
      declareParameter(new SqlParameter("QC_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("publish_conte_idseq", Types.VARCHAR));
      compile();
    }

    protected Object isPublished(
      String acIdseq,String conteIdSeq) {

      Object[] obj =
        new Object[] {
          acIdseq,
          conteIdSeq
        };

      return findObject(obj);
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return new Integer(rs.getInt(1));
    }
  }

    //Publish Change Order
  class CSCSIsByContextIDQuery extends MappingSqlQuery {
    
    CSCSIsByContextIDQuery(DataSource ds)  {
      super(ds, "SELECT  csi.csi_idseq "
                               +"       ,csi_name "
                               +"       ,csitl_name "
                               +"       ,description "
                               +"       ,csc.cs_csi_idseq "
                               +"       ,cs.preferred_name "
                               +"FROM   sbr.class_scheme_items csi "
                               +"      ,sbr.cs_csi csc "
                               +"      ,sbr.classification_schemes cs "
                               +"WHERE csi.csi_idseq = csc.csi_idseq "
                               +"AND cs.CONTE_IDSEQ = ? "
                               +"AND csc.cs_idseq = cs.cs_idseq "
                               +"AND   cs.preferred_name in( 'CRF_DISEASE','Phase' ) "
                               +"ORDER BY upper(csi.csi_name) " );
      declareParameter(new SqlParameter("context_id", Types.VARCHAR));
      compile();
    }

  protected Object mapRow(ResultSet rs, int rownum) throws SQLException {

        ClassSchemeItem csiTO = new CSITransferObject();
        csiTO.setCsiIdseq(rs.getString("csi_idseq"));
        csiTO.setClassSchemeItemName(rs.getString("csi_name"));
        csiTO.setClassSchemeItemType(rs.getString("csitl_name"));
        csiTO.setCsCsiIdseq(rs.getString("cs_csi_idseq"));
        csiTO.setClassSchemeLongName(rs.getString("preferred_name"));

   return csiTO;
  }

    protected List getCSCSIs( String contextIdSeq) {
      Object[] obj =
        new Object[] { contextIdSeq
        };

      return execute(obj);

    }
 }
  
  class CSCSIsByTypeQuery extends MappingSqlQuery {
    CSCSIsByTypeQuery(DataSource ds) {
      super(
        ds,
          "select distinct cscsi.CS_CSI_IDSEQ, i.CSITL_NAME , "  +
          " cs.LONG_NAME, cscsi.LABEL from  classification_schemes cs, "
          + " sbr.class_scheme_items i , cs_csi cscsi "
	   			+" where cs.CSTL_NAME=? and i.CSITL_NAME=? "
					+" and cscsi.CSI_IDSEQ=i.CSI_IDSEQ "
					+" and cscsi.CS_IDSEQ=cs.CS_IDSEQ "
          +" and cs.CONTE_IDSEQ=? ");

      declareParameter(new SqlParameter("CSTL_NAME", Types.VARCHAR));
      declareParameter(new SqlParameter("CSITL_NAME", Types.VARCHAR));
      declareParameter(new SqlParameter("CONTE_IDSEQ", Types.VARCHAR));
      compile();
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
        ClassSchemeItem csiTO = new CSITransferObject();
        csiTO.setClassSchemeItemName(rs.getString("LABEL"));
        csiTO.setClassSchemeItemType(rs.getString("csitl_name"));
        csiTO.setCsCsiIdseq(rs.getString("cs_csi_idseq"));
        csiTO.setClassSchemeLongName(rs.getString("LONG_NAME"));
 
   return csiTO;
   }

    protected List getCSCSIs(
      String csType,
      String csiType,
      String contextIdSeq) {


      Object[] obj =
        new Object[] {
          csType,
          csiType,
          contextIdSeq
        };

      return execute(obj);
    }
  }

  class AllCSCSIsByTypeQuery extends MappingSqlQuery {
    AllCSCSIsByTypeQuery(DataSource ds) {
      super(
        ds,
          "select distinct cscsi.CS_CSI_IDSEQ, i.CSITL_NAME , "  +
          " cs.LONG_NAME, cscsi.LABEL , cs.CONTE_IDSEQ " + 
          " from  classification_schemes cs, "
          + " sbr.class_scheme_items i , cs_csi cscsi "
	   			+" where cs.CSTL_NAME=? and i.CSITL_NAME=? "
					+" and cscsi.CSI_IDSEQ=i.CSI_IDSEQ "
					+" and cscsi.CS_IDSEQ=cs.CS_IDSEQ "
          +" order by cs.CONTE_IDSEQ ");

      declareParameter(new SqlParameter("CSTL_NAME", Types.VARCHAR));
      declareParameter(new SqlParameter("CSITL_NAME", Types.VARCHAR));
      compile();
    }
    
   protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
        ClassSchemeItem csiTO = new CSITransferObject();
        csiTO.setClassSchemeItemName(rs.getString("LABEL"));
        csiTO.setClassSchemeItemType(rs.getString("csitl_name"));
        csiTO.setCsCsiIdseq(rs.getString("cs_csi_idseq"));
        csiTO.setClassSchemeLongName(rs.getString("LONG_NAME"));
        csiTO.setCsConteIdseq(rs.getString("CONTE_IDSEQ"));
 
   return csiTO;
   }

    protected List getCSCSIs(
      String csType,
      String csiType) {


      Object[] obj =
        new Object[] {
          csType,
          csiType,
        };

      return execute(obj);
    }
  }


  /**
   * Inner class that delete a record in the ac_csi table.
   *
   */
  private class DeleteCSIForAdminComp extends SqlUpdate {
    public DeleteCSIForAdminComp(DataSource ds) {
      String deleteSql =
        " DELETE FROM ac_csi WHERE cs_csi_idseq = ? and ac_idseq = ? ";

      this.setDataSource(ds);
      this.setSql(deleteSql);
      declareParameter(new SqlParameter("cs_csi_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("ac_idseq", Types.VARCHAR));

      compile();
    }

    protected int deleteAcClassification(
      String cscsiIdSeq,String acIdSeq) {
      Object[] obj =
        new Object[] {
          cscsiIdSeq,acIdSeq
        };

      int res = update(obj);

      return res;
    }
  }
  
    //Load all CsCSI Hierarchy
  private class CSCSIsHierQuery extends MappingSqlQuery {
    CSCSIsHierQuery() {
      super();
    }

    public void setSql() {
      super.setSql(
        "select cs_idseq, cs_preffered_name, cs_long_name, "
        + "CS_PREFFRED_DEFINITION, "
        + "csi_idseq, csi_name, csitl_name, csi_description, "
        + "cs_csi_idseq, csi_level, parent_csi_idseq, cs_conte_idseq "
        + " from SBREXT.BR_CS_CSI_HIER_VIEW_EXT "
        + " where CS_ASL_NAME = 'RELEASED' "
        + " and CSTL_NAME != 'Publishing' "
        + " order by cs_conte_idseq, CSI_LEVEL, upper(cs_long_name), upper(csi_name)"
        );
    }


    protected Object mapRow( ResultSet rs, int rownum) throws SQLException {
      
        ClassSchemeItem csiTO = new CSITransferObject();
        csiTO.setCsIdseq(rs.getString("cs_idseq"));
        csiTO.setClassSchemeLongName(rs.getString("cs_long_name"));
        csiTO.setClassSchemePrefName(rs.getString("cs_preffered_name"));
        csiTO.setClassSchemeDefinition(rs.getString("CS_PREFFRED_DEFINITION"));

        csiTO.setCsiIdseq(rs.getString("csi_idseq"));
        csiTO.setClassSchemeItemName(rs.getString("csi_name"));
        csiTO.setClassSchemeItemType(rs.getString("csitl_name"));
        csiTO.setCsiDescription(rs.getString("csi_description"));
        
        csiTO.setCsCsiIdseq(rs.getString("cs_csi_idseq"));
        csiTO.setParentCscsiId(rs.getString("parent_csi_idseq"));
        csiTO.setCsConteIdseq(rs.getString("cs_conte_idseq"));
 
   return csiTO;
    }
  }


  //Load CsCSI Hierarchy by cs and csi type
  private class CSCSIsHierQueryByType extends MappingSqlQuery {

    CSCSIsHierQueryByType(DataSource ds) {
      super(
        ds, "select cs_idseq, cs_preffered_name, cs_long_name, "
        + "CS_PREFFRED_DEFINITION, "
        + "csi_idseq, csi_name, csitl_name, csi_description, "
        + "cs_csi_idseq, csi_level, parent_csi_idseq, cs_conte_idseq "
        + " from SBREXT.BR_CS_CSI_HIER_VIEW_EXT "
        + " where upper(CSTL_NAME) =upper(?) and upper(CSITL_NAME)=upper(?) "
        + " and CS_ASL_NAME = 'RELEASED' "
        + " and CSTL_NAME != 'Publishing' "
        + " order by cs_conte_idseq, CSI_LEVEL, upper(cs_long_name), upper(csi_name)");
        
      declareParameter(new SqlParameter("CSTL_NAME", Types.VARCHAR));
      declareParameter(new SqlParameter("CSITL_NAME", Types.VARCHAR));
      compile();
    }

    protected Object mapRow( ResultSet rs, int rownum) throws SQLException {
      
        ClassSchemeItem csiTO = new CSITransferObject();
        csiTO.setCsIdseq(rs.getString("cs_idseq"));
        csiTO.setClassSchemeLongName(rs.getString("cs_long_name"));
        csiTO.setClassSchemePrefName(rs.getString("cs_preffered_name"));
        csiTO.setClassSchemeDefinition(rs.getString("CS_PREFFRED_DEFINITION"));

        csiTO.setCsiIdseq(rs.getString("csi_idseq"));
        csiTO.setClassSchemeItemName(rs.getString("csi_name"));
        csiTO.setClassSchemeItemType(rs.getString("csitl_name"));
        csiTO.setCsiDescription(rs.getString("csi_description"));
        
        csiTO.setCsCsiIdseq(rs.getString("cs_csi_idseq"));
        csiTO.setParentCscsiId(rs.getString("parent_csi_idseq"));
        csiTO.setCsConteIdseq(rs.getString("cs_conte_idseq"));
 
   return csiTO;
    }
    
   protected List getCSCSIs(
      String csType,
      String csiType) {


      Object[] obj =
        new Object[] {
          csType,
          csiType
        };

      return execute(obj);
    }

  }

}
