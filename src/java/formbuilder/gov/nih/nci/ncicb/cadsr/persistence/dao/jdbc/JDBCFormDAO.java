package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCFormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;


public class JDBCFormDAO extends JDBCAdminComponentDAO implements FormDAO {
  public JDBCFormDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all the forms that match the given criteria
   *
   * @param formName
   * @param protocolIdseq
   * @param contextIdseq
   * @param workflow
   * @param category
   * @param type
   *
   * @return forms that match the criteria.
   */
  public Collection getAllForms(
    String formLongName,
    String protocolIdSeq,
    String contextIdSeq,
    String workflow,
    String categoryName,
    String type,
    String classificationIdseq) {
    FormQuery query = new FormQuery();
    query.setDataSource(getDataSource());
    query.setSql(
      formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName, type,
      classificationIdseq);

    return query.execute();
  }

  /**
   * Gets all the modules that belong to the specified form
   *
   * @param formId corresponds to the form idseq
   *
   * @return modules that belong to the specified form
   */
  public Collection getModulesInAForm(String formId) {
    ModulesInAFormQuery query = new ModulesInAFormQuery();
    query.setDataSource(getDataSource());
    query.setSql();

    return query.execute(formId);
  }

  public String copyForm(
    String sourceFormId,
    Form newForm) throws DMLException {
    CopyForm nForm = new CopyForm(this.getDataSource());

    newForm.setPreferredName(generatePreferredName(newForm.getLongName()));

    Map out = nForm.execute(sourceFormId, newForm);

    if ((out.get("p_return_code")) == null) {
      /*newForm.setFormIdseq((String) out.get("p_new_idseq"));
      return newForm;*/
      return (String) out.get("p_new_idseq");
    }
    else {
      throw new DMLException((String) out.get("p_return_desc"));
    }
  }

  /**
   * Creates a new form component (just the header info).
   *
   * @param <b>sourceForm</b> Form object
   *
   * @return <b>Form</b> Form object
   *
   * @throws <b>DMLException</b>
   */
  public Form createFormComponent(Form sourceForm) throws DMLException {
    // check if the user has the privilege to create module
    boolean create =
      this.hasCreate(
        sourceForm.getCreatedBy(), "QUEST_CONTENT", sourceForm.getConteIdseq());

    if (!create) {
      new DMLException("The user does not have the create form privilege.");
    }

    InsertQuestContent insertQuestContent =
      new InsertQuestContent(this.getDataSource());
    String qcIdseq = generateGUID();
    int res = insertQuestContent.createContent(sourceForm, qcIdseq);

    if (res == 1) {
      sourceForm.setFormIdseq(qcIdseq);
      return sourceForm;
    }
    else {
      throw new DMLException(
        "Did not succeed creating form record in the " +
        " quest_contents_ext table.");
    }
  }

  /**
   * Deletes the entire form including all the components associated with it.
   *
   * @param <b>formId</b> Idseq of the form component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteForm(String formId) throws DMLException {
    DeleteForm deleteForm = new DeleteForm(this.getDataSource());
    Map out = deleteForm.executeDeleteCommand(formId);

    String returnCode = (String) out.get("p_return_code");
    String returnDesc = (String) out.get("p_return_desc");

    if (!StringUtils.doesValueExist(returnCode)) {
      return 1;
    }
    else {
           DMLException dmlExp = new DMLException(returnDesc);
	       dmlExp.setErrorCode(ERROR_DELETE_FORM_FAILED);
           throw dmlExp;
    }
  }

  public int updateFormComponent(Form newForm) throws DMLException {
  
    UpdateFormComponent  updateFormComponent  = 
      new UpdateFormComponent (this.getDataSource());
    int res = updateFormComponent.updateFormFields(newForm);
    if (res != 1) {
      throw new DMLException("Did not succeed updating form record in the " + 
        " quest_contents_ext table.");
    }
    return res;
  }

  public Form addModule(
    String formId,
    Module module) throws DMLException {
    return null;
  }

  public Form addModules(
    String formId,
    Collection modules) throws DMLException {
    return null;
  }

  /**
   * Gets a form by the form idseq
   *
   * @param formId corresponds to the form idseq
   *
   * @return form that has the formId as the primary key
   */
  public Form findFormByPrimaryKey(String formId) throws DMLException {
    Form myForm = null;
    FormByPrimaryKey query = new FormByPrimaryKey();
    query.setDataSource(getDataSource());
    query.setSql();

    List result = (List) query.execute(formId);

    if (result.size() != 0) {
      myForm = (Form) (query.execute(formId).get(0));
    }
    else
    {
      throw new DMLException("No matching record found");
    }

    return myForm;
  }

  public Form findFormByTimestamp(
    String formId,
    Timestamp ts) throws DMLException {
    Form myForm = null;
    FormByTimestamp query = new FormByTimestamp();
    query.setDataSource(getDataSource());
    query.setSql();

    List result = (List) query.execute(new Object[] { ts, formId });

    if (result.size() != 0) {
      myForm = (Form) (query.execute(new Object[] { ts, formId }).get(0));
    }
    else
    {
      throw new DMLException("No matching record found");
    }

    return myForm;
  }

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    JDBCFormDAO formTest = new JDBCFormDAO(locator);
    /*
    FormTransferObject newForm = new FormTransferObject();

    newForm.setLongName("CHRIS TEST 1");
    newForm.setPreferredName(formTest.generatePreferredName(newForm.getLongName()));
//     newForm.setFormIdseq();

    newForm.setPreferredDefinition("This is a test Definition");
    
    ContextTransferObject newContext = new ContextTransferObject(null);
    newContext.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
    newForm.setContext(newContext);
    
    ProtocolTransferObject newProtocol = new ProtocolTransferObject(null);
    newProtocol.setProtoIdseq("9DC06830-EB45-2E3C-E034-080020C9C0E0");
    newForm.setProtocol(newProtocol);

    newForm.setFormCategory("Comments");
    newForm.setFormType("CRF");

    newForm.setAslName("DRAFT NEW");
    
    newForm.setVersion(new Float(1.0));
    
//     newForm.setPreferredName(newForm.getLongName());
    newForm.setCreatedBy("Anonymous");
    
    try {
	    formTest.copyForm("9DC06CFF-6DF7-2B36-E034-080020C9C0E0",  newForm);
    } catch (DMLException e){
	    System.out.println("Failed: " + e);
    } 
    */
    
//     //String formId1 = "9E343E83-5FEB-119F-E034-080020C9C0E0";  //CRF
//     String formId1 = "99CD59C5-A98A-3FA4-E034-080020C9C0E0"; // TEMPLATE

//     try {
//       System.out.println(formTest.findFormByPrimaryKey(formId1));
//     }
//     catch (DMLException e) {
//       System.out.println("Failed to get a form for " + formId1);
//     }

//     try {
//       Form form1 =
//         formTest.findFormByPrimaryKey("D3830147-13E8-11BF-E034-0003BA0B1A09");
//       Form form2 =
//         formTest.findFormByPrimaryKey("D3830147-13E8-11BF-E034-0003BA0B1A09");
//       form2.setPreferredName("testcopyprna1456");
//       form2.setLongName("my form test 456123");
//       form2.setAslName("DRAFT MOD");
//       form2.setConteIdseq("29A8FB18-0AB1-11D6-A42F-0010A4C1E842");
//       System.out.println(form2.getProtocol().getProtoIdseq() + "Conte_idseq");

//       //System.out.println(formTest.copyForm(form1, form2).getFormIdseq());
//     }

//     catch (DMLException e) {
//       System.out.println("Failed to find Form");
//     }

    //formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName,
    // type, classificationIdseq

    /*
       System.out.println(formTest.getAllForms(
         "", "", "99BA9DC8-2095-4E69-E034-080020C9C0E0", "", "", "",
         "99BA9DC8-A622-4E69-E034-080020C9C0E0"));
       System.out.println(formTest.getAllForms(
         "", "", "99BA9DC8-2095-4E69-E034-080020C9C0E0", "", "", "", null));
       System.out.println(
         formTest.getModulesInAForm("99CD59C5-A9A0-3FA4-E034-080020C9C0E0"));
       String formId = "9E343E83-5FEB-119F-E034-080020C9C0E0";
       try {
         Form test = formTest.findFormByPrimaryKey(formId);
         //System.out.println(formTest.findFormByPrimaryKey(formId));
         System.out.println(formTest.findFormByTimestamp(formId, test.getDateModified()));
       }
       catch (DMLException e) {
         System.out.println("Failed to get a form for " + formId);
       }
     */
    /*
       // test createFormComponent method.
       // for each test, change long name(preferred name generated from long name)
       try {
         Form newForm = new FormTransferObject();
         newForm.setVersion(new Float(2.31));
         newForm.setLongName("Test Form Long Name 030104 1");
         newForm.setPreferredDefinition("Test Form pref def");
         newForm.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
         Protocol protocol = new ProtocolTransferObject("TEST");
         protocol.setProtoIdseq("A1204FD0-22B8-3B68-E034-080020C9C0E0");
         newForm.setProtocol(protocol);
         newForm.setAslName("DRAFT NEW");
         newForm.setCreatedBy("Hyun Kim");
         newForm.setFormCategory("Registration");
         newForm.setFormType("CRF");
         System.out.println(formTest.createFormComponent(newForm));
       }
       catch (DMLException de) {
         System.out.println("******Printing DMLException*******");
         de.printStackTrace();
         System.out.println("******Finishing printing DMLException*******");
       }
     */
    /*
       try {
         int res = formTest.deleteForm("D4700045-2FD0-0DAA-E034-0003BA0B1A09");
         System.out.println("\n*****Delete Form Result 1: " + res);
       }
       catch (DMLException de) {
         System.out.println("******Printing DMLException*******");
         de.printStackTrace();
         System.out.println("******Finishing printing DMLException*******");
       }
     */
    Form formX = null;
    try {
      formX = formTest.findFormByPrimaryKey("D4D75662-033F-6DD1-E034-0003BA0B1A09");
    }
    catch (DMLException e) {
      System.out.println("Failed to get a form for " + formX);
    }

    formX.setFormType("CRF");
    formX.getContext().setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
    formX.setAslName("CRF TEMPLATE");
    formX.setPreferredName("Form update test");
    formX.setPreferredDefinition("Form update test definition");
    formX.getProtocol().setProtoIdseq("9B0EAC7E-6A62-0DEB-E034-080020C9C0E0");
    formX.setLongName("Form update test long name");
    formX.setFormCategory("Lab");
    formTest.updateFormComponent(formX);
  }

  /**
   * Inner class that accesses database to get all the modules that belong to
   * the specified form
   */
  class ModulesInAFormQuery extends MappingSqlQuery {
    ModulesInAFormQuery() {
      super();
    }

    public void setSql() {
      super.setSql("SELECT * FROM FB_MODULES_VIEW where CRF_IDSEQ = ? ");
      declareParameter(new SqlParameter("CRF_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return new JDBCModuleTransferObject(rs);
    }
  }

  /**
   * Inner class that accesses database to get a form using the form idseq
   */
  class FormByPrimaryKey extends MappingSqlQuery {
    FormByPrimaryKey() {
      super();
    }

    public void setSql() {
      super.setSql("SELECT * FROM FB_FORMS_VIEW where QC_IDSEQ = ? ");
      declareParameter(new SqlParameter("QC_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return new JDBCFormTransferObject(rs);
    }
  }

  class FormByTimestamp extends MappingSqlQuery {
    FormByTimestamp() {
      super();
    }

    public void setSql() {
      super.setSql(
        "SELECT * FROM FB_FORMS_VIEW where DATE_MODIFIED = ? AND QC_IDSEQ = ?");
      declareParameter(new SqlParameter("date_modified", Types.TIMESTAMP));
      declareParameter(new SqlParameter("qc_idseq", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return new JDBCFormTransferObject(rs);
    }
  }

  /**
   * Inner class that accesses database to get all the forms and modules that
   * match the given criteria
   */
  class FormQuery extends MappingSqlQuery {
    FormQuery() {
      super();
    }

    public void setSql(
      String formLongName,
      String protocolIdSeq,
      String contextIdSeq,
      String workflow,
      String categoryName,
      String type,
      String classificationIdseq) {
      String whereClause =
        makeWhereClause(
          formLongName, protocolIdSeq, contextIdSeq, workflow, categoryName,
          type, classificationIdseq);
      super.setSql("SELECT * FROM FB_FORMS_VIEW " + whereClause);
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      String formName = rs.getString("LONG_NAME");

      return new JDBCFormTransferObject(rs);
    }

    public String makeWhereClause(
      String formName,
      String protocol,
      String context,
      String workflow,
      String category,
      String type,
      String classificationIdseq) {
      String where = "";
      StringBuffer whereBuffer = new StringBuffer("");
      boolean hasWhere = false;

      if (StringUtils.doesValueExist(formName)) {
        String temp = StringUtils.strReplace(formName, "*", "%");

        if (hasWhere) {
          whereBuffer.append(
            " AND UPPER(LONG_NAME) LIKE " + "UPPER('" + temp + "')");
        }
        else {
          whereBuffer.append(
            " WHERE UPPER(LONG_NAME) LIKE " + "UPPER('" + temp + "')");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(protocol)) {
        if (hasWhere) {
          whereBuffer.append(" AND PROTO_IDSEQ ='" + protocol + "'");
        }
        else {
          whereBuffer.append(" WHERE PROTO_IDSEQ ='" + protocol + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(context)) {
        if (hasWhere) {
          whereBuffer.append(" AND CONTE_IDSEQ ='" + context + "'");
        }
        else {
          whereBuffer.append(" WHERE CONTE_IDSEQ ='" + context + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(workflow)) {
        if (hasWhere) {
          whereBuffer.append(" AND WORKFLOW ='" + workflow + "'");
        }
        else {
          whereBuffer.append(" WHERE WORKFLOW ='" + workflow + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(category)) {
        if (hasWhere) {
          whereBuffer.append(" AND CATEGORY_NAME ='" + category + "'");
        }
        else {
          whereBuffer.append(" WHERE CATEGORY_NAME ='" + category + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(type)) {
        if (hasWhere) {
          whereBuffer.append(" AND TYPE ='" + type + "'");
        }
        else {
          whereBuffer.append(" WHERE TYPE ='" + type + "'");
          hasWhere = true;
        }
      }

      if (StringUtils.doesValueExist(classificationIdseq)) {
        if (hasWhere) {
          whereBuffer.append(
            " AND QC_IDSEQ in (select ac_idseq from ac_csi where CS_CSI_IDSEQ ='" +
            classificationIdseq + "')");
        }
        else {
          whereBuffer.append(
            " WHERE QC_IDSEQ in (select ac_idseq from ac_csi where CS_CSI_IDSEQ ='" +
            classificationIdseq + "')");
          hasWhere = true;
        }
      }

      where = whereBuffer.toString();

      return where;
    }
  }

  /**
   * Inner class that accesses database to create a form in the
   * quest_contents_ext table.
   */
  private class InsertQuestContent extends SqlUpdate {
    public InsertQuestContent(DataSource ds) {
      String contentInsertSql =
        " INSERT INTO quest_contents_ext " +
        " (qc_idseq, version, preferred_name, long_name, preferred_definition, " +
        "  conte_idseq, proto_idseq, asl_name, created_by, qtl_name, qcdl_name ) " +
        " VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

      this.setDataSource(ds);
      this.setSql(contentInsertSql);
      declareParameter(new SqlParameter("p_qc_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_version", Types.VARCHAR));
      declareParameter(new SqlParameter("p_preferred_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_long_name", Types.VARCHAR));
      declareParameter(
        new SqlParameter("p_preferred_definition", Types.VARCHAR));
      declareParameter(new SqlParameter("p_conte_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_proto_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_asl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      declareParameter(new SqlParameter("p_qtl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_qcdl_name", Types.VARCHAR));
      compile();
    }

    protected int createContent(
      Form sm,
      String qcIdseq) {

      Object[] obj =
        new Object[] {
          qcIdseq, sm.getVersion().toString(),
          generatePreferredName(sm.getLongName()), sm.getLongName(),
          sm.getPreferredDefinition(), sm.getContext().getConteIdseq(), 
          sm.getProtocol().getProtoIdseq(),
          sm.getAslName(), sm.getCreatedBy(), sm.getFormType(),
          sm.getFormCategory()
        };

      int res = update(obj);

      return res;
    }
  }

  /**
   * Inner class that accesses database to delete a form.
   */
  private class DeleteForm extends StoredProcedure {
    public DeleteForm(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.remove_crf");
      declareParameter(new SqlParameter("p_crf_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map executeDeleteCommand(String crfIdseq) {
      Map in = new HashMap();
      in.put("p_crf_idseq", crfIdseq);

      Map out = execute(in);

      return out;
    }
  }

  /**
   * Inner class that copies the source form to a new form
   */
  private class CopyForm extends StoredProcedure {
    public CopyForm(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.copy_crf");
      declareParameter(new SqlParameter("p_src_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_qtl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_version", Types.VARCHAR));
      declareParameter(new SqlParameter("p_preferred_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_long_name", Types.VARCHAR));
      declareParameter(
        new SqlParameter("p_preferred_definition", Types.VARCHAR));
      declareParameter(new SqlParameter("p_conte_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_proto_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_asl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_new_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map execute(
      String sourceFormId,
      Form newForm) {
      Map in = new HashMap();

      in.put("p_src_idseq", sourceFormId);
      in.put("p_qtl_name", newForm.getFormType());
      in.put("p_version", newForm.getVersion().toString());
      in.put("p_preferred_name", newForm.getPreferredName());
      in.put("p_long_name", newForm.getLongName());
      in.put("p_preferred_definition", newForm.getPreferredDefinition());
      in.put("p_conte_idseq", newForm.getContext().getConteIdseq());
      in.put("p_proto_idseq", newForm.getProtocol().getProtoIdseq());
      in.put("p_asl_name", newForm.getAslName());
      in.put("p_created_by", newForm.getCreatedBy());

      Map out = execute(in);

      return out;
    }
  }

  /**
   * Inner class to update the Form component.
   *
   */
  private class UpdateFormComponent extends SqlUpdate {
    public UpdateFormComponent(DataSource ds) {
      String updateFormSql =
        " UPDATE quest_contents_ext SET " +
        " qtl_name = ?, conte_idseq = ?, asl_name = ?, preferred_name = ?, " + 
        " preferred_definition = ?, proto_idseq = ?, long_name = ?, qcdl_name = ? " + 
        " WHERE qc_idseq = ? ";

      this.setDataSource(ds);
      this.setSql(updateFormSql);
      declareParameter(new SqlParameter("qtl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("conte_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("asl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("preferred_name", Types.VARCHAR));
      declareParameter(new SqlParameter("preferred_definition", Types.VARCHAR));
      declareParameter(new SqlParameter("proto_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("long_name", Types.VARCHAR));
      declareParameter(new SqlParameter("qcdl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("qc_idseq", Types.VARCHAR));
      compile();
    }

    protected int updateFormFields(
      Form form) {
      Object[] obj =
        new Object[] {
          form.getFormType(), form.getContext().getConteIdseq(), form.getAslName(),
          form.getPreferredName(), form.getPreferredDefinition(), 
          form.getProtocol().getProtoIdseq(), form.getLongName(),
          form.getFormCategory(), form.getFormIdseq()
        };
      int res = update(obj);

      return res;
    }
  }

}
