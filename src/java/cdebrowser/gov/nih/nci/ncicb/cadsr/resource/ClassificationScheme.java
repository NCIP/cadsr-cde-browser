package gov.nih.nci.ncicb.cadsr.resource;

public interface ClassificationScheme  extends AdminComponent{
  public String getCsIdseq();
  public void setCsIdseq(String sCsIdseq);

  public String getClassSchemeType();
  public void setClassSchemeType(String cstlName);

  public String getContextName();

  public void setContextName(String name);
  
}