package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueInstructionDAO;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValueInstruction;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.dto.FormValidValueInstructionTransferObject;

import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;

import java.util.Collection;
import javax.sql.DataSource;
import java.sql.Types;

import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;


public class JDBCFormValidValueInstructionDAO extends JDBCInstructionDAO
  implements FormValidValueInstructionDAO {
  public JDBCFormValidValueInstructionDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Changes the display order of the specified form valid value instruction. 
   * Display order of the other instructions in the form valid value is also 
   * updated accordingly.
   *
   * @param <b>instructionId</b> Idseq of the form valid value instruction.
   * @param <b>newDisplayOrder</b> New display order of the form valid value
   *        instruction.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateDisplayOrder(
    String instructionId,
    int newDisplayOrder) throws DMLException {

    try {
      swapDisplayOrder(instructionId, "VALUE_INSTRUCTION", newDisplayOrder);
    }
    catch (DMLException e) {
      System.out.println("Failed to find the target record to update its display order");
    }
    return 1; //success
  }

  /**
   * Creates a new form valid value instruction component (just the header info).
   *
   * @param <b>valueInstr</b> FormValidValueInstruction object
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int createValueInstructionComponent(
    FormValidValueInstruction valueInstr) throws DMLException {
    // check if the user has the privilege to create valid value instruction.
    boolean create =
      this.hasCreate(
        valueInstr.getCreatedBy(), "QUEST_CONTENT", valueInstr.getConteIdseq());

    if (!create) {
      new DMLException(
        "The user does not have the privilege to create valid value instruction.");
    }

    InsertQuestContent insertQuestContent =
      new InsertQuestContent(this.getDataSource());
    String qcIdseq = generateGUID();
    int res = insertQuestContent.createContent(valueInstr, qcIdseq);

    if (res != 1) {
      throw new DMLException(
        "Did not succeed creating valid value instruction record in the " +
        " quest_contents_ext table.");
    }

    InsertQuestRec insertQuestRec = new InsertQuestRec(this.getDataSource());
    String qrIdseq = generateGUID();
    int resRec = insertQuestRec.createContent(valueInstr, qcIdseq, qrIdseq);

    if (resRec == 1) {
      return 1;
    }
    else {
      throw new DMLException(
        "Did not succeed creating valid value instrction relationship " +
        "record in the quest_recs_ext table.");
    }
  }

  public Collection getValueInstructions(String formValidValueId)
    throws DMLException {
    return null;
  }

  /**
   * Test application
   */
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    JDBCFormValidValueInstructionDAO test = 
      new JDBCFormValidValueInstructionDAO(locator);
    
    // test for updateDisplayOrder
    try {
      int res = test.updateDisplayOrder("D458E178-32A5-7522-E034-0003BA0B1A09", 7);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
    
    // test createValueInstructionComponent method.
    // for each test, change long name(preferred name generated from long name)
    try {
      FormValidValueInstruction formValidValueInstr = 
        new FormValidValueInstructionTransferObject();
    
      Form form = new FormTransferObject();
      form.setFormIdseq("99CD59C5-A8B7-3FA4-E034-080020C9C0E0");
      form.setProtocol(new ProtocolTransferObject(""));
      Module module = new ModuleTransferObject();
      module.setModuleIdseq("D45A49A8-167D-0422-E034-0003BA0B1A09");
      module.setForm(form);
      Question question = new QuestionTransferObject();
      question.setQuesIdseq("D4A91DCA-3567-0D59-E034-0003BA0B1A09");
      question.setModule(module);
      FormValidValue formValidValue = new FormValidValueTransferObject();
      formValidValue.setValueIdseq("D4AA6D94-3345-1C0E-E034-0003BA0B1A09");
      formValidValue.setQuestion(question);
      formValidValueInstr.setFormValidValue(formValidValue);
      
      formValidValueInstr.setVersion(new Float(2.31));
      formValidValueInstr.setLongName("Test Valid Value Instr Long Name 030204 1");
      formValidValueInstr.setPreferredDefinition("Test Valid Value Instr pref def");
      formValidValueInstr.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
      formValidValueInstr.setAslName("DRAFT NEW");
      formValidValueInstr.setCreatedBy("Hyun Kim");
      formValidValueInstr.setDisplayOrder(101);
      int res = test.createValueInstructionComponent(formValidValueInstr);
      System.out.println("\n*****Create Valid Value Instruction Result 1: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
  }

  /**
   * Inner class to create a valid value instruction record in the
   * quest_contents_ext table.
   */
  private class InsertQuestContent extends SqlUpdate {
    public InsertQuestContent(DataSource ds) {
      String contentInsertSql =
        " INSERT INTO quest_contents_ext " +
        " (qc_idseq, version, preferred_name, long_name, preferred_definition, " +
        "  conte_idseq, proto_idseq, asl_name, created_by, qtl_name) " +
        " VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
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

    protected int createContent(
      FormValidValueInstruction sm,
      String qcIdseq) {
       Object[] obj =
        new Object[] {
          qcIdseq, sm.getVersion().toString(),
          generatePreferredName(sm.getLongName()), sm.getLongName(),
          sm.getPreferredDefinition(), sm.getConteIdseq(),
          sm.getFormValidValue().getQuestion().getModule().getForm().getProtoIdseq(), 
          sm.getAslName(), sm.getCreatedBy(),
          "VALUE_INSTR"
        };

      int res = update(obj);

      return res;
    }
  }

  /**
   * Inner class to create a valid value instruction relationship record in the 
   * qc_recs_ext table.
   * 
   */
  private class InsertQuestRec extends SqlUpdate {
    public InsertQuestRec(DataSource ds) {
      String questRecInsertSql =
        " INSERT INTO qc_recs_ext " +
        " (qr_idseq, p_qc_idseq, c_qc_idseq, display_order, rl_name, created_by)" +
        " VALUES " + "( ?, ?, ?, ?, ?, ? )";
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

    protected int createContent(
      FormValidValueInstruction sm,
      String qcIdseq,
      String qrIdseq) {
      Object[] obj =
        new Object[] {
          qrIdseq, sm.getFormValidValue().getValueIdseq(), qcIdseq,
          new Integer(sm.getDisplayOrder()), "VALUE_INSTRUCTION", 
          sm.getCreatedBy()
        };

      int res = update(obj);

      return res;
    }
  }

}