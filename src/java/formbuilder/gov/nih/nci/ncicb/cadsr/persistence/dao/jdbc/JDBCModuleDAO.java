package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

public class JDBCModuleDAO extends JDBCBaseDAO implements ModuleDAO  {
  public JDBCModuleDAO(ServiceLocator locator) {
    super(locator);  
  }

  public static void main(String[] args) {
    //JDBCModuleDAO jDBCModuleDAO = new JDBCModuleDAO();
  }
}