package gov.nih.nci.ncicb.cadsr.servicelocator;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceConstants;
import gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc.util.DataSourceUtil;

import java.net.URL;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;

import javax.sql.DataSource;

public class SimpleServiceLocator extends ServiceLocatorAdapter implements PersistenceConstants
{
  private Map envEntrys =  new HashMap();
  private Map dataSources = new HashMap();


  public SimpleServiceLocator()
  {
    init();
  }

  protected void init()
  {
     // This is a temp fix need to revisit
      try{
    //   PropertyReader propReaderKeys =
     //  new PropertyReader(this.getClass(),this.DEPLOYMENT_FILENAME);
     //  Properties keysProperties = propReaderKeys.loadProperties();
     //  propVal= keysProperties.getProperty(propName);
       envEntrys = new HashMap();
       envEntrys.put(DRIVER_MANAGER_DS,"true");
       envEntrys.put(DRIVER_CLASS_NAME,"oracle.jdbc.driver.OracleDriver");
       envEntrys.put(CONNECTION_STRING,"jdbc:oracle:thin:@cbiodb2-d.nci.nih.gov:1521:cbdev");
       envEntrys.put(USERNAME,"sbrext");
       envEntrys.put(PASSWORD,"jjuser");
       envEntrys.put(DATASOURCE_LOCATION_KEY,"CDEBrowserDS");



       /**
        * envEntrys.put(CONNECTION_STRING,"jdbc:oracle:thin:@localhost:1521:red");
       envEntrys.put(USERNAME,"sbrext");
       envEntrys.put(PASSWORD,"jjuser");
       **/

       envEntrys.put(DAO_FACTORY_CLASS_KEY,"gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc.JDBCDAOFactory");

        DataSource source = DataSourceUtil.getDriverManagerDS(getString(DRIVER_CLASS_NAME)
                      ,getString(CONNECTION_STRING),getString(USERNAME)
                      ,getString(PASSWORD));
        dataSources.put("CDEBrowserDS",source);

      }
      catch(Exception ex)
      {
        throw new ServiceLocatorException("",ex);
      }
  }
  public EJBLocalHome getLocalHome(String jndiHomeName) throws ServiceLocatorException
  {
    return null;
  }

  public EJBHome getRemoteHome(String jndiHomeName, Class className)
  {
    return null;
  }

  public DataSource getDataSource(String dataSourceName)
  {
    if(log.isDebugEnabled())
      log.debug("Lookup Datasource with key "+ resolveDsLookupKey(dataSourceName));
    return (DataSource)dataSources.get(resolveDsLookupKey(dataSourceName));
  }

  public void setDataSource(String dataSourceKey, DataSource dataSource)
  {
    dataSources.put(dataSourceKey,dataSource);
  }

  public URL getUrl(String envName)
  {
    return null;
  }

  public boolean getBoolean(String envName)
  {
    return false;
  }

  public String getString(String envName)
  {
   if(log.isDebugEnabled())
      log.debug("Lookup String with key "+ resolveEnvLookupKey(envName));
    return (String)envEntrys.get(resolveEnvLookupKey(envName));
  }

  public void setObject(String key, Object value)
    {
      String resolvedKey = resolveEnvLookupKey(key);
      if(log.isDebugEnabled())
        log.debug("Bind with key ="+resolvedKey+" value="+ value);
      envEntrys.put(resolvedKey,value);
    }
 public static void main(String[] args)
 {
   SimpleServiceLocator locator = new SimpleServiceLocator();

 }
}