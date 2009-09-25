package gov.nih.nci.ncicb.cadsr.common.dto.bc4j;

import gov.nih.nci.ncicb.cadsr.common.persistence.bc4j.ClassificationsViewRowImpl;
import gov.nih.nci.ncicb.cadsr.common.resource.Classification;

import java.io.Serializable;

public class BC4JClassificationsTransferObject implements Classification,Serializable {

	protected String deIdseq;
	protected String classSchemeName;
	protected String classSchemeLongName;
	protected String classSchemeDefinition;
	protected String classSchemeItemName;
	protected String classSchemeItemType;
	protected String csPublicId;
	protected String csIdseq;
	protected String csiIdseq;
	protected Float csVersion;
	protected Integer classSchemeItemId;
	protected Float classSchemeItemVersion;	

	public BC4JClassificationsTransferObject() {
		super();
	}

	public BC4JClassificationsTransferObject(ClassificationsViewRowImpl classificationsViewRowImpl) {
		deIdseq = classificationsViewRowImpl.getAcIdseq().trim();
		classSchemeName = classificationsViewRowImpl.getPreferredName();
		classSchemeDefinition = classificationsViewRowImpl.getPreferredDefinition();
		classSchemeLongName = classificationsViewRowImpl.getCsLongName();
		//classSchemeItemName = classificationsViewRowImpl.getCsiName();
		classSchemeItemName = classificationsViewRowImpl.getLongName();	   
		classSchemeItemType = classificationsViewRowImpl.getCsitlName();		
		classSchemeItemId = new Integer(classificationsViewRowImpl.getCsiId().intValue());
		classSchemeItemVersion = new Float(classificationsViewRowImpl.getCsiVersion().floatValue());		
		csIdseq = classificationsViewRowImpl.getCsIdseq().trim();
		csiIdseq = classificationsViewRowImpl.getCsiIdseq().trim();
		csVersion = new Float(classificationsViewRowImpl.getCsVersion().floatValue());
		//If clause added by Ram
		if (classificationsViewRowImpl.getCsId() != null)
			csPublicId = classificationsViewRowImpl.getCsId().stringValue();
		else
			csPublicId = "";
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

	public String getClassSchemeLongName() {
		return classSchemeLongName;
	}

	public void setClassSchemeLongName(String aClassSchemeName) {
		classSchemeLongName = aClassSchemeName;
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


	public void setCsIdseq(String csIdseq) {
		this.csIdseq = csIdseq;
	}

	public String getCsIdseq() {
		return csIdseq;
	}

	public void setCsiIdseq(String csiIdseq) {
		this.csiIdseq = csiIdseq;
	}

	public String getCsiIdseq() {
		return csiIdseq;
	}

	public Float getCsVersion(){
		return csVersion;
	}

	public void setCsVersion(Float pVersion){
		this.csVersion = pVersion;
	}

	/**
	 * @return the classSchemeItemId
	 */
	public Integer getClassSchemeItemId() {
		return classSchemeItemId;
	}

	/**
	 * @param classSchemeItemId the classSchemeItemId to set
	 */
	public void setClassSchemeItemId(Integer classSchemeItemId) {
		this.classSchemeItemId = classSchemeItemId;
	}

	/**
	 * @return the classSchemeItemVersion
	 */
	public Float getClassSchemeItemVersion() {
		return classSchemeItemVersion;
	}

	/**
	 * @param classSchemeItemVersion the classSchemeItemVersion to set
	 */
	public void setClassSchemeItemVersion(Float classSchemeItemVersion) {
		this.classSchemeItemVersion = classSchemeItemVersion;
	}


}
