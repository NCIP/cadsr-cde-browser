package gov.nih.nci.ncicb.cadsr.servicelocator;
import gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc.util.DataSourceUtil;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBHome;
import javax.sql.DataSource;
import java.net.URL;

public class SimpleServiceLocator implements ServiceLocator 
{
  private Map envEntrys = null;
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
       envEntrys.put(USERNAME,"sbr");
       envEntrys.put(PASSWORD,"jjsbr");
       envEntrys.put(DAO_FACTORY_CLASS_KEY,"gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc.JDBCDAOFactory");
       
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
      try
      {
        return DataSourceUtil.getDriverManagerDS(getString(DRIVER_CLASS_NAME)
                      ,getString(CONNECTION_STRING),getString(USERNAME)
                      ,getString(PASSWORD));
      }
      catch (Exception e)
      {
        throw new ServiceLocatorException("",e);
      }

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
    return (String)envEntrys.get(envName);
  }

}