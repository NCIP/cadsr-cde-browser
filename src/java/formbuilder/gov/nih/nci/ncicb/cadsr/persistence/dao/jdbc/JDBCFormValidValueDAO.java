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

public class JDBCFormValidValueDAO extends JDBCAdminComponentDAO
  implements FormValidValueDAO {
  public JDBCFormValidValueDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Creates a new form valid value component (just the header info).
   *
   * @param <b>newValidValue</b> FormValidValue object
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int createFormValidValueComponent(FormValidValue newValidValue)
    throws DMLException {

    // check if the user has the privilege to create valid value
    boolean create = 
      this.hasCreate(newValidValue.getCreatedBy(), "QUEST_CONTENT", 
        newValidValue.getConteIdseq());
    if (!create) {
      DMLException dml = new DMLException("The user does not have the privilege to create valid value.");
      dml.setErrorCode(INSUFFICIENT_PRIVILEGES);
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

  /**
   * Changes the display order of the specified form valid value. Display order
   * of the other form valid values in the question is also updated accordingly.
   *
   * @param <b>validValueId</b> Idseq of the form valid value component.
   * @param <b>newDisplayOrder</b> New display order of the form valid value.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateDisplayOrder(
    String validValueId,
    int newDisplayOrder) throws DMLException {

    return updateDisplayOrderDirect(validValueId, "ELEMENT_VALUE", 
      newDisplayOrder);
  }

  /**
   * Deletes the specified form valid value and all its associated components.
   * 
   * @param <b>validValueId</b> Idseq of the form valid value component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
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
      question.setQuesIdseq("D4A91DCA-3567-0D59-E034-0003BA0B1A09");
      question.setModule(module);
      
      formValidValue.setQuestion(question);
      formValidValue.setVersion(new Float(2.31));
      formValidValue.setLongName("Test ValidValue Long Name 022904 1");
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
    
    /*     
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

}
