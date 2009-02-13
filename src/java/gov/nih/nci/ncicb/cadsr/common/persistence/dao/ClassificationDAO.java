package gov.nih.nci.ncicb.cadsr.common.persistence.dao;
import java.util.Collection;

public interface ClassificationDAO extends AdminComponentDAO
{
  public Collection getAllClassificationSchemes(String conteIdSeq);
}