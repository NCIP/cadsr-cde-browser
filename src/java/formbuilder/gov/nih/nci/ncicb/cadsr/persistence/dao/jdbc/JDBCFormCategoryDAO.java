package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import java.util.Collection;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormCategoryDAO;
import org.springframework.jdbc.object.MappingSqlQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import java.util.Iterator;

public class JDBCFormCategoryDAO extends JDBCBaseDAO implements FormCategoryDAO  {
  public JDBCFormCategoryDAO(ServiceLocator locator) {
    super(locator);
  }

  /**
   * Gets all the form/template categories. This info is maintained in 
   * Table:QC_DISPLAY_LOV_EXT  Column:QCDL_NAME
   * 
   * @return <b>Collection</b> Collection of categories (Strings)
   */
  public Collection getAllCategories() {
     Collection col = new ArrayList();    
     CategoryQuery query = new CategoryQuery();
     query.setDataSource(getDataSource());
     query.setSql();
     return query.execute();  // retrieves all records
  }
  
  // inner class
	class CategoryQuery extends MappingSqlQuery {

    CategoryQuery(){
      super();
    }
    
    public void setSql(){
      super.setSql("select QCDL_NAME from QC_DISPLAY_LOV_EXT");
    }
          
    protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
      // handles only one row
      return rs.getString("QCDL_NAME");
    }
  }
          
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();

    //JDBCDAOFactory factory = (JDBCDAOFactory)new JDBCDAOFactory().getDAOFactory(locator);
    JDBCFormCategoryDAO test = new JDBCFormCategoryDAO(locator);

    Collection coll = test.getAllCategories();
    for (Iterator it=coll.iterator(); it.hasNext( ); ) { 
        Object anObject = it.next( ); 
        System.out.println( "category list display = " + anObject ); 
    }

  }


  
}