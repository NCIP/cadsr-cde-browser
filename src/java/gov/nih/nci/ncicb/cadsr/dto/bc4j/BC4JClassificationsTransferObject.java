package gov.nih.nci.ncicb.cadsr.dto.bc4j;

import gov.nih.nci.ncicb.cadsr.resource.Classification;
//import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;
import java.sql.Date;
import java.sql.SQLException;
import java.io.Serializable;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.ClassificationsViewRowImpl;

public class BC4JClassificationsTransferObject implements Classification,Serializable {

	protected String deIdseq;
	protected String classSchemeName;
	protected String classSchemeDefinition;
	protected String classSchemeItemName;
	protected String classSchemeItemType;
  protected String csPublicId;

	public BC4JClassificationsTransferObject() {
		super();
	}

	public BC4JClassificationsTransferObject(ClassificationsViewRowImpl classificationsViewRowImpl) {
		deIdseq = classificationsViewRowImpl.getAcIdseq();
		classSchemeName = classificationsViewRowImpl.getPreferredName();
		classSchemeDefinition = classificationsViewRowImpl.getPreferredDefinition();
		classSchemeItemName = classificationsViewRowImpl.getCsiName();
		classSchemeItemType = classificationsViewRowImpl.getCsitlName();
    csPublicId = classificationsViewRowImpl.getCsId().stringValue();
	}

	//getter method
	public String getDeIdseq() {
		return deIdseq;
	}

	//setter method
	public void setDeIdseq(String aDeIdseq) {
		deIdseq = aDeIdseq;
	}

	public String getClassSchemeName() {
		return classSchemeName;
	}

	public void setClassSchemeName(String aClassSchemeName) {
		classSchemeName = aClassSchemeName;
	}

	public String getClassSchemeDefinition() {
		return classSchemeDefinition;
	}

	public void setClassSchemeDefinition(String aClassSchemeDefinition) {
		classSchemeDefinition = aClassSchemeDefinition;
	}

	public String getClassSchemeItemName() {
		return classSchemeItemName;
	}

	public void setClassSchemeItemName(String aClassSchemeItemName) {
		classSchemeItemName = aClassSchemeItemName;
	}

	public String getClassSchemeItemType() {
		return classSchemeItemType;
	}

	public void setClassSchemeItemType(String aClassSchemeItemType) {
		classSchemeItemType = aClassSchemeItemType;
	}

  public String getClassSchemePublicId() 
  {
    return csPublicId;
  }
  
  public void setClassSchemePublicId(String publicId) 
  {
    csPublicId = publicId;
  }


}