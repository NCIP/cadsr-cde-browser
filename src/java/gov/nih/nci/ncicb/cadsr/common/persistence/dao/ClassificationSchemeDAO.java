package gov.nih.nci.ncicb.cadsr.common.persistence.dao;

import gov.nih.nci.ncicb.cadsr.common.resource.ClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.common.resource.ClassificationScheme;

import java.util.Collection;
import java.util.List;

public interface ClassificationSchemeDAO extends AdminComponentDAO
{
  /** Returns the classification schemes that are roots of the HAS-A relationship
   *
   * */
  public Collection<ClassificationScheme> getRootClassificationSchemes(String conteIdseq);
  
  public Collection<ClassificationScheme> getChildrenClassificationSchemes(String pCSIdseq);
  
  public List<ClassSchemeItem> getCSCSIHierarchyByCS(String csIdseq);
  
  public List<ClassSchemeItem> getFirstLevelCSIByCS(String csIdseq);
  
  public List<ClassSchemeItem> getChildrenCSI(String pCsiIdseq);
  
}