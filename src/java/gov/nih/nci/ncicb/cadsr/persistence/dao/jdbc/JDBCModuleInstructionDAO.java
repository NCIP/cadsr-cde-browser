package gov.nih.nci.ncicb.cadsr.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.persistence.dao.ModuleInstructionDAO;
import gov.nih.nci.ncicb.cadsr.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.servicelocator.SimpleServiceLocator;
import java.util.Collection;
import java.util.List;

public class JDBCModuleInstructionDAO extends JDBCInstructionDAO
  implements ModuleInstructionDAO {
  public JDBCModuleInstructionDAO(ServiceLocator locator) {
    super(locator);
  }

  

  /**
   * Creates a new Module instruction component (just the header info).
   *
   * @param <b>formInstr</b> Module Instruction object
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int createInstruction(Instruction moduleInstr, String parentId)
    throws DMLException {
    return super.createInstruction(moduleInstr,parentId,"MODULE_INSTR","MODULE_INSTRUCTION");
  }

  public List getInstructions(String moduleID)
    throws DMLException {
    return super.getInstructions(moduleID,"MODULE_INSTR");
  }
  /**
   * Test application
   */
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    JDBCModuleInstructionDAO test = new JDBCModuleInstructionDAO(locator);
    
    // test for createModuleInstructionComponent
    try {
      

      
      Instruction moduleInst = new InstructionTransferObject();
      moduleInst.setVersion(new Float(2.31));
      moduleInst.setLongName("Test Mod Instr Long Name 030204 1");
      moduleInst.setPreferredDefinition("Test Mod instr pref def");
      moduleInst.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
      moduleInst.setAslName("DRAFT NEW");
      moduleInst.setCreatedBy("Hyun Kim");
      moduleInst.setDisplayOrder(7);

      int res = test.createInstruction(moduleInst,"D45A49A8-167D-0422-E034-0003BA0B1A09");
      System.out.println("\n*****Create Module Instruction Result 1: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }

  } 




}
