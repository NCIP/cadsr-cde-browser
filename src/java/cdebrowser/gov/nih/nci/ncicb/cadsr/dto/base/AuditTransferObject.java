package gov.nih.nci.ncicb.cadsr.dto.base;

import java.sql.Date;

public class AuditTransferObject {

	protected String createdBy;
	protected Date createdDate;
	protected String modifiedBy;
	protected Date modifiedDate;

	public AuditTransferObject() {
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Date getDateCreated() {
		return createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public Date getDateModified() {
		return modifiedDate;
	}

	public void setCreatedBy(String pCreatedBy) {
		createdBy = pCreatedBy;
	} //end method 

	public void setDateCreated(Date pCreatedDate) {
		createdDate = pCreatedDate;
	} //end method 

	public void setModifiedBy(String pModifiedBy) {
		modifiedBy = pModifiedBy;
	} //end method 

	public void setDateModified(Date pModifiedDate) {
		modifiedDate = pModifiedDate;
	} //end method 

}