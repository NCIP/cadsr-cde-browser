package gov.nih.nci.ncicb.cadsr.dto.bc4j;
import gov.nih.nci.ncicb.cadsr.resource.DataElementConcept;
import java.sql.Date;
import java.sql.SQLException;
import java.io.Serializable;
import gov.nih.nci.ncicb.cadsr.dto.base.AdminComponentTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.DataElementConceptsViewRowImpl;

public class BC4JDataElementConceptTransferObject extends AdminComponentTransferObject
	                    implements DataElementConcept,Serializable {
	protected String decIdseq;
	protected String conteIdseq;
	protected String cdIdseq;
	protected String proplName;
	protected String oclName;
	protected String objClassQualifier;
	protected String propertyQualifier;
	protected String changeNote;
	protected String objClassPrefName;
	protected String objClassContextName;
	protected String propertyPrefName;
	protected String propertyContextName;
	protected Float propertyVersion;
	protected Float objClassVersion;
	protected String conteName;
	protected String cdPrefName;
	protected String cdContextName;
	protected Float cdVersion;
  protected int cdPublicId;
  protected int ocPublicId;
  protected int propPublicId;

	public BC4JDataElementConceptTransferObject() {
		super();
	}

	public BC4JDataElementConceptTransferObject(DataElementConceptsViewRowImpl dataElementConceptsViewRowImpl)
		throws SQLException {

		decIdseq = dataElementConceptsViewRowImpl.getDecIdseq();
		cdIdseq = dataElementConceptsViewRowImpl.getCdIdseq();
		proplName = checkForNull(dataElementConceptsViewRowImpl.getProplName());
		oclName = checkForNull(dataElementConceptsViewRowImpl.getOclName());
		objClassQualifier =
			checkForNull(dataElementConceptsViewRowImpl.getObjClassQualifier());
		propertyQualifier =
			checkForNull(dataElementConceptsViewRowImpl.getPropertyQualifier());
		changeNote = checkForNull(dataElementConceptsViewRowImpl.getChangeNote());
		preferredDefinition =
			dataElementConceptsViewRowImpl.getPreferredDefinition();
		preferredName = dataElementConceptsViewRowImpl.getPreferredName();
		longName = dataElementConceptsViewRowImpl.getLongName();
		createdBy = dataElementConceptsViewRowImpl.getCreatedBy();
		//createdDate = (Date)dataElementConceptsViewRowImpl.getDateCreated().getData();
		modifiedBy = dataElementConceptsViewRowImpl.getModifiedBy();
		//modifiedDate = (Date)(dataElementConceptsViewRowImpl.getDateModified().getData());
		aslName = dataElementConceptsViewRowImpl.getAslName();
		version =
			new Float(dataElementConceptsViewRowImpl.getVersion().floatValue());
		deletedInd = dataElementConceptsViewRowImpl.getDeletedInd();
		latestVerInd = dataElementConceptsViewRowImpl.getLatestVersionInd();
		objClassPrefName =
			checkForNull(dataElementConceptsViewRowImpl.getObjectClassPrefName());
		objClassContextName =
			checkForNull(dataElementConceptsViewRowImpl.getObjectClassContext());
		propertyPrefName =
			checkForNull(dataElementConceptsViewRowImpl.getPropertyPrefName());
		propertyContextName =
			checkForNull(dataElementConceptsViewRowImpl.getPropertyContextName());
		if (dataElementConceptsViewRowImpl.getPropertyVersion() != null)
			propertyVersion =
				new Float(
					dataElementConceptsViewRowImpl.getPropertyVersion().floatValue());
		else
			propertyVersion = new Float(0.00f);
		if (dataElementConceptsViewRowImpl.getObjectClassVersion() != null)
			objClassVersion =
				new Float(
					dataElementConceptsViewRowImpl.getObjectClassVersion().floatValue());
		else
			objClassVersion = new Float(0.00f);
		conteName = dataElementConceptsViewRowImpl.getContextName();
		cdPrefName = dataElementConceptsViewRowImpl.getCDPrefName();
		cdContextName = dataElementConceptsViewRowImpl.getCDContextName();
		cdVersion =
			new Float(dataElementConceptsViewRowImpl.getCDVersion().floatValue());
    if (dataElementConceptsViewRowImpl.getDecId() != null)
      publicId = dataElementConceptsViewRowImpl.getDecId().intValue();
    origin = checkForNull(dataElementConceptsViewRowImpl.getOrigin());
	}

	public String getDecIdseq() {
		return decIdseq;
	}

	public void setDecIdseq(String aDecIdseq) {
		decIdseq = aDecIdseq;
	}

	public String getCdIdseq() {
		return cdIdseq;
	}

	public void setCdIdseq(String aCdIdseq) {
		cdIdseq = aCdIdseq;
	}

	public String getProplName() {
		return proplName;
	}

	public void setProplName(String aProplName) {
		proplName = aProplName;
	}

	public String getOclName() {
		return oclName;
	}

	public void setOclName(String aOclName) {
		oclName = aOclName;
	}

	public String getObjClassQualifier() {
		return objClassQualifier;
	}

	public void setObjClassQualifier(String aObjClassQualifier) {
		objClassQualifier = aObjClassQualifier;
	}

	public String getPropertyQualifier() {
		return propertyQualifier;
	}

	public void setPropertyQualifier(String aPropertyQualifier) {
		propertyQualifier = aPropertyQualifier;
	}

	public String getChangeNote() {
		return changeNote;
	}

	public void setChangeNote(String aChangeNote) {
		changeNote = aChangeNote;
	}
	public String getObjClassPrefName() {
		return objClassPrefName;
	}

	public String getObjClassContextName() {
		return objClassContextName;
	}
	public String getPropertyPrefName() {
		return propertyPrefName;
	}

	public String getPropertyContextName() {
		return propertyContextName;
	}
	public Float getObjClassVersion() {
		return objClassVersion;
	}
	public Float getPropertyVersion() {
		return propertyVersion;
	}
	public String getContextName() {
		return conteName;
	}
	public String getCDContextName() {
		return cdContextName;
	}
	public String getCDPrefName() {
		return cdPrefName;
	}
	public Float getCDVersion() {
		return cdVersion;
	}

}
