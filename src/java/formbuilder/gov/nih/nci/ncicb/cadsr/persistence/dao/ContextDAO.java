package gov.nih.nci.ncicb.cadsr.persistence.dao;
import java.util.Collection;
public interface ContextDAO  {
  public Collection getAllContexts();
  public Collection getContexts(String username, String businessRole);
}