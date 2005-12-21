package gov.nih.nci.ncicb.cadsr.dto.bc4j;
import gov.nih.nci.ncicb.cadsr.persistence.base.BaseValueObject;
import gov.nih.nci.ncicb.cadsr.resource.Designation;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import java.sql.SQLException;
import gov.nih.nci.ncicb.cadsr.dto.bc4j.BC4JContextTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.DesignationsViewRowImpl;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.ContextsViewRowImpl;

import java.util.ArrayList;
import java.util.List;

public class BC4JDesignationTransferObject extends BaseValueObject
                        implements Designation, java.io.Serializable {
	String name = null;
	String type = null;
	String desigIDSeq = null;
	Context conte = null;
	String lang = null;
    List<ClassSchemeItem> csCsis;
	public BC4JDesignationTransferObject() {
	}
	public BC4JDesignationTransferObject(DesignationsViewRowImpl des)
		throws SQLException {
		name = des.getName();
		type = des.getDetlName();
		desigIDSeq = des.getDesigIdseq();
		lang = checkForNull(des.getLaeName());
		conte = new BC4JContextTransferObject((ContextsViewRowImpl) des.getContextsRow());
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public String getDesigIDSeq() {
		return desigIDSeq;
	}
	public Context getContext() {
		return conte;
	}
	public String getLanguage() {
		return lang;
	}
    
    public List<ClassSchemeItem> getCsCsis(){
       return csCsis;
    }
    
    public void addCscsi(gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem cscsi) {
        if (csCsis == null) {
            csCsis = new ArrayList();
        }
        csCsis.add(cscsi);    
    
    }
}