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

public class JDBCBaseDAO extends BaseDAO {
  
 private final String DATASOURCE_LOCATION_KEY="java:comp/env/jdbc/FormBuilderDS";
 
 private Map dataAccessObjects = new HashMap();
 
 
 
 public JDBCBaseDAO() {
  }
  
  public JDBCBaseDAO(ServiceLocator locator) {
    super(locator);
  }

/**  
  public Object getDataAccessObject(String dataAccessObjectClassName)
  {
    if(dataAccessObjects.containsKey(dataAccessObjectClassName))
    {
      return dataAccessObjects.get(dataAccessObjectClassName);
    }
    else
    {
      try{
        Class accessClass = Class.forName(dataAccessObjectClassName);
        Object  obj = accessClass.newInstance();
        dataAccessObjects.put(dataAccessObjectClassName,obj);
      }
      catch(Exception ex)
      {
        throw new DAOCreateException("Cannot instatiate DataAccessObject",ex);
      }
    }    
  }
  
  **/
  public DataSource getDataSource(String key)
  {
    return getServiceLocator().getDataSource(key);
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