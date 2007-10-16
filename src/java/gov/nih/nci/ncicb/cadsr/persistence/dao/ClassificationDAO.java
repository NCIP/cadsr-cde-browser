package gov.nih.nci.ncicb.cadsr.persistence.dao;
import java.util.Collection;

public interface ClassificationDAO extends AdminComponentDAO
{
  public Collection getAllClassificationSchemes(String conteIdSeq);
}