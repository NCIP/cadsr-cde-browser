package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormInstructionDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc.JDBCFormDAO;
import gov.nih.nci.ncicb.cadsr.dto.FormInstructionTransferObject;

import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormInstruction;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;

import java.util.Collection;
import javax.sql.DataSource;
import java.sql.Types;

import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;

public class JDBCFormInstructionDAO extends JDBCInstructionDAO
  implements FormInstructionDAO {
  public JDBCFormInstructionDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Changes the display order of the specified form instruction. Display order
   * of the other form instructions in the form is also updated accordingly.
   *
   * @param <b>instructionId</b> Idseq of the form instruction.
   * @param <b>newDisplayOrder</b> New display order of the form instruction.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateDisplayOrder(
    String instructionId,
    int newDisplayOrder) throws DMLException {
    
    try {
      swapDisplayOrder(instructionId, "FORM_INSTRUCTION", newDisplayOrder);
    }
    catch (DMLException e) {
      System.out.println("Failed to find the target record to update its display order");
    }
    return 1; //success
  }

  /**
   * Creates a new form instruction component (just the header info).
   *
   * @param <b>formInstr</b> FormInstruction object
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int createFormInstructionComponent(FormInstruction formInstr)
    throws DMLException {
    // check if the user has the privilege to create module
    boolean create =
      this.hasCreate(
        formInstr.getCreatedBy(), "QUEST_CONTENT", formInstr.getConteIdseq());

    if (!create) {
      new DMLException(
        "The user does not have the privilege to create form instruction.");
    }

    InsertQuestContent insertQuestContent =
      new InsertQuestContent(this.getDataSource());
    String qcIdseq = generateGUID();
    int res = insertQuestContent.createContent(formInstr, qcIdseq);

    if (res != 1) {
      throw new DMLException(
        "Did not succeed creating form instruction record in the " +
        " quest_contents_ext table.");
    }

    InsertQuestRec insertQuestRec = new InsertQuestRec(this.getDataSource());
    String qrIdseq = generateGUID();
    int resRec = insertQuestRec.createContent(formInstr, qcIdseq, qrIdseq);

    if (resRec == 1) {
      return 1;
    }
    else {
      throw new DMLException(
        "Did not succeed creating form instrction relationship " +
        "record in the quest_recs_ext table.");
    }
  }

  public Collection getFormInstructions(String formId)
    throws DMLException {
    return null;
  }

  /**
   * Test application
   */
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    JDBCFormInstructionDAO test = new JDBCFormInstructionDAO(locator);
    
    // test for updateDisplayOrder
    try {
      int res = test.updateDisplayOrder("D458E178-32A5-7522-E034-0003BA0B1A09", 7);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }  
    // test createFormInstructionComponent method.
    // for each test, change long name(preferred name generated from long name)
    try {
      JDBCFormDAO formDao = new JDBCFormDAO(locator);
      String formId = "D486B1B4-2AE7-1BB1-E034-0003BA0B1A09";
      Form aForm = formDao.findFormByPrimaryKey(formId);

      FormInstruction newFormInstr = new FormInstructionTransferObject();
      newFormInstr.setForm(aForm);
      newFormInstr.setVersion(new Float(2.31));
      newFormInstr.setLongName("Test Form Instruction Long Name 022904 2");
      newFormInstr.setPreferredDefinition("Test Form instr pref def");
      newFormInstr.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
      newFormInstr.setAslName("DRAFT NEW");
      newFormInstr.setCreatedBy("Hyun Kim");
      newFormInstr.setDisplayOrder(1);
      int res = test.createFormInstructionComponent(newFormInstr);
      System.out.println("\n*****Create Form Instruction: " + res);
      
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
    
  }

  /**
   * Inner class to create a module instruction record in the
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
      FormInstruction sm,
      String qcIdseq) {
      Object[] obj =
        new Object[] {
          qcIdseq, sm.getVersion().toString(),
          generatePreferredName(sm.getLongName()), sm.getLongName(),
          sm.getPreferredDefinition(), sm.getConteIdseq(),
          sm.getForm().getProtoIdseq(), sm.getAslName(), sm.getCreatedBy(),
          "FORM_INSTR"
        };

      int res = update(obj);

      return res;
    }
  }

  /**
   * Inner class to create a form instruction relationship record in the 
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
      FormInstruction sm,
      String qcIdseq,
      String qrIdseq) {
      Object[] obj =
        new Object[] {
          qrIdseq, sm.getForm().getFormIdseq(), qcIdseq,
          new Integer(sm.getDisplayOrder()), "FORM_INSTRUCTION", 
          sm.getCreatedBy()
        };

      int res = update(obj);

      return res;
    }
  }

}
