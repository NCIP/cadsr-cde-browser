package gov.nih.nci.ncicb.cadsr.resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface NCIUser extends Serializable
{
  public String getUsername();
  public boolean hasRoleAccess(String role, Context context);
  public Map getContextsByRole();
  public void setContextsByRole(Map contextsMap);
}