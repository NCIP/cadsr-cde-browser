package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.FormValidValueInstructionDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.SimpleServiceLocator;

import java.util.List;


public class JDBCFormValidValueInstructionDAO extends JDBCInstructionDAO
  implements FormValidValueInstructionDAO {
  public JDBCFormValidValueInstructionDAO(ServiceLocator locator) {
    super(locator);
  }


  /**
   * Creates a new form valid value instruction component (just the header info).
   *
   * @param <b>valueInstr</b> FormValidValueInstruction object
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */

  public int createInstruction(Instruction vvInstr, String parentId)
    throws DMLException {
    return super.createInstruction(vvInstr,parentId,"VALUE_INSTR","VALUE_INSTRUCTION");
  }

  public List getInstructions(String vvID)
    throws DMLException {
    return super.getInstructions(vvID,"VALUE_INSTR");
  }  

  /**
   * Test application
   */
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    JDBCFormValidValueInstructionDAO test = 
      new JDBCFormValidValueInstructionDAO(locator);
    

    
    // test createValueInstructionComponent method.
    // for each test, change long name(preferred name generated from long name)
    try {
      Instruction formValidValueInstr = 
        new InstructionTransferObject();
      
      formValidValueInstr.setVersion(new Float(2.31));
      formValidValueInstr.setLongName("Test Valid Value Instr Long Name 030204 1");
      formValidValueInstr.setPreferredDefinition("Test Valid Value Instr pref def");
      formValidValueInstr.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
      formValidValueInstr.setAslName("DRAFT NEW");
      formValidValueInstr.setCreatedBy("Hyun Kim");
      formValidValueInstr.setDisplayOrder(101);
      int res = test.createInstruction(formValidValueInstr,"D4AA6D94-3345-1C0E-E034-0003BA0B1A09");
      System.out.println("\n*****Create Valid Value Instruction Result 1: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
  }



}