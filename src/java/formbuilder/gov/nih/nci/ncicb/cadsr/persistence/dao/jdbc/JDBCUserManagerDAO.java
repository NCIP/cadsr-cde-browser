package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.dto.NCIUserTransferObject;
import gov.nih.nci.ncicb.cadsr.security.oc4j.OC4JUserObject;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ejb.ServiceLocatorImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import javax.sql.DataSource;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

public class JDBCUserManagerDAO extends JDBCBaseDAO implements UserManagerDAO 
{

  public JDBCUserManagerDAO(ServiceLocator locator) {
    super(locator);
  }

  public boolean userInGroup(String username, String groupName)
  {
     UserGroupQuery query = new UserGroupQuery(getDataSource());
     String[] params = new String[2];
     params[0]=username;
     params[1]=groupName;
     Integer result = (Integer)query.findObject(params);
     
     if(result.intValue()<1)
      return false;
     else
      return true;
  }

  public boolean validUser(String userName, String password)
  {
    boolean validUser =false;
    try
    {
      this.getDataSource().getConnection(userName,password);
      validUser =true;
    }
    catch (SQLException e)
    { 
      validUser =false;
    }
    return validUser;
  }

  public NCIUser getNCIUser(String username)
  {
    UserQuery userQuery = new UserQuery(getDataSource());  
    NCIUser user = (NCIUser)userQuery.findObject(username);
    return user;
  }
  // inner class
	class UserGroupQuery extends MappingSqlQuery {

		UserGroupQuery(DataSource ds) {
      super(ds, "SELECT COUNT(*) from USER_BR_VIEW_EXT where UA_NAME=UPPER(?) AND BRL_NAME=UPPER(?)");
      declareParameter(new SqlParameter("UA_NAME", Types.VARCHAR));
      declareParameter(new SqlParameter("BRL_NAME", Types.VARCHAR));
			compile();
		}

    protected Object mapRow(ResultSet rs, int rownum) throws SQLException 
    {
      return new Integer(rs.getInt(1));
    }
  } // inner class end    
  
  // inner class
	class UserQuery extends MappingSqlQuery {

		UserQuery(DataSource ds) {
      super(ds, "SELECT *  from USER_ACCOUNTS_VIEW where UA_NAME=UPPER(?)");
      declareParameter(new SqlParameter("UA_NAME", Types.VARCHAR));
			compile();
		}

    protected Object mapRow(ResultSet rs, int rownum) throws SQLException 
    {
      NCIUserTransferObject user = new NCIUserTransferObject(rs.getString("UA_NAME"));
      return  user;
    }
  } // inner class end    
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
     JDBCDAOFactory factory = (JDBCDAOFactory)new JDBCDAOFactory().getDAOFactory(locator);
     UserManagerDAO dao = factory.getUserManagerDAO();
     NCIUser user = (NCIUser)dao.getNCIUser("BRAGGV");
     System.out.println(user);    
  }  
}