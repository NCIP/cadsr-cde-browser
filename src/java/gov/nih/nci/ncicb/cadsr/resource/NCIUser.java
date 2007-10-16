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
  public Collection getContextsByRoleAccess(String role);
  //added for GF1224
  public String getEmailAddress();
  public String getPhoneNumber();
}