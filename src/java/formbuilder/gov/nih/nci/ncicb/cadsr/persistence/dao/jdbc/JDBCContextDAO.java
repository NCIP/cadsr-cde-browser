package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.dto.jdbc.JDBCContextTransferObject;
import gov.nih.nci.ncicb.cadsr.resource.Context;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import java.util.Iterator;


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
     Collection col = new ArrayList();    
     ContextQuery query = new ContextQuery();
     query.setDataSource(getDataSource());
     query.setSql();
     return query.execute();  
  }

  /**
   * Gets all the contexts in caDSR
   * 
   * @return <b>Collection</b> Collection of ContextTransferObjects
   */
  public Context getContextByName(String contextName) {   
     ContextByNameQuery query = new ContextByNameQuery();
     query.setDataSource(getDataSource());
     query.setSql();
    List result = (List) query.execute(contextName);
    Context theContext = null;
    if (result.size() != 0) {
      theContext = (Context) (query.execute(contextName).get(0));
    }
    else
    {
         DMLException dmlExp = new DMLException("No matching record found.");
	       dmlExp.setErrorCode(NO_MATCH_FOUND);
           throw dmlExp;         
    }
    return theContext;
  }
  /**
   * Inner class that accesses database to get all the contexts in caDSR
   * 
   */
	class ContextQuery extends MappingSqlQuery {

    ContextQuery(){
      super();
    }
    
    public void setSql(){
      super.setSql("select conte_idseq, name from contexts order by name");
    }
          
    protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
      return new JDBCContextTransferObject(rs);
    }
  }
  
    /**
   * Inner class that accesses database to get all the contexts in caDSR
   * 
   */
	class ContextByNameQuery extends MappingSqlQuery {

    public ContextByNameQuery(){
      super();
    }
    
    public void setSql(){
      super.setSql("select conte_idseq, name from contexts where name = ? ");
      declareParameter(new SqlParameter("name", Types.VARCHAR));
    }
          
    protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
      return new JDBCContextTransferObject(rs);
    }
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

  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    JDBCContextDAO test = new JDBCContextDAO(locator);
    //Collection coll = test.getAllContexts();
    System.out.println(test.getAllContexts());
  }

}
          