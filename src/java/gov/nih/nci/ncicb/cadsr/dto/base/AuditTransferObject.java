package gov.nih.nci.ncicb.cadsr.dto.base;

import java.sql.Date;
import java.sql.Timestamp;

public class AuditTransferObject {

	protected String createdBy;
	protected Timestamp createdDate;
	protected String modifiedBy;
	protected Timestamp modifiedDate;

	public AuditTransferObject() {
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public Timestamp getDateCreated() {
		return createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public Timestamp getDateModified() {
		return modifiedDate;
	}

	public void setCreatedBy(String pCreatedBy) {
		createdBy = pCreatedBy;
	} //end method 

	public void setDateCreated(Timestamp pCreatedDate) {
		createdDate = pCreatedDate;
	} //end method 

	public void setModifiedBy(String pModifiedBy) {
		modifiedBy = pModifiedBy;
	} //end method 

	public void setDateModified(Timestamp pModifiedDate) {
		modifiedDate = pModifiedDate;
	} //end method 
  /**
   * Clones the Object
   * @return 
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException {
    return super.clone();

  }
}