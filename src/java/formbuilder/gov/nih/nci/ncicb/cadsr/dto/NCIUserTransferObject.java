package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import java.util.Map;
import java.util.HashMap;

public class NCIUserTransferObject implements NCIUser {
  private String username = null;
  private Map contexts = new HashMap();

  public NCIUserTransferObject(String newUsername) {
    username = newUsername;
  }

  public String getUsername() {
    return username;
  }

  public boolean hasRoleAccess(
    String role,
    String contextId) {
    return true;
  }

   public Map getContextsByRole() {
     return contexts;
   }

   public void setContextsByRole(Map contextsMap) {
     contexts = contextsMap;
   }
}
