package gov.nih.nci.ncicb.cadsr.persistence.dao;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import java.util.Collection;
import java.util.List;

public interface UserManagerDAO 
{
  public Collection  getAllGroups(String username);
  public Collection  getAllGroups();  
  public boolean userInGroup(String username,String groupName);
  public boolean validateUser(String userName,String password);
  public boolean validateUser(String userName);
  public NCIUser getUser(String userName);
  
}