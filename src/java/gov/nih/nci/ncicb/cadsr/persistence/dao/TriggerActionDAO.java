package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc.JDBCTriggerActionDAO;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;

import java.util.List;

public interface TriggerActionDAO extends AdminComponentDAO
{

    /**
     * Gets all the TriggerActions for a source
     *
     */
    public List<TriggerAction> getTriggerActionsForSource(String sourceId);   
    
    /**
     * Gets all the Protocols associated with a trigger action
     *
     */
    public List<Protocol> getAllProtocolsForTriggerAction(String triggerActionId);
    
    /**
     * Update trigger action by its target and instruction
     *
     */
    public int updateTriggerAction(String triggerId,String targetId
            , String instruction, String modifiedBy);  
    
    /**
     * Create new trigger action
     * @return idSeq of the TriggerAction
     */
    public String createTriggerAction(TriggerAction action, String createdBy);
    
    /**
     * Gets all the TriggerActions by id
     *
     */
    public TriggerAction getTriggerActionForId(String triggerId);    
    
    /**
     * delete Trigger action
     *
     */
    public int deleteTriggerAction(String triggerId, String createdBy); 
}
