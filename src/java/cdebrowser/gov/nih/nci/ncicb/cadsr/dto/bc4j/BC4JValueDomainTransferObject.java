package gov.nih.nci.ncicb.cadsr.dto.bc4j;

import gov.nih.nci.ncicb.cadsr.resource.ValueDomain;
import java.util.List;

import java.sql.Date;
import java.sql.SQLException;
import java.io.Serializable;
import gov.nih.nci.ncicb.cadsr.dto.base.AdminComponentTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.bc4j.ValueDomainsViewRowImpl;
import java.util.List;

public class BC4JValueDomainTransferObject extends AdminComponentTransferObject
                            implements ValueDomain, Serializable {
	protected String vdIdseq;
	protected String datatype;
	protected String uom;
	protected String dispFormat;
	protected String maxLength;
	protected String minLength;
	protected String highVal;
	protected String lowVal;
	protected String charSet;
	protected String decimalPlace;
	protected String cdPrefName;
	protected String cdContextName;
	protected Float cdVersion;
	protected String vdType;
  protected List validValues;

	public BC4JValueDomainTransferObject() {
    idseq = vdIdseq;
	}

	public BC4JValueDomainTransferObject(ValueDomainsViewRowImpl vdViewRowImpl)
		throws SQLException {
		preferredDefinition = vdViewRowImpl.getPreferredDefinition();
		preferredName = vdViewRowImpl.getPreferredName();
		longName = vdViewRowImpl.getLongName();
		createdBy = vdViewRowImpl.getCreatedBy();
		//createdDate = (Date)vdViewRowImpl.getDateCreated().getData();
		modifiedBy = vdViewRowImpl.getModifiedBy();
		//modifiedDate = (Date)(vdViewRowImpl.getDateModified().getData());
		aslName = vdViewRowImpl.getAslName();
		version = new Float(vdViewRowImpl.getVersion().floatValue());
		deletedInd = checkForNull(vdViewRowImpl.getDeletedInd());
		latestVerInd = vdViewRowImpl.getLatestVersionInd();
		vdIdseq = vdViewRowImpl.getVdIdseq();
    idseq = vdIdseq;
		datatype = vdViewRowImpl.getDtlName();
		uom = checkForNull(vdViewRowImpl.getUomlName());
		dispFormat = checkForNull(vdViewRowImpl.getFormlName());
		maxLength = checkForNull(vdViewRowImpl.getMaxLengthNum());
		minLength = checkForNull(vdViewRowImpl.getMinLengthNum());
		highVal = checkForNull(vdViewRowImpl.getHighValueNum());
		lowVal = checkForNull(vdViewRowImpl.getLowValueNum());
		charSet = checkForNull(vdViewRowImpl.getCharSetName());
		decimalPlace = checkForNull(vdViewRowImpl.getDecimalPlace());
		vdType = vdViewRowImpl.getVdTypeFlag();
		cdPrefName = vdViewRowImpl.getCDPrefName();
		cdContextName = vdViewRowImpl.getCDContextName();
		cdVersion = new Float(vdViewRowImpl.getCDVersion().floatValue());
    if (vdViewRowImpl.getVdId() != null)
      publicId = vdViewRowImpl.getVdId().intValue();
    origin = checkForNull(vdViewRowImpl.getOrigin());

	}

	public String getVdIdseq() {
		return vdIdseq;
	}

	public void setVdIdseq(String pVdIdseq) {
		vdIdseq = pVdIdseq;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDisplayFormat() {
		return dispFormat;
	}

	public void setDisplayFormat(String dispFormat) {
		this.dispFormat = dispFormat;
	}

	public String getUnitOfMeasure() {
		return uom;
	}
	public void setUnitOfMeasure(String uom) {
		this.uom = uom;
	}

	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getMinLength() {
		return minLength;
	}
	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}
	public String getHighValue() {
		return highVal;
	}
	public void setHighValue(String highVal) {
		this.highVal = highVal;
	}

	public String getLowValue() {
		return lowVal;
	}
	public void setLowValue(String lowVal) {
		this.lowVal = lowVal;
	}

	public String getCharSet() {
		return charSet;
	}
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}
	public String getDecimalPlace() {
		return decimalPlace;
	}
	public void setDecimalPlace(String dp) {
		this.decimalPlace = dp;
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

	public String getVDType() {
		if ("E".equals(vdType))
			return "Enumerated";
		else if ("N".equals(vdType))
			return "Non Enumerated";
		else
			return vdType;
	}
  
	public void setVDType(String type) {
		vdType = type;
	}
  
   public List getValidValues()
   {
     return null;
   }
   public void setValidValues(List validValues)
   {
     
   }
}