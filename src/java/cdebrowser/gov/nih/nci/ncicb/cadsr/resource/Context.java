package gov.nih.nci.ncicb.cadsr.resource;

public interface Context extends Audit
{

  public static final String CTEP="CTEP";
  public String getConteIdseq();
  public void setConteIdseq(String aConteIdseq);
  
  public String getName();
  public void setName(String aName);

  public String getLlName();
  public void setLlName(String aLlName);

  public String getPalName();
  public void setPalName(String aPalName);

  public String getDescription();
  public void setDescription(String aDescription);

  public String getLanguage();
  public void setLanguage(String aLanguage);
  
  public Object clone() throws CloneNotSupportedException ;
}