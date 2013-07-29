/*L
 * Copyright Oracle Inc, SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.common.dto;

import gov.nih.nci.ncicb.cadsr.common.resource.FormElement;

import gov.nih.nci.ncicb.cadsr.common.resource.TriggerAction;

import java.util.ArrayList;
import java.util.List;

public class FormElementTransferObject extends AdminComponentTransferObject implements FormElement 
{
   private List<TriggerAction> triggerActions;
   
    public FormElementTransferObject()
    {
    }

    public void setTriggerActions(List<TriggerAction> triggerActions)
    {
        this.triggerActions = triggerActions;
    }
    public void addTriggerAction(TriggerAction triggerAction)
    {
        if(triggerActions==null)
            triggerActions = new ArrayList<TriggerAction>();
        triggerActions.add(triggerAction);
    }    

    public List<TriggerAction> getTriggerActions()
    {
        return triggerActions;
    }
}
