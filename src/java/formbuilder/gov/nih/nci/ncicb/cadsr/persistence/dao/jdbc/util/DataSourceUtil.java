package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc.util;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceUtil 
{
  public DataSourceUtil()
  {
  }
  public static DataSource getDriverManagerDS(String driverClassName,String url,String userName,String password)
  {
    return new DriverManagerDataSource(driverClassName,url,userName,password);
  }
  
}