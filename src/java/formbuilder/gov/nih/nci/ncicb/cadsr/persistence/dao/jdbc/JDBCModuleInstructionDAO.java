package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleInstructionDAO;
import gov.nih.nci.ncicb.cadsr.resource.ModuleInstruction;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;

import java.util.Collection;


public class JDBCModuleInstructionDAO extends JDBCInstructionDAO
  implements ModuleInstructionDAO {
  public JDBCModuleInstructionDAO(ServiceLocator locator) {
    super(locator);
  }

  public int updateDisplayOrder(
    String instructionId,
    int newDisplayOrder) throws DMLException {
    
    return 0;
  }

  public int createModuleInstructionComponent(ModuleInstruction moduleInstr)
    throws DMLException {
    return 0;
  }

  public Collection getModuleInstructions(String moduleId)
    throws DMLException {
    return null;
  }

  public static void main(String[] args) {
  }
}
