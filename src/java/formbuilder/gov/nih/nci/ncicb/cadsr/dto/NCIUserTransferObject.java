package gov.nih.nci.ncicb.cadsr.dto;

import gov.nih.nci.ncicb.cadsr.CaDSRConstants;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class NCIUserTransferObject implements NCIUser,CaDSRConstants {
  private String username = null;
  private Map contexts = new HashMap();

  public NCIUserTransferObject(String newUsername) {
   setUsername(newUsername);
  }

  public void setUsername(String newUsername) {
      username = newUsername;
  }
  
  public String getUsername() {
    return username;
  }

  public boolean hasRoleAccess(
    String role,
    String contextId) {
    if (contexts != null) {
      Collection roleContexts = (Collection)contexts.get(role);
      if (roleContexts != null) {
        if (roleContexts.contains(contextId)) {
          return true;
        }
      }
    }
    return false;
  }

  public Map getContextsByRole() {
    return contexts;
  }

  public void setContextsByRole(Map contextsMap) {
    contexts = contextsMap;
  }
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(OBJ_SEPARATOR_START);
    sb.append(super.toString());
    sb.append(ATTR_SEPARATOR+"username="+getUsername());
    sb.append(ATTR_SEPARATOR+"ContextByRole="+getContextsByRole());      
    sb.append(OBJ_SEPARATOR_END);
    return sb.toString();    
  }
}
