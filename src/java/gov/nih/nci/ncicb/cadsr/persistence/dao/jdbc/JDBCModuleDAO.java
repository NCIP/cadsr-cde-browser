package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ValueDomainTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleDAO;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.object.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.sql.DataSource;


public class JDBCModuleDAO extends JDBCAdminComponentDAO implements ModuleDAO {
  public JDBCModuleDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all the questions that belong to the specified module
   *
   * @param moduleId corresponds to the module idseq
   *
   * @return questions that belong to the specified module
   */
  public Collection getQuestionsInAModule(String moduleId) {
    QuestionsInAModuleQuery_STMT query = new QuestionsInAModuleQuery_STMT();

    query.setDataSource(getDataSource());
    query._setSql(moduleId);

    return query.execute();
  }

  public int createModuleComponentStoredProc(Module sourceModule)
    throws DMLException {
    CreateModuleStoredProc createMod = new CreateModuleStoredProc(this.getDataSource());
    Map out = createMod.executeCreateCommand(sourceModule);

    String returnCode = (String) out.get("p_return_code");
    String returnDesc = (String) out.get("p_return_desc");

    if (!StringUtils.doesValueExist(returnCode)) { 
      // null
      return 1;
    }
    else{
     DMLException dml = new DMLException(returnDesc);
     dml.setErrorCode(this.ERROR_CREATEING_MODULE);
     throw dml;
    }
  }

  /**
   * @inheritDoc
   */
  public String createModuleComponent(Module sourceModule)
    throws DMLException {

    // check if the user has the privilege to create module
    //This check need to be done only at the Form level
  /**
    boolean create = 
      this.hasCreate(sourceModule.getCreatedBy(), "QUEST_CONTENT", 
        sourceModule.getConteIdseq());
    if (!create) {
       DMLException dml = new DMLException("The user does not have the create module privilege.");
       dml.setErrorCode(this.INSUFFICIENT_PRIVILEGES);
       throw dml;
    }
   **/
    sourceModule.setPreferredName(generatePreferredName(sourceModule.getLongName()));

    InsertQuestContent  insertQuestContent  = 
      new InsertQuestContent (this.getDataSource());
    String qcIdseq = generateGUID(); 
    int res = insertQuestContent.createContent(sourceModule, qcIdseq);
    if (res != 1) {
       DMLException dml = new DMLException("Did not succeed creating module record in the " + 
        " quest_contents_ext table.");
       dml.setErrorCode(this.ERROR_CREATEING_MODULE);
       throw dml;        
    }
    
    InsertQuestRec  insertQuestRec  = 
      new InsertQuestRec (this.getDataSource());
    String qrIdseq = generateGUID();
    int resRec = insertQuestRec.createContent(sourceModule, qcIdseq, qrIdseq);
    if (resRec == 1) {
      return qcIdseq;
    }
    else {
       DMLException dml = new DMLException("Did not succeed creating form module relationship " +  
        "record in the quest_recs_ext table.");
       dml.setErrorCode(this.ERROR_CREATEING_MODULE);
       throw dml;          
    }
  }

  public Module addQuestions(
    String moduleId,
    Collection questions) throws DMLException {
    return null;
  }

  /**
   * Deletes the specified module and all its associated components.
   *
   * @param <b>moduleId</b> Idseq of the module component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteModule(String moduleId) throws DMLException {
    DeleteModule deleteMod = new DeleteModule(this.getDataSource());
    Map out = deleteMod.executeDeleteCommand(moduleId);

    String returnCode = (String) out.get("p_return_code");
    String returnDesc = (String) out.get("p_return_desc");
    if (!StringUtils.doesValueExist(returnCode)) {
      return 1;
    }
    else{
      DMLException dmlExp = new DMLException(returnDesc);
      dmlExp.setErrorCode(ERROR_DELETEING_MODULE);    
      throw dmlExp;
    }
  }

  /**
   * Changes the display order of the specified module. Display order of the
   * other modules in the form is also updated accordingly.
   * 
   * @param <b>moduleId</b> Idseq of the module component.
   * @param <b>newDisplayOrder</b> New display order of the module component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateDisplayOrder(
    String moduleId,
    int newDisplayOrder, String username) throws DMLException {

    return updateDisplayOrderDirect(moduleId, "FORM_MODULE", newDisplayOrder, username);
  }
  
  public Module addQuestion(
    String moduleId,
    Question question) throws DMLException {
    return null;
  }

  public int updateModuleLongName(
    String moduleId,
    String newLongName) throws DMLException {
    return 0;
  }



  /**
   * Changes several fields of a module.
   *
   * @param <b>Module</b> Module component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateModuleComponent(Module module) throws DMLException {

    UpdateModuleComponent updateModuleComponent =
      new UpdateModuleComponent(this.getDataSource());
    int res = updateModuleComponent.updateModule(module);

    if (res != 1) {
      DMLException dmlExp = new DMLException("Did not succeed updating module's long name");
      dmlExp.setErrorCode(ERROR_UPDATING_MODULE);    
      throw dmlExp;
    }

    return 1;
  }

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    JDBCModuleDAO test = new JDBCModuleDAO(locator);
    

    System.out.println(test.getQuestionsInAModule("99CD59C5-AB7C-3FA4-E034-080020C9C0E0"));

    
    /*
    Module module = test.findModuleByPrimaryKey("99CD59C5-B04A-3FA4-E034-080020C9C0E0");
    System.out.println(module);
    module.setLongName("test long name");    
    try {
      int res = test.updateModuleComponent(module );
      System.out.println("updated module's long name " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
    */
    /*
    try {
      int res = test.deleteModule("99CD59C5-B206-3FA4-E034-080020C9C0E0");
      System.out.println("\n*****Delete Module Result 1: " + res);
    }
    catch (DMLException de) {
      System.out.println("******Printing DMLException*******");
      de.printStackTrace();
      System.out.println("******Finishing printing DMLException*******");
    }
    */
    /*
    try {
      Module module = new ModuleTransferObject();
      Form form = new FormTransferObject();
      form.setFormIdseq("99CD59C5-A8B7-3FA4-E034-080020C9C0E0");
      module.setForm(form);
      module.setVersion(new Float(2.31));
      module.setPreferredName("test_mod_pref_name");
      module.setLongName("Test Mod Long Name");
      module.setPreferredDefinition("Test Mod pref def");
      module.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
      form.setProtocol(new ProtocolTransferObject(""));
      module.setAslName("DRAFT NEW");
      module.setCreatedBy("Hyun");
      module.setDisplayOrder(4);
   
      int res = test.createModuleComponentStoredProc(module);
      System.out.println("\n*****Create Module Result 1: " + res);
    }
    catch (DMLException de) {
      System.out.println("******Printing DMLException*******");
      de.printStackTrace();
      System.out.println("******Finishing printing DMLException*******");
    }
    */
    /*
    try {
      // test createModuleComponent method.
      // for each test, change long name(preferred name generated from long name)
      Module module = new ModuleTransferObject();
      Form form = new FormTransferObject();
      form.setFormIdseq("99CD59C5-A8B7-3FA4-E034-080020C9C0E0");
      module.setForm(form);
      module.setVersion(new Float(2.31));
      //module.setPreferredName("test_mod_pref_name_new 022704");
      module.setLongName("Test Mod Long Name 022704 1");
      module.setPreferredDefinition("Test Mod pref def");
      module.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
      form.setProtocol(new ProtocolTransferObject(""));
      module.setAslName("DRAFT NEW");
      module.setCreatedBy("Hyun Kim");
      module.setDisplayOrder(7);

      int res = test.createModuleComponent(module);
      System.out.println("\n*****Create Module Result 1: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
    */
    /*
    // test for updateDisplayOrder
    try {
      int res = test.updateDisplayOrder("D458E178-32A5-7522-E034-0003BA0B1A09", 5);
      System.out.println("\n*****Update Display Order 1: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
    */
  }

  /**
   * Inner class that accesses database to delete a module.
   */
  private class DeleteModule extends StoredProcedure {
    public DeleteModule(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.remove_module");
      declareParameter(new SqlParameter("p_mod_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map executeDeleteCommand(String modIdseq) {
      Map in = new HashMap();
      in.put("p_mod_idseq", modIdseq);

      Map out = execute(in);

      return out;
    }
  }

  /**
   * Inner class that accesses database to create a module.
   */
  private class CreateModuleStoredProc extends StoredProcedure {
    public CreateModuleStoredProc(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.ins_module");
      declareParameter(new SqlParameter("p_crf_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_version", Types.VARCHAR));
      declareParameter(new SqlParameter("p_preferred_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_long_name", Types.VARCHAR));
      declareParameter(
        new SqlParameter("p_preferred_definition", Types.VARCHAR));
      declareParameter(new SqlParameter("p_conte_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_proto_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_asl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      declareParameter(new SqlParameter("p_display_order", Types.INTEGER));
      declareParameter(new SqlOutParameter("p_mod_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_qr_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map executeCreateCommand(Module sm) {
      Map in = new HashMap();
      in.put("p_crf_idseq", sm.getForm().getFormIdseq());
      in.put("p_version", sm.getVersion().toString());
      in.put("p_preferred_name", sm.getPreferredName());
      in.put("p_long_name", sm.getLongName());
      in.put("p_preferred_definition", sm.getPreferredDefinition());
      in.put("p_conte_idseq", sm.getContext().getConteIdseq());
      //module is no more associate with protocol.
      //in.put("p_proto_idseq", sm.getForm().getProtocol().getProtoIdseq());
      in.put("p_proto_idseq", null);
      in.put("p_asl_name", sm.getAslName());
      in.put("p_created_by", sm.getCreatedBy());
      in.put("p_display_order", new Integer(sm.getDisplayOrder()));

      Map out = execute(in);

      return out;
    }
  }

  /**
   * Inner class that accesses database to create a module in the
   * quest_contents_ext table.
   */
 private class InsertQuestContent extends SqlUpdate {
    public InsertQuestContent(DataSource ds) {
      // super(ds, contentInsertSql);
      String contentInsertSql = 
      " INSERT INTO quest_contents_ext " + 
      " (qc_idseq, version, preferred_name, long_name, preferred_definition, " + 
      "  conte_idseq, proto_idseq, asl_name, created_by, qtl_name ) " +
      " VALUES " +
      " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

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
      compile();
    }
    protected int createContent (Module sm, String qcIdseq) 
    {
      Object [] obj = 
        new Object[]
          {qcIdseq, 
           sm.getVersion().toString(),
           generatePreferredName(sm.getLongName()),
           sm.getLongName(),
           sm.getPreferredDefinition(),
           sm.getForm().getContext().getConteIdseq(),
           //module is not associate with protocol any more.
           //sm.getForm().getProtocol().getProtoIdseq(),
           null,
           sm.getAslName(),
           sm.getCreatedBy(),
           "MODULE"
          };
      
	    int res = update(obj);
      return res;
    }
  }

  /**
   * Inner class that accesses database to create a form and module relationship
   * record in the qc_recs_ext table.
   */
 private class InsertQuestRec extends SqlUpdate {
    public InsertQuestRec(DataSource ds) {
      String questRecInsertSql = 
      " INSERT INTO qc_recs_ext " +
      " (qr_idseq, p_qc_idseq, c_qc_idseq, display_order, rl_name, created_by)" +  
      " VALUES " + 
      "( ?, ?, ?, ?, ?, ? )";

      this.setDataSource(ds);
      this.setSql(questRecInsertSql);
      declareParameter(new SqlParameter("p_qr_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_qc_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("c_qc_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_display_order", Types.INTEGER));
      declareParameter(new SqlParameter("p_rl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      compile();
    }
    protected int createContent (Module sm, String qcIdseq, String qrIdseq) 
    {
      Object [] obj = 
        new Object[]
          {qrIdseq, 
           sm.getForm().getFormIdseq(),
           qcIdseq,
           new Integer(sm.getDisplayOrder()),
           "FORM_MODULE",
           sm.getCreatedBy()
          };
      
	    int res = update(obj);
      return res;
    }
  }

  /**
   * Inner class that accesses database to get all the questions that belong to
   * the specified module
   */
  class QuestionsInAModuleQuery_STMT extends MappingSqlQuery {
    QuestionsInAModuleQuery_STMT() {
      super();
    }

    public void _setSql(String idSeq) {
      super.setSql(
        "SELECT * FROM SBREXT.FB_QUESTIONS_VIEW where MODULE_IDSEQ = '" + idSeq + "'");
//       declareParameter(new SqlParameter("MODULE_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      Question question = new QuestionTransferObject();
      question.setQuesIdseq(rs.getString(1));  //QUES_IDSEQ
      question.setLongName(rs.getString(9));   // LONG_NAME
      question.setDisplayOrder(rs.getInt(13)); // DISPLAY_ORDER
      question.setAslName(rs.getString(5));//Workflow
      question.setPreferredDefinition(rs.getString(7));
      
      question.setMandatory("Yes".equalsIgnoreCase(rs.getString(21)));
      
      String deIdSeq = rs.getString(8);
      if(deIdSeq!=null)
       {
        DataElementTransferObject dataElementTransferObject =
          new DataElementTransferObject();       
        dataElementTransferObject.setDeIdseq(deIdSeq); // DE_IDSEQ
        dataElementTransferObject.setLongCDEName(rs.getString(15)); // DOC_TEXT 
        dataElementTransferObject.setVersion(new Float(rs.getFloat(16))); // VERSION
        dataElementTransferObject.setLongName(rs.getString(17)); // DE_LONG_NAME
        dataElementTransferObject.setCDEId(Integer.toString(rs.getInt(18)));
        dataElementTransferObject.setAslName(rs.getString("DE_WORKFLOW"));
        question.setDataElement(dataElementTransferObject); 
      
      
        ValueDomainTransferObject valueDomainTransferObject = 
                                         new ValueDomainTransferObject();
        valueDomainTransferObject.setVdIdseq(rs.getString(19)); // VD_IDSEQ
        dataElementTransferObject.setValueDomain(valueDomainTransferObject);
    }
    return question;
   }
  }
  /**
   * Inner class that accesses database to get all the questions that belong to
   * the specified module
   */
  class QuestionsInAModuleQuery extends MappingSqlQuery {
    QuestionsInAModuleQuery() {
      super();
    }

    public void setSql() {
      super.setSql(
        "SELECT * FROM SBREXT.FB_QUESTIONS_VIEW where MODULE_IDSEQ = ? ");
      declareParameter(new SqlParameter("MODULE_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      Question question = new QuestionTransferObject();
      question.setQuesIdseq(rs.getString(1));  //QUES_IDSEQ
      question.setLongName(rs.getString(9));   // LONG_NAME
      question.setDisplayOrder(rs.getInt(13)); // DISPLAY_ORDER
      question.setAslName(rs.getString(5));//Workflow
      String deIdSeq = rs.getString(8);
      if(deIdSeq!=null)
       {
        DataElementTransferObject dataElementTransferObject =
          new DataElementTransferObject();       
        dataElementTransferObject.setDeIdseq(deIdSeq); // DE_IDSEQ
        dataElementTransferObject.setLongCDEName(rs.getString(15)); // DOC_TEXT 
        dataElementTransferObject.setVersion(new Float(rs.getFloat(16))); // VERSION
        dataElementTransferObject.setLongName(rs.getString(17)); // DE_LONG_NAME
        dataElementTransferObject.setCDEId(Integer.toString(rs.getInt(18)));
        dataElementTransferObject.setAslName(rs.getString("DE_WORKFLOW"));
        question.setDataElement(dataElementTransferObject); 
      
      
        ValueDomainTransferObject valueDomainTransferObject = 
                                         new ValueDomainTransferObject();
        valueDomainTransferObject.setVdIdseq(rs.getString(19)); // VD_IDSEQ
        dataElementTransferObject.setValueDomain(valueDomainTransferObject);
    }
    return question;
    }
  }

  /**
   * Finds a module based on the primary key.
   *
   * @param <b>moduleId</b> Idseq of the form component.
   *
   * @return <b>Module</b> Module object representing the module.
   *
   * @throws <b>DMLException</b>
   */
  public Module findModuleByPrimaryKey(String moduleId) throws DMLException {

    Module myModule = null;
    ModuleByPrimaryKey query = new ModuleByPrimaryKey();
    query.setDataSource(getDataSource());
    query.setSql();

    List result = (List) query.execute(moduleId);

    if (result.size() != 0) {
      myModule = (Module) (query.execute(moduleId).get(0));
    }
    else
    {
      DMLException dmlExp = new DMLException("No matching module record found");
      dmlExp.setErrorCode(NO_MATCH_FOUND);    
      throw dmlExp;
    }

    return myModule;
  }

  /**
   * Inner class that to get a module using the module idseq
   */
  class ModuleByPrimaryKey extends MappingSqlQuery {
    ModuleByPrimaryKey() {
      super();
    }

    public void setSql() {
      super.setSql("SELECT * FROM FB_MODULES_VIEW where MOD_IDSEQ = ? ");
      declareParameter(new SqlParameter("MOD_IDSEQ", Types.VARCHAR));
    }
    
   /**
    * 3.0 Refactoring- Removed JDBCTransferObject
    */
    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
     Module module = new ModuleTransferObject();
     module.setModuleIdseq(rs.getString(1));  // MOD_IDSEQ
     module.setLongName(rs.getString(8));     // LONG_NAME
     module.setDisplayOrder(rs.getInt(13));   // DISPLAY_ORDER
     module.setNumberOfRepeats(rs.getInt(14));//repeat_no
     module.setVersion(rs.getFloat(3));
     module.setConteIdseq(rs.getString(4));
     module.setPreferredName(rs.getString(6));
     module.setPreferredDefinition(rs.getString(7));
     return module;
    }
  }

  /**
   * Inner class that updates long name of the question. 
   * 
   */
  private class UpdateModuleComponent extends SqlUpdate {
    public UpdateModuleComponent(DataSource ds) {
      String updateSql =
        " UPDATE quest_contents_ext " + 
        " SET LONG_NAME = ?,  modified_by = ? " +
        " WHERE QC_IDSEQ = ? ";

      this.setDataSource(ds);
      this.setSql(updateSql);
      declareParameter(new SqlParameter("long_name", Types.VARCHAR));
      declareParameter(new SqlParameter("modified_by", Types.VARCHAR));
      declareParameter(new SqlParameter("qc_idseq", Types.VARCHAR));
      compile();
    }

    protected int updateModule(
      Module module) {

      Object[] obj =
        new Object[] {
          module.getLongName(),
          module.getModifiedBy(),
          module.getModuleIdseq()
        };

      int res = update(obj);

      return res;
    }
  }

}

