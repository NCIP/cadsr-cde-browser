package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

public class JDBCFormValidValueDAO extends JDBCBaseDAO implements FormValidValueDAO  {
  public JDBCFormValidValueDAO(ServiceLocator locator) {
   super(locator);
  }
  
}