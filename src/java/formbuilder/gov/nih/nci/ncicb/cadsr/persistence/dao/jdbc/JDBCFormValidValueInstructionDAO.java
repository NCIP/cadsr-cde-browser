package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.FormValidValueInstructionDAO;
import gov.nih.nci.ncicb.cadsr.resource.FormValidValueInstruction;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;

import java.util.Collection;


public class JDBCFormValidValueInstructionDAO extends JDBCInstructionDAO
  implements FormValidValueInstructionDAO {
  public JDBCFormValidValueInstructionDAO(ServiceLocator locator) {
    super(locator);
  }

  public int updateDisplayOrder(
    String instructionId,
    int newDisplayOrder) throws DMLException {
    return 0;
  }

  public int createValueInstructionComponent(
    FormValidValueInstruction valueInstr) throws DMLException {
    return 0;
  }

  public Collection getValueInstructions(String formValidValueId)
    throws DMLException {
    return null;
  }

  public static void main(String[] args) {
  }
}
