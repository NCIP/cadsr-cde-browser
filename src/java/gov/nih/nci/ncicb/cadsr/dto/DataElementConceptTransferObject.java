package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.DataElementConcept;


public class DataElementConceptTransferObject
  extends AdminComponentTransferObject implements DataElementConcept {

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
  protected String cDPublicId;
  protected String objClassPublicId;
  protected String propertyPublicId;
  
  
  public DataElementConceptTransferObject() {
    super();
  }

  public String getDecIdseq() {
    return decIdseq;
  }

  public void setDecIdseq(String aDecIdseq) {
    decIdseq = aDecIdseq;
    idseq = aDecIdseq;
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

  public void setCDPublicId(String cDPublicID)
  {
    this.cDPublicId = cDPublicID;
  }


  public String getCDPublicId()
  {
    return cDPublicId;
  }


  public void setObjClassPublicId(String objClassPublicId)
  {
    this.objClassPublicId = objClassPublicId;
  }


  public String getObjClassPublicId()
  {
    return objClassPublicId;
  }


  public void setPropertyPublicId(String propertyPublicId)
  {
    this.propertyPublicId = propertyPublicId;
  }


  public String getPropertyPublicId()
  {
    return propertyPublicId;
  }


}
