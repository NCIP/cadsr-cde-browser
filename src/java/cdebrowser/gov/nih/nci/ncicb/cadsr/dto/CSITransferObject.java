package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.ClassSchemeItem;

public class CSITransferObject implements ClassSchemeItem  {
  private String csiName = null;
  private String csiType = null;
  private String csiIdseq = null;
  private String csCsiIdseq = null;
  private String csIdseq = null;
  private String csDefinition = null;
  private String csLongName = null;
  private String acCsiIdseq = null;

  public CSITransferObject() {
  }

  public String getClassSchemeLongName() {
    return csLongName;
  }

  public void setClassSchemeLongName(String aClassSchemeName) {
    csLongName = aClassSchemeName;
  }

  public String getClassSchemeDefinition() {
    return csDefinition;
  }

  public void setClassSchemeDefinition(String aClassSchemeDefinition) {
    csDefinition = aClassSchemeDefinition;
  }

  public String getClassSchemeItemName() {
    return csiName;
  }

  public void setClassSchemeItemName(String aClassSchemeItemName) {
    csiName = aClassSchemeItemName;
  }

  public String getClassSchemeItemType() {
    return csiType;
  }

  public void setClassSchemeItemType(String aClassSchemeItemType) {
    aClassSchemeItemType = csiType;
  }

  public String getCsiIdseq() {
    return csiIdseq;
  }

  public void setCsiIdseq(String csiIdseq) {
    this.csiIdseq = csiIdseq;
  }

  public String getCsIdseq() {
    return csIdseq;
  }

  public void setCsIdseq(String csIdseq) {
    this.csIdseq = csIdseq;
  }

  public String getCsCsiIdseq() {
    return csCsiIdseq;
  }

  public void setCsCsiIdseq(String csCsiIdseq) {
    this.csCsiIdseq = csCsiIdseq;
  }

  public String getAcCsiIdseq() {
    return acCsiIdseq;
  }
  public void setAcCsiIdseq(String acCsiIdseq) {
    this.acCsiIdseq = acCsiIdseq;
  }
}