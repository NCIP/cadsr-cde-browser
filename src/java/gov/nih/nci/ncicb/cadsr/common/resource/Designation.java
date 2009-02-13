package gov.nih.nci.ncicb.cadsr.common.resource;

import java.util.List;

public interface Designation  {
  public String getType();
  public String getName();
  public String getDesigIDSeq();
  public Context getContext();
  public String getLanguage();
  public List <ClassSchemeItem> getCsCsis();
  public void addCscsi (ClassSchemeItem cscsi);

}