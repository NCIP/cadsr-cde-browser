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
   protected String classSchemeLongName;
	protected String classSchemeDefinition;
	protected String classSchemeItemName;
	protected String classSchemeItemType;
   protected String csPublicId;
   protected String csIdseq;
   protected String csiIdseq;
   protected Float csVersion;

	public BC4JClassificationsTransferObject() {
		super();
	}

	public BC4JClassificationsTransferObject(ClassificationsViewRowImpl classificationsViewRowImpl) {
		deIdseq = classificationsViewRowImpl.getAcIdseq();
		classSchemeName = classificationsViewRowImpl.getPreferredName();
		classSchemeDefinition = classificationsViewRowImpl.getPreferredDefinition();
		classSchemeItemName = classificationsViewRowImpl.getCsiName();
	   classSchemeLongName = classificationsViewRowImpl.getLongName();
		classSchemeItemType = classificationsViewRowImpl.getCsitlName();
      csIdseq = classificationsViewRowImpl.getCsIdseq();
      csiIdseq = classificationsViewRowImpl.getCsiIdseq();
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
}
