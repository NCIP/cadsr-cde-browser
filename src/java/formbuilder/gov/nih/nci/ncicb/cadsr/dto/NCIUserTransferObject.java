package gov.nih.nci.ncicb.cadsr.dto;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

public class NCIUserTransferObject implements NCIUser 
{
  private String username=null;
  public NCIUserTransferObject(String newUsername)
  {
    username=newUsername;
  }

  public String getUsername()
  {
    return username;
  }
  
  public boolean hasRoleAccess(String role, String ContextId)
  {
    return true;
  }
}