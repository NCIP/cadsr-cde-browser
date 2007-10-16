package gov.nih.nci.ncicb.cadsr.persistence.dao;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface UserManagerDAO {
  public boolean userInGroup(
    String username,
    String groupName);

  public boolean validUser(
    String userName,
    String password);

  public NCIUser getNCIUser(String userName);

  public Map getContextsForAllRoles(
    String username,
    String acType);
}
