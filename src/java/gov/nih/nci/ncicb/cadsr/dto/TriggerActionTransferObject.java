package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.FormElement;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;
import gov.nih.nci.ncicb.cadsr.resource.TriggerAction;

import java.util.List;

public class TriggerActionTransferObject implements TriggerAction
{
  private FormElement actionSource;
  private FormElement actionTarget;
  private List<Protocol> protocols;
  private List<ClassSchemeItem> ClassSchemeItems;
  
    public TriggerActionTransferObject()
    {
    }

    public void setActionSource(FormElement actionSource)
    {
        this.actionSource = actionSource;
    }

    public FormElement getActionSource()
    {
        return actionSource;
    }

    public void setActionTarget(FormElement actionTarget)
    {
        this.actionTarget = actionTarget;
    }

    public FormElement getActionTarget()
    {
        return actionTarget;
    }

    public void setProtocols(List<Protocol> protocols)
    {
        this.protocols = protocols;
    }

    public List<Protocol> getProtocols()
    {
        return protocols;
    }

    public void setClassSchemeItems(List<ClassSchemeItem> classSchemeItems)
    {
        this.ClassSchemeItems = classSchemeItems;
    }

    public List<ClassSchemeItem> getClassSchemeItems()
    {
        return ClassSchemeItems;
    }
}
