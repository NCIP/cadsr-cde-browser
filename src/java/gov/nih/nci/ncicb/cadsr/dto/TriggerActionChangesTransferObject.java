package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.TriggerActionChanges;

import java.util.List;

public class TriggerActionChangesTransferObject implements TriggerActionChanges
{
   private String triggerActionId;
   private String newInstruction;
   private List<String> addProtocols;
   private List<String> addCsis;
   private List<String> deleteProtocols;
   private List<String> deleteCsis;
   private String newTargetId;
    
   
    public TriggerActionChangesTransferObject()
    {
    }

    public void setTriggerActionId(String triggerActionId)
    {
        this.triggerActionId = triggerActionId;
    }

    public String getTriggerActionId()
    {
        return triggerActionId;
    }

    public void setNewInstruction(String newInstruction)
    {
        this.newInstruction = newInstruction;
    }

    public String getNewInstruction()
    {
        return newInstruction;
    }

    public void setAddProtocols(List<String> addProtocols)
    {
        this.addProtocols = addProtocols;
    }

    public List<String> getAddProtocols()
    {
        return addProtocols;
    }

    public void setAddCsis(List<String> addCsis)
    {
        this.addCsis = addCsis;
    }

    public List<String> getAddCsis()
    {
        return addCsis;
    }

    public void setDeleteProtocols(List<String> deleteProtocols)
    {
        this.deleteProtocols = deleteProtocols;
    }

    public List<String> getDeleteProtocols()
    {
        return deleteProtocols;
    }

    public void setDeleteCsis(List<String> deleteCsis)
    {
        this.deleteCsis = deleteCsis;
    }

    public List<String> getDeleteCsis()
    {
        return deleteCsis;
    }

    public void setNewTargetId(String newTargetId)
    {
        this.newTargetId = newTargetId;
    }

    public String getNewTargetId()
    {
        return newTargetId;
    }
}
