package gov.nih.nci.ncicb.cadsr.common.persistence.dao;
import gov.nih.nci.ncicb.cadsr.common.resource.ObjectClass;
import gov.nih.nci.ncicb.cadsr.common.resource.Property;

public interface DataElementConceptDAO extends AdminComponentDAO
{
  public Property getProperty(String decId);
  public ObjectClass getObjectClass(String decId);
}