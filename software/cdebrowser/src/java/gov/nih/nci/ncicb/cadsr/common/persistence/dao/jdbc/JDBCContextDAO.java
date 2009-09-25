package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.ContextTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.ContextDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Context;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Types;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.SimpleServiceLocator;
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
   * Gets all the contexts excluding the contexts 
   */
  public List getAllContexts(String excludeList)
  {
     ContextQuery query = new ContextQuery();
     if(excludeList!=null&&!excludeList.equals(""))
        query.setSqlWithExcludeList(excludeList);
    else
        query.setSql();
     query.setDataSource(getDataSource());
     return query.execute();    
  }
  /**
   * Gets all the contexts in caDSR
   *
   * @return <b>Collection</b> Collection of ContextTransferObjects
   */
  public Context getContextByName(String contextName) {
     ContextByNameQuery_STMT query = new ContextByNameQuery_STMT();
     query.setDataSource(getDataSource());
     query._setSql(contextName);
     List result = (List) query.execute();
     Context theContext = null;
     if (result.size() != 0) {
       theContext = (Context) (query.execute().get(0));
     }
    /**
     * This Change was made so that if there is no context return null
     * else
       {
         DMLException dmlExp = new DMLException("No matching record found.");
	 dmlExp.setErrorCode(NO_MATCH_FOUND);
	 throw dmlExp;
       }
       **/
     return theContext;
  }
  /**
   * Inner class that accesses database to get all the contexts in caDSR
   *
   */
	class ContextQuery extends MappingSqlQuery {
    
    String contextsql = " select conte_idseq, name, description from sbr.contexts_view order by upper(name) ";
    String contextsqlNoOrderBy = " select conte_idseq, name , description from sbr.contexts_view  ";
    
    ContextQuery(){
      super();
    }

    public void setSql(){
      super.setSql(contextsql);
    }
    public void setSqlWithExcludeList(String excludeList){
     String contextQueryStmt = contextsqlNoOrderBy +" where upper(name) NOT IN ( " +excludeList + ") ORDER BY upper(name)";
      
      super.setSql(contextQueryStmt);
    }
   /**
    * 3.0 Refactoring- Removed JDBCTransferObject
    */
    protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
      Context aContext = new ContextTransferObject();
      aContext.setConteIdseq(rs.getString(1)); //CONTE_IDSEQ
      aContext.setName(rs.getString(2));  // NAME
      aContext.setDescription(rs.getString(3)); //DESC
      return aContext;
    }
  }

  /**
   * Inner class that accesses database to get all the contexts in caDSR
   *
   */
  class ContextByNameQuery_STMT extends MappingSqlQuery {

    public ContextByNameQuery_STMT(){
      super();
    }

    public void _setSql(String name){
      super.setSql("select conte_idseq, name from sbr.contexts_view where name = '" + name + "'");
    }
   /**
    * 3.0 Refactoring- Removed JDBCTransferObject
    */
    protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
      Context aContext = new ContextTransferObject();
      aContext.setConteIdseq(rs.getString(1)); //CONTE_IDSEQ
      aContext.setName(rs.getString(2));  // NAME
      return aContext;
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
      super.setSql("select conte_idseq, name from sbr.contexts_view where name = ? ");
      declareParameter(new SqlParameter("name", Types.VARCHAR));
    }
   /**
    * 3.0 Refactoring- Removed JDBCTransferObject
    */
    protected Object mapRow(ResultSet rs, int rownum) throws SQLException {

      Context aContext = new ContextTransferObject();
      aContext.setConteIdseq(rs.getString(1)); //CONTE_IDSEQ
      aContext.setName(rs.getString(2));  // NAME
      return aContext;
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
    System.out.println(test.getAllContexts("'caBIG'"));
  }

}
