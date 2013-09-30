/*L
 * Copyright SAIC-F Inc.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-cde-browser/LICENSE.txt for details.
 *
 * Portions of this source file not modified since 2008 are covered by:
 *
 * Copyright 2000-2008 Oracle, Inc.
 *
 * Distributed under the caBIG Software License.  For details see
 * http://ncip.github.com/cadsr-cde-browser/LICENSE-caBIG.txt
 */

package gov.nih.nci.ncicb.cadsr.common.dto.base;

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