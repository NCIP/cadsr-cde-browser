package gov.nih.nci.ncicb.cadsr.servicelocator;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBHome;
import javax.sql.DataSource;
import java.net.URL;
import gov.nih.nci.ncicb.cadsr.persistence.PersistenceContants;

public class AbstractServiceLocator implements ServiceLocator,PersistenceContants
{
  protected String dsLookupPrefix;
  protected String ejbLookupPrefix;
  protected String envLookupPrefix;

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
    return null;
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
    return null;
  }

  public String getDsLookupPrefix()
  {
    return dsLookupPrefix;
  }

  public void setDsLookupPrefix(String newDsLookupPrefix)
  {
    dsLookupPrefix = newDsLookupPrefix;
  }

  public String getEjbLookupPrefix()
  {
    return ejbLookupPrefix;
  }

  public void setEjbLookupPrefix(String newEjbLookupPrefix)
  {
    ejbLookupPrefix = newEjbLookupPrefix;
  }

  public String getEnvLookupPrefix()
  {
    return envLookupPrefix;
  }

  public void setEnvLookupPrefix(String newEnvLookupPrefix)
  {
    envLookupPrefix = newEnvLookupPrefix;
  }

  protected String resolveEJBLookupKey(String key)
  {
    StringBuffer finalKey ;
    if(ejbLookupPrefix!=null)
    {
      finalKey= new StringBuffer(ejbLookupPrefix);
      finalKey.append(key);
      return finalKey.toString();
    }
    else
      return key;
  }
  protected String resolveDsLookupKey(String key)
  {
    StringBuffer finalKey ;
    if(dsLookupPrefix!=null)
    {
      finalKey= new StringBuffer(dsLookupPrefix);
      finalKey.append(key);
      return finalKey.toString();
    }
    else
      return key;    
  }
    protected String resolveEnvLookupKey(String key)
  {
    StringBuffer finalKey ;
    if(this.envLookupPrefix!=null)
    {
      finalKey= new StringBuffer(envLookupPrefix);
      finalKey.append(key);
      return finalKey.toString();
    }
    else
      return key;       
  }
}