package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import java.util.Collection;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;

public class JDBCUserManagerDAO extends JDBCBaseDAO implements UserManagerDAO 
{
  public JDBCUserManagerDAO(ServiceLocator locator) {
    super(locator);
  }

  public Collection getAllGroups()
  {
    return null;
  }

  public boolean userInGroup(String username, String groupName)
  {
    return false;
  }

  public boolean validateUser(String userName, String password)
  {
    return false;
  }

  public Collection getAllGroups(String username)
  {
    return null;
  }

  public boolean validateUser(String userName)
  {
    return false;
  }

  public NCIUser getUser(String userName)
  {
    return null;
  }
}