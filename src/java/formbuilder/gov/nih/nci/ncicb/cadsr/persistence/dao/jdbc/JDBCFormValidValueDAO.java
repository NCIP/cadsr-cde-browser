package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.object.SqlUpdate;

import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCFormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCQuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueDAO;

import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import java.util.Collection;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class JDBCFormValidValueDAO extends JDBCBaseDAO
  implements FormValidValueDAO {
  public JDBCFormValidValueDAO(ServiceLocator locator) {
    super(locator);
  }

  public int createFormValidValueComponent(FormValidValue newValidValue)
    throws DMLException {

    // check if the user has the privilege to create valid value
    boolean create = 
      this.hasCreate(newValidValue.getCreatedBy(), "QUEST_CONTENT", 
        newValidValue.getConteIdseq());
    if (!create) {
      new DMLException("The user does not have the privilege to create valid value.");
    }

    InsertQuestContent  insertQuestContent  = 
      new InsertQuestContent (this.getDataSource());
    String qcIdseq = generateGUID(); 
    int res = insertQuestContent.createContent(newValidValue, qcIdseq);
    if (res != 1) {
      throw new DMLException("Did not succeed creating valid value record " + 
        " in the quest_contents_ext table.");
    }
    
    InsertQuestRec  insertQuestRec  = 
      new InsertQuestRec (this.getDataSource());
    String qrIdseq = generateGUID();
    int resRec = insertQuestRec.createContent(newValidValue, qcIdseq, qrIdseq);
    if (resRec == 1) {
      return 1;
    }
    else {
      throw new DMLException("Did not succeed creating question value relationship " +  
        "record in the quest_recs_ext table.");
    }
  }

  public int createFormValidValueComponents(Collection validValues)
    throws DMLException {
    return 0;
  }

  public int updateDisplayOrder(
    String validValueId,
    int newDisplayOrder) throws DMLException {

    // first, get the original display order of valid value which to be updated
    // with new display order
    ParentIdseq query = new ParentIdseq();
    query.setDataSource(getDataSource());
    query.setSql();
    List result = (List)query.execute(validValueId);
    if (result.size() <= 0){
      throw new DMLException("No matching valid value record found whose " +
        "display order to be updated.");
    }
    Map rec = (Map)(result.get(0));
    String qrIdseq = (String) rec.get("QR_IDSEQ");
    String pQcIdseq = (String) rec.get("P_QC_IDSEQ");
    int originalDisplayOrder = 
      Integer.parseInt(rec.get("DISPLAY_ORDER").toString());

    // now, update the display order of the swapped record with the original 
    // display order
    UpdateSwappedRecDispOrder updateRec1 = 
      new UpdateSwappedRecDispOrder(getDataSource());
    int updateCount1 = 
      updateRec1.executeUpdate (originalDisplayOrder, pQcIdseq, newDisplayOrder); 

    // now update the display order of the indicated record with the new display
    // order
    UpdateRecDispOrder updateRec2 = new UpdateRecDispOrder(getDataSource());
    int updateCount2 = 
      updateRec2.executeUpdate (newDisplayOrder, qrIdseq); 
     
    return 1;  // success
  }

  public int deleteFormValidValue(String validValueId)
    throws DMLException {
    
    DeleteFormValidValue deleteValidValue = new DeleteFormValidValue(this.getDataSource());
    Map out = deleteValidValue.executeDeleteCommand(validValueId);

    String returnCode = (String) out.get("p_return_code");
    String returnDesc = (String) out.get("p_return_desc");
    if (!StringUtils.doesValueExist(returnCode)) {
      return 1;
    }
    else{
      throw new DMLException(returnDesc);
    }
  }

  /**
   * Test application
   */
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    JDBCFormValidValueDAO test = new JDBCFormValidValueDAO(locator);
    /*
    try {
      // test createValidValueComponent method.
      // for each test, change long name(preferred name generated from long name)
      FormValidValue formValidValue = new FormValidValueTransferObject();

      Form form = new FormTransferObject();
      form.setFormIdseq("99CD59C5-A8B7-3FA4-E034-080020C9C0E0");
      form.setProtocol(new ProtocolTransferObject(""));  // template does not have protocol
      Module module = new ModuleTransferObject();
      module.setModuleIdseq("D45A49A8-167D-0422-E034-0003BA0B1A09");
      module.setForm(form);
      Question question = new QuestionTransferObject();
      question.setQuesIdseq("D45B4B3B-8D15-0D0C-E034-0003BA0B1A09");
      question.setModule(module);
      
      formValidValue.setQuestion(question);
      formValidValue.setVersion(new Float(2.31));
      formValidValue.setLongName("Test ValidValue Long Name 022804 4");
      formValidValue.setPreferredDefinition("Test Valid Value pref def");
      formValidValue.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
      formValidValue.setAslName("DRAFT NEW");
      formValidValue.setCreatedBy("Hyun Kim");
      formValidValue.setVpIdseq("99BA9DC8-5B9F-4E69-E034-080020C9C0E0"); 
      formValidValue.setDisplayOrder(100);

      int res = test.createFormValidValueComponent(formValidValue);
      System.out.println("\n*****Create Valid Value Result 2: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
        
    // test for deleteQuestion
    try {
      int res = test.deleteFormValidValue("D472B2E9-BB01-21C2-E034-0003BA0B1A09");
      System.out.println("\n*****Delete Valid Value Result 1: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
    */
    // test for updateDisplayOrder
    try {
      int res = test.updateDisplayOrder("D458E178-32A5-7522-E034-0003BA0B1A09", 7);
      System.out.println("\n*****Update Display Order 1: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
  }

  /**
   * Inner class that accesses database to create a valid value record in the
   * quest_contents_ext table.
   */
 private class InsertQuestContent extends SqlUpdate {
    public InsertQuestContent(DataSource ds) {
      String contentInsertSql = 
      " INSERT INTO quest_contents_ext " + 
      " (qc_idseq, version, preferred_name, long_name, preferred_definition, " + 
      "  conte_idseq, proto_idseq, asl_name, created_by, qtl_name, vp_idseq) " +
      " VALUES " +
      " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

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
      declareParameter(new SqlParameter("p_vp_idseq", Types.VARCHAR));
      compile();
    }
    protected int createContent (FormValidValue sm, String qcIdseq) 
    {
      Object [] obj = 
        new Object[]
          {qcIdseq,
           sm.getVersion().toString(),
           generatePreferredName(sm.getLongName()),
           sm.getLongName(),
           sm.getPreferredDefinition(),
           sm.getConteIdseq(),
           sm.getQuestion().getModule().getForm().getProtoIdseq(),
           sm.getAslName(),
           sm.getCreatedBy(),
           "VALID_VALUE",
           sm.getVpIdseq()
          };
      
	    int res = update(obj);
      return res;
    }
  }

  /**
   * Inner class that accesses database to create a question and valid value 
   * relationship record in the qc_recs_ext table.
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
      declareParameter(new SqlParameter("p_pisplay_order", Types.INTEGER));
      declareParameter(new SqlParameter("p_rl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      compile();
    }
    protected int createContent (FormValidValue sm, String qcIdseq, String qrIdseq) 
    {
      Object [] obj = 
        new Object[]
          {qrIdseq, 
           sm.getQuestion().getQuesIdseq(),
           qcIdseq,
           new Integer(sm.getDisplayOrder()),
           "ELEMENT_VALUE",
           sm.getCreatedBy()
          };
      
	    int res = update(obj);
      return res;
    }
  }

  /**
   * Inner class that accesses database to delete a valid value.
   */
  private class DeleteFormValidValue extends StoredProcedure {
    public DeleteFormValidValue(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.remove_value");
      declareParameter(new SqlParameter("p_val_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map executeDeleteCommand(String valueIdseq) {
      Map in = new HashMap();
      in.put("p_val_idseq", valueIdseq);

      Map out = execute(in);

      return out;
    }
  }

  /**
   * Inner class that accesses database to get the display order, parent idseq, and
   * primary key of the record whose display oder is to be updated.
   */
  private class ParentIdseq extends MappingSqlQuery {
    ParentIdseq() {
      super();
    }

    public void setSql() {
      super.setSql("select QR_IDSEQ, P_QC_IDSEQ, DISPLAY_ORDER from QC_RECS_EXT " +
        " where C_QC_IDSEQ = ? and RL_NAME = 'ELEMENT_VALUE' ");
      declareParameter(new SqlParameter("C_QC_IDSEQ", Types.VARCHAR));
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {

      Map out = new HashMap();
      out.put("QR_IDSEQ", rs.getString(1));  // QR_IDSEQ
      out.put("P_QC_IDSEQ", rs.getString(2));  // P_QC_IDSEQ
      out.put("DISPLAY_ORDER", new Integer(rs.getString(3)));  // DISPLAY_ORDER
      return out;
    }
  }

  /**
   * Inner class that accesses database to update the display order of the
   * display order swapped record.
   */
  private class UpdateSwappedRecDispOrder extends SqlUpdate {
    public UpdateSwappedRecDispOrder(DataSource ds) {
      String updateSql = 
      " update qc_recs_ext set display_order = ? where p_qc_idseq = ? and " + 
      " display_order = ? ";
      this.setDataSource(ds);
      this.setSql(updateSql);
      declareParameter(new SqlParameter("original_display_order", Types.INTEGER));
      declareParameter(new SqlParameter("p_qc_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("new_display_order", Types.INTEGER));
      compile();
    }
    protected int executeUpdate (int originalDisplayOrder, String pQcIdseq, 
      int newDisplayOrder) 
    {
      Object [] obj = 
        new Object[]
          {new Integer(originalDisplayOrder), 
           pQcIdseq,
           new Integer(newDisplayOrder)
          };
      
	    int res = update(obj);
      return res;
    }
  }

  /**
   * Inner class that accesses database to update the display order of the
   * selected record.
   */
  private class UpdateRecDispOrder extends SqlUpdate {
    public UpdateRecDispOrder(DataSource ds) {
      String updateSql = 
      " update qc_recs_ext set display_order = ? where qr_idseq = ? ";
      this.setDataSource(ds);
      this.setSql(updateSql);
      declareParameter(new SqlParameter("display_order", Types.INTEGER));
      declareParameter(new SqlParameter("qr_idseq", Types.VARCHAR));
      compile();
    }
    protected int executeUpdate (int displayOrder, String qrIdseq)
    {
      Object [] obj = 
        new Object[]
          {new Integer(displayOrder), 
           qrIdseq
          };
      
	    int res = update(obj);
      return res;
    }
  }
  
}
