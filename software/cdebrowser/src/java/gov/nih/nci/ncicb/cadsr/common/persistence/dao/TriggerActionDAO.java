/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.dto.CSITransferObject;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc.JDBCTriggerActionDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.common.resource.TriggerAction;

import java.util.List;

public interface TriggerActionDAO extends AdminComponentDAO
{

    /**
     * Gets all the TriggerActions for a source
     * 
     */
    public List<TriggerAction> getTriggerActionsForSource(String sourceId);
    
    /**
     * Gets Sources for this target
     * 
     */
    public List<TriggerAction> getTriggerActionsForTarget(String targetId); 
    
    public boolean isTargetForTriggerAction(String idSeq);
    
    public int deleteTriggerActionCSIProtocols(String triggerId);

    /**
     * Gets The TriggerActions by id
     * 
     */
    public TriggerAction getTriggerActionForId(String triggerId);


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
    public int deleteTriggerAction(String triggerId);

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
                                   
    public List<ClassSchemeItem> getAllClassificationsForTriggerAction(String actionId);                                   
    
    public List<Protocol> getAllProtocolsForTriggerAction(String triggerActionId);  
    

}
