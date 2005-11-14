package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.FormElement;

import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;

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
