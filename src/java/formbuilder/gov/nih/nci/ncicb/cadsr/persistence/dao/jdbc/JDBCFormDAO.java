package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCFormTransferObject;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import javax.sql.DataSource;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

public class JDBCFormDAO extends JDBCBaseDAO implements FormDAO  {
  
  public JDBCFormDAO(ServiceLocator locator) {
    super(locator);  
  }

  public Collection getFormsByContext(String context)
  {
  
     Collection col = new ArrayList();
     
     FromQuery query = new FromQuery(getDataSource());
     return query.execute(context);
     
    }   
    
  // inner class
	class FromQuery extends MappingSqlQuery {

		FromQuery(DataSource ds) {
      super(ds, "SELECT * from forms where context=?");
      declareParameter(new SqlParameter("context", Types.VARCHAR));
			compile();
		}

    protected Object mapRow(ResultSet rs, int rownum) throws SQLException 
    {
      return new JDBCFormTransferObject(rs);
    }

  } // inner class end    
  public static void main(String[] args) {
    //JDBCFormDAO 
  }
  
}