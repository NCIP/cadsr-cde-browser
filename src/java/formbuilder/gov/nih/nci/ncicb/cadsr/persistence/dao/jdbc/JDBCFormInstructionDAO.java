package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormInstructionDAO;
import gov.nih.nci.ncicb.cadsr.resource.FormInstruction;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;

import java.util.Collection;


public class JDBCFormInstructionDAO extends JDBCInstructionDAO
  implements FormInstructionDAO {
  public JDBCFormInstructionDAO(ServiceLocator locator) {
    super(locator);
  }

  public int updateDisplayOrder(
    String instructionId,
    int newDisplayOrder) throws DMLException {
    
    return 0;
  }

  public int createFormInstructionComponent(FormInstruction formInstr)
    throws DMLException {
    return 0;
  }

  public Collection getFormInstructions(String formId)
    throws DMLException {
    return null;
  }
}
