package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.InstructionDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Form;
import gov.nih.nci.ncicb.cadsr.common.resource.FormInstruction;
import gov.nih.nci.ncicb.cadsr.common.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.util.StringUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;


public abstract class JDBCInstructionDAO extends JDBCAdminComponentDAO
  implements InstructionDAO {
  public JDBCInstructionDAO(ServiceLocator locator) {
    super(locator);
  }

    
  /**
   * Gets all the Instructions that belong to the component
   *
   */
  public List getInstructions(String componentId,String type) {
    InstructionQuery_STMT query = new InstructionQuery_STMT();
    query.setDataSource(getDataSource());
    query.setSql(componentId,type);
    return query.execute();
  }
    
    
  public int createInstruction(Instruction instruction, String parentId,String type, String rlType)
    throws DMLException {
    // check if the user has the privilege to create module
   /**  boolean create =
      this.hasCreate(
        instruction.getCreatedBy(), "QUEST_CONTENT", instruction.getConteIdseq());

    if (!create) {
      new DMLException(
        "The user does not have the privilege to create form instruction.");
    }
    **/
    InsertQuestContent insertQuestContent =
      new InsertQuestContent(this.getDataSource());
    String qcIdseq = generateGUID();
    int res = insertQuestContent.createContent(instruction, qcIdseq,type);

    if (res != 1) {
      throw new DMLException(
        "Did not succeed creating form instruction record in the " +
        " quest_contents_ext table.");
    }

    InsertQuestRec insertQuestRec = new InsertQuestRec(this.getDataSource());
    String qrIdseq = generateGUID();
    int resRec = insertQuestRec.createContent(instruction,parentId,qcIdseq, qrIdseq,rlType);

    if (resRec == 1) {
      return 1;
    }
    else {
      throw new DMLException(
        "Did not succeed creating form instrction relationship " +
        "record in the quest_recs_ext table.");
    }
  }
  
  public int updateInstruction(Instruction newInstruction) throws DMLException {
  
    UpdateInstruction  updateInstruction  = 
      new UpdateInstruction (this.getDataSource());
    int res = updateInstruction.updateInstruction(newInstruction);
    if (res != 1) {
         DMLException dmlExp = new DMLException("Did not succeed updating form record in the " + 
        " quest_contents_ext table.");
	       dmlExp.setErrorCode(ERROR_UPDATING_FORM);
           throw dmlExp;             
    }
    return res;
  }  
    
    
 /**
   * Deletes the Instruction including all the components associated with it.
   *
   * @param <b>formId</b> Idseq of the form component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteInstruction(String instructionId) throws DMLException {
    DeleteInstruction deleteForm = new DeleteInstruction(this.getDataSource());
    Map out = deleteForm.executeDeleteCommand(instructionId);

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
  /**
   * Inner class to create a module instruction record in the
   * quest_contents_ext table.
   */
  private class InsertQuestContent extends SqlUpdate {
    public InsertQuestContent(DataSource ds) {
      String contentInsertSql =
        " INSERT INTO sbrext.quest_contents_view_ext " +
        " (qc_idseq, version, preferred_name, long_name, preferred_definition, " +
        "  conte_idseq, asl_name, created_by, qtl_name) " +
        " VALUES " + " (?, ?, ?, ?, ?,?, ?, ?, ?) ";
      this.setDataSource(ds);
      this.setSql(contentInsertSql);
      declareParameter(new SqlParameter("p_qc_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_version", Types.VARCHAR));
      declareParameter(new SqlParameter("p_preferred_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_long_name", Types.VARCHAR));
      declareParameter(
        new SqlParameter("p_preferred_definition", Types.VARCHAR));
      declareParameter(new SqlParameter("p_conte_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_asl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      declareParameter(new SqlParameter("p_qtl_name", Types.VARCHAR));
      compile();
    }

    protected int createContent(
      Instruction instruction,
      String qcIdseq,String instructionType) {
      Object[] obj =
        new Object[] {
          qcIdseq, instruction.getVersion().toString(),
          generatePreferredName(instruction.getLongName()), instruction.getLongName(),
          instruction.getPreferredDefinition(), instruction.getContext().getConteIdseq(),
          instruction.getAslName(), instruction.getCreatedBy(),
          instructionType
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
        " INSERT INTO sbrext.qc_recs_view_ext " +
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
      Instruction intruction,
      String parentIdseq,
      String qcIdseq,
      String qrIdseq,String rlType) {
      Object[] obj =
        new Object[] {
          qrIdseq,parentIdseq, qcIdseq,
          new Integer(intruction.getDisplayOrder()), rlType, 
          intruction.getCreatedBy()
        };

      int res = update(obj);

      return res;
    }
  }
  
  /**
   * Inner class that accesses database to get all the questions that belong to
   * the specified module
   */
  class InstructionQuery_STMT extends MappingSqlQuery {
    InstructionQuery_STMT() {
      super();
    }

    public void setSql(String idSeq, String type) {
      super.setSql(
        "SELECT * FROM SBREXT.FB_INSTRUCTIONS_VIEW where P_QC_IDSEQ = '" + idSeq + "' and QTL_NAME = '"+ type +"'" );
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      Instruction instruction = new InstructionTransferObject();
      instruction.setIdseq(rs.getString(1));  //QUES_IDSEQ
      instruction.setVersion(rs.getFloat(2));
      instruction.setConteIdseq(rs.getString(4));
      instruction.setPreferredName(rs.getString(6));
      instruction.setPreferredDefinition(rs.getString(7));
      instruction.setLongName(rs.getString(8));   // LONG_NAME
      instruction.setDisplayOrder(rs.getInt(12)); // DISPLAY_ORDER
      instruction.setAslName(rs.getString(5));//Workflow

    return instruction;
   }
  }   

  /**
   * Inner class to update the Form component.
   *
   */
  private class UpdateInstruction extends SqlUpdate {
    public UpdateInstruction(DataSource ds) {
      String updateFormSql =
        " UPDATE sbrext.quest_contents_view_ext SET " +
        " long_name = ? , preferred_definition = ? , modified_by = ? " +
        " WHERE qc_idseq = ? ";

      this.setDataSource(ds);
      this.setSql(updateFormSql);
      declareParameter(new SqlParameter("long_name", Types.VARCHAR));
      declareParameter(new SqlParameter("preferred_definition", Types.VARCHAR));
      declareParameter(new SqlParameter("modified_by", Types.VARCHAR));
      declareParameter(new SqlParameter("qc_idseq", Types.VARCHAR));
      compile();
    }

    protected int updateInstruction(
      Instruction instruction) {

      Object[] obj =
        new Object[] {
           instruction.getLongName(),
           instruction.getPreferredDefinition(),
           instruction.getModifiedBy(),
           instruction.getIdseq()
        };
      int res = update(obj);

      return res;
    }
  }

  /**
   * Inner class that accesses database to delete a form.
   */
  private class DeleteInstruction extends StoredProcedure {
    public DeleteInstruction(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.remove_instr");
      declareParameter(new SqlParameter("p_qc_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map executeDeleteCommand(String crfIdseq) {
      Map in = new HashMap();
      in.put("p_qc_idseq", crfIdseq);

      Map out = execute(in);

      return out;
    }
  }
  
  public static void main(String[] args) {
  
  }
}
