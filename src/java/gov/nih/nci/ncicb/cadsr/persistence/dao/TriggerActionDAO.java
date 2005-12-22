package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.dto.CSITransferObject;
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
     * Gets The TriggerActions by id
     * 
     */
    public TriggerAction getTriggerActionForId(String triggerId);

    /**
     * Gets all the Protocols associated with a trigger action
     * 
     */
    public List<Protocol> getAllProtocolsForTriggerAction(String triggerActionId);

    /**
     * Retrieves all the classifications for trigger action
     * 
     * @param <b>acId</b> Idseq of an admin component
     * 
     * @return <b>Collection</b> Collection of CSITransferObject
     */
    public List<CSITransferObject> getAllClassificationsForTriggerAction(String actionId);

    /**
     * Update trigger action by its instruction
     * 
     */
    public int updateTriggerActionInstruction(String triggerId,
                                   String instruction, String modifiedBy);

   /**
    * Update trigger action by its target 
    * 
    */
   public int updateTriggerActionTarget(String triggerId, String targetId,
                                   String modifiedBy);


    /**
     * Create new trigger action
     * 
     */
    public String createTriggerAction(TriggerAction action, String createdBy);

    /**
     * delete Trigger action
     * 
     */
    public int deleteTriggerAction(String triggerId, String createdBy);

    /**
     * delete Trigger action Protocol
     * 
     */
    public int deleteTriggerActionProtocol(String triggerId,
                                           String protocolId);

    /**
     * delete Trigger action CSI
     * 
     */
    public int deleteTriggerActionCSI(String triggerId, String acCsiIdSeq);

    /**
     * Add Protocol to Trigger Action
     * 
     */
    public int addTriggerActionProtocol(String triggerId, String protocolId,
                                        String createdBy);

    /**
     * Add Protocol to Trigger Action
     * 
     */
    public int addTriggerActionCSI(String triggerId, String accsiId,
                                   String createdBy);
}
