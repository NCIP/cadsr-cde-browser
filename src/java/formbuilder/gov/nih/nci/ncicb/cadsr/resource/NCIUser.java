package gov.nih.nci.ncicb.cadsr.resource;
import java.util.Collection;
import java.util.Map;

public interface NCIUser 
{
  public String getUsername();
  public boolean hasRoleAccess(String role, String ContextId);
  public Map getContextsByRole();
  public void setContextsByRole(Map contextsMap);
}