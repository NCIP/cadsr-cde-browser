package gov.nih.nci.ncicb.cadsr.persistence.dao;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import java.util.Collection;
public interface ContextDAO  {
  public static final String CTEP="CTEP";
  public Collection getAllContexts();
  public Context getContextByName(String name);  
  public Collection getContexts(String username, String businessRole);
}