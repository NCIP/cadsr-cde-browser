package gov.nih.nci.ncicb.cadsr.persistence.dao;
import gov.nih.nci.ncicb.cadsr.resource.ObjectClass;
import gov.nih.nci.ncicb.cadsr.resource.Property;

public interface DataElementConceptDAO extends AdminComponentDAO
{
  public Property getProperty(String decId);
  public ObjectClass getObjectClass(String decId);
}