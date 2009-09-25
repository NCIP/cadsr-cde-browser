package gov.nih.nci.ncicb.cadsr.common.dto;

import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.resource.Designation;

import java.util.ArrayList;
import java.util.List;

public class DesignationTransferObject implements Designation
{
    private String type;
    private String name;
    private Context context;
    private String desigIDSeq;
    private String language;
    public List <ClassSchemeItem> csCsis;

    public DesignationTransferObject() {
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }


    public void setCsCsis(List<ClassSchemeItem> csCsis) {
        this.csCsis = csCsis;
    }

    public List<ClassSchemeItem> getCsCsis() {
        return csCsis;
    }

    public void setDesigIDSeq(String desigIDSeq) {
        this.desigIDSeq = desigIDSeq;
    }

    public String getDesigIDSeq() {
        return desigIDSeq;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
    
    public void addCscsi(ClassSchemeItem csi){
        if (csCsis==null){
            csCsis = new ArrayList();            
        }
        csCsis.add(csi);
    }
}
