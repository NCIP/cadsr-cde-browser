package gov.nih.nci.ncicb.cadsr.resource;

public interface Designation  {
  public String getType();
  public String getName();
  public String getDesigIDSeq();
  public Context getContext();
  public String getLanguage();
}