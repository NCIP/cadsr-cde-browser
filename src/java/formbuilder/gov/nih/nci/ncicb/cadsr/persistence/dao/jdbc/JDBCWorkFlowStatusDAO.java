package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import java.util.Collection;
import gov.nih.nci.ncicb.cadsr.persistence.dao.WorkFlowStatusDAO;

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
    return null;
  }
}