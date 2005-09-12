package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.DataElementTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionDAO;
import gov.nih.nci.ncicb.cadsr.resource.DataElement;
import gov.nih.nci.ncicb.cadsr.resource.Form;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.util.StringUtils;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.object.StoredProcedure;

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


public class JDBCQuestionDAO extends JDBCAdminComponentDAO implements QuestionDAO {
  public JDBCQuestionDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all the valid values that belong to the specified question
   *
   * @param questionId corresponds to the question idseq
   *
   * @return valid values that belong to the specified question
   */
  public Collection getValidValues(String questionId) {
    Collection col = new ArrayList();
    ValidValuesForAQuestionQuery_STMT query = new ValidValuesForAQuestionQuery_STMT();
    query.setDataSource(getDataSource());
    query._setSql(questionId);

    return query.execute();

  }

  public Question addValidValues(
    String questionId,
    Collection validValues) throws DMLException {
    return null;
  }

  /**
   * Deletes the specified question and all its associated components.
   *
   * @param <b>questionId</b> Idseq of the question component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int deleteQuestion(String questionId) throws DMLException {
    DeleteQuestion deleteQuestion = new DeleteQuestion(this.getDataSource());
    Map out = deleteQuestion.executeDeleteCommand(questionId);

    String returnCode = (String) out.get("p_return_code");
    String returnDesc = (String) out.get("p_return_desc");

    if (!StringUtils.doesValueExist(returnCode)) {
      return 1;
    }
    else {
       DMLException dml = new DMLException(returnDesc);
       dml.setErrorCode(this.ERROR_DELETEING_QUESTION);
       throw dml;
    }
  }

  /**
   * Changes the display order of the specified question. Display order of the
   * other questions in the module is also updated accordingly.
   * 
   * @param <b>questionId</b> Idseq of the question component.
   * @param <b>newDisplayOrder</b> New display order of the question component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateDisplayOrder(
    String questionId,
    int newDisplayOrder, String username) throws DMLException {

    return updateDisplayOrderDirect(questionId, "MODULE_ELEMENT", 
      newDisplayOrder, username);
  }
  
  public int updateQuestionLongName(
    String questionId,
    String newLongName, String userName) throws DMLException {
    UpdateQuestionLongName  questionLongName  = new UpdateQuestionLongName (this.getDataSource());
    int res = questionLongName.updateLongName(questionId,newLongName, userName);
    System.out.println("result = " +res);
    if (res != 1) {
       DMLException dml = new DMLException("Did not succeed in updateing the long name");
       dml.setErrorCode(this.ERROR_UPDATING_QUESTION);
       throw dml;    
    }
    return 1;
  }

  /**
   * Test application
   */
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    JDBCQuestionDAO test = new JDBCQuestionDAO(locator);
    /*
    Collection result =
      test.getValidValues("D3830147-1454-11BF-E034-0003BA0B1A09");
    System.out.println(test);
    */
    /*
    Iterator iterator = result.iterator();

     try {
       // test createQuestionComponent method.
       // for each test, change long name(preferred name generated from long name)
    
       Form form = new FormTransferObject();
       form.setFormIdseq("99CD59C5-A8B7-3FA4-E034-080020C9C0E0");
       Module module = new ModuleTransferObject();
       module.setModuleIdseq("D45A49A8-167D-0422-E034-0003BA0B1A09");
       module.setForm(form);
       Question question = new QuestionTransferObject();
       question.setModule(module);
       DataElement dataElement = new DataElementTransferObject();
       dataElement.setDeIdseq("29A8FB2C-0AB1-11D6-A42F-0010A4C1E842");
       question.setDataElement(dataElement);
    
       question.setVersion(new Float(2.31));
       question.setLongName("Test Ques Long Name 030204 2");
       question.setPreferredDefinition("Test Ques pref def");
       question.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
       //form.setProtocol(new ProtocolTransferObject(""));  // template does not have protocol
       question.setAslName("DRAFT NEW");
       question.setCreatedBy("Hyun Kim");
       question.setDisplayOrder(101);
       System.out.println(test.createQuestionComponent(question));
     }
     catch (DMLException de) {
       System.out.println("******Printing DMLException*******");
       de.printStackTrace();
       System.out.println("******Finishing printing DMLException*******");
     }*/

    /* try
     {
       int res = test.updateQuestionDEAssociation("A65D6B91-7ADE-4288-E034-0003BA0B1A09"
                     ,"A67221F9-BDCF-4BFB-E034-0003BA0B1A09"
                     ,"Adjacent viscera/vessel"
                     ,"sbrext");
       System.out.println("\n*****Update DE Result 1: " + res);
     }
     catch (DMLException de) {
       de.printStackTrace();
     }*/
    /*
    try
       {
         int res = test.updateQuestionLongName("99CD59C6-2583-3FA4-E034-080020C9C0E0"
                       ,"Lab Unit Type");
      System.out.println("\n*****Update DE Result 1: " + res);
       }
    catch (DMLException de) {
      de.printStackTrace();
    }
    */
    /*
    // test for deleteQuestion
    try {
      int res = test.deleteQuestion("D45B4B3B-8D15-0D0C-E034-0003BA0B1A09");
      System.out.println("\n*****Delete Question Result 1: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
        
    // test for updateDisplayOrder
    /*try {
      int res = test.updateDisplayOrder("D458E178-32A5-7522-E034-0003BA0B1A09", 7);
      System.out.println("\n*****Update Display Order 1: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }*/
    
    // test for updateLongNameDisplayOrderDeIdseq
    //DataElementTransferObject deto = new DataElementTransferObject();
    //deto.setDeIdseq("29A8FB2C-0AB1-11D6-A42F-0010A4C1E842");
    QuestionTransferObject qto = new QuestionTransferObject();
    //qto.setDataElement(deto);
    qto.setLongName("long name test");
    qto.setQuesIdseq("D68C9BC5-BDAB-2F02-E034-0003BA0B1A09");
    qto.setDisplayOrder(2);
    try {
      int res = test.updateQuestionLongNameDispOrderDeIdseq(qto);
      System.out.println("\n*****Updated " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
    
  }

  /**
   * Creates a new question component (just the header info).
   *
   * @param <b>newQuestion</b> Question object
   *
   * @return <b>newQuestion</b> returns Question object
   *
   * @throws <b>DMLException</b>
   */
   public Question createQuestionComponent(Question newQuestion)
    throws DMLException {
    // check if the user has the privilege to create module
    //This need to be done only at the form level-skakkodi
   
   /**
    * boolean create =
      this.hasCreate(
        newQuestion.getCreatedBy(), "QUEST_CONTENT", newQuestion.getConteIdseq());

    if (!create) {
       DMLException dml = new DMLException("The user does not have the privilege to create question.");
       dml.setErrorCode(this.INSUFFICIENT_PRIVILEGES);
       throw dml;
    }
    **/

    InsertQuestContent insertQuestContent =
      new InsertQuestContent(this.getDataSource());
    String qcIdseq = generateGUID();
    int res = insertQuestContent.createContent(newQuestion, qcIdseq);

    if (res != 1) {

       DMLException dml = new DMLException("Did not succeed creating question record in the " +
        " quest_contents_ext table.");
       dml.setErrorCode(this.ERROR_CREATEING_QUESTION);
       throw dml;        
    }

    InsertQuestRec insertQuestRec = new InsertQuestRec(this.getDataSource());
    String qrIdseq = generateGUID();
    int resRec = insertQuestRec.createContent(newQuestion, qcIdseq, qrIdseq);

    if (resRec == 1) {
      newQuestion.setQuesIdseq(qcIdseq);
      return newQuestion;
    }
    else {
       DMLException dml = new DMLException(
        "Did not succeed creating module question relationship " +
        "record in the quest_recs_ext table.");
       dml.setErrorCode(this.ERROR_CREATEING_QUESTION);
       throw dml;      
    }
  }

  public int updateQuestionDEAssociation(
    String questionId,
    String newDEId,
    String username) throws DMLException {
    UpdateQuestionDEAssociation nQuestion = new UpdateQuestionDEAssociation(this.getDataSource());

    Map out = nQuestion.execute(
      questionId,
      newDEId,
      username.toUpperCase());

    if ((out.get("p_return_code")) == null) {
      return 1;
    }
    else {
       DMLException dml = new DMLException((String) out.get("p_return_desc"));
       dml.setErrorCode(this.ERROR_UPDATING_QUESTION);
       throw dml;
    }
  }

  public int updateQuestionDEAssociation(
    String questionId,
    String newDEId,
    String newLongName,
    String username) throws DMLException {

    int ret_val = 0;
    try{
      ret_val = updateQuestionDEAssociation(questionId,newDEId,username);
    }
    catch (DMLException de) {
      ret_val = 0;
      de.printStackTrace();
    }
    System.out.println("After DE"+ret_val);
    try{
      ret_val = updateQuestionLongName(questionId,newLongName, username);
    }
    catch (DMLException de) {
      ret_val = 0;
      de.printStackTrace();
    }
   System.out.println("After LN"+ret_val);
    if (ret_val == 1) {
      return ret_val;
    }
   else
   {
       DMLException dml = new DMLException("Error updating long name or valid value for question");
       dml.setErrorCode(this.ERROR_UPDATING_QUESTION); 
       throw dml;      
    }
  }

  public int createQuestionComponents(Collection questions)
    throws DMLException {
    return 0;
  }

  /**
   * Changes the long name, display order, and de_idseq of a question.
   *
   * @param <b>question</b> the question component.
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int updateQuestionLongNameDispOrderDeIdseq(
    Question question) throws DMLException {

    int res = updateDisplayOrder(question.getQuesIdseq(), question.getDisplayOrder(),question.getModifiedBy());
    if (res != 1) {
       DMLException dml = new DMLException("Did not succeed updating question's display order.");
       dml.setErrorCode(this.ERROR_UPDATING_QUESTION); 
       throw dml;             
    }

    UpdateQuestionLongNameDeIdseq updateQuestionLnDe =
      new UpdateQuestionLongNameDeIdseq(this.getDataSource());
    res = updateQuestionLnDe.updateQuestion(question);

    if (res != 1) {
       DMLException dml = new DMLException("Did not succeed updating question's long name, de idseq.");
       dml.setErrorCode(this.ERROR_UPDATING_QUESTION); 
       throw dml;        
    }

    return 1;
      
  }


  /**
   * Inner class that accesses database to create a question record in the
   * quest_contents_ext table.
   */
  private class InsertQuestContent extends SqlUpdate {
    public InsertQuestContent(DataSource ds) {
      // super(ds, contentInsertSql);
      String contentInsertSql =
        " INSERT INTO quest_contents_ext " +
        " (qc_idseq, version, preferred_name, long_name, preferred_definition, " +
        "  conte_idseq, asl_name, created_by, qtl_name, de_idseq) " +
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
      declareParameter(new SqlParameter("p_asl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      declareParameter(new SqlParameter("p_qtl_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_de_idseq", Types.VARCHAR));
      compile();
    }

    protected int createContent(
      Question sm,
      String qcIdseq) {
      String conextIdSeq = null;
      String deIdseq = null;
      
      if( sm.getDataElement()!=null)
      {
         deIdseq = deIdseq = sm.getDataElement().getDeIdseq();
      }       
      Object[] obj =
        new Object[] {
          qcIdseq, sm.getVersion().toString(),
          generatePreferredName(sm.getLongName()), sm.getLongName(),
          sm.getPreferredDefinition(), sm.getContext().getConteIdseq(),
          sm.getAslName(), sm.getCreatedBy(),
          "QUESTION", deIdseq
        };

      int res = update(obj);

      return res;
    }
  }

  /**
   * Inner class that accesses database to create a module and question
   * relationship record in the qc_recs_ext table.
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
      Question sm,
      String qcIdseq,
      String qrIdseq) {
      Object[] obj =
        new Object[] {
          qrIdseq, sm.getModule().getModuleIdseq(), qcIdseq,
          new Integer(sm.getDisplayOrder()), "MODULE_ELEMENT", sm.getCreatedBy()
        };

      int res = update(obj);

      return res;
    }
  }

  /**
   * Inner class that accesses database to get all the questions that belong to
   * the specified module
   */
  class ValidValuesForAQuestionQuery_STMT extends MappingSqlQuery {
    ValidValuesForAQuestionQuery_STMT() {
      super();
    }

    public void _setSql(String idSeq) {
      super.setSql(
        "SELECT * FROM SBREXT.FB_VALID_VALUES_VIEW where QUES_IDSEQ = '" + idSeq + "'");
//       declareParameter(new SqlParameter("QUESTION_IDSEQ", Types.VARCHAR));
    }
   /**
    * 3.0 Refactoring- Removed JDBCTransferObject
    */
    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
          FormValidValue fvv = new FormValidValueTransferObject();
          fvv.setValueIdseq(rs.getString(1));     // VV_IDSEQ
          fvv.setVpIdseq(rs.getString(8));        // VP_IDSEQ
          fvv.setLongName(rs.getString(9));       // LONG_NAME
          fvv.setDisplayOrder(rs.getInt(14));     // DISPLAY_ORDER
          fvv.setShortMeaning(rs.getString(15));    // Meaning  
          fvv.setVersion(new Float(rs.getString(2))); // VERSION
          //Bug Fix tt#1058
          fvv.setAslName(rs.getString(5));
          fvv.setPreferredDefinition(rs.getString(7));
          ContextTransferObject contextTransferObject = new ContextTransferObject();
          contextTransferObject.setConteIdseq(rs.getString(4)); //CONTE_IDSEQ
          fvv.setContext(contextTransferObject);
          
         return fvv;
    }
  }


  /**
   * Inner class that accesses database to get all the questions that belong to
   * the specified module
   */
  class ValidValuesForAQuestionQuery extends MappingSqlQuery {
    ValidValuesForAQuestionQuery(DataSource ds) {
      super();
      setDataSource(ds);
      setSql();
      compile();
    }

    public void setSql() {
      super.setSql(
        "SELECT * FROM SBREXT.FB_VALID_VALUES_VIEW where QUES_IDSEQ = ? ");
      declareParameter(new SqlParameter("QUESTION_IDSEQ", Types.VARCHAR));
    }
   /**
    * 3.0 Refactoring- Removed JDBCTransferObject
    */    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
          FormValidValue fvv = new FormValidValueTransferObject();
          fvv.setValueIdseq(rs.getString(1));     // VV_IDSEQ
          fvv.setVpIdseq(rs.getString(8));        // VP_IDSEQ
          fvv.setLongName(rs.getString(9));       // LONG_NAME
          fvv.setDisplayOrder(rs.getInt(14));     // DISPLAY_ORDER
          fvv.setShortMeaning(rs.getString(15));    // Meaning      
         return fvv;
    }
  }

  /**
   * Inner class that accesses database to delete a question.
   */
  private class DeleteQuestion extends StoredProcedure {
    public DeleteQuestion(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.remove_question");
      declareParameter(new SqlParameter("p_ques_idseq", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map executeDeleteCommand(String quesIdseq) {
      Map in = new HashMap();
      in.put("p_ques_idseq", quesIdseq);

      Map out = execute(in);

      return out;
    }
  }
 /**
   * Inner class that copies the source form to a new form
   */
  private class UpdateQuestionDEAssociation extends StoredProcedure {
    public UpdateQuestionDEAssociation(DataSource ds) {
      super(ds, "sbrext_form_builder_pkg.update_de");
      declareParameter(new SqlParameter("p_prm_qc_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_de_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("p_created_by", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_code", Types.VARCHAR));
      declareParameter(new SqlOutParameter("p_return_desc", Types.VARCHAR));
      compile();
    }

    public Map execute(
      String p_prm_qc_idseq,
      String p_de_idseq,
      String p_created_by) {
      Map in = new HashMap();

      in.put("p_prm_qc_idseq", p_prm_qc_idseq);
      in.put("p_de_idseq", p_de_idseq);
      in.put("p_created_by", p_created_by);
      
      Map out = execute(in);
      return out;
    }
  } 
/**
   * Inner class that accesses database to update an longname.
   */
 private class UpdateQuestionLongName extends SqlUpdate {
    public UpdateQuestionLongName(DataSource ds) {
      String longNameUpdateSql = 
      " UPDATE Quest_contents_view_ext " +
      " SET long_name = ?,  modified_by = ? " +
      " WHERE qc_idseq =  ?" ;

      this.setDataSource(ds);
      this.setSql(longNameUpdateSql);
      declareParameter(new SqlParameter("p_long_name", Types.VARCHAR));
      declareParameter(new SqlParameter("modified_by", Types.VARCHAR));
      declareParameter(new SqlParameter("p_qc_idseq", Types.VARCHAR));
      compile();
    }
    protected int updateLongName (String questionId, String newLongName, String userName) 
    {
      Object [] obj = 
        new Object[]
          {
          newLongName,
          userName,
          questionId
          };
      
	    int res = update(obj);
      return res;
    }
  }

  
  /**
   * Inner class that updates long name, display order, and de idseq of
   * the question. 
   * 
   */
  private class UpdateQuestionLongNameDeIdseq extends SqlUpdate {
    public UpdateQuestionLongNameDeIdseq(DataSource ds) {
      String updateSql =
        " UPDATE quest_contents_ext " + 
        " SET DE_IDSEQ = ? , LONG_NAME = ? ,  modified_by = ? " +
        " WHERE QC_IDSEQ = ? ";

      this.setDataSource(ds);
      this.setSql(updateSql);
      declareParameter(new SqlParameter("de_idseq", Types.VARCHAR));
      declareParameter(new SqlParameter("long_name", Types.VARCHAR));
      declareParameter(new SqlParameter("modified_by", Types.VARCHAR));
      declareParameter(new SqlParameter("qc_idseq", Types.VARCHAR));
      compile();
    }

    protected int updateQuestion(
      Question question) {

      String deIdseq = null;
      if (question.getDataElement() != null) {
        deIdseq = question.getDataElement().getDeIdseq();
      }
      
      Object[] obj =
        new Object[] {
          deIdseq,
          question.getLongName(),
          question.getModifiedBy(),
          question.getQuesIdseq()
        };

      int res = update(obj);

      return res;
    }
  }

}
