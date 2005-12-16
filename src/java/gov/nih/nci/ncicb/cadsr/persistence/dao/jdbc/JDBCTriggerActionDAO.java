package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.TriggerActionTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.TriggerActionDAO;
import gov.nih.nci.ncicb.cadsr.resource.Form;

import gov.nih.nci.ncicb.cadsr.resource.FormElement;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.resource.Module;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.Question;
import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import java.util.ArrayList;
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
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;

public class JDBCTriggerActionDAO extends JDBCAdminComponentDAO implements TriggerActionDAO,PersistenceConstants
{
    public JDBCTriggerActionDAO(ServiceLocator locator)
    {
        super(locator);
    }
    
    /**
     * Gets all the TriggerActions for a source
     *
     */
    public List getTriggerActionsForSource(String sourceId) {
      TriggerActionQuery query = new TriggerActionQuery();
      query.setDataSource(getDataSource());
      query.setSql(sourceId);
      return query.execute();
    }    
    
    /**
     * Gets all the Protocols associated with a trigger action
     *
     */
    public List getAllProtocolsForTriggerAction(String triggerActionId) {
      TriggerActionProtocolsQuery query = new TriggerActionProtocolsQuery();
      query.setDataSource(getDataSource());
      query.setSql(triggerActionId);
      return query.execute();
    }
    
    /**
     * Update trigger action by its target and instruction
     *
     */
    public int updateTriggerAction(String triggerId,String targetId
            , String instruction, String modifiedBy) {
      UpdateTriggerAction updateAction = new UpdateTriggerAction(getDataSource());
        return updateAction.update( triggerId,
         targetId, instruction, modifiedBy);

    }    
    
    /**
     * Create new trigger action
     *
     */
    public int createTriggerAction(TriggerAction action, String createdBy) {
      CreateTriggerAction createAction = new CreateTriggerAction(getDataSource());
        return createAction.create( action, createdBy);

    }      
    
    /**
     * delete Trigger action
     *
     */
    public int deleteTriggerAction(String triggerId, String createdBy) {
      DeleteTriggerAction deleteAction = new DeleteTriggerAction(getDataSource());
        return deleteAction.delete(triggerId);
    }      
    
    private  class TriggerActionProtocolsQuery extends MappingSqlQuery {
       TriggerActionProtocolsQuery() {
         super();
       }

       public void setSql(String triggerActionIdSeq) {
         super.setSql(
           "select TA_P_CSI.proto_idseq,PROTO.LONG_NAME, PROTO.PREFERRED_DEFINITION "
             + "from TA_PROTO_CSI_EXT TA_P_CSI, PROTOCOLS_EXT PROTO ,TRIGGERED_ACTIONS_EXT TA "
             + " WHERE  TA_P_CSI.proto_idseq=PROTO.proto_idseq "
             + " AND TA_P_CSI.TA_IDSEQ=TA.TA_IDSEQ "
             +" AND TA.TA_IDSEQ = '"+triggerActionIdSeq+"'"    
           );

       }

       protected Object mapRow(
         ResultSet rs,
         int rownum) throws SQLException {
         Protocol protocol =  new ProtocolTransferObject();
          protocol.setLongName(rs.getString("LONG_NAME"));
          protocol.setPreferredDefinition(rs.getString("PREFERRED_DEFINITION"));
          protocol.setProtoIdseq(rs.getString("proto_idseq"));
        return protocol;
      }
      
     } 
         
    
   private  class TriggerActionQuery extends MappingSqlQuery {
      TriggerActionQuery() {
        super();
      }

      public void setSql(String sourceIdSeq) {
        super.setSql(
         " select TA_IDSEQ, S_QC_IDSEQ, T_QC_IDSEQ, TA_INSTRUCTION, QC_IDSEQ, T_QTL_NAME "
        + " from QUEST_CONTENTS_EXT qc, TRIGGERED_ACTIONS_EXT ta where "
        + "  S_QC_IDSEQ = '"+sourceIdSeq + "'" );      

      }

      protected Object mapRow(
        ResultSet rs,
        int rownum) throws SQLException {
        
      TriggerAction action =  new TriggerActionTransferObject();
      
       action.setIdSeq(rs.getString(1));//TA_IDSEQ
      
       String type = rs.getString(6); //T_QTL_NAME

       if(type.equalsIgnoreCase(QTL_NAME_MODULE))
       {
           Module target = new ModuleTransferObject();
           target.setModuleIdseq(rs.getString(3));//T_QC_IDSEQ
           action.setActionTarget(target);
       }
        else if(type.equalsIgnoreCase(QTL_NAME_QUESTION))
         {
             Question target = new QuestionTransferObject();
             target.setQuesIdseq(rs.getString(3));  //T_QC_IDSEQ
             action.setActionTarget(target);
         }
         action.setInstruction(rs.getString(4));//TA_INSTRUCTION
         return action;
     }
     
    } 
    
    private class UpdateTriggerAction extends SqlUpdate {
      public UpdateTriggerAction(DataSource ds) {
        String sql =
          " UPDATE TRIGGERED_ACTIONS_EXT SET " +
          " TA_INSTRUCTION = ? , T_QC_IDSEQ, modified_by = ? " +
          " WHERE TA_IDSEQ = ? ";

        this.setDataSource(ds);
        this.setSql(sql);
        declareParameter(new SqlParameter("TA_INSTRUCTION", Types.VARCHAR));
        declareParameter(new SqlParameter("T_QC_IDSEQ", Types.VARCHAR));
        declareParameter(new SqlParameter("modified_by", Types.VARCHAR));
        declareParameter(new SqlParameter("TA_IDSEQ", Types.VARCHAR));
        compile();
      }

      protected int update( String triggerId,
       String  targetId, String  instruction, String modifiedBy) {


        Object[] obj =
          new Object[] {
          triggerId,targetId,instruction,
          modifiedBy
          };

        int res = update(obj);

        return res;
      }
    }    
    
    private class CreateTriggerAction extends SqlUpdate {
      public CreateTriggerAction(DataSource ds) {
        String sql =
          " INSERT INTO TRIGGERED_ACTIONS_EXT " +
          " (S_QC_IDSEQ, T_QC_IDSEQ,TA_INSTRUCTION,T_QTL_NAME,created_by) " +
          " VALUES " + " (?, ? , ?,?) ";

        this.setDataSource(ds);
        this.setSql(sql);
        declareParameter(new SqlParameter("S_QC_IDSEQ", Types.VARCHAR));
        declareParameter(new SqlParameter("T_QC_IDSEQ", Types.VARCHAR));
        declareParameter(new SqlParameter("TA_INSTRUCTION", Types.VARCHAR));
        declareParameter(new SqlParameter("T_QTL_NAME", Types.VARCHAR));
        declareParameter(new SqlParameter("created_by", Types.VARCHAR));
        compile();
      }

      protected int create(
        TriggerAction action,String createdBy) {
        String targetQtlName = null;
          if(action.getActionTarget() instanceof Module)
              targetQtlName = QTL_NAME_MODULE;
          else
              targetQtlName = QTL_NAME_QUESTION;

        Object[] obj =
          new Object[] {
            action.getActionSource().getIdseq(),
            action.getActionTarget().getIdseq(),
            action.getInstruction(),
            targetQtlName,
            createdBy
          };

        int res = update(obj);

        return res;
      }
    }    
    
    private class DeleteTriggerAction extends SqlUpdate {
      public DeleteTriggerAction(DataSource ds) {
        String sql =
          " delete from TRIGGERED_ACTION_EXT " +
          " where TA_IDSEQ =? " ;

        this.setDataSource(ds);
        this.setSql(sql);
        declareParameter(new SqlParameter("TA_IDSEQ", Types.VARCHAR));
        compile();
      }

      protected int delete(
        String triggerId) {
        Object[] obj =
          new Object[] {
            triggerId
          };

        int res = update(obj);

        return res;
      }
    }  
    
    private class TriggerActionProtocol extends SqlUpdate {
      public TriggerActionProtocol(DataSource ds) {
        String sql =
          " INSERT INTO TA_PROTO_CSI_EXT " +
          " (TA_IDSEQ,TP_PROTO_IDSEQ) " +
          " VALUES " + " (?, ?) ";

        this.setDataSource(ds);
        this.setSql(sql);
        declareParameter(new SqlParameter("TA_IDSEQ", Types.VARCHAR));
        declareParameter(new SqlParameter("TP_PROTO_IDSEQ", Types.VARCHAR));

        compile();
      }

      protected int setProtocol(
        String triggerId, String protocolId) {
        Object[] obj =
          new Object[] {
            triggerId,
            protocolId,
          };

        int res = update(obj);

        return res;
      }
    }        

    private class DeleteTriggerActionProtocol extends SqlUpdate {
      public DeleteTriggerActionProtocol(DataSource ds) {
        String sql =
          " delete from TA_PROTO_CSI_EXT " +
          " where TP_PROTO_IDSEQ =? " +
          " and TA_IDSEQ = ? ";

        this.setDataSource(ds);
        this.setSql(sql);
        declareParameter(new SqlParameter("TP_PROTO_IDSEQ", Types.VARCHAR));
        declareParameter(new SqlParameter("TA_IDSEQ", Types.VARCHAR));

        compile();
      }

      protected int delete(
        String triggerId, String protocolId) {
        Object[] obj =
          new Object[] {
            triggerId,
            protocolId,
          };

        int res = update(obj);

        return res;
      }
    }    
    
    private class TriggerActionCSI extends SqlUpdate {
      public TriggerActionCSI(DataSource ds) {
        String sql =
          " INSERT INTO TA_PROTO_CSI_EXT " +
          " (TA_IDSEQ,AC_CSI_IDSEQ, ) " +
          " VALUES " + " (?, ?) ";

        this.setDataSource(ds);
        this.setSql(sql);
        declareParameter(new SqlParameter("TA_IDSEQ", Types.VARCHAR));
        declareParameter(new SqlParameter("AC_CSI_IDSEQ", Types.VARCHAR));

        compile();
      }

      protected int setCSI(
        String triggerId, String acCsiIdseq) {
        Object[] obj =
          new Object[] {
            triggerId,
            acCsiIdseq,
          };

        int res = update(obj);

        return res;
      }
    }        

    private class DeleteTriggerActionCSI extends SqlUpdate {
      public DeleteTriggerActionCSI(DataSource ds) {
        String contentInsertSql =
          " delete from TA_PROTO_CSI_EXT " +
          " where AC_CSI_IDSEQ =? " +
          " and TA_IDSEQ = ? ";

        this.setDataSource(ds);
        this.setSql(contentInsertSql);
        declareParameter(new SqlParameter("AC_CSI_IDSEQ", Types.VARCHAR));
        declareParameter(new SqlParameter("TA_IDSEQ", Types.VARCHAR));

        compile();
      }

      protected int delete(
        String triggerId, String acCsiIdSeq) {
        Object[] obj =
          new Object[] {
            triggerId,
            acCsiIdSeq,
          };

        int res = update(obj);

        return res;
      }
    }      
}
