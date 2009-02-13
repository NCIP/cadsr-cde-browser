package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.AddressTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.AttachmentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ContactCommunicationTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ContactTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DefinitionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.DesignationTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.OrganizationTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.PersonTransferObject;
import gov.nih.nci.ncicb.cadsr.common.dto.ReferenceDocumentTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.AdminComponentDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Address;
import gov.nih.nci.ncicb.cadsr.common.resource.Attachment;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.resource.Contact;
import gov.nih.nci.ncicb.cadsr.common.resource.ContactCommunication;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.Definition;
import gov.nih.nci.ncicb.cadsr.common.resource.Designation;
import gov.nih.nci.ncicb.cadsr.common.resource.Organization;
import gov.nih.nci.ncicb.cadsr.common.resource.Person;
import gov.nih.nci.ncicb.cadsr.common.resource.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
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
   * Gets all CSCSI for a context
   *
   * @param
   *
   *
   */
   public List<ClassSchemeItem> getCSCSIHierarchyByContext(String contextIdseq)
   {
      CSCSIsHierByContextQuery  query = new CSCSIsHierByContextQuery(getDataSource());
      return query.getCSCSIs(contextIdseq);

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
   /**
    * Gets all CSCSI by type
    *
    * @param csType
    * @param csiType
    *
    */
   public List getCSCSIHierarchyByTypeAndContext(String csType, String csiType,
   String contextIdseq)
     {

       CSCSIsHierQueryByTypeAndContext query = new CSCSIsHierQueryByTypeAndContext(getDataSource());
       return query.getCSCSIs(csType,csiType, contextIdseq);
     }

    /**
        * Designate the ACs to the specified context.
        * @param contextIdSeq context id seq
        * @param acIdList  a list of AC id seq.
        * @return the total number of ac designated to the context.
        *  with the given registration status
        */
  public int designate(String contextIdSeq, List acIdList, String createdBy){
      int res = 0;
       DesignateCDE designateCDE =
          new DesignateCDE(this.getDataSource());
       res = designateCDE.designate(contextIdSeq, acIdList, createdBy);
      return res;
  }

    /**
        *
        * @param acIdList a list of AC
        * @param contextIdSeq context id seq.
        * @return true if all the AC are designated in the context.
        */
    public boolean isAllACDesignatedToContext(List acIdList, String contextIdSeq){
        CheckACDesignationQuery query = new CheckACDesignationQuery(getDataSource());
        return query.isAllACDesignatedToContext(acIdList,contextIdSeq);
    }

    /**
    * @param acIdList a list of AC
    * @return Context object.
     */
    public Context getContext(String acIdSeq) {
        ContextQuery query = new ContextQuery();
        query.setDataSource(getDataSource());
        query.setSql();
        return query.getContext(acIdSeq);
    }

    /**
    * @param acIdList a list of AC
    * @return contact list object.
     */
   public List<Contact> getContacts(String acIdseq) {
      List<Contact> contacts = new ArrayList();
      PersonContactByACIdQuery personQuery = new PersonContactByACIdQuery();
      personQuery.setDataSource(getDataSource());
      contacts.addAll(personQuery.getPersonContacts(acIdseq));

      ContactCommunicationsQuery commQuery = new ContactCommunicationsQuery();
      commQuery.setDataSource(getDataSource());
      Iterator<Contact> perIter=contacts.iterator();
      while (perIter.hasNext()) {
         Person person = perIter.next().getPerson();
         person.setContactCommunications( commQuery.getContactCommsbyPerson(person.getId()));

      }

      OrgContactByACIdQuery orgQuery = new OrgContactByACIdQuery();
      orgQuery.setDataSource(getDataSource());
      List<Contact> orgContacts = orgQuery.getOrgContacts(acIdseq);
      Iterator<Contact> orgIter=orgContacts.iterator();
      while (orgIter.hasNext()) {
         Organization org = orgIter.next().getOrganization();
         org.setContactCommunications( commQuery.getContactCommsbyOrg(org.getId()));
      }

      contacts.addAll(orgContacts);
      return contacts;
   }

    public List<Designation> getDesignations(String acIdSeq, String type) {
        DesignationQuery query = new DesignationQuery();
        query.setDataSource(getDataSource());
        query.setSql(type);
        List<Designation> desigs = query.getDesignations(acIdSeq, type);
        for (Designation designation:desigs){
            List<ClassSchemeItem> cscsiList = getAcAttrCSCSIByAcAttrId(designation.getDesigIDSeq());
            ((DesignationTransferObject)designation).setCsCsis(cscsiList);
        }
        return desigs;
    }

    public List<Definition> getDefinitions(String acIdSeq){
        DefinitionQuery query = new DefinitionQuery();
        query.setDataSource(getDataSource());
        query.setSql();
        List<Definition> definitions = query.getDefinitions(acIdSeq);
        for (Definition def:definitions){
            List<ClassSchemeItem> cscsiList = getAcAttrCSCSIByAcAttrId(def.getId());
            ((DefinitionTransferObject)def).setCsCsis(cscsiList);
        }
        return definitions;
    }

    public List<ClassSchemeItem> getAcAttrCSCSIByAcAttrId(String acAttrIdSeq){
        AcAttrQuery query = new AcAttrQuery();
        query.setDataSource(getDataSource());
        query.setSql();
        List<ClassSchemeItem> results = query.getAcAttrCsCsi(acAttrIdSeq);
        return results;
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


    Collection contacts = jdbcAdminComponentDAO.getContacts("0B244855-6696-5A67-E044-0003BA8EB8F1");
   System.out.println (contacts.size());

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
        " INSERT INTO sbr.ac_csi_view (ac_csi_idseq, cs_csi_idseq, ac_idseq) " +
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
          " INSERT INTO sbr.designations_view (desig_idseq, ac_idseq, conte_idseq, name, detl_name, lae_name, created_by) " +
          " (select ?, ?, ?, PREFERRED_NAME, 'USED_BY', 'ENGLISH', ? from sbr.admin_components_view where " +
          " ac_idseq = ?)";

        this.setDataSource(ds);
        this.setSql(insertSql);
        declareParameter(new SqlParameter("desig_idseq", Types.VARCHAR));
        declareParameter(new SqlParameter("ac_idseq", Types.VARCHAR));
        declareParameter(new SqlParameter("conte_idseq", Types.VARCHAR));
          declareParameter(new SqlParameter("created_by", Types.VARCHAR));
        declareParameter(new SqlParameter("ac_idseq", Types.VARCHAR));
        compile();
      }

      protected int designate(
        String contextIdSeq,
        List acIdList,
        String createdBy){

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
                  createdBy,
                  ""
               };
        int total = 0;
        int res = 0;
        Iterator it = acIdList.iterator();
        while (it.hasNext()){
            obj[0] = generateGUID();
            obj[1] = (String)it.next();
            obj[4] = obj[1];
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
        " DELETE FROM sbr.ac_csi_view WHERE ac_csi_idseq = ? ";

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
        "SELECT csi.long_name csi_name, csi.csitl_name, csi.csi_idseq, " +
        "       cscsi.cs_csi_idseq, cs.preferred_definition, cs.long_name, " +
        "        accsi.ac_csi_idseq, cs.cs_idseq, cs.version, cs.cs_id, csi.csi_id, csi.version csi_version  " +
        " FROM sbr.ac_csi_view accsi, sbr.cs_csi_view cscsi, " +
        "      sbr.cs_items_view csi, sbr.classification_schemes_view cs  " +
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
      csito.setCsVersion(new Float(rs.getString(9)));
      csito.setCsID(rs.getString(10));
      csito.setCsiId(new Integer(rs.getString(11)));
      csito.setCsiVersion(new Float(rs.getString(12)));

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
        " FROM sbr.reference_documents_view ref, sbr.contexts_view con" +
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
      
      String url = getURLWithProtocol(rs.getString(5));
      
      refDoc.setUrl(getURLWithProtocol(url));
      refDoc.setDocText(rs.getString(6));
      refDoc.setDisplayOrder(rs.getInt(9));

      ContextTransferObject contextTransferObject = new ContextTransferObject();
      contextTransferObject.setConteIdseq(rs.getString(7)); //CONTE_IDSEQ
      contextTransferObject.setName(rs.getString(8)); // CONTEXT_NAME
      refDoc.setContext(contextTransferObject);
      return refDoc;
    }
  }
  
  private String getURLWithProtocol(String incompleteURL)  {
	  
	  if (incompleteURL == null) return null;
	  String urlWithProtocol = incompleteURL;
	  
	  try {
		new URL(incompleteURL);
	} catch (MalformedURLException e) {
		urlWithProtocol = "http://"+incompleteURL.trim();
	}
	
	return urlWithProtocol;
  }
  
  /**
   * Inner class to get all ReferenceDocuments that belong to
   * the specified Admin Component
   */
  class ReferenceAttachmentsQuery extends MappingSqlQuery {
    ReferenceAttachmentsQuery(DataSource ds) {
    super( ds,
      "SELECT refb.name, refb.mime_type, refb.doc_size, refb.DATE_CREATED" +
        " FROM sbr.reference_blobs_view refb" +
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
                               +"       ,csi.long_name csi_name"
                               +"       ,csitl_name "
                               +"       ,csi.preferred_definition description "
                               +"       ,csc.cs_csi_idseq "
                               +"       ,cs.preferred_name "
                               +"FROM   sbr.cs_items_view csi "
                               +"      ,sbr.cs_csi_view csc "
                               +"      ,sbr.classification_schemes_view cs "
                               +"WHERE csi.csi_idseq = csc.csi_idseq "
                               +"AND cs.CONTE_IDSEQ = ? "
                               +"AND csc.cs_idseq = cs.cs_idseq "
                               +"AND   cs.preferred_name in( 'CRF_DISEASE','Phase' ) "
                               +"ORDER BY upper(csi.long_name) " );
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
          " cs.LONG_NAME, cscsi.LABEL from  sbr.classification_schemes_view cs, "
          + " sbr.cs_items_view i , sbr.cs_csi_view cscsi "
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
          " from  sbr.classification_schemes_view cs, "
          + " sbr.cs_items_view i , sbr.cs_csi_view cscsi "
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
        " DELETE FROM sbr.ac_csi_view WHERE cs_csi_idseq = ? and ac_idseq = ? ";

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
        "select cs_idseq, cs_preffered_name, cs_long_name, cstl_name, "
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
        csiTO.setClassSchemeType(rs.getString("cstl_name"));
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
   private class CSCSIsHierByContextQuery extends MappingSqlQuery {
      CSCSIsHierByContextQuery (DataSource ds) {
       super(
         ds, "select cs_idseq, cs_preffered_name, cs_long_name, cstl_name, "
         + "CS_PREFFRED_DEFINITION, "
         + "csi_idseq, csi_name, csitl_name, csi_description, "
         + "cs_csi_idseq, csi_level, parent_csi_idseq, cs_conte_idseq "
         + " from SBREXT.BR_CS_CSI_HIER_VIEW_EXT "
         + " where CS_ASL_NAME = 'RELEASED' "
         + " and CSTL_NAME != 'Publishing' "
         + " and cs_conte_idseq = ? "
         + " order by CSI_LEVEL, upper(cs_long_name), upper(csi_name)"
         );
        declareParameter(new SqlParameter("CONTE_IDSEQ", Types.VARCHAR));
        compile();
     }


     protected Object mapRow( ResultSet rs, int rownum) throws SQLException {

         ClassSchemeItem csiTO = new CSITransferObject();
         csiTO.setCsIdseq(rs.getString("cs_idseq"));
         csiTO.setClassSchemeType(rs.getString("cstl_name"));
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
      protected List getCSCSIs(String contextIdseq) {
         Object[] obj =
           new Object[] {
              contextIdseq
           };

         return execute(obj);
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

   private class CSCSIsHierQueryByTypeAndContext extends MappingSqlQuery {

     CSCSIsHierQueryByTypeAndContext (DataSource ds) {
       super(
         ds, "select cs_idseq, cs_preffered_name, cs_long_name, "
         + "CS_PREFFRED_DEFINITION, "
         + "csi_idseq, csi_name, csitl_name, csi_description, "
         + "cs_csi_idseq, csi_level, parent_csi_idseq, cs_conte_idseq "
         + " from SBREXT.BR_CS_CSI_HIER_VIEW_EXT "
         + " where upper(CSTL_NAME) =upper(?) and upper(CSITL_NAME)=upper(?) "
         + " and CS_ASL_NAME = 'RELEASED' "
         + " and CSTL_NAME != 'Publishing' "
         + " and cs_conte_idseq = ? "
         + " order by CSI_LEVEL, upper(cs_long_name), upper(csi_name)");

       declareParameter(new SqlParameter("conte_idseq", Types.VARCHAR));
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
       String csType, String csiType, String contextIdseq) {


       Object[] obj =
         new Object[] {
           csType, csiType, contextIdseq
         };

       return execute(obj);
     }

   }

   /**
       *
       * @param cscsiIdseq cscsi idseq
       * @param regStatus the registration status
       * @return true if there are admin component registered under this cscsi
       *  with the given registration status
       */
   public boolean hasRegisteredAC(String cscsiIdseq, String regStatus){
      HasACRegistered hasAC = new HasACRegistered(this.getDataSource());
      return hasAC.executeHasACRegistered(cscsiIdseq, regStatus);

   }
   /**
    * Inner class that accesses database to delete a question.
    */
   private class HasACRegistered extends StoredProcedure {
     public HasACRegistered(DataSource ds) {
       super(ds, "sbrext_cs_details.has_registered_ac");
       declareParameter(new SqlParameter("p_par_cs_csi_idseq", Types.VARCHAR));
       declareParameter(new SqlParameter("p_reg_status", Types.VARCHAR));
       declareParameter(new SqlOutParameter("p_is_registered", Types.VARCHAR));
       declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
       declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));

       compile();
     }

     public boolean executeHasACRegistered(String cscsiIdseq, String regStatus) {
       Map in = new HashMap();
       in.put("p_par_cs_csi_idseq", cscsiIdseq);
       in.put("p_reg_status", regStatus);

       Map out = execute(in);
       String returnCode = (String) out.get("p_is_registered");



       return returnCode.equals("1");
     }
   }

    /**
     * Inner class to check if all AC are in the specified context.
     */
    private class CheckACDesignationQuery extends MappingSqlQuery {
      CheckACDesignationQuery(DataSource ds) {
          super();
          setDataSource(ds);
      }

      public  boolean isAllACDesignatedToContext(List acIdList, String contextIdSeq) {
        setSql(acIdList, contextIdSeq);
        List retList = execute();
        if (retList == null || retList.isEmpty()){
            return false;
        }

        if (retList.containsAll(acIdList)){
            return true;
        }
        else{
           return false;
        }
      }


      public void setSql(List acIdList, String contextIdSeq) {
       String acIdListStr = getDelimetedIdSeq(acIdList, ", ");
       System.out.println("acIdListStr=" + acIdListStr);
        super.setSql(
          "SELECT ac_idseq from sbr.designations_view where conte_idseq= '" + contextIdSeq + "' " +
          " and detl_name='USED_BY' and lae_name='ENGLISH' and ac_idseq in (" +
          acIdListStr + ")" );

        compile();

      }

      protected Object mapRow(
        ResultSet rs,
        int rownum) throws SQLException {
        String acIdSeq = rs.getString(1);
        return acIdSeq;
      }
    }

    /**
     * Inner class that queries the context for the
     * administered component
     */
    class ContextQuery extends MappingSqlQuery {
      ContextQuery() {
        super();
      }

      public void setSql() {
        super.setSql(
          "select con.CONTE_IDSEQ, con.DESCRIPTION, con.NAME, con.PAL_NAME, con.LL_NAME, con.language "+
    " from sbr.contexts_view con, sbr.admin_components_view ac "+
    " where ac.CONTE_IDSEQ = con.CONTE_IDSEQ "+
    " and ac.ac_idseq = ?");

        declareParameter(new SqlParameter("AC_IDSEQ", Types.VARCHAR));
      }

      protected Context getContext(String acIdSeq) {
          Object[] params = new Object[]{acIdSeq};
          List results = execute(params);
          Context context = null;
          if (results.size()>0) {
              context = (Context)results.get(0);
          }
          return context;
      }

      protected Object mapRow(
        ResultSet rs,
        int rownum) throws SQLException {
        ContextTransferObject cto = new ContextTransferObject();

        cto.setConteIdseq(rs.getString(1));
        cto.setName(rs.getString(3));
        cto.setDescription(rs.getString(2));
        cto.setPalName(rs.getString(4));
        cto.setLlName(rs.getString(5));
        cto.setLanguage(rs.getString(6));

        return cto;
      }
    }

    private class DesignationQuery extends MappingSqlQuery {
      DesignationQuery() {
        super();
      }

      public void setSql(String type) {
       if (type== null){
        super.setSql(
          "select des.desig_idseq, des.name, des.detl_name, des.lae_name, con.CONTE_IDSEQ, con.NAME, con.PAL_NAME "+
            " from sbr.contexts_view con, sbr.designations_view des "+
            " where des.CONTE_IDSEQ = con.CONTE_IDSEQ "+
            " and des.ac_idseq = ?");

        declareParameter(new SqlParameter("AC_IDSEQ", Types.VARCHAR));
       }else{
           super.setSql(
             "select des.desig_idseq, des.name, des.detl_name, des.lae_name, con.CONTE_IDSEQ, con.NAME, con.PAL_NAME "+
           " from sbr.contexts_view con, sbr.designations_view des "+
           " where des.CONTE_IDSEQ = con.CONTE_IDSEQ "+
           " and des.ac_idseq = ? and des.detl_name=?");
           declareParameter(new SqlParameter("AC_IDSEQ", Types.VARCHAR));
           declareParameter(new SqlParameter("detl_name", Types.VARCHAR));
       }
      }

      protected  List<Designation> getDesignations(String acIdSeq, String type) {
          Object[] params;
          if (type == null){
               params = new Object[]{acIdSeq};
          }else{
               params = new Object[]{acIdSeq, type};
          }
          List results = execute(params);
          return (List<Designation>)results;
      }

      protected Object mapRow(
        ResultSet rs,
        int rownum) throws SQLException {
        DesignationTransferObject dto = new DesignationTransferObject();

        dto.setDesigIDSeq(rs.getString(1));
        dto.setName(rs.getString(2));
        dto.setType(rs.getString(3));
        dto.setLanguage(rs.getString(4));
        Context  cto = new ContextTransferObject();
        cto.setConteIdseq(rs.getString(5));
        cto.setName(rs.getString(6));
        dto.setContext(cto);

        return dto;
      }
    }//end of private class


    private class DefinitionQuery extends  MappingSqlQuery {
      DefinitionQuery() {
        super();
      }

      public void setSql() {
        super.setSql(
          " select def.defin_idseq, def.definition,  def.defl_name, " +
          " def.lae_name, con.CONTE_IDSEQ, con.NAME, con.PAL_NAME " +
          " from sbr.contexts_view con, sbr.definitions_view def " +
          " where def.CONTE_IDSEQ = con.CONTE_IDSEQ " +
          " and def.ac_idseq = ? ");

        declareParameter(new SqlParameter("AC_IDSEQ", Types.VARCHAR));
      }

      protected  List<Definition> getDefinitions(String acIdSeq) {
          Object[] params = new Object[]{acIdSeq};
          List results = execute(params);
          return (List<Definition>)results;
      }

      protected Object mapRow(
        ResultSet rs,
        int rownum) throws SQLException {
        Definition dto = new DefinitionTransferObject();
        //fixing
        dto.setId(rs.getString(1));
        dto.setDefinition(rs.getString(2));
        dto.setType(rs.getString(3));
        dto.setLanguage(rs.getString(4));
        //dto.setOrigin();
        Context  cto = new ContextTransferObject();
        cto.setConteIdseq(rs.getString(5));
        cto.setName(rs.getString(6));
        dto.setContext(cto);

        return dto;
      }
    }//end of private class


    private class AcAttrQuery extends MappingSqlQuery {
       AcAttrQuery() {
         super();
       }

       public void setSql() {
         super.setSql( "SELECT csi.long_name csi_name, csi.csitl_name, csi.csi_idseq, " +
         "               cscsi.cs_csi_idseq, cs.preferred_definition, cs.long_name, "+
         "                ext.aca_idseq, cs.cs_idseq, cs.version , csi.preferred_definition description, " +
         "			cs.cs_id, csi.csi_id, csi.version csi_version " +
         "        FROM sbrext.ac_att_cscsi_view_ext ext, sbr.cs_csi_view cscsi, " +
         "             sbr.cs_items_view csi, sbr.classification_schemes_view cs  " +
         "        WHERE ext.ATT_IDSEQ = ? " +
         "        AND   ext.cs_csi_idseq = cscsi.cs_csi_idseq " +
         "        AND   cscsi.csi_idseq = csi.csi_idseq " +
         "        AND   cscsi.cs_idseq = cs.cs_idseq " +
         "        ORDER BY upper(csi.long_name)  ");
           declareParameter(new SqlParameter("ATT_IDSEQ", Types.VARCHAR));
       }

       protected List<ClassSchemeItem> getAcAttrCsCsi(String acAttrIdSeq) {
           Object[] params = new Object[]{acAttrIdSeq};
           List<ClassSchemeItem>  results = execute(params);
           return results;
       }

       protected Object mapRow(
         ResultSet rs,
         int rownum) throws SQLException {
         ClassSchemeItem csito = new CSITransferObject();

           csito.setClassSchemeItemName(rs.getString(1));
           csito.setClassSchemeItemType(rs.getString(2));
           csito.setCsiIdseq(rs.getString(3));
           csito.setCsCsiIdseq(rs.getString(4));
           csito.setClassSchemeDefinition(rs.getString(5));
           csito.setClassSchemeLongName(rs.getString(6));
           csito.setCsIdseq(rs.getString(8));
           csito.setCsVersion(new Float(rs.getString(9)));
           csito.setCsiDescription(rs.getString("description"));
           csito.setCsID(rs.getString("cs_id"));
           csito.setCsiId(new Integer(rs.getString("csi_id")));
           csito.setCsiVersion(new Float(rs.getString("csi_version")));

         return csito;
       }
     }




    private String getDelimetedIdSeq(List idSeqList, String delimiter){
        if (idSeqList==null || idSeqList.isEmpty()){
            return "";
        }

        StringBuffer sbuf = new StringBuffer();
        String delimted = null;
        Iterator it = idSeqList.iterator();
        while (it.hasNext()){
            String  idseq = (String)it.next();
             sbuf.append(delimiter).append("'").append(idseq).append("'");
        }
        //System.out.println("subString = "  + sbuf.substring(1) );
        return sbuf.substring(delimiter.length());
    }

   class PersonContactByACIdQuery extends MappingSqlQuery {
      String last_accId = null;
      Contact currentContact = null;
      List contactList = new ArrayList();
      Person currPerson = null;

     PersonContactByACIdQuery() {
       super();
     }

     public void setQuerySql(String acidSeq) {
       String querySql = " SELECT acc.acc_idseq, acc.org_idseq, acc.per_idseq, acc.contact_role,"
       +" per.LNAME, per.FNAME, addr.CADDR_IDSEQ,"
       + " addr.ADDR_LINE1, addr.ADDR_LINE2, addr.CADDR_IDSEQ, addr.CITY, addr.POSTAL_CODE, addr.STATE_PROV "
      + "  FROM sbr.ac_contacts_view acc, sbr.persons_view per, sbr.contact_addresses_view addr "
        + " where  acc.ac_idseq = '"+ acidSeq +"' and "
      + " acc.per_idseq = per.per_idseq  and addr.PER_IDSEQ = per.PER_IDSEQ "
      + "   ORDER BY acc.acc_idseq, acc.rank_order ";
       super.setSql(querySql);
     }


     protected Object mapRow( ResultSet rs,  int rownum) throws SQLException {
        String accId = rs.getString("acc_idseq");

        Address address = new AddressTransferObject();
        address.setAddressLine1(rs.getString("addr_line1"));
        address.setAddressLine2(rs.getString("addr_line2"));
        address.setId(rs.getString("CADDR_IDSEQ"));
        address.setCity(rs.getString("city"));
        address.setPostalCode(rs.getString("POSTAL_CODE"));
        address.setState(rs.getString("STATE_PROV"));

        String personId = rs.getString("per_idseq");

        if (currPerson == null || !currPerson.getId().equals(personId)) {
           currPerson = new PersonTransferObject();
           currPerson.setFirstName(rs.getString("fname"));
           currPerson.setLastName(rs.getString("lname"));
           currPerson.setId(rs.getString("per_idseq"));
           currPerson.setAddresses(new ArrayList());
        }

        currPerson.getAddresses().add(address);

        if (currentContact == null || !currentContact.getIdseq().equals(accId)) {
           currentContact = new ContactTransferObject();
           currentContact.setIdseq(accId);
           currentContact.setContactRole(rs.getString("contact_role"));
           contactList.add(currentContact);
        }
        currentContact.setPerson(currPerson);

       return currentContact;
     }

      protected List getPersonContacts(String acIdSeq) {
        setQuerySql(acIdSeq);
        this.execute();
        return contactList;
      }
   }

   class ContactCommunicationsQuery extends MappingSqlQuery {
   ContactCommunicationsQuery() {
     super();
   }

   public void setQuerySql(String idType, String orgIdSeq) {
    String querySql = " select cc.CCOMM_IDSEQ, cc.CTL_NAME, cc.CYBER_ADDRESS, " +
    " cc.RANK_ORDER " +
    " from sbr.contact_comms_view cc "
    + " where "+ idType +" = '"+ orgIdSeq +"'"
    + " and ( CTL_NAME='PHONE' OR CTL_NAME='EMAIL') "
       + " ORDER BY rank_order";
     super.setSql(querySql);
   }


   protected Object mapRow( ResultSet rs, int rownum) throws SQLException {

     ContactCommunication cc = new ContactCommunicationTransferObject();
     cc.setId(rs.getString("ccomm_idseq"));
     cc.setType(rs.getString("ctl_name"));
     cc.setValue(rs.getString("cyber_address"));
     cc.setRankOrder(rs.getInt("rank_order"));

     return cc;
   }
    protected List<ContactCommunication> getContactCommsbyPerson( String personId) {
      this.setQuerySql("per_idseq", personId);

      return execute();

    }

    protected List<ContactCommunication> getContactCommsbyOrg( String orgId) {
      this.setQuerySql("org_idseq", orgId);
      return execute();

    }
   }

   class OrgContactByACIdQuery extends MappingSqlQuery {
      String last_accId = null;
      Contact currentContact = null;
      List contactList = new ArrayList();
      Organization currOrg = null;

     OrgContactByACIdQuery() {
       super();
     }

     public void setQuerySql(String acidSeq) {
       String querySql = " SELECT acc.acc_idseq, acc.org_idseq, acc.per_idseq, acc.contact_role,"
       +" org.name, addr.CADDR_IDSEQ,"
       + " addr.ADDR_LINE1, addr.ADDR_LINE2, addr.CADDR_IDSEQ, addr.CITY, addr.POSTAL_CODE, addr.STATE_PROV "
      + "  FROM sbr.ac_contacts_view acc, sbr.organizations_view org, sbr.contact_addresses_view addr "
        + " where  acc.ac_idseq = '"+ acidSeq +"' and "
      + " acc.org_idseq = org.org_idseq  and addr.ORG_IDSEQ = ORG.ORG_IDSEQ "
      + "   ORDER BY acc.acc_idseq, acc.rank_order ";
       super.setSql(querySql);
     }


     protected Object mapRow( ResultSet rs,  int rownum) throws SQLException {
        String accId = rs.getString("acc_idseq");

        Address address = new AddressTransferObject();
        address.setAddressLine1(rs.getString("addr_line1"));
        address.setAddressLine2(rs.getString("addr_line2"));
        address.setId(rs.getString("CADDR_IDSEQ"));
        address.setCity(rs.getString("city"));
        address.setPostalCode(rs.getString("POSTAL_CODE"));
        address.setState(rs.getString("STATE_PROV"));

        String orgId = rs.getString("org_idseq");

        if (currOrg == null || !currOrg.getId().equals(orgId)) {
           currOrg = new OrganizationTransferObject();
           currOrg.setName(rs.getString("name"));
           currOrg.setId(rs.getString("org_idseq"));
           currOrg.setAddresses(new ArrayList());
        }

        currOrg.getAddresses().add(address);

        if (currentContact == null || !currentContact.getIdseq().equals(accId)) {
           currentContact = new ContactTransferObject();
           currentContact.setIdseq(accId);
           currentContact.setContactRole(rs.getString("contact_role"));
           contactList.add(currentContact);
        }
        currentContact.setOrganization(currOrg);

       return currentContact;
     }

      protected List getOrgContacts(String acIdSeq) {
        setQuerySql(acIdSeq);
        this.execute();
        return contactList;
      }
   }

}
