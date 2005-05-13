package gov.nih.nci.ncicb.cadsr.resource;

public interface ClassSchemeItem  {
  public String getClassSchemeLongName();
  public void setClassSchemeLongName(String aClassSchemeName);
  
  public String getClassSchemePrefName() ;
  public void setClassSchemePrefName(String aName);
   
  public String getClassSchemeDefinition();
  public void setClassSchemeDefinition(String aClassSchemeDefinition);
   
  public String getClassSchemeItemName();
  public void setClassSchemeItemName(String aClassSchemeItemName);

  public String getClassSchemeItemType();
  public void setClassSchemeItemType(String aClassSchemeItemType);

  public String getCsiIdseq();
  public void setCsiIdseq(String csiIdseq);

  public String getCsIdseq();
  public void setCsIdseq(String csIdseq);

  public String getCsCsiIdseq();
  public void setCsCsiIdseq(String csCsiIdseq);

  public String getAcCsiIdseq();
  public void setAcCsiIdseq(String acCsiIdseq);
  
  public void setCsiDescription(String csiDescription);
  public String getCsiDescription();
  public void setParentCscsiId(String parentCscsiId);
  public String getParentCscsiId();
  
  public void setCsConteIdseq(String csConteIdseq);
  public String getCsConteIdseq();

}