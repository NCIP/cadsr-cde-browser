package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import java.util.Collection;
import gov.nih.nci.ncicb.cadsr.persistence.dao.WorkFlowStatusDAO;
import org.springframework.jdbc.object.MappingSqlQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class JDBCWorkFlowStatusDAO extends JDBCBaseDAO implements WorkFlowStatusDAO  {
  public JDBCWorkFlowStatusDAO(ServiceLocator locator) {
    super(locator);  
  }

  /**
   * Gets the workflow statuses for a particular admin component type. This info
   * is maintained in Table: ASL_ACTL_EXT Columns: ASL_NAME, ACTL_NAME
   * 
   * @param <b>adminComponentType</b> Indicates the admin component type
   */
  public Collection getWorkFlowStatusesForACType(String adminComponentType) {
     Collection col = new ArrayList();    
     WorkflowQuery query = new WorkflowQuery();
     query.setDataSource(getDataSource());
     query.setSql(adminComponentType, "");
     return query.execute();  
  }

  // inner class
	class WorkflowQuery extends MappingSqlQuery {
    WorkflowQuery(){
      super();
    }
    
    public void setSql(String adminComponentType, String dummy){
      String sqlStmt = "select ASL_NAME from ASL_ACTL_EXT " + 
        " where ACTL_NAME = " + adminComponentType + " " + 
        "   and ASL_NAME != 'RETIRED DELETED' ";
      super.setSql(sqlStmt);
    }
          
    protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
      // handles only one row
      return rs.getString("ASL_NAME");
    }
  }
          
  
}