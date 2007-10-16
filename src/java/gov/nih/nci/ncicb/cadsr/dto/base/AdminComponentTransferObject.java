package gov.nih.nci.ncicb.cadsr.dto.base;

import java.sql.Date;
import java.sql.Timestamp;
import gov.nih.nci.ncicb.cadsr.resource.*;
import gov.nih.nci.ncicb.cadsr.persistence.base.BaseValueObject;
import java.util.List;
import gov.nih.nci.ncicb.cadsr.resource.Context;

public class AdminComponentTransferObject extends BaseValueObject
                          implements java.io.Serializable {
	protected String preferredName;
	protected String preferredDefinition;
	protected String longName;
	protected String aslName;
	protected Float version;
	protected String deletedInd;
	protected String latestVerInd;
	protected String createdBy;
	protected Timestamp createdDate;
	protected String modifiedBy;
	protected Timestamp modifiedDate;
	protected String conteIdseq;
	protected Context context;
	protected List refDocs;
	protected List designations;
  protected int publicId;
  protected String origin;
  protected String idseq;
  protected String registrationStatus;
  protected List<Definition> definitions;
  protected List<Contact> contacts;

  //Publish Change Order
  protected boolean published;  

	public AdminComponentTransferObject() {
		super();
		preferredName = null;
		preferredDefinition = null;
		longName = null;
		aslName = null;
		version = null;
		deletedInd = null;
		latestVerInd = null;
		createdBy = null;
		createdDate = null;
		modifiedBy = null;
		modifiedDate = null;
		conteIdseq = null;
		context = null;
		refDocs = null;
		designations = null;
    registrationStatus=null;
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

	public String getPreferredName() {
		return preferredName;
	}

	public String getLongName() {
		return longName;
	}

	public Float getVersion() {
		return version;
	}

	public String getPreferredDefinition() {
		return preferredDefinition;
	}

	public String getAslName() {
		return aslName;
	}

	public String getLatestVersionInd() {
		return latestVerInd;
	}

	public String getDeletedInd() {
		return deletedInd;
	}

	public String getConteIdseq() {
		return conteIdseq;
	}

	public Context getContext() {
		return context;
	}

  public int getPublicId() {
    return publicId;
  }

  public String getOrigin() {
    return origin;
  }

	//setter methods

	public void setPreferredName(String pPreferredName) {
		preferredName = pPreferredName;
	}

	public void setLongName(String pLongName) {
		longName = pLongName;
	}

	public void setVersion(Float pVersion) {
		version = pVersion;
	}

	public void setPreferredDefinition(String pPreferredDefinition) {
		preferredDefinition = pPreferredDefinition;
	}

	public void setAslName(String pAslName) {
		aslName = pAslName;
	}

	public void setLatestVersionInd(String pLatestVersionInd) {
		latestVerInd = pLatestVersionInd;
	}

	public void setDeletedInd(String pDeletedInd) {
		deletedInd = pDeletedInd;
	}

	public void setCreatedBy(String pCreatedBy) {
		createdBy = pCreatedBy;
	}

	public void setDateCreated(Timestamp pCreatedDate) {
		createdDate = pCreatedDate;
	}

	public void setModifiedBy(String pModifiedBy) {
		modifiedBy = pModifiedBy;
	}

	public void setDateModified(Timestamp pModifiedDate) {
		modifiedDate = pModifiedDate;
	}

	public void setConteIdseq(String pContIdseq) {
		conteIdseq = pContIdseq;
	}

	public void setContext(Context pContext) {
		context = pContext;
	}
	public List getRefereceDocs() {
		return refDocs;
	}

  public void setReferenceDocs(List lRefDocs) {
    refDocs = lRefDocs;
  }
  
	public List getDesignations() {
		return designations;
	}

	public void setDesignations(List lDes) {
		designations = lDes;
	}

  public void setPublicId(int id) {
    publicId = id;
  }

  public void setOrigin(String source) {
    origin = source;
  }

  public String getIdseq(){
    return idseq;
  }

  public void setIdseq(String idseq) {
    this.idseq = idseq;
  }

  public String getRegistrationStatus() {
    return registrationStatus;
  }

  public void setRegistrationStatus(String regStatus) {
    registrationStatus = regStatus;
  }
    /**
   * Clones the Object
   * @return 
   * @throws CloneNotSupportedException
   */
  public Object clone() throws CloneNotSupportedException {
    return super.clone();

  }  
  
//Publish Change Order
  public boolean getIsPublished()
  {
    return published;
  }

  public void setPublished(boolean published)
  {
    this.published = published;
  }  
  
   public List<Definition> getDefinitions() {
    return definitions;
   }
    public void setDefinitions(List<Definition> newDefinitions){
      this.definitions = newDefinitions;
   }


   public void setContacts(List<Contact> contacts) {
      this.contacts = contacts;
   }

   public List<Contact> getContacts() {
      return contacts;
   }
}
