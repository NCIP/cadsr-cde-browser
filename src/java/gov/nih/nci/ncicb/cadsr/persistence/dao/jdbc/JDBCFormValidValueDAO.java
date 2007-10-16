package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.FatalException;
import gov.nih.nci.ncicb.cadsr.persistence.jdbc.oracle.ObjectTransformer;
import gov.nih.nci.ncicb.cadsr.persistence.jdbc.oracle.OracleFormValidvalueList;
import gov.nih.nci.ncicb.cadsr.persistence.jdbc.spring.OracleJBossNativeJdbcExtractor;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.util.TimeUtils;
import java.sql.Connection;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleTypes;
import oracle.jdbc.driver.OracleConnection;
import oracle.jdbc.driver.OracleCallableStatement;

//import oracle.jdbc.OracleConnection;
//import oracle.jdbc.OracleCallableStatement;


import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.jdbc.object.SqlUpdate;

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
  public String createFormValidValueComponent(FormValidValue newValidValue, String parentId, String userName)
    throws DMLException {

    // check if the user has the privilege to create valid value
    // This check only need to be at the form level -skakkodi
   /** boolean create = 
      this.hasCreate(newValidValue.getCreatedBy(), "QUEST_CONTENT", 
        newValidValue.getConteIdseq());
    if (!create) {
      DMLException dml = new DMLException("The user does not have the privilege to create valid value.");
      dml.setErrorCode(INSUFFICIENT_PRIVILEGES);
      throw dml;
    }
    **/
    
  
    InsertFormValidValue insertValidValue = new InsertFormValidValue(this.getDataSource());
    Map out = insertValidValue.executInsertCommand(newValidValue,parentId);

    String returnCode = (String) out.get("p_return_code");
    String returnDesc = (String) out.get("p_return_desc");
    String newFVVIdSeq = (String) out.get("p_val_idseq");
    
    if (!StringUtils.doesValueExist(returnCode)) {
        updateValueMeaning(newFVVIdSeq, newValidValue.getFormValueMeaningText(), 
                            newValidValue.getFormValueMeaningDesc(), userName);
      return newFVVIdSeq;
    }
    else{
      DMLException dml =  new DMLException(returnDesc);
      dml.setErrorCode(this.ERROR_CREATEING_VALID_VALUE);
      throw dml;
    }
    
  }

  public void createFormValidValueComponents(List validValues,String parentId)
    throws DMLException {
    
    OracleFormValidvalueList list = null;
    InsertFormValidValues insertValidValues = new InsertFormValidValues(this.getDataSource());

    try
    {
      list = ObjectTransformer.toOracleFormValidvalueList(validValues,parentId);
    }
    catch (Exception e)
    {
      throw new FatalException("Error While crating Oracle Types",e);
    }
    Map out = insertValidValues.executInsertCommand(list);

    String returnCode = (String) out.get("p_return_code");
    String returnDesc = (String) out.get("p_return_desc");
    
   
    if (!StringUtils.doesValueExist(returnCode)) {
      return ;
    }
    else{
      DMLException dml =  new DMLException(returnDesc);
      dml.setErrorCode(this.ERROR_CREATEING_VALID_VALUE);
      throw dml;
    }    
    
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
    int newDisplayOrder, String username) throws DMLException {

    return updateDisplayOrderDirect(validValueId, "ELEMENT_VALUE", 
      newDisplayOrder,username);
  }

    /**
     * Changes the value meaning text in valid_value_att_ext table.
     */
    public int updateValueMeaning(String vvIdSeq, String updatedValueMeaningText, 
                                    String updatedValueMeaningDesc, String userName)
    throws DMLException {
        int count = 0;
        if ( (updatedValueMeaningText==null || updatedValueMeaningText.length()==0) &&
            (updatedValueMeaningDesc==null || updatedValueMeaningDesc.length()==0)){
            //remove this value meaning text
             DeleteValidValuesAtt sqlDeleteValidValuesAtt = 
                                new DeleteValidValuesAtt(this.getDataSource());
             return sqlDeleteValidValuesAtt.deleteValidValueAtt(vvIdSeq);
        }

        UpdateValidValuesAtt sqlUpdateValidValuesAtt = 
                                new UpdateValidValuesAtt(this.getDataSource());
        count = sqlUpdateValidValuesAtt.updateValueMeaning(
            vvIdSeq, updatedValueMeaningText, updatedValueMeaningDesc, userName);
        if (count != 0){
            return count;
        }
        InsertValidValuesAtt sqlInsertValidValuesAtt = new InsertValidValuesAtt(this.getDataSource());
        return sqlInsertValidValuesAtt.insertValueMeaning(
            vvIdSeq, updatedValueMeaningText, updatedValueMeaningDesc,userName);
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
      DMLException dml =  new DMLException(returnDesc);
      dml.setErrorCode(this.ERROR_DELETEING_VALID_VALUE);
      throw dml;
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
      Protocol prot = new ProtocolTransferObject();
      prot.setProtoIdseq("B1EACF79-3F60-3053-E034-0003BA12F5E7");
      //multiple protocols
      List protocols = new ArrayList(1);
      protocols.add(prot);
      form.setProtocols(protocols);  
      Module module = new ModuleTransferObject();
      module.setModuleIdseq("D45A49A8-167D-0422-E034-0003BA0B1A09");
      module.setForm(form);
      Question question = new QuestionTransferObject();
      question.setQuesIdseq("99CD59C6-17B8-3FA4-E034-080020C9C0E0");
      question.setModule(module);
      
      formValidValue.setQuestion(question);
      formValidValue.setVersion(new Float(2.31));
      formValidValue.setLongName("Test ValidValue Long Name 022904 1");
      formValidValue.setPreferredDefinition("Test Valid Value pref def");
      Context conte = new ContextTransferObject();
      conte.setConteIdseq("29A8FB18-0AB1-11D6-A42F-0010A4C1E842");
      formValidValue.setContext(conte);
      formValidValue.setAslName("DRAFT NEW");
      formValidValue.setCreatedBy("Hyun Kim");
      formValidValue.setVpIdseq("99BA9DC8-5B9F-4E69-E034-080020C9C0E0"); 
      formValidValue.setDisplayOrder(100);

      //String res = test.createFormValidValueComponent(formValidValue);
      //System.out.println("\n*****Create Valid Value Result 2: " + res);
      
      //Test a List
      List list = new ArrayList();
      list.add(formValidValue);
      test.createFormValidValueComponents(list,"99CD59C6-17B8-3FA4-E034-080020C9C0E0");
      
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
   /* try {
      int res = test.updateDisplayOrder("D458E178-32A5-7522-E034-0003BA0B1A09", 7);
      System.out.println("\n*****Update Display Order 1: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
    */
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
      String protocolIdSeq = null;
      
      //protocol is no more associated with questions
        /*
      if( sm.getQuestion().getModule().getForm().getProtocol()!=null)
      {
         protocolIdSeq=sm.getQuestion().getModule().getForm().getProtocol().getProtoIdseq();
      }
      */
      
      Object [] obj = 
        new Object[]
          {qcIdseq,
           sm.getVersion().toString(),
           generatePreferredName(sm.getLongName()),
           sm.getLongName(),
           sm.getPreferredDefinition(),
           sm.getContext().getConteIdseq(),
           protocolIdSeq,
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
   * Inner class that accesses database to delete a valid value.
   */
  private class InsertFormValidValue extends StoredProcedure {
    public InsertFormValidValue(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.ins_value");
      declareParameter(new SqlParameter("p_ques_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_version", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_preferred_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_long_name", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_preferred_definition", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_conte_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_proto_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_asl_name", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_vp_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_display_order", Types.NUMERIC));
      
      declareParameter(new SqlOutParameter("p_val_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_qr_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      
      compile();
    }

    public Map executInsertCommand(FormValidValue fvv, String parentId) {
      String protocolIdSeq = null;

    //question is no more associated with protocols.
    /*
      if( fvv.getQuestion().getModule().getForm().getProtocol()!=null)
      {
         protocolIdSeq=fvv.getQuestion().getModule().getForm().getProtocol().getProtoIdseq();
      }      
     */
     
      Map in = new HashMap();
      
      in.put("p_ques_idseq", parentId);
      in.put("p_version", fvv.getVersion().toString());
      in.put("p_preferred_name", fvv.getPreferredName());
      in.put("p_long_name", fvv.getLongName());
      in.put("p_preferred_definition", fvv.getPreferredDefinition());
      in.put("p_conte_idseq", fvv.getContext().getConteIdseq());
      in.put("p_proto_idseq", protocolIdSeq);
      in.put("p_asl_name", fvv.getAslName());
      in.put("p_vp_idseq", fvv.getVpIdseq());
      in.put("p_created_by", fvv.getCreatedBy());
      in.put("p_display_order", new Integer(fvv.getDisplayOrder()));

      Map out = execute(in);
      return out;
    }
  }
  
    /**
   * This Class uses Oracle database objects to save a
   * Collection of VV in one short 
   * Oracle Native jdbc object are used since this is a propritery way
   * to same a collection of records to 9idb
   */
  private class InsertFormValidValues extends StoredProcedure {
  
    static final String insertvalidvaluesSql = "begin sbrext_form_builder_pkg.ins_multi_values(?,?,?); end;";
    static final String oracleCollectionClass = "gov.nih.nci.ncicb.cadsr.persistence.jdbc.oracle.OracleFormValidvalueList";
    
    public InsertFormValidValues(DataSource ds) {
     super(ds,"dummySql");
      getJdbcTemplate().setNativeJdbcExtractor(new OracleJBossNativeJdbcExtractor());
    }

    public Map executInsertCommand(OracleFormValidvalueList fvvs) {
        
        OracleConnection conn =null;
        OracleCallableStatement stmt =null;
        try
        {
          HashMap querymap = new HashMap();
          OracleJBossNativeJdbcExtractor ext = (OracleJBossNativeJdbcExtractor)getJdbcTemplate().getNativeJdbcExtractor();
          conn =(OracleConnection) ext.doGetOracleConnection(getJdbcTemplate().getDataSource().getConnection());
          //For testing outside jboss 
          //OracleConnection conn =(OracleConnection) getJdbcTemplate().getDataSource().getConnection();
          querymap.put("SBREXT.FB_VALIDVALUELIST", Class.forName(oracleCollectionClass));
          conn.setTypeMap(querymap);
          stmt = (OracleCallableStatement)conn.prepareCall(insertvalidvaluesSql); 
          stmt.setORAData(1,fvvs);
          stmt.registerOutParameter(2, Types.VARCHAR);
          stmt.registerOutParameter(3, Types.VARCHAR);
          stmt.execute();
          Object code = stmt.getString(2);
          Object desc = stmt.getString(3);
          HashMap resultmap = new HashMap();
          resultmap.put("p_return_code",code);
          resultmap.put("p_return_desc",desc);
          return resultmap;
        }
        catch (SQLException e)
        {
          throw new DMLException("SqlExcption on bulk valid value insert",e);
        }
        catch (ClassNotFoundException e)
        {
          throw new DMLException("ClassNotFoundException-" +oracleCollectionClass+ "on bulk valid value insert",e);
        }
    }
  }



    /**
     * Inner class that accesses database to create a question and valid value 
     * relationship record in the qc_recs_ext table.
     */
    private class UpdateValidValuesAtt extends SqlUpdate {
      public UpdateValidValuesAtt(DataSource ds) {
        String updateValidValueAttrSql = 
        " update valid_values_att_ext set meaning_text = ?, description_text=?, modified_by=? " +
        " where qc_idseq=?";

        this.setDataSource(ds);
        this.setSql(updateValidValueAttrSql);
        declareParameter(new SqlParameter("meaning_text", Types.VARCHAR));
        declareParameter(new SqlParameter("description_text", Types.VARCHAR));
        declareParameter(new SqlParameter("modified_by", Types.VARCHAR));
        declareParameter(new SqlParameter("qc_idseq", Types.VARCHAR));
        compile();
      }
      protected int updateValueMeaning(String qcIdSeq, String valueMeaningText, 
                                        String valueMeaningDesc,String userName) 
      {
        Object [] obj = 
          new Object[]
            {valueMeaningText,
            valueMeaningDesc,
             userName,
             qcIdSeq};
        
              int res = update(obj);
        return res;
      }
    }
  
    private class InsertValidValuesAtt extends SqlUpdate {
      public InsertValidValuesAtt(DataSource ds) {
        String insertValidValueAttrSql = 
        " insert into valid_values_att_ext (qc_idseq, meaning_text, description_text, created_by) values(?,?, ?, ?)";

        this.setDataSource(ds);
        this.setSql(insertValidValueAttrSql);
        declareParameter(new SqlParameter("qc_idseq", Types.VARCHAR));
        declareParameter(new SqlParameter("meaning_text", Types.VARCHAR));
        declareParameter(new SqlParameter("description_text", Types.VARCHAR));
        declareParameter(new SqlParameter("created_by", Types.VARCHAR));
        compile();
      }
      protected int insertValueMeaning(String qcIdSeq, String valueMeaningText, 
                    String valueMeaningDesc, String userName) 
      {
        Object [] obj = 
          new Object[]
            {qcIdSeq,
             valueMeaningText,
             valueMeaningDesc,
             userName};            
        int res = update(obj);
        return res;
      }
    }


    private class DeleteValidValuesAtt extends SqlUpdate {
      public DeleteValidValuesAtt(DataSource ds) {
        String deleteValidValueAttrSql = 
        " delete from valid_values_att_ext where qc_idseq=?";
        this.setDataSource(ds);
        this.setSql(deleteValidValueAttrSql);
        declareParameter(new SqlParameter("qc_idseq", Types.VARCHAR));
        compile();
      }
      protected int deleteValidValueAtt(String qcIdSeq) 
      {
        Object [] obj = 
          new Object[]
                {qcIdSeq};
        
        int res = update(obj);
        return res;
      }
    }

}
