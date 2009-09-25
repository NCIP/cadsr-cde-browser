package gov.nih.nci.ncicb.cadsr.common.persistence.dao.jdbc;

import gov.nih.nci.ncicb.cadsr.common.dto.InstructionTransferObject;
import gov.nih.nci.ncicb.cadsr.common.exception.DMLException;
import gov.nih.nci.ncicb.cadsr.common.persistence.dao.QuestionInstructionDAO;
import gov.nih.nci.ncicb.cadsr.common.resource.Instruction;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.ServiceLocator;
import gov.nih.nci.ncicb.cadsr.common.servicelocator.SimpleServiceLocator;

import java.util.Collection;
import java.util.List;

public class JDBCQuestionInstructionDAO extends JDBCInstructionDAO
  implements QuestionInstructionDAO {
  public JDBCQuestionInstructionDAO(ServiceLocator locator) {
    super(locator);
  }


  /**
   * Creates a new Question instruction component (just the header info).
   *
   * @param <b>formInstr</b> QuestionInstruction object
   *
   * @return <b>int</b> 1 - success, 0 - failure.
   *
   * @throws <b>DMLException</b>
   */
  public int createInstruction(Instruction moduleInstr, String parentId)
    throws DMLException {
    return super.createInstruction(moduleInstr,parentId,"QUESTION_INSTR","ELEMENT_INSTRUCTION");
  }


  public List getInstructions(String questionID)
    throws DMLException {
    return super.getInstructions(questionID,"QUESTION_INSTR");
  }  

  /**
   * Test application
   */
  public static void main(String[] args) {
    ServiceLocator locator = new SimpleServiceLocator();
    JDBCQuestionInstructionDAO test = 
      new JDBCQuestionInstructionDAO(locator);

    
    // test for createQuestionInstructionComponent
    try {
      
      Instruction questionInst = new InstructionTransferObject();

      questionInst.setVersion(new Float(2.31));
      questionInst.setLongName("Test Question Instr Long Name 030204 1");
      questionInst.setPreferredDefinition("Test Question instr pref def");
      questionInst.setConteIdseq("99BA9DC8-2095-4E69-E034-080020C9C0E0");
      questionInst.setAslName("DRAFT NEW");
      questionInst.setCreatedBy("Hyun Kim");
      questionInst.setDisplayOrder(7);

      int res = test.createInstruction(questionInst,"D4A91DCA-3567-0D59-E034-0003BA0B1A09");
      System.out.println("\n*****Create Question Instruction Result 1: " + res);
    }
    catch (DMLException de) {
      de.printStackTrace();
    }
  }
 
}
