package gov.nih.nci.ncicb.cadsr.common.resource;

import java.util.List;

public interface FormElement extends AdminComponent
{

    public void setTriggerActions(List<TriggerAction> triggerActions);

    public List<TriggerAction> getTriggerActions();

}
