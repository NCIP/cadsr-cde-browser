package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueDAO;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValue;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;

import java.util.Collection;


public class JDBCFormValidValueDAO extends JDBCBaseDAO
  implements FormValidValueDAO {
  public JDBCFormValidValueDAO(ServiceLocator locator) {
    super(locator);
  }

  public int createFormValidValueComponent(FormValidValue newValidValue)
    throws DMLException {
    return 0;
  }

  public int createFormValidValueComponents(Collection validValues)
    throws DMLException {
    return 0;
  }

  public int updateDisplayOrder(
    String validValueId,
    int newDisplayOrder) throws DMLException {
    return 0;
  }

  public int deleteFormValidValue(String validValueId)
    throws DMLException {
    return 0;
  }
}
