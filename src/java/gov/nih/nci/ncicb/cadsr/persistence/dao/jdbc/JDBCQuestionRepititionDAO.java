package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionRepititionTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.persistence.dao.QuestionRepititionDAO;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.QuestionRepitition;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlUpdate;

public class JDBCQuestionRepititionDAO extends JDBCAdminComponentDAO implements QuestionRepititionDAO {
  public JDBCQuestionRepititionDAO(ServiceLocator locator) {
    super(locator);
  }

    
    public List<QuestionRepitition> getQuestionRepititions(String questionId)
    {
        QuestionRepititionForQuestion query = new QuestionRepititionForQuestion();

        query.setDataSource(getDataSource());
        query.setSql();
        return query.execute(questionId);
    }


    public int updateModuleRepeatCount(
      String moduleId,
      int newCount, String username) throws DMLException {

        UpdateModuleRepeatCount update =
            new UpdateModuleRepeatCount(getDataSource());
        int res = update.updateModule(moduleId,newCount,username);
        if (res == 1)
        {
            return res;
        } else
        {
            DMLException dmlExp =
                new DMLException("Did not updating module form question repitition");
            dmlExp.setErrorCode(ERROR_UPDATING_MODULE_REPITITION);
            throw dmlExp;                
        }        
    }
    
    
    public int deleteRepititionsForQuestion(String questionId)
    {
        DeleteQuestionRepitition deleteAction =
            new DeleteQuestionRepitition(getDataSource());
        return deleteAction.delete(questionId);        
    }
    
    public int createRepitition(String questionId, QuestionRepitition repitition,String username)  
    {

        CreateQuestionRepitition createRepitition =
            new CreateQuestionRepitition(getDataSource());
        FormValidValue fvv =  repitition.getDefaultValidValue();
        String fvvIdSeq = null;
        if(fvv!=null)
            fvvIdSeq = fvv.getValueIdseq();
            
        int res = createRepitition.create(questionId,repitition.getDefaultValue()
            ,fvvIdSeq,repitition.getRepeatSequence(),username);

        if (res == 1)
        {
            return res;
        } else
        {
            DMLException dmlExp =
                new DMLException("Did not succeed creating Question Repitition ");
            dmlExp.setErrorCode(ERROR_CREATEING_QUESTION_REPITITION);
            throw dmlExp;
        }

    }
    
    private class UpdateModuleRepeatCount extends SqlUpdate {
      public UpdateModuleRepeatCount(DataSource ds) {
        String updateSql =
          " UPDATE quest_contents_ext " + 
          " SET REPEAT_NO = ?,  modified_by = ? " +
          " WHERE QC_IDSEQ = ? ";

        this.setDataSource(ds);
        this.setSql(updateSql);
        declareParameter(new SqlParameter("REPEAT_NO", Types.VARCHAR));
        declareParameter(new SqlParameter("modified_by", Types.VARCHAR));
        declareParameter(new SqlParameter("qc_idseq", Types.VARCHAR));
        compile();
      }

      protected int updateModule(
        String moduleId,int count , String username) {

        Object[] obj =
          new Object[] {
            count,
            username,
            moduleId
          };

        int res = update(obj);

        return res;
      }
    }
    
    /**
     * Inner class that to get a Question Repititions for a Question
     */
    class QuestionRepititionForQuestion extends MappingSqlQuery {
      QuestionRepititionForQuestion() {
        super();
      }

      public void setSql() {
        super.setSql("select VALUE,VV_IDSEQ,REPEAT_SEQUENCE from quest_vv_ext where quest_idseq=?");
        declareParameter(new SqlParameter("quest_idseq", Types.VARCHAR));
      }
      
     /**
      *  Refactoring- Removed JDBCTransferObject
      */
      protected Object mapRow(
        ResultSet rs,
        int rownum) throws SQLException {
       QuestionRepitition repeat = new QuestionRepititionTransferObject();
        repeat.setDefaultValue(rs.getString(1));
        repeat.setRepeatSequence(rs.getInt(3));
        String dvvId = rs.getString(2);  
        if(dvvId!=null)
        {
            FormValidValue fvv = new FormValidValueTransferObject();
            fvv.setValueIdseq(dvvId);
            repeat.setDefaultValidValue(fvv);
        }

       return repeat;
      }
    }  
    /**
     * Delete all Question repititions fpr a question
     */
    private class DeleteQuestionRepitition extends SqlUpdate
    {
        public DeleteQuestionRepitition(DataSource ds)
        {
            String sql =
                " delete from quest_vv_ext " + " where QUEST_IDSEQ =? ";

            this.setDataSource(ds);
            this.setSql(sql);
            declareParameter(new SqlParameter("QUEST_IDSEQ", Types.VARCHAR));
            compile();
        }

        protected int delete(String questionId)
        {
            Object[] obj = new Object[]
                { questionId };

            int res = update(obj);

            return res;
        }
    }   
    
    private class CreateQuestionRepitition extends SqlUpdate
    {
        public CreateQuestionRepitition(DataSource ds)
        {
            String sql =
                " INSERT INTO quest_vv_ext " + " (QUEST_IDSEQ,VALUE,VV_IDSEQ,REPEAT_SEQUENCE, created_by) " +
                " VALUES " + " (?,?,?, ?,?) ";

            this.setDataSource(ds);
            this.setSql(sql);
            declareParameter(new SqlParameter("QUEST_IDSEQ", Types.VARCHAR));
            declareParameter(new SqlParameter("VALUE", Types.VARCHAR));
            declareParameter(new SqlParameter("VV_IDSEQ", Types.VARCHAR));
            declareParameter(new SqlParameter("REPEAT_SEQUENCE", Types.INTEGER));
            declareParameter(new SqlParameter("created_by", Types.VARCHAR));
            compile();
        }

        protected int create(String questionId,String value, String vvIdSeq,
                             int repeatSequence,String createdBy)
        {


            Object[] obj = new Object[]
                { questionId,value,vvIdSeq,repeatSequence,createdBy };

            int res = update(obj);

            return res;
        }
    }    
}
