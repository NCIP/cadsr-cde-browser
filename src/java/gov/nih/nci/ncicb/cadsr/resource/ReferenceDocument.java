package gov.nih.nci.ncicb.cadsr.resource;

public interface ReferenceDocument  {
  public String getDocName();
  public String getDocType();
  public String getDocText();
  public String getDocIDSeq();
  public String getUrl();
  public String getLanguage();
}