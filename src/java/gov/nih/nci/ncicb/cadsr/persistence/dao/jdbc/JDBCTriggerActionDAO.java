package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.dto.FormValidValueTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ModuleTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.ProtocolTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.QuestionTransferObject;
import gov.nih.nci.ncicb.cadsr.dto.TriggerActionTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.TriggerActionDAO;
import gov.nih.nci.ncicb.cadsr.resource.Form;

import gov.nih.nci.ncicb.cadsr.resource.FormElement;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
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
    public List<TriggerAction> getTriggerActionsForSource(String sourceId) {
      TriggerActionBySourceQuery query = new TriggerActionBySourceQuery();
      query.setDataSource(getDataSource());
      query.setSql(sourceId);
      return query.execute();
    }    
    
    /**
     * Gets The TriggerActions by id
     *
     */
    public TriggerAction getTriggerActionForId(String triggerId) {
      TriggerActionByIdQuery query = new TriggerActionByIdQuery();
      query.setDataSource(getDataSource());
      query.setSql(triggerId);
      List result = (List) query.execute();
        
      if (result == null || result.isEmpty()){
            DMLException dmlExp = new DMLException("No matching record found.");
                  dmlExp.setErrorCode(NO_MATCH_FOUND);
              throw dmlExp;
        }

       return (TriggerAction) (result.get(0));      
        
    }   
    
    /**
     * Gets all the Protocols associated with a trigger action
     *
     */
    public List<Protocol> getAllProtocolsForTriggerAction(String triggerActionId) {
      TriggerActionProtocolsQuery query = new TriggerActionProtocolsQuery();
      query.setDataSource(getDataSource());
      query.setSql(triggerActionId);
      return query.execute();
    }
    
    /**
     * Retrieves all the classifications for trigger action
     *
     * @param <b>acId</b> Idseq of an admin component
     *
     * @return <b>Collection</b> Collection of CSITransferObject
     */
    public List<CSITransferObject> getAllClassificationsForTriggerAction(String actionId) {

      TriggerActionClassificationsQuery classificationQuery = new TriggerActionClassificationsQuery();
      classificationQuery.setDataSource(getDataSource());
      classificationQuery.setSql();

      return classificationQuery.execute(actionId);
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
    public String createTriggerAction(TriggerAction action, String createdBy) {
    
      String idseq = generateGUID();
      action.setIdSeq(idseq);
      CreateTriggerAction createAction = new CreateTriggerAction(getDataSource());
      int res = createAction.create( action, createdBy);
      
        if (res == 1) {
          return idseq;
        }
        else {
             DMLException dmlExp = new DMLException("Did not succeed creating trigger action ");
                   dmlExp.setErrorCode(ERROR_CREATEING_SKIP_PATTERN);
               throw dmlExp;
        }      

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
       
    /**
     * Inner class to get all classifications that belong to
     * the specified Trigger action
     */
    class TriggerActionClassificationsQuery extends MappingSqlQuery {
      TriggerActionClassificationsQuery() {
        super();
      }

      public void setSql() {
        super.setSql(
          "SELECT csi.csi_name, csi.csitl_name, csi.csi_idseq, " +
          "       cscsi.cs_csi_idseq, cs.preferred_definition, cs.long_name, " +
          "        accsi.ac_csi_idseq, cs.cs_idseq " +
          " FROM ac_csi accsi, cs_csi cscsi, " +
          "      class_scheme_items csi, classification_schemes cs  " +
          "      ,TA_PROTO_CSI_EXT ta_proto_csi  " +
          " WHERE ta_proto_csi.ta_idseq = ? "+
          " AND   accsi.cs_csi_idseq = cscsi.cs_csi_idseq " +
          " AND   cscsi.csi_idseq = csi.csi_idseq " +
          " AND   cscsi.cs_idseq = cs.cs_idseq " +
          " AND ta_proto_csi.ac_csi_idseq = accsi.ac_csi_idseq");

        declareParameter(new SqlParameter("ta_idseq", Types.VARCHAR));
      }

      protected Object mapRow(
        ResultSet rs,
        int rownum) throws SQLException {
        CSITransferObject csito = new CSITransferObject();

        csito.setClassSchemeItemName(rs.getString(1));
        csito.setClassSchemeItemType(rs.getString(2));
        csito.setCsiIdseq(rs.getString(3));
        csito.setCsCsiIdseq(rs.getString(4));
        csito.setClassSchemeDefinition(rs.getString(5));
        csito.setClassSchemeLongName(rs.getString(6));
        csito.setAcCsiIdseq(rs.getString(7));
        csito.setCsIdseq(rs.getString(8));
        return csito;
      }
    }
         
    
   private  class TriggerActionBySourceQuery extends MappingSqlQuery {
      TriggerActionBySourceQuery() {
        super();
      }

      public void setSql(String sourceIdSeq) {
        super.setSql(
         " select TA_IDSEQ, S_QC_IDSEQ, T_QC_IDSEQ, TA_INSTRUCTION, T_QTL_NAME "
        + " from  TRIGGERED_ACTIONS_EXT ta where "
        + "  S_QC_IDSEQ = '"+sourceIdSeq + "'" );      

      }

      protected Object mapRow(
        ResultSet rs,
        int rownum) throws SQLException {
        
      TriggerAction action =  new TriggerActionTransferObject();
      
       action.setIdSeq(rs.getString(1));//TA_IDSEQ
      
       String type = rs.getString(5); //T_QTL_NAME

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
    
    private  class TriggerActionByIdQuery extends MappingSqlQuery {
       TriggerActionByIdQuery() {
         super();
       }

       public void setSql(String targetIdSeq) {
         super.setSql(
          " select TA_IDSEQ, S_QC_IDSEQ, T_QC_IDSEQ, TA_INSTRUCTION, S_QTL_NAME, T_QTL_NAME "
         + " from TRIGGERED_ACTIONS_EXT ta where "
         + "  TA_IDSEQ = '"+targetIdSeq + "'" );      

       }

       protected Object mapRow(
         ResultSet rs,
         int rownum) throws SQLException {
         
       TriggerAction action =  new TriggerActionTransferObject();
       
        action.setIdSeq(rs.getString(1));//TA_IDSEQ
       
         String sourceType = rs.getString(5); //S_QTL_NAME
           if(sourceType.equalsIgnoreCase(QTL_NAME_MODULE))
           {
               Module source = new ModuleTransferObject();
               source.setModuleIdseq(rs.getString(2));//S_QC_IDSEQ
               action.setActionTarget(source);
           }
            else if(sourceType.equalsIgnoreCase(QTL_NAME_VALID_VALUE))
             {
                 FormValidValue source = new FormValidValueTransferObject();
                 source.setIdseq(rs.getString(2));//S_QC_IDSEQ
                 action.setActionTarget(source);
             }
             
        String targetType = rs.getString(6); //T_QTL_NAME
        if(targetType.equalsIgnoreCase(QTL_NAME_MODULE))
        {
            Module target = new ModuleTransferObject();
            target.setModuleIdseq(rs.getString(3));//T_QC_IDSEQ
            action.setActionTarget(target);
        }
        else if(targetType.equalsIgnoreCase(QTL_NAME_QUESTION))
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
          " (TA_IDSEQ, S_QC_IDSEQ, T_QC_IDSEQ,TA_INSTRUCTION,S_QTL_NAME,T_QTL_NAME,created_by) " +
          " VALUES " + " (?,?, ? ,?, ?,?,?) ";

        this.setDataSource(ds);
        this.setSql(sql);
        declareParameter(new SqlParameter("TA_IDSEQ", Types.VARCHAR));
        declareParameter(new SqlParameter("S_QC_IDSEQ", Types.VARCHAR));
        declareParameter(new SqlParameter("T_QC_IDSEQ", Types.VARCHAR));
        declareParameter(new SqlParameter("TA_INSTRUCTION", Types.VARCHAR));
          declareParameter(new SqlParameter("S_QTL_NAME", Types.VARCHAR));
        declareParameter(new SqlParameter("T_QTL_NAME", Types.VARCHAR));
        declareParameter(new SqlParameter("created_by", Types.VARCHAR));
        compile();
      }

      protected int create(
        TriggerAction action,String createdBy) {
        String targetQtlName = null;
        String sourceQtlName = null;
        
          if(action.getActionTarget() instanceof Module)
          {
              targetQtlName = QTL_NAME_MODULE;
          }  
          else 
          {
              targetQtlName = QTL_NAME_QUESTION;
          }

          if(action.getActionSource() instanceof Module)
          {
              sourceQtlName = QTL_NAME_MODULE;            
          }

          else 
          {
              sourceQtlName = QTL_NAME_VALID_VALUE;              
          }
              

        Object[] obj =
          new Object[] {
            action.getIdSeq(),
            action.getActionSource().getIdseq(),
            action.getActionTarget().getIdseq(),
            action.getInstruction(),
            sourceQtlName,targetQtlName,
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
