package gov.nih.nci.ncicb.cadsr.resource;

import java.util.List;

import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.FormElement;
import gov.nih.nci.ncicb.cadsr.resource.Protocol;

public interface TriggerAction extends Cloneable
{

    public void setIdSeq(String idSeq);

    public String getIdSeq();
    
    public void setActionSource(FormElement actionSource);

    public FormElement getActionSource();

    public void setActionTarget(FormElement actionTarget);

    public FormElement getActionTarget();
    
    public void setProtocols(List<Protocol> protocols);

    public List<Protocol> getProtocols();

    public void setClassSchemeItems(List<ClassSchemeItem> classSchemeItems);

    public List<ClassSchemeItem> getClassSchemeItems();
 
    public void setInstruction(String instruction);

    public String getInstruction();   
    
    public Object clone() throws CloneNotSupportedException ;   
}
