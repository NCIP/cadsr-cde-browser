package gov.nih.nci.ncicb.cadsr.resource;

public interface ValidValue {
  public String getVdIdseq();

  public void setVdIdseq(String aVdIdseq);

  public String getShortMeaning();

  public void setShortMeaning(String aShortMeaning);

  public String getShortMeaningDescription();

  public void setShortMeaningDescription(String aShortMeaningDescription);

  public String getShortMeaningValue();

  public void setShortMeaningValue(String aShortMeaningValue);

  public String getDescription();

  public void setDescription(String vmDescription);
  
  public Object clone() throws CloneNotSupportedException ;
}
