package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.persistence.dao.ReferenceDocumentTypeDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.jdbc.object.MappingSqlQuery;

public class JDBCReferenceDocumentTypeDAO
 extends JDBCBaseDAO
 implements ReferenceDocumentTypeDAO {
 public JDBCReferenceDocumentTypeDAO(ServiceLocator locator) {
  super(locator);
 }

 /**
  * Gets all the reference document types. This info is maintained in
  * Table:DOCUMENT_TYPES_LOV  Column:DCTL_NAME
  *
  * @return <b>Collection</b> Collection of categories (Strings)
  */
 public Collection getAllDocumentTypes() {
  Collection col = new ArrayList();

  DocTypeQuery query = new DocTypeQuery();
  query.setDataSource(getDataSource());
  query.setSql();

  return query.execute(); // retrieves all records
 }

 public static void main(String [] args) {
  ServiceLocator locator = new SimpleServiceLocator();

  JDBCReferenceDocumentTypeDAO test = new JDBCReferenceDocumentTypeDAO(locator);

  Collection coll = test.getAllDocumentTypes();

  System.out.println(coll);
 /*
 for (Iterator it = coll.iterator(); it.hasNext();) {
   Object anObject = it.next();
 }
 */
 }

 /**
  * Inner class that accesses database to get all the form/template
  * categories.
  */
 class DocTypeQuery
  extends MappingSqlQuery {
  DocTypeQuery() {
   super();
  }

  public void setSql() {
   super.setSql("select DCTL_NAME from DOCUMENT_TYPES_LOV order by upper(DCTL_NAME)");
  }

  protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
   // handles only one row
   return rs.getString("DCTL_NAME");
  }
 }
}
