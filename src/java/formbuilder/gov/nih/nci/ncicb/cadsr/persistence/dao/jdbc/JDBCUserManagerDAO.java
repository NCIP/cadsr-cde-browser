package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.NCIUserTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.UserManagerDAO;
import gov.nih.nci.ncicb.cadsr.resource.NCIUser;
import gov.nih.nci.ncicb.cadsr.security.oc4j.OC4JUserObject;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.ejb.ServiceLocatorImpl;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.StoredProcedure;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;


public class JDBCUserManagerDAO extends JDBCBaseDAO implements UserManagerDAO {
  public JDBCUserManagerDAO(ServiceLocator locator) {
    super(locator);
  }

  public boolean userInGroup(
    String username,
    String groupName) {
    UserGroupQuery query = new UserGroupQuery(getDataSource());
    String[] params = new String[2];
    params[0] = username;
    params[1] = groupName;

    Integer result = (Integer) query.findObject(params);

    if (result.intValue() < 1) {
      return false;
    }
    else {
      return true;
    }
  }

  public boolean validUser(
    String userName,
    String password) {
    boolean validUser = false;

    try {
      this.getDataSource().getConnection(userName, password);
      validUser = true;
    }
    catch (SQLException e) {
      validUser = false;
    }

    return validUser;
  }

  public NCIUser getNCIUser(String username) {
    UserQuery userQuery = new UserQuery(getDataSource());
    NCIUser user = (NCIUser) userQuery.findObject(username);
    user.setContextsByRole(this.getContextsForAllRoles(username,"QUEST_CONTENT"));
    return user;
  }

  public Map getContextsForAllRoles(
    String username,
    String acType) {
    Map out = new HashMap();
    ContextsQuery qry = new ContextsQuery(this.getDataSource());
    out = qry.getContexts(username, acType);

    return out;
  }

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    JDBCDAOFactory factory =
      (JDBCDAOFactory) new JDBCDAOFactory().getDAOFactory(locator);
    UserManagerDAO dao = factory.getUserManagerDAO();

    //NCIUser user = (NCIUser) dao.getNCIUser("BRAGGV");
    //System.out.println(user);
    Map result = dao.getContextsForAllRoles("SBREXT", "QUEST_CONTENT");
    Set keys = result.keySet();
    Iterator it = keys.iterator();

    while (it.hasNext()) {
      String role = (String) it.next();
      List l = (List) result.get(role);
      System.out.println("Role: " + role + " contexts count: " + l.size());
    }
  }

  // inner class
  class UserGroupQuery extends MappingSqlQuery {
    UserGroupQuery(DataSource ds) {
      super(
        ds,
        "SELECT COUNT(*) from USER_BR_VIEW_EXT where UA_NAME=UPPER(?) AND BRL_NAME=UPPER(?)");
      declareParameter(new SqlParameter("UA_NAME", Types.VARCHAR));
      declareParameter(new SqlParameter("BRL_NAME", Types.VARCHAR));
      compile();
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      return new Integer(rs.getInt(1));
    }
  }

  // inner class end    
  // inner class
  class UserQuery extends MappingSqlQuery {
    UserQuery(DataSource ds) {
      super(ds, "SELECT *  from USER_ACCOUNTS_VIEW where UA_NAME=UPPER(?)");
      declareParameter(new SqlParameter("UA_NAME", Types.VARCHAR));
      compile();
    }

    protected Object mapRow(
      ResultSet rs,
      int rownum) throws SQLException {
      NCIUserTransferObject user =
        new NCIUserTransferObject(rs.getString("UA_NAME"));

      return user;
    }
  }

  // inner class end    
  private class ContextsQuery extends StoredProcedure {
    Map out = new HashMap();
    List results = new ArrayList();

    public ContextsQuery(DataSource ds) {
      super(ds, "cadsr_security_util.get_contexts_list");
      declareParameter(new SqlParameter("p_ua_name", Types.VARCHAR));
      declareParameter(new SqlParameter("p_actl_name", Types.VARCHAR));
      declareParameter(
        new SqlOutParameter(
          "p_contexts", oracle.jdbc.OracleTypes.CURSOR,
          new RowCallbackHandlerImpl()));
      compile();
    }

    public Map getContexts(
      String username,
      String acType) {
      Map in = new HashMap();
      in.put("p_ua_name", username);
      in.put("p_actl_name", acType);
      execute(in);

      Iterator it = results.iterator();
      Map mp = new HashMap();
      String roleName;

      while (it.hasNext()) {
        mp = (Map) it.next();
        roleName = (String) mp.get("BusinessRole");

        if (out.containsKey(roleName)) {
          List availList = (List) out.get(roleName);
          availList.add((String) mp.get("ConteIdseq"));
        }
        else {
          List contextsList = new ArrayList();
          contextsList.add((String) mp.get("ConteIdseq"));
          out.put(roleName, contextsList);
        }
      }

      return out;
    }

    private class RowCallbackHandlerImpl implements RowCallbackHandler {
      public void processRow(ResultSet rs) throws SQLException {
        Map row = new HashMap();
        row.put("ConteIdseq", (String) rs.getString(1));
        row.put("ConteName", (String) rs.getString(2));
        row.put("BusinessRole", (String) rs.getString(3));
        results.add(row);
      }
    }
  }
}
