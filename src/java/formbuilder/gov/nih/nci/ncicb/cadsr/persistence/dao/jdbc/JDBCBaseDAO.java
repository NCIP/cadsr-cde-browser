package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.persistence.dao.DAOCreateException;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import java.sql.SQLException;

import gov.nih.nci.ncicb.cadsr.persistence.dao.ConnectionException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.BaseDAO;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceContants;

public class JDBCBaseDAO extends BaseDAO implements PersistenceContants {
 
  public JDBCBaseDAO(ServiceLocator locator) {
    super(locator);
  }


  public DataSource getDataSource(String key)
  {
    if(getServiceLocator()!=null)
      return getServiceLocator().getDataSource(key);
    return null;
  }
  
  public DataSource getDataSource()
  {
    if(getServiceLocator()!=null)
      return getServiceLocator().getDataSource(getDataSourceKey());
    else
      return null;
  }
  
  public String getDataSourceKey()
  {
    return DATASOURCE_LOCATION_KEY;
  }
}