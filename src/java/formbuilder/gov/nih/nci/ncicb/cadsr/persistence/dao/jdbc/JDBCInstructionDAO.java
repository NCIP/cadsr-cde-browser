package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.InstructionDAO;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;


public abstract class JDBCInstructionDAO extends JDBCBaseDAO
  implements InstructionDAO {
  public JDBCInstructionDAO(ServiceLocator locator) {
    super(locator);
  }

  public int deleteInstruction(String instructionId) throws DMLException {
    return 0;
  }

  public int updateInstruction(
    String instructionId,
    String newLongName) throws DMLException {
    return 0;
  }

  public abstract int updateDisplayOrder(
    String instructionId,
    int newDisplayOrder) throws DMLException;

  public static void main(String[] args) {
  }
}
