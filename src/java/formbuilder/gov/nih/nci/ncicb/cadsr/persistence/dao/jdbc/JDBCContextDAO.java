package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import java.util.Collection;


public class JDBCContextDAO extends JDBCBaseDAO implements ContextDAO {
  public JDBCContextDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all the contexts in caDSR
   * 
   * @return <b>Collection</b> Collection of ContextTransferObjects
   */
  public Collection getAllContexts() {
    return null;
  }

  /**
   * Gets all the contexts in which an user has a particular business role
   *
   * @param <b>username</b> name of the user
   * @param <b>businessRole</b> name of the role
   *
   * @return <b>Collection</b> Collection of ContextTransferObjects
   */
  public Collection getContexts(
    String username,
    String businessRole) {
    //Hyun, don't implement this method right now.
    return null;
  }
}
