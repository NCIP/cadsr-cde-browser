package gov.nih.nci.ncicb.cadsr.resource;
import java.util.Collection;

public interface NCIUser 
{
  public String getUsername();
  public boolean hasRoleAccess(String role, String ContextId);
}